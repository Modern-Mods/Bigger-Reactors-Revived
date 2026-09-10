package modernmods.biggerreactorsrevived.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import modernmods.biggerreactorsrevived.BiggerReactors;

import java.util.concurrent.CompletableFuture;

public class BiggerReactorsFluidTagProvider extends FluidTagsProvider {

    public static final TagKey<Fluid> STEAM = TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath("c", "steam"));

    private static Fluid fluid(String name) {
        return BuiltInRegistries.FLUID.get(ResourceLocation.fromNamespaceAndPath(BiggerReactors.modid, name));
    }

    public BiggerReactorsFluidTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, BiggerReactors.modid, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(STEAM).add(fluid("steam"));
    }
}
