package melonslise.locks.compact.mixin;


import com.beansgalaxy.backpacks.events.CommonForgeEvents;
import melonslise.locks.common.util.Lockable;
import melonslise.locks.common.util.LocksPredicates;
import melonslise.locks.compact.LootrCompactHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Arrays;
import java.util.Optional;

@Mixin(CommonForgeEvents.class)
public class BeansBackpacksMixin {

    @Redirect(remap = false,method = "playerInteract(Lnet/minecraftforge/event/entity/player/PlayerInteractEvent$RightClickBlock;)V",at=@At(remap = false,value = "INVOKE",target = "Lnet/minecraftforge/event/entity/player/PlayerInteractEvent$RightClickBlock;setUseBlock(Lnet/minecraftforge/eventbus/api/Event$Result;)V"))
    private static void onsetUseBlock(PlayerInteractEvent.RightClickBlock instance, Event.Result triggerBlock){
        BlockPos pos = instance.getPos();
        Level level = instance.getLevel();
        BlockState blockState = level.getBlockState(pos);
        if(ModList.get().isLoaded("lootr")){
            if (LootrCompactHandler.handleInteract(level,pos,blockState)){
                return;
            }else {
                instance.setUseBlock(triggerBlock);
            }
        }else {
            if(blockState.is(Blocks.CHEST)||blockState.is(Blocks.TRAPPED_CHEST)){
                ILockableHandler handler = level.getCapability(LocksCapabilities.LOCKABLE_HANDLER).orElse(null);
                Lockable[] intersect = handler.getInChunk(pos).values().stream().filter(lkb -> lkb.bb.intersects(pos)).toArray(Lockable[]::new);
                Optional<Lockable> locked = Arrays.stream(intersect).filter(LocksPredicates.LOCKED).findFirst();
                if(locked.isPresent()){
                    return;
                }else {
                    instance.setUseBlock(triggerBlock);
                }
            }
        }
    }
}
