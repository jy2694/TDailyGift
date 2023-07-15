package com.teger.tdailygift.inventory;

import com.teger.tdailygift.MessageType;
import com.teger.tdailygift.TDailyGift;
import com.teger.tdailygift.configuration.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DailyGiftInventory {

    public static Inventory getInventory(Player player){
        Inventory inv = Bukkit.createInventory(null, 9*6, "[Daily Gift]");

        setBorder(inv);
        setGifts(inv, player);
        setReceivedGifts(inv, player);

        return inv;
    }

    private static void setBorder(Inventory inv){
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName(ChatColor.WHITE+" ");
        item.setItemMeta(meta);
        for(int i = 0; i < 9; i ++) inv.setItem(i, item);
        for(int i = 9; i < 54; i += 9) inv.setItem(i, item);
        for(int i = 17; i < 54; i += 9) inv.setItem(i, item);
        for(int i = 46; i < 53; i ++) inv.setItem(i, item);

        ItemStack receive = new ItemStack(Material.BELL);
        ItemMeta receiveMeta = receive.getItemMeta();
        Objects.requireNonNull(receiveMeta).setDisplayName(ChatColor.GREEN + (ChatColor.BOLD + "Get Daily Gift"));
        receive.setItemMeta(receiveMeta);

        inv.setItem(4, receive);
    }

    private static void setGifts(Inventory inv, Player player){
        PlayerData data = TDailyGift.getPlayerDataManager().getPlayerDataMap().get(player.getUniqueId());
        int count = data == null ? 0 : data.getCount();
        for(int i = 1; i <= 28; i ++){
            int slot = mapToSlot(i);
            ItemStack item = TDailyGift.getGiftDataManager().getGifts().get(i);
            if(item == null) {
                item = new ItemStack(Material.BOOK);
                ItemMeta noneMeta = item.getItemMeta();
                Objects.requireNonNull(noneMeta).setDisplayName(TDailyGift.messages.get(MessageType.NO_DAILY_GIFT));
                item.setItemMeta(noneMeta);
            }
            else item = item.clone();
            ItemMeta meta = item.getItemMeta();
            List<String> lore = Objects.requireNonNull(meta).getLore();
            if(lore == null) {
                lore = new ArrayList<>();
            }
            lore.add(TDailyGift.messages.get(MessageType.DATE_DISPLAY_ITEM_SEPARATOR));
            if (i <= count) {
                lore.add(TDailyGift.messages.get(MessageType.RECEIVED_DISPLAY_ITEM));
            } else {
                lore.add(String.format(TDailyGift.messages.get(MessageType.DATE_DISPLAY_ITEM), i));
            }
            meta.setLore(lore);
            meta.addItemFlags(ItemFlag.values());
            item.setItemMeta(meta);
            inv.setItem(slot, item);
        }
    }

    private static void setReceivedGifts(Inventory inv, Player player){
        PlayerData data = TDailyGift.getPlayerDataManager().getPlayerDataMap().get(player.getUniqueId());
        int count = data == null ? 0 : data.getCount();
        for(int i = 1; i <= count; i ++){
            int slot = mapToSlot(i);
            ItemStack item = inv.getItem(slot);
            Objects.requireNonNull(item).addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        }
    }

    private static int mapToSlot(int date){
        return ((date - 1) / 7) * 9 + ((date - 1) % 7) + 10;
    }

//    private int mapToDate(int slot){
//        return (((slot - 10) / 9) * 7) + ((slot - 10) % 9) + 1;
//    }
}
