package melonslise.locks.mixin.swapLock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Feature.class)
public class FeatureMixin {
    @Inject(method = "setBlock", at = @At("TAIL"))
    public void onFillWithLoot(LevelWriter pLevel, BlockPos pPos, BlockState pState, CallbackInfo ci) {
        //pLevel.setBlock(pPos, Blocks.RED_WOOL.defaultBlockState(), 2);
    }
}
