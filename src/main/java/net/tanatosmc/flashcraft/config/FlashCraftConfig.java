package net.tanatosmc.flashcraft.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FlashCraftConfig {
    
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("flashcraft.json");
    
    private static FlashCraftConfig INSTANCE;
    
    // ===== CONFIGURACIÓN =====
    
    /** Cooldown en milisegundos entre cada crafteo rápido */
    public int cooldownMs = 150;
    
    /** Habilitar crafteo en lote (mantener tecla presionada) */
    public boolean enableBatchCrafting = true;
    
    /** Habilitar el contador visual de crafteos */
    public boolean showCraftCounter = true;
    
    /** Habilitar el sistema de favoritos */
    public boolean enableFavorites = true;
    
    /** Máximo de recetas favoritas */
    public int maxFavorites = 9;
    
    /** Tiempo en ms que se muestra el contador después de craftear */
    public int counterDisplayTimeMs = 3000;
    
    /** Habilitar sonido de cooldown bloqueado */
    public boolean playCooldownSound = true;
    
    // ===== MÉTODOS =====
    
    public static FlashCraftConfig get() {
        if (INSTANCE == null) {
            load();
        }
        return INSTANCE;
    }
    
    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                INSTANCE = GSON.fromJson(json, FlashCraftConfig.class);
                if (INSTANCE == null) {
                    INSTANCE = new FlashCraftConfig();
                }
            } catch (IOException e) {
                System.err.println("[FlashCraft] Error loading config: " + e.getMessage());
                INSTANCE = new FlashCraftConfig();
            }
        } else {
            INSTANCE = new FlashCraftConfig();
            save();
        }
    }
    
    public static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(INSTANCE));
        } catch (IOException e) {
            System.err.println("[FlashCraft] Error saving config: " + e.getMessage());
        }
    }
}
