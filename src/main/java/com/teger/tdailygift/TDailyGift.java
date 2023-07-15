package com.teger.tdailygift;

import com.teger.tdailygift.command.CommandManager;
import com.teger.tdailygift.configuration.GiftDataManager;
import com.teger.tdailygift.configuration.PlayerDataManager;
import com.teger.tdailygift.event.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;
import java.util.logging.Logger;

public final class TDailyGift extends JavaPlugin {

    private static final Logger logger = Bukkit.getLogger();
    private String welcomeMessage = null;
    private static PlayerDataManager playerDataManager;
    private static GiftDataManager giftDataManager;

    @Override
    public void onEnable() {

        /* Command & Event Setting */

        registerEvents();
        registerCommands();

        /* Configuration Load */

        loadSettingConfiguration();
        try {
            playerDataManager = new PlayerDataManager(this);
            giftDataManager = new GiftDataManager(this);
            playerDataManager.loadPlayerData();
            giftDataManager.loadGiftData();
        } catch (IOException | ParseException e) {
            logger.warning("An error occurred loading the configuration file.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        /* Configuration Save */
        try {
            playerDataManager.savePlayerData();
            giftDataManager.saveGiftData();
        } catch (IOException e) {
            logger.warning("The configuration file has not been saved.");
        }
    }

    private void registerEvents(){
        EventManager eventManager = new EventManager(this);
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

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public static PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public static GiftDataManager getGiftDataManager() {
        return giftDataManager;
    }
}
