package com.simpleluckyblock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class LuckyBlock extends Block {
    
    public LuckyBlock(Properties settings) {
        super(settings);
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClientSide && world instanceof ServerLevel serverWorld) {
            generateRandomLoot(serverWorld, pos, player);
        }
        
        return super.playerWillDestroy(world, pos, state, player);
    }

    private void generateRandomLoot(ServerLevel world, BlockPos pos, Player player) {
        Random random = new Random();
        int chance = random.nextInt(100);
        
        if (chance < 20) {
            // 20% chance for unlucky drops
            generateUnluckyDrop(world, pos, player, random);
        } else if (chance < 85) {
            // 65% chance for normal drops
            generateNormalDrop(world, pos, player, random);
        } else {
            // 15% chance for very lucky drops
            generateVeryLuckyDrop(world, pos, player, random);
        }
    }

    private void generateUnluckyDrop(ServerLevel world, BlockPos pos, Player player, Random random) {
        // Play sad villager sound
        world.playSound(null, pos, SoundEvents.VILLAGER_NO, SoundSource.BLOCKS, 1.0f, 0.8f);
        
        int unluckyType = random.nextInt(6);
        
        switch (unluckyType) {
            case 0: // Dirt Drop (20%)
                dropItems(world, pos, new ItemStack(Items.DIRT, random.nextInt(5) + 1));
                break;
                
            case 1: // Explosion (20%)
                world.explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 
                    2.5f, Level.ExplosionInteraction.BLOCK);
                break;
                
            case 2: // Rotten Flesh and Useless Tools (20%)
                dropItems(world, pos, 
                    new ItemStack(Items.ROTTEN_FLESH, random.nextInt(8) + 3),
                    new ItemStack(Items.GOLDEN_SWORD),
                    new ItemStack(Items.GOLDEN_HOE)
                );
                break;
                
            case 3: // Annoying Blocks (20%)
                dropItems(world, pos,
                    new ItemStack(Items.GRAVEL, random.nextInt(10) + 5),
                    new ItemStack(Items.COBBLESTONE, random.nextInt(8) + 4),
                    new ItemStack(Items.SAND, random.nextInt(6) + 3)
                );
                break;
                
            case 4: // Useless Items (20%)
                dropItems(world, pos,
                    new ItemStack(Items.STICK, random.nextInt(15) + 10),
                    new ItemStack(Items.DEAD_BUSH, random.nextInt(5) + 2),
                    new ItemStack(Items.FLINT, random.nextInt(8) + 4)
                );
                break;
        }
    }

    private void generateNormalDrop(ServerLevel world, BlockPos pos, Player player, Random random) {
        // Play level up sound
        world.playSound(null, pos, SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1.0f, 1.0f);
        
        int normalType = random.nextInt(25);
        
        switch (normalType) {
            case 0: // Complete wooden tool set
                dropItems(world, pos,
                    new ItemStack(Items.WOODEN_SWORD),
                    new ItemStack(Items.WOODEN_PICKAXE),
                    new ItemStack(Items.WOODEN_AXE),
                    new ItemStack(Items.WOODEN_SHOVEL),
                    new ItemStack(Items.WOODEN_HOE)
                );
                break;
                
            case 1: // Complete stone tool set
                dropItems(world, pos,
                    new ItemStack(Items.STONE_SWORD),
                    new ItemStack(Items.STONE_PICKAXE),
                    new ItemStack(Items.STONE_AXE),
                    new ItemStack(Items.STONE_SHOVEL),
                    new ItemStack(Items.SHEARS)
                );
                break;
                
            case 2: // Complete gold tool set
                dropItems(world, pos,
                    new ItemStack(Items.RAW_GOLD, 8),
                    new ItemStack(Items.GOLDEN_PICKAXE),
                    new ItemStack(Items.GOLDEN_AXE),
                    new ItemStack(Items.GOLDEN_SHOVEL),
                    new ItemStack(Items.GOLD_INGOT, 2)
                );
                break;
                
            case 3: // Complete leather armor set
                dropItems(world, pos,
                    new ItemStack(Items.LEATHER_HELMET),
                    new ItemStack(Items.LEATHER_CHESTPLATE),
                    new ItemStack(Items.LEATHER_LEGGINGS),
                    new ItemStack(Items.LEATHER_BOOTS)
                );
                break;
                
            case 4: // Complete gold armor set
                dropItems(world, pos,
                    new ItemStack(Items.GOLDEN_HELMET),
                    new ItemStack(Items.GOLDEN_CHESTPLATE),
                    new ItemStack(Items.GOLDEN_LEGGINGS),
                    new ItemStack(Items.GOLDEN_BOOTS)
                );
                break;
                
            case 5: // 2-3 rare items
                List<ItemStack> ironArmor = List.of(
                    new ItemStack(Items.DIAMOND),
                    new ItemStack(Items.GOLDEN_APPLE),
                    new ItemStack(Items.GOLD_BLOCK),
                    new ItemStack(Items.TOTEM_OF_UNDYING),
                    new ItemStack(Items.ENCHANTED_GOLDEN_APPLE)
                );
                int pieces = random.nextInt(2) + 2; // 2-3 pieces
                for (int i = 0; i < pieces; i++) {
                    dropItems(world, pos, ironArmor.get(random.nextInt(ironArmor.size())));
                }
                break;
                
            case 6: // Sponge
                dropItems(world, pos, new ItemStack(Items.SPONGE, random.nextInt(3) + 1));
                break;
                
            case 7: // 2 Ender Chests
                dropItems(world, pos, new ItemStack(Items.ENDER_CHEST, 2));
                break;
                
            case 8: // Ender Pearls and Eyes
                dropItems(world, pos,
                    new ItemStack(Items.ENDER_PEARL, random.nextInt(3) + 2),
                    new ItemStack(Items.ENDER_EYE, random.nextInt(2) + 1)
                );
                break;
                
            case 9: // Custom named clock
                ItemStack clock = new ItemStack(Items.CLOCK);
                clock.set(net.minecraft.core.component.DataComponents.CUSTOM_NAME, Component.literal("Lucky Clock"));
                dropItems(world, pos, clock);
                break;
                
            case 10: // Buckets
                dropItems(world, pos,
                    new ItemStack(Items.LAVA_BUCKET),
                    new ItemStack(Items.WATER_BUCKET),
                    new ItemStack(Items.MILK_BUCKET)
                );
                break;
                
            case 11: // lot's of goodies
                dropItems(world, pos,
                    new ItemStack(Items.IRON_HELMET),
                    new ItemStack(Items.IRON_CHESTPLATE),
                    new ItemStack(Items.IRON_LEGGINGS),
                    new ItemStack(Items.IRON_BOOTS),
                    new ItemStack(Items.IRON_SWORD),
                    new ItemStack(Items.IRON_SHOVEL),
                    new ItemStack(Items.IRON_AXE),
                    new ItemStack(Items.IRON_INGOT, 8),
                    new ItemStack(Items.IRON_PICKAXE),
                    new ItemStack(Items.RABBIT),
                    new ItemStack(Items.RAW_GOLD_BLOCK, 2),
                    new ItemStack(Items.RAW_IRON_BLOCK, 3),
                    new ItemStack(Items.RAW_COPPER_BLOCK),
                    new ItemStack(Items.COOKED_MUTTON, 16),
                    new ItemStack(Items.COOKED_BEEF, 8),
                    new ItemStack(Items.COOKED_CHICKEN, 8)
                );
                break;
                
            case 12: // Pumpkins and jack o' lanterns
                dropItems(world, pos,
                    new ItemStack(Items.PUMPKIN, random.nextInt(5) + 3),
                    new ItemStack(Items.JACK_O_LANTERN, random.nextInt(3) + 2)
                );
                break;
                
            case 13: // TNT blocks
                dropItems(world, pos, new ItemStack(Items.TNT, random.nextInt(4) + 2));
                break;
                
            case 14: // Saddles
                dropItems(world, pos, new ItemStack(Items.SADDLE, random.nextInt(2) + 1));
                break;
                
            case 15: // Various food items
                dropItems(world, pos,
                    new ItemStack(Items.BREAD, random.nextInt(8) + 4),
                    new ItemStack(Items.APPLE, random.nextInt(6) + 3),
                    new ItemStack(Items.GOLDEN_CARROT, random.nextInt(4) + 2)
                );
                break;
                
            case 16: // Redstone equipment
                dropItems(world, pos,
                    new ItemStack(Items.REDSTONE, random.nextInt(20) + 10),
                    new ItemStack(Items.LEVER, random.nextInt(8) + 4),
                    new ItemStack(Items.REPEATER, random.nextInt(4) + 2),
                    new ItemStack(Items.BIRCH_BUTTON, random.nextInt(4) + 2),
                    new ItemStack(Items.PALE_OAK_PRESSURE_PLATE, random.nextInt(4) + 2)
                );
                break;
                
            case 17: // Falling Diamond Block
                createFallingBlock(world, pos, Blocks.DIAMOND_BLOCK, ParticleTypes.ENCHANT);
                break;
                
            case 18: // Falling Gold Block
                createFallingBlock(world, pos, Blocks.GOLD_BLOCK, ParticleTypes.HAPPY_VILLAGER);
                break;
                
            case 19: // Falling Emerald Block
                createFallingBlock(world, pos, Blocks.EMERALD_BLOCK, ParticleTypes.COMPOSTER);
                break;
                
            case 20: // Falling Iron Block
                createFallingBlock(world, pos, Blocks.IRON_BLOCK, ParticleTypes.CRIT);
                break;
                
            case 21: // Fish and fishing rod
                dropItems(world, pos,
                    new ItemStack(Items.COD, random.nextInt(5) + 3),
                    new ItemStack(Items.SALMON, random.nextInt(4) + 2),
                    new ItemStack(Items.FISHING_ROD)
                );
                break;
                
            case 22: // Various meat types
                dropItems(world, pos,
                    new ItemStack(Items.BEEF, random.nextInt(6) + 3),
                    new ItemStack(Items.PORKCHOP, random.nextInt(5) + 2),
                    new ItemStack(Items.CHICKEN, random.nextInt(4) + 3)
                );
                break;
                
            case 23: // Books and bookshelves
                dropItems(world, pos,
                    new ItemStack(Items.BOOK, random.nextInt(8) + 5),
                    new ItemStack(Items.BOOKSHELF, random.nextInt(4) + 2)
                );
                break;
                
            case 24: // Spawn eggs
                List<ItemStack> spawnEggs = List.of(
                    new ItemStack(Items.SHEEP_SPAWN_EGG),
                    new ItemStack(Items.HORSE_SPAWN_EGG),
                    new ItemStack(Items.VILLAGER_SPAWN_EGG),
                    new ItemStack(Items.WOLF_SPAWN_EGG),
                    new ItemStack(Items.VILLAGER_SPAWN_EGG),
                    new ItemStack(Items.ZOMBIE_VILLAGER_SPAWN_EGG)
                );
                dropItems(world, pos, spawnEggs.get(random.nextInt(spawnEggs.size())));
                break;
        }
    }

    private void generateVeryLuckyDrop(ServerLevel world, BlockPos pos, Player player, Random random) {
        // Play celebration sound and spawn totem particles
        world.playSound(null, pos, SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.BLOCKS, 1.0f, 1.0f);
        spawnTotemParticles(world, pos);
        
        int luckyType = random.nextInt(13);
        
        switch (luckyType) {
            case 0: // Hero Armor
                dropItems(world, pos,
                    new ItemStack(Items.DIAMOND_HELMET),
                    new ItemStack(Items.DIAMOND_CHESTPLATE),
                    new ItemStack(Items.DIAMOND_LEGGINGS),
                    new ItemStack(Items.DIAMOND_BOOTS),
                    new ItemStack(Items.ENCHANTED_BOOK, random.nextInt(3) + 2)
                );
                break;
                
            case 1: // Horse Armor Set
                dropItems(world, pos,
                    new ItemStack(Items.EMERALD_ORE, 3),
                    new ItemStack(Items.IRON_HORSE_ARMOR),
                    new ItemStack(Items.MUSHROOM_STEW),
                    new ItemStack(Items.RABBIT_STEW)
                );
                break;
                
            case 2: // Diamond Tools
                List<ItemStack> diamondTools = List.of(
                    new ItemStack(Items.NETHERITE_SWORD),
                    new ItemStack(Items.NETHERITE_PICKAXE),
                    new ItemStack(Items.NETHERITE_AXE),
                    new ItemStack(Items.NETHERITE_SHOVEL),
                    new ItemStack(Items.STONE_HOE)
                );
                int toolCount = random.nextInt(2) + 1; // 1-2 tools
                for (int i = 0; i < toolCount; i++) {
                    dropItems(world, pos, diamondTools.get(random.nextInt(diamondTools.size())));
                }
                break;
                
            case 3: // Very Rare Items
                List<ItemStack> chainmailArmor = List.of(
                    new ItemStack(Items.NETHERITE_SCRAP, 3),
                    new ItemStack(Items.EMERALD_BLOCK, 3),
                    new ItemStack(Items.NETHERITE_INGOT, 2),
                    new ItemStack(Items.DIAMOND_BLOCK)
                );
                int chainmailPieces = random.nextInt(2) + 2; // 2-3 pieces
                for (int i = 0; i < chainmailPieces; i++) {
                    dropItems(world, pos, chainmailArmor.get(random.nextInt(chainmailArmor.size())));
                }
                break;
                
            case 4: // Netherite Armor pieces
                List<ItemStack> diamondArmor = List.of(
                    new ItemStack(Items.NETHERITE_HELMET),
                    new ItemStack(Items.NETHERITE_CHESTPLATE),
                    new ItemStack(Items.NETHERITE_LEGGINGS),
                    new ItemStack(Items.NETHERITE_BOOTS)
                );
                int diamondPieces = random.nextInt(2) + 1; // 1-2 pieces
                for (int i = 0; i < diamondPieces; i++) {
                    dropItems(world, pos, diamondArmor.get(random.nextInt(diamondArmor.size())));
                }
                break;
                
            case 5: // Eye of Ender Portal Frames
                dropItems(world, pos, new ItemStack(Items.ENDER_EYE, random.nextInt(5) + 2));
                break;
                
            case 6: // Hero Tools
                dropItems(world, pos,
                    new ItemStack(Items.DIAMOND_PICKAXE),
                    new ItemStack(Items.DIAMOND_SWORD),
                    new ItemStack(Items.DIAMOND_HELMET),
                    new ItemStack(Items.DIAMOND_CHESTPLATE),
                    new ItemStack(Items.DIAMOND_LEGGINGS),
                    new ItemStack(Items.DIAMOND_BOOTS),
                    new ItemStack(Items.DIAMOND_AXE),
                    new ItemStack(Items.DIAMOND_SHOVEL),
                    new ItemStack(Items.SHIELD)
                );
                break;
                
            case 7: // Chicken and Diamond Combo
                dropItems(world, pos,
                    new ItemStack(Items.CHICKEN_SPAWN_EGG, random.nextInt(5) + 3),
                    new ItemStack(Items.DIAMOND, random.nextInt(4) + 2),
                    new ItemStack(Items.EGG, random.nextInt(8) + 5)
                );
                break;
                
            case 8: // Treasure Chest
                dropItems(world, pos,
                    new ItemStack(Items.CHEST),
                    new ItemStack(Items.IRON_NUGGET, random.nextInt(16) + 8),
                    new ItemStack(Items.GOLD_INGOT, random.nextInt(6) + 3),
                    new ItemStack(Items.DIAMOND, random.nextInt(4) + 2),
                    new ItemStack(Items.EMERALD, random.nextInt(3) + 1),
                    new ItemStack(ModBlocks.LUCKY_BLOCK.asItem(), random.nextInt(3) + 1),
                    new ItemStack(Items.GOLDEN_APPLE, random.nextInt(3) + 1),
                    new ItemStack(Items.ENCHANTED_GOLDEN_APPLE)
                );
                // Also Drop Random 
                dropItems(world, pos, new ItemStack(Items.OBSIDIAN, random.nextInt(5) + 2));
                break;
                
            case 9: // Giant Lucky Block
                dropItems(world, pos,
                    new ItemStack(ModBlocks.LUCKY_BLOCK.asItem(), random.nextInt(5) + 3), // Multiple lucky blocks (3-7)
                    new ItemStack(Items.DIAMOND, 10),
                    new ItemStack(Items.GOLD_BLOCK, 3),
                    new ItemStack(Items.IRON_INGOT, 20),
                    new ItemStack(Items.EMERALD, 18),
                    new ItemStack(Items.ENDER_CHEST),
                    new ItemStack(Items.CHEST, 2)
                );
                break;
                
            case 10: // BEACON KIT
                List<ItemStack> rareItems = List.of(
                    new ItemStack(Items.NETHER_STAR),
                    new ItemStack(Items.BEACON),
                    new ItemStack(Items.COPPER_BLOCK, 43)
                );
                dropItems(world, pos, rareItems.get(random.nextInt(rareItems.size())));
                break;
                
            case 11: // Enchantment Setup
                dropItems(world, pos,
                    new ItemStack(Items.ENCHANTING_TABLE),
                    new ItemStack(Items.BOOKSHELF, random.nextInt(8) + 4),
                    new ItemStack(Items.LAPIS_LAZULI, random.nextInt(16) + 8)
                );
                break;
                
            case 12: // Experience Shower
                createExperienceShower(world, pos, random);
                dropItems(world, pos, new ItemStack(Items.EXPERIENCE_BOTTLE, random.nextInt(15) + 10));
                break;
        }
    }

    private void dropItems(ServerLevel world, BlockPos pos, ItemStack... items) {
        for (ItemStack item : items) {
            Block.popResource(world, pos, item);
        }
    }

    private void createFallingBlock(ServerLevel world, BlockPos pos, Block block, net.minecraft.core.particles.ParticleOptions particle) {
        FallingBlockEntity fallingBlock = FallingBlockEntity.fall(world, pos.above(3), block.defaultBlockState());
        
        // Add particle effects
        for (int i = 0; i < 20; i++) {
            world.sendParticles(particle, 
                pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                1, 0.5, 0.5, 0.5, 0.1);
        }
    }

    private void spawnTotemParticles(ServerLevel world, BlockPos pos) {
        for (int i = 0; i < 50; i++) {
            world.sendParticles(ParticleTypes.TOTEM_OF_UNDYING,
                pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                1, 1.0, 1.0, 1.0, 0.2);
        }
    }

    private void createExperienceShower(ServerLevel world, BlockPos pos, Random random) {
        // Create multiple experience orbs with special effects
        for (int i = 0; i < 20; i++) {
            ExperienceOrb orb = new ExperienceOrb(world, 
                pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 2,
                pos.getY() + 2 + random.nextDouble() * 3,
                pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 2,
                random.nextInt(10) + 5);
            world.addFreshEntity(orb);
        }
        
        // Add special particle effects
        for (int i = 0; i < 100; i++) {
            world.sendParticles(ParticleTypes.ENCHANT,
                pos.getX() + 0.5, pos.getY() + 2, pos.getZ() + 0.5,
                1, 2.0, 2.0, 2.0, 0.3);
        }
    }
} 