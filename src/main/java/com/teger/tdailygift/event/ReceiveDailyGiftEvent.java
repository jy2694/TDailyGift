package com.teger.tdailygift.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class ReceiveDailyGiftEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private final Player player;
    private final int date;
    private final ItemStack item;

    public ReceiveDailyGiftEvent(Player player, int date, ItemStack item){
        this.player = player;
        this.date = date;
        this.item = item;
    }

    public Player getPlayer() {
        return player;
    }

    public int getDate() {
        return date;
    }

    public ItemStack getItem() {
        return item;
    }

    public static HandlerList getHandlerList(){
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
