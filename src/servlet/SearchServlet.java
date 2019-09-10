package servlet;

import common.APPConfig;
import common.BaiduLocation;
import common.CommonUtils;
import common.utils.DataHelper;
import common.utils.HttpUtils;
import common.utils.SafeString;
import common.utils.TextUtils;
import page.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SearchServlet extends BaseServlet {
    @Override
    protected void doHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String key = EscapeUnescape.unescape(req.getParameter("key"));
        req.setCharacterEncoding("UTF-8");
        String delete = req.getParameter("delete");
        String user = req.getParameter("user");
        if (!TextUtils.isEmpty(delete)) {
            //删除搜索历史
            String api = CommonUtils.SumbitUrl+ "Company_Id=" + Page.COMPANYID + "&userid=" + user +
                    "&formName=deleteSelectMidForm&showType=newForm&interactiveLatitude=&interactiveLongitude=" +
                    "&interactiveAreaId=&uId=" + user + "&id=-1511509211094";
            String rs = HttpUtils.get(api);
            System.err.println(rs);
            resp.setContentType("text/json;charset=utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.getWriter().println(rs);
        } else if ("china".equals(req.getParameter("type"))) {
            String city = getCookieValue(Page.LOC_CITY);
            if (TextUtils.isEmpty(city)) {
                BaiduLocation.LocationData data = BaiduLocation.getLocationByIp(getRemortIP());
                BaiduLocation.saveLocation(data, resp);
                city = data.getCity();
            }
            //搜索小区
            String key = DataHelper.toString(req.getParameter("key"), "");
            String tag = DataHelper.toString(req.getParameter("tag"), "");
            String api = "http://api.map.baidu.com/place/v2/search?query=" + urlEncode(key) + "&tag=" + SafeString.escape(tag)
                    + "&city_limit=true&region=" + urlEncode(city)
                    + "&output=json&ak=" + APPConfig.BAIDU_KEY;
            String rs = openUrl(api);
            System.err.println(SafeString.unescape(api));
            System.err.println(rs);
            resp.setContentType("text/json;charset=utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.getWriter().println(rs);
        } else if ("area".equals(req.getParameter("type"))) {
            //搜索小区
            String loc = getCookieValue(Page.LOC_POS);
            if (TextUtils.isEmpty(loc)) {
                BaiduLocation.LocationData data = BaiduLocation.getLocationByIp(getRemortIP());
                BaiduLocation.saveLocation(data, resp);
                loc = data.getLatLon();
            }
            String key = DataHelper.toString(req.getParameter("key"), "");
            String radius = "";
            String tag = DataHelper.toString(req.getParameter("tag"), "");
            String api = "http://api.map.baidu.com/place/v2/search?query=" + urlEncode(key)
                    + "&scope=1&location=" + loc + "&output=json&ak=" + APPConfig.BAIDU_KEY;
            if (!TextUtils.isEmpty(radius)) {
                api += "&radius_limit=true&radius=" + radius;
            }
//            if (!TextUtils.isEmpty(tag)) {
//                api += "&tag=" + EscapeUnescape.escape(tag);
//            }
            String rs = openUrl(api);
            System.err.println("key=" + key + ",loc=" + loc);
            System.err.println(rs);
            resp.setContentType("text/json;charset=utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.getWriter().println(rs);
        } else if ("suggestion".equals(req.getParameter("type"))) {
            String city = SafeString.unescape(getCookieValue(Page.LOC_CITY));
            if (TextUtils.isEmpty(city)) {
                BaiduLocation.LocationData data = BaiduLocation.getLocationByIp(getRemortIP());
                BaiduLocation.saveLocation(data, resp);
                city = data.getCity();
            }
            String key = DataHelper.toString(req.getParameter("key"), "");
            String api = "http://api.map.baidu.com/place/v2/suggestion?query=" + urlEncode(key) + "&region=" +
                    urlEncode(city) + "&city_limit=true&output=json&ak=" + APPConfig.BAIDU_KEY;
            String rs = openUrl(api);
            System.err.println("key=" + key + ",city=" + city);
            System.err.println(rs);
            resp.setContentType("text/json;charset=utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.getWriter().println(rs);
        }
    }
}
