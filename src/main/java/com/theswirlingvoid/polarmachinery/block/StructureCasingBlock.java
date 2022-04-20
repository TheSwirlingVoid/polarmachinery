package com.theswirlingvoid.polarmachinery.block;

import java.util.ArrayList;
import java.util.List;

import com.theswirlingvoid.polarmachinery.blockentity.StructureCasing;
import com.theswirlingvoid.polarmachinery.blockentity.generic.MultiblockBlockEntity;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
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
		MultiblockBlockEntity blockEntity = (MultiblockBlockEntity) level.getBlockEntity(pos);
		blockEntity.setMaster(true);
		blockEntity.setHasMaster(false);
		BlockPos[] mostAndLowest = {pos, pos}; // 0 = lowest, 1 = most
		List<BlockPos> allBlocks = blockEntity.snakeSearchAndRun((b) -> {
			if (b != blockEntity) {
				b.setMaster(false);
				b.setHasMaster(true);
			}

			BlockPos thisPos = b.getBlockPos();
			int sum = thisPos.getX() + thisPos.getY() + thisPos.getZ();
			BlockPos most = mostAndLowest[0];
			BlockPos lowest = mostAndLowest[1];
			int mostSum = most.getX() + most.getY() + most.getZ();
			int lowestSum = lowest.getX() + lowest.getY() + lowest.getZ();
			if (sum > mostSum) {
				mostAndLowest[1] = thisPos;
			}
			else if (sum < lowestSum) {
				mostAndLowest[0] = thisPos;
			}
		}, new ArrayList<BlockPos>());

		for (BlockPos blockPos : allBlocks) {
			MultiblockBlockEntity foundBE = (MultiblockBlockEntity) level.getBlockEntity(blockPos);
			System.out.println(String.format("isMaster: %b; hasMaster: %b", foundBE.isMaster(), foundBE.hasMaster()));
		}
		level.players().get(0).sendMessage(new TextComponent("Lowest Pos: " + mostAndLowest[0] + ". Most Pos: " + mostAndLowest[1]), Util.NIL_UUID);
	}
	
}
