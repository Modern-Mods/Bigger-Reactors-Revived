package modernmods.biggerreactorsrevived.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import modernmods.biggerreactorsrevived.BiggerReactors;

import java.util.concurrent.CompletableFuture;

public class BiggerReactorsItemTagProvider extends ItemTagsProvider {

    public static final TagKey<Item> DUSTS = commonTag("dusts");
    public static final TagKey<Item> DUSTS_CYANITE = commonTag("dusts/cyanite");
    public static final TagKey<Item> DUSTS_GRAPHITE = commonTag("dusts/graphite");
    public static final TagKey<Item> DUSTS_LUDICRITE = commonTag("dusts/ludicrite");
    public static final TagKey<Item> DUSTS_URANIUM = commonTag("dusts/uranium");

    public static final TagKey<Item> INGOTS = commonTag("ingots");
    public static final TagKey<Item> INGOTS_CYANITE = commonTag("ingots/cyanite");
    public static final TagKey<Item> INGOTS_GRAPHITE = commonTag("ingots/graphite");
    public static final TagKey<Item> INGOTS_LUDICRITE = commonTag("ingots/ludicrite");
    public static final TagKey<Item> INGOTS_URANIUM = commonTag("ingots/uranium");

    public static final TagKey<Item> ORES = commonTag("ores");
    public static final TagKey<Item> ORES_URANIUM = commonTag("ores/uranium");
    public static final TagKey<Item> RAW_MATERIALS_URANIUM = commonTag("raw_materials/uranium");

    public static final TagKey<Item> STORAGE_BLOCKS = commonTag("storage_blocks");
    public static final TagKey<Item> STORAGE_BLOCKS_CYANITE = commonTag("storage_blocks/cyanite");
    public static final TagKey<Item> STORAGE_BLOCKS_GRAPHITE = commonTag("storage_blocks/graphite");
    public static final TagKey<Item> STORAGE_BLOCKS_LUDICRITE = commonTag("storage_blocks/ludicrite");
    public static final TagKey<Item> STORAGE_BLOCKS_RAW_URANIUM = commonTag("storage_blocks/raw_uranium");
    public static final TagKey<Item> STORAGE_BLOCKS_URANIUM = commonTag("storage_blocks/uranium");

    public static final TagKey<Item> TOOLS_WRENCH = commonTag("tools/wrench");

    private static TagKey<Item> commonTag(String path) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", path));
    }

    private static Item item(String name) {
        return BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath(BiggerReactors.modid, name));
    }

    public BiggerReactorsItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<net.minecraft.data.tags.TagsProvider.TagLookup<Block>> blockTags) {
        super(output, lookupProvider, BiggerReactors.modid);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(DUSTS).add(
                item("blutonium_dust"),
                item("cyanite_dust"),
                item("graphite_dust"),
                item("ludicrite_dust"),
                item("uranium_dust"));
        tag(DUSTS_CYANITE).add(item("cyanite_dust"));
        tag(DUSTS_GRAPHITE).add(item("graphite_dust"));
        tag(DUSTS_LUDICRITE).add(item("ludicrite_dust"));
        tag(DUSTS_URANIUM).add(item("uranium_dust"));

        tag(INGOTS).add(
                item("blutonium_ingot"),
                item("cyanite_ingot"),
                item("graphite_ingot"),
                item("ludicrite_ingot"),
                item("uranium_ingot"));
        tag(INGOTS_CYANITE).add(item("cyanite_ingot"));
        tag(INGOTS_GRAPHITE).add(item("graphite_ingot"));
        tag(INGOTS_LUDICRITE).add(item("ludicrite_ingot"));
        tag(INGOTS_URANIUM).add(item("uranium_ingot"));

        tag(ORES).add(item("uranium_ore"), item("deepslate_uranium_ore"));
        tag(ORES_URANIUM).add(item("uranium_ore"), item("deepslate_uranium_ore"));
        tag(RAW_MATERIALS_URANIUM).add(item("uranium_chunk"));

        tag(STORAGE_BLOCKS).add(
                item("cyanite_block"),
                item("graphite_block"),
                item("ludicrite_block"),
                item("uranium_block"),
                item("raw_uranium_block"));
        tag(STORAGE_BLOCKS_CYANITE).add(item("cyanite_block"));
        tag(STORAGE_BLOCKS_GRAPHITE).add(item("graphite_block"));
        tag(STORAGE_BLOCKS_LUDICRITE).add(item("ludicrite_block"));
        tag(STORAGE_BLOCKS_RAW_URANIUM).add(item("raw_uranium_block"));
        tag(STORAGE_BLOCKS_URANIUM).add(item("uranium_block"));

        tag(TOOLS_WRENCH).add(item("wrench"));
    }
}
