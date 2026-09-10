package modernmods.biggerreactorsrevived.client.deps.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.neoforged.neoforge.fluids.FluidStack;
import modernmods.biggerreactorsrevived.BiggerReactors;
import modernmods.biggerreactorsrevived.Config;
import modernmods.biggerreactorsrevived.client.deps.jei.classic.reactor.BlockModeratorCategory;
import modernmods.biggerreactorsrevived.client.deps.jei.classic.reactor.FluidModeratorCategory;
import modernmods.biggerreactorsrevived.client.deps.jei.classic.machine.CyaniteReprocessorCategory;
import modernmods.biggerreactorsrevived.client.deps.jei.classic.turbine.CoilCategory;
import modernmods.biggerreactorsrevived.machine.blocks.CyaniteReprocessor;
import modernmods.biggerreactorsrevived.multiblocks.reactor.blocks.ReactorTerminal;
import modernmods.biggerreactorsrevived.multiblocks.turbine.blocks.TurbineTerminal;
import modernmods.biggerreactorsrevived.registries.ReactorModeratorRegistry;
import modernmods.biggerreactorsrevived.registries.TurbineCoilRegistry;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JeiPlugin
public class BiggerReactorsJEIPlugin implements IModPlugin {

    @Nonnull
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(BiggerReactors.modid, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new BlockModeratorCategory(guiHelper));
        registration.addRecipeCategories(new FluidModeratorCategory(guiHelper));
        registration.addRecipeCategories(new CoilCategory(guiHelper));
        registration.addRecipeCategories(new CyaniteReprocessorCategory(guiHelper));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ReactorTerminal.INSTANCE), BlockModeratorCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ReactorTerminal.INSTANCE), FluidModeratorCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(TurbineTerminal.INSTANCE), CoilCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(CyaniteReprocessor.INSTANCE), CyaniteReprocessorCategory.RECIPE_TYPE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (!Config.CONFIG.EnableJEIIntegration) {
            return;
        }
        
        List<CoilCategory.Recipe> recipes = TurbineCoilRegistry.Client.getImmutableRegistry().entrySet().stream()
                .map(e -> new CoilCategory.Recipe(new ItemStack(e.getKey().asItem()), e.getValue()))
                .collect(Collectors.toList());
        
        registration.addRecipes(CoilCategory.RECIPE_TYPE, recipes);
        
        
        List<FluidModeratorCategory.Recipe> fluidModeratorRecipes = new ArrayList<>();
        List<BlockModeratorCategory.Recipe> blockModeratorRecipes = new ArrayList<>();

        ReactorModeratorRegistry.Client.forEach((block, moderatorProperties) -> {
            if (block instanceof LiquidBlock) {
                LiquidBlock fluidBlock = (LiquidBlock) block;
                FluidStack stack = new FluidStack(fluidBlock.fluid, 1000);
                fluidModeratorRecipes.add(new FluidModeratorCategory.Recipe(stack, moderatorProperties));
            } else if (!(block instanceof AirBlock)) {
                ItemStack stack = new ItemStack(block.asItem());
                blockModeratorRecipes.add(new BlockModeratorCategory.Recipe(stack, moderatorProperties));
            }
        });

        registration.addRecipes(FluidModeratorCategory.RECIPE_TYPE, fluidModeratorRecipes);
        registration.addRecipes(BlockModeratorCategory.RECIPE_TYPE, blockModeratorRecipes);

        registration.addRecipes(CyaniteReprocessorCategory.RECIPE_TYPE, List.of(CyaniteReprocessorCategory.Recipe.create()));
    }
}
