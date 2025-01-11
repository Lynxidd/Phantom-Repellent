package me.lynxid.phantomRepellent;

import me.lynxid.phantomRepellent.commands.PhantomCommand;
import me.lynxid.phantomRepellent.listeners.JoinListener;
import me.lynxid.phantomRepellent.listeners.PhantomListener;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Objects;

public final class PhantomRepellent extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        BukkitScheduler scheduler = this.getServer().getScheduler();


        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PhantomListener(this), this);
        Objects.requireNonNull(getCommand("phantoms")).setExecutor(new PhantomCommand(this));

        getLogger().info("Phantom repellent has started!");


        scheduler.runTaskTimer(this, () -> {
            for (var world : Bukkit.getWorlds()) {
                final long time = world.getTime();
                final boolean night = time >= 12000;

                if (night) {
                    resetStatistics(world);
                    getLogger().info("Statistics reset complete for " + world.getName());
                }
            }
        }, 0L, 6000);

    }



    private void resetStatistics(World world) {
        for (final Player player : world.getPlayers()) {
            NamespacedKey phantomKey = new NamespacedKey(this, "phantom");
            PersistentDataContainer pdc = player.getPersistentDataContainer();

            if (pdc.has(phantomKey, PersistentDataType.BOOLEAN)) {
                resetPlayer(player);
            }
        }
    }

    public void resetPlayer(Player player) {
        player.setStatistic(Statistic.TIME_SINCE_REST, 0);
        getLogger().info("Reset statistics for " + player.getName());
    }
    @Override
    public void onDisable() {
        getLogger().info("Phantom repellent has stopped!");
    }

}