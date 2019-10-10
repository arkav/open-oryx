package dev.arkav.openoryx.util.logging;

import dev.arkav.openoryx.Environment;

public class Logger {
    public static void log(String sender, String object, LogLevel level) {
        if (level.getLevel() >= Environment.MIN_LOG_LEVEL.getLevel()) {
            log(sender, object);
        }
    }

    public static void log(String sender, String object) {
        System.out.println("[" + sender + "]: " + object);
    }
}
