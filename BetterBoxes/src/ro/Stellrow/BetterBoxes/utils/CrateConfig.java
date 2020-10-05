package ro.Stellrow.BetterBoxes.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class CrateConfig {
    private File file;
    private FileConfiguration fileConfiguration;

    public CrateConfig(String name, JavaPlugin main){
        File folder = new File(main.getDataFolder(),"boxes");
        if(!folder.exists()){
            try{
                folder.mkdirs();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        file = new File(main.getDataFolder()+"/boxes",name+".yml");
        if(!file.exists()){
            try {
                file.createNewFile();
            }catch (Exception exception){
                exception.printStackTrace();
            }
            fileConfiguration = YamlConfiguration.loadConfiguration(file);
            fileConfiguration.set("BoxConfig.name",name);
            fileConfiguration.set("BoxConfig.lore", Arrays.asList("Default lore"));
            save();
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(file);


    }

    public FileConfiguration getConfig(){
        return fileConfiguration;
    }

    public void save(){
        try {
            fileConfiguration.save(file);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
    public void reload(){
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }
    public void deleteFile(){
        file.delete();
    }

}
