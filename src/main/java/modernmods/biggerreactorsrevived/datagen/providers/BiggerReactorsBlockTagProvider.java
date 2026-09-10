package modernmods.biggerreactorsrevived.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import modernmods.biggerreactorsrevived.BiggerReactors;

import java.util.concurrent.CompletableFuture;

public class BiggerReactorsBlockTagProvider extends BlockTagsProvider {

    public static final TagKey<Block> ORES = commonTag("ores");
    public static final TagKey<Block> ORES_URANIUM = commonTag("ores/uranium");
    public static final TagKey<Block> STORAGE_BLOCKS = commonTag("storage_blocks");
    public static final TagKey<Block> STORAGE_BLOCKS_CYANITE = commonTag("storage_blocks/cyanite");
    public static final TagKey<Block> STORAGE_BLOCKS_GRAPHITE = commonTag("storage_blocks/graphite");
    public static final TagKey<Block> STORAGE_BLOCKS_LUDICRITE = commonTag("storage_blocks/ludicrite");
    public static final TagKey<Block> STORAGE_BLOCKS_RAW_URANIUM = commonTag("storage_blocks/raw_uranium");
    public static final TagKey<Block> STORAGE_BLOCKS_URANIUM = commonTag("storage_blocks/uranium");

    private static final String[] MINEABLE_BLOCKS = {
            "reactor_power_tap",
            "reactor_glass",
            "reactor_manifold",
            "reactor_casing",
            "reactor_redstone_port",
            "reactor_computer_port",
            "reactor_access_port",
            "reactor_terminal",
            "reactor_control_rod",
            "reactor_fuel_rod",
            "reactor_coolant_port",
            "turbine_glass",
            "turbine_rotor_shaft",
            "turbine_terminal",
            "turbine_rotor_blade",
            "turbine_power_tap",
            "turbine_fluid_port",
            "turbine_computer_port",
            "turbine_casing",
            "turbine_rotor_bearing",
            "heat_exchanger_computer_port",
            "heat_exchanger_condenser_channel",
            "heat_exchanger_terminal",
            "heat_exchanger_glass",
            "heat_exchanger_fluid_port",
            "heat_exchanger_evaporator_channel",
            "heat_exchanger_casing",
            "cyanite_reprocessor",
            "uranium_block",
            "raw_uranium_block",
            "ludicrite_block",
            "blutonium_block",
            "uranium_ore",
            "deepslate_uranium_ore",
            "cyanite_block",
            "graphite_block",
    };

    private static TagKey<Block> commonTag(String path) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("c", path));
    }

    private static Block block(String name) {
        return BuiltInRegistries.BLOCK.getValue(Identifier.fromNamespaceAndPath(BiggerReactors.modid, name));
    }

    public BiggerReactorsBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, BiggerReactors.modid);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ORES).add(block("uranium_ore"), block("deepslate_uranium_ore"));
        tag(ORES_URANIUM).add(block("uranium_ore"), block("deepslate_uranium_ore"));

        tag(STORAGE_BLOCKS).add(
                block("cyanite_block"),
                block("graphite_block"),
                block("ludicrite_block"),
                block("uranium_block"),
                block("raw_uranium_block"));
        tag(STORAGE_BLOCKS_CYANITE).add(block("cyanite_block"));
        tag(STORAGE_BLOCKS_GRAPHITE).add(block("graphite_block"));
        tag(STORAGE_BLOCKS_LUDICRITE).add(block("ludicrite_block"));
        tag(STORAGE_BLOCKS_RAW_URANIUM).add(block("raw_uranium_block"));
        tag(STORAGE_BLOCKS_URANIUM).add(block("uranium_block"));

        final var mineable = tag(BlockTags.MINEABLE_WITH_PICKAXE);
        final var needsIron = tag(BlockTags.NEEDS_IRON_TOOL);
        for (final var name : MINEABLE_BLOCKS) {
            final var block = block(name);
            mineable.add(block);
            needsIron.add(block);
        }
    }
}
