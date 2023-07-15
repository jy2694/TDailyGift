package com.teger.tdailygift.configuration;

import com.teger.tdailygift.TDailyGift;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {

    private final File file;
    private FileConfiguration config;
    private final Map<UUID, PlayerData> playerDataMap = new HashMap<>();

    public PlayerDataManager(TDailyGift plugin) throws IOException {
        this.file = new File(String.format("plugins/%s/player.yml", plugin.getDescription().getName()));
        if(!file.exists()) {
            file.createNewFile();
        }
    }

    public void loadPlayerData() throws ParseException {
        config = YamlConfiguration.loadConfiguration(this.file);
        playerDataMap.clear();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for(String key : config.getKeys(false)){
            UUID uuid = UUID.fromString(key);
            Date date = format.parse(config.getString(key + ".lastReceived"));
            int count = config.getInt(key + ".count");
            PlayerData playerData = PlayerData.builder()
                    .uniqueId(uuid)
                    .lastReceived(date)
                    .count(count)
                    .build();
            playerDataMap.put(uuid, playerData);
        }
    }

    public void savePlayerData() throws IOException {
        if (config == null) return;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (UUID uuid : playerDataMap.keySet()) {
            PlayerData data = playerDataMap.get(uuid);
            String key = uuid.toString();
            String lastReceived = format.format(data.getLastReceived());
            int count = data.getCount();
            config.set(key + ".lastReceived", lastReceived);
            config.set(key + ".count", count);
        }
        config.save(file);
    }

    public Map<UUID, PlayerData> getPlayerDataMap() {
        return playerDataMap;
    }
}
