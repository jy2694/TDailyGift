package com.teger.tdailygift.command;

import com.teger.tdailygift.TDailyGift;
import com.teger.tdailygift.configuration.PlayerData;
import com.teger.tdailygift.inventory.DailyGiftEditInventory;
import com.teger.tdailygift.inventory.DailyGiftInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandManager implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("daily")){
            dailyCommand(sender);
            return false;
        }
        else if(command.getName().equalsIgnoreCase("dailyEdit")){
            dailyEditCommand(sender);
            return false;
        }
        return false;
    }

    private void dailyCommand(CommandSender sender){
        if(isNotPlayer(sender)) return;
        Player player = (Player) sender;
        Inventory inv = DailyGiftInventory.getInventory(player);
        player.openInventory(inv);
        PlayerData data = TDailyGift.getPlayerDataManager().getPlayerDataMap().get(player.getUniqueId());
        if(data == null){
            data = PlayerData.emptyBuilder(player.getUniqueId()).build();
            TDailyGift.getPlayerDataManager().getPlayerDataMap().put(player.getUniqueId(), data);
        }
        data.setNowOpen("daily");
    }

    private void dailyEditCommand(CommandSender sender){
        if(isNotPlayer(sender)) return;
        Player player = (Player) sender;
        Inventory inv = DailyGiftEditInventory.getInventory();
        player.openInventory(inv);
        PlayerData data = TDailyGift.getPlayerDataManager().getPlayerDataMap().get(player.getUniqueId());
        if(data == null){
            data = PlayerData.emptyBuilder(player.getUniqueId()).build();
            TDailyGift.getPlayerDataManager().getPlayerDataMap().put(player.getUniqueId(), data);
        }
        data.setNowOpen("dailyedit");
    }

    private boolean isNotPlayer(CommandSender sender){
        if(!(sender instanceof Player)){
            sender.sendMessage("This command can only be executed by the player.");
            return true;
        }
        return false;
    }
}
