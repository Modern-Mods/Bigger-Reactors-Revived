package modernmods.biggerreactorsrevived.deps.kubejs;

import dev.latvian.mods.kubejs.event.KubeEvent;

public abstract class BiggerReactorRegistryKubeEvent implements KubeEvent {

    public abstract void remove(String location);
}
