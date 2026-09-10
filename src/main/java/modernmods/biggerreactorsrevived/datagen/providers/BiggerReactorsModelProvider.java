package modernmods.biggerreactorsrevived.datagen.providers;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import modernmods.biggerreactorsrevived.BiggerReactors;
import modernmods.biggerreactorsrevived.machine.blocks.CyaniteReprocessor;
import modernmods.biggerreactorsrevived.multiblocks.heatexchanger.blocks.HeatExchangerFluidPortBlock;
import modernmods.biggerreactorsrevived.multiblocks.reactor.blocks.ReactorAccessPort;
import modernmods.biggerreactorsrevived.multiblocks.reactor.blocks.ReactorFuelRod;
import modernmods.biggerreactorsrevived.multiblocks.reactor.blocks.ReactorPowerTap;
import modernmods.biggerreactorsrevived.multiblocks.reactor.blocks.ReactorRedstonePort;
import modernmods.biggerreactorsrevived.multiblocks.reactor.state.ReactorActivity;
import modernmods.biggerreactorsrevived.multiblocks.turbine.blocks.TurbineFluidPort;
import modernmods.biggerreactorsrevived.multiblocks.turbine.blocks.TurbinePowerTap;
import modernmods.biggerreactorsrevived.multiblocks.turbine.blocks.TurbineRotorBlade;
import modernmods.biggerreactorsrevived.multiblocks.turbine.state.TurbineActivity;
import modernmods.biggerreactorsrevived.multiblocks.turbine.state.TurbineShaftRotationState;
import modernmods.phosphophylliterevived.modular.block.IConnectedTexture;
import modernmods.phosphophylliterevived.multiblock.IAssemblyStateBlock;
import modernmods.phosphophylliterevived.multiblock.rectangular.IAxisPositionBlock;
import modernmods.phosphophylliterevived.util.BlockStates;

import java.util.EnumSet;

public class BiggerReactorsModelProvider extends ModelProvider {

    private static final String[] SIMPLE_BLOCKS = {
            "blutonium_block",
            "cyanite_block",
            "graphite_block",
            "ludicrite_block",
            "raw_uranium_block",
            "uranium_block",
            "uranium_ore",
            "deepslate_uranium_ore",
    };

    private static final String[] HANDHELD_ITEMS = {
            "blutonium_dust",
            "blutonium_ingot",
            "cyanite_dust",
            "cyanite_ingot",
            "graphite_dust",
            "graphite_ingot",
            "liquid_obsidian_bucket",
            "ludicrite_dust",
            "ludicrite_ingot",
            "steam_bucket",
            "uranium_chunk",
            "uranium_dust",
            "uranium_ingot",
    };

