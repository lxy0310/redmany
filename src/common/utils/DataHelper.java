package common.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class DataHelper {
    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                stringBuilder.append(toString(list.get(i), ""));
                if (i != list.size() - 1) {
                    stringBuilder.append(",");
                }
            }
            return stringBuilder.toString();
        }
        return toString(obj, null);
    }

    public static String toString(Object obj, String def) {
        if (obj == null) return def;
        return String.valueOf(obj);
    }

    public static String get(Map<String, Object> data, String key) {
        Object var = data.get(key);
        if (var == null) {
            return null;
        }
        return String.valueOf(var);
    }

    public static boolean toBoolean(String var) {
        return toBoolean(var, false);
    }

    public static boolean toBoolean(String var, boolean def) {
        try {
            return Integer.parseInt(var) > 0;
        } catch (Exception e) {
            return def;
        }
    }
    public static float toFloat(String var, float def) {
        try {
            return Float.parseFloat(var);
        } catch (Exception e) {
            return def;
        }
    }
    public static int toInt(String var, int def) {
        try {
            return Integer.parseInt(var);
        } catch (Exception e) {
            return def;
        }
    }

    public static int getInt(Map<String, Object> data, String key) {
        Object var = data.get(key);
        if (var == null) {
            return 0;
        }
        if (var instanceof Integer) {
            return (Integer) var;
        }
        try {
            return Integer.parseInt(String.valueOf(var));
        } catch (Exception e) {
            return 0;
        }
    }

    public static List<String> toList(String list) {
        List<String> fields = new ArrayList<>();
        if (list == null || list.trim().length() == 0) {
            return fields;
        }
        String[] fs = list.trim().split(",");
        for (String f : fs) {
            fields.add(f.trim());
        }
        return fields;
    }

    public static Map<String, String> getRequestParams(HttpServletRequest req) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> names = req.getParameterNames();
        if (names != null) {
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                map.put(name, req.getParameter(name));
            }
        }
        return map;
    }
}
