package com.simpleluckyblock;

import com.simpleluckyblock.recipe.ModRecipes;
import com.simpleluckyblock.worldgen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.SharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleLuckyBlock implements ModInitializer {
	public static final String MOD_ID = "simple_lucky_block";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// Creative tab for the mod
	public static final ResourceKey<CreativeModeTab> SIMPLE_LUCKY_BLOCK_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(MOD_ID, "simple_lucky_block"));
	
	// Flag to avoid initializing Bootstrap multiple times
	private static boolean isBootstrapInitialized = false;

	/**
	 * Ensures the Minecraft Bootstrap is initialized.
	 * This is required for proper registry access in 1.21.5.
	 */
	private static void ensureBootstrapInitialized() {
		if (!isBootstrapInitialized) {
			try {
				LOGGER.info("Initializing Bootstrap for MC 1.21.5");
				SharedConstants.tryDetectVersion();
				Bootstrap.bootStrap();
				isBootstrapInitialized = true;
				LOGGER.info("Bootstrap initialization successful");
			} catch (Exception e) {
				LOGGER.warn("Bootstrap initialization failed: " + e.getMessage());
				// Continue anyway - it might already be initialized
			}
		}
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Initializing Simple Lucky Block mod!");

		// Ensure Bootstrap is initialized before accessing registries
		ensureBootstrapInitialized();

		// Register blocks (this also registers the block items)
		ModBlocks.registerModBlocks();

		// Register recipes
		ModRecipes.registerRecipes();

		// Register world generation
		ModWorldGeneration.registerWorldGeneration();

		// Register the creative tab
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, SIMPLE_LUCKY_BLOCK_GROUP, FabricItemGroup.builder()
				.icon(() -> new ItemStack(ModBlocks.LUCKY_BLOCK))
				.title(Component.translatable("itemGroup.simple_lucky_block.simple_lucky_block"))
				.build());

		// Add items to the creative tab
		ItemGroupEvents.modifyEntriesEvent(SIMPLE_LUCKY_BLOCK_GROUP).register(entries -> {
			entries.accept(ModBlocks.LUCKY_BLOCK);
		});

		LOGGER.info("Simple Lucky Block mod initialized successfully!");
	}
}