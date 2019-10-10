package dev.arkav.openoryx.util.logging;

public enum LogLevel {
    DEBUG((byte)0),
    PROD((byte)1);
    private final byte level;
    LogLevel(byte level) {
        this.level = level;
    }

    public byte getLevel() {
        return level;
    }
}
