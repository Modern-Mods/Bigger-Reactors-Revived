package modernmods.biggerreactorsrevived;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import modernmods.biggerreactorsrevived.machine.client.CyaniteReprocessorScreen;
import modernmods.biggerreactorsrevived.machine.containers.CyaniteReprocessorContainer;
import modernmods.biggerreactorsrevived.multiblocks.heatexchanger.client.HeatExchangerFluidPortScreen;
import modernmods.biggerreactorsrevived.multiblocks.heatexchanger.client.HeatExchangerTerminalScreen;
import modernmods.biggerreactorsrevived.multiblocks.heatexchanger.containers.HeatExchangerFluidPortContainer;
import modernmods.biggerreactorsrevived.multiblocks.heatexchanger.containers.HeatExchangerTerminalContainer;
import modernmods.biggerreactorsrevived.multiblocks.reactor.client.*;
import modernmods.biggerreactorsrevived.multiblocks.reactor.containers.*;
import modernmods.biggerreactorsrevived.multiblocks.turbine.client.TurbineFluidPortScreen;
import modernmods.biggerreactorsrevived.multiblocks.turbine.client.TurbineTerminalScreen;
import modernmods.biggerreactorsrevived.multiblocks.turbine.containers.TurbineFluidPortContainer;
import modernmods.biggerreactorsrevived.multiblocks.turbine.containers.TurbineTerminalContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import modernmods.biggerreactorsrevived.deps.kubejs.KubeJSDeps;
import modernmods.biggerreactorsrevived.registries.BiggerReactorsDataMaps;
import modernmods.biggerreactorsrevived.registries.FluidTransitionRegistry;
import modernmods.biggerreactorsrevived.registries.ReactorModeratorRegistry;
import modernmods.biggerreactorsrevived.registries.TurbineCoilRegistry;
import modernmods.phosphophylliterevived.Phosphophyllite;
import modernmods.phosphophylliterevived.event.ReloadDataEvent;
import modernmods.phosphophylliterevived.registry.Registry;
import modernmods.quartzrevived.Quartz;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
@Mod(BiggerReactors.modid)
public class BiggerReactors {

    public static final String modid = "biggerreactors";

    public static final Logger LOGGER = LogManager.getLogger();
    public static final boolean LOG_DEBUG = LOGGER.isDebugEnabled();
    
    public BiggerReactors(IEventBus modBus) {
        new Registry(modid, CreativeTabOrder.before(), CreativeTabOrder.after());
        if (FMLLoader.getDist().isClient()) {
            modBus.addListener(ClientScreens::onRegisterMenuScreens);
        }
        modBus.addListener(BiggerReactorsDataMaps::register);
        modBus.addListener(this::onCommonSetup);
        NeoForge.EVENT_BUS.addListener(this::onReloadData);
        version = FMLLoader.getLoadingModList().getModFileById(modid).versionString();
    }
    
    public void onCommonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(KubeJSDeps::postRegistryEvents);
    }

    public void onReloadData(final ReloadDataEvent reloadDataEvent) {
        ReactorModeratorRegistry.loadRegistry();
        TurbineCoilRegistry.loadRegistry();
        FluidTransitionRegistry.loadRegistry();
    }

    @OnlyIn(Dist.CLIENT)
    private static final class ClientScreens {
        private static void onRegisterMenuScreens(final RegisterMenuScreensEvent e) {
            // TODO: 6/28/20 Registry.
            //  Since I already have the comment here, also need to do a capability registry. I have a somewhat dumb capability to register.
            e.register(CyaniteReprocessorContainer.INSTANCE,
                    CyaniteReprocessorScreen::new);
            e.register(ReactorTerminalContainer.INSTANCE,
                    CommonReactorTerminalScreen::new);
            e.register(ReactorCoolantPortContainer.INSTANCE,
                    ReactorCoolantPortScreen::new);
            e.register(ReactorAccessPortContainer.INSTANCE,
                    ReactorAccessPortScreen::new);
            e.register(ReactorControlRodContainer.INSTANCE,
                    ReactorControlRodScreen::new);
            e.register(ReactorRedstonePortContainer.INSTANCE,
                    ReactorRedstonePortScreen::new);
            e.register(TurbineTerminalContainer.INSTANCE,
                    TurbineTerminalScreen::new);
            e.register(TurbineFluidPortContainer.INSTANCE,
                    TurbineFluidPortScreen::new);
            e.register(HeatExchangerTerminalContainer.INSTANCE,
                    HeatExchangerTerminalScreen::new);
            e.register(HeatExchangerFluidPortContainer.INSTANCE,
                    HeatExchangerFluidPortScreen::new);
        }
    }
    
    private static String version;
    public static String modVersion(){
        return version;
    }
}
