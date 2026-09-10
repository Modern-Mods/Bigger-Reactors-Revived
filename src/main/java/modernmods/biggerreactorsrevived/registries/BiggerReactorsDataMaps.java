package modernmods.biggerreactorsrevived.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import modernmods.biggerreactorsrevived.BiggerReactors;

public final class BiggerReactorsDataMaps {

    public static final DataMapType<Block, ReactorModeratorRegistry.ModeratorProperties> REACTOR_MODERATOR =
            DataMapType.builder(id("reactor_moderator"), Registries.BLOCK, ReactorModeratorRegistry.ModeratorProperties.CODEC)
                    .synced(ReactorModeratorRegistry.ModeratorProperties.CODEC, false)
                    .build();

    public static final DataMapType<Fluid, ReactorModeratorRegistry.ModeratorProperties> REACTOR_FLUID_MODERATOR =
            DataMapType.builder(id("reactor_fluid_moderator"), Registries.FLUID, ReactorModeratorRegistry.ModeratorProperties.CODEC)
                    .synced(ReactorModeratorRegistry.ModeratorProperties.CODEC, false)
                    .build();

    public static final DataMapType<Block, TurbineCoilRegistry.CoilData> TURBINE_COIL =
            DataMapType.builder(id("turbine_coil"), Registries.BLOCK, TurbineCoilRegistry.CoilData.CODEC)
                    .synced(TurbineCoilRegistry.CoilData.CODEC, false)
                    .build();

    public static final DataMapType<Fluid, FluidTransitionRegistry.TransitionData> FLUID_TRANSITION =
            DataMapType.builder(id("fluid_transition"), Registries.FLUID, FluidTransitionRegistry.TransitionData.CODEC)
                    .synced(FluidTransitionRegistry.TransitionData.CODEC, false)
                    .build();

    private BiggerReactorsDataMaps() {
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(BiggerReactors.modid, path);
    }

    public static void register(RegisterDataMapTypesEvent event) {
        event.register(REACTOR_MODERATOR);
        event.register(REACTOR_FLUID_MODERATOR);
        event.register(TURBINE_COIL);
        event.register(FLUID_TRANSITION);
    }
}
