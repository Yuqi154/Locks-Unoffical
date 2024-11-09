package melonslise.locks.mixin;


import melonslise.locks.common.util.LocksUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@SuppressWarnings("DataFlowIssue")
@Mixin(RandomizableContainerBlockEntity.class)
public class RandomizableContainerBlockEntityMixin {
    @Inject(method = "getItem(I)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "RETURN"))
    private void lockRandomizableContainerBlockEntity(int pIndex, CallbackInfoReturnable<ItemStack> cir) {
        RandomizableContainerBlockEntity blockEntity = (RandomizableContainerBlockEntity) (Object) this;
        BlockPos pos = blockEntity.getBlockPos();
        Level level = blockEntity.getLevel();
        if (level == null) return;
        if (LocksUtil.locked(level, pos)) {
            cir.setReturnValue(ItemStack.EMPTY);
        }
    }

    @Inject(method = "removeItem(II)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "RETURN"))
    private void lockRandomizableContainerBlockEntityRemoveItem(int pIndex, int pCount, CallbackInfoReturnable<ItemStack> cir) {
        RandomizableContainerBlockEntity blockEntity = (RandomizableContainerBlockEntity) (Object) this;
        BlockPos pos = blockEntity.getBlockPos();
        Level level = blockEntity.getLevel();
        if (level == null) return;
        if (LocksUtil.locked(level, pos)) {
            cir.setReturnValue(ItemStack.EMPTY);
        }
    }

//    @Redirect(method = "setItem(ILnet/minecraft/world/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/RandomizableContainerBlockEntity;getItems()Lnet/minecraft/core/NonNullList;"))
//    private NonNullList<ItemStack> lockRandomizableContainerBlockEntitySetItem(RandomizableContainerBlockEntity instance) {
//        BlockPos pos = ((RandomizableContainerBlockEntity) (Object) this).getBlockPos();
//        Level level = ((RandomizableContainerBlockEntity) (Object) this).getLevel();
//        if (level == null) return instance.getItems();
//        if (LocksUtil.locked(level, pos)) {
//            return NonNullList.withSize(instance.getContainerSize(), ItemStack.EMPTY);
//        }
//        return instance.getItems();
//    }

}
