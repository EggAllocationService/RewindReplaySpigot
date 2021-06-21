package io.github.eggallocationservice.rewindrepeat.snapshots;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.util.ArrayList;
import java.util.HashMap;

public class Snapshot {
    public HashMap<String, byte[]> chunks;
    public ArrayList<SEntityInfo> entities;

    public Snapshot(int chunksSize, int entitiesSize) {
        entities = new ArrayList<>(entitiesSize);
        chunks = new HashMap<>(chunksSize);
    }
    public byte[] encode() {
        // snapshot format:
        // int - size of following array
        // Array of:
            // String - chunk position
            // int - size of byte array
            // byte[] - raw packet data for that chunk in the replay.
        // int - size of following array
        // Array Of:
            // int - entity id
            // boolean - has custom name?
                // if non-0: UTF string with name
            // String - Entity Type
            // double x
            // double y
            // double z
            // double pitch
            // double yaw

        ByteArrayDataOutput bb = ByteStreams.newDataOutput();
        bb.writeInt(chunks.size());
        for (String s : chunks.keySet()) {
            bb.writeUTF(s);
            bb.writeInt(chunks.get(s).length);
            bb.write(chunks.get(s));
        }
        bb.writeInt(entities.size());
        for (SEntityInfo e : entities) {
            bb.writeInt(e.id);
            if (e.name.equals("")) {
                bb.writeByte(0x0);
            } else {
                bb.writeByte(0x1);
                bb.writeUTF(e.name);
            }
            bb.writeUTF(e.type);
            bb.writeDouble(e.x);
            bb.writeDouble(e.y);
            bb.writeDouble(e.z);
            bb.writeDouble(e.pitch);
            bb.writeDouble(e.yaw);
        }
        return bb.toByteArray();
    }
}
