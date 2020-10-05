package ro.Stellrow.BetterBoxes.eventshandling;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import ro.Stellrow.BetterBoxes.BetterBoxes;

public class BoxEvents implements Listener {
    private final BetterBoxes pl;
    public BoxEvents(BetterBoxes pl) {
        this.pl = pl;
    }


    @EventHandler
    public void onUse(PlayerInteractEvent event){
        if(event.getItem()!=null){
            if(event.getAction()== Action.RIGHT_CLICK_BLOCK||event.getAction()==Action.RIGHT_CLICK_AIR){
                ItemStack used = event.getItem();
                if(used.hasItemMeta()&&used.getItemMeta().getPersistentDataContainer().has(pl.boxKey, PersistentDataType.STRING)){
                    String boxName = used.getItemMeta().getPersistentDataContainer().get(pl.boxKey,PersistentDataType.STRING);
                    event.getItem().setAmount(event.getItem().getAmount()-1);
                    pl.getBoxesManager().giveItems(event.getPlayer(),boxName);
                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ITEM_BREAK,1f,1f);
                }
            }
        }
    }
}
