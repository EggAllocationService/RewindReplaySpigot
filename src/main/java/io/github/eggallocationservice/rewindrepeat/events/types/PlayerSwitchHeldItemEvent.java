package io.github.eggallocationservice.rewindrepeat.events.types;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class PlayerSwitchHeldItemEvent implements ReplayEvent<PlayerSwitchHeldItemEvent>{
    public int entityId;
    public String material;
    public String oldMaterial;
    @Override
    public byte[] serialize() {
        ByteArrayDataOutput bb = ByteStreams.newDataOutput();
        bb.writeInt(entityId);
        bb.writeUTF(material);
        bb.writeUTF(oldMaterial);
        return bb.toByteArray();
    }

    @Override
    public PlayerSwitchHeldItemEvent fromBytes(byte[] data) {
        ByteArrayDataInput bb = ByteStreams.newDataInput(data);
        entityId = bb.readInt();
        material = bb.readUTF();
        oldMaterial = bb.readUTF();
        return this;
    }
}
