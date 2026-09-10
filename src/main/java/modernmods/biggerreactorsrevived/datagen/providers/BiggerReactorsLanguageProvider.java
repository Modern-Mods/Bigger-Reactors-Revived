package modernmods.biggerreactorsrevived.datagen.providers;

import modernmods.biggerreactorsrevived.BiggerReactors;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class BiggerReactorsLanguageProvider extends LanguageProvider {

    public static final String[] LOCALES = {"en_us", "es_es", "es_mx", "es_ar", "zh_cn"};

    private final String locale;

    public BiggerReactorsLanguageProvider(PackOutput output, String locale) {
        super(output, BiggerReactors.modid, locale);
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        switch (locale) {
            case "es_es", "es_mx", "es_ar" -> spanish();
            case "zh_cn" -> chinese();
            default -> english();
        }
    }

    private void english() {
        add("item_group.biggerreactors", "Bigger Reactors");

        add("book.biggerreactors.guide", "Bigger Reactors");
        add("book.biggerreactors.guide.subtitle", "Big Reactors, but even bigger");
        add("book.biggerreactors.guide.landing", "Welcome to $(thing)Bigger Reactors$(). This guide covers every block, item and multiblock the mod adds, from your first chunk of uranium to a fully automated reactor feeding a bank of turbines.$(br2)Pick a category below to get started.");

        add("item.biggerreactors.wrench", "Reactor Wrench");

        add("fluid.biggerreactors.liquid_uranium", "Liquid Uranium");
        add("item.biggerreactors.liquid_uranium_bucket", "Liquid Uranium Bucket");
        add("fluid.biggerreactors.steam", "Steam");
        add("item.biggerreactors.steam_bucket", "Steam Bucket");
        add("fluid.biggerreactors.liquid_obsidian", "Liquid Obsidian");
        add("item.biggerreactors.liquid_obsidian_bucket", "Liquid Obsidian Bucket");

        add("fluid.biggerreactors.superheated_sodium", "Liquid Superheated Sodium");
        add("item.biggerreactors.superheated_sodium_bucket", "Liquid Superheated Sodium Bucket");

        add("block.biggerreactors.uranium_ore", "Uranium Ore");
        add("block.biggerreactors.deepslate_uranium_ore", "Deepslate Uranium Ore");
        add("block.biggerreactors.uranium_block", "Uranium Block");
        add("block.biggerreactors.raw_uranium_block", "Raw Uranium Block");
        add("item.biggerreactors.uranium_dust", "Uranium Dust");
        add("item.biggerreactors.uranium_ingot", "Uranium Ingot");
        add("item.biggerreactors.uranium_chunk", "Uranium Chunk");

        add("block.biggerreactors.blutonium_block", "Blutonium Block");
        add("item.biggerreactors.blutonium_dust", "Blutonium Dust");
        add("item.biggerreactors.blutonium_ingot", "Blutonium Ingot");

        add("block.biggerreactors.cyanite_block", "Cyanite Block");
        add("item.biggerreactors.cyanite_dust", "Cyanite Dust");
        add("item.biggerreactors.cyanite_ingot", "Cyanite Ingot");

        add("block.biggerreactors.graphite_block", "Graphite Block");
        add("item.biggerreactors.graphite_dust", "Graphite Dust");
        add("item.biggerreactors.graphite_ingot", "Graphite Ingot");

        add("block.biggerreactors.ludicrite_block", "Ludicrite Block");
        add("item.biggerreactors.ludicrite_dust", "Ludicrite Dust");
        add("item.biggerreactors.ludicrite_ingot", "Ludicrite Ingot");

        add("tooltip.biggerreactors.is_a_moderator", "§eThis can be used as a moderator in a Bigger Reactor");
        add("tooltip.biggerreactors.is_a_coil", "§eThis block can be used as a coil in a Bigger Turbine");

        add("command.biggerreactors.build.success", "Built a %s of %sx%sx%s at %s, %s, %s");
        add("command.biggerreactors.undo.success", "Undid %s builds, restored %s blocks");
        add("command.biggerreactors.undo.empty", "Nothing to undo");

        add("multiblock.error.biggerreactors.no_terminal", "Reactors require at least one terminal.");
        add("multiblock.error.biggerreactors.no_rods", "Reactors must have at least one fuel element.");
        add("multiblock.error.biggerreactors.control_rod_not_on_top", "Control rods can only be placed on the top of the reactor (%d, %d, %d).");
        add("multiblock.error.biggerreactors.fuel_rod_gap", "All fuel elements must extend from top of the reactor to the bottom (%d, %d, %d).");
        add("multiblock.error.biggerreactors.no_control_rod_for_fuel_rod", "All fuel elements must be topped with a control rod (%d, %d).");
        add("multiblock.error.biggerreactors.dangling_internal_part", "There appears to be another reactor inside this one, is something wrong? (%d, %d, %d).");
        add("multiblock.error.biggerreactors.coolant_and_power_ports", "Reactors may only have coolant ports (actively-cooled type) or power taps (passively-cooled type), not both.");
        add("multiblock.error.biggerreactors.no_manifold_neighbor", "Reactor coolant manifolds must neighbor another manifold or a casing");

        add("screen.biggerreactors.disabled", "Disabled");

        add("block.biggerreactors.cyanite_reprocessor", "Cyanite Reprocessor");
        add("screen.biggerreactors.cyanite_reprocessor", "Cyanite Reprocessor");
        add("screen.biggerreactors.cyanite_reprocessor.internal_battery.tooltip", "Internal Battery");
        add("screen.biggerreactors.cyanite_reprocessor.water_tank.tooltip", "Water Tank");

        add("block.biggerreactors.reactor_casing", "Reactor Casing");
        add("block.biggerreactors.reactor_glass", "Reactor Glass");
        add("block.biggerreactors.reactor_fuel_rod", "Reactor Fuel Rod");
        add("block.biggerreactors.reactor_power_tap", "Reactor Power Tap");
        add("block.biggerreactors.reactor_access_port", "Reactor Access Port");
        add("block.biggerreactors.reactor_coolant_port", "Reactor Coolant Port");
        add("block.biggerreactors.reactor_computer_port", "Reactor Computer Port");
        add("block.biggerreactors.reactor_redstone_port", "Reactor Redstone Port");
        add("block.biggerreactors.reactor_manifold", "Reactor Coolant Manifold");

        add("block.biggerreactors.reactor_terminal", "Reactor Terminal");
        add("screen.biggerreactors.reactor_terminal", "Reactor");
        add("screen.biggerreactors.reactor_terminal.temperature.tooltip", "Temperature\n§7How hot the reactor is.");
        add("screen.biggerreactors.reactor_terminal.energy_generation_rate.tooltip", "RF Generation Rate\n§7How much RF the reactor is generating.");
        add("screen.biggerreactors.reactor_terminal.exhaust_generation_rate.tooltip", "Exhaust Generation Rate\n§7How much exhaust the reactor is generating.");
        add("screen.biggerreactors.reactor_terminal.fuel_usage_rate.tooltip", "Fuel Usage Rate\n§7How much fuel the reactor is consuming.");
        add("screen.biggerreactors.reactor_terminal.reactivity_rate.tooltip", "Reactivity Rate\n§7How reactive the reactor is.");
        add("screen.biggerreactors.reactor_terminal.fuel_mix.tooltip", "Fuel Mix\n§7Ratio of fuel to waste inside the reactor.");
        add("screen.biggerreactors.reactor_terminal.case_heat.tooltip", "Case Heat\n§7How hot the reactor is.");
        add("screen.biggerreactors.reactor_terminal.fuel_heat.tooltip", "Fuel Heat\n§7How hot the fuel is.");
        add("screen.biggerreactors.reactor_terminal.fuel_mix_gauge.tooltip", "%%s/%%s Mixed\n§a%%s of Fuel\n§b%%s of Waste");
        add("screen.biggerreactors.reactor_terminal.internal_battery.tooltip", "Internal Battery\n§7How much RF is stored internally.");
        add("screen.biggerreactors.reactor_terminal.coolant_intake_tank.tooltip", "Coolant Intake Tank\n§7How much coolant is stored internally.");
        add("screen.biggerreactors.reactor_terminal.exhaust_tank.tooltip", "Exhaust Tank\n§7How much exhaust is stored internally.");
        add("screen.biggerreactors.reactor_terminal.activity_toggle.online", "Status: §2Online");
        add("screen.biggerreactors.reactor_terminal.activity_toggle.offline", "Status: §4Offline");
        add("screen.biggerreactors.reactor_terminal.activity_toggle.tooltip", "Toggle Reactor Status\n§7Turn the reactor on or off.");
        add("screen.biggerreactors.reactor_terminal.auto_eject_toggle.enabled", "Waste Ejection: §3Enabled");
        add("screen.biggerreactors.reactor_terminal.auto_eject_toggle.disabled", "Waste Ejection: §4Disabled");
        add("screen.biggerreactors.reactor_terminal.auto_eject_toggle.tooltip", "Toggle Waste Ejection\n§7Set whether waste will be ejected automatically.");

        add("block.biggerreactors.reactor_control_rod", "Reactor Control Rod");
        add("screen.biggerreactors.reactor_control_rod", "Reactor Control Rod");
        add("screen.biggerreactors.reactor_control_rod.name", "Name:");
        add("screen.biggerreactors.reactor_control_rod.apply.tooltip", "Apply");
        add("screen.biggerreactors.reactor_control_rod.retract_rod.tooltip", "Retract Rod\n§7Less insertion increases reaction rate.\n§8[Shift]: Retract by 10%.\n§8[Ctrl]: Retract by 50%.\n§8[Ctrl + Shift]: Max retraction.\n§8[Alt]: Apply to all rods.");
        add("screen.biggerreactors.reactor_control_rod.insert_rod.tooltip", "Insert Rod\n§7More insertion reduces reaction rate.\n§8[Shift]: Insert by 10%.\n§8[Ctrl]: Insert by 50%.\n§8[Ctrl + Shift]: Max insertion.\n§8[Alt]: Apply to all rods.");

        add("screen.biggerreactors.reactor_coolant_port", "Reactor Coolant Port");
        add("screen.biggerreactors.reactor_coolant_port.direction_toggle.input", "Direction: §3Input");
        add("screen.biggerreactors.reactor_coolant_port.direction_toggle.output", "Direction: §cOutput");
        add("screen.biggerreactors.reactor_coolant_port.direction_toggle.tooltip", "Toggle Direction\n§7Change which direction this port flows.");
        add("screen.biggerreactors.reactor_coolant_port.manual_dump", "Manual Tank Dump");
        add("screen.biggerreactors.reactor_coolant_port.manual_dump.tooltip", "Dump Tank Fluids\n§7Manually dump all fluids from the reactor.");

        add("screen.biggerreactors.reactor_access_port", "Reactor Access Port");
        add("screen.biggerreactors.reactor_access_port.direction_toggle.input", "Direction: §6Input");
        add("screen.biggerreactors.reactor_access_port.direction_toggle.output", "Direction: §9Output");
        add("screen.biggerreactors.reactor_access_port.direction_toggle.tooltip", "Toggle Direction\n§7Change which direction this port flows.");
        add("screen.biggerreactors.reactor_access_port.fuel_mode_toggle.fuel", "Outputting: §6Fuel");
        add("screen.biggerreactors.reactor_access_port.fuel_mode_toggle.waste", "Outputting: §3Waste");
        add("screen.biggerreactors.reactor_access_port.fuel_mode_toggle.nope", "Outputting: §7---");
        add("screen.biggerreactors.reactor_access_port.fuel_mode_toggle.tooltip", "Toggle Output\n§7Change what this port is outputting.");
        add("screen.biggerreactors.reactor_access_port.manual_eject", "Manual Waste Eject");
        add("screen.biggerreactors.reactor_access_port.manual_eject.tooltip", "Eject All Waste\n§7Manually eject all waste from the reactor.");

        add("screen.biggerreactors.reactor_redstone_port", "Reactor Redstone Port");
        add("screen.biggerreactors.reactor_redstone_port.choose_setting", "Pick a setting:");
        add("screen.biggerreactors.reactor_redstone_port.apply.tooltip", "Apply");
        add("screen.biggerreactors.reactor_redstone_port.input_reactor_activity", "Change Reactor Status (Input)");
        add("screen.biggerreactors.reactor_redstone_port.input_reactor_activity.tooltip", "Change Reactor Status (Input)\n§8[On Pulse] Toggle whether the reactor is on or off.\n§8[On Signal] When redstone is applied, the reactor is active.");
        add("screen.biggerreactors.reactor_redstone_port.input_control_rod_insertion", "Change Control Rod Insertion (Input)");
        add("screen.biggerreactors.reactor_redstone_port.input_control_rod_insertion.while_on", "While signal applied:");
        add("screen.biggerreactors.reactor_redstone_port.input_control_rod_insertion.while_off", "Otherwise:");
        add("screen.biggerreactors.reactor_redstone_port.input_control_rod_insertion.tooltip", "Change Control Rod Insertion (Input)\n§8[On Pulse] Move the control rod, depending on the current mode.\n§8[On Signal] Set the insertion level depending on whether redstone is applied.");
        add("screen.biggerreactors.reactor_redstone_port.input_eject_waste", "Eject Waste (Input)");
        add("screen.biggerreactors.reactor_redstone_port.input_eject_waste.tooltip", "Eject Waste (Input)\n§8[On Pulse] Eject all waste from the reactor.\n§8[On Signal] N/A.");
        add("screen.biggerreactors.reactor_redstone_port.output_fuel_temp", "Fuel Temperature (Output)");
        add("screen.biggerreactors.reactor_redstone_port.output_fuel_temp.tooltip", "Fuel Temperature (Output)\n§8[While Above/Below] Trigger when the fuel temperature is above or below the set temperature.");
        add("screen.biggerreactors.reactor_redstone_port.output_casing_temp", "Casing Temperature (Output)");
        add("screen.biggerreactors.reactor_redstone_port.output_casing_temp.tooltip", "Casing Temperature (Output)\n§8[While Above/Below] Trigger when the fuel temperature is above or below the set temperature.");
        add("screen.biggerreactors.reactor_redstone_port.output_fuel_enrichment", "Fuel Enrichment (Output)");
        add("screen.biggerreactors.reactor_redstone_port.output_fuel_enrichment.tooltip", "Fuel Enrichment (Output)\n§8[While Above/Below] Trigger when the fuel enrichment level is above or below the set percentage.");
        add("screen.biggerreactors.reactor_redstone_port.output_fuel_amount", "Fuel Amount (Output)");
        add("screen.biggerreactors.reactor_redstone_port.output_fuel_amount.tooltip", "Fuel Amount (Output)\n§8[While Above/Below] Trigger when the fuel level is above or below the set amount.");
        add("screen.biggerreactors.reactor_redstone_port.output_waste_amount", "Waste Amount (Output)");
        add("screen.biggerreactors.reactor_redstone_port.output_waste_amount.tooltip", "Waste Amount (Output)\n§8[While Above/Below] Trigger when the waste level is above or below the set amount.");
        add("screen.biggerreactors.reactor_redstone_port.output_output_stored", "Output Stored (Output)");
        add("screen.biggerreactors.reactor_redstone_port.output_output_stored.tooltip", "Output Stored (Output)\n§8[While Above/Below] Trigger when the output stored is above or below the set amount.");
        add("screen.biggerreactors.reactor_redstone_port.apply_changes.tooltip", "Apply Changes\n§7Your changes will not take affect until applied.");
        add("screen.biggerreactors.reactor_redstone_port.revert_changes.tooltip", "§4[UNIMPLEMENTED]\nRevert Changes\n§7Reset current changes to those last applied.");
        add("screen.biggerreactors.reactor_redstone_port.trigger_type_toggle.ps.tooltip", "Toggle Trigger\n§7Toggle the trigger type.\n§8[On Pulse] Toggle state when redstone is pulsed. \n§8[On Signal] Active only when provided with a redstone signal.");
        add("screen.biggerreactors.reactor_redstone_port.trigger_type_toggle.ab.tooltip", "Toggle Trigger\n§7Toggle the trigger type.\n§8[While Above/Below] Output a signal when above or below...");
        add("screen.biggerreactors.reactor_redstone_port.trigger_type_toggle.ps.on_pulse", "Trigger: On Pulse");
        add("screen.biggerreactors.reactor_redstone_port.trigger_type_toggle.ps.on_signal", "Trigger: On Signal");
        add("screen.biggerreactors.reactor_redstone_port.trigger_type_toggle.ab.while_above", "Trigger: While Above");
        add("screen.biggerreactors.reactor_redstone_port.trigger_type_toggle.ab.while_below", "Trigger: While Below");
        add("screen.biggerreactors.reactor_redstone_port.trigger_mode_toggle.mode_a", "Mode A: Insert");
        add("screen.biggerreactors.reactor_redstone_port.trigger_mode_toggle.mode_b", "Mode B: Retract");
        add("screen.biggerreactors.reactor_redstone_port.trigger_mode_toggle.mode_c", "Mode C: Set");
        add("screen.biggerreactors.reactor_redstone_port.text_buffer_a.trigger_at", "Trigger at:");
        add("screen.biggerreactors.reactor_redstone_port.text_buffer_a.mode_a", "Insert by:");
        add("screen.biggerreactors.reactor_redstone_port.text_buffer_a.mode_b", "Retract by:");
        add("screen.biggerreactors.reactor_redstone_port.text_buffer_a.mode_c", "Set to:");
        add("screen.biggerreactors.reactor_redstone_port.trigger_mode_toggle.tooltip", "Toggle Mode\n§7Toggle trigger mode.\n§8[Mode A] Insert by...\n§8[Mode B] Retract by...\n§8[Mode C] Set to...");

        add("multiblock.error.biggerreactors.turbine.mixed_blades_and_coil", "Blades and coils cannot exist at the same rotor shaft position");
        add("multiblock.error.biggerreactors.turbine.multiple_groups", "Only one blade pack and one coil pack may exist at a time");
        add("multiblock.error.biggerreactors.turbine.dangling_coil", "All coil blocks must be in the 8 blocks closest to the turbine shaft");
        add("multiblock.error.biggerreactors.turbine.rotor_bearing_count", "Turbines require exactly 2 rotor bearings, one at each end");

        add("block.biggerreactors.turbine_casing", "Turbine Casing");
        add("block.biggerreactors.turbine_glass", "Turbine Glass");
        add("block.biggerreactors.turbine_rotor_shaft", "Turbine Rotor Shaft");
        add("block.biggerreactors.turbine_rotor_blade", "Turbine Rotor Blade");
        add("block.biggerreactors.turbine_rotor_bearing", "Turbine Rotor Bearing");
        add("block.biggerreactors.turbine_fluid_port", "Turbine Fluid Port");
        add("block.biggerreactors.turbine_power_tap", "Turbine Power Tap");
        add("block.biggerreactors.turbine_computer_port", "Turbine Computer Port");

        add("block.biggerreactors.turbine_terminal", "Turbine Terminal");
        add("screen.biggerreactors.turbine_terminal", "Turbine");
        add("screen.biggerreactors.turbine_terminal.tachometer.tooltip", "Tachometer\n§7How fast the turbine is spinning.\n§7Rotors perform best at 900 or 1800 RPM.");
        add("screen.biggerreactors.turbine_terminal.energy_generation_rate.tooltip", "RF Generation Rate\n§7How much RF the turbine is generating.");
        add("screen.biggerreactors.turbine_terminal.flow_rate_governor.tooltip", "Flow Rate Governor\n§7How fast the turbine will process fluids.");
        add("screen.biggerreactors.turbine_terminal.rotor_efficiency.tooltip", "Rotor Efficiency\n§7Efficiency drops if flow rate is over rotor capacity.");
        add("screen.biggerreactors.turbine_terminal.internal_battery.tooltip", "Internal Battery\n§7How much RF is stored internally.");
        add("screen.biggerreactors.turbine_terminal.intake_tank.tooltip", "Intake Tank\n§7How much hot intake is stored internally.");
        add("screen.biggerreactors.turbine_terminal.exhaust_tank.tooltip", "Exhaust Tank\n§7How much cold exhaust is stored internally.");
        add("screen.biggerreactors.turbine_terminal.activity_toggle.online", "Status: §2Online");
        add("screen.biggerreactors.turbine_terminal.activity_toggle.offline", "Status: §4Offline");
        add("screen.biggerreactors.turbine_terminal.activity_toggle.tooltip", "Toggle Turbine Status\n§7Turn the turbine on or off.");
        add("screen.biggerreactors.turbine_terminal.coil_engage_toggle.engaged", "Coils: §2Engaged");
        add("screen.biggerreactors.turbine_terminal.coil_engage_toggle.disengaged", "Coils: §4Disengaged");
        add("screen.biggerreactors.turbine_terminal.coil_engage_toggle.tooltip", "Toggle Coil Engagement\n§7When engaged, turbine rotor speed will be converted into electricity.");
        add("screen.biggerreactors.turbine_terminal.vent_state_toggle.overflow", "Vent: §6Overflow Only");
        add("screen.biggerreactors.turbine_terminal.vent_state_toggle.all", "Vent: §2All Exhaust");
        add("screen.biggerreactors.turbine_terminal.vent_state_toggle.closed", "Vent: §4No Exhaust");
        add("screen.biggerreactors.turbine_terminal.vent_state_toggle.tooltip", "Toggle Exhaust Venting\n§7Set how or whether exhaust will be vented.");
        add("screen.biggerreactors.turbine_terminal.flow_rate_increase.tooltip", "Increase Flow Rate\n§7Higher flow rates increase rotor speed.\n§8[Shift]: Increase by %s mB.\n§8[Ctrl]: Increase by %s mB.\n§8[Ctrl + Shift]: Increase by %s mB.");
        add("screen.biggerreactors.turbine_terminal.flow_rate_decrease.tooltip", "Decrease Flow Rate\n§7Lower flow rates decrease rotor speed.\n§8[Shift]: Decrease by %s mB.\n§8[Ctrl]: Decrease by %s mB.\n§8[Ctrl + Shift]: Decrease by %s mB.");

        add("screen.biggerreactors.turbine_fluid_port", "Turbine Fluid Port");
        add("screen.biggerreactors.turbine_fluid_port.direction_toggle.input", "Direction: §cInput");
        add("screen.biggerreactors.turbine_fluid_port.direction_toggle.output", "Direction: §9Output");
        add("screen.biggerreactors.turbine_fluid_port.direction_toggle.tooltip", "Toggle Direction\n§7Change which direction this port flows.");

        add("block.biggerreactors.heat_exchanger_casing", "Heat Exchanger Casing");
        add("block.biggerreactors.heat_exchanger_glass", "Heat Exchanger Glass");
        add("block.biggerreactors.heat_exchanger_condenser_channel", "Heat Exchanger Condenser Channel");
        add("block.biggerreactors.heat_exchanger_evaporator_channel", "Heat Exchanger Evaporator Channel");

        add("block.biggerreactors.heat_exchanger_terminal", "Heat Exchanger Terminal");
        add("screen.biggerreactors.heat_exchanger_terminal", "Exchanger");
        add("screen.biggerreactors.heat_exchanger_terminal.temperature.condenser.tooltip", "Condenser Channel Temperature\n§7How hot the fluid in the condenser\n§7channels is.");
        add("screen.biggerreactors.heat_exchanger_terminal.temperature.evaporator.tooltip", "Evaporator Channel Temperature\n§7How hot the fluid in the evaporator\n§7channels is.");
        add("screen.biggerreactors.heat_exchanger_terminal.flow_rate.condenser.tooltip", "Condenser Flow Rate\n§7How much fluid is flowing through\n§7the condenser channels.");
        add("screen.biggerreactors.heat_exchanger_terminal.flow_rate.evaporator.tooltip", "Evaporator Flow Rate\n§7How much fluid is flowing through\n§7the evaporator channels.");
        add("screen.biggerreactors.heat_exchanger_terminal.intake_gauge.condenser.tooltip", "Condenser Intake Tank\n§7How much hot fluid is in\n§7the condenser channels.");
        add("screen.biggerreactors.heat_exchanger_terminal.exhaust_gauge.condenser.tooltip", "Condenser Exhaust Tank\n§7How much cold fluid is in\n§7the condenser channels.");
        add("screen.biggerreactors.heat_exchanger_terminal.heat_gauge.tooltip", "Heat Exchanger Temperature\n§7How hot the exchanger is.");
        add("screen.biggerreactors.heat_exchanger_terminal.intake_gauge.evaporator.tooltip", "Evaporator Intake Tank\n§7How much cold fluid is in\n§7the evaporator channels.");
        add("screen.biggerreactors.heat_exchanger_terminal.exhaust_gauge.evaporator.tooltip", "Evaporator Exhaust Tank\n§7How much hot fluid is in\n§7the evaporator channels.");

        add("multiblock.error.biggerreactors.heat_exchanger.dangling_internal_part", "There appears to be another heat exchanger inside this one, is something wrong? (%d, %d, %d).");
        add("multiblock.error.biggerreactors.heat_exchanger.dangling_channel", "All channels must be connected to a fluid port. (%d, %d, %d)");
        add("multiblock.error.biggerreactors.heat_exchanger.duplicate_port_types", "Both the evaporator and condenser must connect to two fluid ports.");
        add("multiblock.error.biggerreactors.heat_exchanger.fluid_port_unconnected", "All fluid ports must have a connected channel.");
        add("multiblock.error.biggerreactors.heat_exchanger.invalid_port_count", "Exactly 4 fluid ports are required.");
        add("multiblock.error.biggerreactors.heat_exchanger.missing_channel_type", "At least one evaporator and one condenser channel are required.");

        add("block.biggerreactors.heat_exchanger_computer_port", "Heat Exchanger Computer Port");
        add("block.biggerreactors.heat_exchanger_fluid_port", "Heat Exchanger Fluid Port");
        add("screen.biggerreactors.heat_exchanger_fluid_port", "Heat Exchanger Fluid Port");
        add("screen.biggerreactors.heat_exchanger_fluid_port.channel_type.condenser", "Channel Connection: §cCondenser");
        add("screen.biggerreactors.heat_exchanger_fluid_port.channel_type.evaporator", "Channel Connection: §9Evaporator");
        add("screen.biggerreactors.heat_exchanger_fluid_port.direction_toggle.input", "Direction: §cInput");
        add("screen.biggerreactors.heat_exchanger_fluid_port.direction_toggle.output", "Direction: §9Output");
        add("screen.biggerreactors.heat_exchanger_fluid_port.direction_toggle.tooltip", "Toggle Direction\n§7Change which direction this port flows.");
        add("screen.biggerreactors.heat_exchanger_fluid_port.manual_dump", "Manual Channel Fluids Dump");
        add("screen.biggerreactors.heat_exchanger_fluid_port.manual_dump.tooltip", "Dump Channel Fluids\n§7Manually dump all fluids in the connected channel.");

        add("jei.biggerreactors.classic.cyanite_reprocessor", "Cyanite Reprocessing");
        add("jei.biggerreactors.classic.cyanite_reprocessor_time", "Time: %s s");
        add("jei.biggerreactors.classic.cyanite_reprocessor_energy", "Energy: %s RF");
        add("jei.biggerreactors.classic.cyanite_reprocessor_water", "Water: %s mB");

        add("jei.biggerreactors.classic.turbine_coil_block", "Turbine Coil Blocks");
        add("jei.biggerreactors.classic.turbine_coil_bonus", "Bonus: %s");
        add("jei.biggerreactors.classic.turbine_coil_efficiency", "Efficiency: %s");
        add("jei.biggerreactors.classic.turbine_coil_extraction", "Extraction Rate: %s");

        add("jei.biggerreactors.classic.reactor_moderator_block", "Reactor Moderator Blocks");
        add("jei.biggerreactors.classic.reactor_moderator_fluid", "Reactor Moderator Fluids");
        add("jei.biggerreactors.classic.reactor_moderator_moderation", "Moderation: %s");
        add("jei.biggerreactors.classic.reactor_moderator_absorption", "Absorption: %s");
        add("jei.biggerreactors.classic.reactor_moderator_conductivity", "Heat Conductivity: %s");
        add("jei.biggerreactors.classic.reactor_moderator_efficiency", "Heat Efficiency: %s");
    }

    private void spanish() {
        add("item_group.biggerreactors", "Bigger Reactors");

        add("book.biggerreactors.guide", "Bigger Reactors");
        add("book.biggerreactors.guide.subtitle", "Big Reactors, pero todavía más grande");
        add("book.biggerreactors.guide.landing", "Bienvenido a $(thing)Bigger Reactors$(). Esta guía cubre cada bloque, ítem y multibloque que agrega el mod, desde tu primer trozo de uranio hasta un reactor automatizado alimentando un banco de turbinas.$(br2)Elegí una categoría para empezar.");

        add("item.biggerreactors.wrench", "Llave de Reactor");

        add("fluid.biggerreactors.liquid_uranium", "Uranio Líquido");
        add("item.biggerreactors.liquid_uranium_bucket", "Cubo de Uranio Líquido");
        add("fluid.biggerreactors.steam", "Vapor");
        add("item.biggerreactors.steam_bucket", "Cubo de Vapor");
        add("fluid.biggerreactors.liquid_obsidian", "Obsidiana Líquida");
        add("item.biggerreactors.liquid_obsidian_bucket", "Cubo de Obsidiana Líquida");

        add("fluid.biggerreactors.superheated_sodium", "Sodio Líquido Sobrecalentado");
        add("item.biggerreactors.superheated_sodium_bucket", "Cubo de Sodio Líquido Sobrecalentado");

        add("block.biggerreactors.uranium_ore", "Mena de Uranio");
        add("block.biggerreactors.deepslate_uranium_ore", "Mena de Uranio de Pizarra Profunda");
        add("block.biggerreactors.uranium_block", "Bloque de Uranio");
        add("block.biggerreactors.raw_uranium_block", "Bloque de Uranio en Bruto");
        add("item.biggerreactors.uranium_dust", "Polvo de Uranio");
        add("item.biggerreactors.uranium_ingot", "Lingote de Uranio");
        add("item.biggerreactors.uranium_chunk", "Trozo de Uranio");

        add("block.biggerreactors.blutonium_block", "Bloque de Blutonio");
        add("item.biggerreactors.blutonium_dust", "Polvo de Blutonio");
        add("item.biggerreactors.blutonium_ingot", "Lingote de Blutonio");

        add("block.biggerreactors.cyanite_block", "Bloque de Cianita");
        add("item.biggerreactors.cyanite_dust", "Polvo de Cianita");
        add("item.biggerreactors.cyanite_ingot", "Lingote de Cianita");

        add("block.biggerreactors.graphite_block", "Bloque de Grafito");
        add("item.biggerreactors.graphite_dust", "Polvo de Grafito");
        add("item.biggerreactors.graphite_ingot", "Lingote de Grafito");

        add("block.biggerreactors.ludicrite_block", "Bloque de Ludicrita");
        add("item.biggerreactors.ludicrite_dust", "Polvo de Ludicrita");
        add("item.biggerreactors.ludicrite_ingot", "Lingote de Ludicrita");

        add("tooltip.biggerreactors.is_a_moderator", "§eEsto se puede usar como moderador en un Bigger Reactor");
        add("tooltip.biggerreactors.is_a_coil", "§eEste bloque se puede usar como bobina en una Bigger Turbine");

        add("command.biggerreactors.build.success", "Se construyó un %s de %sx%sx%s en %s, %s, %s");
        add("command.biggerreactors.undo.success", "Se deshicieron %s construcciones, se restauraron %s bloques");
        add("command.biggerreactors.undo.empty", "No hay nada que deshacer");

        add("multiblock.error.biggerreactors.no_terminal", "Los reactores necesitan al menos una terminal.");
        add("multiblock.error.biggerreactors.no_rods", "Los reactores necesitan al menos un elemento de combustible.");
        add("multiblock.error.biggerreactors.control_rod_not_on_top", "Las barras de control solo pueden ir en la cara superior del reactor (%d, %d, %d).");
        add("multiblock.error.biggerreactors.fuel_rod_gap", "Los elementos de combustible deben llegar desde la base hasta el techo del reactor (%d, %d, %d).");
        add("multiblock.error.biggerreactors.no_control_rod_for_fuel_rod", "Todos los elementos de combustible deben tener una barra de control encima (%d, %d).");
        add("multiblock.error.biggerreactors.dangling_internal_part", "Parece haber otro reactor dentro de este, ¿algo anda mal? (%d, %d, %d).");
        add("multiblock.error.biggerreactors.coolant_and_power_ports", "Un reactor puede tener puertos de refrigerante (tipo activo) o tomas de energía (tipo pasivo), pero no ambos.");
        add("multiblock.error.biggerreactors.no_manifold_neighbor", "Los colectores de refrigerante deben tocar otro colector o una carcasa");

        add("screen.biggerreactors.disabled", "Desactivado");

        add("block.biggerreactors.cyanite_reprocessor", "Reprocesador de Cianita");
        add("screen.biggerreactors.cyanite_reprocessor", "Reprocesador de Cianita");
        add("screen.biggerreactors.cyanite_reprocessor.internal_battery.tooltip", "Batería Interna");
        add("screen.biggerreactors.cyanite_reprocessor.water_tank.tooltip", "Tanque de Agua");

        add("block.biggerreactors.reactor_casing", "Carcasa de Reactor");
        add("block.biggerreactors.reactor_glass", "Vidrio de Reactor");
        add("block.biggerreactors.reactor_fuel_rod", "Barra de Combustible");
        add("block.biggerreactors.reactor_power_tap", "Toma de Energía de Reactor");
        add("block.biggerreactors.reactor_access_port", "Puerto de Acceso de Reactor");
        add("block.biggerreactors.reactor_coolant_port", "Puerto de Refrigerante de Reactor");
        add("block.biggerreactors.reactor_computer_port", "Puerto de Computadora de Reactor");
        add("block.biggerreactors.reactor_redstone_port", "Puerto de Redstone de Reactor");
        add("block.biggerreactors.reactor_manifold", "Colector de Refrigerante de Reactor");

        add("block.biggerreactors.reactor_terminal", "Terminal de Reactor");
        add("screen.biggerreactors.reactor_terminal", "Reactor");
        add("screen.biggerreactors.reactor_terminal.temperature.tooltip", "Temperatura\n§7Qué tan caliente está el reactor.");
        add("screen.biggerreactors.reactor_terminal.energy_generation_rate.tooltip", "Generación de RF\n§7Cuánto RF está generando el reactor.");
        add("screen.biggerreactors.reactor_terminal.exhaust_generation_rate.tooltip", "Generación de Escape\n§7Cuánto escape está generando el reactor.");
        add("screen.biggerreactors.reactor_terminal.fuel_usage_rate.tooltip", "Consumo de Combustible\n§7Cuánto combustible está consumiendo el reactor.");
        add("screen.biggerreactors.reactor_terminal.reactivity_rate.tooltip", "Reactividad\n§7Qué tan reactivo está el reactor.");
        add("screen.biggerreactors.reactor_terminal.fuel_mix.tooltip", "Mezcla de Combustible\n§7Proporción de combustible y residuo dentro del reactor.");
        add("screen.biggerreactors.reactor_terminal.case_heat.tooltip", "Calor de la Carcasa\n§7Qué tan caliente está el reactor.");
        add("screen.biggerreactors.reactor_terminal.fuel_heat.tooltip", "Calor del Combustible\n§7Qué tan caliente está el combustible.");
        add("screen.biggerreactors.reactor_terminal.fuel_mix_gauge.tooltip", "%%s/%%s Mezclado\n§a%%s de Combustible\n§b%%s de Residuo");
        add("screen.biggerreactors.reactor_terminal.internal_battery.tooltip", "Batería Interna\n§7Cuánto RF hay almacenado internamente.");
        add("screen.biggerreactors.reactor_terminal.coolant_intake_tank.tooltip", "Tanque de Entrada de Refrigerante\n§7Cuánto refrigerante hay almacenado internamente.");
        add("screen.biggerreactors.reactor_terminal.exhaust_tank.tooltip", "Tanque de Escape\n§7Cuánto escape hay almacenado internamente.");
        add("screen.biggerreactors.reactor_terminal.activity_toggle.online", "Estado: §2Encendido");
        add("screen.biggerreactors.reactor_terminal.activity_toggle.offline", "Estado: §4Apagado");
        add("screen.biggerreactors.reactor_terminal.activity_toggle.tooltip", "Cambiar Estado del Reactor\n§7Encender o apagar el reactor.");
        add("screen.biggerreactors.reactor_terminal.auto_eject_toggle.enabled", "Exp. de Res.: §3Activada");
        add("screen.biggerreactors.reactor_terminal.auto_eject_toggle.disabled", "Exp. de Res.: §4Desactivada");
        add("screen.biggerreactors.reactor_terminal.auto_eject_toggle.tooltip", "Cambiar Expulsión de Residuo\n§7Define si el residuo se expulsa automáticamente.");

        add("block.biggerreactors.reactor_control_rod", "Barra de Control de Reactor");
        add("screen.biggerreactors.reactor_control_rod", "Barra de Control de Reactor");
        add("screen.biggerreactors.reactor_control_rod.name", "Nombre:");
        add("screen.biggerreactors.reactor_control_rod.apply.tooltip", "Aplicar");
        add("screen.biggerreactors.reactor_control_rod.retract_rod.tooltip", "Retraer Barra\n§7Menos inserción aumenta la velocidad de reacción.\n§8[Shift]: Retraer 10%.\n§8[Ctrl]: Retraer 50%.\n§8[Ctrl + Shift]: Retracción máxima.\n§8[Alt]: Aplicar a todas las barras.");
        add("screen.biggerreactors.reactor_control_rod.insert_rod.tooltip", "Insertar Barra\n§7Más inserción reduce la velocidad de reacción.\n§8[Shift]: Insertar 10%.\n§8[Ctrl]: Insertar 50%.\n§8[Ctrl + Shift]: Inserción máxima.\n§8[Alt]: Aplicar a todas las barras.");

        add("screen.biggerreactors.reactor_coolant_port", "Puerto de Refrigerante de Reactor");
        add("screen.biggerreactors.reactor_coolant_port.direction_toggle.input", "Dirección: §3Entrada");
        add("screen.biggerreactors.reactor_coolant_port.direction_toggle.output", "Dirección: §cSalida");
        add("screen.biggerreactors.reactor_coolant_port.direction_toggle.tooltip", "Cambiar Dirección\n§7Cambia hacia dónde fluye este puerto.");
        add("screen.biggerreactors.reactor_coolant_port.manual_dump", "Vaciado Manual del Tanque");
        add("screen.biggerreactors.reactor_coolant_port.manual_dump.tooltip", "Vaciar Fluidos del Tanque\n§7Vacía manualmente todos los fluidos del reactor.");

        add("screen.biggerreactors.reactor_access_port", "Puerto de Acceso de Reactor");
        add("screen.biggerreactors.reactor_access_port.direction_toggle.input", "Dirección: §6Entrada");
        add("screen.biggerreactors.reactor_access_port.direction_toggle.output", "Dirección: §9Salida");
        add("screen.biggerreactors.reactor_access_port.direction_toggle.tooltip", "Cambiar Dirección\n§7Cambia hacia dónde fluye este puerto.");
        add("screen.biggerreactors.reactor_access_port.fuel_mode_toggle.fuel", "Sacando: §6Combustible");
        add("screen.biggerreactors.reactor_access_port.fuel_mode_toggle.waste", "Sacando: §3Residuo");
        add("screen.biggerreactors.reactor_access_port.fuel_mode_toggle.nope", "Sacando: §7---");
        add("screen.biggerreactors.reactor_access_port.fuel_mode_toggle.tooltip", "Cambiar Salida\n§7Cambia qué está sacando este puerto.");
        add("screen.biggerreactors.reactor_access_port.manual_eject", "Expulsión Manual de Residuo");
        add("screen.biggerreactors.reactor_access_port.manual_eject.tooltip", "Expulsar Todo el Residuo\n§7Expulsa manualmente todo el residuo del reactor.");

        add("screen.biggerreactors.reactor_redstone_port", "Puerto de Redstone de Reactor");
        add("screen.biggerreactors.reactor_redstone_port.choose_setting", "Elegí una opción:");
        add("screen.biggerreactors.reactor_redstone_port.apply.tooltip", "Aplicar");
        add("screen.biggerreactors.reactor_redstone_port.input_reactor_activity", "Cambiar Estado del Reactor (Entrada)");
        add("screen.biggerreactors.reactor_redstone_port.input_reactor_activity.tooltip", "Cambiar Estado del Reactor (Entrada)\n§8[Por Pulso] Alterna entre encendido y apagado.\n§8[Por Señal] Mientras haya redstone, el reactor está activo.");
        add("screen.biggerreactors.reactor_redstone_port.input_control_rod_insertion", "Cambiar Inserción de Barras (Entrada)");
        add("screen.biggerreactors.reactor_redstone_port.input_control_rod_insertion.while_on", "Con señal aplicada:");
        add("screen.biggerreactors.reactor_redstone_port.input_control_rod_insertion.while_off", "Si no:");
        add("screen.biggerreactors.reactor_redstone_port.input_control_rod_insertion.tooltip", "Cambiar Inserción de Barras (Entrada)\n§8[Por Pulso] Mueve la barra según el modo actual.\n§8[Por Señal] Fija el nivel de inserción según haya o no redstone.");
        add("screen.biggerreactors.reactor_redstone_port.input_eject_waste", "Expulsar Residuo (Entrada)");
        add("screen.biggerreactors.reactor_redstone_port.input_eject_waste.tooltip", "Expulsar Residuo (Entrada)\n§8[Por Pulso] Expulsa todo el residuo del reactor.\n§8[Por Señal] No aplica.");
        add("screen.biggerreactors.reactor_redstone_port.output_fuel_temp", "Temperatura del Combustible (Salida)");
        add("screen.biggerreactors.reactor_redstone_port.output_fuel_temp.tooltip", "Temperatura del Combustible (Salida)\n§8[Por Encima/Debajo] Se activa cuando la temperatura del combustible pasa el valor fijado.");
        add("screen.biggerreactors.reactor_redstone_port.output_casing_temp", "Temperatura de la Carcasa (Salida)");
        add("screen.biggerreactors.reactor_redstone_port.output_casing_temp.tooltip", "Temperatura de la Carcasa (Salida)\n§8[Por Encima/Debajo] Se activa cuando la temperatura pasa el valor fijado.");
        add("screen.biggerreactors.reactor_redstone_port.output_fuel_enrichment", "Enriquecimiento del Combustible (Salida)");
        add("screen.biggerreactors.reactor_redstone_port.output_fuel_enrichment.tooltip", "Enriquecimiento del Combustible (Salida)\n§8[Por Encima/Debajo] Se activa cuando el enriquecimiento pasa el porcentaje fijado.");
        add("screen.biggerreactors.reactor_redstone_port.output_fuel_amount", "Cantidad de Combustible (Salida)");
        add("screen.biggerreactors.reactor_redstone_port.output_fuel_amount.tooltip", "Cantidad de Combustible (Salida)\n§8[Por Encima/Debajo] Se activa cuando el combustible pasa la cantidad fijada.");
        add("screen.biggerreactors.reactor_redstone_port.output_waste_amount", "Cantidad de Residuo (Salida)");
        add("screen.biggerreactors.reactor_redstone_port.output_waste_amount.tooltip", "Cantidad de Residuo (Salida)\n§8[Por Encima/Debajo] Se activa cuando el residuo pasa la cantidad fijada.");
        add("screen.biggerreactors.reactor_redstone_port.output_output_stored", "Energía Almacenada (Salida)");
        add("screen.biggerreactors.reactor_redstone_port.output_output_stored.tooltip", "Energía Almacenada (Salida)\n§8[Por Encima/Debajo] Se activa cuando la energía almacenada pasa la cantidad fijada.");
        add("screen.biggerreactors.reactor_redstone_port.apply_changes.tooltip", "Aplicar Cambios\n§7Los cambios no tienen efecto hasta que los apliques.");
        add("screen.biggerreactors.reactor_redstone_port.revert_changes.tooltip", "§4[SIN IMPLEMENTAR]\nRevertir Cambios\n§7Vuelve a los últimos cambios aplicados.");
        add("screen.biggerreactors.reactor_redstone_port.trigger_type_toggle.ps.tooltip", "Cambiar Disparador\n§7Cambia el tipo de disparador.\n§8[Por Pulso] Alterna el estado con un pulso de redstone.\n§8[Por Señal] Activo solo mientras haya señal.");
        add("screen.biggerreactors.reactor_redstone_port.trigger_type_toggle.ab.tooltip", "Cambiar Disparador\n§7Cambia el tipo de disparador.\n§8[Por Encima/Debajo] Emite señal al pasar el valor fijado...");
        add("screen.biggerreactors.reactor_redstone_port.trigger_type_toggle.ps.on_pulse", "Disparador: Por Pulso");
        add("screen.biggerreactors.reactor_redstone_port.trigger_type_toggle.ps.on_signal", "Disparador: Por Señal");
        add("screen.biggerreactors.reactor_redstone_port.trigger_type_toggle.ab.while_above", "Disparador: Por Encima");
        add("screen.biggerreactors.reactor_redstone_port.trigger_type_toggle.ab.while_below", "Disparador: Por Debajo");
        add("screen.biggerreactors.reactor_redstone_port.trigger_mode_toggle.mode_a", "Modo A: Insertar");
        add("screen.biggerreactors.reactor_redstone_port.trigger_mode_toggle.mode_b", "Modo B: Retraer");
        add("screen.biggerreactors.reactor_redstone_port.trigger_mode_toggle.mode_c", "Modo C: Fijar");
        add("screen.biggerreactors.reactor_redstone_port.text_buffer_a.trigger_at", "Disparar en:");
        add("screen.biggerreactors.reactor_redstone_port.text_buffer_a.mode_a", "Insertar en:");
        add("screen.biggerreactors.reactor_redstone_port.text_buffer_a.mode_b", "Retraer en:");
        add("screen.biggerreactors.reactor_redstone_port.text_buffer_a.mode_c", "Fijar en:");
        add("screen.biggerreactors.reactor_redstone_port.trigger_mode_toggle.tooltip", "Cambiar Modo\n§7Cambia el modo del disparador.\n§8[Modo A] Insertar en...\n§8[Modo B] Retraer en...\n§8[Modo C] Fijar en...");

        add("multiblock.error.biggerreactors.turbine.mixed_blades_and_coil", "Las aspas y las bobinas no pueden estar en la misma posición del eje");
        add("multiblock.error.biggerreactors.turbine.multiple_groups", "Solo puede haber un grupo de aspas y un grupo de bobinas");
        add("multiblock.error.biggerreactors.turbine.dangling_coil", "Todas las bobinas deben estar en los 8 bloques más cercanos al eje de la turbina");
        add("multiblock.error.biggerreactors.turbine.rotor_bearing_count", "Las turbinas necesitan exactamente 2 rodamientos, uno en cada extremo");

        add("block.biggerreactors.turbine_casing", "Carcasa de Turbina");
        add("block.biggerreactors.turbine_glass", "Vidrio de Turbina");
        add("block.biggerreactors.turbine_rotor_shaft", "Eje del Rotor de Turbina");
        add("block.biggerreactors.turbine_rotor_blade", "Aspa del Rotor de Turbina");
        add("block.biggerreactors.turbine_rotor_bearing", "Rodamiento del Rotor de Turbina");
        add("block.biggerreactors.turbine_fluid_port", "Puerto de Fluidos de Turbina");
        add("block.biggerreactors.turbine_power_tap", "Toma de Energía de Turbina");
        add("block.biggerreactors.turbine_computer_port", "Puerto de Computadora de Turbina");

        add("block.biggerreactors.turbine_terminal", "Terminal de Turbina");
        add("screen.biggerreactors.turbine_terminal", "Turbina");
        add("screen.biggerreactors.turbine_terminal.tachometer.tooltip", "Tacómetro\n§7A qué velocidad gira la turbina.\n§7Los rotores rinden mejor a 900 o 1800 RPM.");
        add("screen.biggerreactors.turbine_terminal.energy_generation_rate.tooltip", "Generación de RF\n§7Cuánto RF está generando la turbina.");
        add("screen.biggerreactors.turbine_terminal.flow_rate_governor.tooltip", "Regulador de Caudal\n§7Qué tan rápido procesa fluidos la turbina.");
        add("screen.biggerreactors.turbine_terminal.rotor_efficiency.tooltip", "Eficiencia del Rotor\n§7La eficiencia cae si el caudal supera la capacidad del rotor.");
        add("screen.biggerreactors.turbine_terminal.internal_battery.tooltip", "Batería Interna\n§7Cuánto RF hay almacenado internamente.");
        add("screen.biggerreactors.turbine_terminal.intake_tank.tooltip", "Tanque de Entrada\n§7Cuánto fluido caliente hay almacenado internamente.");
        add("screen.biggerreactors.turbine_terminal.exhaust_tank.tooltip", "Tanque de Escape\n§7Cuánto fluido frío hay almacenado internamente.");
        add("screen.biggerreactors.turbine_terminal.activity_toggle.online", "Estado: §2Encendida");
        add("screen.biggerreactors.turbine_terminal.activity_toggle.offline", "Estado: §4Apagada");
        add("screen.biggerreactors.turbine_terminal.activity_toggle.tooltip", "Cambiar Estado de la Turbina\n§7Encender o apagar la turbina.");
        add("screen.biggerreactors.turbine_terminal.coil_engage_toggle.engaged", "Bobinas: §2Acopladas");
        add("screen.biggerreactors.turbine_terminal.coil_engage_toggle.disengaged", "Bobinas: §4Desacopladas");
        add("screen.biggerreactors.turbine_terminal.coil_engage_toggle.tooltip", "Cambiar Acople de Bobinas\n§7Acopladas, la velocidad del rotor se convierte en electricidad.");
        add("screen.biggerreactors.turbine_terminal.vent_state_toggle.overflow", "Purga: §6Solo Excedente");
        add("screen.biggerreactors.turbine_terminal.vent_state_toggle.all", "Purga: §2Todo el Escape");
        add("screen.biggerreactors.turbine_terminal.vent_state_toggle.closed", "Purga: §4Sin Escape");
        add("screen.biggerreactors.turbine_terminal.vent_state_toggle.tooltip", "Cambiar Purga de Escape\n§7Define cómo o si se purga el escape.");
        add("screen.biggerreactors.turbine_terminal.flow_rate_increase.tooltip", "Aumentar Caudal\n§7Más caudal aumenta la velocidad del rotor.\n§8[Shift]: Aumentar %s mB.\n§8[Ctrl]: Aumentar %s mB.\n§8[Ctrl + Shift]: Aumentar %s mB.");
        add("screen.biggerreactors.turbine_terminal.flow_rate_decrease.tooltip", "Reducir Caudal\n§7Menos caudal reduce la velocidad del rotor.\n§8[Shift]: Reducir %s mB.\n§8[Ctrl]: Reducir %s mB.\n§8[Ctrl + Shift]: Reducir %s mB.");

        add("screen.biggerreactors.turbine_fluid_port", "Puerto de Fluidos de Turbina");
        add("screen.biggerreactors.turbine_fluid_port.direction_toggle.input", "Dirección: §cEntrada");
        add("screen.biggerreactors.turbine_fluid_port.direction_toggle.output", "Dirección: §9Salida");
        add("screen.biggerreactors.turbine_fluid_port.direction_toggle.tooltip", "Cambiar Dirección\n§7Cambia hacia dónde fluye este puerto.");

        add("block.biggerreactors.heat_exchanger_casing", "Carcasa de Intercambiador de Calor");
        add("block.biggerreactors.heat_exchanger_glass", "Vidrio de Intercambiador de Calor");
        add("block.biggerreactors.heat_exchanger_condenser_channel", "Canal Condensador de Intercambiador");
        add("block.biggerreactors.heat_exchanger_evaporator_channel", "Canal Evaporador de Intercambiador");

        add("block.biggerreactors.heat_exchanger_terminal", "Terminal de Intercambiador de Calor");
        add("screen.biggerreactors.heat_exchanger_terminal", "Intercambiador");
        add("screen.biggerreactors.heat_exchanger_terminal.temperature.condenser.tooltip", "Temperatura del Canal Condensador\n§7Qué tan caliente está el fluido en los\n§7canales condensadores.");
        add("screen.biggerreactors.heat_exchanger_terminal.temperature.evaporator.tooltip", "Temperatura del Canal Evaporador\n§7Qué tan caliente está el fluido en los\n§7canales evaporadores.");
        add("screen.biggerreactors.heat_exchanger_terminal.flow_rate.condenser.tooltip", "Caudal del Condensador\n§7Cuánto fluido está pasando por los\n§7canales condensadores.");
        add("screen.biggerreactors.heat_exchanger_terminal.flow_rate.evaporator.tooltip", "Caudal del Evaporador\n§7Cuánto fluido está pasando por los\n§7canales evaporadores.");
        add("screen.biggerreactors.heat_exchanger_terminal.intake_gauge.condenser.tooltip", "Tanque de Entrada del Condensador\n§7Cuánto fluido caliente hay en los\n§7canales condensadores.");
        add("screen.biggerreactors.heat_exchanger_terminal.exhaust_gauge.condenser.tooltip", "Tanque de Salida del Condensador\n§7Cuánto fluido frío hay en los\n§7canales condensadores.");
        add("screen.biggerreactors.heat_exchanger_terminal.heat_gauge.tooltip", "Temperatura del Intercambiador\n§7Qué tan caliente está el intercambiador.");
        add("screen.biggerreactors.heat_exchanger_terminal.intake_gauge.evaporator.tooltip", "Tanque de Entrada del Evaporador\n§7Cuánto fluido frío hay en los\n§7canales evaporadores.");
        add("screen.biggerreactors.heat_exchanger_terminal.exhaust_gauge.evaporator.tooltip", "Tanque de Salida del Evaporador\n§7Cuánto fluido caliente hay en los\n§7canales evaporadores.");

        add("multiblock.error.biggerreactors.heat_exchanger.dangling_internal_part", "Parece haber otro intercambiador dentro de este, ¿algo anda mal? (%d, %d, %d).");
        add("multiblock.error.biggerreactors.heat_exchanger.dangling_channel", "Todos los canales deben estar conectados a un puerto de fluidos. (%d, %d, %d)");
        add("multiblock.error.biggerreactors.heat_exchanger.duplicate_port_types", "Tanto el evaporador como el condensador deben conectarse a dos puertos de fluidos.");
        add("multiblock.error.biggerreactors.heat_exchanger.fluid_port_unconnected", "Todos los puertos de fluidos deben tener un canal conectado.");
        add("multiblock.error.biggerreactors.heat_exchanger.invalid_port_count", "Se necesitan exactamente 4 puertos de fluidos.");
        add("multiblock.error.biggerreactors.heat_exchanger.missing_channel_type", "Se necesita al menos un canal evaporador y un canal condensador.");

        add("block.biggerreactors.heat_exchanger_computer_port", "Puerto de Computadora de Intercambiador");
        add("block.biggerreactors.heat_exchanger_fluid_port", "Puerto de Fluidos de Intercambiador");
        add("screen.biggerreactors.heat_exchanger_fluid_port", "Puerto de Fluidos de Intercambiador");
        add("screen.biggerreactors.heat_exchanger_fluid_port.channel_type.condenser", "Canal Conectado: §cCondensador");
        add("screen.biggerreactors.heat_exchanger_fluid_port.channel_type.evaporator", "Canal Conectado: §9Evaporador");
        add("screen.biggerreactors.heat_exchanger_fluid_port.direction_toggle.input", "Dirección: §cEntrada");
        add("screen.biggerreactors.heat_exchanger_fluid_port.direction_toggle.output", "Dirección: §9Salida");
        add("screen.biggerreactors.heat_exchanger_fluid_port.direction_toggle.tooltip", "Cambiar Dirección\n§7Cambia hacia dónde fluye este puerto.");
        add("screen.biggerreactors.heat_exchanger_fluid_port.manual_dump", "Vaciado Manual del Canal");
        add("screen.biggerreactors.heat_exchanger_fluid_port.manual_dump.tooltip", "Vaciar Fluidos del Canal\n§7Vacía manualmente todos los fluidos del canal conectado.");

        add("jei.biggerreactors.classic.cyanite_reprocessor", "Reprocesado de Cianita");
        add("jei.biggerreactors.classic.cyanite_reprocessor_time", "Tiempo: %s s");
        add("jei.biggerreactors.classic.cyanite_reprocessor_energy", "Energía: %s RF");
        add("jei.biggerreactors.classic.cyanite_reprocessor_water", "Agua: %s mB");

        add("jei.biggerreactors.classic.turbine_coil_block", "Bloques de Bobina de Turbina");
        add("jei.biggerreactors.classic.turbine_coil_bonus", "Bonus: %s");
        add("jei.biggerreactors.classic.turbine_coil_efficiency", "Eficiencia: %s");
        add("jei.biggerreactors.classic.turbine_coil_extraction", "Tasa de Extracción: %s");

        add("jei.biggerreactors.classic.reactor_moderator_block", "Bloques Moderadores de Reactor");
        add("jei.biggerreactors.classic.reactor_moderator_fluid", "Fluidos Moderadores de Reactor");
        add("jei.biggerreactors.classic.reactor_moderator_moderation", "Moderación: %s");
        add("jei.biggerreactors.classic.reactor_moderator_absorption", "Absorción: %s");
        add("jei.biggerreactors.classic.reactor_moderator_conductivity", "Conductividad Térmica: %s");
        add("jei.biggerreactors.classic.reactor_moderator_efficiency", "Eficiencia Térmica: %s");
    }

    private void chinese() {
        add("book.biggerreactors.guide", "Bigger Reactors");
        add("book.biggerreactors.guide.subtitle", "大型反应堆，但是更大");
        add("book.biggerreactors.guide.landing", "欢迎使用$(thing)大型反应堆$()。本指南涵盖该模组添加的每一个方块、物品和多方块结构。$(br2)请在下方选择一个分类。");
        add("block.biggerreactors.blutonium_block", "蓝钚块");
        add("block.biggerreactors.cyanite_block", "蓝晶块");
        add("block.biggerreactors.cyanite_reprocessor", "蓝晶再处理器");
        add("block.biggerreactors.graphite_block", "石墨块");
        add("block.biggerreactors.ludicrite_block", "镥块");
        add("block.biggerreactors.reactor_access_port", "反应堆访问端口");
        add("block.biggerreactors.reactor_casing", "反应堆外壳");
        add("block.biggerreactors.reactor_computer_port", "反应堆电脑端口");
        add("block.biggerreactors.reactor_control_rod", "反应堆控制杆");
        add("block.biggerreactors.reactor_coolant_port", "反应堆冷却端口");
        add("block.biggerreactors.reactor_fuel_rod", "反应堆燃料棒");
        add("block.biggerreactors.reactor_glass", "反应堆玻璃");
        add("block.biggerreactors.reactor_power_tap", "反应堆能量接口");
        add("block.biggerreactors.reactor_terminal", "反应堆终端");
        add("block.biggerreactors.turbine_casing", "涡轮机外壳");
        add("block.biggerreactors.turbine_computer_port", "涡轮机电脑端口");
        add("block.biggerreactors.turbine_fluid_port", "涡轮机冷却端口");
        add("block.biggerreactors.turbine_glass", "涡轮机玻璃");
        add("block.biggerreactors.turbine_power_tap", "涡轮机能量接口");
        add("block.biggerreactors.turbine_rotor_bearing", "涡轮机转子轴承");
        add("block.biggerreactors.turbine_rotor_blade", "涡轮机叶片");
        add("block.biggerreactors.turbine_rotor_shaft", "涡轮机转轴");
        add("block.biggerreactors.turbine_terminal", "涡轮机终端");
        add("block.biggerreactors.uranium_block", "黄铀块");
        add("block.biggerreactors.uranium_ore", "黄铀矿石");
        add("fluid.biggerreactors.steam", "辐射蒸汽");
        add("item.biggerreactors.blutonium_dust", "蓝钚粉");
        add("item.biggerreactors.blutonium_ingot", "蓝钚锭");
        add("item.biggerreactors.cyanite_dust", "蓝晶粉");
        add("item.biggerreactors.cyanite_ingot", "蓝晶锭");
        add("item.biggerreactors.graphite_dust", "石墨粉");
        add("item.biggerreactors.graphite_ingot", "石墨锭");
        add("item.biggerreactors.liquid_uranium_bucket", "液态黄铀桶");
        add("item.biggerreactors.ludicrite_dust", "镥粉");
        add("item.biggerreactors.ludicrite_ingot", "镥锭");
        add("item.biggerreactors.uranium_dust", "黄铀粉");
        add("item.biggerreactors.uranium_ingot", "黄铀锭");
        add("item.biggerreactors.wrench", "反应堆扳手");
        add("item_group.biggerreactors", "大型反应堆");
        add("multiblock.error.biggerreactors.control_rod_not_on_top", "控制杆只能放在反应堆上方(%d, %d, %d)");
        add("multiblock.error.biggerreactors.dangling_internal_part", "这里面似乎还有一个反应堆，是不是出了什么问题？(%d, %d, %d)");
        add("multiblock.error.biggerreactors.fuel_rod_gap", "所有燃料元件必须从反应堆上方延伸至其底部(%d, %d, %d)");
        add("multiblock.error.biggerreactors.no_control_rod_for_fuel_rod", "所有的燃料元件必须用控制杆控制(%d, %d)");
        add("multiblock.error.biggerreactors.no_rods", "反应堆至少要有一个燃料元件");
        add("multiblock.error.biggerreactors.no_terminal", "反应堆需要至少一个终端");
        add("screen.biggerreactors.cyanite_reprocessor", "蓝晶再处理器");
        add("screen.biggerreactors.reactor_access_port", "反应堆访问端口");
        add("screen.biggerreactors.reactor_control_rod", "反应堆控制杆");
        add("screen.biggerreactors.reactor_coolant_port", "反应堆冷却端口");
        add("screen.biggerreactors.reactor_terminal", "反应堆");
        add("screen.biggerreactors.turbine_fluid_port", "涡轮机冷却端口");
        add("screen.biggerreactors.turbine_terminal", "涡轮机");
        add("tooltip.biggerreactors.buttons.control_rod.insertion.insert", "§b插入杆\n杆的插入程度越深，反应速度越慢。\n§8[Shift]：插入10%。\n§8[Ctrl]：插入50%。\n§8[Ctrl + Shift]插入100% (完全插入)。\n§8[Alt]：应用于所有杆。");
        add("tooltip.biggerreactors.buttons.control_rod.insertion.retract", "§b缩回杆\n杆的插入程度越浅，反应速度越块。\n§8[Shift]：缩回10%。\n§8[Ctrl]：缩回50%。\n§8[Ctrl + Shift]：缩回100% (完全缩回)。\n§8[Alt]]：应用于所有杆。");
        add("tooltip.biggerreactors.buttons.io", "§b切换端口 I/O。");
        add("tooltip.biggerreactors.buttons.reactor.activity.activate", "§b启动反应堆");
        add("tooltip.biggerreactors.buttons.reactor.activity.deactivate", "§b关闭反应堆\n余热仍然会\n产生能量/消耗冷却液。");
        add("tooltip.biggerreactors.buttons.reactor.waste_eject_auto.disabled", "§b不自动排出废料\n§d废料只能手动排出！");
        add("tooltip.biggerreactors.buttons.reactor.waste_eject_auto.enabled", "§b自动排出废料\n核心里的废料将会尽快被排出。");
        add("tooltip.biggerreactors.buttons.reactor.waste_eject_manual", "§b现在排出废料\n排出核心中的废料。\n1000 mB的废料等于1块锭。\n§8[Shift]：倾泻所有多余的废料。");
        add("tooltip.biggerreactors.buttons.turbine.activity.activate", "§b启动涡轮机\n使进气流体流向转子。\n流体流动会使转子旋转起来。");
        add("tooltip.biggerreactors.buttons.turbine.activity.deactivate", "§b关闭涡轮机\n关闭进气口。\n转子会停止旋转。");
        add("tooltip.biggerreactors.buttons.turbine.coils.activate", "§b啮合线圈\n啮合感应线圈。\n将从转子中提取能量\n并转化为RF。\n提取能量会\n对转子产生阻力，\n使其减速。");
        add("tooltip.biggerreactors.buttons.turbine.coils.deactivate", "§b取消啮合线圈\n取消啮合感应线圈。\n不会从转子中\n提取能量，\n可让转子转得更快。");
        add("tooltip.biggerreactors.buttons.turbine.flow.decrease", "§b降低最大流量\n降低流量会降低转子转速。\n§8[Shift]：降低 10 mB/t。\n§8[Ctrl]：降低 100 mB/t。\n§8[Ctrl + Shift]：降低 1000 mB/t.");
        add("tooltip.biggerreactors.buttons.turbine.flow.increase", "§b增加最大流量\n更高的流量可增加转子转速。\n§8[Shift]：增加 10 mB/t。\n§8[Ctrl]：增加 100 mB/t。\n§8[Ctrl + Shift]：增加 1000 mB/t。");
        add("tooltip.biggerreactors.buttons.turbine.vent_state.all", "§b排出所有废料\n倾泻所有的废料。\n不会填充\n废料液罐。");
        add("tooltip.biggerreactors.buttons.turbine.vent_state.closed", "§b不排出\n保留所有多余废料。\n如果废料储罐已满，\n涡轮机会减慢或停止输入液体。");
        add("tooltip.biggerreactors.buttons.turbine.vent_state.overflow", "§b仅排出多余废料\n倾泻多余的废料。\n如果废料液罐满了，\n多余的液体就会流失。");
        add("tooltip.biggerreactors.status.io.input", "§2接受输入");
        add("tooltip.biggerreactors.status.io.output", "§c提供输出");
        add("tooltip.biggerreactors.status.reactor.activity.offline", "状态：§4离线");
        add("tooltip.biggerreactors.status.reactor.activity.online", "状态：§2在线");
        add("tooltip.biggerreactors.status.turbine.activity.offline", "状态：§4离线");
        add("tooltip.biggerreactors.status.turbine.activity.online", "状态：§2在线");
        add("tooltip.biggerreactors.symbols.control_rod.insertion", "§b杆插口\n更改控制杆的插入程度。\n杆的插入程度越深，反应速度越慢，\n即降低热量、能量、\n辐射输出和\n燃料消耗。");
        add("tooltip.biggerreactors.symbols.io.gui_change", "§b\"这个GUI缺少输入栏！\"\nI/O 端口已被重写！\n请用漏斗或管道\n运输材料。");
        add("tooltip.biggerreactors.symbols.io.input", "§b该端口目前提供输出。");
        add("tooltip.biggerreactors.symbols.reactor.case_heat", "§b堆体热量\n反应堆燃料的热量。\n高热量会提高燃料的使用量。\n核心的热量将传递到外壳上。\n转移率基于\n反应堆的内部设计。");
        add("tooltip.biggerreactors.symbols.reactor.coolant_tank", "§b冷却液罐\n堆体热量会使罐中的冷却液\n过热。");
        add("tooltip.biggerreactors.symbols.reactor.energy_tank", "§b能量缓冲");
        add("tooltip.biggerreactors.symbols.reactor.fuel_consumption", "§b燃料消耗率\n燃料消耗\n并化为废料的速率。");
        add("tooltip.biggerreactors.symbols.reactor.fuel_heat", "§b燃料热量\n反应堆外壳的热量。\n高热量会提高能量输出\n和冷却液的转换。");
        add("tooltip.biggerreactors.symbols.reactor.fuel_mix", "§b燃油混合");
        add("tooltip.biggerreactors.symbols.reactor.fuel_reactivity", "§b燃料反应性\n核心的辐射程度。\n较高的辐射度会\n降低燃料消耗。");
        add("tooltip.biggerreactors.symbols.reactor.hot_tank", "§b热液罐\n过热冷却液\n会被泵入到该罐中，\n且必须通过\n冷却端口排出。");
        add("tooltip.biggerreactors.symbols.reactor.output", "§b反应堆输出");
        add("tooltip.biggerreactors.symbols.reactor.temperature", "§b核心稳固\n反应堆核心的温度。\n高温会导致\n燃料消耗速度增加。");
        add("tooltip.biggerreactors.symbols.turbine.energy_tank", "§b能量缓冲");
        add("tooltip.biggerreactors.symbols.turbine.exhaust_tank", "§b废料液罐");
        add("tooltip.biggerreactors.symbols.turbine.governor", "§b流量管理\n控制最大流量，\n即涡轮机\n可处理的液体量。");
        add("tooltip.biggerreactors.symbols.turbine.intake_tank", "§b进料液罐");
        add("tooltip.biggerreactors.symbols.turbine.output", "§b涡轮机输出\n涡轮机通过放置在\n旋转转子周围的金属感应线圈\n产生能量。\n更多(或更高质量)的线圈\n产生能量的速度更快。");
        add("tooltip.biggerreactors.symbols.turbine.rotor_efficiency", "§b转子效率\n每片转子叶片\n只能捕获多少液体。\n如果输入流量超过了容量，\n效率就会下降。");
        add("tooltip.biggerreactors.symbols.turbine.tachometer", "§b转速表/转子在900或1800转/分左右时性能最佳。\n转子超速时间过长，\n可能会出现故障。");
    }
}
