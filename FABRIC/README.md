# Simple Lucky Block Mod

A Minecraft Fabric mod for version 1.21.8 that adds a Lucky Block to the game!

## Features

- **Lucky Block**: A special block that drops random loot when broken
- **Custom Creative Tab**: All mod items are organized in their own creative tab called "Simple Lucky Block"
- **Random Loot System**: Breaking a Lucky Block gives you random valuable items and experience

## Loot Table

(I did not want update this part because of the large changes i made)

## Installation

1. Make sure you have Minecraft 1.21.8 and Fabric Loader installed
2. Download The latest Fabric API for 1.21.8
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