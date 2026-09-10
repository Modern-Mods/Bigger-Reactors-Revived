package modernmods.biggerreactorsrevived.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import modernmods.biggerreactorsrevived.Config;
import modernmods.biggerreactorsrevived.blocks.materials.MaterialBlock;
import modernmods.biggerreactorsrevived.multiblocks.heatexchanger.blocks.HeatExchangerCasingBlock;
import modernmods.biggerreactorsrevived.multiblocks.heatexchanger.blocks.HeatExchangerCondenserChannelBlock;
import modernmods.biggerreactorsrevived.multiblocks.heatexchanger.blocks.HeatExchangerEvaporatorChannelBlock;
import modernmods.biggerreactorsrevived.multiblocks.heatexchanger.blocks.HeatExchangerFluidPortBlock;
import modernmods.biggerreactorsrevived.multiblocks.heatexchanger.blocks.HeatExchangerTerminalBlock;
import modernmods.biggerreactorsrevived.multiblocks.reactor.blocks.ReactorAccessPort;
import modernmods.biggerreactorsrevived.multiblocks.reactor.blocks.ReactorCasing;
import modernmods.biggerreactorsrevived.multiblocks.reactor.blocks.ReactorControlRod;
import modernmods.biggerreactorsrevived.multiblocks.reactor.blocks.ReactorFuelRod;
import modernmods.biggerreactorsrevived.multiblocks.reactor.blocks.ReactorPowerTap;
import modernmods.biggerreactorsrevived.multiblocks.reactor.blocks.ReactorTerminal;
import modernmods.biggerreactorsrevived.multiblocks.turbine.blocks.TurbineCasing;
import modernmods.biggerreactorsrevived.multiblocks.turbine.blocks.TurbineFluidPort;
import modernmods.biggerreactorsrevived.multiblocks.turbine.blocks.TurbinePowerTap;
import modernmods.biggerreactorsrevived.multiblocks.turbine.blocks.TurbineRotorBearing;
import modernmods.biggerreactorsrevived.multiblocks.turbine.blocks.TurbineRotorBlade;
import modernmods.biggerreactorsrevived.multiblocks.turbine.blocks.TurbineRotorShaft;
import modernmods.biggerreactorsrevived.multiblocks.turbine.blocks.TurbineTerminal;
import modernmods.biggerreactorsrevived.registries.ReactorModeratorRegistry;
import modernmods.biggerreactorsrevived.registries.TurbineCoilRegistry;
import modernmods.phosphophylliterevived.registry.OnModLoad;
import org.joml.Vector3i;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MultiblockBuildCommand {

    private interface Builder {
        void build(Structure structure);
    }

    private static final Block[] COIL_CANDIDATES = {
            Blocks.IRON_BLOCK,
            Blocks.GOLD_BLOCK,
            Blocks.COPPER_BLOCK,
            Blocks.DIAMOND_BLOCK,
            Blocks.EMERALD_BLOCK,
    };

    private static final int UNDO_DEPTH = 16;

    private record Snapshot(ResourceKey<Level> dimension, Object2ObjectLinkedOpenHashMap<BlockPos, BlockState> states) {
    }

    private static final Map<UUID, Deque<Snapshot>> UNDO_HISTORY = new HashMap<>();

    @OnModLoad
    private static void onModLoad() {
        NeoForge.EVENT_BUS.addListener(MultiblockBuildCommand::registerCommands);
    }

    private static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(root("big"));
        event.getDispatcher().register(root("bg"));
        event.getDispatcher().register(root("biggerreactors"));
    }

    private static LiteralArgumentBuilder<CommandSourceStack> root(String literal) {
        return Commands.literal(literal).requires(source -> source.hasPermission(2))
                .then(Commands.literal("build")
                        .then(multiblock("reactor", MultiblockBuildCommand::buildReactor, 3, 3,
                                Config.CONFIG.Reactor.MaxLength, Config.CONFIG.Reactor.MaxHeight))
                        .then(multiblock("turbine", MultiblockBuildCommand::buildTurbine, 5, 4,
                                Config.CONFIG.Turbine.MaxLength, Config.CONFIG.Turbine.MaxHeight))
                        .then(multiblock("heat_exchanger", MultiblockBuildCommand::buildHeatExchanger, 4, 3,
                                Config.CONFIG.HeatExchanger.MaxLength, Config.CONFIG.HeatExchanger.MaxHeight)))
                .then(Commands.literal("undo")
                        .executes(context -> undo(context, 1))
                        .then(Commands.argument("count", IntegerArgumentType.integer(1, UNDO_DEPTH))
                                .executes(context -> undo(context, IntegerArgumentType.getInteger(context, "count")))));
    }

    private static LiteralArgumentBuilder<CommandSourceStack> multiblock(String name, Builder builder, int minLength, int minHeight, int maxLength, int maxHeight) {
        return Commands.literal(name)
                .then(Commands.argument("size", IntegerArgumentType.integer(minLength, maxLength))
                        .executes(context -> execute(context, name, builder, size(context), size(context), null))
                        .then(Commands.argument("height", IntegerArgumentType.integer(minHeight, maxHeight))
                                .executes(context -> execute(context, name, builder, size(context), height(context), null))
                                .then(Commands.argument("position", BlockPosArgument.blockPos())
                                        .executes(context -> execute(context, name, builder, size(context), height(context), BlockPosArgument.getLoadedBlockPos(context, "position"))))));
    }

    private static int size(CommandContext<CommandSourceStack> context) {
        return IntegerArgumentType.getInteger(context, "size");
    }

    private static int height(CommandContext<CommandSourceStack> context) {
        return IntegerArgumentType.getInteger(context, "height");
    }

    private static int execute(CommandContext<CommandSourceStack> context, String name, Builder builder, int length, int height, @Nullable BlockPos position) throws CommandSyntaxException {
        final var source = context.getSource();
        final var player = source.getPlayer();
        final Direction facing = player != null ? player.getDirection() : Direction.NORTH;
        final BlockPos origin;
        if (position != null) {
            origin = position;
        } else {
            origin = frontCenteredOrigin(source.getPlayerOrException().blockPosition(), facing, length);
        }
        final var structure = new Structure(origin, length, height, length, facing.getOpposite());
        builder.build(structure);
        final int placed = structure.place(source.getLevel(), player != null ? player.getUUID() : null);
        source.sendSuccess(() -> Component.translatable("command.biggerreactors.build.success",
                name, length, height, length, origin.getX(), origin.getY(), origin.getZ()), true);
        return placed;
    }

    private static BlockPos frontCenteredOrigin(BlockPos playerPosition, Direction facing, int length) {
        final int half = (length - 1) / 2;
        final var near = playerPosition.relative(facing, 2);
        return switch (facing) {
            case NORTH -> new BlockPos(near.getX() - half, playerPosition.getY(), near.getZ() - (length - 1));
            case SOUTH -> new BlockPos(near.getX() - half, playerPosition.getY(), near.getZ());
            case WEST -> new BlockPos(near.getX() - (length - 1), playerPosition.getY(), near.getZ() - half);
            case EAST -> new BlockPos(near.getX(), playerPosition.getY(), near.getZ() - half);
            default -> new BlockPos(near.getX() - half, playerPosition.getY(), near.getZ() - half);
        };
    }

    private static int undo(CommandContext<CommandSourceStack> context, int count) throws CommandSyntaxException {
        final var source = context.getSource();
        final var player = source.getPlayerOrException();
        final var history = UNDO_HISTORY.get(player.getUUID());
        if (history == null || history.isEmpty()) {
            source.sendFailure(Component.translatable("command.biggerreactors.undo.empty"));
            return 0;
        }
        int restored = 0;
        int undone = 0;
        for (int i = 0; i < count && !history.isEmpty(); i++) {
            final var snapshot = history.peek();
            final var level = source.getServer().getLevel(snapshot.dimension());
            if (level == null) {
                break;
            }
            history.pop();
            for (final var entry : snapshot.states().object2ObjectEntrySet()) {
                level.setBlock(entry.getKey(), entry.getValue(), Block.UPDATE_CLIENTS);
            }
            for (final var entry : snapshot.states().object2ObjectEntrySet()) {
                level.updateNeighborsAt(entry.getKey(), entry.getValue().getBlock());
            }
            restored += snapshot.states().size();
            undone++;
        }
        if (undone == 0) {
            source.sendFailure(Component.translatable("command.biggerreactors.undo.empty"));
            return 0;
        }
        final int restoredBlocks = restored;
        final int undoneBuilds = undone;
        source.sendSuccess(() -> Component.translatable("command.biggerreactors.undo.success", undoneBuilds, restoredBlocks), true);
        return restored;
    }

    private static void buildReactor(Structure structure) {
        structure.fillShell(ReactorCasing.INSTANCE);

        final int length = structure.sizeX;
        final int height = structure.sizeY;
        final var moderator = bestModerator();

        for (int x = 1; x < length - 1; x++) {
            for (int z = 1; z < length - 1; z++) {
                final boolean fuelColumn = ((x - 1) + (z - 1)) % 2 == 0;
                for (int y = 1; y < height - 1; y++) {
                    structure.set(x, y, z, fuelColumn ? ReactorFuelRod.INSTANCE : moderator);
                }
                if (fuelColumn) {
                    structure.set(x, height - 1, z, ReactorControlRod.INSTANCE);
                }
            }
        }

        structure.placePorts(ReactorTerminal.INSTANCE, ReactorPowerTap.INSTANCE, ReactorAccessPort.INSTANCE, ReactorAccessPort.INSTANCE);
    }

    private static void buildTurbine(Structure structure) {
        structure.fillShell(TurbineCasing.INSTANCE);

        final int length = structure.sizeX;
        final int height = structure.sizeY;
        final int center = length / 2;
        final var coil = coilBlock();
        final int bladeLayers = (height - 1) / 2;

        structure.set(center, 0, center, TurbineRotorBearing.INSTANCE);
        structure.set(center, height - 1, center, TurbineRotorBearing.INSTANCE);

        for (int y = 1; y < height - 1; y++) {
            structure.set(center, y, center, TurbineRotorShaft.INSTANCE);
            if (coil == null || y <= bladeLayers) {
                for (int x = 1; x < length - 1; x++) {
                    if (x != center) {
                        structure.set(x, y, center, TurbineRotorBlade.INSTANCE);
                    }
                }
                for (int z = 1; z < length - 1; z++) {
                    if (z != center) {
                        structure.set(center, y, z, TurbineRotorBlade.INSTANCE);
                    }
                }
                continue;
            }
            for (int x = 1; x < length - 1; x++) {
                for (int z = 1; z < length - 1; z++) {
                    if (x != center || z != center) {
                        structure.set(x, y, z, coil);
                    }
                }
            }
        }

        structure.placePorts(TurbineTerminal.INSTANCE, TurbinePowerTap.INSTANCE, TurbineFluidPort.INSTANCE, TurbineFluidPort.INSTANCE);
    }

    private static void buildHeatExchanger(Structure structure) {
        structure.fillShell(HeatExchangerCasingBlock.INSTANCE);

        final int length = structure.sizeX;
        final int condenserDepth = 1;
        final int evaporatorDepth = length - 2;

        for (int across = 1; across < length - 1; across++) {
            structure.setOriented(across, 1, condenserDepth, HeatExchangerCondenserChannelBlock.INSTANCE);
            structure.setOriented(across, 1, evaporatorDepth, HeatExchangerEvaporatorChannelBlock.INSTANCE);
        }

        structure.setOriented(0, 1, condenserDepth, HeatExchangerFluidPortBlock.INSTANCE);
        structure.setOriented(length - 1, 1, condenserDepth, HeatExchangerFluidPortBlock.INSTANCE);
        structure.setOriented(0, 1, evaporatorDepth, HeatExchangerFluidPortBlock.INSTANCE);
        structure.setOriented(length - 1, 1, evaporatorDepth, HeatExchangerFluidPortBlock.INSTANCE);

        structure.placePorts(HeatExchangerTerminalBlock.INSTANCE, null, null, null);
    }

    private static Block bestModerator() {
        Block best = null;
        double bestScore = 0;
        for (final var block : BuiltInRegistries.BLOCK) {
            final var properties = ReactorModeratorRegistry.blockModeratorProperties(block);
            if (properties == null) {
                continue;
            }
            final var state = block.defaultBlockState();
            if (state.isAir() || !state.getFluidState().isEmpty()) {
                continue;
            }
            final double score = properties.moderation() * properties.heatEfficiency();
            if (score > bestScore) {
                bestScore = score;
                best = block;
            }
        }
        if (best != null) {
            return best;
        }
        if (ReactorModeratorRegistry.isBlockAllowed(MaterialBlock.GRAPHITE)) {
            return MaterialBlock.GRAPHITE;
        }
        return Blocks.AIR;
    }

    @Nullable
    private static Block coilBlock() {
        for (final var candidate : COIL_CANDIDATES) {
            if (TurbineCoilRegistry.isBlockAllowed(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    private static void pushUndo(ServerLevel level, UUID owner, Object2ObjectLinkedOpenHashMap<BlockPos, BlockState> states) {
        final var history = UNDO_HISTORY.computeIfAbsent(owner, key -> new ArrayDeque<>());
        history.push(new Snapshot(level.dimension(), states));
        while (history.size() > UNDO_DEPTH) {
            history.removeLast();
        }
    }

    private static final class Structure {

        private final Object2ObjectLinkedOpenHashMap<BlockPos, Block> blocks = new Object2ObjectLinkedOpenHashMap<>();
        private final BlockPos origin;
        private final int sizeX;
        private final int sizeY;
        private final int sizeZ;
        private final Direction front;
        private Block shell = Blocks.AIR;

        private Structure(BlockPos origin, int sizeX, int sizeY, int sizeZ, Direction front) {
            this.origin = origin;
            this.sizeX = sizeX;
            this.sizeY = sizeY;
            this.sizeZ = sizeZ;
            this.front = front.getAxis().isHorizontal() ? front : Direction.NORTH;
        }

        private void set(int x, int y, int z, Block block) {
            blocks.put(origin.offset(x, y, z), block);
        }

        private void setOriented(int across, int y, int depth, Block block) {
            int x = across;
            int z = depth;
            switch (front) {
                case NORTH:
                    x = sizeX - 1 - across;
                    z = depth;
                    break;
                case SOUTH:
                    x = across;
                    z = sizeZ - 1 - depth;
                    break;
                case WEST:
                    x = depth;
                    z = across;
                    break;
                default:
                    x = sizeX - 1 - depth;
                    z = sizeZ - 1 - across;
                    break;
            }
            set(x, y, z, block);
        }

        private boolean isShell(Vector3i slot) {
            final var current = blocks.get(origin.offset(slot.x, slot.y, slot.z));
            return current == null || current == shell;
        }

        private void fillShell(Block block) {
            shell = block;
            for (int x = 0; x < sizeX; x++) {
                for (int y = 0; y < sizeY; y++) {
                    for (int z = 0; z < sizeZ; z++) {
                        final boolean shellSlot = x == 0 || x == sizeX - 1 || y == 0 || y == sizeY - 1 || z == 0 || z == sizeZ - 1;
                        set(x, y, z, shellSlot ? block : Blocks.AIR);
                    }
                }
            }
        }

        private void placePorts(@Nullable Block frontPort, @Nullable Block backPort, @Nullable Block leftPort, @Nullable Block rightPort) {
            placeOnFace(front, frontPort);
            placeOnFace(front.getOpposite(), backPort);
            placeOnFace(front.getCounterClockWise(), leftPort);
            placeOnFace(front.getClockWise(), rightPort);
        }

        private void placeOnFace(Direction face, @Nullable Block block) {
            if (block == null) {
                return;
            }
            for (final var slot : sortedFaceSlots(face)) {
                if (isShell(slot)) {
                    set(slot.x, slot.y, slot.z, block);
                    return;
                }
            }
        }

        private List<Vector3i> sortedFaceSlots(Direction face) {
            final var slots = switch (face) {
                case NORTH -> faceSlots(true, 0);
                case SOUTH -> faceSlots(true, sizeZ - 1);
                case WEST -> faceSlots(false, 0);
                default -> faceSlots(false, sizeX - 1);
            };
            final double centerX = (sizeX - 1) / 2.0;
            final double centerZ = (sizeZ - 1) / 2.0;
            slots.sort(Comparator.<Vector3i>comparingInt(slot -> slot.y).thenComparingDouble(slot -> {
                final double dx = slot.x - centerX;
                final double dz = slot.z - centerZ;
                return dx * dx + dz * dz;
            }));
            return slots;
        }

        private List<Vector3i> faceSlots(boolean zFace, int value) {
            final var slots = new ArrayList<Vector3i>();
            for (int y = 1; y < sizeY - 1; y++) {
                if (zFace) {
                    for (int x = 1; x < sizeX - 1; x++) {
                        slots.add(new Vector3i(x, y, value));
                    }
                } else {
                    for (int z = 1; z < sizeZ - 1; z++) {
                        slots.add(new Vector3i(value, y, z));
                    }
                }
            }
            return slots;
        }

        private int place(ServerLevel level, @Nullable UUID owner) {
            final var previous = new Object2ObjectLinkedOpenHashMap<BlockPos, BlockState>();
            for (final var entry : blocks.object2ObjectEntrySet()) {
                previous.put(entry.getKey(), level.getBlockState(entry.getKey()));
            }
            for (final var entry : blocks.object2ObjectEntrySet()) {
                level.setBlock(entry.getKey(), entry.getValue().defaultBlockState(), Block.UPDATE_CLIENTS);
            }
            for (final var entry : blocks.object2ObjectEntrySet()) {
                level.updateNeighborsAt(entry.getKey(), entry.getValue());
            }
            if (owner != null) {
                pushUndo(level, owner, previous);
            }
            return blocks.size();
        }
    }
}
