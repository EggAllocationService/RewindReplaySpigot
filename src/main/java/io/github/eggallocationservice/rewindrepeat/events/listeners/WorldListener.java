package io.github.eggallocationservice.rewindrepeat.events.listeners;

import io.github.eggallocationservice.rewindrepeat.events.ReelManager;
import io.github.eggallocationservice.rewindrepeat.events.types.ExplosionEvent;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;

public class WorldListener implements Listener {
    @EventHandler
    public void explode(BlockExplodeEvent e) {
        if (ReelManager.get(e.getBlock().getWorld()) == null) return;
        ExplosionEvent b = new ExplosionEvent();
        b.x = e.getBlock().getX();
        b.y = e.getBlock().getY();
        b.z = e.getBlock().getZ();
        b.toBreak = new ArrayList<>();
        for (Block a : e.blockList()) {
            int[] loc = new int[3];
            loc[0] = a.getX();
            loc[1] = a.getY();
            loc[2] = a.getZ();
            b.toBreak.add(loc);
        }
        ReelManager.get(e.getBlock().getWorld()).add(b);
    }
    @EventHandler
    public void explode(EntityExplodeEvent e) {
        if (ReelManager.get(e.getEntity().getWorld()) == null) return;
        ExplosionEvent b = new ExplosionEvent();
        b.x = e.getEntity().getLocation().getBlockX();
        b.y = e.getEntity().getLocation().getBlockY();
        b.z = e.getEntity().getLocation().getBlockZ();
        b.toBreak = new ArrayList<>();
        for (Block a : e.blockList()) {
            int[] loc = new int[3];
            loc[0] = a.getX();
            loc[1] = a.getY();
            loc[2] = a.getZ();
            b.toBreak.add(loc);
        }
        ReelManager.get(e.getEntity().getWorld()).add(b);
    }
}
