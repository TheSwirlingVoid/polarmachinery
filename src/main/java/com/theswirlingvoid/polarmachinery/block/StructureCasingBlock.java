package com.theswirlingvoid.polarmachinery.block;

import java.util.ArrayList;
import java.util.List;

import com.theswirlingvoid.polarmachinery.blockentity.StructureCasing;
import com.theswirlingvoid.polarmachinery.blockentity.generic.DimensionalFreezerBE;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class StructureCasingBlock extends Block implements EntityBlock {

	public StructureCasingBlock(Properties properties) {
		super(
			Properties.of(Material.STONE)
		);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState block) {
		return new StructureCasing(blockPos, block);
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state2, boolean bool) {
		DimensionalFreezerBE blockEntity = (DimensionalFreezerBE) level.getBlockEntity(pos);
		blockEntity.setMaster(true);
		blockEntity.setHasMaster(false);
		List<BlockPos> allBlocks = blockEntity.snakeSearchAndRun((b) -> {
			if (b != blockEntity) {
				b.setMaster(false);
				b.setHasMaster(true);
			}
		}, new ArrayList<BlockPos>());

		for (BlockPos blockPos : allBlocks) {
			DimensionalFreezerBE foundBE = (DimensionalFreezerBE) level.getBlockEntity(blockPos);
			System.out.println(String.format("isMaster: %b; hasMaster: %b", foundBE.isMaster(), foundBE.hasMaster()));
		}
	}
	
}
