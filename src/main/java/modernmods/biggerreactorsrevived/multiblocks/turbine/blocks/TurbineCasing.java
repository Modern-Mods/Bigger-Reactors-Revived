package modernmods.biggerreactorsrevived.multiblocks.turbine.blocks;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import modernmods.biggerreactorsrevived.multiblocks.turbine.tiles.TurbineCasingTile;
import modernmods.phosphophylliterevived.multiblock.IAssemblyStateBlock;
import modernmods.phosphophylliterevived.multiblock.rectangular.IAxisPositionBlock;
import modernmods.phosphophylliterevived.registry.RegisterBlock;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TurbineCasing extends TurbineBaseBlock implements IAssemblyStateBlock, IAxisPositionBlock {
    
    @RegisterBlock(name = "turbine_casing", tileEntityClass = TurbineCasingTile.class)
    public static final TurbineCasing INSTANCE = new TurbineCasing();
    
    public TurbineCasing() {
        super();
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return TurbineCasingTile.SUPPLIER.create(pos, state);
    }
    
    @Override
    public boolean isGoodForFrame() {
        return true;
    }
}
