package io.github.eggallocationservice.rewindrepeat.snapshots;

import org.bukkit.Bukkit;

import java.util.HashMap;

public class SnapshotReelManager {
    public static HashMap<String, SnapshotReel> reels = new HashMap<>();
    public static void setup(String worldName) {
        reels.put(worldName, new SnapshotReel());
    }
    public static void tick() {
        for (String s : reels.keySet()) {
            new SnapshotThread(Bukkit.getWorld(s)).start();
        }
    }
}
