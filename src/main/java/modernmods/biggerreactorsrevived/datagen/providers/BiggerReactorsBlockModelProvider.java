package modernmods.biggerreactorsrevived.datagen.providers;

import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.client.data.models.model.ModelInstance;
import modernmods.biggerreactorsrevived.BiggerReactors;

import java.util.LinkedHashMap;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.function.BiConsumer;

public final class BiggerReactorsBlockModelProvider {

    public static final String TRANSPARENT = "phosphophyllite:block/transparent";
    private static final String CUTOUT = "minecraft:cutout";

    private static final String[] CUBE_ALL_BLOCKS = {
            "blutonium_block",
            "cyanite_block",
            "graphite_block",
            "ludicrite_block",
            "raw_uranium_block",
            "uranium_block",
            "uranium_ore",
            "deepslate_uranium_ore",
    };

    private static final String[][] CUBE_ALL_PARTS = {
            {"heat_exchanger/casing/corner", "block/heat_exchanger/casing/corner"},
            {"heat_exchanger/casing/disassembled", "block/heat_exchanger/casing/disassembled"},
            {"heat_exchanger/casing/face", "block/heat_exchanger/casing/face"},
            {"heat_exchanger/computer_port", "block/heat_exchanger/computer_port"},
            {"heat_exchanger/fluid_port/condenser_inlet", "block/heat_exchanger/fluid_port/condenser_inlet"},
            {"heat_exchanger/fluid_port/condenser_outlet", "block/heat_exchanger/fluid_port/condenser_outlet"},
            {"heat_exchanger/fluid_port/evaporator_inlet", "block/heat_exchanger/fluid_port/evaporator_inlet"},
            {"heat_exchanger/fluid_port/evaporator_outlet", "block/heat_exchanger/fluid_port/evaporator_outlet"},
            {"heat_exchanger/glass", "block/heat_exchanger/glass/glass"},
            {"heat_exchanger/terminal/active", "block/heat_exchanger/terminal/active"},
            {"heat_exchanger/terminal/idle", "block/heat_exchanger/terminal/idle"},
            {"heat_exchanger/terminal/off", "block/heat_exchanger/terminal/off"},
            {"reactor/access_port/inlet", "block/reactor/access_port/inlet"},
            {"reactor/access_port/outlet", "block/reactor/access_port/outlet"},
            {"reactor/casing/corner", "block/reactor/casing/corner"},
            {"reactor/casing/disassembled", "block/reactor/casing/disassembled"},
            {"reactor/casing/face", "block/reactor/casing/face"},
            {"reactor/computer_port", "block/reactor/computer_port"},
            {"reactor/coolant_port/inlet", "block/reactor/coolant_port/inlet"},
            {"reactor/coolant_port/outlet", "block/reactor/coolant_port/outlet"},
            {"reactor/power_tap/connected", "block/reactor/power_tap/connected"},
            {"reactor/power_tap/disassembled", "block/reactor/power_tap/disassembled"},
            {"reactor/power_tap/disconnected", "block/reactor/power_tap/disconnected"},
            {"reactor/redstone_port/lit", "block/reactor/redstone_port/lit"},
            {"reactor/redstone_port/unlit", "block/reactor/redstone_port/unlit"},
            {"reactor/terminal/active", "block/reactor/terminal/active"},
            {"reactor/terminal/idle", "block/reactor/terminal/idle"},
            {"reactor/terminal/off", "block/reactor/terminal/off"},
            {"turbine/casing/corner", "block/turbine/casing/corner"},
            {"turbine/casing/disassembled", "block/turbine/casing/disassembled"},
            {"turbine/casing/face", "block/turbine/casing/face"},
            {"turbine/computer_port", "block/turbine/computer_port"},
            {"turbine/fluid_port/inlet", "block/turbine/fluid_port/inlet"},
            {"turbine/fluid_port/outlet", "block/turbine/fluid_port/outlet"},
            {"turbine/power_tap/connected", "block/turbine/power_tap/connected"},
            {"turbine/power_tap/disassembled", "block/turbine/power_tap/disassembled"},
            {"turbine/power_tap/disconnected", "block/turbine/power_tap/disconnected"},
            {"turbine/rotor_bearing", "block/turbine/rotor/bearing"},
            {"turbine/terminal/active", "block/turbine/terminal/active"},
            {"turbine/terminal/idle", "block/turbine/terminal/idle"},
            {"turbine/terminal/off", "block/turbine/terminal/off"},
    };

