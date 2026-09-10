package modernmods.biggerreactorsrevived.items.raw;

import net.minecraft.world.item.Item;
import modernmods.phosphophylliterevived.registry.RegisterItem;

import javax.annotation.Nonnull;

public class UraniumChunk extends Item {

    @RegisterItem(name = "uranium_chunk")
    public static final UraniumChunk INSTANCE = new UraniumChunk(new Properties());

    @SuppressWarnings("unused")
    public UraniumChunk(@Nonnull Properties properties) {
        super(properties);
    }
}
