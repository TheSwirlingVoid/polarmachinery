package com.theswirlingvoid.polarmachinery.blockentity.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class MultiblockBlockEntity extends BlockEntity {

	private boolean isMaster = false;
	private BlockEntityType<MultiblockBlockEntity> typeBE;

	@SuppressWarnings("unchecked")
	protected MultiblockBlockEntity(BlockEntityType<?> type, BlockPos blockPos, BlockState blockState) {
		super(type, blockPos, blockState);
		typeBE = (BlockEntityType<MultiblockBlockEntity>) type; // only called by sub classes
	}
	
	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		isMaster = tag.getBoolean("isMaster");
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putBoolean("isMaster", isMaster);
	}

	public boolean isMaster() {
		return isMaster;
	}

	public void setMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}

	/** 
	 * Get all blocks adjacent to this block that extends the class type {@code DimensionalFreezerBE}.
	 * @return A list of type {@code BlockPos} that contains each adjacent found block.
	 */
	public List<BlockPos> getAdjacentParts() {
		BlockPos currentPos = this.getBlockPos();
		List<BlockPos> adj = new ArrayList<BlockPos>();
		// X, -X
		this.getLevel().getBlockEntity(currentPos.offset(1, 0, 0), typeBE).ifPresent((blockEntity) -> adj.add(blockEntity.getBlockPos()));
		this.getLevel().getBlockEntity(currentPos.offset(-1, 0, 0), typeBE).ifPresent((blockEntity) -> adj.add(blockEntity.getBlockPos()));
		// Y, -Y
		this.getLevel().getBlockEntity(currentPos.offset(0, 1, 0), typeBE).ifPresent((blockEntity) -> adj.add(blockEntity.getBlockPos()));
		this.getLevel().getBlockEntity(currentPos.offset(0, -1, 0), typeBE).ifPresent((blockEntity) -> adj.add(blockEntity.getBlockPos()));
		// Z, -Z
		this.getLevel().getBlockEntity(currentPos.offset(0, 0, 1), typeBE).ifPresent((blockEntity) -> adj.add(blockEntity.getBlockPos()));
		this.getLevel().getBlockEntity(currentPos.offset(0, 0, -1), typeBE).ifPresent((blockEntity) -> adj.add(blockEntity.getBlockPos()));

		return adj;
	}

	/**
	 * Find all tile entities of this type connected to the one running this function, and run a given function for each connected.
	 * @param func The function to run
	 * @param checkedBlocks A list in which to add blocks that have been checked. Most likely you should make this code new ArrayList<BlockPos>()}.
	 * @return A {@code List<DimensionalFreezerBE>} of affected blocks.
	 */
	public List<BlockPos> snakeSearchAndRun(Consumer<MultiblockBlockEntity> func, @Nullable List<BlockPos> checkedPositions) {
		checkedPositions.add(this.getBlockPos());
		func.accept(this);														// run given function for this block
		List<BlockPos> adjBlocksPos = this.getAdjacentParts(); 					// adjacent blocks to this specific block

		for (BlockPos blockPos : adjBlocksPos) {
			if (!checkedPositions.contains(blockPos)) {							// for every block that hasn't been checked, recurse on it
				((MultiblockBlockEntity) this.getLevel().getBlockEntity(blockPos)).snakeSearchAndRun(func, checkedPositions);
			}
		}
		return checkedPositions;
	}
}
