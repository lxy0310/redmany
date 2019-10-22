package showtype;


import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.forms.Button;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.sections.A;
import com.sangupta.htmlgen.tags.body.sections.H3;
import com.sangupta.htmlgen.tags.body.table.*;
import com.sangupta.htmlgen.tags.body.text.Italic;
import com.sangupta.htmlgen.tags.body.text.Span;
import com.sangupta.htmlgen.tags.head.Script;
import common.SQLHelper;
import common.SQLUtil;
import common.utils.TextUtils;
import dao.CommonDao;
import dao.FormDao;
import dao.FormFiledDao;
import dao.MenuDao;
import model.Form;
import model.Menu;
import org.apache.commons.lang.StringUtils;
import service.PagingService;
import service.impl.PagingServiceImpl;
import viewtype.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static page.Page.platform;

/**
 * Created by Su on 2017/12/21.
 */
public class ListModifyForm extends CustomForm {

    private Menu Menus;
    private MenuDao menuDao;
    private CommonDao commonDao;
    private FormDao formDao;
    private FormFiledDao filedDao;
    private String isShow;  //  不为空,可以修改,   1   替换页面,   2 上下页面
    private List<Map<String, Object>> FormStateOpertionList;
    private List<Map<String, Object>> formStateOperation;
    private int group;  //判断是否有分组
    private FormUtil formUtil;
    private String paramId; //参数 ID
    private String mdAssoWord; //从表
    private String searchCondition ; //查询条件
    private  Form froms ;//form表信息
    private PagingService pagingService=new PagingServiceImpl();



    @Override
    public HtmlBodyElement<?> createViews() {
        Div div = new Div();
        div.id(formName);

        make(div);
        //添加分页菜单栏
        pagingService.addPagingMenuBar(div,getPage());
        return div;
    }
    protected void initDao(SQLHelper pSQLHelper) {
        menuDao = new MenuDao(pSQLHelper);
        commonDao = new CommonDao(pSQLHelper);
        formDao = new FormDao(pSQLHelper);
        filedDao = new FormFiledDao(pSQLHelper);
        formUtil = new FormUtil();

    }

    @Override
    protected void loadData(String sql) {
        froms = formDao.getForm(companyId,formName);
        paramId=getPage().getParameter("ParamId"); //获取参数ID
        sql = sqlGetIDs(paramId,sql,froms.getReplaceName());
        mdAssoWord = getPage().getParameter("mdAssoWord");
        sql = sqlGetMD(mdAssoWord,sql,froms.getReplaceName());
        searchCondition = getPage().getRequestParamter("condition");
        if (searchCondition!=null){
            sql = sqlSearchCondition(searchCondition,sql,froms.getReplaceName(),froms.getSearch_fields());  //查询
        }
        Menus = menuDao.getMenu(getCompanyId(), getFormName());
        if (Menus != null) {
            isShow = Menus.getIsShow();
        }
        //查询是否有分组
        group = 0;  //默认没有分组
        if (platform.equals("1") || platform == "1") {  //platform 1 为后台
            // if (isShow!=null){
            Integer formStateId = commonDao.getFormStateIdByFormName(getCompanyId(), getFormName());
            if (formStateId != null) {
                System.out.println(formStateId.toString());
                formStateOperation = commonDao.getFormStateOperationByStateId(getCompanyId(), formStateId.toString());
            }
        }
        if (platform.equals("0")) {
            sql = sql + " where u.Id=" + getPage().getUserId();
        }
        //获取总的条数
        Integer dataCount=(Integer) getPage().getSQLHelper().ExecScalar(companyId, SQLUtil.getCountSql(sql),null);
        if(dataCount!=null&& dataCount>0){
            getPage().setDataCount(dataCount);
        }
        //获取到分页后的url
        sql= SQLUtil.getPagingSQL(sql,getPage().getPageSize(),getPage().getPageIndex(),getFormData().getReplaceName());
        System.out.println(sql);
        super.loadData(sql);
    }

    @Override
    protected void make(Div div) {
        div.add(new Script("js/jquery.js"));
        div.add(new Script("js/colResizable-1.6.min.js"));
        div.add(new Script("js/colResizable-1.6.js"));

        System.out.println(platform);
        if (platform.equals("1") || platform == "1" ) {//1为后台
            //没有分组
            if (group == 0) {
                /*List<View> views = getViews();
                formUtil.showListBack(div,views,mDatas);*/
                showBack(div);

            } else {
                //showGroupBack(div);

            }
        } else { // 前端
            listShow(div);
        }
    }

