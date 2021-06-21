package io.github.eggallocationservice.rewindrepeat.events.types;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class PlayerPlaceBlockEvent implements ReplayEvent<PlayerPlaceBlockEvent> {
    public int entityId;
    public int x;
    public int y;
    public int z;
    public String material;

    @Override
    public byte[] serialize() {
        ByteArrayDataOutput bb = ByteStreams.newDataOutput();
        bb.writeInt(entityId);
        bb.writeInt(x);
        bb.writeInt(y);
        bb.writeInt(z);
        bb.writeUTF(material);
        return bb.toByteArray();
    }

    @Override
    public PlayerPlaceBlockEvent fromBytes(byte[] data) {
        ByteArrayDataInput bb = ByteStreams.newDataInput(data);
        entityId = bb.readInt();
        x = bb.readInt();
        y = bb.readInt();
        z = bb.readInt();
        material = bb.readUTF();
        return this;
    }
}
