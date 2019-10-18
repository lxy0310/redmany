package servlet;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xpath.internal.objects.XNull;
import common.*;
import common.utils.CookieHelper;
import common.utils.SafeString;
import common.utils.TextUtils;
import dao.BackLoginDao;
import dao.UserDao;
import page.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public class LoginServlet extends BaseServlet {

    @Override
    protected void doHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        PrintWriter out=resp.getWriter();
        //判断是否是登出
        if ("1".equalsIgnoreCase(req.getParameter("out"))) {
            //读取最后的链接
            AccountHelper accountHelper = new AccountHelper(req, resp);
            accountHelper.logout();
            String loginGoto=req.getParameter("url");
            if(loginGoto!=null){
                resp.sendRedirect(loginGoto);
            }else{
                resp.sendRedirect(Page.getLoginPage());
            }

           // resp.sendRedirect("queryStudentServlet");
            return;
        }
        //判断是否是自动登录
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
            }
            else {
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

        String results = "";//输出
        String pcPort =  req.getParameter("pcPort");
        if (pcPort=="1" || pcPort.equals("1")){ //后台
            String piccode=(String) req.getSession().getAttribute("verifyCode"); //获取验证码
            String  checkCode= req.getParameter("message");//获取输入验证码
            //判断验证是否正确
            if (checkCode.equalsIgnoreCase(piccode)){
                //判断企业id
                String CompanyId = getCompany_Id();
                String falgs = CheckCompanyId(req,CompanyId); //判断企业id
                if (falgs.equals("ok")){
                  //  login(req,resp,out);
                    //获取输入的账号名
                    String name = req.getParameter("username");
                    if (name == null) {
                        name = req.getParameter("userName");
                    }
                    //获取输入的密码
                    String pwd = req.getParameter("password");
                    if (pwd == null) {
                        pwd = req.getParameter("passWord");
                    }
                    //新的登录逻辑（ajax版本）
                    UserDao userDao=new UserDao(new SQLHelper(req));
                    List<Map<String, Object>> userInfo=userDao.login(getCompany_Id(),name);

                    String loginMsg=null;
                    if (userInfo!=null && userInfo.size()>0){
                        Map<String,Object> user=userInfo.get(0);
                        //对比密码
                        String userPassword=  user.get("UserPassword").toString();
                        String md5Password=null;
                        try {
                            md5Password= MD5Util.passWordMd5(pwd);
                            if(userPassword.equals(md5Password)){
                                HttpSession session = req.getSession();
                                session.setAttribute("realName",user.get("RealName").toString());
                                AccountHelper accountHelper = new AccountHelper(req, resp);
                                boolean falg = accountHelper.onLogin(user,pwd,false);

                                loginMsg="success";
                            }else{
                                loginMsg="账号或者密码错误";
                            }
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }else{
                        //用户不存在
                        loginMsg="账号不存在";
                    }
                    if(loginMsg!=null){
                        AccountHelper accountHelper = new AccountHelper(req, resp);
                        accountHelper.onLogin(userInfo.get(0),pwd,false);
                        String loginResult= JSON.toJSONString(loginMsg);
                        out.println(loginResult);
                        return;
                    }


                }else{
                    out.print(results);
                }
            }else {
                results = "验证码错误！";
                out.print(results);
            }
        }else{
            login(req,resp,out);
        }

        //旧的登录实现逻辑,走rob平台的登录
       /*if ("phone".equals(req.getParameter("type"))) {
            //手机号登录
        } else {
            String reme = req.getParameter("reme");
            String result = openUrl(ApiParser.getLoginUrl(name, pwd));
            System.err.println("login:"+result);
            if (result.startsWith("fail")) {
                //302到登录页面 queryStudentServlet?copformName=loginPage&showType=listForm
                resp.sendRedirect(Page.getLoginPage());
            }
            else {
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
                        resp.sendRedirect("queryStudentServlet");
                    }
                } else {
                    CookieHelper.saveCookie(resp, "login_fail", "1", true);
                    resp.sendRedirect(Page.getLoginPage());
                }
            }
        }*/


    }

    /**
     * 登陆
     * @param req
     * @param resp
     * @param out
     */
    public String login(HttpServletRequest req,HttpServletResponse resp,PrintWriter out){

        //获取输入的账号名
        String name = req.getParameter("username");
        if (name == null) {
            name = req.getParameter("userName");
        }
        //获取输入的密码
        String pwd = req.getParameter("password");
        if (pwd == null) {
            pwd = req.getParameter("passWord");
        }

        //新的登录逻辑（ajax版本）
        UserDao userDao=new UserDao(new SQLHelper(req));
        List<Map<String, Object>> userInfo=userDao.login(getCompany_Id(),name);

        String loginMsg=null;
        if (userInfo!=null && userInfo.size()>0){
            Map<String,Object> user=userInfo.get(0);
            //对比密码
            String userPassword=  user.get("UserPassword").toString();
            String md5Password=null;
            try {
                md5Password= MD5Util.passWordMd5(pwd);
                if(userPassword.equals(md5Password)){
                    HttpSession session = req.getSession();
                    session.setAttribute("realName",user.get("RealName").toString());
                   // session.setAttribute("","");
                    AccountHelper accountHelper = new AccountHelper(req, resp);
                    boolean falg = accountHelper.onLogin(user,pwd,false);

                    loginMsg="success";
                }else{
                    loginMsg="账号或者密码错误";
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
            //用户不存在
            loginMsg="账号不存在";
        }
        if(loginMsg!=null){
            AccountHelper accountHelper = new AccountHelper(req, resp);
            accountHelper.onLogin(userInfo.get(0),pwd,false);
            String loginResult= loginMsg;
            //out.println(loginResult);
            return loginResult;
        }
        return null;
    }


    /**
     * 检查CompanyId
     * @param request
     * @param CompanyId
     * @return
     */
    public String CheckCompanyId(HttpServletRequest request, String CompanyId){
        String flag="";
        SQLHelper sqlHelper = new SQLHelper(request);
        BackLoginDao backDao=new BackLoginDao(sqlHelper);//创建数据层
        String Company_Id="redmany";
        int existCompany=backDao.getcheckCompanyId(Company_Id,CompanyId);
        int users_mobile = backDao.getUsersMobileNum(Company_Id,CompanyId);//手机登录人数
        int users_computer = backDao.getUsersPCNum(Company_Id,CompanyId); //PC登录人数
        int mobile= backDao.getLoginMobileNum(CompanyId);    //获取授权手机数量
        int pc = backDao.getLoginPCNum(CompanyId);   //获取授权电脑数量
        if (existCompany>0){ ////是否存在
            if (backDao.getExpireTimeByCompanyId(Company_Id,CompanyId)<= 0 ){//企业是否过期
                flag="企业帐号已经过期，请联系客服中心处理，谢谢！";
            }else if (users_mobile < mobile){   //手机数量超标
                flag="手机客户端数量超过授权，请联系客服中心处理，谢谢！";
            }else if (users_computer < pc){   //电脑用户数量超标
                flag="电脑客户端数量超过授权，请联系客服中心处理，谢谢！";
            }else {
                flag="ok";
            }
        }else {
            flag="企业帐号不存在，请重新输入，谢谢！";
        }
        return flag;
    }




}