    private static final String[][] BLOCK_PARENT_ITEMS = {
            {"blutonium_block", "block/blutonium_block"},
            {"cyanite_block", "block/cyanite_block"},
            {"cyanite_reprocessor", "block/cyanite_reprocessor"},
            {"deepslate_uranium_ore", "block/deepslate_uranium_ore"},
            {"graphite_block", "block/graphite_block"},
            {"heat_exchanger_casing", "block/heat_exchanger/casing/disassembled"},
            {"heat_exchanger_computer_port", "block/heat_exchanger/computer_port"},
            {"heat_exchanger_condenser_channel", "block/heat_exchanger/hot_channel/connected_tb"},
            {"heat_exchanger_evaporator_channel", "block/heat_exchanger/cold_channel/connected_tb"},
            {"heat_exchanger_fluid_port", "block/heat_exchanger/fluid_port/condenser_inlet"},
            {"heat_exchanger_glass", "block/heat_exchanger/glass"},
            {"heat_exchanger_terminal", "block/heat_exchanger/terminal/off"},
            {"ludicrite_block", "block/ludicrite_block"},
            {"raw_uranium_block", "block/raw_uranium_block"},
            {"reactor_access_port", "block/reactor/access_port/inlet"},
            {"reactor_casing", "block/reactor/casing/disassembled"},
            {"reactor_computer_port", "block/reactor/computer_port"},
            {"reactor_control_rod", "block/reactor/control_rod"},
            {"reactor_coolant_port", "block/reactor/coolant_port/inlet"},
            {"reactor_fuel_rod", "block/reactor/fuel_rod"},
            {"reactor_glass", "block/reactor/glass/connected_none"},
            {"reactor_manifold", "block/reactor/manifold/connected_none"},
            {"reactor_power_tap", "block/reactor/power_tap/disassembled"},
            {"reactor_redstone_port", "block/reactor/redstone_port/unlit"},
            {"reactor_terminal", "block/reactor/terminal/off"},
            {"turbine_casing", "block/turbine/casing/disassembled"},
            {"turbine_computer_port", "block/turbine/computer_port"},
            {"turbine_fluid_port", "block/turbine/fluid_port/inlet"},
            {"turbine_glass", "block/turbine/glass/connected_none"},
            {"turbine_power_tap", "block/turbine/power_tap/disassembled"},
            {"turbine_rotor_bearing", "block/turbine/rotor_bearing"},
            {"turbine_rotor_blade", "block/turbine/rotor_blade"},
            {"turbine_rotor_shaft", "block/turbine/rotor_shaft"},
            {"turbine_terminal", "block/turbine/terminal/off"},
            {"uranium_block", "block/uranium_block"},
            {"uranium_ore", "block/uranium_ore"},
    };

    private BlockModelGenerators blocks;

    public BiggerReactorsModelProvider(PackOutput output) {
        super(output, BiggerReactors.modid);
    }

    private static Identifier local(String path) {
        return Identifier.fromNamespaceAndPath(BiggerReactors.modid, path);
    }

    private static Block block(String name) {
        final var location = local(name);
        final var block = BuiltInRegistries.BLOCK.getValue(location);
        if (block == Blocks.AIR) {
            throw new IllegalStateException("Unknown block " + location);
        }
        return block;
    }

    private static Item item(String name) {
        final var location = local(name);
        final var item = BuiltInRegistries.ITEM.getValue(location);
        if (item == Items.AIR) {
            throw new IllegalStateException("Unknown item " + location);
        }
        return item;
    }

    private void emit(Block block, java.util.function.Function<net.minecraft.world.level.block.state.BlockState, MultiVariant> mapper) {
        blocks.blockStateOutput.accept(ModelDsl.forAllStates(block, mapper));
    }

    private static MultiVariant model(String path) {
        return ModelDsl.plain(local(path));
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        this.blocks = blockModels;

        BiggerReactorsBlockModelProvider.registerModels(blockModels.modelOutput);

        for (final var name : SIMPLE_BLOCKS) {
            emit(block(name), state -> model("block/" + name));
        }

        emit(block("steam"), state -> model("fluid/steam"));
        emit(block("liquid_obsidian"), state -> model("fluid/liquid_obsidian"));
        emit(block("liquid_uranium"), state -> model("fluid/liquid_uranium"));

        emit(block("reactor_computer_port"), state -> model("block/reactor/computer_port"));
        emit(block("turbine_computer_port"), state -> model("block/turbine/computer_port"));
        emit(block("heat_exchanger_computer_port"), state -> model("block/heat_exchanger/computer_port"));
        emit(block("reactor_control_rod"), state -> model("block/reactor/control_rod"));
        emit(block("turbine_rotor_bearing"), state -> model("block/turbine/rotor_bearing"));
        emit(block("heat_exchanger_condenser_channel"), state -> model("block/heat_exchanger/hot_channel"));
        emit(block("heat_exchanger_evaporator_channel"), state -> model("block/heat_exchanger/cold_channel"));

        cyaniteReprocessor();

        casing("reactor_casing", "block/reactor/casing");
        casing("turbine_casing", "block/turbine/casing");
        casing("heat_exchanger_casing", "block/heat_exchanger/casing");

        connectedTexture("reactor_glass", "block/reactor/glass");
        connectedTexture("reactor_manifold", "block/reactor/manifold");
        connectedTexture("turbine_glass", "block/turbine/glass");
        connectedTexture("heat_exchanger_glass", "block/heat_exchanger/glass");

        portDirection("reactor_access_port", ReactorAccessPort.PortDirection.PORT_DIRECTION_ENUM_PROPERTY, "block/reactor/access_port");
        portDirection("reactor_coolant_port", ReactorAccessPort.PortDirection.PORT_DIRECTION_ENUM_PROPERTY, "block/reactor/coolant_port");
        portDirection("turbine_fluid_port", TurbineFluidPort.PortDirection.PORT_DIRECTION_ENUM_PROPERTY, "block/turbine/fluid_port");

        heatExchangerFluidPort();
        heatExchangerTerminal();
        reactorTerminal();
        turbineTerminal();
        powerTap("reactor_power_tap", ReactorPowerTap.ConnectionState.CONNECTION_STATE_ENUM_PROPERTY, "block/reactor/power_tap");
        powerTap("turbine_power_tap", TurbinePowerTap.ConnectionState.CONNECTION_STATE_ENUM_PROPERTY, "block/turbine/power_tap");
        redstonePort();
        rotorShaft();
        rotorBlade();
        fuelRod();

        items(itemModels);
    }

