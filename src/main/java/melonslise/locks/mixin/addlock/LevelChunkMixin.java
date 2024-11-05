package melonslise.locks.mixin.addlock;

import melonslise.locks.common.capability.ILockableHandler;
import melonslise.locks.common.capability.ILockableStorage;
import melonslise.locks.common.config.LocksConfig;
import melonslise.locks.common.init.LocksCapabilities;
import melonslise.locks.common.init.LocksNetwork;
import melonslise.locks.common.network.toclient.AddLockableToChunkPacket;
import melonslise.locks.common.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.DOOR_HINGE;
import static net.minecraft.world.level.block.state.properties.DoorHingeSide.LEFT;
import static net.minecraft.world.level.block.state.properties.DoubleBlockHalf.LOWER;

@Mixin(value = LevelChunk.class, priority = 1001)
public abstract class LevelChunkMixin {
    @Shadow
    public abstract Level getLevel();


    @Shadow
    @Final
    private Level level;

    @Inject(method = "updateBlockEntityTicker", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getTicker(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/entity/BlockEntityType;)Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", shift = At.Shift.AFTER))
    public void lockChest(BlockEntity entity, CallbackInfo ci) {
        if (entity instanceof RandomizableContainerBlockEntity) {
            if (this.getLevel().isClientSide()) return;
            ChunkAccess chunkAccess = (ChunkAccess) (Object) this;
            LevelChunk ch = (LevelChunk) (Object) this;
            BlockPos blockPos = entity.getBlockPos();
            BlockState state = entity.getBlockState();
            RandomSource randomSource = RandomSource.create();
            if (this.getLevel().hasChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4)) {
                if (LocksConfig.canGen(randomSource, state.getBlock())) {
                    BlockPos pos1 = blockPos;
                    Direction dir = null;
                    if (state.hasProperty(FACING)) {
                        dir = state.getValue(FACING);
                    } else if (state.hasProperty(HORIZONTAL_FACING)) {
                        dir = state.getValue(HORIZONTAL_FACING);
                    }

                    if (state.hasProperty(CHEST_TYPE)) {
                        switch (state.getValue(CHEST_TYPE)) {
                            case LEFT -> pos1 = blockPos.relative(ChestBlock.getConnectedDirection(state));
                            case RIGHT -> {
                                return;
                            }
                        }
                    }
                    Cuboid6i bb = new Cuboid6i(blockPos, pos1);
                    ItemStack stack = LocksConfig.getRandomLock(randomSource);
                    Lock lock = Lock.from(stack);
                    Transform tr = dir == null ? Transform.NORTH_UP : Transform.fromDirection(dir, dir);
                    Lockable lkb = new Lockable(bb, lock, tr, stack, this.getLevel());
                    lkb.bb.getContainedChunks((x, z) -> {
                        ILockableStorage st = ch.getCapability(LocksCapabilities.LOCKABLE_STORAGE).orElse(null);
                        ILockableHandler handler = this.level.getCapability(LocksCapabilities.LOCKABLE_HANDLER).orElse(null);
                        st.add(lkb);
                        handler.getLoaded().put(lkb.id, lkb);
                        lkb.addObserver(handler);
                        LocksNetwork.MAIN.send(PacketDistributor.TRACKING_CHUNK.with(() -> ch), new AddLockableToChunkPacket(lkb, ch));
                        return true;
                    });
                }
            }
        }
    }
}
