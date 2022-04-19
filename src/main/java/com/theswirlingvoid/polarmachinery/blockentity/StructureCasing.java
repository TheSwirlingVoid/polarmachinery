package com.theswirlingvoid.polarmachinery.blockentity;

import com.theswirlingvoid.polarmachinery.blockentity.generic.DimensionalFreezerBE;
import com.theswirlingvoid.polarmachinery.blockentity.types.BlockEntityTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class StructureCasing extends DimensionalFreezerBE {

	public StructureCasing(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityTypes.STRUCTURE_CASING, blockPos, blockState);
	}
}
