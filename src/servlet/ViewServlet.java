package servlet;

import com.alibaba.fastjson.JSON;
import common.SQLHelper;
import common.utils.TextUtils;
import page.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ViewServlet extends BaseServlet {
    /**
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String Company_Id = Page.COMPANYID;
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out=resp.getWriter();
        boolean viewType = !TextUtils.isEmpty(req.getParameter("viewType"));
        if(viewType){
            String type = req.getParameter("viewType");
            if("1".equals(type)){//是否唯一
                String sql = req.getParameter("sql");
                String value = req.getParameter("value");
                SQLHelper sqlHelper = new SQLHelper(req);
                List<Map<String, Object>> list = sqlHelper.executeQueryList(Company_Id, sql, new String[]{value});
                if (list == null || list.size() == 0) {
                    resp.getWriter().write("ok");
                } else {
                    resp.getWriter().write("fail");
                }
                return;
            }
            if("2".equals(type)){//联动下拉获取子级数据
                String value = req.getParameter("value");
                String cid = req.getParameter("cid");
                String childSql = session.getAttribute(cid+"_sql").toString();
                if(childSql.indexOf("[Current_Father_Value]")>0){
                    childSql = childSql.replace("[Current_Father_Value]",value);
                    SQLHelper sqlHelper = new SQLHelper(req);
                    List<Map<String, Object>> childList = sqlHelper.executeQueryList(Company_Id, childSql, null);
                        if (childList != null || childList.size() > 0) {
                            out.print(JSON.toJSONString(childList));
                        }
                        return;
                }
            }
        }
    }

}
