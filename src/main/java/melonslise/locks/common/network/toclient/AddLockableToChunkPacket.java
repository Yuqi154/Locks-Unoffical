package melonslise.locks.common.network.toclient;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import melonslise.locks.Locks;
import melonslise.locks.common.components.interfaces.ILockableHandler;
import melonslise.locks.common.components.interfaces.ILockableStorage;
import melonslise.locks.common.init.LocksComponents;
import melonslise.locks.common.util.Lockable;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public class AddLockableToChunkPacket {
    public static final ResourceLocation ID = new ResourceLocation(Locks.ID, "add_lockable2chunk");
    private final Lockable lockable;
    private final int x, z;

    public AddLockableToChunkPacket(Lockable lkb, int x, int z) {
        this.lockable = lkb;
        this.x = x;
        this.z = z;
    }

    public AddLockableToChunkPacket(Lockable lkb, ChunkPos pos) {
        this(lkb, pos.x, pos.z);
    }

    public AddLockableToChunkPacket(Lockable lkb, LevelChunk ch) {
        this(lkb, ch.getPos());
    }

    public static AddLockableToChunkPacket decode(FriendlyByteBuf buf) {
        return new AddLockableToChunkPacket(Lockable.fromBuf(buf), buf.readInt(), buf.readInt());
    }

    public static FriendlyByteBuf encode(AddLockableToChunkPacket pkt) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        Lockable.toBuf(buf, pkt.lockable);
        buf.writeInt(pkt.x);
        buf.writeInt(pkt.z);
        return buf;
    }


    public static void execute(AddLockableToChunkPacket pkt, Level level){

        ILockableStorage st = LocksComponents.LOCKABLE_STORAGE.get(level.getChunk(pkt.x, pkt.z));
        ILockableHandler handler = LocksComponents.LOCKABLE_HANDLER.get(level);
        Int2ObjectMap<Lockable> lkbs = handler.getLoaded();
        Lockable lkb = lkbs.get(pkt.lockable.id);
        if (lkb == lkbs.defaultReturnValue()) {
            lkb = pkt.lockable;
            lkb.addObserver(handler);
            lkbs.put(lkb.id, lkb);
        }
        st.add(lkb);
    }
}