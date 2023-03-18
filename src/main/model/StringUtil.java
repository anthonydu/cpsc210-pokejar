package model;

import java.util.Collections;

/**
 * A static class containing utility functions for handling Strings.
 *
 * @author Anthony Du
 */
public final class StringUtil {
    private StringUtil() {}

    /**
     * Truncates a string to a specified number of characters.
     * And adds an ellipsis if the String's length exceeds charCount.
     *
     * @param str the string to truncate
     * @param charCount the max number of characters to return
     * @return a truncated string that does not exceed charCount (even after adding the ellipsis)
     */
    public static String fixCharCount(String str, int charCount) {
        if (str.length() > charCount) {
            return str.substring(0, charCount - 1) + "…";
        } else {
            return str + String.join("", Collections.nCopies(charCount - str.length(), " "));
        }
    }
}
