package com.simpleluckyblock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LuckyBlock extends Block {
    
    public LuckyBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && world instanceof ServerWorld serverWorld) {
            generateRandomLoot(serverWorld, pos, player);
        }
        
        return super.onBreak(world, pos, state, player);
    }

    private void generateRandomLoot(ServerWorld world, BlockPos pos, PlayerEntity player) {
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

    private void generateUnluckyDrop(ServerWorld world, BlockPos pos, PlayerEntity player, Random random) {
        // Play sad villager sound
        world.playSound(null, pos, SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.BLOCKS, 1.0f, 0.8f);
        
        int unluckyType = random.nextInt(5);
        
        switch (unluckyType) {
            case 0: // Dirt Drop (20%)
                dropItems(world, pos, new ItemStack(Items.DIRT, random.nextInt(5) + 1));
                break;
                
            case 1: // Explosion (20%)
                world.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 
                    2.5f, World.ExplosionSourceType.BLOCK);
                break;
                
            case 2: // Rotten Food (20%)
                dropItems(world, pos, 
                    new ItemStack(Items.ROTTEN_FLESH, random.nextInt(8) + 3),
                    new ItemStack(Items.SPIDER_EYE, random.nextInt(3) + 1),
                    new ItemStack(Items.POISONOUS_POTATO, random.nextInt(4) + 2)
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

    private void generateNormalDrop(ServerWorld world, BlockPos pos, PlayerEntity player, Random random) {
        // Play level up sound
        world.playSound(null, pos, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 1.0f, 1.0f);
        
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
                    new ItemStack(Items.STONE_HOE)
                );
                break;
                
            case 2: // Complete gold tool set
                dropItems(world, pos,
                    new ItemStack(Items.GOLDEN_SWORD),
                    new ItemStack(Items.GOLDEN_PICKAXE),
                    new ItemStack(Items.GOLDEN_AXE),
                    new ItemStack(Items.GOLDEN_SHOVEL),
                    new ItemStack(Items.GOLDEN_HOE)
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
                
            case 5: // 2-3 iron armor pieces
                List<ItemStack> ironArmor = List.of(
                    new ItemStack(Items.IRON_HELMET),
                    new ItemStack(Items.IRON_CHESTPLATE),
                    new ItemStack(Items.IRON_LEGGINGS),
                    new ItemStack(Items.IRON_BOOTS)
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
                clock.set(net.minecraft.component.DataComponentTypes.CUSTOM_NAME, Text.literal("Lucky Clock"));
                dropItems(world, pos, clock);
                break;
                
            case 10: // Buckets
                dropItems(world, pos,
                    new ItemStack(Items.LAVA_BUCKET),
                    new ItemStack(Items.WATER_BUCKET),
                    new ItemStack(Items.MILK_BUCKET)
                );
                break;
                
            case 11: // Complete set of dyes
                dropItems(world, pos,
                    new ItemStack(Items.WHITE_DYE),
                    new ItemStack(Items.ORANGE_DYE),
                    new ItemStack(Items.MAGENTA_DYE),
                    new ItemStack(Items.LIGHT_BLUE_DYE),
                    new ItemStack(Items.YELLOW_DYE),
                    new ItemStack(Items.LIME_DYE),
                    new ItemStack(Items.PINK_DYE),
                    new ItemStack(Items.GRAY_DYE),
                    new ItemStack(Items.LIGHT_GRAY_DYE),
                    new ItemStack(Items.CYAN_DYE),
                    new ItemStack(Items.PURPLE_DYE),
                    new ItemStack(Items.BLUE_DYE),
                    new ItemStack(Items.BROWN_DYE),
                    new ItemStack(Items.GREEN_DYE),
                    new ItemStack(Items.RED_DYE),
                    new ItemStack(Items.BLACK_DYE)
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
                    new ItemStack(Items.REDSTONE_TORCH, random.nextInt(8) + 4),
                    new ItemStack(Items.REPEATER, random.nextInt(4) + 2)
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
                    new ItemStack(Items.LLAMA_SPAWN_EGG),
                    new ItemStack(Items.WOLF_SPAWN_EGG),
                    new ItemStack(Items.RABBIT_SPAWN_EGG),
                    new ItemStack(Items.CAT_SPAWN_EGG)
                );
                dropItems(world, pos, spawnEggs.get(random.nextInt(spawnEggs.size())));
                break;
        }
    }

    private void generateVeryLuckyDrop(ServerWorld world, BlockPos pos, PlayerEntity player, Random random) {
        // Play celebration sound and spawn totem particles
        world.playSound(null, pos, SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.BLOCKS, 1.0f, 1.0f);
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
                    new ItemStack(Items.LEATHER_HORSE_ARMOR),
                    new ItemStack(Items.IRON_HORSE_ARMOR),
                    new ItemStack(Items.GOLDEN_HORSE_ARMOR),
                    new ItemStack(Items.DIAMOND_HORSE_ARMOR)
                );
                break;
                
            case 2: // Diamond Tools
                List<ItemStack> diamondTools = List.of(
                    new ItemStack(Items.DIAMOND_SWORD),
                    new ItemStack(Items.DIAMOND_PICKAXE),
                    new ItemStack(Items.DIAMOND_AXE),
                    new ItemStack(Items.DIAMOND_SHOVEL),
                    new ItemStack(Items.DIAMOND_HOE)
                );
                int toolCount = random.nextInt(2) + 1; // 1-2 tools
                for (int i = 0; i < toolCount; i++) {
                    dropItems(world, pos, diamondTools.get(random.nextInt(diamondTools.size())));
                }
                break;
                
            case 3: // Chainmail Armor
                List<ItemStack> chainmailArmor = List.of(
                    new ItemStack(Items.CHAINMAIL_HELMET),
                    new ItemStack(Items.CHAINMAIL_CHESTPLATE),
                    new ItemStack(Items.CHAINMAIL_LEGGINGS),
                    new ItemStack(Items.CHAINMAIL_BOOTS)
                );
                int chainmailPieces = random.nextInt(2) + 2; // 2-3 pieces
                for (int i = 0; i < chainmailPieces; i++) {
                    dropItems(world, pos, chainmailArmor.get(random.nextInt(chainmailArmor.size())));
                }
                break;
                
            case 4: // Diamond Armor pieces
                List<ItemStack> diamondArmor = List.of(
                    new ItemStack(Items.DIAMOND_HELMET),
                    new ItemStack(Items.DIAMOND_CHESTPLATE),
                    new ItemStack(Items.DIAMOND_LEGGINGS),
                    new ItemStack(Items.DIAMOND_BOOTS)
                );
                int diamondPieces = random.nextInt(2) + 1; // 1-2 pieces
                for (int i = 0; i < diamondPieces; i++) {
                    dropItems(world, pos, diamondArmor.get(random.nextInt(diamondArmor.size())));
                }
                break;
                
            case 5: // End Portal Frames
                dropItems(world, pos, new ItemStack(Items.END_PORTAL_FRAME, random.nextInt(5) + 2));
                break;
                
            case 6: // Hero Tools
                dropItems(world, pos,
                    new ItemStack(Items.DIAMOND_PICKAXE),
                    new ItemStack(Items.DIAMOND_SWORD),
                    new ItemStack(Items.ENCHANTED_BOOK, random.nextInt(2) + 1)
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
                // Add various dyes
                ItemStack[] dyes = {
                    new ItemStack(Items.WHITE_DYE), new ItemStack(Items.ORANGE_DYE),
                    new ItemStack(Items.MAGENTA_DYE), new ItemStack(Items.LIGHT_BLUE_DYE),
                    new ItemStack(Items.YELLOW_DYE), new ItemStack(Items.LIME_DYE),
                    new ItemStack(Items.PINK_DYE), new ItemStack(Items.GRAY_DYE),
                    new ItemStack(Items.LIGHT_GRAY_DYE), new ItemStack(Items.CYAN_DYE),
                    new ItemStack(Items.PURPLE_DYE), new ItemStack(Items.BLUE_DYE),
                    new ItemStack(Items.BROWN_DYE), new ItemStack(Items.GREEN_DYE),
                    new ItemStack(Items.RED_DYE), new ItemStack(Items.BLACK_DYE)
                };
                for (int i = 0; i < 5; i++) {
                    dropItems(world, pos, dyes[random.nextInt(dyes.length)]);
                }
                break;
                
            case 9: // Giant Lucky Block
                dropItems(world, pos,
                    new ItemStack(ModBlocks.LUCKY_BLOCK.asItem(), random.nextInt(5) + 3), // Multiple lucky blocks (3-7)
                    new ItemStack(Items.DIAMOND, 10),
                    new ItemStack(Items.GOLD_BLOCK, 3),
                    new ItemStack(Items.IRON_INGOT, 20),
                    new ItemStack(Items.EMERALD, 18),
                    new ItemStack(Items.CHEST)
                );
                break;
                
            case 10: // Rare Items
                List<ItemStack> rareItems = List.of(
                    new ItemStack(Items.NETHER_STAR),
                    new ItemStack(Items.BEACON),
                    new ItemStack(Items.DRAGON_EGG)
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

    private void dropItems(ServerWorld world, BlockPos pos, ItemStack... items) {
        for (ItemStack item : items) {
            Block.dropStack(world, pos, item);
        }
    }

    private void createFallingBlock(ServerWorld world, BlockPos pos, Block block, net.minecraft.particle.ParticleEffect particle) {
        FallingBlockEntity fallingBlock = FallingBlockEntity.spawnFromBlock(world, pos.up(3), block.getDefaultState());
        
        // Add particle effects
        for (int i = 0; i < 20; i++) {
            world.spawnParticles(particle, 
                pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                1, 0.5, 0.5, 0.5, 0.1);
        }
    }

    private void spawnTotemParticles(ServerWorld world, BlockPos pos) {
        for (int i = 0; i < 50; i++) {
            world.spawnParticles(ParticleTypes.TOTEM_OF_UNDYING,
                pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                1, 1.0, 1.0, 1.0, 0.2);
        }
    }

    private void createExperienceShower(ServerWorld world, BlockPos pos, Random random) {
        // Create multiple experience orbs with special effects
        for (int i = 0; i < 20; i++) {
            ExperienceOrbEntity orb = new ExperienceOrbEntity(world, 
                pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 2,
                pos.getY() + 2 + random.nextDouble() * 3,
                pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 2,
                random.nextInt(10) + 5);
            world.spawnEntity(orb);
        }
        
        // Add special particle effects
        for (int i = 0; i < 100; i++) {
            world.spawnParticles(ParticleTypes.ENCHANT,
                pos.getX() + 0.5, pos.getY() + 2, pos.getZ() + 0.5,
                1, 2.0, 2.0, 2.0, 0.3);
        }
    }
} 