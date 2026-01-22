package com.simpleluckyblock.worldgen;

import com.simpleluckyblock.SimpleLuckyBlock;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * Handles world generation for the Simple Lucky Block mod.
 * Registers Lucky Blocks naturally in the world on land surfaces.
 */
public class ModWorldGeneration {
    
    // Registry keys for our placed features
    public static final ResourceKey<PlacedFeature> LUCKY_BLOCK_PLACED_KEY = 
        ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(SimpleLuckyBlock.MOD_ID, "lucky_block_placed"));
    
    /**
     * Registers world generation features for the mod.
     * This should be called during mod initialization.
     */
    public static void registerWorldGeneration() {
        SimpleLuckyBlock.LOGGER.info("Registering world generation for Simple Lucky Block mod");
        
        // Add Lucky Block generation to overworld biomes
        BiomeModifications.addFeature(
            BiomeSelectors.foundInOverworld()
                .and(BiomeSelectors.excludeByKey(Biomes.OCEAN, Biomes.DEEP_OCEAN, Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.COLD_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.WARM_OCEAN))
                .and(BiomeSelectors.excludeByKey(Biomes.RIVER, Biomes.FROZEN_RIVER))
                .and(BiomeSelectors.excludeByKey(Biomes.MUSHROOM_FIELDS)),
            GenerationStep.Decoration.SURFACE_STRUCTURES,
            LUCKY_BLOCK_PLACED_KEY
        );
        
        SimpleLuckyBlock.LOGGER.info("Lucky Block world generation registered successfully");
    }
} 