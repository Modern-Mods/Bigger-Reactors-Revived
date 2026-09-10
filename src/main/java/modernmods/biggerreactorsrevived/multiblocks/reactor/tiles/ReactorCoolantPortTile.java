package modernmods.biggerreactorsrevived.multiblocks.reactor.tiles;

import net.minecraft.core.component.DataComponentPatch;
import mekanism.api.chemical.IChemicalHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import modernmods.phosphophylliterevived.capabilities.MekanismCapabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import modernmods.biggerreactorsrevived.multiblocks.reactor.util.ReactorTransitionTank;
import modernmods.biggerreactorsrevived.multiblocks.reactor.blocks.ReactorAccessPort;
import modernmods.biggerreactorsrevived.multiblocks.reactor.blocks.ReactorCoolantPort;
import modernmods.biggerreactorsrevived.multiblocks.reactor.containers.ReactorCoolantPortContainer;
import modernmods.biggerreactorsrevived.multiblocks.reactor.state.ReactorCoolantPortState;
import modernmods.phosphophylliterevived.fluids.ResourceFluidHandlerWrapper;
import modernmods.phosphophylliterevived.fluids.IPhosphophylliteFluidHandler;
import modernmods.phosphophylliterevived.client.gui.api.IHasUpdatableState;
import modernmods.phosphophylliterevived.fluids.MekanismGasWrappers;
import modernmods.phosphophylliterevived.multiblock.common.IEventMultiblock;
import modernmods.phosphophylliterevived.multiblock.validated.IValidatedMultiblock;
import modernmods.phosphophylliterevived.registry.RegisterTile;
import modernmods.phosphophylliterevived.util.BlockStates;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static modernmods.biggerreactorsrevived.multiblocks.reactor.blocks.ReactorAccessPort.PortDirection.*;

@ParametersAreNonnullByDefault
public class ReactorCoolantPortTile extends ReactorBaseTile implements IPhosphophylliteFluidHandler, MenuProvider, IHasUpdatableState<ReactorCoolantPortState>, IEventMultiblock.AssemblyStateTransition {
    
    @RegisterTile("reactor_coolant_port")
    public static final BlockEntityType.BlockEntitySupplier<ReactorCoolantPortTile> SUPPLIER = new RegisterTile.Producer<>(ReactorCoolantPortTile::new);
    
    public ReactorCoolantPortTile(BlockEntityType<?> TYPE, BlockPos pos, BlockState state) {
        super(TYPE, pos, state);
    }
    
    
    @Nullable
    @Override
    public <T> T capability(BlockCapability<T, Direction> cap, @Nullable Direction side) {
        if (cap == Capabilities.Fluid.BLOCK) {
            //noinspection unchecked
            return (T) modernmods.phosphophylliterevived.transfer.FluidResourceHandler.of(this);
        }
        if (modernmods.phosphophylliterevived.capabilities.MekanismPresence.loaded() && cap == MekanismCapabilities.CHEMICAL_HANDLER) {
            //noinspection unchecked
            return (T) MekanismGasWrappers.wrap(this);
        }
        return super.capability(cap, side);
    }
    
    @Nullable
    private ReactorTransitionTank transitionTank;
    
    @Override
    public int tankCount() {
        if (transitionTank == null) {
            return 0;
        }
        return transitionTank.tankCount();
    }
    
    @Override
    public long tankCapacity(int tank) {
        if (transitionTank == null) {
            return 0;
        }
        return transitionTank.tankCapacity(tank);
    }
    
    @Override
    public Fluid fluidTypeInTank(int tank) {
        if (transitionTank == null) {
            return Fluids.EMPTY;
        }
        return transitionTank.fluidTypeInTank(tank);
    }
    
    @Override
    public DataComponentPatch fluidComponentsInTank(int tank) {
        if (transitionTank == null) {
            return DataComponentPatch.EMPTY;
        }
        return transitionTank.fluidComponentsInTank(tank);
    }
    
    @Override
    public long fluidAmountInTank(int tank) {
        if (transitionTank == null) {
            return 0;
        }
        return transitionTank.fluidAmountInTank(tank);
    }
    
    @Override
    public boolean fluidValidForTank(int tank, Fluid fluid) {
        if (transitionTank == null) {
            return false;
        }
        return transitionTank.fluidValidForTank(tank, fluid);
    }
    
    @Override
    public long fill(Fluid fluid, DataComponentPatch components, long amount, boolean simulate) {
        if (transitionTank == null || direction != INLET) {
            return 0;
        }
        return transitionTank.fill(fluid, components, amount, simulate);
    }
    
