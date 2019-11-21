package common;

import java.util.Map;

/**
 * 数据处理工具类
 * @author  Arios
 */
public class DataUtil {
    /**
     * 根据key，从参数集中获取值
     * @param mParams 参数集
     * @param key 关键字
     * @return 返回相应的值，不存在或者格式不对，则返回null
     */
    public static String getParam(Map<String, String> mParams, String key) {
        if (mParams == null && !mParams.containsKey(key)) {
            return null;
        }
        return mParams.get(key);
    }


    /**
     * 根据内外键值获取相应的参数，内键区分大小写
     * @param mParams 参数集
     * @param outSizeKey 外关键值，一般为FormName，区分大小写
     * @param innerKey 内关键值，参数等于号前面部份，如 "p.id=572"中的"p.id"，区分大小写
     * @return 返回相应的值，不存在或者格式不对，则返回null
     */
    public static String getInnerParam(Map<String, String> mParams, String outSizeKey, String innerKey) {
        String val = null;
        if (mParams != null && mParams.containsKey(outSizeKey)) {
            String params = mParams.get(outSizeKey);

            if (params.indexOf(innerKey) >= 0) {
                if (params.toLowerCase().indexOf("and") > 0) {
                    String[] param = params.split("and");
                    for (String paramStr : param) {
                        if (paramStr.indexOf(innerKey) >= 0) {
                            if (paramStr.indexOf("=") >= 0) {
                                val = paramStr.split("=")[1];
                            }

                        }
                    }

                } else {
                    if (params.indexOf("=") >= 0) {
                        val = params.split("=")[1];
                    }

                }
            }

        }

        if (val != null) {
            int oldFlag = val.indexOf("$");
            if (oldFlag > 0) {
                val = val.split("\\$")[0];
            }

        }
        return val;
    }

    /**
     * 根据内外键值获取相应的参数，内键不区分大小写
     * @param mParams 参数集
     * @param outSizeKey 外关键值，一般为FormName，区分大小写
     * @param innerKey 内关键值，参数等于号前面部份，如 "p.id=572"中的"p.id"，不区分大小写
     * @return 返回相应的值，不存在或者格式不对，则返回null
     */
    public static String getInnerParamIgnoreCase(Map<String, String> mParams, String outSizeKey, String innerKey) {
         String val = null;
        if (mParams != null && mParams.containsKey(outSizeKey)) {
            String params = mParams.get(outSizeKey);

            if (params.toLowerCase().indexOf(innerKey.toLowerCase()) >= 0) {
                if (params.toLowerCase().indexOf("and") > 0) {
                    String[] param = params.split("and");
                    for (String paramStr : param) {
                        if (paramStr.toLowerCase().indexOf(innerKey.toLowerCase()) >= 0) {
                            if (paramStr.indexOf("=") >= 0) {
                                val = paramStr.split("=")[1];
                            }

                        }
                    }

                } else {
                    if (params.indexOf("=") >= 0) {
                        val = params.split("=")[1];
                    }

                }
            }

        }

        if (val != null) {
            int oldFlag = val.indexOf("$");
            if (oldFlag > 0) {
                val = val.split("\\$")[0];
            }

        }
        return val;
    }


    /**
     * 查看参数集是否含有相应的内关键值，内键区分大小写
     * @param mParams 参数集
     * @param outSizeKey 外关键值，一般为FormName，区分大小写
     * @param innerKey 内关键值，参数等于号前面部份，如 "p.id=572"中的"p.id"，区分大小写
     * @return true-存在内关键字 false-不存在内关键字
     */
    public  static boolean hasInnerKey(Map<String, String> mParams, String outSizeKey, String innerKey){


        if (mParams != null && mParams.containsKey(outSizeKey)) {
            String params = mParams.get(outSizeKey);

            if (params.indexOf(innerKey) >= 0) {

              return  true;
            }
        }


        return  false;
    }

    /**
     * 查看参数集是否含有相应的内关键值，内键不区分大小写
     * @param mParams 参数集
     * @param outSizeKey 外关键值，一般为FormName，区分大小写
     * @param innerKey 内关键值，参数等于号前面部份，如 "p.id=572"中的"p.id"，不区分大小写
     * @return true-存在内关键字 false-不存在内关键字
     */
    public  static boolean hasInnerKeyIgnoreCase(Map<String, String> mParams, String outSizeKey, String innerKey){


        if (mParams != null && mParams.containsKey(outSizeKey)) {
            String params = mParams.get(outSizeKey);

            if (params.toLowerCase().indexOf(innerKey.toLowerCase()) >= 0) {

                return  true;
            }
        }


        return  false;
    }





}