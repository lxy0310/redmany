package servlet;

import common.APPConfig;
import common.utils.CookieHelper;
import common.utils.HttpUtils;
import common.utils.SafeString;
import common.utils.TextUtils;
import viewtype.DefaultDataProvider;
import viewtype.ParentView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BaseServlet extends HttpServlet {
    private HttpServletResponse mResponse;
    private HttpServletRequest request;

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return mResponse;
    }

    protected abstract void doHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    public String getCompany_Id() {
        String Company_Id = getParameter("Company_Id");
        if (!TextUtils.isEmpty(Company_Id)) {
            return Company_Id;
        }
        return APPConfig.COMPANYID;
    }

    @Override
    public   void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    public   void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        request = req;
        mResponse = resp;
        //初始化默认路径
        if (DefaultDataProvider.DIR_IMAGES == null) {
            String images = req.getServletContext().getRealPath("images");
            DefaultDataProvider.DIR_IMAGES = images;
            System.out.println(images);
        }
        if (ParentView.IMAGE_UPLOAD == null) {
            String upload = req.getServletContext().getRealPath("upload");
            ParentView.IMAGE_UPLOAD = upload;
            System.out.println(upload);
        }
        doHtml(req, resp);
    }

    public String urlEncode(String str) {
        return SafeString.urlEncode(str);
    }

    public String urlDecode(String str) {
        return SafeString.urlDecode(str);
    }

    /**
     * url参数
     *
     * @param key
     */
    public String getParameter(String key) {
        return getRequest().getParameter(key);
    }

    public String getCookieValue(String name) {
        return CookieHelper.getCookieValue(getRequest(), name);
    }

    /**
     * 客户端ip
     */
    public String getRemortIP() {
        return getRemortIP(getRequest());
    }

    /**
     * 客户端ip
     */
    public static String getRemortIP(HttpServletRequest request) {
        if (TextUtils.isEmpty(request.getHeader("x-forwarded-for"))) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    protected void saveCookie(String key, String value, boolean temp) {
        CookieHelper.saveCookie(getResponse(), key, value, temp);
    }

    protected String openUrl(String url) {
        return HttpUtils.get(url);
    }
}
