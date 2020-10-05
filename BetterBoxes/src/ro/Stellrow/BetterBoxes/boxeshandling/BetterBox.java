package ro.Stellrow.BetterBoxes.boxeshandling;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import ro.Stellrow.BetterBoxes.utils.CrateConfig;

import java.util.HashMap;

public class BetterBox {
    private HashMap<Integer, ItemStack> items = new HashMap<>();
    private final CrateConfig crateConfig;

    public BetterBox(CrateConfig crateConfig) {
        this.crateConfig = crateConfig;
    }


    public HashMap<Integer, ItemStack> getItems() {
        return items;
    }

    public void setItems(HashMap<Integer, ItemStack> items) {
        this.items = items;
    }

    public CrateConfig getCrateConfig() {
        return crateConfig;
    }
    public void loadItems(){
        FileConfiguration config = getCrateConfig().getConfig();
        if(config.contains("Items")){
            for(String slot : config.getConfigurationSection("Items").getKeys(false)){
                try{
                    items.put(Integer.parseInt(slot),config.getItemStack("Items."+slot));
                }catch (IllegalArgumentException ex){

                }
            }
        }
    }
    public void saveItems(){
        FileConfiguration config = getCrateConfig().getConfig();
        config.set("Items",null);
        for(Integer i : items.keySet()){
            config.set("Items."+i,items.get(i));
        }
        getCrateConfig().save();
    }
}
