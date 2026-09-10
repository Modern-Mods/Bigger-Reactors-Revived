package modernmods.biggerreactorsrevived.blocks.materials;

import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import modernmods.phosphophylliterevived.registry.RegisterBlock;

public class UraniumOre extends DropExperienceBlock {

    @RegisterBlock(name = "uranium_ore")
    public static final UraniumOre INSTANCE = new UraniumOre();

    public UraniumOre() {
        super(
                net.minecraft.util.valueproviders.ConstantInt.of(0),
                Properties.of()
                        .sound(SoundType.STONE)
                        .explosionResistance(3.0F)
                        .destroyTime(3.0f)
                        .requiresCorrectToolForDrops()
        );
    }
}
