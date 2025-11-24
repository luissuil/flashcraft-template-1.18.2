# FlashCraft

<div align="center">

![Minecraft Version](https://img.shields.io/badge/Minecraft-1.18.2-brightgreen)
![Mod Loader](https://img.shields.io/badge/Mod%20Loader-Fabric-dbd0b4)
![Java Version](https://img.shields.io/badge/Java-17-orange)
![License](https://img.shields.io/badge/License-CC0--1.0-blue)

**Trae la función de Crafteo Rápido del Snapshot 24w34a a Minecraft 1.18.2**

[English](README.md) | [Español](#-descripción)

</div>

---

## 📖 Descripción

**FlashCraft** es un mod de Fabric ligero del lado del cliente que trae la funcionalidad moderna de "Crafteo Rápido" de Minecraft 1.21.2 (Snapshot 24w34a) a la versión 1.18.2. Mejora la interfaz del Libro de Recetas permitiendo a los jugadores re-craftear items rápidamente usando atajos de teclado, haciendo las tareas de crafteo repetitivas significativamente más rápidas y convenientes.

## ✨ Características

### Crafteo Rápido
- **Presiona `Espacio` o `Enter`** mientras pasas sobre una receta en el Libro de Recetas para craftearla instantáneamente
- **Mantén `Shift` + `Espacio/Enter`** para craftear tantos items como sea posible con tus materiales disponibles
- **Mantén presionada la tecla** para craftear continuamente (crafteo en lote)
- **Feedback de Audio**: Diferentes sonidos para crafteos exitosos vs bloqueo por cooldown

### ⚙️ Totalmente Configurable
- **Teclas Personalizables**: Cambia las teclas en Opciones > Controles
- **Archivo de Configuración**: Ajusta todas las opciones en `config/flashcraft.json`
  - `cooldownMs` - Delay entre crafteos (por defecto: 150ms)
  - `enableBatchCrafting` - Activar/desactivar crafteo en lote
  - `showCraftCounter` - Activar/desactivar contador visual
  - `enableFavorites` - Activar/desactivar sistema de favoritos
  - `playCooldownSound` - Activar/desactivar sonido de cooldown

### 📊 Contador de Crafteos
- Contador visual mostrando cuántos items has crafteado en esta sesión
- Se muestra en el área del Libro de Recetas
- Se oculta automáticamente después de 3 segundos de inactividad

### 🔢 Craftear Cantidades Específicas
- Presiona **Ctrl + Número** antes de craftear para establecer una cantidad objetivo
- Ejemplo: `Ctrl+3+2` luego `Espacio` = craftea exactamente 32 items
- Notificación de sonido cuando se alcanza el objetivo

### ⭐ Sistema de Favoritos
- Presiona **F** en una receta para marcarla como favorita
- Los favoritos se marcan con ★ y su número de slot
- Acceso rápido a tus recetas más usadas

### Experiencia Similar a Vanilla
- **100% Lado Cliente**: No requiere instalación en el servidor
- **Integración Nativa**: Se integra perfectamente con la UI del Libro de Recetas
- **Rendimiento**: Implementación ligera usando inyecciones Mixin
- **Compatibilidad**: Funciona con servidores vanilla y otros mods

## 🎮 Cómo Usar

1. Abre cualquier interfaz de crafteo (Mesa de Crafteo, grid de crafteo del inventario, etc.)
2. Haz clic en una receta en el Libro de Recetas para seleccionarla
3. Presiona `Espacio` o `Enter` para craftear un lote
4. **Mantén** la tecla para craftear continuamente
5. Mantén `Shift` + tecla para craftear cantidad máxima por crafteo
6. Presiona `Ctrl + Número` para establecer una cantidad específica
7. Presiona `F` para alternar favorito en la receta seleccionada

## ⌨️ Teclas por Defecto

| Tecla | Acción |
|-------|--------|
| `Espacio` | Crafteo Rápido |
| `Enter` | Crafteo Rápido (Alt) |
| `F` | Alternar Favorito |
| `Ctrl + 1-9` | Establecer cantidad |

*Todas las teclas pueden cambiarse en Opciones > Controles > FlashCraft*

## 📦 Instalación

### Requisitos
- **Minecraft**: 1.18.2
- **Fabric Loader**: 0.18.1 o superior
- **Fabric API**: Requerido
- **Java**: 17 o superior

### Pasos
1. Instala [Fabric Loader](https://fabricmc.net/use/) para Minecraft 1.18.2
2. Descarga [Fabric API](https://modrinth.com/mod/fabric-api) para 1.18.2
3. Descarga la última versión de FlashCraft desde [Releases](#) / [Modrinth](#) / [CurseForge](#)
4. Coloca ambos archivos `.jar` en tu carpeta `.minecraft/mods`
5. Inicia Minecraft con el perfil de Fabric

## 🔧 Detalles Técnicos

### Arquitectura
FlashCraft usa tecnología **Mixin** para inyectar funcionalidad en el sistema del Libro de Recetas de Minecraft:

- **`RecipeBookResultsMixin`**: Captura y almacena la última receta clickeada
- **`RecipeBookWidgetMixin`**: Intercepta entrada de teclado y dispara el crafteo
- **`IRecipeBookResults`**: Interfaz duck para almacenar estado de recetas
- **`FlashCraftConfig`**: Sistema de configuración con archivo JSON
- **`FlashCraftKeybindings`**: Registro de teclas configurables
- **`CraftingState`**: Estado global del sistema de crafteo

### ¿Por Qué Este Enfoque?
- **No invasivo**: No sobrescribe ni reemplaza clases vanilla
- **Compatible**: Funciona junto a otros mods que modifican el crafteo
- **Mantenible**: Separación limpia entre lógica del mod y código vanilla

## 🤝 Compatibilidad

### Mods Compatibles Conocidos
- ✅ Mods de Libro de Recetas (REI, alternativas JEI)
- ✅ Mods de gestión de inventario
- ✅ Mods QoL (Calidad de Vida)

### Posibles Conflictos
- ⚠️ Mods que reemplacen completamente la UI del Libro de Recetas podrían no funcionar
- ⚠️ Mods que reasignen `Espacio` o `Enter` en pantallas de crafteo pueden conflictuar

*Si encuentras problemas con mods específicos, por favor repórtalos en la sección de [Issues](../../issues).*

## 🐛 Reportes de Bugs & Sugerencias

¿Encontraste un bug o tienes una idea? Por favor abre un issue en la página de [GitHub Issues](../../issues) con:
- Versión de Minecraft
- Versión de Fabric Loader
- Versión de FlashCraft
- Pasos para reproducir (para bugs)
- Log de crash (si aplica)

## 📜 Licencia

Este proyecto está licenciado bajo **CC0-1.0** (Dominio Público). Eres libre de usar, modificar y distribuir este mod sin restricciones.

## 💖 Créditos

- **Autor**: [luissuil](https://github.com/luissuil)
- **Inspirado por**: Función de Crafteo Rápido del Minecraft Snapshot 24w34a
- **Construido con**: [Fabric](https://fabricmc.net/), [Fabric API](https://github.com/FabricMC/fabric), [Mixin](https://github.com/SpongePowered/Mixin)

---

<div align="center">

**¡Disfruta un crafteo más rápido! ⚡**

[Reportar Bug](../../issues) · [Solicitar Función](../../issues) · [Modrinth](#) · [CurseForge](#)

</div>
