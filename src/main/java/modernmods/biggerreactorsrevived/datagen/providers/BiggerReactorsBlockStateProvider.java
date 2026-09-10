package modernmods.biggerreactorsrevived.datagen.providers;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
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

public class BiggerReactorsBlockStateProvider extends BlockStateProvider {

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

    public BiggerReactorsBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BiggerReactors.modid, existingFileHelper);
    }

    private static Block block(String name) {
        final var location = ResourceLocation.fromNamespaceAndPath(BiggerReactors.modid, name);
        final var block = BuiltInRegistries.BLOCK.get(location);
        if (block == net.minecraft.world.level.block.Blocks.AIR) {
            throw new IllegalStateException("Unknown block " + location);
        }
        return block;
    }

    private ModelFile generated(String path) {
        return models().getBuilder(path);
    }

    private ModelFile authored(String path) {
        return models().getExistingFile(ResourceLocation.fromNamespaceAndPath(BiggerReactors.modid, path));
    }

    @Override
    protected void registerStatesAndModels() {
        BiggerReactorsBlockModelProvider.registerModels(models());

        for (final var name : SIMPLE_BLOCKS) {
            simpleBlock(block(name), generated("block/" + name));
        }

        simpleBlock(block("steam"), authored("fluid/steam"));
        simpleBlock(block("liquid_obsidian"), authored("fluid/liquid_obsidian"));
        simpleBlock(block("liquid_uranium"), authored("fluid/liquid_uranium"));

        simpleBlock(block("reactor_computer_port"), generated("block/reactor/computer_port"));
        simpleBlock(block("turbine_computer_port"), generated("block/turbine/computer_port"));
        simpleBlock(block("heat_exchanger_computer_port"), generated("block/heat_exchanger/computer_port"));
        simpleBlock(block("reactor_control_rod"), generated("block/reactor/control_rod"));
        simpleBlock(block("turbine_rotor_bearing"), generated("block/turbine/rotor_bearing"));
        simpleBlock(block("heat_exchanger_condenser_channel"), authored("block/heat_exchanger/hot_channel"));
        simpleBlock(block("heat_exchanger_evaporator_channel"), authored("block/heat_exchanger/cold_channel"));

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
    }

    private void cyaniteReprocessor() {
        final var idle = generated("block/cyanite_reprocessor");
        final var active = generated("block/cyanite_reprocessor_active");
        getVariantBuilder(block("cyanite_reprocessor")).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(CyaniteReprocessor.ENABLED) ? active : idle)
                .rotationY(((int) state.getValue(CyaniteReprocessor.FACING).toYRot() + 180) % 360)
                .build());
    }

    private void casing(String blockName, String modelFolder) {
        final var corner = generated(modelFolder + "/corner");
        final var frame = authored(modelFolder + "/frame");
        final var face = generated(modelFolder + "/face");
        final var disassembled = generated(modelFolder + "/disassembled");
        getVariantBuilder(block(blockName)).forAllStates(state -> {
            if (!state.getValue(IAssemblyStateBlock.ASSEMBLED)) {
                return ConfiguredModel.builder().modelFile(disassembled).build();
            }
            final var x = state.getValue(IAxisPositionBlock.AxisPosition.X_AXIS_POSITION);
            final var y = state.getValue(IAxisPositionBlock.AxisPosition.Y_AXIS_POSITION);
            final var z = state.getValue(IAxisPositionBlock.AxisPosition.Z_AXIS_POSITION);
            final var middles = (isMiddle(x) ? 1 : 0) + (isMiddle(y) ? 1 : 0) + (isMiddle(z) ? 1 : 0);
            switch (middles) {
                case 0:
                    return ConfiguredModel.builder().modelFile(corner).build();
                case 2:
                    return ConfiguredModel.builder().modelFile(face).build();
                case 3:
                    return ConfiguredModel.builder().modelFile(disassembled).build();
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
            return ConfiguredModel.builder().modelFile(frame).rotationX(rotationX).rotationY(rotationY).build();
        });
    }

    private static boolean isMiddle(IAxisPositionBlock.AxisPosition position) {
        return position == IAxisPositionBlock.AxisPosition.MIDDLE;
    }

    private static boolean isLower(IAxisPositionBlock.AxisPosition position) {
        return position == IAxisPositionBlock.AxisPosition.LOWER;
    }

    private void connectedTexture(String blockName, String modelFolder) {
        getVariantBuilder(block(blockName)).forAllStates(state -> {
            final var connected = java.util.EnumSet.noneOf(Direction.class);
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
            final var name = modelFolder + "/connected_" + BiggerReactorsBlockModelProvider.connectionName(connected);
            return ConfiguredModel.builder().modelFile(generated(name)).build();
        });
    }

    private void portDirection(String blockName, net.minecraft.world.level.block.state.properties.EnumProperty<?> property, String modelFolder) {
        final var inlet = generated(modelFolder + "/inlet");
        final var outlet = generated(modelFolder + "/outlet");
        getVariantBuilder(block(blockName)).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(property).getSerializedName().equals("inlet") ? inlet : outlet)
                .build());
    }

    private void heatExchangerFluidPort() {
        final var condenserInlet = generated("block/heat_exchanger/fluid_port/condenser_inlet");
        final var condenserOutlet = generated("block/heat_exchanger/fluid_port/condenser_outlet");
        final var evaporatorInlet = generated("block/heat_exchanger/fluid_port/evaporator_inlet");
        final var evaporatorOutlet = generated("block/heat_exchanger/fluid_port/evaporator_outlet");
        getVariantBuilder(block("heat_exchanger_fluid_port")).forAllStates(state -> {
            final var condenser = state.getValue(HeatExchangerFluidPortBlock.CONDENSER);
            final var inlet = state.getValue(BlockStates.PORT_DIRECTION);
            final ModelFile model;
            if (condenser) {
                model = inlet ? condenserInlet : condenserOutlet;
            } else {
                model = inlet ? evaporatorInlet : evaporatorOutlet;
            }
            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    private void heatExchangerTerminal() {
        final var off = generated("block/heat_exchanger/terminal/off");
        final var active = generated("block/heat_exchanger/terminal/active");
        getVariantBuilder(block("heat_exchanger_terminal")).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(IAssemblyStateBlock.ASSEMBLED) ? active : off)
                .build());
    }

    private void reactorTerminal() {
        final var off = generated("block/reactor/terminal/off");
        final var active = generated("block/reactor/terminal/active");
        final var idle = generated("block/reactor/terminal/idle");
        getVariantBuilder(block("reactor_terminal")).forAllStates(state -> {
            if (!state.getValue(IAssemblyStateBlock.ASSEMBLED)) {
                return ConfiguredModel.builder().modelFile(off).build();
            }
            final var activity = state.getValue(ReactorActivity.REACTOR_ACTIVITY_ENUM_PROPERTY);
            return ConfiguredModel.builder().modelFile(activity == ReactorActivity.ACTIVE ? active : idle).build();
        });
    }

    private void turbineTerminal() {
        final var off = generated("block/turbine/terminal/off");
        final var active = generated("block/turbine/terminal/active");
        final var idle = generated("block/turbine/terminal/idle");
        getVariantBuilder(block("turbine_terminal")).forAllStates(state -> {
            if (!state.getValue(IAssemblyStateBlock.ASSEMBLED)) {
                return ConfiguredModel.builder().modelFile(off).build();
            }
            final var activity = state.getValue(TurbineActivity.TURBINE_STATE_ENUM_PROPERTY);
            return ConfiguredModel.builder().modelFile(activity == TurbineActivity.ACTIVE ? active : idle).build();
        });
    }

    private void powerTap(String blockName, net.minecraft.world.level.block.state.properties.EnumProperty<?> property, String modelFolder) {
        final var disassembled = generated(modelFolder + "/disassembled");
        final var connected = generated(modelFolder + "/connected");
        final var disconnected = generated(modelFolder + "/disconnected");
        getVariantBuilder(block(blockName)).forAllStates(state -> {
            if (!state.getValue(IAssemblyStateBlock.ASSEMBLED)) {
                return ConfiguredModel.builder().modelFile(disassembled).build();
            }
            final var connectionState = state.getValue(property).getSerializedName();
            return ConfiguredModel.builder().modelFile(connectionState.equals("connected") ? connected : disconnected).build();
        });
    }

    private void redstonePort() {
        final var lit = generated("block/reactor/redstone_port/lit");
        final var unlit = generated("block/reactor/redstone_port/unlit");
        getVariantBuilder(block("reactor_redstone_port")).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(ReactorRedstonePort.IS_LIT_BOOLEAN_PROPERTY) ? lit : unlit)
                .build());
    }

    private void rotorShaft() {
        final var invisible = models().getExistingFile(ResourceLocation.parse("phosphophyllite:block/invisible"));
        final var shaft = authored("block/turbine/rotor_shaft");
        getVariantBuilder(block("turbine_rotor_shaft")).forAllStates(state -> {
            if (state.getValue(IAssemblyStateBlock.ASSEMBLED)) {
                return ConfiguredModel.builder().modelFile(invisible).build();
            }
            return switch (state.getValue(TurbineShaftRotationState.TURBINE_SHAFT_ROTATION_STATE_ENUM_PROPERTY)) {
                case X -> ConfiguredModel.builder().modelFile(shaft).rotationY(90).build();
                case Y -> ConfiguredModel.builder().modelFile(shaft).rotationX(90).build();
                default -> ConfiguredModel.builder().modelFile(shaft).build();
            };
        });
    }

    private void rotorBlade() {
        final var invisible = models().getExistingFile(ResourceLocation.parse("phosphophyllite:block/invisible"));
        final var blade = authored("block/turbine/rotor_blade");
        final var bladeZ = authored("block/turbine/rotor_blade_z");
        getVariantBuilder(block("turbine_rotor_blade")).forAllStates(state -> {
            if (state.getValue(IAssemblyStateBlock.ASSEMBLED)) {
                return ConfiguredModel.builder().modelFile(invisible).build();
            }
            final var position = state.getValue(TurbineRotorBlade.BLADE_POSITION);
            return switch (state.getValue(TurbineShaftRotationState.TURBINE_SHAFT_ROTATION_STATE_ENUM_PROPERTY)) {
                case X -> switch (position) {
                    case 0 -> ConfiguredModel.builder().modelFile(blade).rotationX(180).rotationY(90).build();
                    case 1 -> ConfiguredModel.builder().modelFile(blade).rotationY(90).build();
                    case 2 -> ConfiguredModel.builder().modelFile(bladeZ).rotationX(180).rotationY(270).build();
                    default -> ConfiguredModel.builder().modelFile(bladeZ).rotationX(180).rotationY(90).build();
                };
                case Y -> switch (position) {
                    case 0 -> ConfiguredModel.builder().modelFile(blade).rotationX(90).build();
                    case 1 -> ConfiguredModel.builder().modelFile(blade).rotationX(90).rotationY(180).build();
                    case 2 -> ConfiguredModel.builder().modelFile(blade).rotationX(90).rotationY(270).build();
                    default -> ConfiguredModel.builder().modelFile(blade).rotationX(90).rotationY(90).build();
                };
                default -> switch (position) {
                    case 0 -> ConfiguredModel.builder().modelFile(blade).rotationX(180).build();
                    case 1 -> ConfiguredModel.builder().modelFile(blade).build();
                    case 2 -> ConfiguredModel.builder().modelFile(bladeZ).rotationY(180).build();
                    default -> ConfiguredModel.builder().modelFile(bladeZ).build();
                };
            };
        });
    }

    private void fuelRod() {
        final var builder = getMultipartBuilder(block("reactor_fuel_rod"));
        builder.part().modelFile(generated("block/reactor/fuel_rod")).addModel().end();
        for (int level = 1; level <= 16; level++) {
            builder.part().modelFile(authored("block/reactor/fuel_rod/fuel/level_" + level)).addModel()
                    .condition(ReactorFuelRod.FUEL_HEIGHT_PROPERTY, level).end();
            builder.part().modelFile(authored("block/reactor/fuel_rod/waste/level_" + level)).addModel()
                    .condition(ReactorFuelRod.WASTE_HEIGHT_PROPERTY, level).end();
        }
    }
}
