package melonslise.locks.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.LootPool;

@Mixin(LootPool.class)
public interface LootPoolAccessor
{
	@Accessor
	List<LootPoolEntryContainer> getEntries();
}