package ro.Stellrow.BetterBoxes.inventorymanaging;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ro.Stellrow.BetterBoxes.BetterBoxes;
import ro.Stellrow.BetterBoxes.boxeshandling.BetterBox;
import ro.Stellrow.BetterBoxes.utils.Utils;

import java.util.HashMap;

public class InventoryHandler implements Listener {
    private final BetterBoxes pl;
    public InventoryHandler(BetterBoxes pl) {
        this.pl = pl;
    }

    private HashMap<Inventory,String> activeInventories = new HashMap<>();


    public void editBox(Player whoOpened, String boxName){
        BetterBox box = pl.getBoxesManager().getBox(boxName);
        Inventory inv = Bukkit.createInventory(null,54,boxName);
        for(Integer i : box.getItems().keySet()){
            inv.setItem(i,box.getItems().get(i));
        }
        whoOpened.openInventory(inv);
        activeInventories.put(inv,boxName);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        if(activeInventories.containsKey(event.getInventory())){
            Inventory i = event.getInventory();
            HashMap<Integer, ItemStack> items = new HashMap<>();
            for(int x =0;x<54;x++){
                if(i.getItem(x)!=null){
                    items.put(x,i.getItem(x));
                }
            }
            BetterBox box = pl.getBoxesManager().getBox(activeInventories.get(i));
            box.setItems(items);
            box.saveItems();
            event.getPlayer().sendMessage(Utils.asColor(pl.getConfig().getString("Messages.prefix"))+Utils.asColor(pl.getConfig().getString("Messages.box-saved")));
        }

    }


}
