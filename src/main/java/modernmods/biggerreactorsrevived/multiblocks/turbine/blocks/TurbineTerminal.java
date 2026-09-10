package modernmods.biggerreactorsrevived.multiblocks.turbine.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import modernmods.biggerreactorsrevived.multiblocks.turbine.tiles.TurbineTerminalTile;
import modernmods.phosphophylliterevived.multiblock.IAssemblyStateBlock;
import modernmods.phosphophylliterevived.multiblock.rectangular.IFaceDirectionBlock;
import modernmods.phosphophylliterevived.registry.RegisterBlock;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TurbineTerminal extends TurbineBaseBlock implements IAssemblyStateBlock, IFaceDirectionBlock {
    
    @RegisterBlock(name = "turbine_terminal", tileEntityClass = TurbineTerminalTile.class)
    public static final TurbineTerminal INSTANCE = new TurbineTerminal();
    
    public TurbineTerminal() {
        super();
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return TurbineTerminalTile.SUPPLIER.create(pos, state);
    }
    
    @Override
    public boolean usesTurbineState() {
        return true;
    }
}
