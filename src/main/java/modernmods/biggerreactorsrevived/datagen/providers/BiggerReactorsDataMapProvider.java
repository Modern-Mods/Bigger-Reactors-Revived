package modernmods.biggerreactorsrevived.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.data.DataMapProvider;
import modernmods.biggerreactorsrevived.registries.BiggerReactorsDataMaps;
import modernmods.biggerreactorsrevived.registries.FluidTransitionRegistry;
import modernmods.biggerreactorsrevived.registries.ReactorModeratorRegistry;
import modernmods.biggerreactorsrevived.registries.TurbineCoilRegistry;

import java.util.concurrent.CompletableFuture;

public class BiggerReactorsDataMapProvider extends DataMapProvider {

    public BiggerReactorsDataMapProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    private static TagKey<Block> blockTag(String path) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("c", path));
    }

    private static TagKey<Fluid> fluidTag(String path) {
        return TagKey.create(Registries.FLUID, Identifier.fromNamespaceAndPath("c", path));
    }

    private static ResourceKey<Block> block(String id) {
        return ResourceKey.create(Registries.BLOCK, Identifier.parse(id));
    }

    private static ResourceKey<Fluid> fluid(String id) {
        return ResourceKey.create(Registries.FLUID, Identifier.parse(id));
    }

    private static ICondition[] loaded(String... modids) {
        final var conditions = new ICondition[modids.length];
        for (int i = 0; i < modids.length; i++) {
            conditions[i] = new ModLoadedCondition(modids[i]);
        }
        return conditions;
    }

    private static ReactorModeratorRegistry.ModeratorProperties moderator(double absorption, double efficiency, double moderation, double conductivity) {
        return new ReactorModeratorRegistry.ModeratorProperties(absorption, efficiency, moderation, conductivity);
    }

    private static TurbineCoilRegistry.CoilData coil(double efficiency, double extractionRate, double bonus) {
        return new TurbineCoilRegistry.CoilData(efficiency, bonus, extractionRate);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        moderators();
        coils();
        transitions();
    }

    private void moderators() {
        final var blocks = builder(BiggerReactorsDataMaps.REACTOR_MODERATOR);

        blocks.add(blockTag("storage_blocks/graphite"), moderator(0.1, 0.5, 2, 2), false);
        blocks.add(block("biggerreactors:ludicrite_block"), moderator(0.6, 0.87, 3, 3), false);

        blocks.add(block("minecraft:air"), moderator(0.1, 0.25, 1.1, 0.05), false);
        blocks.add(block("minecraft:cave_air"), moderator(0.1, 0.25, 1.1, 0.05), false);
        blocks.add(block("minecraft:void_air"), moderator(0.1, 0.25, 1.1, 0.05), false);
        blocks.add(block("minecraft:glass"), moderator(0.2, 0.25, 1.1, 0.3), false);
        blocks.add(block("minecraft:ice"), moderator(0.33, 0.33, 1.15, 0.1), false);
        blocks.add(block("minecraft:snow_block"), moderator(0.15, 0.33, 1.05, 0.05), false);
        blocks.add(block("minecraft:water"), moderator(0.33, 0.5, 1.33, 0.1), false);
        blocks.add(block("minecraft:lava"), moderator(0.33, 0.33, 1.15, 0.7), false);
        blocks.add(blockTag("storage_blocks/diamond"), moderator(0.55, 0.85, 1.5, 3), false);
        blocks.add(blockTag("storage_blocks/emerald"), moderator(0.55, 0.85, 1.5, 2.5), false);
        blocks.add(blockTag("storage_blocks/gold"), moderator(0.52, 0.8, 1.45, 2), false);
        blocks.add(blockTag("storage_blocks/iron"), moderator(0.5, 0.75, 1.4, 0.6), false);

        blocks.add(blockTag("storage_blocks/aluminum"), moderator(0.5, 0.78, 1.42, 0.6), false);
        blocks.add(blockTag("storage_blocks/bronze"), moderator(0.51, 0.77, 1.41, 1), false);
        blocks.add(blockTag("storage_blocks/copper"), moderator(0.5, 0.75, 1.4, 1), false);
        blocks.add(blockTag("storage_blocks/lead"), moderator(0.75, 0.75, 1.75, 1.5), false);
        blocks.add(blockTag("storage_blocks/nickel"), moderator(0.5, 0.82, 1.46, 0.6), false);
        blocks.add(blockTag("storage_blocks/platinum"), moderator(0.53, 0.86, 1.58, 2.5), false);
        blocks.add(blockTag("storage_blocks/silver"), moderator(0.51, 0.79, 1.43, 1.5), false);
        blocks.add(blockTag("storage_blocks/steel"), moderator(0.5, 0.78, 1.42, 0.6), false);
        blocks.add(blockTag("storage_blocks/tin"), moderator(0.3, 0.7, 1.35, 0.75), false);
        blocks.add(blockTag("storage_blocks/zinc"), moderator(0.51, 0.77, 1.41, 1), false);
        blocks.add(blockTag("storage_blocks/osmium"), moderator(0.51, 0.77, 1.41, 1), false);

        blocks.add(blockTag("storage_blocks/electrum"), moderator(0.53, 0.82, 1.47, 2.2), false);
        blocks.add(blockTag("storage_blocks/enderium"), moderator(0.53, 0.88, 1.6, 3), false);
        blocks.add(blockTag("storage_blocks/invar"), moderator(0.5, 0.79, 1.43, 0.6), false);
        blocks.add(blockTag("storage_blocks/lumium"), moderator(0.75, 0.55, 1.5, 1.8), false);
        blocks.add(blockTag("storage_blocks/signalum"), moderator(0.63, 0.66, 1.5, 1.8), false);

        blocks.add(blockTag("storage_blocks/allthemodium"), moderator(0.66, 0.9, 3.5, 3.5), false);
        blocks.add(blockTag("storage_blocks/unobtainium"), moderator(0.95, 0.82, 2, 5), false);
        blocks.add(blockTag("storage_blocks/vibranium"), moderator(0.15, 0.75, 8, 4), false);

        blocks.add(block("avaritia:crystal_matrix"), moderator(0.85, 0.92, 4, 1132), false, loaded("avaritia"));
        blocks.add(block("avaritia:neutron"), moderator(0.95, 0.95, 6, 5900), false, loaded("avaritia"));
        blocks.add(block("avaritia:infinity"), moderator(0.99, 0.99, 10, 10500), false, loaded("avaritia"));

        blocks.add(block("ultimatefoods:mushashite_block"), moderator(80, 80, 80, 80), false, loaded("ultimatefoods"));
        blocks.add(block("ultimatefoods:joanfoite_block"), moderator(90, 90, 90, 90), false, loaded("ultimatefoods"));
        blocks.add(block("ultimatefoods:nadienite_block"), moderator(200, 200, 200, 200), false, loaded("ultimatefoods"));

        // blocks.add(block("astralsorcery:liquid_starlight"), moderator(0.85, 0.8, 2.0, 3.0), false, loaded("astralsorcery"));
        // blocks.add(block("bloodmagic:life_essence_block"), moderator(0.7, 0.55, 1.75, 2.5), false, loaded("bloodmagic"));
        blocks.add(block("neovitae:essentia_vitae_block"), moderator(0.7, 0.55, 1.75, 2.5), false, loaded("neovitae"));

        blocks.add(block("mekanismgenerators:deuterium"), moderator(0.03, 0.3, 1.07, 0.1), false, loaded("mekanismgenerators"));
        blocks.add(block("mekanism:ethene"), moderator(0.37, 0.65, 1.9, 1.5), false, loaded("mekanism"));
        blocks.add(block("mekanism:hydrofluoric_acid"), moderator(0.6, 0.45, 1.4, 2.5), false, loaded("mekanism"));
        blocks.add(block("mekanism:hydrogen"), moderator(0.2, 0.3, 1.2, 0.1), false, loaded("mekanism"));
        blocks.add(block("mekanism:hydrogen_chloride"), moderator(0.31, 0.65, 1.7, 1), false, loaded("mekanism"));
        blocks.add(block("mekanism:lithium"), moderator(0.7, 0.6, 1.04, 0.7), false, loaded("mekanism"));
        blocks.add(block("mekanism:oxygen"), moderator(0.01, 0.35, 1.04, 0.1), false, loaded("mekanism"));
        blocks.add(block("mekanism:sodium"), moderator(0.23, 0.6, 1.7, 1), false, loaded("mekanism"));
        blocks.add(block("mekanism:steam"), moderator(0.33, 0.5, 1.33, 0.5), false, loaded("mekanism"));

        final var fluids = builder(BiggerReactorsDataMaps.REACTOR_FLUID_MODERATOR);

        fluids.add(fluid("biggerreactors:liquid_obsidian"), moderator(0.3, 0.7, 1.35, 0.75), false);
        fluids.add(fluidTag("superheated_sodium"), moderator(0.23, 0.6, 1.7, 1), false);
        fluids.add(fluidTag("redstone"), moderator(0.75, 0.55, 1.6, 2.5), false);
        fluids.add(fluidTag("ender"), moderator(0.9, 0.75, 2.0, 2), false);

        fluids.add(fluid("allthemodium:molten_allthemodium"), moderator(0.66, 0.9, 3.5, 3.5), false, loaded("allthemodium"));
        fluids.add(fluid("allthemodium:molten_unobtainium"), moderator(0.95, 0.82, 2, 5), false, loaded("allthemodium"));
        fluids.add(fluid("allthemodium:molten_vibranium"), moderator(0.15, 0.75, 8, 4), false, loaded("allthemodium"));
        fluids.add(fluid("allthemodium:vapor_allthemodium"), moderator(0.66, 0.9, 3.5, 3.5), false, loaded("allthemodium"));
        fluids.add(fluid("allthemodium:vapor_unobtainium"), moderator(0.95, 0.82, 2, 5), false, loaded("allthemodium"));
        fluids.add(fluid("allthemodium:vapor_vibranium"), moderator(0.15, 0.75, 8, 4), false, loaded("allthemodium"));
    }

    private void coils() {
        final var coils = builder(BiggerReactorsDataMaps.TURBINE_COIL);

        coils.add(block("biggerreactors:ludicrite_block"), coil(1.15, 0.35, 1.02), false);

        coils.add(blockTag("storage_blocks/gold"), coil(0.66, 0.175, 1), false);
        coils.add(blockTag("storage_blocks/iron"), coil(0.33, 0.1, 1), false);

        coils.add(blockTag("storage_blocks/aluminum"), coil(0.495, 0.13, 1), false);
        coils.add(blockTag("storage_blocks/copper"), coil(0.396, 0.12, 1), false);
        coils.add(blockTag("storage_blocks/electrum"), coil(0.825, 0.2, 1), false);
        coils.add(blockTag("storage_blocks/enderium"), coil(0.99, 0.3, 1.02), false);
        coils.add(blockTag("storage_blocks/invar"), coil(0.495, 0.14, 1), false);
        coils.add(blockTag("storage_blocks/platinum"), coil(0.99, 0.25, 1), false);
        coils.add(blockTag("storage_blocks/silver"), coil(0.561, 0.15, 1), false);
        coils.add(blockTag("storage_blocks/osmium"), coil(0.462, 0.12, 1), false);
        coils.add(blockTag("storage_blocks/steel"), coil(0.495, 0.13, 1), false);

        coils.add(blockTag("storage_blocks/allthemodium"), coil(1.2, 0.4, 1.02), false);
        coils.add(blockTag("storage_blocks/unobtainium"), coil(1.5, 0.7, 1.06), false);
        coils.add(blockTag("storage_blocks/vibranium"), coil(1.35, 0.5, 1.04), false);

        coils.add(block("avaritia:crystal_matrix"), coil(1132, 1132, 1.06), false, loaded("avaritia"));
        coils.add(block("avaritia:neutron"), coil(5900, 5900, 1.1), false, loaded("avaritia"));
        coils.add(block("avaritia:infinity"), coil(10500, 10500, 1.15), false, loaded("avaritia"));

        coils.add(block("ultimatefoods:mushashite_block"), coil(80, 80, 80), false, loaded("ultimatefoods"));
        coils.add(block("ultimatefoods:joanfoite_block"), coil(90, 90, 90), false, loaded("ultimatefoods"));
        coils.add(block("ultimatefoods:nadienite_block"), coil(200, 200, 200), false, loaded("ultimatefoods"));
    }

    private static ExtraCodecs.TagOrElementLocation gasId(String id) {
        return new ExtraCodecs.TagOrElementLocation(Identifier.parse(id), false);
    }

    private static ExtraCodecs.TagOrElementLocation gasTag(String path) {
        return new ExtraCodecs.TagOrElementLocation(Identifier.fromNamespaceAndPath("c", path), true);
    }

    private void transitions() {
        final var transitions = builder(BiggerReactorsDataMaps.FLUID_TRANSITION);

        transitions.add(fluid("minecraft:water"), new FluidTransitionRegistry.TransitionData(
                gasTag("steam"), 4, 373.15, 0.6, 0.025, 2.5), false);

        transitions.add(fluid("biggerreactors:liquid_obsidian"), new FluidTransitionRegistry.TransitionData(
                gasId("minecraft:lava"), 15, 1273.15, 1.9, 1.0, 0), false);

        transitions.add(fluid("mekanism:sodium"), new FluidTransitionRegistry.TransitionData(
                gasTag("superheated_sodium"), 0.4, 473.15, 1.2, 1.2, 0), false, loaded("mekanism"));

        transitions.add(fluid("allthemodium:molten_allthemodium"), new FluidTransitionRegistry.TransitionData(
                gasId("allthemodium:vapor_allthemodium"), 40, 1773.15, 5, 5, 0), false, loaded("allthemodium"));

        transitions.add(fluid("allthemodium:molten_unobtainium"), new FluidTransitionRegistry.TransitionData(
                gasId("allthemodium:vapor_unobtainium"), 100, 3523.15, 12, 12, 0), false, loaded("allthemodium"));

        transitions.add(fluid("allthemodium:molten_vibranium"), new FluidTransitionRegistry.TransitionData(
                gasId("allthemodium:vapor_vibranium"), 60, 2523.15, 8, 8, 0), false, loaded("allthemodium"));
    }
}
