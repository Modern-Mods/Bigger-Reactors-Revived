package modernmods.biggerreactorsrevived.registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import modernmods.biggerreactorsrevived.BiggerReactors;
import modernmods.biggerreactorsrevived.Config;
import modernmods.phosphophylliterevived.registry.OnModLoad;

import net.minecraft.util.ExtraCodecs;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TurbineCoilRegistry {

    public static class CoilData {

        public static final Codec<CoilData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.DOUBLE.fieldOf("efficiency").forGetter(data -> data.efficiency),
                Codec.DOUBLE.fieldOf("extractionRate").forGetter(data -> data.extractionRate),
                Codec.DOUBLE.fieldOf("bonus").forGetter(data -> data.bonus)
        ).apply(instance, (efficiency, extractionRate, bonus) -> new CoilData(efficiency, bonus, extractionRate)));

        public final double efficiency;
        public final double bonus;
        public final double extractionRate;

        public CoilData(double efficiency, double bonus, double extractionRate) {
            this.efficiency = efficiency;
            this.bonus = bonus;
            this.extractionRate = extractionRate;
        }
    }

    private static final Object2ObjectLinkedOpenHashMap<Block, CoilData> registry = new Object2ObjectLinkedOpenHashMap<>();
    private static final List<ScriptedEntry<CoilData>> scriptedBlocks = new ArrayList<>();
    private static final List<ExtraCodecs.TagOrElementLocation> scriptedRemovals = new ArrayList<>();

    public static synchronized boolean isBlockAllowed(Block block) {
        return registry.containsKey(block);
    }

    @Nullable
    public static synchronized CoilData getCoilData(Block block) {
        return registry.get(block);
    }

    public static synchronized void registerBlock(String location, CoilData data) {
        scriptedBlocks.add(new ScriptedEntry<>(ScriptedEntry.parseLocation(location), data));
    }

    public static synchronized void removeBlock(String location) {
        scriptedRemovals.add(ScriptedEntry.parseLocation(location));
    }

    @OnModLoad
    private static void onModLoad() {
        NeoForge.EVENT_BUS.addListener(TurbineCoilRegistry::tagsUpdated);
        if (FMLEnvironment.getDist().isClient()) {
            NeoForge.EVENT_BUS.addListener(Client::toolTipEvent);
        }
    }

    private static void tagsUpdated(TagsUpdatedEvent event) {
        loadRegistry();
    }

    public static synchronized void loadRegistry() {
        registry.clear();

        for (final var entry : BuiltInRegistries.BLOCK.getDataMap(BiggerReactorsDataMaps.TURBINE_COIL).entrySet()) {
            registry.put(BuiltInRegistries.BLOCK.getValue(entry.getKey()), entry.getValue());
        }

        for (final var entry : LegacyRegistryMigration.coils()) {
            for (final var block : ScriptedEntry.resolve(BuiltInRegistries.BLOCK, entry.location())) {
                registry.put(block, entry.value());
            }
        }

        for (final var entry : scriptedBlocks) {
            for (final var block : ScriptedEntry.resolve(BuiltInRegistries.BLOCK, entry.location())) {
                registry.put(block, entry.value());
            }
        }
        for (final var removal : scriptedRemovals) {
            ScriptedEntry.resolve(BuiltInRegistries.BLOCK, removal).forEach(registry::remove);
        }

        BiggerReactors.LOGGER.info("Loaded " + registry.size() + " coil entries");
    }

    public static class Client {

        public static void toolTipEvent(ItemTooltipEvent event) {
            final var item = event.getItemStack().getItem();
            if (item instanceof BlockItem blockItem) {
                if (!registry.containsKey(blockItem.getBlock())) {
                    return;
                }
            } else {
                return;
            }
            if (Minecraft.getInstance().options.advancedItemTooltips || Config.CONFIG.AlwaysShowTooltips) {
                event.getToolTip().add(Component.translatable("tooltip.biggerreactors.is_a_coil"));
            }
        }

        public static Map<Block, CoilData> getImmutableRegistry() {
            return Collections.unmodifiableMap(registry);
        }
    }
}
