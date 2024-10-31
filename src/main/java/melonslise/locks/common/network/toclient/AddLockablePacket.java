package melonslise.locks.common.network.toclient;

import melonslise.locks.Locks;
import melonslise.locks.common.init.LocksComponents;
import melonslise.locks.common.util.Lockable;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class AddLockablePacket {
    public static final ResourceLocation ID = new ResourceLocation(Locks.ID, "add_lockable");

    private final Lockable lockable;

    public AddLockablePacket(Lockable lkb) {
        this.lockable = lkb;
    }

    public static AddLockablePacket decode(FriendlyByteBuf buf) {
        return new AddLockablePacket(Lockable.fromBuf(buf));
    }

    public static FriendlyByteBuf encode(AddLockablePacket pkt) {
        FriendlyByteBuf empty = PacketByteBufs.empty();
        Lockable.toBuf(empty, pkt.lockable);
        return empty;
    }

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(ID, (client, handler, buf, responseSender) -> {
            client.execute(() -> {
                AddLockablePacket packet = decode(buf);
                LocksComponents.LOCKABLE_HANDLER.get(client.level).add(packet.lockable);
            });
        });
    }

}