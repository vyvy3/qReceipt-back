package xyz.qakashi.qreceipt.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String DATABASE_PREFIX = "qreceipt_";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_PREFIX = "bearer";
    public static final String PUBLIC_API_ENDPOINT = "/public";
    public static final String PRIVATE_API_ENDPOINT = "/private";
    public static final String ADMIN_API_ENDPOINT = "/admin";
    public static final String API_USER_API_ENDPOINT = "/api";
    public static final String FILE_UPLOAD_FOLDER = "/files/";

    public static Integer CODE_LIFETIME_IN_SECONDS = 300;
    public static Integer CODE_BLOCK_TIME_IN_MINUTES = 5;
    public static Integer CODE_DEFAULT_LENGTH = 6;
    public static Integer CODE_MAX_ATTEMPT_COUNT = 5;

    public static Integer PROFILE_PAGE_ANALYTICS_NUMBER_OF_MONTHS = 6;
    public static Integer ANALYTICS_PER_CATEGORY_NUMBER_OF_DAYS = 30;
}
