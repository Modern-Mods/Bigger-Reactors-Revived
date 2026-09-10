package modernmods.biggerreactorsrevived.deps.kubejs;

import modernmods.biggerreactorsrevived.registries.TurbineCoilRegistry;

public class CoilRegistryKubeEvent extends BiggerReactorRegistryKubeEvent {

    public void add(String location, double efficiency, double extractionRate, double bonus) {
        TurbineCoilRegistry.registerBlock(location, new TurbineCoilRegistry.CoilData(efficiency, bonus, extractionRate));
    }

    @Override
    public void remove(String location) {
        TurbineCoilRegistry.removeBlock(location);
    }
}
