package modernmods.biggerreactorsrevived.multiblocks.heatexchanger.tiles;

import net.minecraft.core.component.DataComponentPatch;
import mekanism.api.chemical.IChemicalHandler;
import net.minecraft.MethodsReturnNonnullByDefault;
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
import modernmods.biggerreactorsrevived.multiblocks.heatexchanger.blocks.HeatExchangerFluidPortBlock;
import modernmods.biggerreactorsrevived.multiblocks.heatexchanger.containers.HeatExchangerFluidPortContainer;
import modernmods.biggerreactorsrevived.multiblocks.heatexchanger.state.HeatExchangerFluidPortState;
import modernmods.phosphophylliterevived.client.gui.api.IHasUpdatableState;
import modernmods.phosphophylliterevived.fluids.FluidHandlerWrapper;
import modernmods.phosphophylliterevived.fluids.IPhosphophylliteFluidHandler;
import modernmods.phosphophylliterevived.fluids.MekanismGasWrappers;
import modernmods.phosphophylliterevived.multiblock.common.IEventMultiblock;
import modernmods.phosphophylliterevived.multiblock.validated.IValidatedMultiblock;
import modernmods.phosphophylliterevived.registry.RegisterTile;
import modernmods.phosphophylliterevived.util.BlockStates;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static modernmods.biggerreactorsrevived.multiblocks.heatexchanger.blocks.HeatExchangerFluidPortBlock.CONDENSER;
import static modernmods.phosphophylliterevived.util.BlockStates.PORT_DIRECTION;


