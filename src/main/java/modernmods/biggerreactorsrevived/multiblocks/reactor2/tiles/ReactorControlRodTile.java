package modernmods.biggerreactorsrevived.multiblocks.reactor2.tiles;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import modernmods.phosphophylliterevived.registry.RegisterTile;
import modernmods.phosphophylliterevived.util.NonnullDefault;

@NonnullDefault
public class ReactorControlRodTile extends ReactorTile {
    
    public ReactorControlRodTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }
    
    private double insertion = 0;
    
    @Override
    protected void readNBT(CompoundTag compound) {
        insertion = compound.getDoubleOr("insertion", 0D);
    }
    
    @Override
    protected CompoundTag writeNBT() {
        final var tag = new CompoundTag();
        tag.putDouble("insertion", insertion);
        return tag;
    }
}
