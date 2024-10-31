package melonslise.locks.common.network.toclient;

import melonslise.locks.Locks;
import melonslise.locks.common.init.LocksComponents;
import melonslise.locks.common.util.Lockable;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

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
        FriendlyByteBuf buf = PacketByteBufs.create();
        Lockable.toBuf(buf, pkt.lockable);
        Locks.LOGGER.info(buf.copy().toString());
        return buf;
    }

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(ID, (client, handler, buf, responseSender) -> {
            client.execute(() -> {
                AddLockablePacket packet = decode(buf);
                execute(packet, client.level);
            });
        });
    }
    public static void execute(AddLockablePacket pkt, Level level){
        LocksComponents.LOCKABLE_HANDLER.get(level).add(pkt.lockable);
    }

}