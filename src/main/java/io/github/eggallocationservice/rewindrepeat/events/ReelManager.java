package io.github.eggallocationservice.rewindrepeat.events;

import org.bukkit.World;

import java.util.HashMap;

public class ReelManager {
    public static HashMap<String, EventReel> reels = new HashMap<>();
    public static void create(String worldName) {
        reels.put(worldName, new EventReel());
    }
    public static EventReel get(World w) {
        return reels.get(w.getName());
    }
    public static void tick() {
        for (EventReel r : reels.values()) {
            r.tick();
        }
    }
}
