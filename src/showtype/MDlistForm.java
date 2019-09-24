package showtype;

import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.sections.A;
import com.sangupta.htmlgen.tags.body.table.TBody;
import com.sangupta.htmlgen.tags.body.table.THead;
import com.sangupta.htmlgen.tags.body.table.Table;
import com.sangupta.htmlgen.tags.body.table.TableRow;
import com.sangupta.htmlgen.tags.body.text.Span;
import com.sangupta.htmlgen.tags.head.Script;
import common.SQLHelper;
import common.utils.TextUtils;
import dao.CommonDao;
import dao.FormDao;
import dao.MenuDao;
import model.Menu;
import viewtype.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MDlistForm extends CustomForm {

    private String FFormColumnName; //主表
    private String SFormColumnName; //从表
    private String FormColumnName; //关联字段
    private List<Map<String, Object>> FFormList; //主表Form
    private List<Map<String, Object>> SFormListByName; //从表Form
    private List<Map<String, Object>> SFormTable; //主表数据
    private List<Map<String, Object>> FFormTable;//从表数据

    private Menu Menus;
    private MenuDao menuDao;
    private CommonDao commonDao;
    private FormDao formDao;
    //变量
    private String ListFeilds;
    private String publish; //   1 pc,2 mobile
    private List<Map<String, Object>> FormStateOpertionList;
    private List<Map<String, Object>> getFormUpdateFileldList;

    protected void initDao(SQLHelper pSQLHelper) {
        menuDao = new MenuDao(pSQLHelper);
        commonDao = new CommonDao(pSQLHelper);
        formDao = new FormDao(pSQLHelper);
    }

    @Override
    protected void loadData(String sql) {

        Menus = menuDao.getMenu(getCompanyId(), getFormName());
        if (Menus != null) {
            publish = Menus.getPublish();

        }
        FFormColumnName = formName.split(",")[0]; //主表
        System.out.println("FFormColumnName:"+FFormColumnName);
        FFormList = formDao.getFormList(getCompanyId(), FFormColumnName);//获取主表form表信息
        System.out.println("FFormList:" + FFormList.toString());
        if (FFormList != null) {
            for (Map<String, Object> line : FFormList) {
                sql = line.get("Get_data_sql").toString();   //获取主表的sql语句
                System.out.println("sql:"+sql);
                ListFeilds = line.get("List_fields").toString(); //获取主表显示字段
            }
        }
        getFormUpdateFileldList = formDao.getFormList(getCompanyId(), FFormColumnName);  //
        System.out.println("getFormUpdateFileldList:>>>>" + getFormUpdateFileldList.toString());
        String setList = getPage().getUrlParameter("map");
        System.out.println("setList===" + setList);

        super.loadData(sql);
    }

    @Override
    protected void make(Div div) {

        div.add(new Script("js/MDForm.js"));
        div.add(new Script("js/jquery-2.1.4.min.js"));
         /*String formname = getFormName().toString();
       FFormColumnName = formname.split(",")[0];
        //查询主表sql语句
        FFormList = formDao.getFormList(getCompanyId(), FFormColumnName);
        String sql1 = null;
        String ListFeilds = null;
        if (FFormList != null) {
            for (Map<String, Object> line : FFormList) {
                sql1 = line.get("Get_data_sql").toString();   //获取主表的sql语句
                ListFeilds = line.get("List_fields").toString();
            }
        }
        //查询主表数据
        int uid = getPage().getUserId();
        List<Map<String, Object>> fDates = commonDao.getListBySql(getCompanyId(), sql1, String.valueOf(uid));
*/
        List<View> views = getViewLists(ListFeilds);
        //toList
        Table table = div.table().addCssClass("layui-table");
        THead thead = table.thead();
        TableRow rowTh = new TableRow();  //表头
        for (View view : views) {
            rowTh.td(view.getTitle());
        }
        rowTh.td("操作");
        thead.tr(rowTh);
        TBody tBody = table.tbody();
        for (Map<String, Object> line : mDatas) {
            String html = getHtmlTemplate();
            List<String> list = new ArrayList<String>();
            TableRow row = new TableRow();
            System.out.println("Id === " + line.get("id"));
            System.out.println(mDatas.toString());
            for (View view : views) {
                view.setIsTitle("1");
                html = makeViews(list, view, line, html);
            }
            if (!TextUtils.isEmpty(html)) {
                row.td(html);
            }
            for (String v : list) {
                row.td(v);
            }
            Integer Tablestate=(Integer) line.get("state");
            System.out.println(Tablestate);
            if (Tablestate!=null) {
                FormStateOpertionList = commonDao.getFormListOperationShow(getCompanyId(), 1, FFormColumnName, Tablestate);
                if (FormStateOpertionList!=null){
                    Span span=row.td().span().styles("white-space: nowrap;");
                    for(Map<String,Object> btnList : FormStateOpertionList){
                        A a1=span.a();
                        if ("_update".equals(btnList.get("OperationType").toString())){  //修改
                            a1.text(btnList.get("OperationName").toString());
                            a1.herf("queryStudentServlet?copformName="+getFormName()+"&showType=MDnewForm&optype=1&ParamId="+line.get("Id"));
                        }else if ("_look".equals(btnList.get("OperationType").toString())){ //查看
                            a1.text(btnList.get("OperationName").toString());
                            a1.herf("queryStudentServlet?copformName="+getFormName()+"&showType=newForm&optype=2&ParamId="+line.get("Id"));
                        }else if ("_del".equals(btnList.get("OperationType").toString())){    //删除
                            a1.text(btnList.get("OperationName").toString());
                            a1.herf("javascript:void(0);").onClick("delListForm("+line.get("Id")+",'"+getFormName()+"');");
                        }else if ("_add".equals(btnList.get("OperationType").toString())){  //添加
                            a1.text(btnList.get("OperationName").toString());
                            a1.herf("queryStudentServlet?copformName="+getFormName()+"&showType=newForm&optype=2&ParamId="+line.get("Id"));
                        }else if ("_select".equals(btnList.get("OperationType").toString())){ //跳转到自定义页面
                            a1.text(btnList.get("OperationName").toString());
                            String TemplatePage=commonDao.getTemplatePageByOperationId(getCompanyId(),(Integer) btnList.get("OperationId"));
                            a1.herf(TemplatePage+"?FormName="+getFormName()+"&id="+line.get("Id")+"&NeedState="+Tablestate);
                        }
                        else{
                            a1.text(btnList.get("OperationName").toString());
                            a1.herf("javascript:void(0);").onClick("updateListBtn("+line.get("Id")+",'"+getFormName()+"','"+btnList.get("AfterProcessState")+"'"+");");

                        }
                        a1.styles("white-space: nowrap;background-color: #1E9FFF;color: white;padding: 5px;border-radius: 3px;margin-right: 5px;" +
                                "text-decoration: none;");

                    }
                }
            }
            // }
            tBody.tr(row);
        }



    }







}
