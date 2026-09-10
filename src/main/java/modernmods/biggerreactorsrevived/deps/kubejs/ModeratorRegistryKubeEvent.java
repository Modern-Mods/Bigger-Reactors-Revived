package modernmods.biggerreactorsrevived.deps.kubejs;

import modernmods.biggerreactorsrevived.registries.ReactorModeratorRegistry;

public class ModeratorRegistryKubeEvent extends BiggerReactorRegistryKubeEvent {

    public void add(String location, double absorption, double efficiency, double moderation, double conductivity) {
        ReactorModeratorRegistry.registerBlock(location, new ReactorModeratorRegistry.ModeratorProperties(absorption, efficiency, moderation, conductivity));
    }

    public void addFluid(String location, double absorption, double efficiency, double moderation, double conductivity) {
        ReactorModeratorRegistry.registerFluid(location, new ReactorModeratorRegistry.ModeratorProperties(absorption, efficiency, moderation, conductivity));
    }

    @Override
    public void remove(String location) {
        ReactorModeratorRegistry.removeBlock(location);
    }

    public void removeFluid(String location) {
        ReactorModeratorRegistry.removeFluid(location);
    }
}
