package modernmods.biggerreactorsrevived.multiblocks.reactor.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import modernmods.biggerreactorsrevived.BiggerReactors;
import modernmods.biggerreactorsrevived.client.CommonRender;
import modernmods.biggerreactorsrevived.multiblocks.reactor.containers.ReactorTerminalContainer;
import modernmods.biggerreactorsrevived.multiblocks.reactor.state.ReactorState;
import modernmods.biggerreactorsrevived.multiblocks.reactor.state.ReactorType;
import modernmods.phosphophylliterevived.client.gui.screens.PhosphophylliteScreen;
import modernmods.phosphophylliterevived.client.gui.RenderHelper;
import modernmods.phosphophylliterevived.client.gui.elements.RenderedElement;
import modernmods.phosphophylliterevived.client.gui.elements.TooltipElement;

import javax.annotation.Nonnull;

public class PassiveReactorTerminalScreen extends PhosphophylliteScreen<ReactorTerminalContainer> {

    private static final Identifier DEFAULT_TEXTURE = Identifier.fromNamespaceAndPath(BiggerReactors.modid, "textures/screen/reactor_terminal_passive.png");

    private ReactorState reactorState;

    public PassiveReactorTerminalScreen(ReactorTerminalContainer container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title, DEFAULT_TEXTURE, 176, 152);

        // Initialize reactor state.
        reactorState = (ReactorState) this.getMenu().getGuiPacket();
    }

    /**
     * Initialize the screen.
     */
    @Override
    public void init() {
        super.init();

        // Initialize tooltips:
        CommonReactorTerminalScreen.initTooltips(this, reactorState);
        this.initTooltips();

        // Initialize controls:
        CommonReactorTerminalScreen.initControls(this, reactorState);

        // Initialize gauges:
        CommonReactorTerminalScreen.initGauges(this, reactorState);
        this.initGauges();

        // Initialize symbols:
        CommonReactorTerminalScreen.initSymbols(this, reactorState);
    }

    /**
     * Initialize tooltips.
     */
    private void initTooltips() {
        // (Left) RF generation rate tooltip:
        this.addScreenElement(new TooltipElement<>(this, 8, 38, 16, 16, Component.translatable("screen.biggerreactors.reactor_terminal.energy_generation_rate.tooltip")));

        // (Left) RF generation readout tooltip:
        TooltipElement<ReactorTerminalContainer> generationRateReadoutTooltip = new TooltipElement<>(this, 26, 38, 53, 16, Component.empty());
        generationRateReadoutTooltip.onTick = () -> {
            generationRateReadoutTooltip.tooltip = Component.literal(String.format("%.3f RF/t", this.reactorState.reactorOutputRate));
        };
        this.addScreenElement(generationRateReadoutTooltip);

        // (Top) Internal battery tooltip:
        this.addScreenElement(new TooltipElement<>(this, 152, 6, 16, 16, Component.translatable("screen.biggerreactors.reactor_terminal.internal_battery.tooltip")));
    }

    /**
     * Initialize gauges.
     */
    private void initGauges() {
        // (Top) Internal battery:
        RenderedElement<ReactorTerminalContainer> internalBattery = new RenderedElement<>(this, 151, 25, 18, 64, 0, 152, Component.empty());
        internalBattery.onRender = (@Nonnull GuiGraphicsExtractor graphics, int mX, int mY) -> CommonRender.renderEnergyGauge(graphics,
                internalBattery, reactorState.energyStored, reactorState.energyCapacity);
        this.addScreenElement(internalBattery);
    }

    /**
     * Tick/update this screen.
     */
    @Override
    public void containerTick() {
        // Update reactor state and tick.
        reactorState = (ReactorState) this.getMenu().getGuiPacket();
        super.containerTick();
        // Check if reactor type changed.
        if (reactorState.reactorType != ReactorType.PASSIVE) {
            this.getMinecraft().setScreen(new ActiveReactorTerminalScreen(this.menu, this.inventory, this.title));
        }
    }

    /**
     * Draw the status text for this screen.
     *
     * @param poseStack    The current pose stack.
     * @param mouseX       The x position of the mouse.
     * @param mouseY       The y position of the mouse.
     * @param partialTicks Partial ticks.
     */
    @Override
    public void extractRenderState(@Nonnull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTicks);

        // Render the other text:
        CommonReactorTerminalScreen.renderStatusText(graphics, this, reactorState.reactorActivity, reactorState.doAutoEject,
                reactorState.fuelHeatStored, reactorState.fuelUsageRate, reactorState.reactivityRate);

        // Render text for output rate:
        graphics.text(this.getFont(), RenderHelper.formatValue(reactorState.reactorOutputRate, "RF/t"), this.getGuiLeft() + 27, this.getGuiTop() + 42, 0xFF404040, false);
    }
}