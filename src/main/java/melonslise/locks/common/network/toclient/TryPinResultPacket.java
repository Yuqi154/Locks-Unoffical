package melonslise.locks.common.network.toclient;

import melonslise.locks.Locks;
import melonslise.locks.common.container.LockPickingContainer;
import melonslise.locks.common.init.LocksContainerTypes;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class TryPinResultPacket
{
	public static final ResourceLocation ID = new ResourceLocation(Locks.ID, "try_pin_result");
	private final boolean correct, reset;

	public TryPinResultPacket(boolean correct, boolean reset)
	{
		this.correct = correct;
		this.reset = reset;
	}

	public static TryPinResultPacket decode(FriendlyByteBuf buf)
	{
		return new TryPinResultPacket(buf.readBoolean(), buf.readBoolean());
	}

	public static FriendlyByteBuf encode(TryPinResultPacket pkt)
	{
		FriendlyByteBuf buf = PacketByteBufs.empty();
		buf.writeBoolean(pkt.correct);
		buf.writeBoolean(pkt.reset);
		return buf;
	}

	public static void register() {
		ClientPlayNetworking.registerGlobalReceiver(ID, (client, handler, buf, responseSender) -> {
			client.execute(() -> {
				TryPinResultPacket pkt = decode(buf);
				AbstractContainerMenu container = Minecraft.getInstance().player.containerMenu;
				if(container.getType() == LocksContainerTypes.LOCK_PICKING)
					((LockPickingContainer) container).handlePin(pkt.correct, pkt.reset);
			});
		});
	}

}