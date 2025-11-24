# FlashCraft

<div align="center">

![Minecraft Version](https://img.shields.io/badge/Minecraft-1.18.2-brightgreen)
![Mod Loader](https://img.shields.io/badge/Mod%20Loader-Fabric-dbd0b4)
![Java Version](https://img.shields.io/badge/Java-17-orange)
![License](https://img.shields.io/badge/License-CC0--1.0-blue)

**Backports the Quick Crafting feature from Snapshot 24w34a to Minecraft 1.18.2**

[English](#-overview) | [Español](README_ES.md)

</div>

---

## 📖 Overview

**FlashCraft** is a lightweight client-side Fabric mod that brings the modern "Quick Crafting" functionality from Minecraft 1.21.2 (Snapshot 24w34a) back to version 1.18.2. It enhances the Recipe Book interface by allowing players to quickly re-craft items using keyboard shortcuts, making repetitive crafting tasks significantly faster and more convenient.

## ✨ Features

### Quick Re-Crafting
- **Press `Space` or `Enter`** while hovering over a recipe in the Recipe Book to instantly craft it
- **Hold `Shift` + `Space/Enter`** to craft as many items as possible with your available materials
- **Hold the key** to craft continuously (batch crafting)
- **Audio Feedback**: Different sounds for successful crafts vs cooldown blocks

### ⚙️ Fully Configurable
- **Custom Keybindings**: Change craft keys in Controls settings
- **Config File**: Adjust all settings in `config/flashcraft.json`
  - `cooldownMs` - Delay between crafts (default: 150ms)
  - `enableBatchCrafting` - Toggle batch crafting
  - `showCraftCounter` - Toggle visual counter
  - `enableFavorites` - Toggle favorites system
  - `playCooldownSound` - Toggle cooldown sound

### 📊 Craft Counter
- Visual counter showing how many items you've crafted this session
- Displays in the Recipe Book area
- Auto-hides after 3 seconds of inactivity

### 🔢 Craft Specific Amounts
- Press **Ctrl + Number** before crafting to set a target amount
- Example: `Ctrl+3+2` then `Space` = craft exactly 32 items
- Sound notification when target is reached

### ⭐ Favorites System
- Press **F** on a recipe to mark it as favorite
- Favorites are marked with ★ and their slot number
- Quick access to your most-used recipes

### Vanilla-Like Experience
- **100% Client-Side**: No server installation required
- **Native Integration**: Seamlessly blends with the default Recipe Book UI
- **Performance**: Lightweight implementation using Mixin injections
- **Compatibility**: Works with vanilla servers and other mods

## 🎮 How to Use

1. Open any crafting interface (Crafting Table, Inventory crafting grid, etc.)
2. Click on a recipe in the Recipe Book to select it
3. Press `Space` or `Enter` to craft one batch
4. **Hold** the key to craft continuously
5. Hold `Shift` + key to craft maximum amount per craft
6. Press `Ctrl + Number` to set a specific craft amount
7. Press `F` to toggle favorite on selected recipe

## ⌨️ Default Keybindings

| Key | Action |
|-----|--------|
| `Space` | Quick Craft |
| `Enter` | Quick Craft (Alt) |
| `F` | Toggle Favorite |
| `Ctrl + 1-9` | Set craft amount |

*All keys can be changed in Options > Controls > FlashCraft*

## 📦 Installation

### Requirements
- **Minecraft**: 1.18.2
- **Fabric Loader**: 0.18.1 or higher
- **Fabric API**: Required
- **Java**: 17 or higher

### Steps
1. Install [Fabric Loader](https://fabricmc.net/use/) for Minecraft 1.18.2
2. Download [Fabric API](https://modrinth.com/mod/fabric-api) for 1.18.2
3. Download the latest release of FlashCraft from [Releases](#) / [Modrinth](#) / [CurseForge](#)
4. Place both `.jar` files in your `.minecraft/mods` folder
5. Launch Minecraft with the Fabric profile

## 🔧 Technical Details

### Architecture
FlashCraft uses **Mixin** technology to inject functionality into Minecraft's Recipe Book system:

- **`RecipeBookResultsMixin`**: Captures and stores the last clicked recipe
- **`RecipeBookWidgetMixin`**: Intercepts keyboard input and triggers crafting
- **`IRecipeBookResults`**: Duck interface for storing recipe state
- **`FlashCraftConfig`**: Configuration system with JSON file
- **`FlashCraftKeybindings`**: Configurable keybinding registration
- **`CraftingState`**: Global crafting system state

### Why This Approach?
- **Non-invasive**: Doesn't override or replace vanilla classes
- **Compatible**: Works alongside other mods that modify crafting
- **Maintainable**: Clean separation between mod logic and vanilla code

## 🤝 Compatibility

### Known Compatible Mods
- ✅ Recipe Book mods (REI, JEI alternatives)
- ✅ Inventory management mods
- ✅ QoL (Quality of Life) mods

### Potential Conflicts
- ⚠️ Mods that completely replace the Recipe Book UI might not work
- ⚠️ Mods that rebind `Space` or `Enter` in crafting screens may conflict

*If you encounter issues with specific mods, please report them in the [Issues](../../issues) section.*

## 🐛 Bug Reports & Suggestions

Found a bug or have an idea? Please open an issue on the [GitHub Issues](../../issues) page with:
- Minecraft version
- Fabric Loader version
- FlashCraft version
- Steps to reproduce (for bugs)
- Crash log (if applicable)

## 📜 License

This project is licensed under **CC0-1.0** (Public Domain). You are free to use, modify, and distribute this mod without restrictions.

## 💖 Credits

- **Author**: [luissuil](https://github.com/luissuil)
- **Inspired by**: Minecraft Snapshot 24w34a Quick Crafting feature
- **Built with**: [Fabric](https://fabricmc.net/), [Fabric API](https://github.com/FabricMC/fabric), [Mixin](https://github.com/SpongePowered/Mixin)

---

<div align="center">

**Enjoy faster crafting! ⚡**

[Report Bug](../../issues) · [Request Feature](../../issues) · [Modrinth](#) · [CurseForge](#)

</div>
