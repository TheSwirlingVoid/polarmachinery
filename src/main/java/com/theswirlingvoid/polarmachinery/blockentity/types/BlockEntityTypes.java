package com.theswirlingvoid.polarmachinery.blockentity.types;

import com.theswirlingvoid.polarmachinery.block.BlockTypes;
import com.theswirlingvoid.polarmachinery.blockentity.StructureCasing;

import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityTypes {
	// to feed into tile entity constructors
	public static final BlockEntityType<StructureCasing> STRUCTURE_CASING = BlockEntityType.Builder.of(StructureCasing::new, BlockTypes.STRUCTURE_CASING).build(null);
}
