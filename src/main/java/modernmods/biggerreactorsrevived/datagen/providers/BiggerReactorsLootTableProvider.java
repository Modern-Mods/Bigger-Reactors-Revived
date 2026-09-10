package modernmods.biggerreactorsrevived.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import modernmods.biggerreactorsrevived.BiggerReactors;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class BiggerReactorsLootTableProvider {

    private static final String[] SELF_DROPPING_BLOCKS = {
            "blutonium_block",
            "cyanite_block",
            "cyanite_reprocessor",
            "graphite_block",
            "heat_exchanger_casing",
            "heat_exchanger_computer_port",
            "heat_exchanger_condenser_channel",
            "heat_exchanger_evaporator_channel",
            "heat_exchanger_fluid_port",
            "heat_exchanger_glass",
            "heat_exchanger_terminal",
            "ludicrite_block",
            "raw_uranium_block",
            "reactor_access_port",
            "reactor_casing",
            "reactor_computer_port",
            "reactor_control_rod",
            "reactor_coolant_port",
            "reactor_fuel_rod",
            "reactor_glass",
            "reactor_manifold",
            "reactor_power_tap",
            "reactor_redstone_port",
            "reactor_terminal",
            "turbine_casing",
            "turbine_computer_port",
            "turbine_fluid_port",
            "turbine_glass",
            "turbine_power_tap",
            "turbine_rotor_bearing",
            "turbine_rotor_blade",
            "turbine_rotor_shaft",
            "turbine_terminal",
            "uranium_block",
    };

    private static final String[] ORE_BLOCKS = {
            "uranium_ore",
            "deepslate_uranium_ore",
    };

    private BiggerReactorsLootTableProvider() {
    }

    public static LootTableProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(BlockLoot::new, LootContextParamSets.BLOCK)
        ), lookupProvider);
    }

    private static Block block(String name) {
        return BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath(BiggerReactors.modid, name));
    }

    private static Item item(String name) {
        return BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath(BiggerReactors.modid, name));
    }

    private static final class BlockLoot extends BlockLootSubProvider {

        private BlockLoot(HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
        }

        @Override
        protected void generate() {
            for (final var name : SELF_DROPPING_BLOCKS) {
                dropSelf(block(name));
            }
            for (final var name : ORE_BLOCKS) {
                add(block(name), block -> createOreDrop(block, item("uranium_chunk")));
            }
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return Stream.concat(Stream.of(SELF_DROPPING_BLOCKS), Stream.of(ORE_BLOCKS))
                    .map(BiggerReactorsLootTableProvider::block)
                    .collect(Collectors.toList());
        }
    }
}
