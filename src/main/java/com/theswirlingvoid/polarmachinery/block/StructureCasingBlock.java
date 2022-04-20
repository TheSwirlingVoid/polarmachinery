package com.theswirlingvoid.polarmachinery.block;

import java.util.ArrayList;
import java.util.List;

import com.theswirlingvoid.polarmachinery.blockentity.StructureCasing;
import com.theswirlingvoid.polarmachinery.blockentity.generic.MultiblockBlockEntity;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
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
		BlockPos[] mostAndLowest = {pos, pos}; // 0 = lowest, 1 = most
		List<BlockPos> allBlocks = blockEntity.snakeSearchAndRun((b) -> {
			BlockPos thisPos = b.getBlockPos();
			BlockPos most = mostAndLowest[1];
			BlockPos lowest = mostAndLowest[0];
			BlockPos compareMost = thisPos.subtract(most);
			BlockPos compareLeast = thisPos.subtract(lowest);
			if (compareMost.getX() >= 0 && compareMost.getY() >= 0 && compareMost.getZ() >= 0) {
				mostAndLowest[1] = thisPos;
			}
			else if (compareLeast.getX() <= 0 && compareLeast.getY() <= 0 && compareLeast.getZ() <= 0) {
				mostAndLowest[0] = thisPos;
			}
		}, new ArrayList<BlockPos>());

		if (allBlocks.isEmpty()) {
			blockEntity.setMaster(true);
		}
		else {
			for (BlockPos blockPos : allBlocks) {
				MultiblockBlockEntity foundBE = (MultiblockBlockEntity) level.getBlockEntity(blockPos);
				// TODO: remove test line below
				System.out.println(String.format("isMaster: %b", foundBE.isMaster()));
			}
		}
		// TODO: remove test line below
		level.players().get(0).sendMessage(new TextComponent("Dimensions: " + mostAndLowest[1].subtract(mostAndLowest[0]).subtract(new Vec3i(-1, -1, -1)).toShortString()), Util.NIL_UUID);
		level.players().get(0).sendMessage(new TextComponent("Block Count: " + allBlocks.size()), Util.NIL_UUID);
		level.players().get(0).sendMessage(new TextComponent("Lowest: " + mostAndLowest[0] + "Most: " + mostAndLowest[1]), Util.NIL_UUID);
	}
	
}