    private void items(ItemModelGenerators itemModels) {
        for (final var name : HANDHELD_ITEMS) {
            final var model = local("item/" + name);
            itemModels.modelOutput.accept(model, ModelDsl.flatItem(Identifier.withDefaultNamespace("item/handheld"), local("item/" + name)));
            itemModels.itemModelOutput.accept(item(name), net.minecraft.client.data.models.model.ItemModelUtils.plainModel(model));
        }

        final var wrench = local("item/wrench");
        itemModels.modelOutput.accept(wrench, ModelDsl.flatItem(Identifier.withDefaultNamespace("item/handheld"), Identifier.parse("phosphophyllite:item/debug_tool")));
        itemModels.itemModelOutput.accept(item("wrench"), net.minecraft.client.data.models.model.ItemModelUtils.plainModel(wrench));

        for (final var entry : BLOCK_PARENT_ITEMS) {
            final var model = local("item/" + entry[0]);
            itemModels.modelOutput.accept(model, ModelDsl.inherit(local(entry[1])));
            itemModels.itemModelOutput.accept(item(entry[0]), net.minecraft.client.data.models.model.ItemModelUtils.plainModel(model));
        }
    }

    private void cyaniteReprocessor() {
        emit(block("cyanite_reprocessor"), state -> ModelDsl.rotated(
                local(state.getValue(CyaniteReprocessor.ENABLED) ? "block/cyanite_reprocessor_active" : "block/cyanite_reprocessor"),
                0,
                ((int) state.getValue(CyaniteReprocessor.FACING).toYRot() + 180) % 360));
    }

    private void casing(String blockName, String modelFolder) {
        emit(block(blockName), state -> {
            if (!state.getValue(IAssemblyStateBlock.ASSEMBLED)) {
                return model(modelFolder + "/disassembled");
            }
            final var x = state.getValue(IAxisPositionBlock.AxisPosition.X_AXIS_POSITION);
            final var y = state.getValue(IAxisPositionBlock.AxisPosition.Y_AXIS_POSITION);
            final var z = state.getValue(IAxisPositionBlock.AxisPosition.Z_AXIS_POSITION);
            final var middles = (isMiddle(x) ? 1 : 0) + (isMiddle(y) ? 1 : 0) + (isMiddle(z) ? 1 : 0);
            switch (middles) {
                case 0:
                    return model(modelFolder + "/corner");
                case 2:
                    return model(modelFolder + "/face");
                case 3:
                    return model(modelFolder + "/disassembled");
                default:
                    break;
            }
            int rotationX = 0;
            int rotationY = 0;
            if (isMiddle(z)) {
                rotationX = isLower(y) ? 90 : 270;
                rotationY = isLower(x) ? 0 : 180;
            } else if (isMiddle(y)) {
                if (isLower(x)) {
                    rotationY = isLower(z) ? 0 : 270;
                } else {
                    rotationY = isLower(z) ? 90 : 180;
                }
            } else {
                rotationX = isLower(y) ? 90 : 270;
                rotationY = isLower(z) ? 90 : 270;
            }
            return ModelDsl.rotated(local(modelFolder + "/frame"), rotationX, rotationY);
        });
    }

