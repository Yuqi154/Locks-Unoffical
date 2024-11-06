package melonslise.locks.mixin;


import melonslise.locks.common.capability.ILockableHandler;
import melonslise.locks.common.init.LocksCapabilities;
import melonslise.locks.common.util.Lockable;
import melonslise.locks.common.util.LocksPredicates;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.*;

@SuppressWarnings("DataFlowIssue")
@Mixin(RandomizableContainerBlockEntity.class)
public class RandomizableContainerBlockEntityMixin {

    @Redirect(method = "getItem(I)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/RandomizableContainerBlockEntity;getItems()Lnet/minecraft/core/NonNullList;"))
    private NonNullList<ItemStack> lockRandomizableContainerBlockEntity(RandomizableContainerBlockEntity instance) {
        BlockPos pos = ((RandomizableContainerBlockEntity) (Object) this).getBlockPos();
        Level level = ((RandomizableContainerBlockEntity) (Object) this).getLevel();
        if(level==null){
            return instance.getItems();
        }

        ILockableHandler handler = level.getCapability(LocksCapabilities.LOCKABLE_HANDLER).orElse(null);
        Lockable[] intersect = handler.getInChunk(pos).values().stream().filter(lkb -> lkb.bb.intersects(pos)).toArray(Lockable[]::new);
        Optional<Lockable> locked = Arrays.stream(intersect).filter(LocksPredicates.LOCKED).findFirst();
        if(locked.isPresent())
        {
            return NonNullList.withSize(instance.getContainerSize(), ItemStack.EMPTY);
        }
        return instance.getItems();
    }

    @Redirect(method = "removeItem(II)Lnet/minecraft/world/item/ItemStack;",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/RandomizableContainerBlockEntity;getItems()Lnet/minecraft/core/NonNullList;"))
    private NonNullList<ItemStack> lockRandomizableContainerBlockEntityRemoveItem(RandomizableContainerBlockEntity instance) {
        BlockPos pos = ((RandomizableContainerBlockEntity) (Object) this).getBlockPos();
        Level level = ((RandomizableContainerBlockEntity) (Object) this).getLevel();
        if(level==null){
            return instance.getItems();
        }
        ILockableHandler handler = level.getCapability(LocksCapabilities.LOCKABLE_HANDLER).orElse(null);
        Lockable[] intersect = handler.getInChunk(pos).values().stream().filter(lkb -> lkb.bb.intersects(pos)).toArray(Lockable[]::new);
        Optional<Lockable> locked = Arrays.stream(intersect).filter(LocksPredicates.LOCKED).findFirst();
        if(locked.isPresent())
        {
            return NonNullList.withSize(instance.getContainerSize(), ItemStack.EMPTY);
        }
        return instance.getItems();
    }
    @Redirect(method = "removeItem(II)Lnet/minecraft/world/item/ItemStack;",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ContainerHelper;removeItem(Ljava/util/List;II)Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack lockRandomizableContainerBlockEntityRemoveItem(List<ItemStack> list, int i, int j) {
        BlockPos pos = ((RandomizableContainerBlockEntity) (Object) this).getBlockPos();
        Level level = ((RandomizableContainerBlockEntity) (Object) this).getLevel();
        if(level==null){
            return ContainerHelper.removeItem(list, i, j);
        }
        ILockableHandler handler = level.getCapability(LocksCapabilities.LOCKABLE_HANDLER).orElse(null);
        Lockable[] intersect = handler.getInChunk(pos).values().stream().filter(lkb -> lkb.bb.intersects(pos)).toArray(Lockable[]::new);
        Optional<Lockable> locked = Arrays.stream(intersect).filter(LocksPredicates.LOCKED).findFirst();
        if(locked.isPresent())
        {
            return ItemStack.EMPTY;
        }
        return ContainerHelper.removeItem(list, i, j);
    }
    @Redirect(method = "setItem(ILnet/minecraft/world/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/RandomizableContainerBlockEntity;getItems()Lnet/minecraft/core/NonNullList;"))
    private NonNullList<ItemStack> lockRandomizableContainerBlockEntitySetItem(RandomizableContainerBlockEntity instance) {
        BlockPos pos = ((RandomizableContainerBlockEntity) (Object) this).getBlockPos();
        Level level = ((RandomizableContainerBlockEntity) (Object) this).getLevel();
        if(level==null){
            return instance.getItems();
        }
        ILockableHandler handler = level.getCapability(LocksCapabilities.LOCKABLE_HANDLER).orElse(null);
        Lockable[] intersect = handler.getInChunk(pos).values().stream().filter(lkb -> lkb.bb.intersects(pos)).toArray(Lockable[]::new);
        Optional<Lockable> locked = Arrays.stream(intersect).filter(LocksPredicates.LOCKED).findFirst();
        if(locked.isPresent())
        {
            return NonNullList.withSize(instance.getContainerSize(), ItemStack.EMPTY);
        }
        return instance.getItems();
    }

}
