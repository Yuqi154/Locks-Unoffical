package melonslise.locks.common.init;

import melonslise.locks.common.network.toclient.*;
import melonslise.locks.common.network.toserver.TryPinPacket;

public final class LocksNetwork
{
	private LocksNetwork() {}

	public static void register()
	{
		//S2C
		AddLockablePacket.register();
		AddLockableToChunkPacket.register();
		RemoveLockablePacket.register();
		UpdateLockablePacket.register();
		TryPinResultPacket.register();
		//C2S
		TryPinPacket.register();
	}
}