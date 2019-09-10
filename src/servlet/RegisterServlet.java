package servlet;

import common.APPConfig;
import common.ApiParser;
import common.CommonUtils;
import common.SQLHelper;
import common.utils.TextUtils;
import page.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RegisterServlet extends BaseServlet {
    /**
     * http://:50011/submitData.aspx?UserName=18316547812&Company_Id=bzService&formName=User&showType=newForm&
     * UserPassword=123&Id=&NickName=u123456&userId=-1&Mobile=18316547812
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String UserName = req.getParameter("username");
        boolean check = !TextUtils.isEmpty(req.getParameter("check"));
        String Company_Id = Page.COMPANYID;

        if (check) {
            SQLHelper sqlHelper = new SQLHelper(req);
            List<Map<String, Object>> list = sqlHelper.executeQueryList(Company_Id, "Select * from [User] where UserName=?", new String[]{UserName});
            if (list == null || list.size() == 0) {

                resp.getWriter().write("ok");
            } else {
                resp.getWriter().write("fail");
            }
            return;
        }

        String UserPassword = req.getParameter("password");
        String NickName = req.getParameter("nickName");
        String Mobile = req.getParameter("mobile");
        String code = req.getParameter("code");

        String RealName = new String(NickName.getBytes("UTF-8"),"UTF-8");
        String url = CommonUtils.userRegister +"company_id="+ APPConfig.COMPANYID +"&username=" + UserName + "&UserPassword=" + UserPassword
                +"&formName=User&showType=newForm&RealName="+RealName+"&userid=-1";

        String result = openUrl(url);
        System.err.println("register:" + result);
        resp.sendRedirect(Page.getLoginPage());
    }
}