    private static boolean isMiddle(IAxisPositionBlock.AxisPosition position) {
        return position == IAxisPositionBlock.AxisPosition.MIDDLE;
    }

    private static boolean isLower(IAxisPositionBlock.AxisPosition position) {
        return position == IAxisPositionBlock.AxisPosition.LOWER;
    }

    private void connectedTexture(String blockName, String modelFolder) {
        emit(block(blockName), state -> {
            final var connected = EnumSet.noneOf(Direction.class);
            if (state.getValue(IConnectedTexture.Module.TOP_CONNECTED_PROPERTY)) {
                connected.add(Direction.UP);
            }
            if (state.getValue(IConnectedTexture.Module.BOTTOM_CONNECTED_PROPERTY)) {
                connected.add(Direction.DOWN);
            }
            if (state.getValue(IConnectedTexture.Module.NORTH_CONNECTED_PROPERTY)) {
                connected.add(Direction.NORTH);
            }
            if (state.getValue(IConnectedTexture.Module.SOUTH_CONNECTED_PROPERTY)) {
                connected.add(Direction.SOUTH);
            }
            if (state.getValue(IConnectedTexture.Module.EAST_CONNECTED_PROPERTY)) {
                connected.add(Direction.EAST);
            }
            if (state.getValue(IConnectedTexture.Module.WEST_CONNECTED_PROPERTY)) {
                connected.add(Direction.WEST);
            }
            return model(modelFolder + "/connected_" + BiggerReactorsBlockModelProvider.connectionName(connected));
        });
    }

    private void portDirection(String blockName, EnumProperty<?> property, String modelFolder) {
        emit(block(blockName), state -> model(modelFolder
                + (state.getValue(property).getSerializedName().equals("inlet") ? "/inlet" : "/outlet")));
    }

    private void heatExchangerFluidPort() {
        emit(block("heat_exchanger_fluid_port"), state -> {
            final var condenser = state.getValue(HeatExchangerFluidPortBlock.CONDENSER);
            final var inlet = state.getValue(BlockStates.PORT_DIRECTION);
            if (condenser) {
                return model(inlet ? "block/heat_exchanger/fluid_port/condenser_inlet" : "block/heat_exchanger/fluid_port/condenser_outlet");
            }
            return model(inlet ? "block/heat_exchanger/fluid_port/evaporator_inlet" : "block/heat_exchanger/fluid_port/evaporator_outlet");
        });
    }

    private void heatExchangerTerminal() {
        emit(block("heat_exchanger_terminal"), state -> model(state.getValue(IAssemblyStateBlock.ASSEMBLED)
                ? "block/heat_exchanger/terminal/active"
                : "block/heat_exchanger/terminal/off"));
    }

    private void reactorTerminal() {
        emit(block("reactor_terminal"), state -> {
            if (!state.getValue(IAssemblyStateBlock.ASSEMBLED)) {
                return model("block/reactor/terminal/off");
            }
            final var activity = state.getValue(ReactorActivity.REACTOR_ACTIVITY_ENUM_PROPERTY);
            return model(activity == ReactorActivity.ACTIVE ? "block/reactor/terminal/active" : "block/reactor/terminal/idle");
        });
    }

    private void turbineTerminal() {
        emit(block("turbine_terminal"), state -> {
            if (!state.getValue(IAssemblyStateBlock.ASSEMBLED)) {
                return model("block/turbine/terminal/off");
            }
            final var activity = state.getValue(TurbineActivity.TURBINE_STATE_ENUM_PROPERTY);
            return model(activity == TurbineActivity.ACTIVE ? "block/turbine/terminal/active" : "block/turbine/terminal/idle");
        });
    }

