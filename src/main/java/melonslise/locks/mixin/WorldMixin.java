package melonslise.locks.mixin;

import melonslise.locks.common.util.LocksUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(Level.class)
public abstract class WorldMixin implements SignalGetter {
    @Override
    public boolean hasNeighborSignal(@NotNull BlockPos pPos) {
        if (LocksUtil.locked((Level) (Object) this, pPos)) {
            return false;
        }
        if (this.getSignal(pPos.below(), Direction.DOWN) > 0) {
            return true;
        } else if (this.getSignal(pPos.above(), Direction.UP) > 0) {
            return true;
        } else if (this.getSignal(pPos.north(), Direction.NORTH) > 0) {
            return true;
        } else if (this.getSignal(pPos.south(), Direction.SOUTH) > 0) {
            return true;
        } else if (this.getSignal(pPos.west(), Direction.WEST) > 0) {
            return true;
        } else {
            return this.getSignal(pPos.east(), Direction.EAST) > 0;
        }
    }
}