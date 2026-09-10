package modernmods.biggerreactorsrevived.datagen.providers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.math.Quadrant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelDispatcher;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ModelDsl {

    private ModelDsl() {
    }

    public static BlockModelDefinitionGenerator forAllStates(Block block, Function<BlockState, MultiVariant> mapper) {
        final Map<String, BlockStateModel.Unbaked> models = new LinkedHashMap<>();
        for (final var state : block.getStateDefinition().getPossibleStates()) {
            models.put(selector(state), mapper.apply(state).toUnbaked());
        }
        return new BlockModelDefinitionGenerator() {
            @Override
            public Block block() {
                return block;
            }

            @Override
            public BlockStateModelDispatcher create() {
                return new BlockStateModelDispatcher(Optional.of(new BlockStateModelDispatcher.SimpleModelSelectors(models)), Optional.empty());
            }
        };
    }

    private static String selector(BlockState state) {
        return state.getValues().map(Property.Value::toString).collect(Collectors.joining(","));
    }

    public static MultiVariant plain(Identifier model) {
        return BlockModelGenerators.plainVariant(model);
    }

    public static MultiVariant rotated(Identifier model, int xRotation, int yRotation) {
        var variant = plain(model);
        if (xRotation != 0) {
            variant = variant.with(VariantMutator.X_ROT.withValue(quadrant(xRotation)));
        }
        if (yRotation != 0) {
            variant = variant.with(VariantMutator.Y_ROT.withValue(quadrant(yRotation)));
        }
        return variant;
    }

    private static Quadrant quadrant(int degrees) {
        return switch (Math.floorMod(degrees, 360)) {
            case 90 -> Quadrant.R90;
            case 180 -> Quadrant.R180;
            case 270 -> Quadrant.R270;
            default -> Quadrant.R0;
        };
    }

    public static ModelInstance cubeAllCutout(Identifier texture, String renderType) {
        return parented(Identifier.withDefaultNamespace("block/cube_all"), Map.of("particle", texture, "all", texture), renderType);
    }

    public static ModelInstance cubeAll(Identifier texture) {
        return cube(Map.of(
                "particle", texture,
                "down", texture,
                "up", texture,
                "north", texture,
                "south", texture,
                "east", texture,
                "west", texture), null);
    }

    public static ModelInstance orientable(Identifier side, Identifier front, Identifier top) {
        return parented(Identifier.withDefaultNamespace("block/orientable"), Map.of(
                "particle", front,
                "side", side,
                "front", front,
                "top", top), null);
    }

    public static ModelInstance cube(Map<String, Identifier> textures, String renderType) {
        return parented(Identifier.withDefaultNamespace("block/cube"), textures, renderType);
    }

    public static ModelInstance faces(Map<Direction, Identifier> faces, Identifier particle, String renderType) {
        final Map<String, Identifier> textures = new LinkedHashMap<>();
        textures.put("particle", particle);
        faces.forEach((direction, texture) -> textures.put(direction.getSerializedName(), texture));
        return cube(textures, renderType);
    }

    public static ModelInstance parented(Identifier parent, Map<String, Identifier> textures, String renderType) {
        return () -> {
            final var model = new JsonObject();
            model.addProperty("parent", parent.toString());
            if (renderType != null) {
                model.addProperty("render_type", renderType);
            }
            final var textureObject = new JsonObject();
            textures.forEach((slot, texture) -> textureObject.addProperty(slot, texture.toString()));
            model.add("textures", textureObject);
            return (JsonElement) model;
        };
    }

    public static ModelInstance flatItem(Identifier parent, Identifier layer0) {
        return () -> {
            final var model = new JsonObject();
            model.addProperty("parent", parent.toString());
            final var textureObject = new JsonObject();
            textureObject.addProperty("layer0", layer0.toString());
            model.add("textures", textureObject);
            return (JsonElement) model;
        };
    }

    public static ModelInstance inherit(Identifier parent) {
        return () -> {
            final var model = new JsonObject();
            model.addProperty("parent", parent.toString());
            return (JsonElement) model;
        };
    }
}
