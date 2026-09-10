package modernmods.biggerreactorsrevived.deps;

import dan200.computercraft.api.peripheral.PeripheralCapability;
import net.minecraft.core.Direction;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.capabilities.BlockCapability;
import modernmods.phosphophylliterevived.capabilities.PhosphophylliteCapabilities;
import modernmods.phosphophylliterevived.registry.OnModLoad;

public final class ComputerCraftDeps {

    public static final boolean LOADED = FMLLoader.getCurrent().getLoadingModList().getModFileById("computercraft") != null;

    @OnModLoad
    private static void onModLoad() {
        if (LOADED) {
            Holder.register();
        }
    }

    public static BlockCapability<?, Direction> peripheralCapability() {
        return Holder.CAPABILITY;
    }

    private static final class Holder {

        private static final BlockCapability<?, Direction> CAPABILITY = PeripheralCapability.get();

        private static void register() {
            PhosphophylliteCapabilities.registerBlockCapability(CAPABILITY);
        }
    }
}