    @Override
    public long drain(Fluid fluid, DataComponentPatch components, long amount, boolean simulate) {
        if (transitionTank == null || direction == INLET) {
            return 0;
        }
        return transitionTank.drain(fluid, components, amount, simulate);
    }
    
    public long pushFluid() {
        if (!connected || direction == INLET) {
            return 0;
        }
        if (handler != null && transitionTank != null) {
            Fluid fluid = transitionTank.vaporType();
            long amount = transitionTank.vaporAmount();
            amount = transitionTank.drain(fluid, DataComponentPatch.EMPTY, amount, true);
            amount = handler.fill(fluid, DataComponentPatch.EMPTY, amount, false);
            amount = transitionTank.drain(fluid, DataComponentPatch.EMPTY, amount, false);
            return amount;
        } else {
            handler = null;
            connected = false;
        }
        return 0;
    }
    
    private boolean connected = false;
    Direction steamOutputDirection = null;
    
    IPhosphophylliteFluidHandler handler = null;
    private ReactorAccessPort.PortDirection direction = INLET;
    public final ReactorCoolantPortState reactorCoolantPortState = new ReactorCoolantPortState(this);
    
    @SuppressWarnings("DuplicatedCode")
    public void neighborChanged() {
        handler = null;
        if (steamOutputDirection == null) {
            connected = false;
            return;
        }
        assert level != null;
        BlockEntity te = level.getBlockEntity(worldPosition.relative(steamOutputDirection));
        if (te == null) {
            connected = false;
            return;
        }
        connected = false;
        final var fluidHandler = level.getCapability(Capabilities.Fluid.BLOCK, te.getBlockPos(), steamOutputDirection.getOpposite());
        if (fluidHandler != null) {
            connected = true;
            handler = ResourceFluidHandlerWrapper.wrap(fluidHandler);
        } else {
            if (!modernmods.phosphophylliterevived.capabilities.MekanismPresence.loaded()) {
                return;
            }
            final var chemicalHandler = level.getCapability(MekanismCapabilities.CHEMICAL_HANDLER, te.getBlockPos(), steamOutputDirection.getOpposite());
            if (chemicalHandler != null) {
                connected = true;
                handler = MekanismGasWrappers.wrap(chemicalHandler);
            }
        }
    }
    
    public void setDirection(ReactorAccessPort.PortDirection direction) {
        this.direction = direction;
        this.setChanged();
    }
    
    @Override
    protected void readNBT(CompoundTag compound) {
        if (compound.contains("direction")) {
            direction = ReactorAccessPort.PortDirection.valueOf(compound.getStringOr("direction", ""));
        }
    }
    
    @Override
    
    protected CompoundTag writeNBT() {
        CompoundTag NBT = new CompoundTag();
        NBT.putString("direction", String.valueOf(direction));
        return NBT;
    }
    
    @Override
    public void runRequest(String requestName, Object requestData) {
        // Change IO direction.
        if (requestName.equals("setDirection")) {
            this.setDirection(((Integer) requestData != 0) ? OUTLET : INLET);
            assert level != null;
            level.setBlock(this.worldPosition, this.getBlockState().setValue(PORT_DIRECTION_ENUM_PROPERTY, direction), 3);
        }
        
        super.runRequest(requestName, requestData);
    }
    
    @Override
    public Component getDisplayName() {
        return Component.translatable(ReactorCoolantPort.INSTANCE.getDescriptionId());
    }
    
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
        return new ReactorCoolantPortContainer(windowId, this.worldPosition, player);
    }
    
    @Override
    
    public ReactorCoolantPortState getState() {
        this.updateState();
        return this.reactorCoolantPortState;
    }
    
    @Override
    public void updateState() {
        reactorCoolantPortState.direction = (this.direction == INLET);
    }
    
    @Override
    public void onAssemblyStateTransition(IValidatedMultiblock.AssemblyState oldState, IValidatedMultiblock.AssemblyState newState) {
        assert level != null;
        if (newState == IValidatedMultiblock.AssemblyState.ASSEMBLED) {
            level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(PORT_DIRECTION_ENUM_PROPERTY, direction), 3);
            steamOutputDirection = getBlockState().getValue(BlockStates.FACING);
            transitionTank = controller().coolantTank();
        } else {
            steamOutputDirection = null;
            transitionTank = null;
        }
        neighborChanged();
    }
}
