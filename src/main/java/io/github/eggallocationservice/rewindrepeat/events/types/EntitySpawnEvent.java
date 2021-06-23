package io.github.eggallocationservice.rewindrepeat.events.types;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class EntitySpawnEvent implements ReplayEvent<EntitySpawnEvent> {

    public int entityId;
    public double x;
    public double y;
    public double z;
    public double pitch;
    public double yaw;
    public String type;
    public double sx;
    public double sy;
    public double sz;
    @Override
    public byte[] serialize() {
        ByteArrayDataOutput bb = ByteStreams.newDataOutput();
        bb.writeInt(entityId);
        bb.writeUTF(type);
        bb.writeDouble(x);
        bb.writeDouble(y);
        bb.writeDouble(z);
        bb.writeDouble(pitch);
        bb.writeDouble(yaw);
        bb.writeDouble(sx);
        bb.writeDouble(sy);
        bb.writeDouble(sz);
        return bb.toByteArray();
    }

    @Override
    public EntitySpawnEvent fromBytes(byte[] data) {
        ByteArrayDataInput bb = ByteStreams.newDataInput(data);
        entityId = bb.readInt();
        type = bb.readUTF();
        x = bb.readDouble();
        y = bb.readDouble();
        z = bb.readDouble();
        pitch = bb.readDouble();
        yaw = bb.readDouble();
        return this;
    }
}
