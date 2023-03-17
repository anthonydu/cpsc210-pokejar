package model;

import java.util.Collections;

/**
 * A static class containing utility functions for handling Strings.
 *
 * @auther Anthony Du
 */
public final class StringUtil {
    private StringUtil() {}

    public static String fixCharCount(String str, int charCount) {
        if (str.length() > charCount) {
            return str.substring(0, charCount - 1) + "…";
        } else {
            return str + String.join("", Collections.nCopies(charCount - str.length(), " "));
        }
    }
}
