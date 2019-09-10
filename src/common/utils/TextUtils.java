package common.utils;

public class TextUtils {
    public static boolean isEmpty(String str) {
        if (str == null || str.trim().length() == 0)
            return true;
        else
            return false;
    }

    public static boolean equalsIgnoreCase(String a, String b) {
        if (a == b) return true;
        if (a == null) {
            return false;
        }
        return a.equalsIgnoreCase(b);
    }

    public static boolean equals(String a, String b) {
        if (a == b) return true;
        if (a == null) {
            return false;
        }
        return a.equals(b);
    }
}
