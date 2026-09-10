package modernmods.biggerreactorsrevived.multiblocks.heatexchanger.blocks;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import modernmods.biggerreactorsrevived.multiblocks.heatexchanger.tiles.HeatExchangerChannelTile;
import modernmods.phosphophylliterevived.modular.block.IConnectedTexture;
import modernmods.phosphophylliterevived.registry.RegisterBlock;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class HeatExchangerEvaporatorChannelBlock extends HeatExchangerBaseBlock implements IConnectedTexture {
    
    @RegisterBlock(name = "heat_exchanger_evaporator_channel", tileEntityClass = HeatExchangerChannelTile.class)
    public static final HeatExchangerEvaporatorChannelBlock INSTANCE = new HeatExchangerEvaporatorChannelBlock();
    
    public HeatExchangerEvaporatorChannelBlock() {
        super(PROPERTIES_GLASS);
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return HeatExchangerChannelTile.SUPPLIER.create(pos, state);
    }
    
    @SuppressWarnings("deprecation")
    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 1.0F;
    }
    
    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return true;
    }
    
    @Override
    public boolean connectToBlock(Block block) {
        return IConnectedTexture.super.connectToBlock(block) || block == HeatExchangerFluidPortBlock.INSTANCE;
    }
    
    @Override
    public boolean isGoodForExterior() {
        return false;
    }
    
    @Override
    public boolean isGoodForInterior() {
        return true;
    }
}
