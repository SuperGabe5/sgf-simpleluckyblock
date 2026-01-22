package com.simpleluckyblock;

import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {
    
    public static Block LUCKY_BLOCK;

    /**
     * Register a block with its item using the proper 1.21.5 pattern
     */
    private static <T extends Block> T register(String name, Function<BlockBehaviour.Properties, T> factory, BlockBehaviour.Properties settings) {
        // Create identifier and registry keys
        Identifier identifier = Identifier.fromNamespaceAndPath(SimpleLuckyBlock.MOD_ID, name);
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, identifier);
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, identifier);
        
        // Set registry key in settings
        BlockBehaviour.Properties blockSettings = settings.setId(blockKey);
        
        // Create and register the block
        T block = Registry.register(BuiltInRegistries.BLOCK, blockKey, factory.apply(blockSettings));
        
        // Register block item with registry key in item settings
        Item.Properties itemSettings = new Item.Properties().setId(itemKey);
        BlockItem blockItem = new BlockItem(block, itemSettings);
        Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
        
        return block;
    }

    public static void registerModBlocks() {
        SimpleLuckyBlock.LOGGER.info("Registering ModBlocks for " + SimpleLuckyBlock.MOD_ID);
        
        // Register the lucky block
        LUCKY_BLOCK = register("lucky_block", LuckyBlock::new, 
            BlockBehaviour.Properties.of()
                .strength(0.2f)
                .sound(SoundType.GRASS));
    }
} 