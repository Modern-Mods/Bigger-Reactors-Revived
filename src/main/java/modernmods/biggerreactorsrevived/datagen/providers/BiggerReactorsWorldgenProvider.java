package modernmods.biggerreactorsrevived.datagen.providers;

import modernmods.biggerreactorsrevived.BiggerReactors;
import modernmods.biggerreactorsrevived.blocks.materials.DeepslateUraniumOre;
import modernmods.biggerreactorsrevived.blocks.materials.UraniumOre;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class BiggerReactorsWorldgenProvider extends DatapackBuiltinEntriesProvider {

    public static final ResourceKey<ConfiguredFeature<?, ?>> URANIUM_ORE_FEATURE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, id("uranium_ore"));
    public static final ResourceKey<PlacedFeature> URANIUM_ORE_PLACEMENT =
            ResourceKey.create(Registries.PLACED_FEATURE, id("uranium_ore"));
    public static final ResourceKey<BiomeModifier> OVERWORLD_ORES =
            ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, id("overworld_ores"));

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, BiggerReactorsWorldgenProvider::configuredFeatures)
            .add(Registries.PLACED_FEATURE, BiggerReactorsWorldgenProvider::placedFeatures)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, BiggerReactorsWorldgenProvider::biomeModifiers);

    public BiggerReactorsWorldgenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, BUILDER, Set.of(BiggerReactors.modid));
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(BiggerReactors.modid, path);
    }

    private static void configuredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        final var targets = List.of(
                OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), UraniumOre.INSTANCE.defaultBlockState()),
                OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), DeepslateUraniumOre.INSTANCE.defaultBlockState()));
        context.register(URANIUM_ORE_FEATURE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(targets, 8)));
    }

    private static void placedFeatures(BootstrapContext<PlacedFeature> context) {
        final var features = context.lookup(Registries.CONFIGURED_FEATURE);
        context.register(URANIUM_ORE_PLACEMENT, new PlacedFeature(features.getOrThrow(URANIUM_ORE_FEATURE), List.of(
                CountPlacement.of(5),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(48)))));
    }

    private static void biomeModifiers(BootstrapContext<BiomeModifier> context) {
        final var biomes = context.lookup(Registries.BIOME);
        final var placements = context.lookup(Registries.PLACED_FEATURE);
        context.register(OVERWORLD_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD),
                HolderSet.direct(placements.getOrThrow(URANIUM_ORE_PLACEMENT)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
    }
}
