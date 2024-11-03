package melonslise.locks.common.network.toclient;

import melonslise.locks.Locks;
import melonslise.locks.common.init.LocksComponents;
import melonslise.locks.common.util.Lockable;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class UpdateLockablePacket {
    public static final ResourceLocation ID = new ResourceLocation(Locks.ID, "update_lockable");
    private final int id;
    // Expandable
    private final boolean locked;

    public UpdateLockablePacket(int id, boolean locked) {
        this.id = id;
        this.locked = locked;
    }

    public UpdateLockablePacket(Lockable lkb) {
        this(lkb.id, lkb.lock.isLocked());
    }

    public static UpdateLockablePacket decode(FriendlyByteBuf buf) {
        return new UpdateLockablePacket(buf.readInt(), buf.readBoolean());
    }

    public static FriendlyByteBuf encode(UpdateLockablePacket pkt) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeInt(pkt.id);
        buf.writeBoolean(pkt.locked);
        return buf;
    }


    public static void execute(UpdateLockablePacket pkt, Level level){
        LocksComponents.LOCKABLE_HANDLER.get(level).getLoaded().get(pkt.id).lock.setLocked(pkt.locked);
    }

}