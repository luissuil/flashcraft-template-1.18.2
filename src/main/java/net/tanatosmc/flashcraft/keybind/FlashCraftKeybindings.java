package net.tanatosmc.flashcraft.keybind;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class FlashCraftKeybindings {
    
    private static final String CATEGORY = "category.flashcraft.main";
    
    /** Tecla principal para crafteo rápido */
    public static KeyBinding QUICK_CRAFT;
    
    /** Tecla alternativa para crafteo rápido */
    public static KeyBinding QUICK_CRAFT_ALT;
    
    /** Tecla para añadir/quitar de favoritos */
    public static KeyBinding TOGGLE_FAVORITE;
    
    /** Teclas numéricas para craftear cantidad específica (1-9) */
    public static KeyBinding CRAFT_AMOUNT_PREFIX;
    
    /** Teclas para acceder a favoritos (1-9) */
    public static KeyBinding[] FAVORITE_SLOTS = new KeyBinding[9];
    
    public static void register() {
        QUICK_CRAFT = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.flashcraft.quick_craft",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_SPACE,
            CATEGORY
        ));
        
        QUICK_CRAFT_ALT = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.flashcraft.quick_craft_alt",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_ENTER,
            CATEGORY
        ));
        
        TOGGLE_FAVORITE = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.flashcraft.toggle_favorite",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_F,
            CATEGORY
        ));
        
        CRAFT_AMOUNT_PREFIX = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.flashcraft.craft_amount",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_CONTROL,
            CATEGORY
        ));
        
        // Registrar slots de favoritos (Ctrl + 1-9)
        for (int i = 0; i < 9; i++) {
            final int slotNum = i + 1;
            FAVORITE_SLOTS[i] = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.flashcraft.favorite_" + slotNum,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_1 + i,
                CATEGORY
            ));
        }
    }
    
    /**
     * Verifica si alguna tecla de crafteo rápido está presionada
     */
    public static boolean isQuickCraftKeyPressed(int keyCode) {
        return keyCode == getBoundKey(QUICK_CRAFT) || keyCode == getBoundKey(QUICK_CRAFT_ALT);
    }
    
    /**
     * Verifica si la tecla de favoritos está presionada
     */
    public static boolean isFavoriteKeyPressed(int keyCode) {
        return keyCode == getBoundKey(TOGGLE_FAVORITE);
    }
    
    /**
     * Obtiene el código de tecla vinculado a un KeyBinding
     */
    public static int getBoundKey(KeyBinding binding) {
        return InputUtil.fromTranslationKey(binding.getBoundKeyTranslationKey()).getCode();
    }
    
    /**
     * Verifica si es una tecla numérica (1-9) y retorna el número, o -1 si no
     */
    public static int getNumberKey(int keyCode) {
        if (keyCode >= GLFW.GLFW_KEY_1 && keyCode <= GLFW.GLFW_KEY_9) {
            return keyCode - GLFW.GLFW_KEY_1 + 1;
        }
        if (keyCode >= GLFW.GLFW_KEY_KP_1 && keyCode <= GLFW.GLFW_KEY_KP_9) {
            return keyCode - GLFW.GLFW_KEY_KP_1 + 1;
        }
        return -1;
    }
}
