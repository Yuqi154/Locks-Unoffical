package melonslise.locks.common.compact;

import melonslise.locks.Locks;
import melonslise.locks.common.capability.ILockableHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import noobanidus.mods.lootr.init.ModBlocks;

import java.util.stream.Collectors;

public class LootrBlockUpdateHandler {
    public static void handleBlockUpdate(ServerLevel world, ILockableHandler handler, BlockPos pos, BlockState oldState, BlockState newState) {

        if(oldState.is(Blocks.CHEST)){
//            Locks.LOGGER.info("5");
//            Locks.LOGGER.info(oldState);
//            Locks.LOGGER.info(newState);
            return;
        }
        if(newState.is(ModBlocks.CHEST.get())){
//            Locks.LOGGER.info("6");
//            Locks.LOGGER.info(oldState);
//            Locks.LOGGER.info(newState);
            return;
        }

        // create buffer list because otherwise we will be deleting elements while iterating (BAD!!)
        handler.getInChunk(pos).values().stream().filter(lkb -> lkb.bb.intersects(pos)).collect(Collectors.toList()).forEach(lkb ->
        {
            world.playSound(null, pos, SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 0.8f, 0.8f + world.random.nextFloat() * 0.4f);
            world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, lkb.stack));
            handler.remove(lkb.id);
        });


    }
}
