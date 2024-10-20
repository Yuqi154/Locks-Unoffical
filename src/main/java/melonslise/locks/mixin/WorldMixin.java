package melonslise.locks.mixin;

import melonslise.locks.common.util.LocksUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Level.class)
public class WorldMixin {
    /*
    private boolean hasNeighborSignal(BlockPos pos) {
        if (LocksUtil.locked((Level) (Object) this, pos)) {
            return false;
        } else {
            return this.hasNeighborSignal(pos);
        }
    }
     */
}