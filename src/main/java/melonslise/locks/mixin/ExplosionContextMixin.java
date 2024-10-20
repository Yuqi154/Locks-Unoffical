package melonslise.locks.mixin;

import melonslise.locks.common.item.LockItem;
import melonslise.locks.common.util.LocksPredicates;
import melonslise.locks.common.util.LocksUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ExplosionDamageCalculator.class)
public class ExplosionContextMixin
{
	@Inject(at = @At("RETURN"), method = "getBlockExplosionResistance", cancellable = true)
	private void getBlockExplosionResistance(Explosion ex, BlockGetter pReader, BlockPos pos, BlockState pState, FluidState pFluid, CallbackInfoReturnable<Optional<Float>> cir)
	{
		cir.setReturnValue(cir.getReturnValue().map(r -> Math.max(r, LocksUtil.intersecting(ex.getExploder().level(), pos).filter(LocksPredicates.LOCKED).findFirst().map(lkb -> LockItem.getResistance(lkb.stack)).orElse(0))));
	}
}