package dev.sweetberry.liberry.content.block;

import dev.sweetberry.liberry.Liberry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FungusBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class LiberryFungusBlock extends FungusBlock {
	public final TagKey<Block> plantable;

	public LiberryFungusBlock(Settings settings, Identifier baseId, Block block) {
		super(settings, RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, baseId.extendPath("_tree")), block);
		this.plantable = TagKey.of(RegistryKeys.BLOCK, baseId.extendPath("_growable"));
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isIn(plantable) || super.canPlantOnTop(floor, world, pos);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		var v = state.getModelOffset(world, pos);
		return super.getOutlineShape(state, world, pos, context).offset(v.x, v.y, v.z);
	}
}