    //查询
    public  void  seach(Div div){
        //获取搜索字段
        String search = froms.getSearch_fields();
        if (search!=null && !search.equals("")){
            search = search.replaceAll("%","");
            List<View> views = getViewLists(search);
            String html = getHtmlTemplate();
            List<String> list = new ArrayList<>();

            com.sangupta.htmlgen.tags.body.forms.Form divshow = div.form().addCssClass("layui-form").id("searchCondition");
            Div layuiRow = divshow.div().addCssClass("layui-row").styles("margin-top:10px;");
            for (View view : views) {
                view.setIsValue("1");
                if (view.getType().equalsIgnoreCase("Datetime") ){
                    view.setIsDouble("1");
                }
                html = makeViews(list, view, null, html);
            }
            if (!TextUtils.isEmpty(html)) {
                div.text(html);
            }
            for (String v : list) {
                Div  divs  = layuiRow.div();

                if (v.contains("useLayDateMultiple")){
                    divs.addCssClass("layui-col-xs12 layui-col-sm12 layui-col-md6");
                }else {
                    divs.addCssClass("layui-col-xs6 layui-col-sm6 layui-col-md3");
                }
                Div div2 = divs.div().addCssClass("layui-form-item");
                Div div1 = div2.div().styles("height: 30px;line-height: 30px;" );
                div1.addCssClass("layui-input-block").styles("margin:0px;");
                v = v.replaceAll("<label>","<label class=\"labelRight\">");
                div1.text(v);
            }
            Div  divbtn1  = layuiRow.div().addCssClass("layui-col-xs6 layui-col-sm6 layui-col-md3");
            Div divbtn2 = divbtn1.div().addCssClass("layui-form-item");
            Button searchBtn = divbtn2.button();
            searchBtn.attr("type","button");
            searchBtn.addCssClass("layui-btn");
            searchBtn.styles("margin-left: 120px;");
            Italic i = new Italic();
            i.addCssClass("layui-icon ");
            i.text("&#xe615;");
            searchBtn.italic(i);
            searchBtn.text("搜索");
            searchBtn.id("search");
            String url = "queryStudentServlet?copformName="+formName+"&showType="+getPage().getShowType(); //queryStudentServlet?copformName=user1&showType=listForm
            searchBtn.onClick("searchCondition('"+url+"')");
        }
    }