    private void powerTap(String blockName, EnumProperty<?> property, String modelFolder) {
        emit(block(blockName), state -> {
            if (!state.getValue(IAssemblyStateBlock.ASSEMBLED)) {
                return model(modelFolder + "/disassembled");
            }
            final var connectionState = state.getValue(property).getSerializedName();
            return model(modelFolder + (connectionState.equals("connected") ? "/connected" : "/disconnected"));
        });
    }

    private void redstonePort() {
        emit(block("reactor_redstone_port"), state -> model(state.getValue(ReactorRedstonePort.IS_LIT_BOOLEAN_PROPERTY)
                ? "block/reactor/redstone_port/lit"
                : "block/reactor/redstone_port/unlit"));
    }

    private void rotorShaft() {
        final var invisible = Identifier.parse("phosphophyllite:block/invisible");
        emit(block("turbine_rotor_shaft"), state -> {
            if (state.getValue(IAssemblyStateBlock.ASSEMBLED)) {
                return ModelDsl.plain(invisible);
            }
            final var shaft = local("block/turbine/rotor_shaft");
            return switch (state.getValue(TurbineShaftRotationState.TURBINE_SHAFT_ROTATION_STATE_ENUM_PROPERTY)) {
                case X -> ModelDsl.rotated(shaft, 0, 90);
                case Y -> ModelDsl.rotated(shaft, 90, 0);
                default -> ModelDsl.plain(shaft);
            };
        });
    }

    private void rotorBlade() {
        final var invisible = Identifier.parse("phosphophyllite:block/invisible");
        emit(block("turbine_rotor_blade"), state -> {
            if (state.getValue(IAssemblyStateBlock.ASSEMBLED)) {
                return ModelDsl.plain(invisible);
            }
            final var blade = local("block/turbine/rotor_blade");
            final var bladeZ = local("block/turbine/rotor_blade_z");
            final var position = state.getValue(TurbineRotorBlade.BLADE_POSITION);
            return switch (state.getValue(TurbineShaftRotationState.TURBINE_SHAFT_ROTATION_STATE_ENUM_PROPERTY)) {
                case X -> switch (position) {
                    case 0 -> ModelDsl.rotated(blade, 180, 90);
                    case 1 -> ModelDsl.rotated(blade, 0, 90);
                    case 2 -> ModelDsl.rotated(bladeZ, 180, 270);
                    default -> ModelDsl.rotated(bladeZ, 180, 90);
                };
                case Y -> switch (position) {
                    case 0 -> ModelDsl.rotated(blade, 90, 0);
                    case 1 -> ModelDsl.rotated(blade, 90, 180);
                    case 2 -> ModelDsl.rotated(blade, 90, 270);
                    default -> ModelDsl.rotated(blade, 90, 90);
                };
                default -> switch (position) {
                    case 0 -> ModelDsl.rotated(blade, 180, 0);
                    case 1 -> ModelDsl.plain(blade);
                    case 2 -> ModelDsl.rotated(bladeZ, 0, 180);
                    default -> ModelDsl.plain(bladeZ);
                };
            };
        });
    }

    private void fuelRod() {
        var generator = MultiPartGenerator.multiPart(block("reactor_fuel_rod"))
                .with(model("block/reactor/fuel_rod"));
        for (int level = 1; level <= 16; level++) {
            generator = generator.with(
                    new net.minecraft.client.data.models.blockstates.ConditionBuilder().term(ReactorFuelRod.FUEL_HEIGHT_PROPERTY, level),
                    model("block/reactor/fuel_rod/fuel/level_" + level));
            generator = generator.with(
                    new net.minecraft.client.data.models.blockstates.ConditionBuilder().term(ReactorFuelRod.WASTE_HEIGHT_PROPERTY, level),
                    model("block/reactor/fuel_rod/waste/level_" + level));
        }
        blocks.blockStateOutput.accept(generator);
    }
}
