# Simple Lucky Block Mod

A Minecraft Fabric mod for version 1.21.5 that adds a Lucky Block to the game!

## Features

- **Lucky Block**: A special block that drops random loot when broken
- **Custom Creative Tab**: All mod items are organized in their own creative tab called "Simple Lucky Block"
- **Random Loot System**: Breaking a Lucky Block gives you random valuable items and experience

## Loot Table

When you break a Lucky Block, you can get one of these rewards:

- 1-3 Diamonds
- 1-5 Emeralds  
- 2-5 Gold Ingots
- 3-8 Iron Ingots
- 1 Enchanted Golden Apple
- 5-12 Experience Bottles
- 1-2 Netherite Scraps
- 2-4 Ender Pearls
- 1 Totem of Undying
- 5-14 Coal (common drop)

Plus 10-29 experience points!

## Installation

1. Make sure you have Minecraft 1.21.5 and Fabric Loader installed
2. Download Fabric API 0.124.2+1.21.5 or later
3. Place both the Fabric API and this mod's JAR file in your `mods` folder
4. Launch Minecraft with the Fabric profile

## Building from Source

1. Clone this repository
2. Add a texture file named `lucky_block.png` (16x16 pixels) to `src/main/resources/assets/simple-lucky-block/textures/block/`
3. Run `./gradlew build` (or `gradlew.bat build` on Windows)
4. The built JAR will be in `build/libs/`

## Texture Note

This mod template includes all the code and JSON files needed, but you'll need to add your own texture for the Lucky Block. Create a 16x16 pixel PNG file and save it as `lucky_block.png` in the textures/block directory.

## License

This project is licensed under CC0-1.0 - see the LICENSE file for details. 