package modernmods.biggerreactorsrevived.multiblocks.turbine.tiles;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import modernmods.biggerreactorsrevived.multiblocks.turbine.TurbineMultiblockController;
import modernmods.biggerreactorsrevived.multiblocks.turbine.blocks.TurbineBaseBlock;
import modernmods.phosphophylliterevived.modular.tile.PhosphophylliteTile;
import modernmods.phosphophylliterevived.multiblock.IMultiblockTile;
import modernmods.phosphophylliterevived.multiblock.common.IPersistentMultiblockTile;
import modernmods.phosphophylliterevived.multiblock.rectangular.IRectangularMultiblockTile;
import modernmods.phosphophylliterevived.multiblock.touching.ITouchingMultiblockTile;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TurbineBaseTile extends PhosphophylliteTile implements IMultiblockTile<TurbineBaseTile, TurbineBaseBlock, TurbineMultiblockController>,
        IRectangularMultiblockTile<TurbineBaseTile, TurbineBaseBlock, TurbineMultiblockController>,
        IPersistentMultiblockTile<TurbineBaseTile, TurbineBaseBlock, TurbineMultiblockController>,
        ITouchingMultiblockTile<TurbineBaseTile, TurbineBaseBlock, TurbineMultiblockController> {
    public TurbineBaseTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }
    
    @Override
    public final TurbineMultiblockController createController() {
        return new TurbineMultiblockController(level);
    }
    
    public void runRequest(String requestName, Object requestData) {
        if (nullableController() != null) {
            controller().runRequest(requestName, requestData);
        }
    }
    
    public boolean isCurrentController(TurbineMultiblockController turbineMultiblockController) {
        return controller() == turbineMultiblockController;
    }
}
