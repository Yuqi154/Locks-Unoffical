package melonslise.locks.common.init;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class LocksVillagerTrades {

    public static void addVillagerTrades(VillagerTradesEvent e)
    {
        if(e.getType() != VillagerProfession.TOOLSMITH)
            return;
        Int2ObjectMap<List<VillagerTrades.ItemListing>> levels = e.getTrades();
        List<VillagerTrades.ItemListing> trades;
        trades = levels.get(1);
        trades.add(new VillagerTrades.ItemsForEmeralds(new ItemStack(LocksItems.WOOD_LOCK_PICK), 1, 2, 16, 2, 0.05f));
        trades.add(new VillagerTrades.ItemsForEmeralds(new ItemStack(LocksItems.WOOD_LOCK_MECHANISM), 2, 1, 12, 1, 0.2f));
        trades = levels.get(2);
        trades.add(new VillagerTrades.ItemsForEmeralds(new ItemStack(LocksItems.IRON_LOCK_PICK), 2, 2, 16, 5, 0.05f));
        trades = levels.get(3);
        trades.add(new VillagerTrades.ItemsForEmeralds(new ItemStack(LocksItems.GOLD_LOCK_PICK), 6, 2, 12, 20, 0.05f));
        trades.add(new VillagerTrades.ItemsForEmeralds(new ItemStack(LocksItems.IRON_LOCK_MECHANISM), 5, 1, 8, 10, 0.2f));
        trades = levels.get(4);
        trades.add(new VillagerTrades.ItemsForEmeralds(new ItemStack(LocksItems.STEEL_LOCK_PICK), 4, 2, 16, 20, 0.05f));
        trades = levels.get(5);
        trades.add(new VillagerTrades.ItemsForEmeralds(new ItemStack(LocksItems.DIAMOND_LOCK_PICK), 8, 2, 12, 30, 0.05f));
        trades.add(new VillagerTrades.ItemsForEmeralds(new ItemStack(LocksItems.STEEL_LOCK_MECHANISM), 8, 1, 8, 30, 0.2f));
    }

    public static void addWandererTrades(WandererTradesEvent e)
    {
        List<VillagerTrades.ItemListing> trades;
        trades = e.getGenericTrades();
        trades.add(new VillagerTrades.ItemsForEmeralds(LocksItems.GOLD_LOCK_PICK, 5, 2, 6, 1));
        trades.add(new VillagerTrades.ItemsForEmeralds(LocksItems.STEEL_LOCK_PICK, 3, 2, 8, 1));
        trades.add(new VillagerTrades.EnchantedItemForEmeralds(LocksItems.STEEL_LOCK, 16, 4, 1));
        trades = e.getRareTrades();
        trades.add(new VillagerTrades.ItemsForEmeralds(LocksItems.STEEL_LOCK_MECHANISM, 6, 1, 4, 1));
        trades.add(new VillagerTrades.EnchantedItemForEmeralds(LocksItems.DIAMOND_LOCK, 28, 4, 1));
    }
}
