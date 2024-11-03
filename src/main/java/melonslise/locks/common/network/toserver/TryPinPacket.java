package melonslise.locks.common.network.toserver;

import melonslise.locks.Locks;
import melonslise.locks.common.container.LockPickingContainer;
import melonslise.locks.common.init.LocksContainerTypes;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class TryPinPacket
{
	public static final ResourceLocation ID = new ResourceLocation(Locks.ID, "try_pin");
	public final byte pin;

	public TryPinPacket(byte pin)
	{
		this.pin = pin;
	}

	public static TryPinPacket decode(FriendlyByteBuf buf)
	{
		return new TryPinPacket(buf.readByte());
	}

	public static FriendlyByteBuf encode(TryPinPacket pkt)
	{
		FriendlyByteBuf buf = PacketByteBufs.create();
		buf.writeByte(pkt.pin);
		return buf;
	}

}