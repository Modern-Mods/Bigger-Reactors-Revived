package modernmods.biggerreactorsrevived.multiblocks.turbine.simulation;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;
import modernmods.phosphophylliterevived.fluids.IPhosphophylliteFluidHandler;

public interface ITurbineFluidTank extends IPhosphophylliteFluidHandler {
    
    CompoundTag serializeNBT();
    
    void deserializeNBT(CompoundTag nbt);
    default long perSideCapacity() {
        return getTankCapacity(0);
    }
    
    default long liquidAmount() {
        return fluidAmountInTank(1);
    }
    
    default Fluid liquidType() {
        return fluidTypeInTank(1);
    }
    
    default long vaporAmount() {
        return fluidAmountInTank(0);
    }
    
    default Fluid vaporType() {
        return fluidTypeInTank(0);
    }
    
    void dumpLiquid();
    
    void dumpVapor();
}
