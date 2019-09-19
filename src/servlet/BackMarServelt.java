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
        PrintWriter out = response.getWriter();//输出
        String method=request.getParameter("method");//获取方法
        SQLHelper sqlHelper = new SQLHelper(request);
        BackMarDao backDao=new BackMarDao(sqlHelper);//创建数据层
       // String Company_Id=request.getParameter("gCompany_Id");
        HttpSession session=request.getSession();
        String Company_Id=(String)session.getAttribute("CompanyId");
        Company_Id="guoyuhall";
//        if (Company_Id==null){
//            Company_Id="CloudMall";
//        }
        System.err.println("Company_Id-------------"+Company_Id);
        if (method.equals("menuList")){
            List<Map<String,Object>> menuList=backDao.getMenuList(Company_Id,"1");
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
            //List<Map<String,Object>> formFeildList=backDao.getFormFeilds(Company_Id,hidFormName);
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
            String uid = request.getParameter("uid");
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
            String uid = request.getParameter("uid");
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
            String uid = request.getParameter("uid");
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

        }else if (method.equals("MDlistShowTables")){

        }else if (method.equals("MDnewFormDate")){
            String formName=request.getParameter("FormColumnName");
            String uid = request.getParameter("uid");
            uid="1";
            String FFormColumnName = formName.split(",")[0]; //主表
            List<Map<String,Object>> fformList = backDao.getForms(Company_Id,FFormColumnName);//form表
            List<Map<String,Object>> fformFeildList = backDao.getFormFeilds(Company_Id,FFormColumnName);  //formfeild表
            StringBuilder sb = new StringBuilder();
           sb.append("<form class='layui-form'>");
            for (Map<String,Object> fform: fformFeildList){
                sb.append("<div class='layui-form-item' >");
                sb.append("<label class='layui-form-lable'>");
                sb.append(fform.get("Title").toString()+"</label>");
                sb.append("<div class='layui-input-block'>");
                if ("select".equals(fform.get("Type").toString())){
                    String replacerName = fform.get("data_source").toString();
                    List<Map<String,Object>> replacerList = backDao.getReplacer(Company_Id,replacerName);
                    sb.append("<select name='"+fform.get("Name").toString()+"' lay-verify='required' lay-search=''>");
                    sb.append("<option value=''>==请选择==</option>");
                    for (Map<String,Object> replacer: replacerList){
                        String datasql = "";
                        Object datasql1 = replacer.get("Datasql");
                        datasql = datasql.toString();

                        if (datasql !=null && !datasql.equals("") ){
                            List<Map<String,Object>> selectList = backDao.getListBySql(Company_Id,replacer.get("Datasql").toString(),uid);
                            for (Map<String,Object> s : selectList){
                                sb.append("<option value='"+s.get("value").toString()+"'>"+s.get("name").toString()+"</option>");
                            }
                        }else if (replacer.get("Txtsource").toString() != null ){
                            String str = replacer.get("Txtsource").toString();
                            String strs[] = str.split("#");
                            for (int i = 0; i <strs.length ; i++) {
                                System.out.println(strs[i]);
                                String stri= strs[i];
                                String name = stri.substring(0,stri.indexOf(":"));
                                String values=stri.substring(stri.lastIndexOf(":")+1,stri.length());
                                sb.append("<option value='"+name+"'>"+values+"</option>");
                            }
                        }
                    }
                    sb.append("</select>");
                }else if ("multiText".equals(fform.get("Type").toString())){
                    sb.append("<textarea name='");
                    sb.append(fform.get("Name").toString());
                    sb.append("' class='layui-textarea'</textarea>");
                }else if ("intText".equals(fform.get("Type").toString())){

                }
                else{
                    sb.append("<input type='text' name='"+fform.get("Name").toString()+"' placeholder='请输入"+fform.get("Title").toString()+"' class='layui-input'>");
                }
                sb.append("</div>");
                sb.append("</div>");
            }
            sb.append("</form>");


            Gson gson=new Gson();
            String fformLists = gson.toJson(fformFeildList);
          //  String fformFeildLists = gson.toJson(fformFeildList);
            System.out.println(fformLists);
            String fformFeildListstr= fformLists.substring(1,fformLists.length()-1);
            System.out.println(fformFeildListstr);
        //    System.out.println(fformFeildLists);
            // String json = "{\"fformTable\":\""+fformLists+"\",\"sformTable\":\""+fformFeildLists+"\"}";
            String json = "{\"fformTable\":"+fformFeildListstr+"}";

           // System.out.println(sb.toString());
          //  out.print(sb.toString());
            out.print(json);

        }



    }

}
