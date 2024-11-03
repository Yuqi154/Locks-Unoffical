package melonslise.locks.common.init;

import melonslise.locks.common.network.toserver.ServerNet;
import melonslise.locks.common.network.toserver.TryPinPacket;

public final class LocksNetwork
{
	private LocksNetwork() {}

	public static void register()
	{
		//C2S
		ServerNet.register();
	}
}