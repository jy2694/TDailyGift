package com.teger.tdailygift.command;

import com.teger.tdailygift.inventory.DailyGiftEditInventory;
import com.teger.tdailygift.inventory.DailyGiftInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("daily")){
            return dailyCommand(sender);
        }
        else if(command.getName().equalsIgnoreCase("dailyEdit")){
            return dailyEditCommand(sender);
        }
        return false;
    }

    private boolean dailyCommand(CommandSender sender){
        if(isNotPlayer(sender)) return false;
        Player player = (Player) sender;
        player.openInventory(DailyGiftInventory.getInventory(player));
        return false;
    }

    private boolean dailyEditCommand(CommandSender sender){
        if(isNotPlayer(sender)) return false;
        Player player = (Player) sender;
        player.openInventory(DailyGiftEditInventory.getInventory(player));
        return false;
    }

    private boolean isNotPlayer(CommandSender sender){
        if(!(sender instanceof Player)){
            sender.sendMessage("This command can only be executed by the player.");
            return true;
        }
        return false;
    }
}
