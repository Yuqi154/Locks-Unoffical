package melonslise.locks.common.components.interfaces;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface IItemHandler extends Component, AutoSyncedComponent, Container {
        List<ItemStack> getItems();
        void setItems(List<ItemStack> items);
        int getSlots();
}
