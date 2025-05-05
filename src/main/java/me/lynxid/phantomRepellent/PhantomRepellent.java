package me.lynxid.phantomRepellent;

import me.lynxid.phantomRepellent.commands.PhantomCommand;
import me.lynxid.phantomRepellent.commands.PhantomTabCompleter;
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
        Objects.requireNonNull(getCommand("phantoms")).setTabCompleter(new PhantomTabCompleter());

        getLogger().info("Phantom repellent has started!");


        scheduler.runTaskTimer(this, () -> {
            for (var world : Bukkit.getWorlds()) {
                final long time = world.getTime();
                final boolean night = time >= 12000;

                if (night) {
                    resetStatistics(world);
                }
            }
        }, 0L, 6000);

    }

    private void resetStatistics(World world) {
        for (final Player player : world.getPlayers()) {
            NamespacedKey phantomKey = new NamespacedKey(this, "phantom");
            PersistentDataContainer pdc = player.getPersistentDataContainer();
            boolean phantomsOff = Objects.equals(pdc.get(phantomKey, PersistentDataType.BOOLEAN), true);

            if (phantomsOff) {
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