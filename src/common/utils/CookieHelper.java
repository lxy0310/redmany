package common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by cxy on 2017/6/13.
 */
public class CookieHelper {

    public static String getCookieValue(HttpServletRequest req, String name) {
        Cookie cookie = getCookie(req, name);
        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }

    public static Cookie getCookie(HttpServletRequest req, String name) {
        if(req==null)return null;
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (name.equalsIgnoreCase(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }

    /**
     * @param resp
     * @param key
     * @param value
     * @param temp  关闭浏览器自动清空
     */
    public static void saveCookie(HttpServletResponse resp, String key, String value, boolean temp) {
        Cookie cookie = new Cookie(key, value);
        if (!temp) {
            cookie.setMaxAge(24 * 3600 * 7);
        }
        resp.addCookie(cookie);
    }

//    /**
//     * 设置Cookie
//     * @param resp
//     * @param name Cookie名字
//     * @param value Cookie值
//     * @param maxAge Cookie生命周期 （秒）
//     */
//    public static void addCookie(HttpServletResponse resp, String name, String value, int maxAge) throws UnsupportedEncodingException{
//        value = URLEncoder.encode(value,"UTF-8");
//        Cookie cookie = new Cookie(name,value);
//        cookie.setPath("/");
//        if(maxAge>0){
//            cookie.setMaxAge(maxAge);
//        }
//        resp.addCookie(cookie);
//    }

//    /**
//     * 获取Cookie值
//     * @param req
//     * @param name
//     * @return
//     * @throws UnsupportedEncodingException
//     */
//    public static String getCookieValue(HttpServletRequest req,String name) throws UnsupportedEncodingException {
//        Map<String,Cookie> cookieMap = ReadCookieMap(req);
//        if(cookieMap.containsKey(name)){
//            Cookie cookie = cookieMap.get(name);
//            String coo = cookie.getValue();
//            coo = URLDecoder.decode(coo,"UTF-8");
//            return coo;
//        }else{
//            return null;
//        }
//    }

//    /**
//     * 将Cookie封装到Map中
//     * @param req
//     * @return CookieMap
//     */
//    public static Map<String,Cookie> ReadCookieMap(HttpServletRequest req){
//        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
//        Cookie[] cookies = req.getCookies();
//        if(null != cookies){
//            for(Cookie cookie : cookies){
//                cookieMap.put(cookie.getName(),cookie);
//            }
//        }
//        return cookieMap;
//    }
}
