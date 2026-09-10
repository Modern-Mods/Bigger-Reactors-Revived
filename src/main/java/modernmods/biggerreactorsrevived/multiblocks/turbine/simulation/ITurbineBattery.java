package modernmods.biggerreactorsrevived.multiblocks.turbine.simulation;

import net.minecraft.nbt.CompoundTag;

public interface ITurbineBattery {
    
    CompoundTag serializeNBT();
    
    void deserializeNBT(CompoundTag nbt);
    long extract(long toExtract);
    
    long stored();
    
    long capacity();
}
