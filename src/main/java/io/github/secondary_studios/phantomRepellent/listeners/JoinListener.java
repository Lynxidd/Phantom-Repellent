package io.github.secondary_studios.phantomRepellent.listeners;

import io.github.secondary_studios.phantomRepellent.PhantomRepellent;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class JoinListener implements Listener {

    private final PhantomRepellent plugin;

    public JoinListener(PhantomRepellent plugin)  {this.plugin = plugin;}

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();
        PersistentDataContainer pdc = p.getPersistentDataContainer();
        NamespacedKey phantomKey = new NamespacedKey(this.plugin, "phantom");
        boolean phantomsOff = Objects.equals(pdc.get(phantomKey, PersistentDataType.BOOLEAN), true);

        Boolean phantoms = null;

        try {
            phantoms = pdc.get(phantomKey, PersistentDataType.BOOLEAN);
        } catch (Exception i) {
            //stfu
        }

        if (!p.hasPlayedBefore()) {
            pdc.set(phantomKey, PersistentDataType.BOOLEAN, false);
            plugin.getLogger().info("New player joined! Setting Phantoms to 'On' for " + p.getName());
        } else if (phantoms == null) {
            pdc.set(phantomKey, PersistentDataType.BOOLEAN, false);
            plugin.getLogger().info("This player doesn't have a Phantoms preference set!! Setting Phantoms to 'On' for " + p.getName());
        } else if (phantomsOff) {
            plugin.resetPlayer(p);
        }
    }
}
