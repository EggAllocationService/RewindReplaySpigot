package io.github.eggallocationservice.rewindrepeat.events.types;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class PlayerDespawnEvent implements ReplayEvent<PlayerDespawnEvent>{
    public int entityId;
    @Override
    public byte[] serialize() {
        ByteArrayDataOutput bb = ByteStreams.newDataOutput(4);
        bb.writeInt(entityId);
        return bb.toByteArray();
    }

    @Override
    public PlayerDespawnEvent fromBytes(byte[] data) {
        ByteArrayDataInput bb = ByteStreams.newDataInput(data);
        entityId = bb.readInt();
        return null;
    }
}
