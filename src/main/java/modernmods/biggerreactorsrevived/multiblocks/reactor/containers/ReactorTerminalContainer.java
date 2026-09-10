package modernmods.biggerreactorsrevived.multiblocks.reactor.containers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import modernmods.biggerreactorsrevived.multiblocks.reactor.blocks.ReactorTerminal;
import modernmods.biggerreactorsrevived.multiblocks.reactor.tiles.ReactorTerminalTile;
import modernmods.phosphophylliterevived.client.gui.GuiSync;
import modernmods.phosphophylliterevived.registry.ContainerSupplier;
import modernmods.phosphophylliterevived.registry.RegisterContainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@RegisterContainer(name = "reactor_terminal")
public class ReactorTerminalContainer extends AbstractContainerMenu implements GuiSync.IGUIPacketProvider {

    @RegisterContainer.Type
    public static MenuType<ReactorTerminalContainer> INSTANCE;
    @RegisterContainer.Supplier
    public static final ContainerSupplier SUPPLIER = ReactorTerminalContainer::new;
    
    private Player player;
    private ReactorTerminalTile tileEntity;

    public ReactorTerminalContainer(int windowId, BlockPos blockPos, Player player) {
        super(INSTANCE, windowId);
        this.player = player;
        this.tileEntity = (ReactorTerminalTile) player.level().getBlockEntity(blockPos);
        this.getGuiPacket();
    }

    /**
     * @return The current state of the machine.
     */
    @Nullable
    @Override
    public GuiSync.IGUIPacket getGuiPacket() {
        return this.tileEntity.getState();
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        assert tileEntity.getLevel() != null;
        return stillValid(ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos()),
                player, ReactorTerminal.INSTANCE);
    }

    @Override
    public void executeRequest(String requestName, Object requestData) {
        assert tileEntity.getLevel() != null;
        if (tileEntity.getLevel().isClientSide) {
            runRequest(requestName, requestData);
            return;
        }

        tileEntity.runRequest(requestName, requestData);
    }
    
    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return ItemStack.EMPTY;
    }
}
