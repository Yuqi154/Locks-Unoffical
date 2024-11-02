package melonslise.locks.mixin.swapLock;

import melonslise.locks.Locks;
import melonslise.locks.common.config.LocksConfig;
import melonslise.locks.common.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;
import static net.minecraft.world.level.block.state.properties.DoorHingeSide.LEFT;
import static net.minecraft.world.level.block.state.properties.DoubleBlockHalf.LOWER;

@Mixin(WorldGenRegion.class)
public class WorldGenRegionMixin {
    @Shadow @Final private ServerLevel level;

    @Inject(method = "setBlock", at = @At(value = "RETURN", ordinal = 1), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void lockBlock(BlockPos blockPos, BlockState pState, int pFlags, int pRecursionLeft, CallbackInfoReturnable<Boolean> cir, ChunkAccess chunkaccess, BlockState state) {
        ServerLevel level = this.level;
        RandomSource randomSource = RandomSource.create();
        if (level.hasChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4)) {
            if (LocksConfig.canGen(randomSource, pState.getBlock())) {
                Locks.LOGGER.info(blockPos + pState.getBlock().getName().toString());

                BlockPos pos1 = blockPos;
                Direction dir;
                if (state.hasProperty(FACING)) {
                    dir = state.getValue(FACING);
                } else if (state.hasProperty(HORIZONTAL_FACING)) {
                    dir = state.getValue(HORIZONTAL_FACING);
                } else {
                    dir = Direction.NORTH;
                }

                if (state.hasProperty(CHEST_TYPE)) {
                    switch (state.getValue(CHEST_TYPE)) {
                        case LEFT -> pos1 = blockPos.relative(ChestBlock.getConnectedDirection(state));
                        case RIGHT -> {
                            return;
                        }
                    }
                }
                if (state.hasProperty(DOUBLE_BLOCK_HALF)) {
                    if (state.getValue(DOUBLE_BLOCK_HALF) == LOWER) return;
                    pos1 = blockPos.below();
                    if (state.hasProperty(DOOR_HINGE)) {
                        if (state.hasProperty(DOOR_HINGE) && state.hasProperty(HORIZONTAL_FACING)) {
                            BlockPos pos2 = pos1.relative(state.getValue(DOOR_HINGE) == LEFT ? dir.getClockWise() : dir.getCounterClockWise());
                            if (chunkaccess.getBlockState(pos2).is(state.getBlock())) {
                                if (state.getValue(DOOR_HINGE) == LEFT) {
                                    return;
                                }
                                pos1 = pos2;
                            }
                        }
                        dir = dir.getOpposite();
                    }
                }
                Cuboid6i bb = new Cuboid6i(blockPos, pos1);
                ItemStack stack = LocksConfig.getRandomLock(randomSource);
                Lock lock = Lock.from(stack);
                Transform tr = Transform.fromDirection(dir, dir);
                if (tr == null) return;
                Lockable lkb = new Lockable(bb, lock, tr, stack, level);
                lkb.bb.getContainedChunks((x, z) -> {
                    ((ILockableProvider) chunkaccess).getLockables().add(lkb);
                    return true;
                });
            }
        }
    }
}
