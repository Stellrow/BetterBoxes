package ro.Stellrow.BetterBoxes;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import ro.Stellrow.BetterBoxes.boxeshandling.BoxesManager;
import ro.Stellrow.BetterBoxes.commands.CommandManager;
import ro.Stellrow.BetterBoxes.eventshandling.BoxEvents;
import ro.Stellrow.BetterBoxes.inventorymanaging.InventoryHandler;
import ro.Stellrow.BetterBoxes.itemmanaging.ItemBuilder;

public class BetterBoxes extends JavaPlugin
{
    public NamespacedKey boxKey = new NamespacedKey(this,"boxKey");
    private BoxesManager boxesManager = new BoxesManager(this);
    private CommandManager commandManager = new CommandManager(this);
    private InventoryHandler inventoryHandler = new InventoryHandler(this);
    private ItemBuilder itemBuilder = new ItemBuilder(this,boxKey);


    public void onEnable(){
        loadConfig();
        boxesManager.init();
        commandManager.registerCommands();
        getServer().getPluginManager().registerEvents(inventoryHandler,this);
        getServer().getPluginManager().registerEvents(new BoxEvents(this),this);
    }

    private void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
    public BoxesManager getBoxesManager() {
        return boxesManager;
    }

    public InventoryHandler getInventoryHandler() {
        return inventoryHandler;
    }

    public ItemBuilder getItemBuilder() {
        return itemBuilder;
    }
}
