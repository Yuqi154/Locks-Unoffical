package melonslise.locks.mixin.compact;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "noobanidus.mods.lootr.block.entities.TileTicker")
public class LootrMixin {
    @Redirect(method = "serverTick(Lnet/minecraftforge/event/TickEvent$ServerTickEvent;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;destroyBlock(Lnet/minecraft/core/BlockPos;Z)Z"))
    private static boolean lockLootr(ServerLevel instance, BlockPos pos, boolean b){
        return b;
    }
}
