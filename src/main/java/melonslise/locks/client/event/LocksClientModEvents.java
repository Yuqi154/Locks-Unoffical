package melonslise.locks.client.event;

import melonslise.locks.Locks;
import melonslise.locks.client.init.LocksItemModelsProperties;
import melonslise.locks.client.init.LocksScreens;

public final class LocksClientModEvents
{
	private LocksClientModEvents() {}

	@SubscribeEvent
	public static void onSetup(FMLClientSetupEvent e)
	{
		LocksScreens.register();
		LocksItemModelsProperties.register();
	}
}