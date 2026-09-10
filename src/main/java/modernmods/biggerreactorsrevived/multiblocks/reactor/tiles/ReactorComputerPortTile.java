package modernmods.biggerreactorsrevived.multiblocks.reactor.tiles;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import modernmods.biggerreactorsrevived.deps.ComputerCraftDeps;
import modernmods.biggerreactorsrevived.multiblocks.reactor.deps.ReactorPeripheral;
import modernmods.phosphophylliterevived.multiblock.common.IEventMultiblock;
import modernmods.phosphophylliterevived.registry.RegisterTile;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ReactorComputerPortTile extends ReactorBaseTile implements IEventMultiblock.AssemblyStateTransition.OnAssembly {

    @RegisterTile("reactor_computer_port")
    public static final BlockEntityType.BlockEntitySupplier<ReactorComputerPortTile> SUPPLIER = new RegisterTile.Producer<>(ReactorComputerPortTile::new);

    public ReactorComputerPortTile(BlockEntityType<?> TYPE, BlockPos pos, BlockState state) {
        super(TYPE, pos, state);
    }

    @Nullable
    private Object peripheral;

    {
        // avoids classloading without CC existing
        if (ComputerCraftDeps.LOADED) {
            peripheral = ReactorPeripheral.create(this::controller);
        }
    }

    @Nullable
    @Override
    public <T> T capability(BlockCapability<T, Direction> cap, final @Nullable Direction side) {
        if (ComputerCraftDeps.LOADED && cap == ComputerCraftDeps.peripheralCapability()) {
            //noinspection unchecked
            return (T) peripheral;
        }
        return super.capability(cap, side);
    }

    @Override
    public void onAssembly() {
        // class loading BS, dont remove this if
        if (ComputerCraftDeps.LOADED && peripheral != null) {
            ((ReactorPeripheral) peripheral).rebuildControlRodList();
        }
    }
}