    private static final Map<Direction, Map<Direction, Character>> FACE_EDGES = buildFaceEdges();

    private BiggerReactorsBlockModelProvider() {
    }

    public static void registerModels(BiConsumer<Identifier, ModelInstance> models) {
        for (final var name : CUBE_ALL_BLOCKS) {
            models.accept(modLocation("block/" + name), ModelDsl.cubeAll(modLocation("block/" + name)));
        }
        for (final var entry : CUBE_ALL_PARTS) {
            models.accept(modLocation("block/" + entry[0]), ModelDsl.cubeAll(modLocation(entry[1])));
        }

        models.accept(modLocation("block/cyanite_reprocessor"), ModelDsl.orientable(
                modLocation("block/reactor/casing/disassembled"),
                modLocation("block/cyanite_reprocessor"),
                modLocation("block/reactor/casing/disassembled")));
        models.accept(modLocation("block/cyanite_reprocessor_active"), ModelDsl.orientable(
                modLocation("block/reactor/casing/disassembled"),
                modLocation("block/cyanite_reprocessor_active"),
                modLocation("block/reactor/casing/disassembled")));

        singleFace(models, "block/reactor/control_rod", Direction.UP,
                "block/reactor/control_rod", "block/reactor/casing/disassembled", "block/reactor/casing/disassembled", null);

        for (final var direction : Direction.values()) {
            singleFace(models, "block/reactor/coolant_port/inlet_" + direction.getSerializedName(), direction,
                    "block/reactor/coolant_port/inlet_face", "block/reactor/casing/face", "block/reactor/casing/disassembled", null);
            singleFace(models, "block/reactor/terminal/idle_" + direction.getSerializedName(), direction,
                    "block/reactor/terminal/idle", "block/reactor/casing/face", "block/reactor/casing/disassembled", null);
        }

        fuelRod(models, "block/reactor/fuel_rod", "block/reactor/fuel_rod");
        fuelRod(models, "block/reactor/fuel_rod/copper", "block/reactor/fuel_rod");
        fuelRod(models, "block/reactor/fuel_rod/gold", "block/reactor/fuel_rod/gold");
        fuelRod(models, "block/reactor/fuel_rod/iron", "block/reactor/fuel_rod/iron");

        connectedTexture(models, "block/reactor/glass", "block/reactor/glass/connected_", "block/reactor/glass/connected_none");
        connectedTexture(models, "block/reactor/manifold", "block/reactor/manifold/connected_", "block/reactor/manifold/connected_none");
        connectedTexture(models, "block/turbine/glass", "block/turbine/glass/connected_", "block/turbine/glass/connected_none");
        connectedTexture(models, "block/heat_exchanger/glass", "block/heat_exchanger/glass/glass_connected_", "block/heat_exchanger/glass/glass");
    }

    private static Identifier modLocation(String path) {
        return Identifier.fromNamespaceAndPath(BiggerReactors.modid, path);
    }

    private static void fuelRod(BiConsumer<Identifier, ModelInstance> models, String name, String texturePath) {
        final var textures = new LinkedHashMap<String, Identifier>();
        textures.put("particle", modLocation(texturePath + "/end"));
        textures.put("up", modLocation(texturePath + "/end"));
        textures.put("down", modLocation(texturePath + "/end"));
        textures.put("north", modLocation(texturePath + "/side"));
        textures.put("south", modLocation(texturePath + "/side"));
        textures.put("east", modLocation(texturePath + "/side"));
        textures.put("west", modLocation(texturePath + "/side"));
        models.accept(modLocation(name), ModelDsl.cube(textures, CUTOUT));
    }

    private static void singleFace(BiConsumer<Identifier, ModelInstance> models, String name, Direction face, String faceTexture, String otherTexture, String particleTexture, String renderType) {
        final var textures = new LinkedHashMap<String, Identifier>();
        textures.put("particle", modLocation(particleTexture));
        for (final var direction : Direction.values()) {
            textures.put(direction.getSerializedName(), modLocation(direction == face ? faceTexture : otherTexture));
        }
        models.accept(modLocation(name), ModelDsl.cube(textures, renderType));
    }

