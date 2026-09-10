package modernmods.biggerreactorsrevived.multiblocks.heatexchanger.blocks;


import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import modernmods.biggerreactorsrevived.multiblocks.heatexchanger.tiles.HeatExchangerComputerPortTile;
import modernmods.phosphophylliterevived.multiblock.IAssemblyStateBlock;
import modernmods.phosphophylliterevived.multiblock.rectangular.IFaceDirectionBlock;
import modernmods.phosphophylliterevived.registry.RegisterBlock;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class HeatExchangerComputerPortBlock extends HeatExchangerBaseBlock implements IAssemblyStateBlock, IFaceDirectionBlock {
    
    @RegisterBlock(name = "heat_exchanger_computer_port", tileEntityClass = HeatExchangerComputerPortTile.class)
    public static final HeatExchangerComputerPortBlock INSTANCE = new HeatExchangerComputerPortBlock();
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return HeatExchangerComputerPortTile.SUPPLIER.create(pos, state);
    }
}
