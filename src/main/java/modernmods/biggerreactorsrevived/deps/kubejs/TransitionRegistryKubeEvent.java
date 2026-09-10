package modernmods.biggerreactorsrevived.deps.kubejs;

import modernmods.biggerreactorsrevived.registries.FluidTransitionRegistry;

public class TransitionRegistryKubeEvent extends BiggerReactorRegistryKubeEvent {

    public void add(String liquid, String gas, double latentHeat, double boilingPoint, double liquidThermalConductivity, double gasThermalConductivity, double turbineMultiplier) {
        FluidTransitionRegistry.registerTransition(liquid, gas, latentHeat, boilingPoint, liquidThermalConductivity, gasThermalConductivity, turbineMultiplier);
    }

    @Override
    public void remove(String location) {
        FluidTransitionRegistry.removeTransition(location);
    }
}
