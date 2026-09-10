package modernmods.biggerreactorsrevived;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.resources.ResourceLocation;
import modernmods.phosphophylliterevived.Phosphophyllite;
import modernmods.quartzrevived.Quartz;

import java.util.List;

public final class CreativeTabOrder {

    public static List<ResourceLocation> before() {
        return new ReferenceArrayList<>();
    }

    public static List<ResourceLocation> after() {
        return ReferenceArrayList.of(ResourceLocation.fromNamespaceAndPath(Phosphophyllite.modid, "creative_tab"), ResourceLocation.fromNamespaceAndPath(Quartz.modid, "creative_tab"));
    }
}
