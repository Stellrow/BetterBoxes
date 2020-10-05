package ro.Stellrow.BetterBoxes.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ro.Stellrow.BetterBoxes.BetterBoxes;
import ro.Stellrow.BetterBoxes.boxeshandling.BetterBox;
import ro.Stellrow.BetterBoxes.utils.CrateConfig;
import ro.Stellrow.BetterBoxes.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class BetterBoxCommand implements CommandExecutor {
    private final BetterBoxes pl;
    private String prefix;

    public BetterBoxCommand(BetterBoxes pl) {
        this.pl = pl;
        prefix=Utils.asColor(pl.getConfig().getString("Messages.prefix"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String sa, String[] args) {
        if(sender.hasPermission("betterboxes.admin")){
            //Reload
            if(args.length==1&&args[0].equalsIgnoreCase("reload")){
                pl.getBoxesManager().reloadBoxes();
                prefix=Utils.asColor(pl.getConfig().getString("Messages.prefix"));
                sender.sendMessage(prefix+Utils.asColor(pl.getConfig().getString("Messages.reloaded")));
                return true;
            }
            //Create
            if(args.length==2&&args[0].equalsIgnoreCase("create")){
                String boxName = args[1];
                if(pl.getBoxesManager().getActiveBoxes().containsKey(boxName)){
                    sender.sendMessage(prefix+Utils.asColor(pl.getConfig().getString("Messages.box-already-exists")));
                    return true;
                }
                pl.getBoxesManager().addBox(boxName,new BetterBox(new CrateConfig(boxName,pl)));
                sender.sendMessage(prefix+Utils.asColor(pl.getConfig().getString("Messages.created-box").replaceAll("%boxName",boxName)));
                return true;
            }
            //Delete
            if(args.length==2&&args[0].equalsIgnoreCase("delete")||args.length==2&&args[0].equalsIgnoreCase("remove")){
                String boxName = args[1];
                if(!pl.getBoxesManager().getActiveBoxes().containsKey(boxName)){
                    sender.sendMessage(prefix+Utils.asColor(pl.getConfig().getString("Messages.no-such-box")));
                    return true;
                }
                pl.getBoxesManager().removeBox(boxName);
                    sender.sendMessage(prefix+Utils.asColor(pl.getConfig().getString("Messages.deleted-box").replaceAll("%boxName",boxName)));

            }
            //Edit
            if(args.length==2&&args[0].equalsIgnoreCase("edit")){
                if(!(sender instanceof Player)){
                    sender.sendMessage(Utils.asColor("&cPlayers only command!"));
                    return true;
                }
                String boxName = args[1];
                if(!pl.getBoxesManager().getActiveBoxes().containsKey(boxName)){
                    sender.sendMessage(prefix+Utils.asColor(pl.getConfig().getString("Messages.no-such-box")));
                    return true;
                }
                //open editor for box
                pl.getInventoryHandler().editBox((Player)sender,boxName);
                return true;
            }
            //SetName
            if(args.length>=3&&args[0].equalsIgnoreCase("setname")){
                String boxName = args[1];
                if(!pl.getBoxesManager().getActiveBoxes().containsKey(boxName)){
                    sender.sendMessage(prefix+Utils.asColor(pl.getConfig().getString("Messages.no-such-box")));
                    return true;
                }
                StringBuilder builder = new StringBuilder();
                for(int x = 2;x<args.length;x++){
                    builder.append(args[x]+" ");
                }
                pl.getBoxesManager().getActiveBoxes().get(boxName).getCrateConfig().getConfig().set("BoxConfig.name",Utils.asColor(builder.toString()));
                pl.getBoxesManager().getActiveBoxes().get(boxName).getCrateConfig().save();
                sender.sendMessage(prefix+Utils.asColor(pl.getConfig().getString("Messages.changed-box-name")));
                return true;
            }
            //SetLore
            if(args.length>=3&&args[0].equalsIgnoreCase("setlore")){
                String boxName = args[1];
                if(!pl.getBoxesManager().getActiveBoxes().containsKey(boxName)){
                    sender.sendMessage(prefix+Utils.asColor(pl.getConfig().getString("Messages.no-such-box")));
                    return true;
                }
                StringBuilder builder = new StringBuilder();
                for(int x = 2;x<args.length;x++){
                    builder.append(args[x]+" ");
                }
                String raw = builder.toString();
                String[] splitted = raw.split("/");
                List<String> lore = new ArrayList<>();
                for(String s : splitted){
                    lore.add(Utils.asColor(s));
                }

                pl.getBoxesManager().getActiveBoxes().get(boxName).getCrateConfig().getConfig().set("BoxConfig.lore",lore);
                pl.getBoxesManager().getActiveBoxes().get(boxName).getCrateConfig().save();
                sender.sendMessage(prefix+Utils.asColor(pl.getConfig().getString("Messages.changed-box-lore")));
                return true;
            }
            //Give
            if(args.length==4&&args[0].equalsIgnoreCase("give")){
                Player target = Bukkit.getPlayer(args[1]);
                if(target==null){
                    sender.sendMessage(prefix+Utils.asColor(pl.getConfig().getString("Messages.no-such-player")));
                    return true;
                }
                String boxName = args[2];
                if(!pl.getBoxesManager().getActiveBoxes().containsKey(boxName)){
                    sender.sendMessage(prefix+Utils.asColor(pl.getConfig().getString("Messages.no-such-box")));
                    return true;
                }
                Integer amount;
                try{
                    amount = Integer.parseInt(args[3]);
                    if(amount>64){amount=64;}
                    addItem(target,pl.getItemBuilder().getItemForBox(boxName,amount));
                    if (sender instanceof Player) {
                        sender.sendMessage(Utils.asColor(pl.getConfig().getString("Messages.gave-player-box")));
                    }
                    target.sendMessage(prefix+Utils.asColor(pl.getConfig().getString("Messages.received-box").replaceAll("%boxName",boxName)));
                    return true;
                }catch (IllegalArgumentException ex){
                    sender.sendMessage(prefix+Utils.asColor(pl.getConfig().getString("Messages.worng-amount")));
                    return true;
                }
            }
        }
        sender.sendMessage(prefix+Utils.asColor("&aBetter Boxes made by Stellrow"));
        if(sender.hasPermission("betterboxes.admin")) {
            sender.sendMessage(prefix + Utils.asColor("&aCommands available"));
            sender.sendMessage(prefix + Utils.asColor("&7/betterboxes create <boxName>"));
            sender.sendMessage(prefix + Utils.asColor("&7/betterboxes delete <boxName>"));
            sender.sendMessage(prefix + Utils.asColor("&7/betterboxes edit <boxName>"));
            sender.sendMessage("");
            sender.sendMessage(prefix + Utils.asColor("&7/betterboxes setname <boxName> <name(allows multiple words)>"));
            sender.sendMessage(prefix + Utils.asColor("&7Example: /betterboxes setname mining ")+"&7Mining box");
            sender.sendMessage("");
            sender.sendMessage(prefix + Utils.asColor("&7/betterboxes setlore <boxName> lore1/lore2/lore3"));
            sender.sendMessage(prefix + Utils.asColor("&7Each lore line is separated by '&a/&7'!"));
            sender.sendMessage(prefix + Utils.asColor("&7Example: /betterboxes setlore mining ")+"&7This is a mining box/&7Contains usefull stuff");
            sender.sendMessage("");
            sender.sendMessage(prefix+Utils.asColor("&cYou can find the box's name and lore in the config file inside the boxes folder!"));
            sender.sendMessage("");
            sender.sendMessage(prefix + Utils.asColor("&7/betterboxes give <player> <boxName> <amount>"));
        }
        return true;
    }


    //Utility
    private void addItem(Player player, ItemStack item){
        if(player.getInventory().firstEmpty()==-1){
            player.getWorld().dropItemNaturally(player.getLocation(),item);
            return;
        }
        player.getInventory().addItem(item);
    }
}
