package modernmods.biggerreactorsrevived.datagen;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import modernmods.biggerreactorsrevived.BiggerReactors;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsBlockStateProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsBlockTagProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsDataMapProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsFluidTagProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsItemModelProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsItemTagProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsLanguageProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsCompatRecipeProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsLootTableProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsQuartzStateProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsRecipeProvider;
import modernmods.biggerreactorsrevived.datagen.providers.BiggerReactorsWorldgenProvider;

@EventBusSubscriber(modid = BiggerReactors.modid, bus = EventBusSubscriber.Bus.MOD)
public final class BiggerReactorsDatagen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        final var generator = event.getGenerator();
        final var output = generator.getPackOutput();
        final var existingFileHelper = event.getExistingFileHelper();
        final var lookupProvider = event.getLookupProvider();

        for (final var locale : BiggerReactorsLanguageProvider.LOCALES) {
            generator.addProvider(event.includeClient(), new BiggerReactorsLanguageProvider(output, locale));
        }
        generator.addProvider(event.includeClient(), new BiggerReactorsBlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new BiggerReactorsItemModelProvider(output, existingFileHelper));

        final var blockTags = new BiggerReactorsBlockTagProvider(output, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new BiggerReactorsItemTagProvider(output, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new BiggerReactorsFluidTagProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), BiggerReactorsLootTableProvider.create(output, lookupProvider));
        generator.addProvider(event.includeServer(), new BiggerReactorsDataMapProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new BiggerReactorsWorldgenProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new BiggerReactorsRecipeProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new BiggerReactorsCompatRecipeProvider(output));
        generator.addProvider(event.includeClient(), new BiggerReactorsQuartzStateProvider(output));
    }
}
