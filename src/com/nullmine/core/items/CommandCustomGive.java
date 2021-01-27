package com.nullmine.core.items;

import com.nullmine.core.NullMine;
import com.nullmine.core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CommandCustomGive implements CommandExecutor, TabCompleter {

    public CommandCustomGive() {
        Bukkit.getPluginCommand("customgive").setExecutor(this);
        Bukkit.getPluginCommand("customgive").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length > 3 || args.length < 2) {
            return false;
        }

        String nick = args[0];
        String id = args[1];
        int amount = args.length == 2 ? 1 : Integer.parseInt(args[2]);

        Player player = Bukkit.getPlayer(nick);
        if (player == null || !player.isOnline()) {
            sender.sendMessage("§3Sorry, but §b" + nick + "§3 not found or not online");
            return true;
        }

        ItemStack item = ItemManager.getInstance().get(id);
        if (item == null) {
            sender.sendMessage("§3Sorry, but §b" + id + "§3 is not a correct DreamingWorld item id");
            return true;
        }

        item.setAmount(amount);
        player.getInventory().addItem(item);
        sender.sendMessage("§3Given §b" + amount + "§3 pcs of §b" + id + "§3 to §b" + nick);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length == 1) {
            return Util.smartAutocomplete(NullMine.playerNames, args);
        } else if (args.length == 2) {
            return Util.smartAutocomplete(ItemManager.getInstance().getIds(), args);
        }

        return new ArrayList<>();
    }
}
