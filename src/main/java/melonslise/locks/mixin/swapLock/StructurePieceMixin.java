package melonslise.locks.mixin.swapLock;

import melonslise.locks.Locks;
import melonslise.locks.common.capability.ILockableHandler;
import melonslise.locks.common.config.LocksConfig;
import melonslise.locks.common.init.LocksCapabilities;
import melonslise.locks.common.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(StructureStart.class)
public abstract class StructurePieceMixin {
    @Shadow @Final private PiecesContainer pieceContainer;

    @Shadow public abstract boolean isValid();

    @Inject(method = "placeInChunk", at = @At(value = "TAIL"))
    public void placeBlock(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource pRandom, BoundingBox pBox, ChunkPos pChunkPos, CallbackInfo ci){
        List<StructurePiece> list = this.pieceContainer.pieces();
        if (list.isEmpty() && !this.isValid()) return;
        for (StructurePiece piece : list) {
            BoundingBox boundingBox = piece.getBoundingBox();

            for (int x = boundingBox.minX(); x <= boundingBox.maxX(); x++) {
                for (int y = boundingBox.minY(); y <= boundingBox.maxY(); y++) {
                    for (int z = boundingBox.minZ(); z <= boundingBox.maxZ(); z++) {
                        BlockPos pos = new BlockPos(x, y, z);

                        if ((pos.getX() >> 4) == pChunkPos.x && (pos.getZ() >> 4) == pChunkPos.z) {
                            if(LocksConfig.canGen(pRandom, pLevel, pos)){
                                ILockableHandler handler = pLevel.getLevel().getCapability(LocksCapabilities.LOCKABLE_HANDLER).orElse(null);
                                Cuboid6i bb = new Cuboid6i(pos, pos);
                                ItemStack stack = LocksConfig.getRandomLock(pRandom);
                                Lock lock = Lock.from(stack);
                                Direction dir = pLevel.getBlockState(pos).getValue(HorizontalDirectionalBlock.FACING);
                                Transform tr = Transform.fromDirection(dir, dir);
                                handler.add(new Lockable(bb, lock, tr, stack, pLevel.getLevel()));
                            }
                        }
                    }
                }
            }
        }
    }
}
