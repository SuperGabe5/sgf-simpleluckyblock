package com.simpleluckyblock.worldgen;

import com.simpleluckyblock.SimpleLuckyBlock;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

/**
 * Handles world generation for the Simple Lucky Block mod.
 * Registers Lucky Blocks naturally in the world on land surfaces.
 */
public class ModWorldGeneration {
    
    // Registry keys for our placed features
    public static final RegistryKey<PlacedFeature> LUCKY_BLOCK_PLACED_KEY = 
        RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(SimpleLuckyBlock.MOD_ID, "lucky_block_placed"));
    
    /**
     * Registers world generation features for the mod.
     * This should be called during mod initialization.
     */
    public static void registerWorldGeneration() {
        SimpleLuckyBlock.LOGGER.info("Registering world generation for Simple Lucky Block mod");
        
        // Add Lucky Block generation to overworld biomes
        BiomeModifications.addFeature(
            BiomeSelectors.foundInOverworld()
                .and(BiomeSelectors.excludeByKey(BiomeKeys.OCEAN, BiomeKeys.DEEP_OCEAN, BiomeKeys.FROZEN_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.COLD_OCEAN, BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN))
                .and(BiomeSelectors.excludeByKey(BiomeKeys.RIVER, BiomeKeys.FROZEN_RIVER))
                .and(BiomeSelectors.excludeByKey(BiomeKeys.MUSHROOM_FIELDS)),
            GenerationStep.Feature.SURFACE_STRUCTURES,
            LUCKY_BLOCK_PLACED_KEY
        );
        
        SimpleLuckyBlock.LOGGER.info("Lucky Block world generation registered successfully");
    }
} 