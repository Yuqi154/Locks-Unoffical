package melonslise.locks.mixin;

import com.google.gson.JsonElement;
import melonslise.locks.common.util.LocksUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.storage.loot.LootDataType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LootDataType.class)
public class LootTableManagerMixin
{
	@Inject(at = @At("HEAD"), method = "deserialize", remap = false)
	private void apply(ResourceLocation p_279253_, JsonElement p_279330_, ResourceManager resourceManager, CallbackInfoReturnable<Optional<?>> cir)
	{
		LocksUtil.resourceManager = resourceManager;
	}
}