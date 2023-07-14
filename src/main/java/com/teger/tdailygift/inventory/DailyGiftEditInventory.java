package com.teger.tdailygift.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class DailyGiftEditInventory {

    public static Inventory getInventory(Player player){
        Inventory inv = Bukkit.createInventory(null, 9*6, "[Daily Gift]");

        return inv;
    }
}
