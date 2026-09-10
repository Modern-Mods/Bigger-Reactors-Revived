package modernmods.biggerreactorsrevived.datagen.providers;

import com.google.gson.JsonParser;
import modernmods.biggerreactorsrevived.BiggerReactors;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BiggerReactorsQuartzStateProvider implements DataProvider {

    private final PackOutput.PathProvider states;
    private final List<CompletableFuture<?>> written = new ArrayList<>();

    public BiggerReactorsQuartzStateProvider(PackOutput output) {
        this.states = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "quartzstates");
    }

    @Override
    public String getName() {
        return "Bigger Reactors Quartz States";
    }

    private void raw(CachedOutput cache, String path, String json) {
        final var location = ResourceLocation.fromNamespaceAndPath(BiggerReactors.modid, path);
        written.add(DataProvider.saveStable(cache, JsonParser.parseString(json), states.json(location)));
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        written.clear();
        raw(cache, "reactor_casing", """
                {
                    "AssemblyState" : {
                        "assembled" : {
                            "Position" : {
                                "corner" : {
                                    "textures" : {
                                        "all" : "biggerreactors:block/reactor_casing_corner"
                                    }
                                },
                                "face" : {
                                    "textures" : {
                                        "all" : "biggerreactors:block/reactor_casing_face"
                                    }
                                },
                                "frame_x" : {
                                    "rotations" : {
                                        "all" : 1
                                    },
                                    "textures" : {
                                        "all" : "biggerreactors:block/reactor_casing_frame"
                                    }
                                },
                                "frame_y" : {
                                    "textures" : {
                                        "all" : "biggerreactors:block/reactor_casing_frame"
                                    }
                                },
                                "frame_z" : {
                                    "rotations" : {
                                        "all" : 1,
                                        "north" : 0,
                                        "south" : 0
                                    },
                                    "textures" : {
                                        "all" : "biggerreactors:block/reactor_casing_frame"
                                    }
                                }
                            }
                        }
                    },
                    "textures" : {
                        "all" : "biggerreactors:block/reactor_casing_disassembled"
                    }
                }
                """);
        raw(cache, "reactor_terminal", """
                {
                    "AssemblyState" : {
                        "ASSEMBLED" : {
                            "ReactorState" : {
                                "ACTIVE" : {
                                    "textures" : {
                                        "all" : "biggerreactors:block/reactor_terminal_active"
                                    }
                                },
                                "INACTIVE" : {
                                    "textures" : {
                                        "all" : "biggerreactors:block/reactor_terminal_idle"
                                    }
                                }
                            }
                        }
                    },
                    "textures" : {
                        "all" : "biggerreactors:block/reactor_terminal_off"
                    }
                }
                """);
        return CompletableFuture.allOf(written.toArray(CompletableFuture[]::new));
    }
}
