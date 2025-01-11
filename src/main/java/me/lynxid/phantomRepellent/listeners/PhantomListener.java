package me.lynxid.phantomRepellent.listeners;

import me.lynxid.phantomRepellent.PhantomRepellent;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PhantomListener implements Listener {

    private final PhantomRepellent plugin;
    public PhantomListener(PhantomRepellent plugin)  {this.plugin = plugin;}

    @EventHandler
    public void onEntityTarget(EntityTargetEvent e){
       if (e.getEntity().getType() != EntityType.PHANTOM) {return;}
       final Entity target = e.getTarget();
        NamespacedKey phantomKey = new NamespacedKey(this.plugin, "phantom");
        assert target != null;
        PersistentDataContainer pdc = target.getPersistentDataContainer();
        e.setCancelled(target instanceof Player && pdc.has(phantomKey, PersistentDataType.BOOLEAN));
    }
}
