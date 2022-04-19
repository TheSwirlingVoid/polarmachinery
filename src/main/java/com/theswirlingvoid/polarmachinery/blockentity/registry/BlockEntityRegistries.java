package com.theswirlingvoid.polarmachinery.blockentity.registry;

import com.theswirlingvoid.polarmachinery.PolarMachinery;
import com.theswirlingvoid.polarmachinery.blockentity.types.BlockEntityTypes;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistries {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, PolarMachinery.MOD_ID);

	public static final RegistryObject<BlockEntityType<?>> STRUCTURE_CASING = BLOCK_ENTITIES.register("structure_casing",() -> BlockEntityTypes.STRUCTURE_CASING);
}
