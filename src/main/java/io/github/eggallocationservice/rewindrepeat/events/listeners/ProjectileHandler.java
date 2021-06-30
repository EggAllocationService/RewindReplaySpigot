package io.github.eggallocationservice.rewindrepeat.events.listeners;

import io.github.eggallocationservice.rewindrepeat.events.ReelManager;
import io.github.eggallocationservice.rewindrepeat.events.types.EntityMovementEvent;
import org.bukkit.entity.Projectile;

import java.util.HashSet;

public class ProjectileHandler {
    public static HashSet<Projectile> trackedProjectiles = new HashSet<>();
    public static void tick() {
        for (Projectile p : trackedProjectiles) {
            EntityMovementEvent e = new EntityMovementEvent();
            e.x = p.getLocation().getX();
            e.y = p.getLocation().getY();
            e.z = p.getLocation().getZ();
            e.yaw = p.getLocation().getYaw();
            e.pitch = p.getLocation().getPitch();
            e.entityId = p.getEntityId();
            e.hasFrom = false;
            ReelManager.get(p.getWorld()).add(e);
        }
    }
}
