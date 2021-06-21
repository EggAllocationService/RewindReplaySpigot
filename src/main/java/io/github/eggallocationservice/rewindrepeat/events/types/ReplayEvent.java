package io.github.eggallocationservice.rewindrepeat.events.types;

public interface ReplayEvent<SELF> {
    byte[] serialize();
    SELF fromBytes(byte[] data);
}
