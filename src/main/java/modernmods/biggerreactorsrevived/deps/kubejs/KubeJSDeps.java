package modernmods.biggerreactorsrevived.deps.kubejs;

import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;

public final class KubeJSDeps {

    public static final boolean LOADED = FMLLoader.getCurrent().getLoadingModList().getModFileById("kubejs") != null;

    private KubeJSDeps() {
    }

    public static void postRegistryEvents() {
        if (LOADED) {
            Poster.post();
        }
    }

    private static final class Poster {

        private static final String[] MODERATOR_TARGETS = {"moderators", "moderator", "moderadores", "moderador"};
        private static final String[] COIL_TARGETS = {"coils", "coil", "bobinas", "bobina"};
        private static final String[] FLUID_TARGETS = {"fluids", "fluid", "fluidos", "fluido"};
        private static final String[] TRANSITION_TARGETS = {"transitions", "transition", "transiciones", "transicion"};

        private static void post() {
            final var moderators = new ModeratorRegistryKubeEvent();
            for (final var target : MODERATOR_TARGETS) {
                BiggerReactorEvents.REGISTRY.post(moderators, target);
            }
            final var coils = new CoilRegistryKubeEvent();
            for (final var target : COIL_TARGETS) {
                BiggerReactorEvents.REGISTRY.post(coils, target);
            }
            final var fluids = new FluidModeratorRegistryKubeEvent();
            for (final var target : FLUID_TARGETS) {
                BiggerReactorEvents.REGISTRY.post(fluids, target);
            }
            final var transitions = new TransitionRegistryKubeEvent();
            for (final var target : TRANSITION_TARGETS) {
                BiggerReactorEvents.REGISTRY.post(transitions, target);
            }
        }
    }
}
