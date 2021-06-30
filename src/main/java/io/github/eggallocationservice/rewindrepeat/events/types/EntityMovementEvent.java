package io.github.eggallocationservice.rewindrepeat.events.types;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class EntityMovementEvent implements ReplayEvent<EntityMovementEvent> {
    public int entityId;
    public double x;
    public double y;
    public double z;
    public double pitch;
    public double yaw;
    public boolean hasFrom;
    public double fx;
    public double fy;
    public double fz;
    public double fpitch;
    public double fyaw;
    @Override
    public byte[] serialize() {
        ByteArrayDataOutput bb = ByteStreams.newDataOutput();
        bb.writeInt(entityId);
        bb.writeDouble(x);
        bb.writeDouble(y);
        bb.writeDouble(z);
        bb.writeDouble(pitch);
        bb.writeDouble(yaw);
        bb.writeBoolean(hasFrom);
        if (hasFrom) {
            bb.writeDouble(fx);
            bb.writeDouble(fy);
            bb.writeDouble(fz);
            bb.writeDouble(fpitch);
            bb.writeDouble(fyaw);
        }
        return bb.toByteArray();
    }

    @Override
    public EntityMovementEvent fromBytes(byte[] data) {
        ByteArrayDataInput bb = ByteStreams.newDataInput(data);
        entityId = bb.readInt();
        x = bb.readDouble();
        y = bb.readDouble();
        z = bb.readDouble();
        pitch = bb.readDouble();
        yaw = bb.readDouble();
        return this;
    }
}
