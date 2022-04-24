package xyz.qakashi.qrecipient.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessage {
    public static String userWithLoginExists(String login) {
        return "User with username=" + login + " already exists";
    }

    public static String userNotFoundByUsername(String username) {
        return "Cannot find user with username=" + username;
    }

    public static String incorrectPassword() {
        return "Incorrect password";
    }

    public static String userNotVerified() {
        return "User email is not verified";
    }

}
