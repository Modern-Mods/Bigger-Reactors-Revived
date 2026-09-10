package modernmods.biggerreactorsrevived.multiblocks.reactor.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import modernmods.biggerreactorsrevived.multiblocks.reactor.tiles.ReactorCasingTile;
import modernmods.phosphophylliterevived.multiblock.IAssemblyStateBlock;
import modernmods.phosphophylliterevived.multiblock.rectangular.IAxisPositionBlock;
import modernmods.phosphophylliterevived.registry.RegisterBlock;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ReactorCasing extends ReactorBaseBlock implements IAssemblyStateBlock, IAxisPositionBlock {
    
    @RegisterBlock(name = "reactor_casing", tileEntityClass = ReactorCasingTile.class)
    public static final ReactorCasing INSTANCE = new ReactorCasing();
    
    public ReactorCasing() {
        super();
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ReactorCasingTile.SUPPLIER.create(pos, state);
    }
    
    @Override
    public boolean isGoodForFrame() {
        return true;
    }
}
