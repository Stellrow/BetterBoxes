package ro.Stellrow.BetterBoxes.itemmanaging;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import ro.Stellrow.BetterBoxes.BetterBoxes;
import ro.Stellrow.BetterBoxes.boxeshandling.BetterBox;
import ro.Stellrow.BetterBoxes.utils.Utils;

import javax.annotation.Nullable;
import java.util.Arrays;


public class ItemBuilder {
    private final BetterBoxes pl;
    private final NamespacedKey boxKey;

    public ItemBuilder(BetterBoxes pl, NamespacedKey boxKey) {
        this.pl = pl;
        this.boxKey = boxKey;
    }

    public ItemStack getItemForBox(String boxName, @Nullable int amount){
        BetterBox box = pl.getBoxesManager().getBox(boxName);
        ItemStack i = new ItemStack(Material.ENDER_CHEST);
        if(amount>0){
            i.setAmount(amount);
        }
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Utils.asColor(box.getCrateConfig().getConfig().getString("BoxConfig.name","Default Missing Box Name")));
        if(box.getCrateConfig().getConfig().contains("BoxConfig.lore")) {
            im.setLore(Utils.loreAsColor(box.getCrateConfig().getConfig().getStringList("BoxConfig.lore")));
        }else{
            im.setLore(Arrays.asList(Utils.asColor("&cDefault Box Lore-This box is missing a lore!")));
        }
        im.getPersistentDataContainer().set(boxKey, PersistentDataType.STRING,boxName);
        i.setItemMeta(im);
        return i;
    }

}
