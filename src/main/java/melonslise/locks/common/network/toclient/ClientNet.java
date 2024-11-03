package melonslise.locks.common.network.toclient;

import melonslise.locks.common.container.LockPickingContainer;
import melonslise.locks.common.init.LocksContainerTypes;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class ClientNet {

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(AddLockablePacket.ID, (client, handler, buf, responseSender) -> {
            client.execute(() -> {

                AddLockablePacket packet = AddLockablePacket.decode(buf);
                AddLockablePacket.execute(packet, client.level);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(AddLockableToChunkPacket.ID, (client, phandler, buf, responseSender) -> {
            client.execute(() -> {
                AddLockableToChunkPacket pkt = AddLockableToChunkPacket.decode(buf);
                if(client.level==null){
                    return;
                }
                AddLockableToChunkPacket.execute(pkt, client.level);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(RemoveLockablePacket.ID, (client, handler, buf, responseSender) -> {
            client.execute(() -> {
                RemoveLockablePacket packet = RemoveLockablePacket.decode(buf);
                if(client.level==null){
                    return;
                }
                RemoveLockablePacket.execute(packet, client.level);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(TryPinResultPacket.ID, (client, handler, buf, responseSender) -> {
            client.execute(() -> {
                TryPinResultPacket pkt = TryPinResultPacket.decode(buf);
                AbstractContainerMenu container = client.player.containerMenu;
                if(container.getType() == LocksContainerTypes.LOCK_PICKING)
                    ((LockPickingContainer) container).handlePin(pkt.correct, pkt.reset);
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(UpdateLockablePacket.ID, (client, handler, buf, responseSender) -> {
            client.execute(() -> {
                UpdateLockablePacket pkt = UpdateLockablePacket.decode(buf);
                if(client.level==null){
                    return;
                }
                UpdateLockablePacket.execute(pkt, client.level);
            });
        });
    }
}
