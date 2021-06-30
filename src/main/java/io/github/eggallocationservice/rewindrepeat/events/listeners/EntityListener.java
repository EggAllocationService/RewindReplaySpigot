package io.github.eggallocationservice.rewindrepeat.events.listeners;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import io.github.eggallocationservice.rewindrepeat.events.ReelManager;
import io.github.eggallocationservice.rewindrepeat.events.types.*;
import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.Material;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

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
        b.hasFrom = true;
        b.fx = e.getFrom().getX();
        b.fy = e.getFrom().getY();
        b.fz = e.getFrom().getZ();
        b.fyaw = e.getFrom().getYaw();
        b.fpitch = e.getFrom().getPitch();

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
        b.hasFrom = true;
        b.fx = e.getFrom().getX();
        b.fy = e.getFrom().getY();
        b.fz = e.getFrom().getZ();
        b.fyaw = e.getFrom().getYaw();
        b.fpitch = e.getFrom().getPitch();
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
        b.sx = e.getEntity().getVelocity().getX();
        b.sy = e.getEntity().getVelocity().getY();
        b.sz = e.getEntity().getVelocity().getZ();
        ReelManager.get(e.getEntity().getWorld()).add(b);
        if (e.getEntity() instanceof Projectile) {
            ProjectileHandler.trackedProjectiles.add((Projectile) e.getEntity());
        }
        if (e.getEntity().getType() == EntityType.ENDER_SIGNAL) {
            ((EnderSignal) e.getEntity()).setItem(new ItemStack(Material.DIAMOND_BLOCK));
            System.out.println("why");
        }

    }



    @EventHandler
    public void disconnect(PlayerQuitEvent e) {
        if (ReelManager.get(e.getPlayer().getWorld()) == null) {
            return;
        }
        PlayerDespawnEvent b = new PlayerDespawnEvent();
        b.entityId = e.getPlayer().getEntityId();
        ReelManager.get(e.getPlayer().getWorld()).add(b);
    }
    @EventHandler
    public void spawn(PlayerJoinEvent e) {
        if (ReelManager.get(e.getPlayer().getWorld()) == null) {
            return;
        }
        io.github.eggallocationservice.rewindrepeat.events.types.EntitySpawnEvent b = new io.github.eggallocationservice.rewindrepeat.events.types.EntitySpawnEvent();
        b.entityId = e.getPlayer().getEntityId();
        b.yaw = e.getPlayer().getLocation().getYaw();
        b.pitch = e.getPlayer().getLocation().getPitch();
        b.y = e.getPlayer().getLocation().getY();
        b.z = e.getPlayer().getLocation().getZ();
        b.x = e.getPlayer().getLocation().getX();
        b.sx = 0;
        b.sy = 0;
        b.sz = 0;
        b.name = e.getPlayer().getName();
        b.hasName = true;
        b.type = "PLAYER";

        ReelManager.get(e.getPlayer().getWorld()).add(b);
    }


    @EventHandler
    public void projectileHit(ProjectileHitEvent e) {
        if (!ProjectileHandler.trackedProjectiles.contains(e.getEntity())) {
            return;
        }
        ProjectileHandler.trackedProjectiles.remove(e.getEntity());
        EntityDiesEvent b = new EntityDiesEvent();
        b.entityId = e.getEntity().getEntityId();
        b.x = e.getEntity().getLocation().getX();
        b.y = e.getEntity().getLocation().getY();
        b.z = e.getEntity().getLocation().getZ();
        b.pitch = e.getEntity().getLocation().getPitch();
        b.yaw = e.getEntity().getLocation().getPitch();
        b.type = e.getEntity().getType().toString();
        ReelManager.get(e.getEntity().getWorld()).add(b);
    }

    @EventHandler
    public void projectile(PlayerLaunchProjectileEvent e) {

    }
    @EventHandler
    public void heldItem(PlayerItemHeldEvent e) {
        if (ReelManager.get(e.getPlayer().getWorld()) == null) {
            return;
        }
        PlayerSwitchHeldItemEvent b = new PlayerSwitchHeldItemEvent();
        b.entityId = e.getPlayer().getEntityId();
        ItemStack inHand =  e.getPlayer().getInventory().getItem(e.getNewSlot());
        if (inHand == null) {
            b.material = "AIR";
        } else {
            b.material = inHand.getType().toString();
        }
        ItemStack oldHand =  e.getPlayer().getInventory().getItem(e.getPreviousSlot());
        if (oldHand == null) {
            b.oldMaterial = "AIR";
        } else {
            b.oldMaterial = oldHand.getType().toString();
        }
        ReelManager.get(e.getPlayer().getWorld()).add(b);
    }
    @EventHandler
    public void swing(PlayerAnimationEvent e) {
        if (ReelManager.get(e.getPlayer().getWorld()) == null) {
            return;
        }
        PlayerSwingArmEvent b = new PlayerSwingArmEvent();
        b.entityId = e.getPlayer().getEntityId();
        ReelManager.get(e.getPlayer().getWorld()).add(b);
    }
    @EventHandler
    public void death(EntityDeathEvent e) {
        if (e.getEntity().getType() == EntityType.PLAYER) return;
        if (ReelManager.get(e.getEntity().getWorld()) == null) {
            return;
        }
        EntityDiesEvent b = new EntityDiesEvent();
        b.entityId = e.getEntity().getEntityId();
        b.x = e.getEntity().getLocation().getX();
        b.y = e.getEntity().getLocation().getY();
        b.z = e.getEntity().getLocation().getZ();
        b.pitch = e.getEntity().getLocation().getPitch();
        b.yaw = e.getEntity().getLocation().getPitch();
        b.type = e.getEntity().getType().toString();
        ReelManager.get(e.getEntity().getWorld()).add(b);
    }
}