    public void showBack(Div div) {
        if (mDatas != null) {
            List<View> views = getViews();
            seach(div);
            Div head = div.div().addCssClass("layui-table-tool");
            Div temp = head.div().addCssClass("layui-table-tool-temp");
            Div btncontainer = temp.div().addCssClass("layui-btn-container");

            //Button add = btncontainer.button().addCssClass("layui-btn layui-btn-sm layui-btn-normal").text("添加");
            // Button del = btncontainer.button().addCssClass("layui-btn layui-btn-sm").text("删除").onClick("del('" + getFormName() + "')");;
            //生成批量操作按钮

            //表头
            Table table = div.table().addCssClass("layui-table");
//        table.attr("lay-skin","line");
            table.styles("margin:0px auto;");
            THead thead = table.thead();
            TableRow rowTh = new TableRow();  //表头

            Div sel = rowTh.td().div().addCssClass("layui-input-inline");
            // sel.styles("white-space: nowrap;");
            Span selAll = sel.span().id("as").text("全选");
            Input checkbox = sel.input();
            checkbox.id("box");
            checkbox.type("checkbox");
            checkbox.onClick("my()");

            for (View view : views) {

                rowTh.td(view.getTitle());
            }
            rowTh.td("操作");
            thead.tr(rowTh);
            //内容
            TBody tBody = table.tbody();
            Map<String, Object> batch = new HashMap<>();//承载批量按钮
            for (Map<String, Object> line : mDatas) {
                String html = getHtmlTemplate();
                List<String> list = new ArrayList<String>();
                TableRow row = tBody.tr();

                Input check = row.td().input().type("checkbox");
                check.name("box1");
                check.value(line.get("Id") != null ? line.get("Id").toString() : line.get("id").toString());

                for (View view : views) {
                    view.setIsTitle("1");
                    html = makeViews(list, view, line, html);
                }
                if (!TextUtils.isEmpty(html)) {
                    row.td(html);
                }

                for (String v : list) {
                    TableDataCell td = row.td();
                    td.text(v);
                    String before = StringUtils.substringBefore(v, "</div>");
                    String after1 = StringUtils.substringAfter(before, ">");
                    //字段内容长度过长，鼠标移入显示

                    if ( after1.length() > 20) {
                        if (v.contains("-val")){

                        }else {
                            td.attr("title", after1);
                        }
                    }
                    td.onClick("tableUpdate('" + getFormName() + "'," + line.get("Id") + ");");
                }
                Integer Tablestate = (Integer) line.get("state");
                if (Tablestate != null) {
                    FormStateOpertionList = commonDao.getFormListOperationShow(getCompanyId(), 1, getFormName(), Tablestate);
                    if (FormStateOpertionList != null) {
                        TableDataCell td = row.td();
                        Span span = td.span().styles("white-space: nowrap;");
                        String  Id = line.get("Id")!= null?line.get("Id").toString() : line.get("id").toString();
                        for (Map<String, Object> btnList : FormStateOpertionList) {
                            Integer FormState = (Integer) btnList.get("FormState");

                            if (Tablestate.equals(FormState) || Tablestate == FormState) {
                                A a1 = span.a();
                                if ("_update".equals(btnList.get("OperationType").toString())) {  //修改
                                    a1.text(btnList.get("OperationName").toString());
                                    a1.herf("queryStudentServlet?copformName=" + getFormName() + "&showType=newForm&optype=1&ParamId=" + Id);
                                } else if ("_look".equals(btnList.get("OperationType").toString())) { //查看
                                    a1.text(btnList.get("OperationName").toString());
                                    a1.herf("queryStudentServlet?copformName=" + getFormName() + "&showType=newForm&optype=2&ParamId=" + Id);
                                } else if ("_del".equals(btnList.get("OperationType").toString())) {    //删除
                                    a1.text(btnList.get("OperationName").toString());
                                    a1.herf("javascript:void(0);").onClick("delListForm(" + Id + ",'" + getFormName() + "');");
                                } else if ("_add".equals(btnList.get("OperationType").toString())) {  //添加
                                    a1.text(btnList.get("OperationName").toString());
                                    a1.herf("queryStudentServlet?copformName=" + getFormName() + "&showType=newForm&optype=2&ParamId=" + Id);
                                } else if ("_select".equals(btnList.get("OperationType").toString())) { //跳转到自定义页面
                                    a1.text(btnList.get("OperationName").toString());
                                    String TemplatePage = commonDao.getTemplatePageByOperationId(getCompanyId(), (Integer) btnList.get("OperationId"));
                                    a1.herf(TemplatePage + "?FormName=" + getFormName() + "&id=" + Id + "&NeedState=" + Tablestate);
                                } else {   //其他的操作按钮
                                    // Button del1 = btncontainer.button().addCssClass("layui-btn layui-btn-sm").text(btnList.get("OperationName").toString()).id("elDelete").onClick("del("+getFormName()+")");
                                    a1.text(btnList.get("OperationName").toString());
                                    a1.herf("javascript:void(0);").onClick("updateListBtn(" + Id + ",'" + getFormName() + "','" + btnList.get("AfterProcessState") + "'" + ");");
                                }
                                a1.styles("white-space: nowrap;background-color: #1E9FFF;color: white;padding: 5px;border-radius: 3px;margin-right: 5px;" +
                                        "text-decoration: none;");
                                /*  批量操作按钮  */
                                if (btnList.get("Batch") == "1" || "1".equals(btnList.get("Batch"))) {
                                    //batch.put(btnList.get("OperationName").toString(),btnList.get("OperationName").toString());
                                    // batch.add(btnList.get("OperationName").toString());
                                    batch.put(btnList.get("OperationName").toString(), btnList.get("OperationType").toString());
                                }
                            }
                        }
                    }
                }
                // }
                //tBody.tr(row);
            }
            //去重
            for (String key : batch.keySet()) {
                Button batchOperation = btncontainer.button().addCssClass("layui-btn layui-btn-sm").text(key);//.onClick("batchList(" + getFormName() + ")")
                String str = batch.get(key).toString();
                if ("_del".equals(str) || "_del" == str) { //如果是删除
                    batchOperation.onClick("del('" + getFormName() + "')");
                } else { //其他按钮
                    batchOperation.onClick("batchList('" + getFormName() + "')");
                }
            }
        }else {
            Div div1 = div.div();
            H3 h3 = new H3();
            h3.text("暂无数据");
            div1.h3(h3);
        }
    }

    //手机端没分组
    public void listShow(Div div) {
        if (mDatas != null) {
            List<View> views = getViews();
            for (Map<String, Object> line : mDatas) {
                String html = getHtmlTemplate();
                List<String> list = new ArrayList<>();

                Div item = div.div();
                item.addCssClass(formName + "-item");
                for (View view : views) {
                    html = makeViews(list, view, line, html);
                }
                if (!TextUtils.isEmpty(html)) {
                    item.text(html);
                }
                for (String v : list) {
                    item.text(v);
                }
            }
        }
    }

}

