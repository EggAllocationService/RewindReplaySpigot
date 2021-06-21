package io.github.eggallocationservice.rewindrepeat.events.listeners;

import io.github.eggallocationservice.rewindrepeat.events.ReelManager;
import io.github.eggallocationservice.rewindrepeat.events.types.EntityMovementEvent;
import io.github.eggallocationservice.rewindrepeat.events.types.PlayerBreakBlockEvent;
import io.github.eggallocationservice.rewindrepeat.events.types.PlayerPlaceBlockEvent;
import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class EntityListener implements Listener {
    @EventHandler
    public void move(EntityMoveEvent e) {
        if (ReelManager.get(e.getEntity().getWorld()) == null) {
            return;
        }
        if (e.getEntity().getType() == EntityType.PLAYER) return;
        EntityMovementEvent b = new EntityMovementEvent();
        b.entityId = e.getEntity().getEntityId();
        b.yaw = e.getTo().getYaw();
        b.pitch = e.getTo().getPitch();
        b.y = e.getTo().getY();
        b.z = e.getTo().getZ();
        b.x = e.getTo().getX();
        ReelManager.get(e.getEntity().getWorld()).add(b);

    }
    @EventHandler
    public void move(PlayerMoveEvent e) {
        if (ReelManager.get(e.getPlayer().getWorld()) == null) {
            return;
        }
        EntityMovementEvent b = new EntityMovementEvent();
        b.entityId = e.getPlayer().getEntityId();
        b.yaw = e.getTo().getYaw();
        b.pitch = e.getTo().getPitch();
        b.y = e.getTo().getY();
        b.z = e.getTo().getZ();
        b.x = e.getTo().getX();
        ReelManager.get(e.getPlayer().getWorld()).add(b);

    }

    @EventHandler
    public void place(BlockPlaceEvent e) {
        if (ReelManager.get(e.getPlayer().getWorld()) == null) {
            return;
        }
        PlayerPlaceBlockEvent b = new PlayerPlaceBlockEvent();
        b.entityId = e.getPlayer().getEntityId();
        b.material = e.getBlockPlaced().getBlockData().getMaterial().toString();
        b.x = e.getBlockPlaced().getX();
        b.y = e.getBlockPlaced().getY();
        b.z = e.getBlockPlaced().getZ();
        ReelManager.get(e.getPlayer().getWorld()).add(b);
    }
    @EventHandler
    public void brk(BlockBreakEvent e) {
        if (ReelManager.get(e.getPlayer().getWorld()) == null) {
            return;
        }
        PlayerBreakBlockEvent b = new PlayerBreakBlockEvent();
        b.entityId = e.getPlayer().getEntityId();
        b.oldMaterial = e.getBlock().getBlockData().getMaterial().toString();
        b.x = e.getBlock().getX();
        b.y = e.getBlock().getY();
        b.z = e.getBlock().getZ();
        ReelManager.get(e.getPlayer().getWorld()).add(b);
    }
    @EventHandler
    public void spawn(EntitySpawnEvent e) {
        if (ReelManager.get(e.getEntity().getWorld()) == null) {
            return;
        }
        io.github.eggallocationservice.rewindrepeat.events.types.EntitySpawnEvent b = new io.github.eggallocationservice.rewindrepeat.events.types.EntitySpawnEvent();
        b.entityId = e.getEntity().getEntityId();
        b.x = e.getLocation().getX();
        b.y = e.getLocation().getY();
        b.z = e.getLocation().getZ();
        b.pitch = e.getLocation().getPitch();
        b.yaw = e.getLocation().getPitch();
        b.type = e.getEntity().getType().toString();
        ReelManager.get(e.getEntity().getWorld()).add(b);
    }
}
