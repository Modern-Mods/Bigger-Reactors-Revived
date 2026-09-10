package modernmods.biggerreactorsrevived.multiblocks.reactor.simulation.base;

import modernmods.biggerreactorsrevived.registries.ReactorModeratorRegistry;

public class ModeratorCache {
    private final ReactorModeratorRegistry.IModeratorProperties rootModerator;
    
    public double absorption;
    public double moderation;
    public double heatEfficiency;
    
    public ModeratorCache(ReactorModeratorRegistry.IModeratorProperties rootModerator) {
        this.rootModerator = rootModerator;
    }
    
    public void update() {
        absorption = rootModerator.absorption();
        moderation = rootModerator.moderation() - 1.0;
        heatEfficiency = rootModerator.heatEfficiency();
    }
    
    public ModeratorCache duplicate() {
        return new ModeratorCache(rootModerator);
    }
}
