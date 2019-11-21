package common;

import common.utils.*;
import page.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class AccountHelper {

    private HttpServletRequest req;
    private HttpServletResponse resp;

    public AccountHelper(HttpServletRequest pReq, HttpServletResponse pResp) {
        req = pReq;
        resp = pResp;
    }

    public boolean isLogin() {
        return getUserId() > 0;
    }

    public int getUserId() {
        return DataHelper.toInt(CookieHelper.getCookieValue(req, Page.Settings.USERID), -1);
    }

    public int getRoleId(){ return DataHelper.toInt(CookieHelper.getCookieValue(req, Page.Settings.ROLEID), -1);}

    public int getDeptId(){return DataHelper.toInt(CookieHelper.getCookieValue(req, Page.Settings.DeptId), -1);}

    public boolean autoLogin() {
        String pwd = SafeString.decode(CookieHelper.getCookieValue(req, Page.Settings.PWD));
        if(TextUtils.isEmpty(pwd)){
            return false;
        }
        String userId = CookieHelper.getCookieValue(req, Page.Settings.USERID);
        String roleId = CookieHelper.getCookieValue(req,Page.Settings.ROLEID);
        String deptId = CookieHelper.getCookieValue(req,Page.Settings.DeptId);
        String account = SafeString.decode(CookieHelper.getCookieValue(req, Page.Settings.ACCOUNT));
        if (TextUtils.isEmpty(userId)
                || TextUtils.isEmpty(account)) {
            System.err.println("don't auto login");
            return false;
        }
        String result = HttpUtils.get(ApiParser.getLoginUrl(account, pwd));
        if (onLogin(result, pwd, true)) {
            return true;
        }else{
            //登录失败，设置密码为null，防止重复登录
            CookieHelper.saveCookie(resp, Page.Settings.PWD, null, false);
        }
        return false;
    }

    public void logout() {
        CookieHelper.saveCookie(resp, Page.Settings.USERID, null, false);
//        mPage.saveCookie(new Cookie(Page.Settings.ACCOUNT,  null);
        CookieHelper.saveCookie(resp, Page.Settings.USER_NAME, null, false);
        CookieHelper.saveCookie(resp, Page.Settings.ROLEID, null, false);
        CookieHelper.saveCookie(resp, Page.Settings.XMPP, null, false);
        CookieHelper.saveCookie(resp, Page.Settings.PWD, null, false);
        CookieHelper.saveCookie(resp, Page.Settings.DeptId, null, false);
        CookieHelper.saveCookie(resp, Page.Settings.IP, null, false);
        HttpSession session = req.getSession();
        session.setAttribute(Page.Settings.USERID, null);
        session.setAttribute(Page.Settings.ACCOUNT, null);
        session.setAttribute(Page.Settings.USER_NAME, null);
        session.setAttribute(Page.Settings.ROLEID, null);
        session.setAttribute(Page.Settings.XMPP, null);
        session.setAttribute(Page.Settings.PWD, null);
        session.setAttribute(Page.Settings.DeptId, null);
        session.setAttribute(Page.Settings.IP, null);
    }



    public boolean onLogin(String result, String pwd, boolean reme) {
//638&yonghu2&test11111111111111111111111111111111111111111&112&dfr_test1
        String[] ws = result.split("&");
        try {
            int userId = Integer.parseInt(ws[0]);
            String account = ws[1];
            String name = ws[2];
            int roleId = ws.length > 3 ? Integer.parseInt(ws[3]) : 0;
            String xmpp = ws.length > 4 ? ws[4] : null;
            String DeptId = ws.length > 5 ? ws[5] : null;
            String Ip = ws.length > 6 ? ws[6] : null;
            CookieHelper.saveCookie(resp, Page.Settings.USERID, "" + userId, false);
            if (reme) {
                CookieHelper.saveCookie(resp, Page.Settings.ACCOUNT, SafeString.encode(account), false);
                CookieHelper.saveCookie(resp, Page.Settings.USER_NAME, name, false);
                CookieHelper.saveCookie(resp, Page.Settings.ROLEID, "" + roleId, false);
                CookieHelper.saveCookie(resp, Page.Settings.XMPP, xmpp, false);
                CookieHelper.saveCookie(resp, Page.Settings.PWD, SafeString.encode(pwd), false);
                CookieHelper.saveCookie(resp, Page.Settings.DeptId, DeptId, false);
                CookieHelper.saveCookie(resp, Page.Settings.IP, Ip, false);
            }
            HttpSession session = req.getSession();
            session.setAttribute(Page.Settings.USERID, userId);
            session.setAttribute(Page.Settings.ACCOUNT, SafeString.encode(account));
            session.setAttribute(Page.Settings.USER_NAME, name);
            session.setAttribute(Page.Settings.ROLEID, roleId);
            session.setAttribute(Page.Settings.XMPP, xmpp);
            session.setAttribute(Page.Settings.PWD, SafeString.encode(pwd));
            session.setAttribute(Page.Settings.DeptId,DeptId);
            session.setAttribute(Page.Settings.IP,Ip);
            System.out.println("密码"+ SafeString.encode(pwd));
        } catch (Throwable e) {
            return false;
        }
        return true;
    }

    public boolean onLogin(Map<String, Object> uesrInfo, String pwd, boolean reme) {
//638&yonghu2&test11111111111111111111111111111111111111111&112&dfr_test1
        try {
            int userId = uesrInfo.get("Id")==null? null: Integer.parseInt(uesrInfo.get("Id").toString());
            String account = uesrInfo.get("UserName")==null ? null:uesrInfo.get("UserName").toString();
            String name = uesrInfo.get("RealName")==null ? null:uesrInfo.get("RealName").toString();
            int roleId =uesrInfo.get("RoleId")==null?null: Integer.parseInt(uesrInfo.get("RoleId").toString());
            String xmpp =uesrInfo.get("Xmpp")==null?null :uesrInfo.get("Xmpp").toString();
            String DeptId = uesrInfo.get("DeptId")==null?null:uesrInfo.get("DeptId").toString();
            String Ip = null;
            CookieHelper.saveCookie(resp, Page.Settings.USERID, "" + userId, false);
            if (reme) {
                CookieHelper.saveCookie(resp, Page.Settings.ACCOUNT, SafeString.encode(account), false);
                CookieHelper.saveCookie(resp, Page.Settings.USER_NAME, name, false);
                CookieHelper.saveCookie(resp, Page.Settings.ROLEID, "" + roleId, false);
                CookieHelper.saveCookie(resp, Page.Settings.XMPP, xmpp, false);
                CookieHelper.saveCookie(resp, Page.Settings.PWD, SafeString.encode(pwd), false);
                CookieHelper.saveCookie(resp, Page.Settings.DeptId, DeptId, false);
                CookieHelper.saveCookie(resp, Page.Settings.IP, Ip, false);
            }
            HttpSession session = req.getSession();
            session.setAttribute(Page.Settings.USERID, userId);
            session.setAttribute(Page.Settings.ACCOUNT, SafeString.encode(account));
            session.setAttribute(Page.Settings.USER_NAME, name);
            session.setAttribute(Page.Settings.ROLEID, roleId);
            session.setAttribute(Page.Settings.XMPP, xmpp);
            session.setAttribute(Page.Settings.PWD, SafeString.encode(pwd));
            session.setAttribute(Page.Settings.DeptId,DeptId);
            session.setAttribute(Page.Settings.IP,Ip);
            System.out.println("密码"+ SafeString.encode(pwd));
        } catch (Throwable e) {
            return false;
        }
        return true;
    }


}
