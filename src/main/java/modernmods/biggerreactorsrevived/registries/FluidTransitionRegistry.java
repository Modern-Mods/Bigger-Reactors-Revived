package modernmods.biggerreactorsrevived.registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import modernmods.biggerreactorsrevived.BiggerReactors;
import modernmods.phosphophylliterevived.registry.OnModLoad;
import modernmods.phosphophylliterevived.robn.ROBNObject;
import org.apache.commons.lang3.NotImplementedException;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ParametersAreNonnullByDefault
public class FluidTransitionRegistry {

    public interface ITransitionProperties extends ROBNObject {
        double latentHeat();

        double boilingPoint();

        double liquidRFMKT();

        double gasRFMKT();

        double turbineMultiplier();

        @Override
        default Map<String, Object> toROBNMap() {
            final Map<String, Object> map = new HashMap<>();
            map.put("latentHeat", latentHeat());
            map.put("boilingPoint", boilingPoint());
            map.put("liquidRFMKT", liquidRFMKT());
            map.put("gasRFMKT", gasRFMKT());
            map.put("turbineMultiplier", turbineMultiplier());
            return map;
        }

        @Override
        default void fromROBNMap(Map<String, Object> map) {
            throw new NotImplementedException("");
        }
    }

    public record TransitionData(ExtraCodecs.TagOrElementLocation gas, double latentHeat, double boilingPoint,
                                 double liquidThermalConductivity, double gasThermalConductivity,
                                 double turbineMultiplier) {

        public static final Codec<TransitionData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ExtraCodecs.TAG_OR_ELEMENT_ID.fieldOf("gas").forGetter(TransitionData::gas),
                Codec.DOUBLE.fieldOf("latentHeat").forGetter(TransitionData::latentHeat),
                Codec.DOUBLE.fieldOf("boilingPoint").forGetter(TransitionData::boilingPoint),
                Codec.DOUBLE.fieldOf("liquidThermalConductivity").forGetter(TransitionData::liquidThermalConductivity),
                Codec.DOUBLE.fieldOf("gasThermalConductivity").forGetter(TransitionData::gasThermalConductivity),
                Codec.DOUBLE.fieldOf("turbineMultiplier").forGetter(TransitionData::turbineMultiplier)
        ).apply(instance, TransitionData::new));
    }

    public static class FluidTransition implements ITransitionProperties {
        public final List<Fluid> liquids;
        public final List<Fluid> gases;
        public final double latentHeat;
        public final double boilingPoint;
        public final double liquidRFMKT;
        public final double gasRFMKT;
        public final double turbineMultiplier;

        public FluidTransition(List<Fluid> liquids, List<Fluid> gases, double latentHeat, double boilingPoint, double liquidRFMKT, double gasRFMKT, double turbineMultiplier) {
            this.liquids = Collections.unmodifiableList(liquids);
            this.gases = Collections.unmodifiableList(gases);
            this.latentHeat = latentHeat;
            this.boilingPoint = boilingPoint;
            this.liquidRFMKT = liquidRFMKT;
            this.gasRFMKT = gasRFMKT;
            this.turbineMultiplier = turbineMultiplier;
        }

        @Override
        public double latentHeat() {
            return latentHeat;
        }

        @Override
        public double boilingPoint() {
            return boilingPoint;
        }

        @Override
        public double liquidRFMKT() {
            return liquidRFMKT;
        }

        @Override
        public double gasRFMKT() {
            return gasRFMKT;
        }

        @Override
        public double turbineMultiplier() {
            return turbineMultiplier;
        }
    }

    private static final Map<Fluid, FluidTransition> liquidTransitions = new HashMap<>();
    private static final Map<Fluid, FluidTransition> gasTransitions = new HashMap<>();
    private static final List<ScriptedEntry<TransitionData>> scriptedTransitions = new ArrayList<>();
    private static final List<ExtraCodecs.TagOrElementLocation> scriptedRemovals = new ArrayList<>();

    @Nullable
    public static FluidTransition liquidTransition(Fluid liquid) {
        return liquidTransitions.get(liquid);
    }

    @Nullable
    public static FluidTransition gasTransition(Fluid gas) {
        return gasTransitions.get(gas);
    }

    public static synchronized void removeTransition(String location) {
        scriptedRemovals.add(ScriptedEntry.parseLocation(location));
    }

    public static synchronized void registerTransition(String liquid, String gas, double latentHeat, double boilingPoint, double liquidThermalConductivity, double gasThermalConductivity, double turbineMultiplier) {
        scriptedTransitions.add(new ScriptedEntry<>(ScriptedEntry.parseLocation(liquid),
                new TransitionData(ScriptedEntry.parseLocation(gas), latentHeat, boilingPoint, liquidThermalConductivity, gasThermalConductivity, turbineMultiplier)));
    }

    @OnModLoad
    private static void onModLoad() {
        NeoForge.EVENT_BUS.addListener(FluidTransitionRegistry::tagsUpdated);
    }

    private static void tagsUpdated(TagsUpdatedEvent event) {
        loadRegistry();
    }

    public static synchronized void loadRegistry() {
        liquidTransitions.clear();
        gasTransitions.clear();

        final var grouped = new LinkedHashMap<TransitionData, List<Fluid>>();
        for (final var entry : BuiltInRegistries.FLUID.getDataMap(BiggerReactorsDataMaps.FLUID_TRANSITION).entrySet()) {
            final var liquid = BuiltInRegistries.FLUID.getValue(entry.getKey());
            if (!liquid.isSource(liquid.defaultFluidState())) {
                continue;
            }
            grouped.computeIfAbsent(entry.getValue(), k -> new ArrayList<>()).add(liquid);
        }

        for (final var group : grouped.entrySet()) {
            final var data = group.getKey();
            final var gases = resolveGases(data.gas());
            if (gases.isEmpty()) {
                continue;
            }
            addTransition(new FluidTransition(group.getValue(), gases, data.latentHeat(), data.boilingPoint(),
                    data.liquidThermalConductivity(), data.gasThermalConductivity(), data.turbineMultiplier()));
        }

        for (final var legacy : LegacyRegistryMigration.transitions()) {
            final var liquids = ScriptedEntry.resolve(BuiltInRegistries.FLUID, legacy.location());
            final var gases = resolveGases(legacy.value().gas());
            if (liquids.isEmpty() || gases.isEmpty()) {
                continue;
            }
            final var data = legacy.value();
            addTransition(new FluidTransition(liquids, gases, data.latentHeat(), data.boilingPoint(),
                    data.liquidThermalConductivity(), data.gasThermalConductivity(), data.turbineMultiplier()));
        }

        for (final var scripted : scriptedTransitions) {
            final var liquids = ScriptedEntry.resolve(BuiltInRegistries.FLUID, scripted.location());
            final var gases = resolveGases(scripted.value().gas());
            if (liquids.isEmpty() || gases.isEmpty()) {
                continue;
            }
            final var data = scripted.value();
            addTransition(new FluidTransition(liquids, gases, data.latentHeat(), data.boilingPoint(),
                    data.liquidThermalConductivity(), data.gasThermalConductivity(), data.turbineMultiplier()));
        }

        for (final var removal : scriptedRemovals) {
            for (final var fluid : ScriptedEntry.resolve(BuiltInRegistries.FLUID, removal)) {
                final var removed = liquidTransitions.remove(fluid);
                if (removed != null) {
                    removed.gases.forEach(gasTransitions::remove);
                }
            }
        }

        BiggerReactors.LOGGER.info("Loaded " + liquidTransitions.size() + " liquid transition entries");
        BiggerReactors.LOGGER.info("Loaded " + gasTransitions.size() + " gas transition entries");
    }

    private static List<Fluid> resolveGases(ExtraCodecs.TagOrElementLocation gas) {
        final var gases = new ArrayList<Fluid>();
        if (gas.tag()) {
            BuiltInRegistries.FLUID.get(TagKey.create(BuiltInRegistries.FLUID.key(), gas.id())).ifPresent(holders -> holders.forEach(holder -> {
                final var fluid = holder.value();
                if (fluid.isSource(fluid.defaultFluidState())) {
                    gases.add(fluid);
                }
            }));
        } else if (BuiltInRegistries.FLUID.containsKey(gas.id())) {
            final var fluid = BuiltInRegistries.FLUID.getValue(gas.id());
            if (fluid.isSource(fluid.defaultFluidState())) {
                gases.add(fluid);
            }
        }
        return gases;
    }

    private static void addTransition(FluidTransition transition) {
        for (final var liquid : transition.liquids) {
            if (liquidTransitions.put(liquid, transition) != null) {
                BiggerReactors.LOGGER.error("Duplicate transitions given for liquid fluid " + BuiltInRegistries.FLUID.getKey(liquid));
            }
        }
        for (final var gas : transition.gases) {
            if (gasTransitions.put(gas, transition) != null) {
                BiggerReactors.LOGGER.error("Duplicate transitions given for gas fluid " + BuiltInRegistries.FLUID.getKey(gas));
            }
        }
    }
}
