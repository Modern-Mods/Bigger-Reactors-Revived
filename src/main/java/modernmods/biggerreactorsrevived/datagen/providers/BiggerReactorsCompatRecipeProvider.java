package modernmods.biggerreactorsrevived.datagen.providers;

import com.google.gson.JsonParser;
import modernmods.biggerreactorsrevived.BiggerReactors;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BiggerReactorsCompatRecipeProvider implements DataProvider {

    private final PackOutput.PathProvider recipes;
    private final List<CompletableFuture<?>> written = new ArrayList<>();

    public BiggerReactorsCompatRecipeProvider(PackOutput output) {
        this.recipes = output.createPathProvider(PackOutput.Target.DATA_PACK, "recipe");
    }

    @Override
    public String getName() {
        return "Bigger Reactors Compat Recipes";
    }

    private void raw(CachedOutput cache, String path, String json) {
        final var location = Identifier.fromNamespaceAndPath(BiggerReactors.modid, path);
        written.add(DataProvider.saveStable(cache, JsonParser.parseString(json), recipes.json(location)));
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        written.clear();
        raw(cache, "compat/appliedenergistics/inscriber_blutonium_ingot", """
                {
                    "ingredients" : {
                        "middle" : "biggerreactors:blutonium_ingot"
                    },
                    "mode" : "inscribe",
                    "neoforge:conditions" : [
                        {
                            "modid" : "ae2",
                            "type" : "neoforge:mod_loaded"
                        }
                    ],
                    "result" : {
                        "count" : 1,
                        "id" : "biggerreactors:blutonium_dust"
                    },
                    "type" : "ae2:inscriber"
                }
                """);
        raw(cache, "compat/appliedenergistics/inscriber_cyanite_ingot", """
                {
                    "ingredients" : {
                        "middle" : "biggerreactors:cyanite_ingot"
                    },
                    "mode" : "inscribe",
                    "neoforge:conditions" : [
                        {
                            "modid" : "ae2",
                            "type" : "neoforge:mod_loaded"
                        }
                    ],
                    "result" : {
                        "count" : 1,
                        "id" : "biggerreactors:cyanite_dust"
                    },
                    "type" : "ae2:inscriber"
                }
                """);
        raw(cache, "compat/appliedenergistics/inscriber_deepslate_uranium_ore", """
                {
                    "ingredients" : {
                        "middle" : "biggerreactors:deepslate_uranium_ore"
                    },
                    "mode" : "inscribe",
                    "neoforge:conditions" : [
                        {
                            "modid" : "ae2",
                            "type" : "neoforge:mod_loaded"
                        }
                    ],
                    "result" : {
                        "count" : 2,
                        "id" : "biggerreactors:uranium_dust"
                    },
                    "type" : "ae2:inscriber"
                }
                """);
        raw(cache, "compat/appliedenergistics/inscriber_graphite_ingot", """
                {
                    "ingredients" : {
                        "middle" : "biggerreactors:graphite_ingot"
                    },
                    "mode" : "inscribe",
                    "neoforge:conditions" : [
                        {
                            "modid" : "ae2",
                            "type" : "neoforge:mod_loaded"
                        }
                    ],
                    "result" : {
                        "count" : 1,
                        "id" : "biggerreactors:graphite_dust"
                    },
                    "type" : "ae2:inscriber"
                }
                """);
        raw(cache, "compat/appliedenergistics/inscriber_ludicrite_ingot", """
                {
                    "ingredients" : {
                        "middle" : "biggerreactors:ludicrite_ingot"
                    },
                    "mode" : "inscribe",
                    "neoforge:conditions" : [
                        {
                            "modid" : "ae2",
                            "type" : "neoforge:mod_loaded"
                        }
                    ],
                    "result" : {
                        "count" : 1,
                        "id" : "biggerreactors:ludicrite_dust"
                    },
                    "type" : "ae2:inscriber"
                }
                """);
        raw(cache, "compat/appliedenergistics/inscriber_uranium_chunk", """
                {
                    "ingredients" : {
                        "middle" : "biggerreactors:uranium_chunk"
                    },
                    "mode" : "inscribe",
                    "neoforge:conditions" : [
                        {
                            "modid" : "ae2",
                            "type" : "neoforge:mod_loaded"
                        }
                    ],
                    "result" : {
                        "count" : 2,
                        "id" : "biggerreactors:uranium_dust"
                    },
                    "type" : "ae2:inscriber"
                }
                """);
        raw(cache, "compat/appliedenergistics/inscriber_uranium_ingot", """
                {
                    "ingredients" : {
                        "middle" : "biggerreactors:uranium_ingot"
                    },
                    "mode" : "inscribe",
                    "neoforge:conditions" : [
                        {
                            "modid" : "ae2",
                            "type" : "neoforge:mod_loaded"
                        }
                    ],
                    "result" : {
                        "count" : 1,
                        "id" : "biggerreactors:uranium_dust"
                    },
                    "type" : "ae2:inscriber"
                }
                """);
        raw(cache, "compat/appliedenergistics/inscriber_uranium_ore", """
                {
                    "ingredients" : {
                        "middle" : "biggerreactors:uranium_ore"
                    },
                    "mode" : "inscribe",
                    "neoforge:conditions" : [
                        {
                            "modid" : "ae2",
                            "type" : "neoforge:mod_loaded"
                        }
                    ],
                    "result" : {
                        "count" : 2,
                        "id" : "biggerreactors:uranium_dust"
                    },
                    "type" : "ae2:inscriber"
                }
                """);
        raw(cache, "compat/mekanism/crushing/crusher_blutonium_ingot", """
                {
                    "input" : {
                        "item" : "biggerreactors:blutonium_ingot"
                    },
                    "neoforge:conditions" : [
                        {
                            "modid" : "mekanism",
                            "type" : "neoforge:mod_loaded"
                        }
                    ],
                    "output" : {
                        "count" : 1,
                        "id" : "biggerreactors:blutonium_dust"
                    },
                    "type" : "mekanism:crushing"
                }
                """);
        raw(cache, "compat/mekanism/crushing/crusher_cyanite_ingot", """
                {
                    "input" : {
                        "item" : "biggerreactors:cyanite_ingot"
                    },
                    "neoforge:conditions" : [
                        {
                            "modid" : "mekanism",
                            "type" : "neoforge:mod_loaded"
                        }
                    ],
                    "output" : {
                        "count" : 1,
                        "id" : "biggerreactors:cyanite_dust"
                    },
                    "type" : "mekanism:crushing"
                }
                """);
        raw(cache, "compat/mekanism/crushing/crusher_graphite_ingot", """
                {
                    "input" : {
                        "item" : "biggerreactors:graphite_ingot"
                    },
                    "neoforge:conditions" : [
                        {
                            "modid" : "mekanism",
                            "type" : "neoforge:mod_loaded"
                        }
                    ],
                    "output" : {
                        "count" : 1,
                        "id" : "biggerreactors:graphite_dust"
                    },
                    "type" : "mekanism:crushing"
                }
                """);
        raw(cache, "compat/mekanism/crushing/crusher_ludicrite_ingot", """
                {
                    "input" : {
                        "item" : "biggerreactors:ludicrite_ingot"
                    },
                    "neoforge:conditions" : [
                        {
                            "modid" : "mekanism",
                            "type" : "neoforge:mod_loaded"
                        }
                    ],
                    "output" : {
                        "count" : 1,
                        "id" : "biggerreactors:ludicrite_dust"
                    },
                    "type" : "mekanism:crushing"
                }
                """);
        raw(cache, "compat/mekanism/crushing/crusher_uranium_ingot", """
                {
                    "input" : {
                        "item" : "biggerreactors:uranium_ingot"
                    },
                    "neoforge:conditions" : [
                        {
                            "modid" : "mekanism",
                            "type" : "neoforge:mod_loaded"
                        }
                    ],
                    "output" : {
                        "count" : 1,
                        "id" : "biggerreactors:uranium_dust"
                    },
                    "type" : "mekanism:crushing"
                }
                """);
        raw(cache, "compat/mekanism/enriching/enrichment_deepslate_uranium_ore", """
                {
                    "input" : {
                        "item" : "biggerreactors:deepslate_uranium_ore"
                    },
                    "neoforge:conditions" : [
                        {
                            "modid" : "mekanism",
                            "type" : "neoforge:mod_loaded"
                        }
                    ],
                    "output" : {
                        "count" : 2,
                        "id" : "biggerreactors:uranium_dust"
                    },
                    "type" : "mekanism:enriching"
                }
                """);
        raw(cache, "compat/mekanism/enriching/enrichment_uranium_chunk", """
                {
                    "input" : {
                        "item" : "biggerreactors:uranium_chunk"
                    },
                    "neoforge:conditions" : [
                        {
                            "modid" : "mekanism",
                            "type" : "neoforge:mod_loaded"
                        }
                    ],
                    "output" : {
                        "count" : 2,
                        "id" : "biggerreactors:uranium_dust"
                    },
                    "type" : "mekanism:enriching"
                }
                """);
        raw(cache, "compat/mekanism/enriching/enrichment_uranium_ore", """
                {
                    "input" : {
                        "item" : "biggerreactors:uranium_ore"
                    },
                    "neoforge:conditions" : [
                        {
                            "modid" : "mekanism",
                            "type" : "neoforge:mod_loaded"
                        }
                    ],
                    "output" : {
                        "count" : 2,
                        "id" : "biggerreactors:uranium_dust"
                    },
                    "type" : "mekanism:enriching"
                }
                """);
        return CompletableFuture.allOf(written.toArray(CompletableFuture[]::new));
    }
}
