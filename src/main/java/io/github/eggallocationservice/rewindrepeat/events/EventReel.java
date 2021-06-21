package io.github.eggallocationservice.rewindrepeat.events;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.github.eggallocationservice.rewindrepeat.Config;
import io.github.eggallocationservice.rewindrepeat.events.types.ReplayEvent;

import java.util.ArrayList;

public class EventReel {
    public EventCollection currentTick = new EventCollection();
    public ArrayList<EventCollection> history = new ArrayList<>();
    public void add(ReplayEvent e) {
        currentTick.events.add(e);
    }
    public void tick() {
        if (history.size() == Config.BUFFER_SIZE_SECONDS * 20) {
            history.remove(0);
        }
        history.add(currentTick);
        currentTick = new EventCollection();

    }
    public byte[] getReel(int seconds) {
        /*
            format for event reels:
            int - count of ticks
            array of tick:
                int eventCount
                array of events:
                    String - Event Class Name
                    int - length of event data
                    byte[] - event data
         */
        EventCollection[] events = new EventCollection[seconds * 20];
        System.arraycopy(history.toArray(new EventCollection[0]), history.size() - (seconds * 20), events, 0, events.length);
        ByteArrayDataOutput bb = ByteStreams.newDataOutput();
        bb.writeInt(events.length);
        for (EventCollection c : events) {
            bb.write(c.encode());
        }
        return bb.toByteArray();
    }
}
