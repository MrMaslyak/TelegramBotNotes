package org.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeValidator {

    private static final String TIME_PATTERN = "^([01]?\\d|2[0-3]):([0-5]\\d)$";
    private final Pattern pattern;

    public TimeValidator() {
        pattern = Pattern.compile(TIME_PATTERN);
    }

    public boolean validate(final String time) {
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }
}