package servlet;

import com.google.gson.Gson;
import common.SQLHelper;
import dao.BackMarDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackMarServelt extends BaseServlet {

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
        String method=request.getParameter("method");//获取方法
        SQLHelper sqlHelper = new SQLHelper(request);
        BackMarDao backDao=new BackMarDao(sqlHelper);//创建数据层
       // String Company_Id=request.getParameter("gCompany_Id");
        HttpSession session=request.getSession();
        String Company_Id=getCompany_Id();
       // System.out.println(getCompany_Id());
        Integer uids = (int)session.getAttribute("userId");
        String uid = String.valueOf(uids);
        System.err.println("Company_Id-------------"+Company_Id);
        System.err.println("userId-------------"+uid);
        if (method.equals("menuList")){
            List<Map<String,Object>> menuList=backDao.getMenuLists(Company_Id,uid);
            List<Map<String,Object>> panelList=backDao.getPanelList(Company_Id);
            List<Map<String,Object>> panelId=backDao.getPanelId(Company_Id);
            request.getSession().setAttribute("menuList",menuList);
            request.getSession().setAttribute("panelList",panelList);
            request.getSession().setAttribute("getPanelId",panelId);
            System.out.println("menuList"+menuList.toString());
            System.out.println("panelList"+panelList.toString());
            System.out.println("getPanelId"+panelId.toString());
            request.getRequestDispatcher("zTree.jsp").forward(request,response);
        }
        else if (method.equals("addNewForm")){ //newForm   添加
            String hidFormName=request.getParameter("hidFormName");
            List<Map<String,Object>> formList=backDao.getForms(Company_Id,hidFormName);
            String ds = request.getParameter("setList");
            System.out.println(ds);
            Gson gson=new Gson();
            Map<String,Object> map=new HashMap<String,Object>();
            map=gson.fromJson(ds,map.getClass());
            String tableName=null;
            for (int i = 0; i < formList.size(); i++) {
                tableName=(String)formList.get(i).get("Table_name");
            }
            int results=backDao.addDate(Company_Id,tableName,map);
            System.out.println(results);

        }else if (method.equals("delListForm")){    //删除
            String hidFormName=request.getParameter("hidFormName");
            String id=request.getParameter("id");
            String TableName=backDao.getTableNameByFormName(Company_Id,hidFormName);
            Integer result=backDao.delDate(Company_Id,TableName,id);
            out.print(result);
        }else if (method.equals("editNewForm")){    //修改
            String hidFormName=request.getParameter("hidFormName");
            String TableName=backDao.getTableNameByFormName(Company_Id,hidFormName);
            String paramId=request.getParameter("paramId");
            String ds = request.getParameter("setList");
            Gson gson=new Gson();
            Map<String,Object> map=new HashMap<String,Object>();
            map=gson.fromJson(ds,map.getClass());
            int results=backDao.editDate(Company_Id,TableName,map,paramId);
            System.out.println(results);
            out.print(results);
        }else if (method.equals("updateListBtn")){  //列表操作
            String listId1=request.getParameter("listId");
            Integer listId=Integer.valueOf(listId1);
            String formName=request.getParameter("FormName");
            String afterProcessState=request.getParameter("AfterProcessState");
            Integer afterState=Integer.valueOf(afterProcessState);
            //获取表名
            String tableName=backDao.getTableNameByFormName(Company_Id,formName);
            int count=backDao.updateBtnList(Company_Id,listId,afterState,tableName);
            out.print(count);

        }else if (method.equals("SearchHead")){ //  查询
            String formName=request.getParameter("formName");
            List<Map<String,Object>> formSearchList=backDao.getFormSearchLsit(Company_Id,formName); //form表

            String Search_fields="";
            if (formSearchList!=null){
                for (Map<String,Object> search: formSearchList) {
                    if (search.get("Search_fields").toString()!=null){
                        Search_fields=search.get("Search_fields").toString();
                    }
                }
            }
            List<Map<String,Object>> formFeildList=backDao.getSearchFormField(Company_Id,formName,Search_fields);  //formfeild表
            request.getSession().setAttribute("Search_fieldList",formSearchList);
            request.getSession().setAttribute("formFeildList",formFeildList);
            request.getSession().setAttribute("formName",formName);
            request.getRequestDispatcher("Search.jsp").forward(request,response);
        }else if (method.equals("searchForm")){ //搜索查询提交
            String formName=request.getParameter("formName");
            String ds = request.getParameter("setList");
            System.out.println(ds);
            Gson gson=new Gson();
            Map<String,Object> map=new HashMap<String,Object>();
            map=gson.fromJson(ds,map.getClass());
            System.out.println(map);
            //TransferParams
            request.getRequestDispatcher("queryStudentServlet?copformName=Shop_information_update&showType=ListModifyForm&map="+map).forward(request,response);
        }
        else if (method.equals("MDListjson")){
            String formName=request.getParameter("FormColumnName");
//            String uid = request.getParameter("uid");
            uid="1";
            String FFormColumnName = formName.split(",")[0]; //主表
            List<Map<String,Object>> formList = backDao.getForms(Company_Id,FFormColumnName);//form表
            List<Map<String,Object>> formFeildList = backDao.getFormFeilds(Company_Id,FFormColumnName);  //formfeild表
            String getdatesql="";
            for (Map<String,Object> form1 : formList){
                getdatesql = form1.get("Get_data_sql").toString();
            }
            List<Map<String,Object>> fformTable = backDao.getListBySql(Company_Id,getdatesql,uid);//主表
            int counts = backDao.getCountData(Company_Id,getdatesql,uid);   //条数
            System.out.println(counts);
            StringBuilder sb = new StringBuilder();
            sb.append("<table class='layui-table'>");
            sb.append("<thead><tr>");
            for (Map<String,Object> thead : formFeildList){
                sb.append("<th>"+thead.get("Title").toString()+"</th>");
            }
            sb.append("</thead></tr>");
            sb.append("<tbody>");
            for (Map<String,Object> ff : fformTable){
                sb.append("<tr>");
                for (Map<String,Object> tbody : formFeildList){
                    String str = tbody.get("Name").toString();
                    sb.append("<td>"+ff.get(str).toString()+"</td>");
                }
                sb.append("</tr>");
            }
            sb.append("</tbody>");//<div id="test1"></div>
            sb.append("</table>");
            sb.append("<input type='hidden' value='"+counts+"' class='pageValue' id='pageValue'>");
            System.out.println(sb.toString());
            out.print(sb.toString());
           // response.getWriter().write(sb.toString());

        }
        else if (method.equals("MDinitializeJSon")){
            String formName=request.getParameter("FormColumnName");
            /*String uid = request.getParameter("uid");*/
            uid="1";
            String FFormColumnName = formName.split(",")[0]; //主表
            List<Map<String,Object>> formList = backDao.getForms(Company_Id,FFormColumnName);//form表
            List<Map<String,Object>> formFeildList = backDao.getFormFeilds(Company_Id,FFormColumnName);  //formfeild表
            String getdatesql="";
            for (Map<String,Object> form1 : formList){
                getdatesql = form1.get("Get_data_sql").toString();
            }
          //  List<Map<String,Object>> fformTable = backDao.getListBySql(Company_Id,getdatesql,uid);//主表
          //  int counts = backDao.getCountData(Company_Id,getdatesql,uid);   //条数

            StringBuilder sb = new StringBuilder();
            sb.append("[[");
            for (Map<String,Object> map : formFeildList){
                sb.append("{field:'"+map.get("Name").toString()+"',title:'"+map.get("Title").toString()+"'},");
            }
            sb.append("{fixed:'right',title:'操作',toolbar:'#barDemo'}");
            sb.append("]]");
            out.print(sb.toString());
        }else if (method.equals("MDlistTable")){
            String formName=request.getParameter("FormColumnName");
          /*  String uid = request.getParameter("uid");*/
            uid="1";
            String FFormColumnName = formName.split(",")[0]; //主表
            List<Map<String,Object>> formList = backDao.getForms(Company_Id,FFormColumnName);//form表
            String getdatesql="";
            for (Map<String,Object> form1 : formList){
                getdatesql = form1.get("Get_data_sql").toString();
            }
            List<Map<String,Object>> fformTable = backDao.getListBySql(Company_Id,getdatesql,uid);//主表
            int counts = backDao.getCountData(Company_Id,getdatesql,uid);   //条数
            Gson gson = new Gson();
            String js=gson.toJson(fformTable);
            String jso = "{\"code\":0,\"msg\":\"\",\"count\":"+counts+",\"data\":"+js+"}";
            out.print(jso);

        }


    }

}
