package modernmods.biggerreactorsrevived;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.resources.Identifier;
import modernmods.phosphophylliterevived.Phosphophyllite;
import modernmods.quartzrevived.Quartz;

import java.util.List;

public final class CreativeTabOrder {

    public static List<Identifier> before() {
        return new ReferenceArrayList<>();
    }

    public static List<Identifier> after() {
        return ReferenceArrayList.of(Identifier.fromNamespaceAndPath(Phosphophyllite.modid, "creative_tab"), Identifier.fromNamespaceAndPath(Quartz.modid, "creative_tab"));
    }
}
