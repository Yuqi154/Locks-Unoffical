package melonslise.locks.common.util;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public interface IContainerFactory<T extends AbstractContainerMenu> extends MenuType.MenuSupplier<T> {
    T create(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf);

    default T create(int i, Inventory inventory){
        return this.create(i, inventory, null);
    }
}
