package ro.Stellrow.BetterBoxes.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static String asColor(String toTraslate){
        return ChatColor.translateAlternateColorCodes('&',toTraslate);
    }
    public static List<String> loreAsColor(List<String> toTranslate){
        List<String> toReturn = new ArrayList<>();
        for(String s : toTranslate){
            toReturn.add(asColor(s));
        }
        return toReturn;
    }
}
