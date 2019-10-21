package servlet;

import com.google.gson.Gson;
import common.SQLHelper;
import dao.CommonDao;
import dao.FormDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CommonServlet extends BaseServlet {
    protected void doHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        super.doPost(request,response);
        PrintWriter out = response.getWriter();//输出
        String method = request.getParameter("method");//获取方法
        SQLHelper sqlHelper = new SQLHelper(request);
        CommonDao commonDao = new CommonDao(sqlHelper);
        FormDao formDao = new FormDao(sqlHelper);
        HttpSession session = request.getSession();
        //获取企业id

        String Company_Id = getCompany_Id();
        System.out.println(Company_Id);
       /* String Company_Id = (String) session.getAttribute("CompanyId");*/
        //Company_Id = "antmall";

        if (method.equals("addForm")){ //   添加 、 修改
            String paramId=request.getParameter("paramId");//获取当前ID参数
            String formName = request.getParameter("FormName"); //获取formName
            String tableName = request.getParameter("tableName"); //获取要数据表名
            String mdAssoWord = request.getParameter("mdAssoWords"); //获取子表单的关联字段
            String ds = request.getParameter("addForm"); //获取form表单
            System.out.println(ds.toString());
            //获取tablename
            String TableName=formDao.getTableNameByFormName(Company_Id,formName);
            Gson gson=new Gson();
            Map<String,Object> map=new HashMap<String,Object>();
            map=gson.fromJson(ds,map.getClass());

            int results = 0;
            if (paramId!=null & !"".equals(paramId)){//修改
                results=commonDao.editDate(Company_Id,TableName,map,paramId);
            }else if (mdAssoWord!=null && !"".equals(mdAssoWord) && paramId==null){ //子表添加
                results = commonDao.addDate(Company_Id,TableName,map,mdAssoWord);
            }
            else{ //添加
                results=commonDao.addDate(Company_Id,TableName,map,null);
            }
            out.print(results);

        }else if (method.equals("batchList")){  //列表操作
            String listId1=request.getParameter("listId");
            Integer listId=Integer.valueOf(listId1);
            String formName=request.getParameter("FormName");
            String afterProcessState=request.getParameter("AfterProcessState");
            Integer afterState=Integer.valueOf(afterProcessState);
            //获取表名
            String tableName=formDao.getTableNameByFormName(Company_Id,formName);
            int count=commonDao.updateBtnList(Company_Id,listId,afterState,tableName);
            out.print(count);

        }else if (method.equals("delBatch")){ //批量删除
            String hidFormName=request.getParameter("hidFormName");
            String id=request.getParameter("id");
            String TableName=formDao.getTableNameByFormName(Company_Id,hidFormName);
            Integer result=commonDao.delBatch(Company_Id,TableName,id);
            out.print(result);
        }



    }

}
