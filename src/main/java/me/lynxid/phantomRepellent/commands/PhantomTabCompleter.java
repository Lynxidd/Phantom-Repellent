package me.lynxid.phantomRepellent.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PhantomTabCompleter implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender Sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (command.getName().equalsIgnoreCase("phantoms") && strings.length <= 1) {
            List<String> list = new ArrayList<>();
            list.add("get");
            list.add("on");
            list.add("off");
            return list;
        } else if (command.getName().equalsIgnoreCase("phantoms") && strings.length <= 2 && strings[0].equalsIgnoreCase("get") || strings[0].equalsIgnoreCase("off") || strings[0].equalsIgnoreCase("on") && Sender.hasPermission("phantoms.admin")) {
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (Player player : players) {
                playerNames.add(player.getName());
            }
            return playerNames;
        }
        return null;
    }
}
