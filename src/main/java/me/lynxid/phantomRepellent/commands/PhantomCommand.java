package me.lynxid.phantomRepellent.commands;

import me.lynxid.phantomRepellent.PhantomRepellent;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;

import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class PhantomCommand implements CommandExecutor {

    private final PhantomRepellent plugin;

    public PhantomCommand(PhantomRepellent plugin)  {this.plugin = plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] strings) {

        if (strings.length < 1) {
            sender.sendMessage("Usage: /phantoms [get|on|off]");
            return true;
        }
        if (sender instanceof Player p) {
            String playerN = (p.getName());
            if (strings[0].equalsIgnoreCase("get")) {
                PersistentDataContainer pdc = p.getPersistentDataContainer();
                NamespacedKey phantomKey = new NamespacedKey(this.plugin, "phantom");
                Boolean phantoms = pdc.get(phantomKey, PersistentDataType.BOOLEAN);
                if (Boolean.FALSE.equals(phantoms)) {
                    p.sendMessage("Phantoms are turned on for " + playerN);
                } else if (Boolean.TRUE.equals(phantoms)) {
                    p.sendMessage("Phantoms are turned off for " + playerN);
                }
            } else if (strings[0].equalsIgnoreCase("on")) {
                PersistentDataContainer pdc = p.getPersistentDataContainer();
                NamespacedKey phantomKey = new NamespacedKey(this.plugin, "phantom");
                pdc.set(phantomKey, PersistentDataType.BOOLEAN, false);
                p.sendMessage("Turned phantoms on for " + playerN);
            } else if (strings[0].equalsIgnoreCase("off")) {
                PersistentDataContainer pdc = p.getPersistentDataContainer();
                NamespacedKey phantomKey = new NamespacedKey(this.plugin, "phantom");
                pdc.set(phantomKey, PersistentDataType.BOOLEAN, true);
                p.sendMessage("Turned phantoms off for " + playerN);
            }
        }
        return true;
    }
}