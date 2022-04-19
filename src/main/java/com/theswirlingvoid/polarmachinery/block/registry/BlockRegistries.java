package com.theswirlingvoid.polarmachinery.block.registry;

import com.theswirlingvoid.polarmachinery.PolarMachinery;
import com.theswirlingvoid.polarmachinery.block.BlockTypes;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistries {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PolarMachinery.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PolarMachinery.MOD_ID);

	//Blocks
	public static final RegistryObject<Block> REGISTRY_STRUCTURE_CASING = BLOCKS.register("structure_casing", () -> BlockTypes.STRUCTURE_CASING);

	//BlockItems
	public static final RegistryObject<BlockItem> REGISTRY_ITEM_STRUCTURE_CASING = ITEMS.register(
																						"structure_casing", 
																						() -> new BlockItem(BlockTypes.STRUCTURE_CASING, new Item.Properties().stacksTo(64))
																					);
}
