package io.github.eggallocationservice.rewindrepeat.snapshots;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SEntityInfo {
    public String type;
    public double x;
    public int id;
    public double y;
    public double z;
    public String name = "";
    public double pitch;
    public double yaw;
    public SEntityInfo(Entity e) {
        type = e.getType().toString();
        x = e.getLocation().getX();
        y = e.getLocation().getY();
        z = e.getLocation().getZ();
        if (e.getCustomName() != null) {
            name = e.getCustomName();
        } else if (e.getType() == EntityType.PLAYER) {
            name = ((Player) e).getName();
        }
        pitch = e.getLocation().getPitch();
        yaw = e.getLocation().getYaw();
        id = e.getEntityId();

    }
}
