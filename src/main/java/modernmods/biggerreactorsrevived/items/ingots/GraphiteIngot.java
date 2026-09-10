package modernmods.biggerreactorsrevived.items.ingots;

import net.minecraft.world.item.Item;
import modernmods.phosphophylliterevived.registry.RegisterItem;

import javax.annotation.Nonnull;

public class GraphiteIngot extends Item {
    
    @RegisterItem(name = "graphite_ingot")
    public static final GraphiteIngot INSTANCE = new GraphiteIngot(new Properties());
    
    @SuppressWarnings("unused")
    public GraphiteIngot(@Nonnull Properties properties) {
        super(properties);
    }
}
