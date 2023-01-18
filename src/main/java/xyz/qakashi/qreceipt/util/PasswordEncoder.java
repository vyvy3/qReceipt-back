package xyz.qakashi.qreceipt.util;

import com.amdelamar.jhash.Hash;
import com.amdelamar.jhash.algorithms.Type;
import lombok.SneakyThrows;

public class PasswordEncoder {
    private static final String pepper = "qreceipt";
    public static String encode(String password) {
        return Hash.password(password.toCharArray()).pepper(pepper.toCharArray()).algorithm(Type.BCRYPT).create();
    }
    @SneakyThrows
    public static Boolean verifyPassword(String newPassword, String oldPassword) {
        return Hash.password(newPassword.toCharArray()).pepper(pepper.toCharArray()).algorithm(Type.BCRYPT).verify(oldPassword);
    }
}
