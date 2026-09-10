package modernmods.biggerreactorsrevived.datagen;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import modernmods.biggerreactorsrevived.BiggerReactors;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsBlockTagProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsDataMapProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsFluidTagProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsItemTagProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsLanguageProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsCompatRecipeProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsLootTableProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsModelProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsQuartzStateProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsRecipeProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsWorldgenProvider;

@EventBusSubscriber(modid = BiggerReactors.modid)
public final class BiggerReactorsDatagen {

    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        for (final var locale : BiggerReactorsLanguageProvider.LOCALES) {
            event.createProvider(output -> new BiggerReactorsLanguageProvider(output, locale));
        }
        event.createProvider(BiggerReactorsModelProvider::new);
        event.createProvider(BiggerReactorsQuartzStateProvider::new);
    }

    @SubscribeEvent
    public static void gatherServerData(GatherDataEvent.Server event) {
        final var blockTags = event.createProvider(BiggerReactorsBlockTagProvider::new);
        event.createProvider((output, lookupProvider) -> new BiggerReactorsItemTagProvider(output, lookupProvider, blockTags.contentsGetter()));
        event.createProvider(BiggerReactorsFluidTagProvider::new);
        event.createProvider(BiggerReactorsLootTableProvider::create);
        event.createProvider(BiggerReactorsDataMapProvider::new);
        event.createProvider(BiggerReactorsWorldgenProvider::new);
        event.createProvider(BiggerReactorsRecipeProvider.Runner::new);
        event.createProvider(BiggerReactorsCompatRecipeProvider::new);
    }
}
