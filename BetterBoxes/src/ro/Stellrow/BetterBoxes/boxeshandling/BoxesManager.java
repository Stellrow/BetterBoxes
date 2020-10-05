package ro.Stellrow.BetterBoxes.boxeshandling;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ro.Stellrow.BetterBoxes.BetterBoxes;
import ro.Stellrow.BetterBoxes.utils.CrateConfig;
import ro.Stellrow.BetterBoxes.utils.Utils;

import java.io.File;
import java.util.HashMap;

public class BoxesManager {
    private final BetterBoxes pl;

    private HashMap<String, BetterBox> activeBoxes = new HashMap<>();

    public BoxesManager(BetterBoxes pl) {
        this.pl = pl;
    }

    public void init() {
        File folder = new File(pl.getDataFolder(), "boxes");
        if (!folder.exists()) {
            try {
                folder.mkdirs();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        for (File f : folder.listFiles()) {
            CrateConfig crateConfig = new CrateConfig(f.getName().split(".yml")[0], pl);
            BetterBox box = new BetterBox(crateConfig);
            box.loadItems();
            activeBoxes.put(f.getName().split(".yml")[0], box);
        }

    }

    public void addBox(String boxName, BetterBox box) {
        if (activeBoxes.containsKey(boxName)) {
            return;
        }
        activeBoxes.put(boxName, box);
    }

    public void removeBox(String boxName) {
        if (activeBoxes.containsKey(boxName)) {
            activeBoxes.get(boxName).getCrateConfig().deleteFile();
            activeBoxes.remove(boxName);
        }
    }

    public BetterBox getBox(String boxName) {
        if (activeBoxes.containsKey(boxName)) {
            return activeBoxes.get(boxName);
        }
        return null;
    }

    public HashMap<String, BetterBox> getActiveBoxes() {
        return activeBoxes;
    }

    //Utility
    public void reloadBoxes() {
        pl.reloadConfig();
        for (String s : activeBoxes.keySet()) {
            activeBoxes.get(s).getCrateConfig().reload();
        }
    }

    public void giveItems(Player toGive, String boxName) {
        if (!activeBoxes.containsKey(boxName)) {
            return;
        }
        for (Integer i : activeBoxes.get(boxName).getItems().keySet()) {
            HashMap<Integer, ItemStack> remainingItems = toGive.getInventory().addItem(activeBoxes.get(boxName).getItems().get(i));
            for (Integer ir : remainingItems.keySet()) {
                toGive.getWorld().dropItemNaturally(toGive.getLocation(), remainingItems.get(ir));
            }
        }
    }
}
