package common;

import common.utils.HttpUtils;
import common.utils.SafeString;
import model.*;
import page.Page;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiParser {

    public static List<CouponInfo> getCouponInfos(int id) {
        String api = CommonUtils.GetUrl3+"language="+CommonUtils.language+"&interactiveLatitude=&condition=&interactiveAreaId=&Company_Id=" + Page.COMPANYID +
                "&formName=MyVoucherPage&interactiveLongitude=&startRowNo=&rowCount=&userid=" + id + "&indistinctSearch=&updateType=all";
        return ApiParser.parseList(HttpUtils.get(api), CouponInfo.class);
    }

    public static String getLoginUrl(String name, String pwd) {
        return CommonUtils.userLogin+
                "language="+CommonUtils.language+"&AppName=&IMIE=866940027337724&appcode=87&username=" + name + "&password=" +
                pwd + "&company_id=" + Page.COMPANYID;
    }

    public static String getBackLogin(String name, String pwd,String CompanyId){
        return  CommonUtils.userLogin +
                "language="+CommonUtils.language+"&AppName=&IMIE=866940027337724&appcode=87&username=" + name + "&password=" +
                pwd + "&company_id=" + CompanyId;
    }



    public static String getShopCarUrl(int userId, String search) {
        return CommonUtils.GetUrl3 + "language="+CommonUtils.language+"&Company_Id=" + Page.COMPANYID + "&userid=" + userId + "&formName=shoppingCart" +
                "&updateType=all&startRowNo=&rowCount=&interactiveLatitude=23.137658&interactiveLongitude=113.351191&interactiveAreaId=";
//fasf
    }

    /**
     * 商店的标语
     *
     * @param shopId
     */
    public static List<ShopTextInfo> getShopTexts(int shopId) {
        String api = CommonUtils.GetUrl3 +
                "language="+CommonUtils.language+"&interactiveLatitude=&condition=" + SafeString.escape("'shopID =" + shopId + "'")
                + "&Company_Id=" + Page.COMPANYID + "&rowCount=" +
                "&indistinctSearch=&formName=Safeguard&interactiveAreaId=&updateType=all&startRowNo=&userid=-1&interactiveLongitude=";
        return parseList(HttpUtils.get(api), ShopTextInfo.class);
    }

    public static List<CarInfo> getCarInfos(int id,String Company_Id,String cId) {
        String api =CommonUtils.GetUrl3 +"condition=" +
                SafeString.escape("'ci.Id in(" + cId + ")'") + "&language="+CommonUtils.language+"&Company_Id=" + Company_Id +
                "&formName=orderConfirm&startRowNo=&rowCount=&userid=" + id + "&updateType=all";
        System.out.println("==========getCarInfos============"+api);
        return ApiParser.parseList(HttpUtils.get(api), CarInfo.class);
    }

    public static List<ReceivingAddressInfo> getRaddressInfos(int id,String Company_Id) {
        String api = CommonUtils.GetUrl3+"Company_Id="+Company_Id+"&userid="+id+"&language="+CommonUtils.language+"&formName=Delivery_address&startRowNo=&rowCount=&condition='Id=3'&updateType=all";
        System.out.println("==========getRaddressInfos============"+api);
        return ApiParser.parseList(HttpUtils.get(api), ReceivingAddressInfo.class);
    }



    public static List<AddressInfo> getAddressInfos(int id) {
        String api =  CommonUtils.GetUrl3+"condition=" +
                SafeString.escape("'uId=" + id + "'") + "&language="+CommonUtils.language+"&interactiveAreaId=&Company_Id=" + Page.COMPANYID +
                "&formName=AddressPage&startRowNo=&rowCount=&userid=" + id + "&indistinctSearch=&updateType=all";
        return ApiParser.parseList(HttpUtils.get(api), AddressInfo.class);
    }

    public static String getOrderListUrl(String condition, int userId) {
        if (condition == null) {
            condition = "";
        }
        return  CommonUtils.GetUrl3+"interactiveLatitude=&condition=" +
                SafeString.escape(condition) + "&language="+CommonUtils.language+"&interactiveAreaId=&Company_Id=" + Page.COMPANYID + "&formName=orderAffirm&interactiveLongitude=&startRowNo=&rowCount=&userid=" + userId + "&indistinctSearch=&updateType=all";
    }



    //region prease

    public static String getResultId(String rs) {
        if (rs == null) return null;
        try {
            int i = rs.indexOf("[");
            int j = rs.indexOf("]", i);
            if (i >= 0 && j >= 0 && j > i) {
                return rs.substring(i + 1, j);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static <T> List<T> parseList(String result, Class<T> pClass) {
        List<Map<String, String>> lines = parse(result);

        List<T> tList = new ArrayList<>();
        for (Map<String, String> line : lines) {
            T t = null;
            try {
                t = pClass.newInstance();
            } catch (Exception pE) {
                break;
            }
            fill(t, line);
            tList.add(t);
        }
        return tList;

    }

    private static void fill(Object obj, Map<String, String> map) {
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            boolean a = field.isAccessible();
            String name = field.getName();
            String val = map.get(name);
            if (val != null) {
                field.setAccessible(true);
                //
                try {
                    field.set(obj, toObject(val, field.getType()));
                } catch (Exception e) {
                    //
                }
                field.setAccessible(a);
            }

        }
    }

    private static Object toObject(String str, Class<?> pTClass) {
        Class<?> type = wrapper(pTClass);
        if (Boolean.class == type) {
            str = str.trim();
            return "true".equalsIgnoreCase(str) || "1".equals(str);
        } else if (Integer.class == type) {
            return (char) Integer.parseInt(str);
        } else if (Long.class == type) {
            return Long.parseLong(str);
        } else if (Short.class == type) {
            return (short) Integer.parseInt(str);
        } else if (Byte.class == type) {
            return (byte) Integer.parseInt(str);
        } else if (Double.class == type) {
            return Double.parseDouble(str);
        } else if (Float.class == type) {
            return Float.parseFloat(str);
        } else if (Character.class == type) {
            return (char) Integer.parseInt(str);
        } else if (String.class == type) {
            return String.valueOf(str);
        }
        return null;
    }

    private static Class<?> wrapper(Class<?> type) {
        if (type == null) {
            return null;
        } else if (type.isPrimitive()) {
            if (boolean.class == type) {
                return Boolean.class;
            } else if (int.class == type) {
                return Integer.class;
            } else if (long.class == type) {
                return Long.class;
            } else if (short.class == type) {
                return Short.class;
            } else if (byte.class == type) {
                return Byte.class;
            } else if (double.class == type) {
                return Double.class;
            } else if (float.class == type) {
                return Float.class;
            } else if (char.class == type) {
                return Character.class;
            } else if (void.class == type) {
                return Void.class;
            }
        }

        return type;
    }


    public static List<Map<String, String>> parse(String result) {
        List<Map<String, String>> lines = new ArrayList<>();
        if (result != null) {
            String[] _ws = result.split("╚");
            if (_ws.length <= 1) {
                return lines;
            }
            String linesStr = _ws[0];
            String lineHead = _ws[1];

            String[] heads = lineHead.split(",");
            for (int i = 0; i < heads.length; i++) {
                heads[i] = heads[i].trim();
            }
            _ws = linesStr.split("╝");
            for (String lineStr : _ws) {
                if (lineStr.trim().length() == 0) {
                    continue;
                }
                String[] ws = lineStr.split("&");
                Map<String, String> line = new HashMap<>();
                for (int i = 0; i < ws.length; i++) {
                    line.put(heads[i], ws[i]);
                }
                lines.add(line);
            }
        }
        return lines;
    }
//endregion
}
