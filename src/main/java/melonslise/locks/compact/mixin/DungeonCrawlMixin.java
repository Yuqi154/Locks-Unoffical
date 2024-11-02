package melonslise.locks.compact.mixin;


import melonslise.locks.common.config.LocksConfig;
import melonslise.locks.common.util.LocksUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xiroc.dungeoncrawl.dungeon.model.DungeonModelFeature;
import xiroc.dungeoncrawl.theme.SecondaryTheme;
import xiroc.dungeoncrawl.theme.Theme;

@Mixin(DungeonModelFeature.class)
public class DungeonCrawlMixin {

    @Inject(remap = false,method = "placeChest(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lxiroc/dungeoncrawl/theme/Theme;Lxiroc/dungeoncrawl/theme/SecondaryTheme;ILnet/minecraft/util/RandomSource;)V", at = @At(value = "RETURN"))
    private static void onRun(LevelAccessor world, BlockPos pos, BlockState chest, Theme theme, SecondaryTheme secondaryTheme, int lootLevel, RandomSource rand, CallbackInfo ci) {
        if (world instanceof ServerLevelAccessor accessor) {
            Block block = chest.getBlock();
            if (LocksConfig.canGen(rand, block)) {
                //LocksUtil.lockWhenGen(accessor, pos, rand);
            }
        }
    }

}
