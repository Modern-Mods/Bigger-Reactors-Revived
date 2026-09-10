package modernmods.biggerreactorsrevived.multiblocks.reactor2.tiles;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import modernmods.biggerreactorsrevived.multiblocks.reactor2.blocks.ReactorBlock;
import modernmods.biggerreactorsrevived.multiblocks.reactor2.ReactorMultiblockController;
import modernmods.phosphophylliterevived.modular.tile.PhosphophylliteTile;
import modernmods.phosphophylliterevived.multiblock.IMultiblockTile;
import modernmods.phosphophylliterevived.multiblock.common.IPersistentMultiblockTile;
import modernmods.phosphophylliterevived.multiblock.rectangular.IRectangularMultiblockTile;
import modernmods.phosphophylliterevived.multiblock.touching.ITouchingMultiblockTile;
import modernmods.phosphophylliterevived.util.NonnullDefault;

@NonnullDefault
public class ReactorTile extends PhosphophylliteTile implements IMultiblockTile<ReactorTile, ReactorBlock, ReactorMultiblockController>, IPersistentMultiblockTile<ReactorTile, ReactorBlock, ReactorMultiblockController>, IRectangularMultiblockTile<ReactorTile, ReactorBlock, ReactorMultiblockController>, ITouchingMultiblockTile<ReactorTile, ReactorBlock, ReactorMultiblockController> {
    
    public long lastCheckedTick = 0;
    
    public ReactorTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }
    
    @Override
    public ReactorMultiblockController createController() {
        assert level != null;
        return new ReactorMultiblockController(level);
    }
}
