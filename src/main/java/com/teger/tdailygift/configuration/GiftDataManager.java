package com.teger.tdailygift.configuration;

import com.teger.tdailygift.TDailyGift;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GiftDataManager {

    private final File file;
    private FileConfiguration config;
    private final Map<Integer, ItemStack> gifts = new HashMap<>();

    public GiftDataManager(TDailyGift plugin) throws IOException {
        file = new File(String.format("plugins/%s/gifts.yml", plugin.getDescription().getName()));
        if(!file.exists()){
            file.createNewFile();
        }
    }

    public void loadGiftData(){
        config = YamlConfiguration.loadConfiguration(file);
        gifts.clear();
        for(int i = 1; i <= 28; i ++){
            ItemStack item = config.getItemStack(Integer.toString(i));
            gifts.put(i, item);
        }
    }

    public void saveGiftData() throws IOException {
        if(config == null) return;
        for(int i = 1; i <= 28; i ++){
            ItemStack item = gifts.get(i);
            config.set(Integer.toString(i), item);
        }
        config.save(file);
    }

    public Map<Integer, ItemStack> getGifts() {
        return gifts;
    }

    public ItemStack[] getGiftsToArray(){
        ItemStack[] items = new ItemStack[29];
        for(int i = 1; i <= 28; i ++){
            ItemStack item = gifts.get(i);
            items[i] = item;
        }
        return items;
    }
}
