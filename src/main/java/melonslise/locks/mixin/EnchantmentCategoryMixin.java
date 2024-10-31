package melonslise.locks.mixin;

import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(EnchantmentCategory.class)
public class EnchantmentCategoryMixin {

    @Shadow
    @Final
    @Mutable
    private static EnchantmentCategory[] $VALUES;

}
