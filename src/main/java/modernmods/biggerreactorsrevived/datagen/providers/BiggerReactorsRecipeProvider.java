package modernmods.biggerreactorsrevived.datagen.providers;

import modernmods.biggerreactorsrevived.BiggerReactors;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class BiggerReactorsRecipeProvider extends RecipeProvider {

    public BiggerReactorsRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(BiggerReactors.modid, path);
    }

    private static Item lookup(String id) {
        return BuiltInRegistries.ITEM.get(ResourceLocation.parse(id));
    }

    private static Ingredient item(String id) {
        return Ingredient.of(lookup(id));
    }

    private static ItemStack result(String id, int count) {
        return new ItemStack(lookup(id), count);
    }

    private static Ingredient items(String... ids) {
        return Ingredient.of(Arrays.stream(ids).map(BiggerReactorsRecipeProvider::lookup).toArray(net.minecraft.world.level.ItemLike[]::new));
    }

    private static Ingredient tag(String id) {
        return Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.parse(id)));
    }

    private static ICondition modLoaded(String... modids) {
        return new ModLoadedCondition(modids[0]);
    }

    private static void shaped(RecipeOutput output, String path, int count, String result, List<String> pattern, Map<Character, Ingredient> key) {
        output.accept(id(path), new ShapedRecipe("", CraftingBookCategory.MISC,
                ShapedRecipePattern.of(key, pattern), result(result, count)), null);
    }

    private static void shapeless(RecipeOutput output, String path, int count, String result, List<Ingredient> ingredients) {
        output.accept(id(path), new ShapelessRecipe("", CraftingBookCategory.MISC,
                result(result, count), net.minecraft.core.NonNullList.copyOf(ingredients)), null);
    }

    @SuppressWarnings("unchecked")
    private static void shapelessWithBook(RecipeOutput output, String path, int count, String result, String component, String value, List<Ingredient> ingredients) {
        final var stack = result(result, count);
        final var type = BuiltInRegistries.DATA_COMPONENT_TYPE.get(ResourceLocation.parse(component));
        stack.set((DataComponentType<ResourceLocation>) type, ResourceLocation.parse(value));
        output.accept(id(path), new ShapelessRecipe("", CraftingBookCategory.MISC,
                stack, net.minecraft.core.NonNullList.copyOf(ingredients)), null);
    }

    private static void smelting(RecipeOutput output, String path, Ingredient input, int count, String result, float experience, int time) {
        output.accept(id(path), new SmeltingRecipe("", CookingBookCategory.MISC, input,
                result(result, count), experience, time), null);
    }

    private static void blasting(RecipeOutput output, String path, Ingredient input, int count, String result, float experience, int time) {
        output.accept(id(path), new BlastingRecipe("", CookingBookCategory.MISC, input,
                result(result, count), experience, time), null);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        blasting(output, "blasting/blutonium_ingot", item("biggerreactors:blutonium_dust"), 1, "biggerreactors:blutonium_ingot", 0F, 100);
        blasting(output, "blasting/cyanite_ingot", item("biggerreactors:cyanite_dust"), 1, "biggerreactors:cyanite_ingot", 0F, 100);
        blasting(output, "blasting/deepslate_uranium_ore", item("biggerreactors:deepslate_uranium_ore"), 1, "biggerreactors:uranium_ingot", 0.35F, 100);
        blasting(output, "blasting/graphite_dust", item("biggerreactors:graphite_dust"), 1, "biggerreactors:graphite_ingot", 0F, 100);
        blasting(output, "blasting/graphite_ingot", items("minecraft:coal", "minecraft:charcoal"), 1, "biggerreactors:graphite_ingot", 0.1F, 100);
        blasting(output, "blasting/ludicrite_ingot", item("biggerreactors:ludicrite_dust"), 1, "biggerreactors:ludicrite_ingot", 0F, 100);
        blasting(output, "blasting/uranium_chunk", item("biggerreactors:uranium_chunk"), 1, "biggerreactors:uranium_ingot", 0.35F, 100);
        blasting(output, "blasting/uranium_ingot", item("biggerreactors:uranium_dust"), 1, "biggerreactors:uranium_ingot", 0F, 100);
        blasting(output, "blasting/uranium_ore", item("biggerreactors:uranium_ore"), 1, "biggerreactors:uranium_ingot", 0.35F, 100);
        shaped(output, "crafting/blutonium_block", 1, "biggerreactors:blutonium_block", List.of("III", "III", "III"), Map.of(
                'I', item("biggerreactors:blutonium_ingot")));
        shapeless(output, "crafting/blutonium_ingot", 9, "biggerreactors:blutonium_ingot", List.of(item("biggerreactors:blutonium_block")));
        shaped(output, "crafting/cyanite_block", 1, "biggerreactors:cyanite_block", List.of("III", "III", "III"), Map.of(
                'I', tag("c:ingots/cyanite")));
        shapeless(output, "crafting/cyanite_ingot", 9, "biggerreactors:cyanite_ingot", List.of(item("biggerreactors:cyanite_block")));
        shaped(output, "crafting/cyanite_reprocessor", 1, "biggerreactors:cyanite_reprocessor", List.of("CIC", "PFP", "CRC"), Map.of(
                'C', item("biggerreactors:reactor_casing"),
                'F', item("biggerreactors:reactor_fuel_rod"),
                'I', item("minecraft:iron_ingot"),
                'P', item("minecraft:piston"),
                'R', item("minecraft:redstone")));
        shaped(output, "crafting/graphite_block", 1, "biggerreactors:graphite_block", List.of("III", "III", "III"), Map.of(
                'I', tag("c:ingots/graphite")));
        shapeless(output, "crafting/graphite_ingot", 9, "biggerreactors:graphite_ingot", List.of(item("biggerreactors:graphite_block")));
        shaped(output, "crafting/heat_exchanger/casing", 2, "biggerreactors:heat_exchanger_casing", List.of("CIC", "ICI", "CIC"), Map.of(
                'C', item("minecraft:copper_ingot"),
                'I', item("minecraft:iron_ingot")));
        {
            final var conditional = output.withConditions(modLoaded("computercraft"));
            shaped(conditional, "crafting/heat_exchanger/computer_port", 1, "biggerreactors:heat_exchanger_computer_port", List.of("CDC", "GRG", "CDC"), Map.of(
                    'C', item("biggerreactors:heat_exchanger_casing"),
                    'D', item("minecraft:redstone"),
                    'G', item("minecraft:gold_ingot"),
                    'R', item("minecraft:repeater")));
        }
        shaped(output, "crafting/heat_exchanger/condenser_channel_alt", 1, "biggerreactors:heat_exchanger_condenser_channel", List.of("C"), Map.of(
                'C', item("biggerreactors:heat_exchanger_evaporator_channel")));
        shaped(output, "crafting/heat_exchanger/evaporator_channel", 1, "biggerreactors:heat_exchanger_evaporator_channel", List.of("CGC", "CGC", "CGC"), Map.of(
                'C', item("minecraft:copper_ingot"),
                'G', item("minecraft:glass")));
        shaped(output, "crafting/heat_exchanger/evaporator_channel_alt", 1, "biggerreactors:heat_exchanger_evaporator_channel", List.of("C"), Map.of(
                'C', item("biggerreactors:heat_exchanger_condenser_channel")));
        shaped(output, "crafting/heat_exchanger/fluid_port", 1, "biggerreactors:heat_exchanger_fluid_port", List.of("C C", "IVI", "CPC"), Map.of(
                'C', item("biggerreactors:heat_exchanger_casing"),
                'I', item("minecraft:iron_ingot"),
                'P', item("minecraft:piston"),
                'V', item("minecraft:bucket")));
        shaped(output, "crafting/heat_exchanger/glass", 1, "biggerreactors:heat_exchanger_glass", List.of("GCG"), Map.of(
                'C', item("biggerreactors:heat_exchanger_casing"),
                'G', item("minecraft:glass")));
        shaped(output, "crafting/heat_exchanger/terminal", 1, "biggerreactors:heat_exchanger_terminal", List.of("CDC", "LLL", "CDC"), Map.of(
                'C', item("biggerreactors:heat_exchanger_casing"),
                'D', item("minecraft:diamond"),
                'L', tag("c:ingots/ludicrite")));
        shaped(output, "crafting/ludicrite_block", 1, "biggerreactors:ludicrite_block", List.of("III", "III", "III"), Map.of(
                'I', item("biggerreactors:ludicrite_ingot")));
        shaped(output, "crafting/ludicrite_block_nether_star", 1, "biggerreactors:ludicrite_block", List.of("BPB", "ENE", "BPB"), Map.of(
                'B', item("biggerreactors:blutonium_ingot"),
                'E', item("minecraft:emerald_block"),
                'N', item("minecraft:nether_star"),
                'P', item("minecraft:ender_pearl")));
        shapeless(output, "crafting/ludicrite_ingot", 9, "biggerreactors:ludicrite_ingot", List.of(item("biggerreactors:ludicrite_block")));
        shaped(output, "crafting/raw_uranium_block", 1, "biggerreactors:raw_uranium_block", List.of("III", "III", "III"), Map.of(
                'I', tag("c:raw_materials/uranium")));
        shapeless(output, "crafting/raw_uranium_block_to_chunk", 9, "biggerreactors:uranium_chunk", List.of(item("biggerreactors:raw_uranium_block")));
        shaped(output, "crafting/reactor/reactor_access_port", 1, "biggerreactors:reactor_access_port", List.of("C C", " V ", "CPC"), Map.of(
                'C', item("biggerreactors:reactor_casing"),
                'P', item("minecraft:piston"),
                'V', tag("c:chests/wooden")));
        shaped(output, "crafting/reactor/reactor_casing", 4, "biggerreactors:reactor_casing", List.of("IGI", "GYG", "IGI"), Map.of(
                'G', tag("c:ingots/graphite"),
                'I', item("minecraft:iron_ingot"),
                'Y', tag("c:ingots/uranium")));
        {
            final var conditional = output.withConditions(modLoaded("computercraft"));
            shaped(conditional, "crafting/reactor/reactor_computer_port", 1, "biggerreactors:reactor_computer_port", List.of("CDC", "GRG", "CDC"), Map.of(
                    'C', item("biggerreactors:reactor_casing"),
                    'D', item("minecraft:redstone"),
                    'G', item("minecraft:gold_ingot"),
                    'R', item("minecraft:repeater")));
        }
        shaped(output, "crafting/reactor/reactor_control_rod", 1, "biggerreactors:reactor_control_rod", List.of("CGC", "GRG", "CYC"), Map.of(
                'C', item("biggerreactors:reactor_casing"),
                'G', tag("c:ingots/graphite"),
                'R', item("minecraft:redstone"),
                'Y', tag("c:ingots/uranium")));
        shaped(output, "crafting/reactor/reactor_coolant_port", 1, "biggerreactors:reactor_coolant_port", List.of("C C", "IVI", "CPC"), Map.of(
                'C', item("biggerreactors:reactor_casing"),
                'I', item("minecraft:iron_ingot"),
                'P', item("minecraft:piston"),
                'V', item("minecraft:bucket")));
        shaped(output, "crafting/reactor/reactor_fuel_rod", 1, "biggerreactors:reactor_fuel_rod", List.of("IGI", "IYI", "IGI"), Map.of(
                'G', tag("c:ingots/graphite"),
                'I', item("minecraft:iron_ingot"),
                'Y', tag("c:ingots/uranium")));
        shaped(output, "crafting/reactor/reactor_glass", 1, "biggerreactors:reactor_glass", List.of("GCG"), Map.of(
                'C', item("biggerreactors:reactor_casing"),
                'G', item("minecraft:glass")));
        shaped(output, "crafting/reactor/reactor_manifold", 4, "biggerreactors:reactor_manifold", List.of("IGI", "G G", "IGI"), Map.of(
                'G', tag("c:glass"),
                'I', item("minecraft:iron_ingot")));
        shaped(output, "crafting/reactor/reactor_power_tap", 1, "biggerreactors:reactor_power_tap", List.of("CRC", "R R", "CRC"), Map.of(
                'C', item("biggerreactors:reactor_casing"),
                'R', item("minecraft:redstone")));
        shaped(output, "crafting/reactor/reactor_redstone_port", 1, "biggerreactors:reactor_redstone_port", List.of("CRC", "RGR", "CRC"), Map.of(
                'C', item("biggerreactors:reactor_casing"),
                'G', item("minecraft:gold_ingot"),
                'R', item("minecraft:redstone")));
        shaped(output, "crafting/reactor/reactor_terminal", 1, "biggerreactors:reactor_terminal", List.of("C C", "YDY", "CRC"), Map.of(
                'C', item("biggerreactors:reactor_casing"),
                'D', item("minecraft:diamond"),
                'R', item("minecraft:redstone"),
                'Y', tag("c:ingots/uranium")));
        shaped(output, "crafting/turbine/turbine_casing", 4, "biggerreactors:turbine_casing", List.of("IGI", "QCQ", "IGI"), Map.of(
                'C', tag("c:ingots/cyanite"),
                'G', tag("c:ingots/graphite"),
                'I', item("minecraft:iron_ingot"),
                'Q', item("minecraft:quartz")));
        {
            final var conditional = output.withConditions(modLoaded("computercraft"));
            shaped(conditional, "crafting/turbine/turbine_computer_port", 1, "biggerreactors:turbine_computer_port", List.of("CDC", "GRG", "CDC"), Map.of(
                    'C', item("biggerreactors:turbine_casing"),
                    'D', item("minecraft:redstone"),
                    'G', item("minecraft:gold_ingot"),
                    'R', item("minecraft:repeater")));
        }
        shaped(output, "crafting/turbine/turbine_fluid_port", 1, "biggerreactors:turbine_fluid_port", List.of("C C", "IVI", "CPC"), Map.of(
                'C', item("biggerreactors:turbine_casing"),
                'I', item("minecraft:iron_ingot"),
                'P', item("minecraft:piston"),
                'V', item("minecraft:bucket")));
        shaped(output, "crafting/turbine/turbine_glass", 1, "biggerreactors:turbine_glass", List.of("GCG"), Map.of(
                'C', item("biggerreactors:turbine_casing"),
                'G', item("minecraft:glass")));
        shaped(output, "crafting/turbine/turbine_power_tap", 1, "biggerreactors:turbine_power_tap", List.of("CRC", "R R", "CRC"), Map.of(
                'C', item("biggerreactors:turbine_casing"),
                'R', item("minecraft:redstone")));
        shaped(output, "crafting/turbine/turbine_rotor_bearing", 1, "biggerreactors:turbine_rotor_bearing", List.of("CSC", "DDD", "CSC"), Map.of(
                'C', item("biggerreactors:turbine_casing"),
                'D', item("minecraft:diamond"),
                'S', item("biggerreactors:turbine_rotor_shaft")));
        shaped(output, "crafting/turbine/turbine_rotor_blade", 1, "biggerreactors:turbine_rotor_blade", List.of("CII"), Map.of(
                'C', tag("c:ingots/cyanite"),
                'I', item("minecraft:iron_ingot")));
        shaped(output, "crafting/turbine/turbine_rotor_shaft", 1, "biggerreactors:turbine_rotor_shaft", List.of("ICI"), Map.of(
                'C', tag("c:ingots/cyanite"),
                'I', item("minecraft:iron_ingot")));
        shaped(output, "crafting/turbine/turbine_terminal", 1, "biggerreactors:turbine_terminal", List.of("C C", "BDB", "C C"), Map.of(
                'B', item("biggerreactors:blutonium_ingot"),
                'C', item("biggerreactors:turbine_casing"),
                'D', item("minecraft:diamond")));
        shaped(output, "crafting/uranium_block", 1, "biggerreactors:uranium_block", List.of("III", "III", "III"), Map.of(
                'I', tag("c:ingots/uranium")));
        shapeless(output, "crafting/uranium_block_to_ingot", 9, "biggerreactors:uranium_ingot", List.of(item("biggerreactors:uranium_block")));
        shapeless(output, "crafting/uranium_to_cyanite", 1, "biggerreactors:cyanite_dust", List.of(tag("c:dusts/uranium"), item("minecraft:sand")));
        shapelessWithBook(output, "guide", 1, "patchouli:guide_book", "patchouli:book", "biggerreactors:guide", List.of(item("minecraft:writable_book"), item("biggerreactors:reactor_casing")));
        smelting(output, "smelting/blutonium_ingot", item("biggerreactors:blutonium_dust"), 1, "biggerreactors:blutonium_ingot", 0F, 200);
        smelting(output, "smelting/cyanite_ingot", item("biggerreactors:cyanite_dust"), 1, "biggerreactors:cyanite_ingot", 0F, 200);
        smelting(output, "smelting/deepslate_uranium_ore", item("biggerreactors:deepslate_uranium_ore"), 1, "biggerreactors:uranium_ingot", 0.35F, 200);
        smelting(output, "smelting/graphite_dust", item("biggerreactors:graphite_dust"), 1, "biggerreactors:graphite_ingot", 0F, 200);
        smelting(output, "smelting/graphite_ingot", items("minecraft:coal", "minecraft:charcoal"), 1, "biggerreactors:graphite_ingot", 0.1F, 200);
        smelting(output, "smelting/ludicrite_ingot", item("biggerreactors:ludicrite_dust"), 1, "biggerreactors:ludicrite_ingot", 0F, 200);
        smelting(output, "smelting/uranium_chunk", item("biggerreactors:uranium_chunk"), 1, "biggerreactors:uranium_ingot", 0.35F, 200);
        smelting(output, "smelting/uranium_dust", item("biggerreactors:uranium_dust"), 1, "biggerreactors:uranium_ingot", 0F, 200);
        smelting(output, "smelting/uranium_ore", item("biggerreactors:uranium_ore"), 1, "biggerreactors:uranium_ingot", 0.35F, 200);
    }
}
