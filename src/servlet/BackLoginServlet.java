package servlet;

import com.google.gson.Gson;
import common.AccountHelper;
import common.ApiParser;
import common.MD5Util;
import common.SQLHelper;
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
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class BackLoginServlet extends BaseServlet {

    protected void doHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();//输出
        String method=request.getParameter("method");//获取方法
        SQLHelper sqlHelper = new SQLHelper(request);
        UserDao userDao=new UserDao(sqlHelper);//创建数据层

        if (method.equals("login")){
            int result=0;
            String ds = request.getParameter("setSubList");
            System.out.println(ds);
            Gson gson=new Gson();
            Map<String,Object> map=new HashMap<String,Object>();
            map=gson.fromJson(ds,map.getClass());
            String piccode=(String) request.getSession().getAttribute("verifyCode");
            String  checkCode = (String) map.get("massage");
            checkCode = checkCode.toUpperCase();    //把字符全部转换为大写
            System.out.println(checkCode);
            if (checkCode.equalsIgnoreCase(piccode)){
                String CompanyId = (String) map.get("CompanyId");
                int falg=CheckCompanyId(request,CompanyId);
                result=falg;
                if (falg==4){
                    String userName = (String) map.get("username");
                    String password = (String) map.get("password");

                    String results = openUrl(ApiParser.getBackLogin(userName, password,CompanyId));
                    if (results.startsWith("fail")) {
                        response.sendRedirect("backLogin.jsp");
                    }else{
                        AccountHelper accountHelper = new AccountHelper(request, response);
                        boolean f = accountHelper.onLogin(results, password, "true".equals(true));
                        System.out.println(f+"===============");
                        if (results!=null){
                            // APPConfig.COMPANYID=CompanyId;
                            String[] ws = results.split("&");
                            int userId = Integer.parseInt(ws[0]);
                            int roleId = ws.length > 3 ? Integer.parseInt(ws[3]) : 0;
                            HttpSession session=request.getSession();
                            session.setAttribute("userId",userId);
                            session.setAttribute("userName",userName);
                            session.setAttribute("roleId",roleId);
                            session.setAttribute("CompanyId",CompanyId);
                           // Page.RoleId=roleId;
                            /*Integer DeptId = backDao.getDeptIdByUserId(CompanyId,userId);
                            if (DeptId!=null){
                                session.setAttribute("DeptId",DeptId);
                            }*/
                            String targetUrl = CookieHelper.getCookieValue(request, Page.Settings.TARGETURL);
                            System.out.println(targetUrl);
                            if (!TextUtils.isEmpty(targetUrl)) {
                                targetUrl = SafeString.decode(targetUrl);
                                CookieHelper.saveCookie(response, Page.Settings.TARGETURL, null, true);
                                System.err.println("LoginServlet targetUrl=" + targetUrl);
                                response.sendRedirect(targetUrl);
                            } else {
                                //response.sendRedirect("backMar?method=BackHome");
                               /* List<Map<String,Object>> menuList=backDao.getMenuLists(CompanyId,String.valueOf(userId));
                                List<Map<String,Object>> panelList=backDao.getPanelList(CompanyId);
                                List<Map<String,Object>> panelId=backDao.getPanelId(CompanyId);
                                request.getSession().setAttribute("menuList",menuList);
                                request.getSession().setAttribute("panelList",panelList);
                                request.getSession().setAttribute("getPanelId",panelId);*/
                                //request.getRequestDispatcher("backMar?method=menuList").forward(request,response);
                                out.print(result);
                            }
                        }else {
                            CookieHelper.saveCookie(response, "login_fail", "1", true);
                            response.sendRedirect("queryStudentServlet");
                        }
                    }
                }else{
                    response.sendRedirect("backLogin.jsp");
                }
            }else{
               // out.print(6);
               /* out.println("<script type='text/javascript'>alert('验证码错误！');");
                out.print("location.href='backLogin.jsp';");
                out.print("</script>");*/
                result=6;
                out.print(result);
            }

        }




    }

    /**
     * 检查CompanyId
     * @param request
     * @param CompanyId
     * @return
     */
    public int CheckCompanyId(HttpServletRequest request, String CompanyId){
        int flag=0;
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
                flag=1;
            }else if (users_mobile < mobile){   //手机数量超标
                flag=2;
            }else if (users_computer < pc){   //电脑用户数量超标
                flag=3;
            }else {
               flag=4;
            }
        }else {
            flag=5;
        }
        return flag;
    }

    public void Login(HttpServletRequest request, String CompanyId){

    }

}
