package modernmods.biggerreactorsrevived.multiblocks.heatexchanger.tiles;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import modernmods.biggerreactorsrevived.deps.ComputerCraftDeps;
import modernmods.biggerreactorsrevived.multiblocks.heatexchanger.deps.HeatExchangerPeripheral;
import modernmods.phosphophylliterevived.registry.RegisterTile;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class HeatExchangerComputerPortTile extends HeatExchangerBaseTile {
    
    
    @RegisterTile("heat_exchanger_computer_port")
    public static final BlockEntityType.BlockEntitySupplier<HeatExchangerComputerPortTile> SUPPLIER = new RegisterTile.Producer<>(HeatExchangerComputerPortTile::new);
    
    public HeatExchangerComputerPortTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }
    
    
    @Nullable
    @Override
    public <T> T capability(BlockCapability<T, Direction> cap, final @Nullable Direction side) {
        if (ComputerCraftDeps.LOADED && cap == ComputerCraftDeps.peripheralCapability()) {
            //noinspection unchecked
            return (T) HeatExchangerPeripheral.create(this::controller);
        }
        return super.capability(cap, side);
    }
    
}
