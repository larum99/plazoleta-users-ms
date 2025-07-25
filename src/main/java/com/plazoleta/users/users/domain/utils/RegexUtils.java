package com.plazoleta.users.users.domain.utils;

import java.util.regex.Pattern;

public class RegexUtils {

    private RegexUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE
    );

    public static final Pattern PHONE_PATTERN = Pattern.compile(
            "^\\+?[0-9]{1,13}$"
    );

    public static final Pattern DOCUMENT_PATTERN = Pattern.compile(
            "^[0-9]+$"
    );
}
