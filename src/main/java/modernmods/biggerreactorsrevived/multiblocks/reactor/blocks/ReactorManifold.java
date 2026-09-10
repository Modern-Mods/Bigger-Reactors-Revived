package modernmods.biggerreactorsrevived.multiblocks.reactor.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import modernmods.biggerreactorsrevived.Config;
import modernmods.biggerreactorsrevived.multiblocks.reactor.tiles.ReactorManifoldTile;
import modernmods.phosphophylliterevived.modular.block.IConnectedTexture;
import modernmods.phosphophylliterevived.registry.RegisterBlock;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ReactorManifold extends ReactorBaseBlock implements IConnectedTexture {
    
    @RegisterBlock(name = "reactor_manifold", tileEntityClass = ReactorManifoldTile.class)
    public static final ReactorManifold INSTANCE = new ReactorManifold();
    
    public ReactorManifold() {
        super(false);
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ReactorManifoldTile.SUPPLIER.create(pos, state);
    }
    
    @Override
    protected float getShadeBrightness(BlockState p_60472_, BlockGetter p_60473_, BlockPos p_60474_) {
        return 1.0f;
    }
    
    protected boolean propagatesSkylightDown(BlockState state) {
        return true;
    }
    
    @Override
    public boolean isGoodForInterior() {
        return true;
    }
    
    @Override
    public boolean isGoodForExterior() {
        return false;
    }
    
    @Override
    public boolean connectToBlock(Block block) {
        if (block instanceof ReactorBaseBlock reactorBlock) {
            return !(reactorBlock instanceof ReactorGlass) && (reactorBlock).isGoodForExterior() || reactorBlock == this;
        }
        return false;
    }
}
