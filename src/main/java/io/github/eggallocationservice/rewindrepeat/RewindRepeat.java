package io.github.eggallocationservice.rewindrepeat;

import io.github.eggallocationservice.rewindrepeat.events.ReelManager;
import io.github.eggallocationservice.rewindrepeat.events.listeners.EntityListener;
import io.github.eggallocationservice.rewindrepeat.snapshots.SnapshotReel;
import io.github.eggallocationservice.rewindrepeat.snapshots.SnapshotReelManager;
import io.github.eggallocationservice.rewindrepeat.snapshots.SnapshotThread;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockChange;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RewindRepeat extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        new SnapshotCommand().register();
        ReelManager.create("world");
        SnapshotReelManager.setup("world");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, SnapshotReelManager::tick, 0, 20);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, ReelManager::tick, 0, 0);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
