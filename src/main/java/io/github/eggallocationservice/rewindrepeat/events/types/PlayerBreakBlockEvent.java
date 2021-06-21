package io.github.eggallocationservice.rewindrepeat.events.types;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class PlayerBreakBlockEvent implements ReplayEvent<PlayerBreakBlockEvent> {
    public int entityId;
    public int x;
    public int y;
    public int z;
    public String oldMaterial;

    @Override
    public byte[] serialize() {
        ByteArrayDataOutput bb = ByteStreams.newDataOutput();
        bb.writeInt(entityId);
        bb.writeInt(x);
        bb.writeInt(y);
        bb.writeInt(z);
        bb.writeUTF(oldMaterial);
        return bb.toByteArray();
    }

    @Override
    public PlayerBreakBlockEvent fromBytes(byte[] data) {
        ByteArrayDataInput bb = ByteStreams.newDataInput(data);
        entityId = bb.readInt();
        x = bb.readInt();
        y = bb.readInt();
        z = bb.readInt();
        oldMaterial = bb.readUTF();
        return this;
    }
}
