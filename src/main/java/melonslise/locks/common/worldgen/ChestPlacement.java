package melonslise.locks.common.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;
import java.util.stream.Stream;

public class ChestPlacement
{
	/*
	public ChestPlacement(Codec<NoneFeatureConfiguration> codec)
	{
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
		return false;
	}
	@Override
	public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rng, NoneFeatureConfiguration cfg, BlockPos pos)
	{
		return helper.level.getChunk(pos).getBlockEntitiesPos().stream()
			.filter(tePos ->
			{
				BlockState state = helper.level.getBlockState(tePos);
				// Prevent from adding double chests twice
				return state.hasProperty(ChestBlock.TYPE) && state.getValue(ChestBlock.TYPE) != ChestType.RIGHT;
			});
	}
	 */
}