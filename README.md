# FlashCraft

A minimalist Fabric mod for Minecraft 1.18.2 that adds quick crafting functionality to the recipe book.

## Features

- **Space/Enter Crafting**: Press Space or Enter while hovering over a recipe to craft instantly
- **Batch Crafting**: Hold Space/Enter to continuously craft the same recipe
- **Shift Support**: Hold Shift while pressing Space/Enter to craft 1 stack at a time
- **150ms Cooldown**: Prevents accidental double-clicks and spamming

## Installation

1. Download the latest `.jar` file from the [Releases](https://github.com/luissuil/flashcraft-template-1.18.2/releases)
2. Place it in your `mods` folder
3. Launch Minecraft with Fabric Loader installed

## Requirements

- Minecraft 1.18.2
- Fabric Loader (≥0.18.1)
- Fabric API

## Controls

| Action | Key |
|--------|-----|
| Quick Craft | Space or Enter |
| Batch Craft | Hold Space/Enter |
| Craft 1 Stack | Shift + Space/Enter |

## License

CC0-1.0 - Feel free to use this code in any way you want.
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
