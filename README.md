# Allah Sunshine

A server-side Fabric mod for Minecraft.

**Description**: A fun punishment/sunshine command mod for MC 26.1.2.

## Dependencies

This mod requires the following dependencies to run properly:

- **Minecraft**: `26.1.2`
- **Java**: `26` (Due to Minecraft 26.1.2 requirements)
- **Fabric Loader**: `>=0.16.14`
- **Fabric API**: `0.152.1+26.1.2`

## Building from Source

> [!WARNING]
> Because Minecraft 26.1.2 requires Java 25+, and current Gradle versions crash on Java 25+, you cannot build this mod using Gradle right now.

To compile this mod, you must use the provided `build_and_install.bat` script. 

1. Open `build_and_install.bat` in a text editor.
2. Modify the hardcoded paths (`JAVAC`, `JAR_TOOL`, and `SERVER_DIR`) at the top of the file to match your system's Java 26 installation and your server directory.
3. Run the script to compile and install the mod directly.
