package melonslise.locks.common.network.toclient;

import melonslise.locks.Locks;
import melonslise.locks.common.init.LocksComponents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class RemoveLockablePacket {
    public static final ResourceLocation ID = new ResourceLocation(Locks.ID, "remove_lockable");
    private final int id;

    public RemoveLockablePacket(int id) {
        this.id = id;
    }

    public static RemoveLockablePacket decode(FriendlyByteBuf buf) {
        return new RemoveLockablePacket(buf.readInt());
    }

    public static FriendlyByteBuf encode(RemoveLockablePacket pkt) {
        FriendlyByteBuf empty = PacketByteBufs.create();
        empty.writeInt(pkt.id);
        return empty;
    }


    public static void execute(RemoveLockablePacket pkt, Level level){
        LocksComponents.LOCKABLE_HANDLER.get(level).remove(pkt.id);
    }

}