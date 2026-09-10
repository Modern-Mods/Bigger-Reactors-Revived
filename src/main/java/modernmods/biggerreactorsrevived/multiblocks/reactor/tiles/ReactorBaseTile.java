package modernmods.biggerreactorsrevived.multiblocks.reactor.tiles;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import modernmods.biggerreactorsrevived.multiblocks.reactor.ReactorMultiblockController;
import modernmods.biggerreactorsrevived.multiblocks.reactor.blocks.ReactorBaseBlock;
import modernmods.phosphophylliterevived.modular.api.TileModule;
import modernmods.phosphophylliterevived.modular.tile.PhosphophylliteTile;
import modernmods.phosphophylliterevived.multiblock.MultiblockTileModule;
import modernmods.phosphophylliterevived.multiblock.rectangular.IRectangularMultiblockTile;
import modernmods.phosphophylliterevived.multiblock.IMultiblockTile;
import modernmods.phosphophylliterevived.multiblock.common.IPersistentMultiblockTile;
import modernmods.phosphophylliterevived.multiblock.touching.ITouchingMultiblockTile;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ReactorBaseTile extends PhosphophylliteTile implements IMultiblockTile<ReactorBaseTile, ReactorBaseBlock, ReactorMultiblockController>,
        IRectangularMultiblockTile<ReactorBaseTile, ReactorBaseBlock, ReactorMultiblockController>,
        IPersistentMultiblockTile<ReactorBaseTile, ReactorBaseBlock, ReactorMultiblockController>,
        ITouchingMultiblockTile<ReactorBaseTile, ReactorBaseBlock, ReactorMultiblockController> {
    
    public int index = -1;
    
    public ReactorBaseTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }
    
    @Override
    public final ReactorMultiblockController createController() {
        if (level == null) {
            throw new IllegalStateException("Attempt to create controller with null world");
        }
        return new ReactorMultiblockController(level);
    }
    
    public void runRequest(String requestName, Object requestData) {
        if (nullableController() != null) {
            controller().runRequest(requestName, requestData);
        }
    }
    
    public boolean isCurrentController(@Nullable ReactorMultiblockController reactorMultiblockController) {
        return controller() == reactorMultiblockController;
    }
    
    @org.jetbrains.annotations.Nullable
    private TileModule<?> multiblockTileModule;
    
    @Override
    public MultiblockTileModule<ReactorBaseTile, ReactorBaseBlock, ReactorMultiblockController> multiblockModule() {
        if(multiblockTileModule == null){
            multiblockTileModule = module(IMultiblockTile.class);
        }
        //noinspection unchecked
        return (MultiblockTileModule<ReactorBaseTile, ReactorBaseBlock, ReactorMultiblockController>) multiblockTileModule;
    }
    
    @Override
    public boolean alwaysConnectToSameController() {
        return true;
    }
}
