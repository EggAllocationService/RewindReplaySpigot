package io.github.eggallocationservice.rewindrepeat.snapshots;

import io.github.eggallocationservice.rewindrepeat.Config;

import java.util.ArrayList;

public class SnapshotReel {
    public  volatile ArrayList<Snapshot> reel = new ArrayList<>(30);
    public  void newSnapshot(Snapshot s) {
        if (reel.size() == Config.BUFFER_SIZE_SECONDS) {
            reel.remove(0);
        }
        reel.add(s);
    }
    public Snapshot getSnapshot(int secondsAgo) {
        return reel.get(Config.BUFFER_SIZE_SECONDS - secondsAgo);
    }
}
