package boodater.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class StringUtil {
    public static String removeAllBefore(String s, String remove) {
        if (s == null || remove == null)
            throw new IllegalArgumentException();

        int pos = s.indexOf(remove);
        if (pos > -1)
            return s.substring(pos + remove.length(), s.length());
        return s;

    }

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        int size = str.length();
        for (int i = 0; i < size; i++) {
            char c = str.charAt(i);
            if (c != ' ' && ((int) c) != 160) { //160 is non-breaking space (&nbsp;)
                return false;
            }
        }

        return true;
    }

    public static String encodeUrl(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        }
        catch (UnsupportedEncodingException uee) {
            throw new IllegalArgumentException(uee);
        }
    }
}
