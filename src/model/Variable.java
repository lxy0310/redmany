package model;



import java.util.Map;

/**
 * Created by Suhaibo on 2018/3/1.
 */
public class Variable {
    public static String type="";
    public static Map<String, String> mParam = null;
    public static Map<String, String> globalVariable = null;

    public static Map<String, String> getGlobalVariable() {
        return globalVariable;
    }
}
