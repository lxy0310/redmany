package servlet;

import common.AccountHelper;
import common.ApiParser;
import common.SQLHelper;
import common.utils.CookieHelper;
import common.utils.SafeString;
import common.utils.TextUtils;
import page.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends BaseServlet {

    @Override
    protected void doHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("1".equalsIgnoreCase(req.getParameter("out"))) {
            //读取最后的链接
            AccountHelper accountHelper = new AccountHelper(req, resp);
            accountHelper.logout();
            resp.sendRedirect(Page.getLoginPage());
           // resp.sendRedirect("queryStudentServlet");
            return;
        }
        String autologing = req.getParameter("autologin");
        if (autologing != null && autologing.length() > 0) {
            //自动登录
            //读取最后的链接
            AccountHelper accountHelper = new AccountHelper(req, resp);
            String gotoUrl = SafeString.unescape(req.getParameter("goto"));
            String transferParams = req.getParameter("transferParams");
            String targetUrl = null;



            if (gotoUrl != null) {
                if (gotoUrl.contains(":")) {
                    gotoUrl = gotoUrl.split(":")[1];
                }
                String[] ws = gotoUrl.split(",");
                targetUrl = Page.getHomeUrl(ws[0], ws[1]);

                if (transferParams != null && transferParams.length() > 0) {
                    targetUrl += "&transferParams=" + transferParams;
                }
            }

            if (accountHelper.autoLogin()) {
                if (targetUrl != null) {
                    System.err.println("targetUrl=" + targetUrl);
                    resp.sendRedirect(targetUrl);
                } else {
                    resp.sendRedirect("queryStudentServlet");
                }
            } else {
                //手动登录
                System.err.println("targetUrl=" + targetUrl);
                //关闭浏览就过时
                CookieHelper.saveCookie(resp, Page.Settings.TARGETURL, SafeString.encode(targetUrl), true);
                resp.sendRedirect(Page.getLoginPage());
            }
            return;
//        }else{
//            String targetUrl = req.getParameter("url");
//            resp.addCookie(new Cookie(Page.Settings.TARGETURL, targetUrl));
//            resp.sendRedirect(Page.getLoginPage());
//            return;
        }
        String name = req.getParameter("username");
        if (name == null) {
            name = req.getParameter("userName");
        }
        String pwd = req.getParameter("password");
        if (pwd == null) {
            pwd = req.getParameter("passWord");
        }
        if ("phone".equals(req.getParameter("type"))) {
            //手机号登录
        } else {
            String reme = req.getParameter("reme");
            String result = openUrl(ApiParser.getLoginUrl(name, pwd));
            System.err.println("login:"+result);
            if (result.startsWith("fail")) {
                //302到登录页面 queryStudentServlet?copformName=loginPage&showType=listForm
                resp.sendRedirect(Page.getLoginPage());
            } else {
                //登录ok
                //320到首页 queryStudentServlet
                //读取最后的链接
                AccountHelper accountHelper = new AccountHelper(req, resp);
                if (result!=null){
                    SQLHelper sqlHelper = new SQLHelper(req);
                    //BackMarDao backmar=new BackMarDao(sqlHelper);
                    String[] ws = result.split("&");
                    int userId = Integer.parseInt(ws[0]);
                    int roleId = ws.length > 3 ? Integer.parseInt(ws[3]) : 0;
                    HttpSession session=req.getSession();
                    session.setAttribute("userId",userId);
                    session.setAttribute("roleId",roleId);
                   /* Integer DeptId = backmar.getDeptIdByUserId(getCompany_Id(),userId);
                    if (DeptId!=null){
                        result += "&" + DeptId;
                        session.setAttribute("DeptId",DeptId);
                    }*/
                    String Ip=req.getRemoteAddr();
                    result += "&" + Ip;
                }
                if (accountHelper.onLogin(result, pwd, "true".equals(reme))) {
                    String targetUrl = CookieHelper.getCookieValue(req, Page.Settings.TARGETURL);
                    if (!TextUtils.isEmpty(targetUrl)) {
                        targetUrl = SafeString.decode(targetUrl);
                        CookieHelper.saveCookie(resp, Page.Settings.TARGETURL, null, true);
                        System.err.println("LoginServlet targetUrl=" + targetUrl);
                        resp.sendRedirect(targetUrl);
                    } else {
//                    Enumeration<String> names = req.getParameterNames();
//                    while (names.hasMoreElements()) {
//                        System.err.println("names=" + names.nextElement());
//                    }
                        resp.sendRedirect("queryStudentServlet");
                    }
                } else {
                    CookieHelper.saveCookie(resp, "login_fail", "1", true);
                    resp.sendRedirect(Page.getLoginPage());
                }
            }
        }
    }
}