    private static void connectedTexture(BiConsumer<Identifier, ModelInstance> models, String folder, String texturePrefix, String noneTexture) {
        for (int mask = 0; mask < 64; mask++) {
            final var connected = connectedSides(mask);
            final var name = folder + "/connected_" + connectionName(connected);
            if (connected.isEmpty()) {
                models.accept(modLocation(name), ModelDsl.cubeAllCutout(modLocation(noneTexture), CUTOUT));
                continue;
            }
            final var textures = new LinkedHashMap<String, Identifier>();
            textures.put("particle", modLocation(texturePrefix + "none"));
            for (final var face : Direction.values()) {
                if (connected.contains(face)) {
                    textures.put(face.getSerializedName(), Identifier.parse(TRANSPARENT));
                    continue;
                }
                final var edges = new StringBuilder();
                for (final var edge : "tblr".toCharArray()) {
                    for (final var side : connected) {
                        final var letter = FACE_EDGES.get(face).get(side);
                        if (letter != null && letter == edge) {
                            edges.append(edge);
                            break;
                        }
                    }
                }
                final var edgeName = edges.isEmpty() ? "none" : edges.length() == 4 ? "all" : edges.toString();
                textures.put(face.getSerializedName(), modLocation(texturePrefix + edgeName));
            }
            models.accept(modLocation(name), ModelDsl.cube(textures, CUTOUT));
        }
    }

    public static EnumSet<Direction> connectedSides
(int mask) {
        final var set = EnumSet.noneOf(Direction.class);
        if ((mask & 1) != 0) {
            set.add(Direction.UP);
        }
        if ((mask & 2) != 0) {
            set.add(Direction.DOWN);
        }
        if ((mask & 4) != 0) {
            set.add(Direction.NORTH);
        }
        if ((mask & 8) != 0) {
            set.add(Direction.SOUTH);
        }
        if ((mask & 16) != 0) {
            set.add(Direction.EAST);
        }
        if ((mask & 32) != 0) {
            set.add(Direction.WEST);
        }
        return set;
    }

    public static String connectionName(EnumSet<Direction> connected) {
        if (connected.isEmpty()) {
            return "none";
        }
        if (connected.size() == 6) {
            return "all";
        }
        final var builder = new StringBuilder();
        if (connected.contains(Direction.UP)) {
            builder.append('t');
        }
        if (connected.contains(Direction.DOWN)) {
            builder.append('b');
        }
        if (connected.contains(Direction.NORTH)) {
            builder.append('n');
        }
        if (connected.contains(Direction.SOUTH)) {
            builder.append('s');
        }
        if (connected.contains(Direction.EAST)) {
            builder.append('e');
        }
        if (connected.contains(Direction.WEST)) {
            builder.append('w');
        }
        return builder.toString();
    }

    private static Map<Direction, Map<Direction, Character>> buildFaceEdges() {
        final var map = new EnumMap<Direction, Map<Direction, Character>>(Direction.class);
        map.put(Direction.UP, edges(Direction.NORTH, 't', Direction.SOUTH, 'b', Direction.EAST, 'r', Direction.WEST, 'l'));
        map.put(Direction.DOWN, edges(Direction.NORTH, 'b', Direction.SOUTH, 't', Direction.EAST, 'r', Direction.WEST, 'l'));
        map.put(Direction.NORTH, edges(Direction.UP, 't', Direction.DOWN, 'b', Direction.EAST, 'l', Direction.WEST, 'r'));
        map.put(Direction.SOUTH, edges(Direction.UP, 't', Direction.DOWN, 'b', Direction.EAST, 'r', Direction.WEST, 'l'));
        map.put(Direction.EAST, edges(Direction.UP, 't', Direction.DOWN, 'b', Direction.NORTH, 'r', Direction.SOUTH, 'l'));
        map.put(Direction.WEST, edges(Direction.UP, 't', Direction.DOWN, 'b', Direction.NORTH, 'l', Direction.SOUTH, 'r'));
        return map;
    }

    private static Map<Direction, Character> edges(Direction a, char ca, Direction b, char cb, Direction c, char cc, Direction d, char cd) {
        final var map = new EnumMap<Direction, Character>(Direction.class);
        map.put(a, ca);
        map.put(b, cb);
        map.put(c, cc);
        map.put(d, cd);
        return map;
    }
}
