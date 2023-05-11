package xyz.qakashi.qreceipt.util;

import lombok.experimental.UtilityClass;

import java.time.ZonedDateTime;

@UtilityClass
public class Utils {
    public static ZonedDateTime currentTime() {
        return ZonedDateTime.now();
    }
}
