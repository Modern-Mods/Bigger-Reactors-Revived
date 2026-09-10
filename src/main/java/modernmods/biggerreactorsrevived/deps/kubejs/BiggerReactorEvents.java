package modernmods.biggerreactorsrevived.deps.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventTargetType;
import dev.latvian.mods.kubejs.event.TargetedEventHandler;

public interface BiggerReactorEvents {

    EventGroup GROUP = EventGroup.of("BiggerReactorEvents");

    TargetedEventHandler<String> REGISTRY = GROUP.startup("registry", () -> BiggerReactorRegistryKubeEvent.class)
            .requiredTarget(EventTargetType.STRING);
}
