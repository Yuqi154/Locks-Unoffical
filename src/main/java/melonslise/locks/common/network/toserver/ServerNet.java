package melonslise.locks.common.network.toserver;

import melonslise.locks.common.container.LockPickingContainer;
import melonslise.locks.common.init.LocksContainerTypes;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class ServerNet {
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(TryPinPacket.ID,(server, player, Impl, buf, sender)->{
            server.execute(()->{
                TryPinPacket pkt = TryPinPacket.decode(buf);
                AbstractContainerMenu container = player.containerMenu;
                if(container.getType() == LocksContainerTypes.LOCK_PICKING)
                    ((LockPickingContainer) container).tryPin(pkt.pin);
            });
        });

    }
}
