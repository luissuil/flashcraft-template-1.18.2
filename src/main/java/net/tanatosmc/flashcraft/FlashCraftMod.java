package net.tanatosmc.flashcraft;

import net.fabricmc.api.ClientModInitializer;
import net.tanatosmc.flashcraft.config.FlashCraftConfig;
import net.tanatosmc.flashcraft.keybind.FlashCraftKeybindings;

public class FlashCraftMod implements ClientModInitializer {
    
    public static final String MOD_ID = "flashcraft";
    
    @Override
    public void onInitializeClient() {
        // Cargar configuración
        FlashCraftConfig.load();
        
        // Registrar keybindings
        FlashCraftKeybindings.register();
    }
}
