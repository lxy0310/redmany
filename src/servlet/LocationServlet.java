package servlet;

import common.BaiduLocation;
import common.utils.SafeString;
import common.utils.TextUtils;
import page.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LocationServlet extends BaseServlet {

    @Override
    protected void doHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        //http://lbsyun.baidu.com/index.php?title=webapi/ip-api
        if (!TextUtils.isEmpty(req.getParameter("save"))) {
            //保存地址，然后返回首页
            String lat = req.getParameter("lat");
            String lon = req.getParameter("lon");
            String name = req.getParameter("name");
            String address = req.getParameter("address");
            System.err.println("lat=" + lat + ",lon=" + lon + ",name=" + name + ",address=" + address);
            saveCookie(Page.LOC_POS, lat + "," + lon, false);
            saveCookie(Page.LOC_ADDRESS_SHORT,
                    name == null ? SafeString.escape(address) : SafeString.escape(name), false);
            saveCookie(Page.LOC_ADDRESS, SafeString.escape(address), false);
            resp.setContentType("text/json;charset=utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.getWriter().println("success");
//            resp.sendRedirect(Page.getHomeUrl("Service_mainPage", "copForm"));
            return;
        }
        String lat = req.getParameter("lat");
        String lon = req.getParameter("lon");
        BaiduLocation.LocationData data;
        if (BaiduLocation.checkPos(lat, lon)) {
            data = BaiduLocation.getLocationByPos(lat, lon);
        } else {
            data = BaiduLocation.getLocationByIp(getRemortIP(req));
        }
//        try {
//            jsonObject.put("statu", data.statu);
//            jsonObject.put("province", data.getProvince());
//            jsonObject.put("street", data.getStreet() + data.getStreet_number());
//            jsonObject.put("address", data.getAddress());
//            jsonObject.put("city", data.getCity());
//            jsonObject.put("lat", lat);
//            jsonObject.put("lon", lon);
//        } catch (JSONException pE) {
//            pE.printStackTrace();
//        }
        BaiduLocation.saveLocation(data, resp);
        resp.setContentType("text/json;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().println(BaiduLocation.sGson.toJson(data));
    }

}