@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class HeatExchangerFluidPortTile extends HeatExchangerBaseTile implements IPhosphophylliteFluidHandler, IEventMultiblock.AssemblyStateTransition.OnAssembly, IEventMultiblock.AssemblyStateTransition.OnDisassembly, MenuProvider, IHasUpdatableState<HeatExchangerFluidPortState> {
    
    public long lastCheckedTick;
    
    @RegisterTile("heat_exchanger_fluid_port")
    public static final BlockEntityType.BlockEntitySupplier<HeatExchangerFluidPortTile> SUPPLIER = new RegisterTile.Producer<>(HeatExchangerFluidPortTile::new);
    
    public HeatExchangerFluidPortTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }
    
    

    @Nullable
    @Override
    public <T> T capability(BlockCapability<T, Direction> cap, @Nullable Direction side) {
        if (cap == Capabilities.FluidHandler.BLOCK) {
            //noinspection unchecked
            return (T) this;
        }
        if (cap == MekanismCapabilities.CHEMICAL_HANDLER) {
            //noinspection unchecked
            return (T) MekanismGasWrappers.wrap(this);
        }
        return super.capability(cap, side);
    }
    
    private IPhosphophylliteFluidHandler HETank;
    
    public void setHETank(IPhosphophylliteFluidHandler HETank) {
        this.HETank = HETank;
    }
    
    private boolean inlet = true;
    private boolean condenser = true;
    
    public void setInlet(boolean inlet) {
        assert level != null;
        this.inlet = inlet;
        level.setBlock(this.getBlockPos(), this.getBlockState().setValue(PORT_DIRECTION, inlet), 3);
        setChanged();
    }
    
    public void setInletOtherOutlet(boolean inlet) {
        controller().setInletPort(this, inlet);
    }
    
    public boolean isInlet() {
        return inlet;
    }
    
    public void setCondenser(boolean condenser) {
        this.condenser = condenser;
        assert level != null;
        level.setBlock(this.getBlockPos(), this.getBlockState().setValue(CONDENSER, condenser), 3);
    }
    
    public boolean isCondenser() {
        return condenser;
    }
    
    @Override
    public int tankCount() {
        if (HETank == null) {
            return 0;
        }
        return HETank.tankCount();
    }
    
    @Override
    public long tankCapacity(int tank) {
        if (HETank == null) {
            return 0;
        }
        return HETank.tankCapacity(tank);
    }
    
    
    @Override
    public Fluid fluidTypeInTank(int tank) {
        if (HETank == null) {
            return Fluids.EMPTY;
        }
        return HETank.fluidTypeInTank(tank);
    }
    
    @Override
    public DataComponentPatch fluidComponentsInTank(int tank) {
        if (HETank == null) {
            return DataComponentPatch.EMPTY;
        }
        return HETank.fluidComponentsInTank(tank);
    }
    
    @Override
    public long fluidAmountInTank(int tank) {
        if (HETank == null) {
            return 0;
        }
        return HETank.fluidAmountInTank(tank);
    }
    
    @Override
    public boolean fluidValidForTank(int tank, Fluid fluid) {
        if (HETank == null) {
            return false;
        }
        return HETank.fluidValidForTank(tank, fluid);
    }
    
    @Override
    public long fill(Fluid fluid, DataComponentPatch components, long amount, boolean simulate) {
        if (HETank == null || !inlet) {
            return 0;
        }
        return HETank.fill(fluid, DataComponentPatch.EMPTY, amount, simulate);
    }
    
    @Override
    public long drain(Fluid fluid, DataComponentPatch components, long amount, boolean simulate) {
        if (HETank == null || inlet) {
            return 0;
        }
        return HETank.drain(fluid, DataComponentPatch.EMPTY, amount, simulate);
    }
    
    
    public long pushFluid() {
        if (!connected || inlet) {
            return 0;
        }
        if (handler != null) {
            Fluid fluid = HETank.fluidTypeInTank(1);
            long amount = HETank.fluidAmountInTank(1);
            amount = HETank.drain(fluid, DataComponentPatch.EMPTY, amount, true);
            amount = handler.fill(fluid, DataComponentPatch.EMPTY, amount, false);
            amount = HETank.drain(fluid, DataComponentPatch.EMPTY, amount, false);
            return amount;
        } else {
            handler = null;
            connected = false;
        }
        return 0;
    }
    
    private boolean connected = false;
    Direction outputDirection = null;
    IPhosphophylliteFluidHandler handler = null;
    
    @SuppressWarnings("DuplicatedCode")
    public void neighborChanged() {
        handler = null;
        if (outputDirection == null) {
            connected = false;
            return;
        }
        assert level != null;
        BlockEntity te = level.getBlockEntity(worldPosition.relative(outputDirection));
        if (te == null) {
            connected = false;
            return;
        }
        connected = false;
        final var fluidHandler = level.getCapability(Capabilities.FluidHandler.BLOCK, te.getBlockPos(), outputDirection.getOpposite());
        if (fluidHandler != null) {
            connected = true;
            handler = FluidHandlerWrapper.wrap(fluidHandler);
        } else {
            final var chemicalHandler = level.getCapability(MekanismCapabilities.CHEMICAL_HANDLER, te.getBlockPos(), outputDirection.getOpposite());
            if (chemicalHandler != null) {
                connected = true;
                handler = MekanismGasWrappers.wrap(chemicalHandler);
            }
        }
    }
    
    @Override
    protected void readNBT(CompoundTag compound) {
        super.readNBT(compound);
        inlet = compound.getBoolean("inlet");
    }
    
    
    @Override
    protected CompoundTag writeNBT() {
        CompoundTag nbt = super.writeNBT();
        nbt.putBoolean("inlet", inlet);
        return nbt;
    }
    
    @Override
    public void onAssemblyStateTransition(IValidatedMultiblock.AssemblyState oldState, IValidatedMultiblock.AssemblyState newState) {
        OnAssembly.super.onAssemblyStateTransition(oldState, newState);
        OnDisassembly.super.onAssemblyStateTransition(oldState, newState);
    }
    
    @Override
    public void onAssembly() {
        outputDirection = getBlockState().getValue(BlockStates.FACING);
        neighborChanged();
    }
    
    @Override
    public void onDisassembly() {
        outputDirection = null;
        HETank = null;
        neighborChanged();
    }
    
    private final HeatExchangerFluidPortState state = new HeatExchangerFluidPortState(this);
    
    @Override
    public HeatExchangerFluidPortState getState() {
        return state;
    }
    
    @Override
    public void updateState() {
        state.direction = isInlet();
        state.condenser = isCondenser();
    }
    
    @Override
    public Component getDisplayName() {
        return Component.translatable(HeatExchangerFluidPortBlock.INSTANCE.getDescriptionId());
    }
    
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
        return new HeatExchangerFluidPortContainer(windowId, this.worldPosition, player);
    }

    @Override
    public void runRequest(String requestName, Object requestData) {
        if (requestName.equals("setDirection")) {
            int direction = (Integer) requestData;
            setInletOtherOutlet(direction == 0);
        }

        super.runRequest(requestName, requestData);
    }
}
