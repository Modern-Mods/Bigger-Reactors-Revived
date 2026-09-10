package modernmods.biggerreactorsrevived.client.deps.jei.classic.machine;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import modernmods.biggerreactorsrevived.BiggerReactors;
import modernmods.biggerreactorsrevived.Config;
import modernmods.biggerreactorsrevived.items.ingots.BlutoniumIngot;
import modernmods.biggerreactorsrevived.items.ingots.CyaniteIngot;
import modernmods.biggerreactorsrevived.machine.blocks.CyaniteReprocessor;

import java.awt.*;

public class CyaniteReprocessorCategory implements IRecipeCategory<CyaniteReprocessorCategory.Recipe> {

    private final IDrawable background;
    private final IDrawable icon;
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(BiggerReactors.modid, "classic/cyanite_reprocessor");
    public static final RecipeType<Recipe> RECIPE_TYPE = new RecipeType<>(UID, Recipe.class);

    public CyaniteReprocessorCategory(IGuiHelper guiHelper) {
        icon = guiHelper.createDrawableItemStack(new ItemStack(CyaniteReprocessor.INSTANCE));
        background = guiHelper.createDrawable(ResourceLocation.fromNamespaceAndPath(BiggerReactors.modid, "textures/jei/common.png"), 0, 6, 144, 34);
    }

    @Override
    public RecipeType<Recipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.biggerreactors.classic.cyanite_reprocessor");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, Recipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 9).addItemStack(recipe.input());
        builder.addSlot(RecipeIngredientRole.INPUT, 21, 9)
                .addIngredient(NeoForgeTypes.FLUID_STACK, recipe.water())
                .setFluidRenderer(recipe.water().getAmount(), false, 16, 16);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 123, 9).addItemStack(recipe.output());
    }

    @Override
    public void draw(Recipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        final var mc = Minecraft.getInstance();
        final Component[] info = {
                Component.translatable("jei.biggerreactors.classic.cyanite_reprocessor_time", recipe.workTime() / 20.0),
                Component.translatable("jei.biggerreactors.classic.cyanite_reprocessor_energy", recipe.energy()),
                Component.translatable("jei.biggerreactors.classic.cyanite_reprocessor_water", recipe.water().getAmount())
        };
        for (int i = 0; i < info.length; i++) {
            guiGraphics.drawString(mc.font, info[i], 80 - mc.font.width(info[i]) / 2, i * 12, Color.BLACK.getRGB(), false);
        }
    }

    public record Recipe(ItemStack input, FluidStack water, ItemStack output, int workTime, int energy) {

        public static Recipe create() {
            final var config = Config.CONFIG.CyaniteReprocessor;
            return new Recipe(
                    new ItemStack(CyaniteIngot.INSTANCE),
                    new FluidStack(Fluids.WATER, config.WaterConsumptionPerTick * config.TotalWorkTime),
                    new ItemStack(BlutoniumIngot.INSTANCE),
                    config.TotalWorkTime,
                    config.EnergyConsumptionPerTick * config.TotalWorkTime);
        }
    }
}
