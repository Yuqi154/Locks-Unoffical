package melonslise.locks.common.components;

import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import melonslise.locks.Locks;
import melonslise.locks.common.components.interfaces.ILockableHandler;
import melonslise.locks.common.components.interfaces.ILockableStorage;
import melonslise.locks.common.config.LocksServerConfig;
import melonslise.locks.common.init.LocksComponents;
import melonslise.locks.common.network.toclient.AddLockablePacket;
import melonslise.locks.common.network.toclient.RemoveLockablePacket;
import melonslise.locks.common.network.toclient.UpdateLockablePacket;
import melonslise.locks.common.util.Lockable;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.List;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;

public class LockableHandler implements ILockableHandler {

    public static final ResourceLocation ID = new ResourceLocation(Locks.ID, "lockable_handler");

    public final Level world;

    public AtomicInteger lastId = new AtomicInteger();

    public Int2ObjectMap<Lockable> lockables = new Int2ObjectLinkedOpenHashMap<Lockable>();

    public LockableHandler(Level world)
    {
        this.world = world;
    }

    public int nextId()
    {
        return this.lastId.incrementAndGet();
    }

    @Override
    public Int2ObjectMap<Lockable> getLoaded()
    {
        return this.lockables;
    }

    @Override
    public Int2ObjectMap<Lockable> getInChunk(BlockPos pos)
    {
        return this.world.hasChunkAt(pos) ? LocksComponents.LOCKABLE_STORAGE.get(this.world.getChunkAt(pos)).get(): null;
    }

    @Override
    public boolean add(Lockable lkb)
    {
        if(lkb.bb.volume() > LocksServerConfig.MAX_LOCKABLE_VOLUME.get())
            return false;
        List<ILockableStorage> sts = lkb.bb.containedChunksTo((x, z) ->
        {
            try {
                LevelChunk levelChunk = this.world.getChunk(x, z);
                ILockableStorage st = LocksComponents.LOCKABLE_STORAGE.get(levelChunk);
                return st.get().values().stream().anyMatch(lkb1 -> lkb1.bb.intersects(lkb.bb)) ? null : st;
            } catch (Exception e){
                Locks.LOGGER.warn("Chunk not gen");
            }
            return null;
        }, true);
        if(sts == null)
            return false;

        // Add to chunk
        for(int a = 0; a < sts.size(); ++a)
            sts.get(a).add(lkb);
        // Add to world
        this.lockables.put(lkb.id, lkb);
        lkb.addObserver(this);
        // Do client/server extras
        if(this.world.isClientSide)
            lkb.swing(10);
        else
        {
            AddLockablePacket.execute(new AddLockablePacket(lkb), this.world);
//            world.getServer().getPlayerList().players.forEach(player -> {
//                ServerPlayNetworking.send(player, AddLockablePacket.ID, AddLockablePacket.encode(new AddLockablePacket(lkb)));
//            });
        }
        return true;
    }

    @Override
    public boolean remove(int id)
    {
        Lockable lkb = this.lockables.get(id);
        if(lkb == this.lockables.defaultReturnValue())
            return false;
        List<LevelChunk> chs = lkb.bb.containedChunksTo((x, z) -> this.world.hasChunk(x, z) ? this.world.getChunk(x, z) : null, true);

        // Remove from chunk
        for(int a = 0; a < chs.size(); ++a)
            LocksComponents.LOCKABLE_STORAGE.get(chs.get(a)).remove(id);
        // Remove from world
        this.lockables.remove(id);
        lkb.deleteObserver(this);
        // Do client/server extras
        if(this.world.isClientSide)
            return true;
//        world.getServer().getPlayerList().players.forEach(player -> {
//            ServerPlayNetworking.send(player, RemoveLockablePacket.ID, RemoveLockablePacket.encode(new RemoveLockablePacket(lkb.id)));
//        });
        RemoveLockablePacket.execute(new RemoveLockablePacket(lkb.id), this.world);
        return true;
    }
    @Override
    public void update(Observable o, Object arg)
    {
        if(this.world.isClientSide || !(o instanceof Lockable))
            return;
        Lockable lockable = (Lockable) o;
        UpdateLockablePacket.execute(new UpdateLockablePacket(lockable), this.world);
//        world.getServer().getPlayerList().players.forEach(player -> {
//            ServerPlayNetworking.send(player, UpdateLockablePacket.ID, UpdateLockablePacket.encode(new UpdateLockablePacket(lockable)));
//        });
    }

    @Override
    public void readFromNbt(CompoundTag compoundTag) {
        this.lastId.set(compoundTag.getInt("last_id"));
    }

    @Override
    public void writeToNbt(CompoundTag compoundTag) {
        compoundTag.putInt("last_id", this.lastId.get());
    }
}
