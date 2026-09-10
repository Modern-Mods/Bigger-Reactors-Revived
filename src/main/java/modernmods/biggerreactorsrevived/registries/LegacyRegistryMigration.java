package modernmods.biggerreactorsrevived.registries;

import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import modernmods.biggerreactorsrevived.BiggerReactors;
import modernmods.phosphophylliterevived.parsers.Element;
import modernmods.phosphophylliterevived.parsers.JSON5;
import modernmods.phosphophylliterevived.registry.OnModLoad;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class LegacyRegistryMigration {

    private static final String MODERATOR_PATH = "ebcr/moderators";
    private static final String COIL_PATH = "ebest/coils";
    private static final String TRANSITION_PATH = "transitions";

    private static final List<ScriptedEntry<ReactorModeratorRegistry.ModeratorProperties>> moderatorBlocks = new ArrayList<>();
    private static final List<ScriptedEntry<ReactorModeratorRegistry.ModeratorProperties>> moderatorFluids = new ArrayList<>();
    private static final List<ScriptedEntry<TurbineCoilRegistry.CoilData>> coils = new ArrayList<>();
    private static final List<ScriptedEntry<FluidTransitionRegistry.TransitionData>> transitions = new ArrayList<>();

    private LegacyRegistryMigration() {
    }

    public static synchronized List<ScriptedEntry<ReactorModeratorRegistry.ModeratorProperties>> moderatorBlocks() {
        return moderatorBlocks;
    }

    public static synchronized List<ScriptedEntry<ReactorModeratorRegistry.ModeratorProperties>> moderatorFluids() {
        return moderatorFluids;
    }

    public static synchronized List<ScriptedEntry<TurbineCoilRegistry.CoilData>> coils() {
        return coils;
    }

    public static synchronized List<ScriptedEntry<FluidTransitionRegistry.TransitionData>> transitions() {
        return transitions;
    }

    @OnModLoad
    private static void onModLoad() {
        NeoForge.EVENT_BUS.addListener(LegacyRegistryMigration::addReloadListener);
    }

    private static void addReloadListener(AddServerReloadListenersEvent event) {
        event.addListener(Identifier.fromNamespaceAndPath(BiggerReactors.modid, "legacy_registry_migration"), new SimplePreparableReloadListener<Void>() {
            @Override
            protected Void prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
                load(resourceManager);
                return null;
            }

            @Override
            protected void apply(Void prepared, ResourceManager resourceManager, ProfilerFiller profiler) {
            }

            @Override
            public String getName() {
                return "biggerreactors:legacy_registry_migration";
            }
        });
    }

    public static synchronized void load(ResourceManager resourceManager) {
        moderatorBlocks.clear();
        moderatorFluids.clear();
        coils.clear();
        transitions.clear();

        forEachLegacyFile(resourceManager, MODERATOR_PATH, LegacyRegistryMigration::readModerator);
        forEachLegacyFile(resourceManager, COIL_PATH, LegacyRegistryMigration::readCoil);
        forEachLegacyFile(resourceManager, TRANSITION_PATH, LegacyRegistryMigration::readTransition);

        final int total = moderatorBlocks.size() + moderatorFluids.size() + coils.size() + transitions.size();
        if (total > 0) {
            BiggerReactors.LOGGER.info("Migrated " + total + " legacy registry entries ("
                    + moderatorBlocks.size() + " moderators, " + moderatorFluids.size() + " fluid moderators, "
                    + coils.size() + " coils, " + transitions.size() + " transitions)");
        }
    }

    private interface EntryReader {
        void read(Identifier id, Map<String, Element> values);
    }

    private static void forEachLegacyFile(ResourceManager resourceManager, String path, EntryReader reader) {
        for (final var entry : resourceManager.listResources(path, LegacyRegistryMigration::isLegacyFile).entrySet()) {
            final var id = entry.getKey();
            try (final InputStream stream = entry.getValue().open()) {
                final var contents = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
                final var root = JSON5.parseString(contents);
                if (root == null || root.subArray == null) {
                    continue;
                }
                final var values = new java.util.HashMap<String, Element>();
                for (final var element : root.subArray) {
                    if (element.name != null) {
                        values.put(element.name, element);
                    }
                }
                reader.read(id, values);
            } catch (Exception e) {
                BiggerReactors.LOGGER.error("Failed to migrate legacy entry " + id, e);
            }
        }
    }

    private static boolean isLegacyFile(Identifier id) {
        return id.getPath().endsWith(".json5") || id.getPath().endsWith(".json");
    }

    private static void readModerator(Identifier id, Map<String, Element> values) {
        final var type = string(values, "type", "tag");
        final var location = location(values, "location", type.endsWith("tag"));
        if (location == null) {
            return;
        }
        final var properties = new ReactorModeratorRegistry.ModeratorProperties(
                number(values, "absorption"), number(values, "efficiency"),
                number(values, "moderation"), number(values, "conductivity"));
        final var entry = new ScriptedEntry<>(ScriptedEntry.parseLocation(location), properties);
        if (type.startsWith("fluid")) {
            moderatorFluids.add(entry);
        } else {
            moderatorBlocks.add(entry);
        }
    }

    private static void readCoil(Identifier id, Map<String, Element> values) {
        final var type = string(values, "type", "tag");
        final var location = location(values, "location", type.endsWith("tag"));
        if (location == null) {
            return;
        }
        final var data = new TurbineCoilRegistry.CoilData(
                number(values, "efficiency"), number(values, "bonus"), number(values, "extractionRate"));
        coils.add(new ScriptedEntry<>(ScriptedEntry.parseLocation(location), data));
    }

    private static void readTransition(Identifier id, Map<String, Element> values) {
        final var liquid = location(values, "liquid", string(values, "liquidType", "registry").endsWith("tag"));
        final var gas = location(values, "gas", string(values, "gasType", "registry").endsWith("tag"));
        if (liquid == null || gas == null) {
            return;
        }
        final var data = new FluidTransitionRegistry.TransitionData(
                ScriptedEntry.parseLocation(gas),
                number(values, "latentHeat"), number(values, "boilingPoint"),
                number(values, "liquidThermalConductivity"), number(values, "gasThermalConductivity"),
                number(values, "turbineMultiplier"));
        transitions.add(new ScriptedEntry<>(ScriptedEntry.parseLocation(liquid), data));
    }

    private static String string(Map<String, Element> values, String name, String fallback) {
        final var element = values.get(name);
        return element == null ? fallback : element.asString();
    }

    private static double number(Map<String, Element> values, String name) {
        final var element = values.get(name);
        if (element == null) {
            return 0;
        }
        return element.asDouble();
    }

    @Nullable
    private static String location(Map<String, Element> values, String name, boolean tag) {
        final var element = values.get(name);
        if (element == null) {
            return null;
        }
        var location = element.asString();
        if (location.startsWith("forge:")) {
            location = "c:" + location.substring("forge:".length());
        }
        return tag ? "#" + location : location;
    }
}
