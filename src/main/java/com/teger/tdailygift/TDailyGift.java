package com.teger.tdailygift;

import com.teger.tdailygift.command.CommandManager;
import com.teger.tdailygift.event.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

public final class TDailyGift extends JavaPlugin {

//    private static Logger logger = Bukkit.getLogger();
    private static String welcomeMessage = null;
    private static PlayerDataManager playerDataManager;

    @Override
    public void onEnable() {

        /* Command & Event Setting */

        registerEvents();
        registerCommands();

        /* Configuration Load */

        loadSettingConfiguration();
        try {
            playerDataManager = new PlayerDataManager(this);
            playerDataManager.loadPlayerData();
        } catch (IOException | ParseException e) {
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        /* Configuration Save */
        try {
            playerDataManager.savePlayerData();
        } catch (IOException e) {
            //Exception
        }
    }

    private void registerEvents(){
        EventManager eventManager = new EventManager();
        Bukkit.getPluginManager().registerEvents(eventManager, this);
    }

    private void registerCommands(){
        CommandManager commandManager = new CommandManager();
        Objects.requireNonNull(getCommand("daily")).setExecutor(commandManager);
        Objects.requireNonNull(getCommand("dailyedit")).setExecutor(commandManager);
    }

    private void loadSettingConfiguration(){
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        if(config.getBoolean("welcome-message.enable")){
            welcomeMessage = config.getString("welcome-message.message");
        }
    }

    public static String getWelcomeMessage() {
        return welcomeMessage;
    }
}
