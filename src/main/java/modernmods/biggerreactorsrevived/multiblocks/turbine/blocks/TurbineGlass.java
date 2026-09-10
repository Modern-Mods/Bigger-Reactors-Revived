package modernmods.biggerreactorsrevived.multiblocks.turbine.blocks;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import modernmods.biggerreactorsrevived.multiblocks.turbine.tiles.TurbineGlassTile;
import modernmods.phosphophylliterevived.modular.block.IConnectedTexture;
import modernmods.phosphophylliterevived.multiblock.IAssemblyStateBlock;
import modernmods.phosphophylliterevived.registry.RegisterBlock;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TurbineGlass extends TurbineBaseBlock implements IAssemblyStateBlock, IConnectedTexture {
    
    @RegisterBlock(name = "turbine_glass", tileEntityClass = TurbineGlassTile.class)
    public static final TurbineGlass INSTANCE = new TurbineGlass();
    
    public TurbineGlass() {
        super(false);
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return TurbineGlassTile.SUPPLIER.create(pos, state);
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public float getShadeBrightness(BlockState p_60472_, BlockGetter p_60473_, BlockPos p_60474_) {
        return 1.0f;
    }
    
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }
}
