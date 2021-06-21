package io.github.eggallocationservice.rewindrepeat.snapshots;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Palette {
    public ArrayList<String> ids;
    public Palette() {
        ids = new ArrayList<>();
        ids.add(0, "AIR");
    }
    public short getOrSetMapping(String id) {
        if (ids.contains(id)) {
            return (short) ids.indexOf(id);
        }
        ids.add(id);
        return (short) ids.indexOf(id);
    }
    public byte[] toBytes() {
        ByteArrayDataOutput bb = ByteStreams.newDataOutput();
        bb.writeInt(ids.size());
        for (String id: ids) {
            bb.writeUTF(id);
        }

        return bb.toByteArray();
    }

}