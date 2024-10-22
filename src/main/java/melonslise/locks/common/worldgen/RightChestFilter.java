package melonslise.locks.common.worldgen;

import com.mojang.serialization.Codec;
import melonslise.locks.common.init.LocksPlacementModifiers;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RightChestFilter extends PlacementFilter {
    public static final Codec<RightChestFilter> CODEC = Codec.unit(RightChestFilter::new);
    @Override
    protected boolean shouldPlace(@NotNull PlacementContext pContext, @NotNull RandomSource pRandom, @NotNull BlockPos pPos) {
        var state = pContext.getLevel().getBlockState(pPos);
        return state.hasProperty(ChestBlock.TYPE) && state.getValue(ChestBlock.TYPE) != ChestType.RIGHT;
    }

    @Override
    public @NotNull PlacementModifierType<?> type() {
        return LocksPlacementModifiers.RIGHT_CHEST_FILTER.get();
    }
}
