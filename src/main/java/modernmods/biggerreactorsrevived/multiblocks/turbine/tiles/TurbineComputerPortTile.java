package modernmods.biggerreactorsrevived.multiblocks.turbine.tiles;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import modernmods.biggerreactorsrevived.deps.ComputerCraftDeps;
import modernmods.biggerreactorsrevived.multiblocks.turbine.deps.TurbinePeripheral;
import modernmods.phosphophylliterevived.registry.RegisterTile;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TurbineComputerPortTile extends TurbineBaseTile {
    
    @RegisterTile("turbine_computer_port")
    public static final BlockEntityType.BlockEntitySupplier<TurbineComputerPortTile> SUPPLIER = new RegisterTile.Producer<>(TurbineComputerPortTile::new);
    
    public TurbineComputerPortTile(BlockEntityType<?> TYPE, BlockPos pos, BlockState state) {
        super(TYPE, pos, state);
    }
    
    @Nullable
    @Override
    public <T> T capability(BlockCapability<T, Direction> cap, final @Nullable Direction side) {
        if (ComputerCraftDeps.LOADED && cap == ComputerCraftDeps.peripheralCapability()) {
            //noinspection unchecked
            return (T) TurbinePeripheral.create(this::controller);
        }
        return super.capability(cap, side);
    }
    
}
