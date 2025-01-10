package me.lynxid.phantomRepellent;

import me.lynxid.phantomRepellent.commands.PhantomCommand;
import me.lynxid.phantomRepellent.listeners.JoinListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class PhantomRepellent extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Phantom repellent has started!");

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new JoinListener(this),this);
        Objects.requireNonNull(getCommand("phantoms")).setExecutor(new PhantomCommand(this));

    }

    @Override
    public void onDisable() {
        getLogger().info("Phantom repellent has stopped!");
    }

}
