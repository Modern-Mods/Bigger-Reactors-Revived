package modernmods.biggerreactorsrevived.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;

public final class KeyModifiers {

    private static final boolean MAC = System.getProperty("os.name", "").toLowerCase(java.util.Locale.ROOT).contains("mac");

    public static boolean isSelectAll(int keyCode) {
        return keyCode == 65 && control() && !shift() && !alt();
    }

    public static boolean isCopy(int keyCode) {
        return keyCode == 67 && control() && !shift() && !alt();
    }

    public static boolean isPaste(int keyCode) {
        return keyCode == 86 && control() && !shift() && !alt();
    }

    public static boolean isCut(int keyCode) {
        return keyCode == 88 && control() && !shift() && !alt();
    }

    public static boolean shift() {
        return down(340, 344);
    }

    public static boolean control() {
        return MAC ? down(343, 347) : down(341, 345);
    }

    public static boolean alt() {
        return down(342, 346);
    }

    private static boolean down(int left, int right) {
        final var window = Minecraft.getInstance().getWindow();
        return InputConstants.isKeyDown(window, left) || InputConstants.isKeyDown(window, right);
    }
}
