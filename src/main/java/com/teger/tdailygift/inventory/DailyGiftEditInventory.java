package com.teger.tdailygift.inventory;

import com.teger.tdailygift.TDailyGift;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class DailyGiftEditInventory {

    public static Inventory getInventory(){
        Inventory inv = Bukkit.createInventory(null, 9*6, "[Daily Gift]");

        setBorder(inv);
        setGifts(inv);

        return inv;
    }

    private static void setBorder(Inventory inv){
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName("");
        item.setItemMeta(meta);
        for(int i = 0; i < 9; i ++) inv.setItem(i, item);
        for(int i = 9; i < 54; i += 9) inv.setItem(i, item);
        for(int i = 17; i < 54; i += 9) inv.setItem(i, item);
        for(int i = 46; i < 53; i ++) inv.setItem(i, item);
    }

    private static void setGifts(Inventory inv){
        for(int i = 1; i <= 28; i ++){
            int slot = mapToSlot(i);
            ItemStack item = TDailyGift.getGiftDataManager().getGifts().get(i);
            inv.setItem(slot, item);
        }
    }

    private static int mapToSlot(int date){
        return ((date - 1) / 7) * 9 + ((date - 1) % 7) + 10;
    }
}
