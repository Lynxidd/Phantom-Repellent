package me.lynxid.phantomRepellent.commands;

import me.lynxid.phantomRepellent.PhantomRepellent;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;

import org.bukkit.persistence.PersistentDataType;

import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getServer;

public class PhantomCommand implements CommandExecutor {

    private final PhantomRepellent plugin;

    public PhantomCommand(PhantomRepellent plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] strings) {

        if (strings.length < 1) {
            sender.sendMessage(ChatColor.GRAY + "Usage: /phantoms [get|on|off]");
            return true;
        }
        if (sender instanceof Player p) {
            String playerN = (p.getName());
            if (strings[0].equalsIgnoreCase("get") && strings.length < 2) {
                PersistentDataContainer pdc = p.getPersistentDataContainer();
                NamespacedKey phantomKey = new NamespacedKey(this.plugin, "phantom");
                Boolean phantoms = pdc.get(phantomKey, PersistentDataType.BOOLEAN);
                if (Boolean.FALSE.equals(phantoms)) {
                    p.sendMessage(ChatColor.BLUE + "Phantoms are turned" + ChatColor.RED + " " + ChatColor.BOLD + "on " + ChatColor.BLUE + "for" + ChatColor.WHITE + " " + ChatColor.BOLD + playerN);
                } else if (Boolean.TRUE.equals(phantoms)) {
                    p.sendMessage(ChatColor.BLUE + "Phantoms are turned" + ChatColor.GREEN + " " + ChatColor.BOLD + "off " + ChatColor.BLUE + "for" + ChatColor.WHITE + " " + ChatColor.BOLD + playerN);
                }
            } else if (strings[0].equalsIgnoreCase("get") && strings.length == 2 && sender.hasPermission("phantoms.admin")) {
                Player target = getServer().getPlayer(strings[1]);
                if (target != null) {
                    String targetN = target.getName();
                    PersistentDataContainer pdc = target.getPersistentDataContainer();
                    NamespacedKey phantomKey = new NamespacedKey(this.plugin, "phantom");
                    Boolean phantoms = pdc.get(phantomKey, PersistentDataType.BOOLEAN);
                    if (Boolean.FALSE.equals(phantoms) || phantoms == null ) {
                        p.sendMessage(ChatColor.BLUE + "Phantoms are turned" + ChatColor.RED + " " + ChatColor.BOLD + "on " + ChatColor.BLUE + "for" + ChatColor.WHITE + " " + ChatColor.BOLD + targetN);
                    } else if (phantoms) {
                        p.sendMessage(ChatColor.BLUE + "Phantoms are turned" + ChatColor.GREEN + " " + ChatColor.BOLD + "off " + ChatColor.BLUE + "for" + ChatColor.WHITE + " " + ChatColor.BOLD + targetN);
                    }
                } else {
                    sender.sendMessage("That player is not online!");
                }
            } else if (strings[0].equalsIgnoreCase("on") && strings.length < 2) {
                PersistentDataContainer pdc = p.getPersistentDataContainer();
                NamespacedKey phantomKey = new NamespacedKey(this.plugin, "phantom");
                pdc.set(phantomKey, PersistentDataType.BOOLEAN, false);
                p.sendMessage(ChatColor.RED + "Turned on Phantoms for" + ChatColor.WHITE + " " + ChatColor.BOLD + playerN);
            } else if (strings[0].equalsIgnoreCase("on") && strings.length == 2 && sender.hasPermission("phantoms.admin")) {
                Player target = getServer().getPlayer(strings[1]);
                if (target != null) {
                    String targetN = target.getName();
                    PersistentDataContainer pdc = target.getPersistentDataContainer();
                    NamespacedKey phantomKey = new NamespacedKey(this.plugin, "phantom");
                    pdc.set(phantomKey, PersistentDataType.BOOLEAN, false);
                    p.sendMessage(ChatColor.RED + "Turned on Phantoms for" + ChatColor.WHITE + " " + ChatColor.BOLD + targetN);

                } else {
                    sender.sendMessage("That player is not online!");
                }
            } else if (strings[0].equalsIgnoreCase("off") && strings.length < 2) {
                PersistentDataContainer pdc = p.getPersistentDataContainer();
                NamespacedKey phantomKey = new NamespacedKey(this.plugin, "phantom");
                pdc.set(phantomKey, PersistentDataType.BOOLEAN, true);
                p.sendMessage(ChatColor.GREEN + "Turned off Phantoms for" + ChatColor.WHITE + " " + ChatColor.BOLD + playerN);
            } else if (strings[0].equalsIgnoreCase("off") && strings.length == 2 && sender.hasPermission("phantoms.admin")) {
                Player target = getServer().getPlayer(strings[1]);
                if (target != null) {
                    String targetN = target.getName();
                    PersistentDataContainer pdc = target.getPersistentDataContainer();
                    NamespacedKey phantomKey = new NamespacedKey(this.plugin, "phantom");
                    pdc.set(phantomKey, PersistentDataType.BOOLEAN, true);
                    p.sendMessage(ChatColor.GREEN + "Turned off Phantoms for" + ChatColor.WHITE + " " + ChatColor.BOLD + targetN);
                } else {
                    sender.sendMessage("That player is not online!");
                }
            } else if (strings[0].equalsIgnoreCase("off") || strings[0].equalsIgnoreCase("on") || strings[0].equalsIgnoreCase("get") && strings.length == 2 && !sender.hasPermission("phantoms.admin")) {
                sender.sendMessage("You do you have permission to turn that command");
            }
        }
        return true;
    }
}