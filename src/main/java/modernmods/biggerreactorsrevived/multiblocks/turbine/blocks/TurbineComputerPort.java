package modernmods.biggerreactorsrevived.multiblocks.turbine.blocks;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import modernmods.biggerreactorsrevived.multiblocks.turbine.tiles.TurbineComputerPortTile;
import modernmods.phosphophylliterevived.multiblock.IAssemblyStateBlock;
import modernmods.phosphophylliterevived.multiblock.rectangular.IFaceDirectionBlock;
import modernmods.phosphophylliterevived.registry.RegisterBlock;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TurbineComputerPort extends TurbineBaseBlock implements IAssemblyStateBlock, IFaceDirectionBlock {
    
    @RegisterBlock(name = "turbine_computer_port", tileEntityClass = TurbineComputerPortTile.class)
    public static final TurbineComputerPort INSTANCE = new TurbineComputerPort();
    
    public TurbineComputerPort() {
        super();
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return TurbineComputerPortTile.SUPPLIER.create(pos, state);
    }
}
