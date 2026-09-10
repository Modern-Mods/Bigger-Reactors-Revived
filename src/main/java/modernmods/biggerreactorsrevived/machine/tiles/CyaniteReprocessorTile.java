package modernmods.biggerreactorsrevived.machine.tiles;


import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.IFluidTank;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import modernmods.biggerreactorsrevived.BiggerReactors;
import modernmods.biggerreactorsrevived.Config;
import modernmods.biggerreactorsrevived.api.IWorkHandler;
import modernmods.biggerreactorsrevived.api.WorkHandler;
import modernmods.biggerreactorsrevived.machine.blocks.CyaniteReprocessor;
import modernmods.biggerreactorsrevived.machine.containers.CyaniteReprocessorContainer;
import modernmods.biggerreactorsrevived.machine.state.CyaniteReprocessorState;
import modernmods.biggerreactorsrevived.machine.tiles.impl.CyaniteReprocessorItemHandler;
import modernmods.biggerreactorsrevived.items.ingots.BlutoniumIngot;
import modernmods.phosphophylliterevived.capabilities.IPhosphophylliteCapabilityProvider;
import modernmods.phosphophylliterevived.client.gui.api.IHasUpdatableState;
import modernmods.phosphophylliterevived.debug.DebugTool;
import modernmods.phosphophylliterevived.registry.RegisterTile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class CyaniteReprocessorTile extends BaseContainerBlockEntity implements MenuProvider, IHasUpdatableState<CyaniteReprocessorState>, IPhosphophylliteCapabilityProvider {
    
    @RegisterTile("cyanite_reprocessor")
    public static final BlockEntityType.BlockEntitySupplier<CyaniteReprocessorTile> SUPPLIER = new RegisterTile.Producer<>(CyaniteReprocessorTile::new);
    
    /**
     * The (mostly) current state of the machine.
     */
    public final CyaniteReprocessorState cyaniteReprocessorState = new CyaniteReprocessorState(this);
    /**
     * The work handler.
     */
    private WorkHandler workHandler;
    /**
     * The item handler.
     */
    private CyaniteReprocessorItemHandler itemHandler;
    /**
     * The energy storage.
     */
    private EnergyStorage energyStorage;
    /**
     * The fluid tank.
     */
    private FluidTank fluidTank;
    /**
     * "Anti-cheat" item stack, to ensure players don't swap items mid-process.
     *
     * @see CyaniteReprocessorTile#tick()
     */
    private ItemStack itemPresentLastTick = ItemStack.EMPTY;
    
    public CyaniteReprocessorTile(BlockEntityType<CyaniteReprocessorTile> TYPE, BlockPos pos, BlockState state) {
        super(TYPE, pos, state);
        this.clearContent();
        this.updateState();
    }
    
    /**
     * Do right-click stuff.
     */
    @Nonnull
    public InteractionResult onBlockActivated(@Nonnull BlockState blockState, Level world, @Nonnull BlockPos blockPos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult trace) {
        // Check for client-side.
        if (world.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        
        // Print tile data.
        if (ItemStack.isSameItem(player.getMainHandItem(), new ItemStack(DebugTool.INSTANCE))) {
            player.sendSystemMessage(Component.literal(String.format("[%s] Progress: %s/%s", BiggerReactors.modid, this.cyaniteReprocessorState.workTime, this.cyaniteReprocessorState.workTimeTotal)));
            player.sendSystemMessage(Component.literal(String.format("[%s] Energy: %s/%s RF", BiggerReactors.modid, this.cyaniteReprocessorState.energyStored, this.cyaniteReprocessorState.energyCapacity)));
            player.sendSystemMessage(Component.literal(String.format("[%s] Fluid Tank: %s/%s mB", BiggerReactors.modid, this.cyaniteReprocessorState.waterStored, this.cyaniteReprocessorState.waterCapacity)));
            return InteractionResult.SUCCESS;
        }
        
        // Do water bucket check.
        if (ItemStack.isSameItem(player.getMainHandItem(), new ItemStack(Items.WATER_BUCKET))) {
            if (this.fluidTank.getFluidAmount() <= (Config.CONFIG.CyaniteReprocessor.WaterTankCapacity - 1000)) {
                this.fluidTank.fill(new FluidStack(Fluids.WATER, 1000), IFluidHandler.FluidAction.EXECUTE);
                player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BUCKET));
            }
            return InteractionResult.SUCCESS;
        }
        
        // Get container and open GUI.
        ((ServerPlayer) player).openMenu(this, blockPos);
        return InteractionResult.SUCCESS;
    }
    
    /**
     * Drop items on destruction.
     */
    public void onReplaced(BlockState blockState, Level world, BlockPos blockPos, BlockState newBlockState, boolean isMoving) {
        ItemStack inputStack = this.itemHandler.getStackInSlot(CyaniteReprocessorItemHandler.INPUT_SLOT_INDEX);
        if (!inputStack.isEmpty()) {
            Containers.dropContents(world, blockPos, new SimpleContainer(inputStack));
        }
        
        ItemStack outputStack = this.itemHandler.getStackInSlot(CyaniteReprocessorItemHandler.OUTPUT_SLOT_INDEX);
        if (!outputStack.isEmpty()) {
            Containers.dropContents(world, blockPos, new SimpleContainer(outputStack));
        }
    }
    
    /**
     * @see CyaniteReprocessorTile#getDefaultName()
     */
    @Override
    public Component getDisplayName() {
        return this.getDefaultName();
    }
    
    /**
     * @return The localized default name for the tile.
     */
    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.biggerreactors.cyanite_reprocessor");
    }
    
    /**
     * Create a GUI container for the tile.
     *
     * @param windowId        The window ID to use.
     * @param playerInventory The player's inventory.
     * @return A GUI container to render.
     */
    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory) {
        return new CyaniteReprocessorContainer(windowId, this.getBlockPos(), playerInventory.player);
    }
    
    /**
     * @return The current state of the tile.
     */
    @Override
    @Nonnull
    public CyaniteReprocessorState getState() {
        this.updateState();
        return this.cyaniteReprocessorState;
    }
    
    /**
     * Call for an update to the current state information.
     */
    @Override
    public void updateState() {
        this.cyaniteReprocessorState.workTime = this.workHandler.getProgress();
        this.cyaniteReprocessorState.workTimeTotal = this.workHandler.getGoal();
        this.cyaniteReprocessorState.energyStored = this.energyStorage.getEnergyStored();
        this.cyaniteReprocessorState.energyCapacity = this.energyStorage.getMaxEnergyStored();
        this.cyaniteReprocessorState.waterStored = this.fluidTank.getFluidAmount();
        this.cyaniteReprocessorState.waterCapacity = this.fluidTank.getCapacity();
    }
    
    /**
     * @return How large this tile's inventory is.
     */
    @Override
    public int getContainerSize() {
        return this.itemHandler.getSlots();
    }
    
    /**
     * @return Whether or not the inventory is empty.
     */
    @Override
    public boolean isEmpty() {
        for (int index = 0; index < this.itemHandler.getSlots(); ++index) {
            if (!this.itemHandler.getStackInSlot(index).isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Gets the item held in the specified slot.
     *
     * @param index The slot index to fetch from.
     * @return Any items held in that slot.
     */
    @Override
    public ItemStack getItem(int index) {
        return this.itemHandler.getStackInSlot(index);
    }
    
    /**
     * Removes the specified number of items from the specified slot.
     *
     * @param index The slot index to remove from.
     * @param count The number of items to remove.
     * @return The items that were removed.
     */
    @Override
    public ItemStack removeItem(int index, int count) {
        return this.itemHandler.getStackInSlot(index).split(count);
    }
    
    /**
     * Removes an entire stack fromm the specified slot.
     *
     * @param index The slot index to remove from.
     * @return The items that were removed.
     */
    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack itemStack = this.itemHandler.getStackInSlot(index).copy();
        this.itemHandler.setStackInSlot(index, ItemStack.EMPTY);
        return itemStack;
    }
    
    /**
     * Updates the stored items in the specified slot.
     *
     * @param index The slot index to update.
     * @param stack The items to update with.
     */
    @Override
    public void setItem(int index, ItemStack stack) {
        ItemStack oldStack = this.itemHandler.getStackInSlot(index);
        boolean flag = !stack.isEmpty() && ItemStack
                .isSameItem(stack, oldStack);
        this.itemHandler.setStackInSlot(index, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
        
        if (index == 0 && !flag) {
            this.workHandler.clear();
            this.setChanged();
        }
    }
    
    @Override
    protected NonNullList<ItemStack> getItems() {
        final var items = NonNullList.withSize(this.itemHandler.getSlots(), ItemStack.EMPTY);
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            items.set(i, this.itemHandler.getStackInSlot(i));
        }
        return items;
    }
    
    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        for (int i = 0; i < items.size() && i < this.itemHandler.getSlots(); i++) {
            this.itemHandler.setStackInSlot(i, items.get(i));
        }
    }
    
    /**
     * Clears all data and inventory for this tile.
     */
    @Override
    public void clearContent() {
        // Reset work.
        this.workHandler = new WorkHandler(Config.CONFIG.CyaniteReprocessor.TotalWorkTime);
        
        // Reset items.
        this.itemHandler = new CyaniteReprocessorItemHandler();
        this.itemHandler.setSize(2);
        
        // Reset energy.
        this.energyStorage = new EnergyStorage(Config.CONFIG.CyaniteReprocessor.EnergyTankCapacity);
        
        // Reset fluids.
        this.fluidTank = new FluidTank(Config.CONFIG.CyaniteReprocessor.WaterTankCapacity, fluid -> fluid.getFluid() == Fluids.WATER);
    }
    
    /**
     * Checks if the player can currently use this tile.
     *
     * @param player The player to check.
     * @return True if usable, false otherwise.
     */
    @Override
    public boolean stillValid(Player player) {
        assert this.level != null;
        if (this.level.getBlockEntity(this.getBlockPos()) != this) {
            return false;
        } else {
            return player.distanceToSqr(
                    (double) this.getBlockPos().getX() + 0.5D,
                    (double) this.getBlockPos().getY() + 0.5D,
                    (double) this.getBlockPos().getZ() + 0.5D) <= 64.0D;
        }
    }
    
    /**
     * Read NBT data from the world.
     *
     * @param parentCompound The parent compound to read from.
     */
    @Override
    protected void loadAdditional(@Nonnull ValueInput input) {
        super.loadAdditional(input);
        final var child = input.childOrEmpty("cyaniteReprocessorState");

        this.workHandler = new WorkHandler(child.getIntOr("workTimeTotal", 0), child.getIntOr("workTime", 0));
        this.itemHandler.deserialize(child.childOrEmpty("inventory"));
        this.energyStorage = new EnergyStorage(child.getIntOr("energyCapacity", 0),
                Config.CONFIG.CyaniteReprocessor.TransferRate,
                Config.CONFIG.CyaniteReprocessor.TransferRate,
                child.getIntOr("energyStored", 0));
        this.fluidTank.deserialize(child.childOrEmpty("fluidStorage"));
    }
    
    /**
     * Save NBT data to the world.
     *
     * @param parentCompound The parent compound to append onto.
     * @return The updated compound.
     */
    @Override
    protected void saveAdditional(@Nonnull ValueOutput output) {
        super.saveAdditional(output);
        final var child = output.child("cyaniteReprocessorState");

        child.putInt("workTime", this.workHandler.getProgress());
        child.putInt("workTimeTotal", this.workHandler.getGoal());
        this.itemHandler.serialize(child.child("inventory"));
        child.putInt("energyStored", this.energyStorage.getEnergyStored());
        child.putInt("energyCapacity", this.energyStorage.getMaxEnergyStored());
        this.fluidTank.serialize(child.child("fluidStorage"));
    }
    
    /**
     * @return Whether or not the machine can perform work.
     * @see CyaniteReprocessorTile#tick()
     */
    private boolean canWork() {
        // If the output slot is full, we cannot work.
        if (this.getItem(CyaniteReprocessorItemHandler.OUTPUT_SLOT_INDEX).getCount() >= 64) {
            return false;
        }
        return (this.energyStorage.getEnergyStored() >= Config.CONFIG.CyaniteReprocessor.EnergyConsumptionPerTick
                && this.fluidTank.getFluidAmount() >= Config.CONFIG.CyaniteReprocessor.WaterConsumptionPerTick);
    }
    
    /**
     * Do work (if possible).
     */
    public void tick() {
        // Check for client-side.
        assert level != null;
        if (level.isClientSide()) {
            return;
        }
        
        boolean doUpdate = false;
        boolean isActive = false;
        ItemStack inputStack = this.itemHandler.getStackInSlot(CyaniteReprocessorItemHandler.INPUT_SLOT_INDEX);
        
        // Check to make sure the player doesn't try to pull a fast one.
        if (!ItemStack.isSameItem(this.itemPresentLastTick, inputStack)) {
            this.workHandler.clear();
        }
        this.itemPresentLastTick = inputStack.copy();
        
        if (inputStack.getCount() >= 2) {
            // Can we continue?
            if (canWork()) {
                isActive = true;
                doUpdate = true;
                this.workHandler.increment(1);
                this.energyStorage.extractEnergy(Config.CONFIG.CyaniteReprocessor.EnergyConsumptionPerTick, false);
                this.fluidTank.drain(Config.CONFIG.CyaniteReprocessor.WaterConsumptionPerTick, IFluidHandler.FluidAction.EXECUTE);
                // We've run out of resources, halt.
            } else if (this.workHandler.getProgress() > 0) {
                this.workHandler.decrement(2);
            }
            
            // Item is done.
            if (this.workHandler.isFinished()) {
                this.itemHandler.extractItem(CyaniteReprocessorItemHandler.INPUT_SLOT_INDEX, 2, false);
                this.itemHandler.insertItem(CyaniteReprocessorItemHandler.OUTPUT_SLOT_INDEX, new ItemStack(BlutoniumIngot.INSTANCE, 1), false);
                this.workHandler.clear();
            }
        }
        
        BlockState currentBlockState = level.getBlockState(this.getBlockPos());
        BlockState newBlockState = currentBlockState.setValue(CyaniteReprocessor.ENABLED, isActive);
        if (!newBlockState.equals(currentBlockState)) {
            this.level.setBlock(this.getBlockPos(), newBlockState, 3);
            doUpdate = true;
        }
        
        if (doUpdate) {
            setChanged();
        }
        
        // Update the current machine state.
        this.updateState();
    }
    
    /**
     * Check if the tile holds a certain capability.
     *
     * @param capability The capability to check for.
     * @param side       Which side this capability should belong to.
     * @param <T>        The type class of the capability.
     * @return The handler for the capability, if present.
     */
    @Nullable
    @Override
    public <T> T getCapability(BlockCapability<T, Direction> capability, @Nullable Direction side) {
        // Check for work.
        // TODO: While this capability is exclusive to the Reprocessor for now, it may not always be.
        //  The capability is technically implemented, but no registration is done.
        //  That oughta be fixed, so it can be checked for here.
        //  But I'm lazy, so I'll do that some other time.
        // Check for items.
        if (capability == Capabilities.Item.BLOCK) {
            //noinspection unchecked
            return (T) modernmods.phosphophylliterevived.transfer.ItemResourceHandler.of(this.itemHandler.pipeHandler());
        }
        
        // Check for energy.
        if (capability == Capabilities.Energy.BLOCK) {
            //noinspection unchecked
            return (T) this.energyStorage;
        }
        
        // Check for water.
        if (capability == Capabilities.Fluid.BLOCK) {
            //noinspection unchecked
            return (T) modernmods.phosphophylliterevived.transfer.FluidResourceHandler.of(this.fluidTank);
        }
        
        return null;
    }
    
    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return this.itemHandler.isItemValid(index, stack);
    }
    
    public CyaniteReprocessorItemHandler getItemHandler() {
        return itemHandler;
    }
}
