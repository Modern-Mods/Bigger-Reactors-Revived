package modernmods.biggerreactorsrevived.multiblocks.heatexchanger.tiles;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import modernmods.biggerreactorsrevived.multiblocks.heatexchanger.HeatExchangerMultiblockController;
import modernmods.biggerreactorsrevived.multiblocks.heatexchanger.blocks.HeatExchangerBaseBlock;
import modernmods.phosphophylliterevived.modular.tile.PhosphophylliteTile;
import modernmods.phosphophylliterevived.multiblock.common.IPersistentMultiblockTile;
import modernmods.phosphophylliterevived.multiblock.rectangular.IRectangularMultiblockTile;
import modernmods.phosphophylliterevived.multiblock.touching.ITouchingMultiblockTile;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class HeatExchangerBaseTile extends PhosphophylliteTile implements IRectangularMultiblockTile<HeatExchangerBaseTile, HeatExchangerBaseBlock, HeatExchangerMultiblockController>,
        IPersistentMultiblockTile<HeatExchangerBaseTile, HeatExchangerBaseBlock, HeatExchangerMultiblockController>,
        ITouchingMultiblockTile<HeatExchangerBaseTile, HeatExchangerBaseBlock, HeatExchangerMultiblockController> {
    public HeatExchangerBaseTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }
    
    @Nonnull
    @Override
    public HeatExchangerMultiblockController createController() {
        if (level == null) {
            throw new IllegalStateException("Attempt to create controller with null world");
        }
        return new HeatExchangerMultiblockController(level);
    }

    public void runRequest(String requestName, Object requestData) {
        if (nullableController() != null) {
            controller().runRequest(requestName, requestData);
        }
    }
}
