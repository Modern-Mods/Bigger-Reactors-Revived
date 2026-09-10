package modernmods.biggerreactorsrevived.datagen.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import modernmods.biggerreactorsrevived.BiggerReactors;

public class BiggerReactorsItemModelProvider extends ItemModelProvider {

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

    public BiggerReactorsItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BiggerReactors.modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (final var name : HANDHELD_ITEMS) {
            withExistingParent(name, "item/handheld")
                    .texture("layer0", ResourceLocation.fromNamespaceAndPath(BiggerReactors.modid, "item/" + name));
        }

        withExistingParent("wrench", "item/handheld")
                .texture("layer0", ResourceLocation.parse("phosphophyllite:item/debug_tool"));

        for (final var entry : BLOCK_PARENT_ITEMS) {
            withExistingParent(entry[0], ResourceLocation.fromNamespaceAndPath(BiggerReactors.modid, entry[1]));
        }
    }
}
