package melonslise.locks.common.init;

import melonslise.locks.Locks;
import melonslise.locks.common.container.KeyRingContainer;
import melonslise.locks.common.container.LockPickingContainer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public final class LocksContainerTypes
{

	public static final MenuType<LockPickingContainer>
		LOCK_PICKING = add("lock_picking", new MenuType<>(LockPickingContainer.FACTORY, FeatureFlags.DEFAULT_FLAGS));

	public static final MenuType<KeyRingContainer>
		KEY_RING = add("key_ring", new MenuType<>(KeyRingContainer.FACTORY, FeatureFlags.DEFAULT_FLAGS));

	private LocksContainerTypes() {}

	public static void register()
	{
	}

	public static <T extends AbstractContainerMenu> MenuType<T> add(String name, MenuType<T> type)
	{
		return Registry.register(BuiltInRegistries.MENU,new ResourceLocation(Locks.ID,name),type);
	}
}