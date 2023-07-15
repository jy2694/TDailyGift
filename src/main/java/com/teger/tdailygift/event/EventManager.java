package com.teger.tdailygift.event;

import com.teger.tdailygift.MessageType;
import com.teger.tdailygift.TDailyGift;
import com.teger.tdailygift.configuration.PlayerData;
import com.teger.tdailygift.inventory.DailyGiftInventory;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Date;

public class EventManager implements Listener {

    private final TDailyGift plugin;

    private static final int RECEIVE_SLOT = 4;

    public EventManager(TDailyGift instance){
        plugin = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if(plugin.getWelcomeMessage() == null) return;
        Player player = e.getPlayer();
        PlayerData playerData = TDailyGift.getPlayerDataManager().getPlayerDataMap().get(player.getUniqueId());
        if(playerData == null || playerData.todayCanReceive()){
            player.sendMessage(plugin.getWelcomeMessage());
        }
    }

    @EventHandler
    public void onDailyGiftInventoryClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        PlayerData data = TDailyGift.getPlayerDataManager().getPlayerDataMap().get(player.getUniqueId());
        if(data == null) return;
        if(data.getNowOpen() == null) return;
        if(data.getNowOpen().equalsIgnoreCase("daily")){
            int slot = e.getRawSlot();
            e.setCancelled(true);
            tryReceiveGift(player, data, slot);
        } else if(data.getNowOpen().equalsIgnoreCase("dailyedit")){
            int slot = e.getRawSlot();
            if(isBorder(slot)){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        Player player = (Player) e.getPlayer();
        PlayerData data = TDailyGift.getPlayerDataManager().getPlayerDataMap().get(player.getUniqueId());
        if(data == null) return;
        if(data.getNowOpen() == null) return;
        if(data.getNowOpen().equalsIgnoreCase("dailyedit")){
            for(int i = 1; i <= 28; i ++){
                int slot = mapToSlot(i);
                ItemStack item = e.getInventory().getItem(slot);
                if(item == null) TDailyGift.getGiftDataManager().getGifts().remove(i);
                else TDailyGift.getGiftDataManager().getGifts().put(i, item);
            }
        }
        data.setNowOpen(null);
    }

    private boolean isBorder(int slot){
        if((slot >= 0 && slot <= 8)
                || (slot >= 45 && slot <= 53)) return true;
        for(int i = 9; i < 54; i += 9) if(i == slot) return true;
        for(int i = 17; i < 54 ; i+= 9) if(i == slot) return true;
        return false;
    }

    private int mapToSlot(int date){
        return ((date - 1) / 7) * 9 + ((date - 1) % 7) + 10;
    }

    private boolean canStackable(PlayerInventory inv, ItemStack item){
        int count = 0;
        for(int i = 0; i < 36; i ++){
            ItemStack inventoryItem = inv.getItem(i);
            if(inventoryItem == null || inventoryItem.getType().equals(Material.AIR)){
                count += item.getMaxStackSize();
                continue;
            }
            if(inventoryItem.isSimilar(item)){
                count += (item.getMaxStackSize() - item.getAmount());
            }
        }
        return count >= item.getAmount();
    }

    private void tryReceiveGift(Player player, PlayerData data, int slot){
        if(slot == RECEIVE_SLOT){
            if(!data.todayCanReceive()){
                player.sendMessage(TDailyGift.messages.get(MessageType.ALREADY_GET_TODAY));
                return;
            }
            int enableSlot = mapToSlot(data.getCount() + 1);
            if(isBorder(enableSlot)) {
                player.sendMessage(TDailyGift.messages.get(MessageType.ALREADY_GET_MONTH));
                return;
            }
            if(receiveGift(player, data.getCount()+1)){
                data.setCount(data.getCount()+1);
                data.setLastReceived(new Date());
                player.closeInventory();
                player.openInventory(DailyGiftInventory.getInventory(player));
                data.setNowOpen("daily");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            } else {
                player.sendMessage(TDailyGift.messages.get(MessageType.INVENTORY_FULL));
            }
        } else if(!isBorder(slot)) {
            if(!data.todayCanReceive()){
                player.sendMessage(TDailyGift.messages.get(MessageType.ALREADY_GET_TODAY));
                return;
            }
            int enableSlot = mapToSlot(data.getCount() + 1);
            if(isBorder(enableSlot)) {
                player.sendMessage(TDailyGift.messages.get(MessageType.ALREADY_GET_MONTH));
                return;
            }
            if(slot == enableSlot){
                if(receiveGift(player, data.getCount()+1)){
                    data.setCount(data.getCount()+1);
                    data.setLastReceived(new Date());
                    player.closeInventory();
                    player.openInventory(DailyGiftInventory.getInventory(player));
                    data.setNowOpen("daily");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                } else {
                    player.sendMessage(TDailyGift.messages.get(MessageType.INVENTORY_FULL));
                }
            }
        }
    }

    private boolean receiveGift(Player player, int date){
        ItemStack item = TDailyGift.getGiftDataManager().getGifts().get(date);
        if(item == null) return true;
        if(!canStackable(player.getInventory(), item)) return false;
        player.getInventory().addItem(item);
        return true;
    }
}