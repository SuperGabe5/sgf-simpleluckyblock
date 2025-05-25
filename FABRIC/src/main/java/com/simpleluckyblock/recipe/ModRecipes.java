package com.simpleluckyblock.recipe;

import com.simpleluckyblock.SimpleLuckyBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles registration of custom recipes for the Simple Lucky Block mod.
 */
public class ModRecipes {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleLuckyBlock.MOD_ID);

    /**
     * Registers all recipes for the mod.
     * Currently, all recipes are defined via JSON files in the data directory.
     */
    public static void registerRecipes() {
        LOGGER.info("Registering recipes for Simple Lucky Block mod");
        // Currently, all recipes are defined via JSON files
        // This method serves as a hook for future custom recipe types if needed
    }
} 