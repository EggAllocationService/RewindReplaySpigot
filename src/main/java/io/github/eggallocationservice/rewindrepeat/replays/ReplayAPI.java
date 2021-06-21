package io.github.eggallocationservice.rewindrepeat.replays;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.github.eggallocationservice.rewindrepeat.events.ReelManager;
import io.github.eggallocationservice.rewindrepeat.snapshots.SnapshotReelManager;
import io.github.eggallocationservice.rewindrepeat.snapshots.SnapshotThread;
import org.bukkit.World;

public class ReplayAPI {
    public static byte[] captureReplay(World w, int seconds) {
        byte[] snapshot = SnapshotReelManager.reels.get(w.getName()).getSnapshot(seconds).encode();
        byte[] events = ReelManager.get(w).getReel(seconds);
        byte[] header = generateHeader(snapshot.length, events.length);

        byte[] fullReplay = new byte[snapshot.length + events.length + header.length];
        System.arraycopy(header, 0, fullReplay, 0, header.length);
        System.arraycopy(snapshot, 0, fullReplay, header.length, snapshot.length);
        System.arraycopy(events, 0, fullReplay, snapshot.length + header.length, events.length);
        return SnapshotThread.compress(fullReplay);
    }
    public static byte[] generateHeader(int sn, int en) {
        ByteArrayDataOutput bb = ByteStreams.newDataOutput();
        bb.writeByte(0x1);
        bb.writeInt(sn);
        bb.writeInt(en);
        return bb.toByteArray();
    }
}
