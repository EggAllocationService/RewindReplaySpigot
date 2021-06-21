package io.github.eggallocationservice.rewindrepeat.events;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.github.eggallocationservice.rewindrepeat.events.types.ReplayEvent;

import java.util.ArrayList;

public class EventCollection {
    public ArrayList<ReplayEvent> events = new ArrayList<>();
    public byte[] encode() {
        ByteArrayDataOutput bb = ByteStreams.newDataOutput();
        bb.writeInt(events.size());
        for(ReplayEvent e : events) {
            bb.writeUTF(e.getClass().getSimpleName());
            byte[] d = e.serialize();
            bb.writeInt(d.length);
            bb.write(d);
        }
        return bb.toByteArray();
    }
}
