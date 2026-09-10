package modernmods.biggerreactorsrevived.deps.kubejs;

import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.ClassFilter;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import modernmods.biggerreactorsrevived.registries.FluidTransitionRegistry;
import modernmods.biggerreactorsrevived.registries.ReactorModeratorRegistry;
import modernmods.biggerreactorsrevived.registries.TurbineCoilRegistry;

public class BiggerReactorsKubePlugin implements KubeJSPlugin {

    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(BiggerReactorEvents.GROUP);
    }

    @Override
    public void registerClasses(ClassFilter filter) {
        filter.allow("modernmods.biggerreactorsrevived.deps.kubejs");
        filter.allow(ReactorModeratorRegistry.class.getName());
        filter.allow(TurbineCoilRegistry.class.getName());
        filter.allow(FluidTransitionRegistry.class.getName());
    }

}
