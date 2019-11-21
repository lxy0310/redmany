package showtype;

import com.sangupta.htmlgen.tags.body.forms.Button;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.sections.A;
import com.sangupta.htmlgen.tags.body.table.TBody;
import com.sangupta.htmlgen.tags.body.table.THead;
import com.sangupta.htmlgen.tags.body.table.Table;
import com.sangupta.htmlgen.tags.body.table.TableRow;
import com.sangupta.htmlgen.tags.body.text.Span;
import common.SQLHelper;
import common.utils.TextUtils;
import dao.CommonDao;
import page.CustomForm;
import viewtype.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public  class FormUtil extends CustomForm {
    private CommonDao commonDao;
    private List<Map<String, Object>> FormStateOpertionList;
    protected void initDao(SQLHelper pSQLHelper) {
        commonDao = new CommonDao(pSQLHelper);
    }

    protected void loadData(String sql) {
        super.loadData(sql);
    }

    public void showListBack(Div div,List<View> views,List<Map<String, Object>> mDatas){
       // List<View> views = getViews();
        Div head = div.div().addCssClass("layui-table-tool");
        Div temp = head.div().addCssClass("layui-table-tool-temp");
        Div btncontainer = temp.div().addCssClass("layui-btn-container");
        Button add = btncontainer.button().addCssClass("layui-btn layui-btn-sm layui-btn-normal").text("添加");
        // Button del = btncontainer.button().addCssClass("layui-btn layui-btn-sm").text("删除").onClick("del('" + getFormName() + "')");;
        //生成批量操作按钮

        Table table = div.table().addCssClass("table");
        THead thead = table.thead();
        TableRow rowTh = new TableRow();  //表头

        Div sel = rowTh.td().div().addCssClass("layui-input-inline");
        sel.styles("white-space: nowrap;");
        Span selAll = sel.span().id("as").text("全选");
        Input checkbox = sel.input();
        checkbox.id("box");
        checkbox.type("checkbox");
        checkbox.onClick("my()");

        rowTh.td("操作");
        thead.tr(rowTh);
        TBody tBody = table.tbody();
        Map<String,Object> batch = new HashMap<>();//承载批量按钮
        for (Map<String, Object> line : mDatas) {
            String html = getHtmlTemplate();
            List<String> list = new ArrayList<String>();
            TableRow row = new TableRow();

            Input check = row.td().input().type("checkbox");
            check.name("box1");
            check.value(line.get("Id").toString());

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
            Integer Tablestate = (Integer) line.get("state");
            System.out.println(line.get("state"));
            System.out.println(Tablestate);

            if (Tablestate != null) {
                FormStateOpertionList = commonDao.getFormListOperationShow(getCompanyId(), 1, getFormName(), Tablestate);
                if (FormStateOpertionList != null) {
                    Span span = row.td().span().styles("white-space: nowrap;");
                    for (Map<String, Object> btnList : FormStateOpertionList) {
                        //    Span span = span1.span();
                        Integer FormState = (Integer) btnList.get("FormState");

                        if (Tablestate.equals(FormState) || Tablestate == FormState) {
                            A a1 = span.a();
                            if ("_update".equals(btnList.get("OperationType").toString())) {  //修改
                                a1.text(btnList.get("OperationName").toString());
                                a1.herf("queryStudentServlet?copformName=" + getFormName() + "&showType=newForm&optype=1&ParamId=" + line.get("Id"));
                            } else if ("_look".equals(btnList.get("OperationType").toString())) { //查看
                                a1.text(btnList.get("OperationName").toString());
                                a1.herf("queryStudentServlet?copformName=" + getFormName() + "&showType=newForm&optype=2&ParamId=" + line.get("Id"));
                            } else if ("_del".equals(btnList.get("OperationType").toString())) {    //删除
                                a1.text(btnList.get("OperationName").toString());
                                a1.herf("javascript:void(0);").onClick("delListForm(" + line.get("Id") + ",'" + getFormName() + "');");
                            } else if ("_add".equals(btnList.get("OperationType").toString())) {  //添加
                                a1.text(btnList.get("OperationName").toString());
                                a1.herf("queryStudentServlet?copformName=" + getFormName() + "&showType=newForm&optype=2&ParamId=" + line.get("Id"));
                            } else if ("_select".equals(btnList.get("OperationType").toString())) { //跳转到自定义页面
                                a1.text(btnList.get("OperationName").toString());
                                String TemplatePage = commonDao.getTemplatePageByOperationId(getCompanyId(), (Integer) btnList.get("OperationId"));
                                a1.herf(TemplatePage + "?FormName=" + getFormName() + "&id=" + line.get("Id") + "&NeedState=" + Tablestate);
                            } else {   //其他的操作按钮
                                // Button del1 = btncontainer.button().addCssClass("layui-btn layui-btn-sm").text(btnList.get("OperationName").toString()).id("elDelete").onClick("del("+getFormName()+")");
                                a1.text(btnList.get("OperationName").toString());
                                a1.herf("javascript:void(0);").onClick("updateListBtn(" + line.get("Id") + ",'" + getFormName() + "','" + btnList.get("AfterProcessState") + "'" + ");");
                            }
                            a1.styles("white-space: nowrap;background-color: #1E9FFF;color: white;padding: 5px;border-radius: 3px;margin-right: 5px;" +
                                    "text-decoration: none;");
                            /*  批量操作按钮  */
                            if (btnList.get("Batch") == "1" || "1".equals(btnList.get("Batch"))) {
                                //batch.put(btnList.get("OperationName").toString(),btnList.get("OperationName").toString());
                                // batch.add(btnList.get("OperationName").toString());
                                batch.put(btnList.get("OperationName").toString(),btnList.get("OperationType").toString());
                            }
                        }
                    }
                }
            }
            // }
            tBody.tr(row);
        }
        //去重
        for (String key : batch.keySet()) {
            Button batchOperation = btncontainer.button().addCssClass("layui-btn layui-btn-sm").text(key);//.onClick("batchList(" + getFormName() + ")")
            String str = batch.get(key).toString();
            if ("_del".equals(str )|| "_del" == str){ //如果是删除
                batchOperation.onClick("del('" + getFormName() + "')");
            }else{ //其他按钮
                batchOperation.onClick("batchList('" + getFormName() + "')");
            }
        }
    }


}
