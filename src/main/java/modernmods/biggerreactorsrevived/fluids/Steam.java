package modernmods.biggerreactorsrevived.fluids;

import modernmods.phosphophylliterevived.registry.PhosphophylliteFluid;
import modernmods.phosphophylliterevived.registry.RegisterFluid;

import javax.annotation.Nonnull;

@RegisterFluid(name = "steam", registerBucket = true)
public class Steam extends PhosphophylliteFluid {
    
    @RegisterFluid.Instance
    public static Steam INSTANCE;
    
    public Steam(@Nonnull Properties properties) {
        super(properties);
    }
}
