package melonslise.locks.mixin;

import melonslise.locks.common.capability.ILockableHandler;
import melonslise.locks.common.capability.ILockableStorage;
import melonslise.locks.common.init.LocksCapabilities;
import melonslise.locks.common.init.LocksNetwork;
import melonslise.locks.common.network.toclient.AddLockableToChunkPacket;
import melonslise.locks.common.util.ILockableProvider;
import melonslise.locks.common.util.Lockable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelChunk.class)
public class ChunkMixin
{
	@Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/ChunkPos;)V")
	private void init(Level world, ChunkPos pr, CallbackInfo ci)
	{
		LevelChunk ch = (LevelChunk) (Object) this;
		ILockableStorage st = ch.getCapability(LocksCapabilities.LOCKABLE_STORAGE).orElse(null);
		ILockableHandler handler = world.getCapability(LocksCapabilities.LOCKABLE_HANDLER).orElse(null);
		// We trust that all checks pass (such as volume and intersect checks) due to this happening only during world gen
		for(Lockable lkb : ((ILockableProvider) pr).getLockables())
		{
			st.add(lkb);
			handler.getLoaded().put(lkb.id, lkb);
			lkb.addObserver(handler);
			LocksNetwork.MAIN.send(PacketDistributor.TRACKING_CHUNK.with(() -> ch), new AddLockableToChunkPacket(lkb, ch));
		}
	}
}