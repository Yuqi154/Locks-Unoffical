package melonslise.locks.common.init;

import melonslise.locks.Locks;
import melonslise.locks.common.network.toclient.*;
import melonslise.locks.common.network.toserver.TryPinPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class LocksNetwork
{
	public static final SimpleChannel MAIN = NetworkRegistry.newSimpleChannel(new ResourceLocation(Locks.ID, "main"), () -> "locks", a -> true, a -> true);

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
		MAIN.registerMessage(4, TryPinPacket.class, TryPinPacket::encode, TryPinPacket::decode, TryPinPacket::handle);
	}
}