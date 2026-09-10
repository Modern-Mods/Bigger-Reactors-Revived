package modernmods.biggerreactorsrevived.multiblocks.turbine.tiles;

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
import modernmods.biggerreactorsrevived.multiblocks.turbine.blocks.TurbineFluidPort;
import modernmods.biggerreactorsrevived.multiblocks.turbine.containers.TurbineFluidPortContainer;
import modernmods.biggerreactorsrevived.multiblocks.turbine.simulation.ITurbineFluidTank;
import modernmods.biggerreactorsrevived.multiblocks.turbine.state.TurbineFluidPortState;
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

import static modernmods.biggerreactorsrevived.multiblocks.turbine.blocks.TurbineFluidPort.PortDirection.*;

@ParametersAreNonnullByDefault
public class TurbineFluidPortTile extends TurbineBaseTile implements IPhosphophylliteFluidHandler, MenuProvider, IHasUpdatableState<TurbineFluidPortState>, IEventMultiblock.AssemblyStateTransition.OnAssembly, IEventMultiblock.AssemblyStateTransition.OnDisassembly {
    
    @RegisterTile("turbine_fluid_port")
    public static final BlockEntityType.BlockEntitySupplier<TurbineFluidPortTile> SUPPLIER = new RegisterTile.Producer<>(TurbineFluidPortTile::new);
    
    public TurbineFluidPortTile(BlockEntityType<?> TYPE, BlockPos pos, BlockState state) {
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
    
    private ITurbineFluidTank transitionTank;
    
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
        return transitionTank.fill(fluid, DataComponentPatch.EMPTY, amount, simulate);
    }
    
    @Override
    public long drain(Fluid fluid, DataComponentPatch components, long amount, boolean simulate) {
        if (transitionTank == null || direction == INLET) {
            return 0;
        }
        return transitionTank.drain(fluid, DataComponentPatch.EMPTY, amount, simulate);
    }
    
    public long pushFluid() {
        if (!connected || direction == INLET) {
            return 0;
        }
        if (handler != null) {
            
            Fluid fluid = transitionTank.liquidType();
            long amount = transitionTank.liquidAmount();
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
    Direction waterOutputDirection = null;
    
    IPhosphophylliteFluidHandler handler = null;
    private TurbineFluidPort.PortDirection direction = INLET;
    public final TurbineFluidPortState fluidPortState = new TurbineFluidPortState(this);
    
    @SuppressWarnings("DuplicatedCode")
    public void neighborChanged() {
        handler = null;
        if (waterOutputDirection == null) {
            connected = false;
            return;
        }
        assert level != null;
        BlockEntity te = level.getBlockEntity(worldPosition.relative(waterOutputDirection));
        if (te == null) {
            connected = false;
            return;
        }
        connected = false;
        final var fluidHandler = level.getCapability(Capabilities.Fluid.BLOCK, te.getBlockPos(), waterOutputDirection.getOpposite());
        if (fluidHandler != null) {
            connected = true;
            handler = ResourceFluidHandlerWrapper.wrap(fluidHandler);
        } else {
            if (!modernmods.phosphophylliterevived.capabilities.MekanismPresence.loaded()) {
                return;
            }
            final var chemicalHandler = level.getCapability(MekanismCapabilities.CHEMICAL_HANDLER, te.getBlockPos(), waterOutputDirection.getOpposite());
            if (chemicalHandler != null) {
                connected = true;
                handler = MekanismGasWrappers.wrap(chemicalHandler);
            }
        }
    }
    
    public void setDirection(TurbineFluidPort.PortDirection direction) {
        this.direction = direction;
        this.setChanged();
    }
    
    @Override
    protected void readNBT(CompoundTag compound) {
        if (compound.contains("direction")) {
            direction = TurbineFluidPort.PortDirection.valueOf(compound.getStringOr("direction", ""));
        }
    }
    
    @Override
    protected CompoundTag writeNBT() {
        CompoundTag NBT = new CompoundTag();
        NBT.putString("direction", String.valueOf(direction));
        return NBT;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void runRequest(String requestName, Object requestData) {
        // Change IO direction.
        if (requestName.equals("setDirection")) {
            this.setDirection(((Integer) requestData != 0) ? OUTLET : INLET);
            level.setBlockAndUpdate(this.worldPosition, this.getBlockState().setValue(PORT_DIRECTION_ENUM_PROPERTY, direction));
        }
        super.runRequest(requestName, requestData);
    }
    
    @Override
    public Component getDisplayName() {
        return Component.translatable(TurbineFluidPort.INSTANCE.getDescriptionId());
    }
    
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
        return new TurbineFluidPortContainer(windowId, this.worldPosition, player);
    }
    
    @Nullable
    @Override
    public TurbineFluidPortState getState() {
        this.updateState();
        return this.fluidPortState;
    }
    
    @Override
    public void updateState() {
        fluidPortState.direction = (this.direction == INLET);
    }
    
    
    @Override
    public void onAssemblyStateTransition(IValidatedMultiblock.AssemblyState oldState, IValidatedMultiblock.AssemblyState newState) {
        OnAssembly.super.onAssemblyStateTransition(oldState, newState);
        OnDisassembly.super.onAssemblyStateTransition(oldState, newState);
    }
    
    @Override
    public void onAssembly() {
        this.transitionTank = controller().simulation().fluidTank();
        waterOutputDirection = getBlockState().getValue(BlockStates.FACING);
        level.setBlockAndUpdate(worldPosition, level.getBlockState(worldPosition).setValue(PORT_DIRECTION_ENUM_PROPERTY, direction));
        neighborChanged();
    }
    
    @Override
    public void onDisassembly() {
        waterOutputDirection = null;
        transitionTank = null;
        neighborChanged();
    }
}
