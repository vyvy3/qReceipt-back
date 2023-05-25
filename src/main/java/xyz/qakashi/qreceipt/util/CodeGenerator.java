package xyz.qakashi.qreceipt.util;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class CodeGenerator {
    public static String byLength(int lenght) {
        return String.valueOf(lenght < 1 ? 0 : new Random()
                .nextInt((9 * (int) Math.pow(10, lenght - 1)) - 1)
                + (int) Math.pow(10, lenght - 1));
    }
}
