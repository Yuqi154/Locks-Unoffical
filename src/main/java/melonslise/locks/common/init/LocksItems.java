package melonslise.locks.common.init;

import melonslise.locks.Locks;
import melonslise.locks.common.item.KeyItem;
import melonslise.locks.common.item.KeyRingItem;
import melonslise.locks.common.item.LockItem;
import melonslise.locks.common.item.LockPickItem;
import melonslise.locks.common.item.MasterKeyItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class LocksItems
{
	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Locks.ID);

	public static final RegistryObject<CreativeModeTab> TAB = TABS.register(Locks.ID,
			()-> CreativeModeTab
					.builder()
					.icon(()->new ItemStack(LocksItems.IRON_LOCK.get()))
					.displayItems((parameters, output)->{
						output.accept(LocksItems.SPRING.get());
						output.accept(LocksItems.WOOD_LOCK_MECHANISM.get());
						output.accept(LocksItems.IRON_LOCK_MECHANISM.get());
						output.accept(LocksItems.STEEL_LOCK_MECHANISM.get());
						output.accept(LocksItems.KEY_BLANK.get());
						output.accept(LocksItems.WOOD_LOCK.get());
						output.accept(LocksItems.IRON_LOCK.get());
						output.accept(LocksItems.STEEL_LOCK.get());
						output.accept(LocksItems.GOLD_LOCK.get());
						output.accept(LocksItems.DIAMOND_LOCK.get());
						output.accept(LocksItems.KEY.get());
						output.accept(LocksItems.MASTER_KEY.get());
						output.accept(LocksItems.KEY_RING.get());
						output.accept(LocksItems.WOOD_LOCK_PICK.get());
						output.accept(LocksItems.IRON_LOCK_PICK.get());
						output.accept(LocksItems.STEEL_LOCK_PICK.get());
						output.accept(LocksItems.GOLD_LOCK_PICK.get());
						output.accept(LocksItems.DIAMOND_LOCK_PICK.get());
					})
					.build()
	);
	/*
	public static final CreativeModeTab TAB = new CreativeModeTab(Locks.ID)
	{
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack getIconItem()
		{
			return new ItemStack(LocksItems.IRON_LOCK.get());
		}
	}.setEnchantmentCategories(LocksEnchantments.LOCK_TYPE);
	 */

	public static final DeferredRegister ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Locks.ID);

	public static final RegistryObject<Item>
		SPRING = add("spring", new Item(new Item.Properties())),
		WOOD_LOCK_MECHANISM = add("wood_lock_mechanism", new Item(new Item.Properties())),
		IRON_LOCK_MECHANISM = add("iron_lock_mechanism", new Item(new Item.Properties())),
		STEEL_LOCK_MECHANISM = add("steel_lock_mechanism", new Item(new Item.Properties())),
		KEY_BLANK = add("key_blank", new Item(new Item.Properties())),
		WOOD_LOCK = add("wood_lock", new LockItem(5, 15, 4, new Item.Properties())),
		IRON_LOCK = add("iron_lock", new LockItem(7, 14, 12, new Item.Properties())),
		STEEL_LOCK = add("steel_lock", new LockItem(9, 12, 20, new Item.Properties())),
		GOLD_LOCK = add("gold_lock", new LockItem(6, 22, 6, new Item.Properties())),
		DIAMOND_LOCK = add("diamond_lock", new LockItem(11, 10, 100, new Item.Properties())),
		KEY = add("key", new KeyItem(new Item.Properties())),
		MASTER_KEY = add("master_key", new MasterKeyItem(new Item.Properties())),
		KEY_RING = add("key_ring", new KeyRingItem(1, new Item.Properties())),
		WOOD_LOCK_PICK = add("wood_lock_pick", new LockPickItem(0.2f, new Item.Properties())),
		IRON_LOCK_PICK = add("iron_lock_pick", new LockPickItem(0.35f, new Item.Properties())),
		STEEL_LOCK_PICK = add("steel_lock_pick", new LockPickItem(0.7f, new Item.Properties())),
		GOLD_LOCK_PICK = add("gold_lock_pick", new LockPickItem(0.25f, new Item.Properties())),
		DIAMOND_LOCK_PICK = add("diamond_lock_pick", new LockPickItem(0.85f, new Item.Properties()));

	private LocksItems() {}

	public static void register()
	{
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	public static RegistryObject<Item> add(String name, Item item)
	{
		return ITEMS.register(name, () -> item);
	}
}