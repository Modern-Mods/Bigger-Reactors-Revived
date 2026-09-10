package modernmods.biggerreactorsrevived.registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import modernmods.biggerreactorsrevived.BiggerReactors;
import modernmods.biggerreactorsrevived.Config;
import modernmods.phosphophylliterevived.registry.OnModLoad;
import modernmods.phosphophylliterevived.robn.ROBNObject;
import org.apache.commons.lang3.NotImplementedException;

import net.minecraft.util.ExtraCodecs;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class ReactorModeratorRegistry {

    public interface IModeratorProperties extends ROBNObject {
        double absorption();

        double heatEfficiency();

        double moderation();

        double heatConductivity();

        @Override
        default Map<String, Object> toROBNMap() {
            final Map<String, Object> map = new HashMap<>();
            map.put("absorption", absorption());
            map.put("heatEfficiency", heatEfficiency());
            map.put("moderation", moderation());
            map.put("heatConductivity", heatConductivity());
            return map;
        }

        @Override
        default void fromROBNMap(Map<String, Object> map) {
            throw new NotImplementedException("");
        }
    }

    public static class ModeratorProperties implements IModeratorProperties, ROBNObject {

        public static final ModeratorProperties EMPTY_MODERATOR = new ModeratorProperties(0, 0, 1, 0);

        public static final Codec<ModeratorProperties> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.DOUBLE.fieldOf("absorption").forGetter(ModeratorProperties::absorption),
                Codec.DOUBLE.fieldOf("efficiency").forGetter(ModeratorProperties::heatEfficiency),
                Codec.DOUBLE.fieldOf("moderation").forGetter(ModeratorProperties::moderation),
                Codec.DOUBLE.fieldOf("conductivity").forGetter(ModeratorProperties::heatConductivity)
        ).apply(instance, ModeratorProperties::new));

        public final double absorption;
        public final double heatEfficiency;
        public final double moderation;
        public final double heatConductivity;

        public ModeratorProperties(double absorption, double heatEfficiency, double moderation, double heatConductivity) {
            this.absorption = absorption;
            this.heatEfficiency = heatEfficiency;
            this.moderation = moderation;
            this.heatConductivity = heatConductivity;
        }

        public ModeratorProperties(IModeratorProperties properties) {
            this(properties.absorption(), properties.heatEfficiency(), properties.moderation(), properties.heatConductivity());
        }

        @Override
        public double absorption() {
            return absorption;
        }

        @Override
        public double heatEfficiency() {
            return heatEfficiency;
        }

        @Override
        public double moderation() {
            return moderation;
        }

        @Override
        public double heatConductivity() {
            return heatConductivity;
        }
    }

    private static final Object2ObjectLinkedOpenHashMap<Block, ModeratorProperties> registry = new Object2ObjectLinkedOpenHashMap<>();
    private static final List<ScriptedEntry<ModeratorProperties>> scriptedBlocks = new ArrayList<>();
    private static final List<ScriptedEntry<ModeratorProperties>> scriptedFluids = new ArrayList<>();
    private static final List<ExtraCodecs.TagOrElementLocation> scriptedBlockRemovals = new ArrayList<>();
    private static final List<ExtraCodecs.TagOrElementLocation> scriptedFluidRemovals = new ArrayList<>();

    public static boolean isBlockAllowed(Block block) {
        return registry.containsKey(block);
    }

    @Nullable
    public static ModeratorProperties blockModeratorProperties(Block block) {
        return registry.get(block);
    }

    public static synchronized void registerBlock(String location, IModeratorProperties properties) {
        scriptedBlocks.add(new ScriptedEntry<>(ScriptedEntry.parseLocation(location), new ModeratorProperties(properties)));
    }

    public static synchronized void registerFluid(String location, IModeratorProperties properties) {
        scriptedFluids.add(new ScriptedEntry<>(ScriptedEntry.parseLocation(location), new ModeratorProperties(properties)));
    }

    public static synchronized void removeBlock(String location) {
        scriptedBlockRemovals.add(ScriptedEntry.parseLocation(location));
    }

    public static synchronized void removeFluid(String location) {
        scriptedFluidRemovals.add(ScriptedEntry.parseLocation(location));
    }

    @OnModLoad
    private static void onModLoad() {
        NeoForge.EVENT_BUS.addListener(ReactorModeratorRegistry::tagsUpdated);
        if (FMLEnvironment.dist.isClient()) {
            NeoForge.EVENT_BUS.addListener(Client::toolTipEvent);
        }
    }

    private static void tagsUpdated(TagsUpdatedEvent event) {
        loadRegistry();
    }

    public static synchronized void loadRegistry() {
        registry.clear();

        for (final var entry : BuiltInRegistries.BLOCK.getDataMap(BiggerReactorsDataMaps.REACTOR_MODERATOR).entrySet()) {
            registry.put(BuiltInRegistries.BLOCK.get(entry.getKey()), entry.getValue());
        }

        for (final var entry : BuiltInRegistries.FLUID.getDataMap(BiggerReactorsDataMaps.REACTOR_FLUID_MODERATOR).entrySet()) {
            final var fluid = BuiltInRegistries.FLUID.get(entry.getKey());
            registry.put(fluid.defaultFluidState().createLegacyBlock().getBlock(), entry.getValue());
        }

        for (final var entry : LegacyRegistryMigration.moderatorBlocks()) {
            for (final var block : ScriptedEntry.resolve(BuiltInRegistries.BLOCK, entry.location())) {
                registry.put(block, entry.value());
            }
        }
        for (final var entry : LegacyRegistryMigration.moderatorFluids()) {
            for (final var fluid : ScriptedEntry.resolve(BuiltInRegistries.FLUID, entry.location())) {
                registry.put(fluid.defaultFluidState().createLegacyBlock().getBlock(), entry.value());
            }
        }

        for (final var entry : scriptedBlocks) {
            for (final var block : ScriptedEntry.resolve(BuiltInRegistries.BLOCK, entry.location())) {
                registry.put(block, entry.value());
            }
        }
        for (final var entry : scriptedFluids) {
            for (final var fluid : ScriptedEntry.resolve(BuiltInRegistries.FLUID, entry.location())) {
                registry.put(fluid.defaultFluidState().createLegacyBlock().getBlock(), entry.value());
            }
        }
        for (final var removal : scriptedBlockRemovals) {
            ScriptedEntry.resolve(BuiltInRegistries.BLOCK, removal).forEach(registry::remove);
        }
        for (final var removal : scriptedFluidRemovals) {
            for (final var fluid : ScriptedEntry.resolve(BuiltInRegistries.FLUID, removal)) {
                registry.remove(fluid.defaultFluidState().createLegacyBlock().getBlock());
            }
        }

        BiggerReactors.LOGGER.info("Loaded " + registry.size() + " moderator entries");
    }

    public static class Client {

        public static void toolTipEvent(ItemTooltipEvent event) {
            final var item = event.getItemStack().getItem();
            if (item instanceof BlockItem blockItem) {
                if (!registry.containsKey(blockItem.getBlock())) {
                    return;
                }
            } else if (item instanceof BucketItem bucketItem) {
                final var fluidBlock = bucketItem.content.defaultFluidState().createLegacyBlock().getBlock();
                if (fluidBlock.defaultBlockState().isAir() || !registry.containsKey(fluidBlock)) {
                    return;
                }
            } else {
                return;
            }
            if (Minecraft.getInstance().options.advancedItemTooltips || Config.CONFIG.AlwaysShowTooltips) {
                event.getToolTip().add(Component.translatable("tooltip.biggerreactors.is_a_moderator"));
            }
        }

        public static void forEach(BiConsumer<Block, IModeratorProperties> consumer) {
            registry.forEach(consumer);
        }
    }
}
