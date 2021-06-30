package io.github.eggallocationservice.rewindrepeat.events.types;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Location;

import java.util.ArrayList;

public class ExplosionEvent implements ReplayEvent<ExplosionEvent>{
    public ArrayList<int[]> toBreak;
    public int x;
    public int y;
    public int z;
    @Override
    public byte[] serialize() {
        ByteArrayDataOutput bb = ByteStreams.newDataOutput();
        bb.writeInt(x);
        bb.writeInt(y);
        bb.writeInt(z);
        bb.writeInt(toBreak.size());
        for (int[] l : toBreak) {
            encodeLocation(l, bb);
        }
        return bb.toByteArray();
    }
    private void encodeLocation(int[] l, ByteArrayDataOutput bb) {
        bb.writeInt(l[0]);
        bb.writeInt(l[1]);
        bb.writeInt(l[2]);
    }

    @Override
    public ExplosionEvent fromBytes(byte[] data) {
        ByteArrayDataInput bb = ByteStreams.newDataInput(data);
        x = bb.readInt();
        y = bb.readInt();
        z = bb.readInt();
        int count = bb.readInt();
        toBreak = new ArrayList<>();
        for (int i =0; i < count; i++) {
            int[] loc = new int[3];
            loc[0] = bb.readInt();
            loc[1] = bb.readInt();
            loc[2] = bb.readInt();
            toBreak.add(loc);
        }
        return this;
    }
}
