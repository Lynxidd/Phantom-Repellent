package me.lynxid.phantomRepellent.listeners;

import me.lynxid.phantomRepellent.PhantomRepellent;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class JoinListener implements Listener {

    private final PhantomRepellent plugin;

    public JoinListener(PhantomRepellent plugin)  {this.plugin = plugin;}

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();
        PersistentDataContainer pdc = p.getPersistentDataContainer();
        NamespacedKey phantomKey = new NamespacedKey(this.plugin, "phantom");

        Boolean phantoms = null;

        try {
            phantoms = pdc.get(phantomKey, PersistentDataType.BOOLEAN);
        } catch (Exception i) {
            //stfu
        }

        if (!p.hasPlayedBefore() || phantoms == null) {
            pdc.set(phantomKey, PersistentDataType.BOOLEAN, false);
        } else if (pdc.has(phantomKey, PersistentDataType.BOOLEAN)) {
            plugin.resetPlayer(p);
        }
    }
}
