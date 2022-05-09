package mcholic.tp.mcholic.xyz.Tp.data;

import mcholic.tp.mcholic.xyz.Tp.mcholic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ConfigManager {


    public FileConfiguration config;
    public File file;
    private String name;

    public ConfigManager(String name){
        this.name = name;

    }
    public void reloadConfig(){
        if(this.config == null)
            this.file = new File(mcholic.plugin.getDataFolder(), name + ".yml");
        this.config = YamlConfiguration.loadConfiguration(this.file);

        InputStream inputStream = mcholic.plugin.getResource(name + ".yml");
        if(inputStream != null) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream));
            this.config.setDefaults(config);
        }
    }

    public FileConfiguration getConfig() {
        if(this.config == null) reloadConfig();
        return config;
    }

    public boolean saveConfig(){
        if(this.config == null || this.file == null) return false;
        try {
            getConfig().save(this.file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void saveDefualtConfig(){
        if(this.file == null)
            this.file = new File(mcholic.plugin.getDataFolder(), name + ".yml");
        if(!this.file.exists()){
            file = new File(mcholic.plugin.getDataFolder(), name + ".yml");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean Delete(){
        this.file = new File(mcholic.plugin.getDataFolder(), name + ".yml");

        return this.file.delete();
    }

    public Object get(String path){
        return this.config.get(path);
    }

    public void removeObject(String path, Object obj ){
        List<Object> list = (List<Object>) this.getConfig().getList(path);
        list.remove(obj);
        this.saveConfig();
    }

    public void addObject(String path, Object obj ){
        List<Object> list = (List<Object>) this.getConfig().getList(path);
        list.add(obj);
        this.saveConfig();
    }
}
