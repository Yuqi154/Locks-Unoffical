package melonslise.locks.compact;

import melonslise.locks.Locks;
import melonslise.locks.common.capability.ILockableHandler;
import melonslise.locks.common.init.LocksCapabilities;
import melonslise.locks.common.util.Lockable;
import melonslise.locks.common.util.LocksPredicates;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import noobanidus.mods.lootr.init.ModBlocks;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class LootrCompactHandler {
    public static void handleBlockUpdate(ServerLevel world, ILockableHandler handler, BlockPos pos, BlockState oldState, BlockState newState) {

        if(oldState.is(Blocks.CHEST)||oldState.is(Blocks.TRAPPED_CHEST)||oldState.is(Blocks.BARREL)){
            return;
        }
        if(newState.is(ModBlocks.CHEST.get())||newState.is(ModBlocks.TRAPPED_CHEST.get())||newState.is(ModBlocks.BARREL.get())){
            return;
        }

        handler.getInChunk(pos).values().stream().filter(lkb -> lkb.bb.intersects(pos)).collect(Collectors.toList()).forEach(lkb ->
        {
            world.playSound(null, pos, SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, 0.8f, 0.8f + world.random.nextFloat() * 0.4f);
            world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, lkb.stack));
            handler.remove(lkb.id);
        });

    }
    public static boolean handleInteract(Level level,BlockPos pos,BlockState blockState){
        if(blockState.is(Blocks.CHEST)||blockState.is(Blocks.TRAPPED_CHEST)){
            ILockableHandler handler = level.getCapability(LocksCapabilities.LOCKABLE_HANDLER).orElse(null);
            Lockable[] intersect = handler.getInChunk(pos).values().stream().filter(lkb -> lkb.bb.intersects(pos)).toArray(Lockable[]::new);
            Optional<Lockable> locked = Arrays.stream(intersect).filter(LocksPredicates.LOCKED).findFirst();
            return locked.isPresent();
        }
        return false;
    }
}
