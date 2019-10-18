package showtype;

import com.sangupta.htmlgen.tags.body.forms.Button;
import com.sangupta.htmlgen.tags.body.forms.Form;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.grouping.ListItem;
import com.sangupta.htmlgen.tags.body.grouping.UL;
import com.sangupta.htmlgen.tags.body.sections.A;
import com.sangupta.htmlgen.tags.body.sections.H3;
import com.sangupta.htmlgen.tags.body.table.Table;
import com.sangupta.htmlgen.tags.body.text.Label;
import com.sangupta.htmlgen.tags.head.Script;
import com.sun.org.apache.bcel.internal.generic.DLOAD;
import common.SQLHelper;
import common.utils.TextUtils;
import dao.BackMarDao;
import dao.FormDao;
import dao.FormFiledDao;
import model.FormFiled;
import org.apache.commons.lang.StringUtils;
import viewtype.View;

import javax.swing.text.html.CSS;
import java.util.*;
import java.util.stream.Collectors;

public class NewForm extends FreeForm {

    private BackMarDao backMarDao;
    private FormFiledDao filedDao;
    private FormDao formDao;
    private List<Map<String,Object>> formFeildList;
    private String optype;  //optype为1标识修改，2标识查看详情
    private String paramId;
    private int filedGroup;  //是否有分组
    private String mdAssoWord;
    private String TableName; //表名
    private String forms;  //form表数据
    private model.Form formList;
    private String FFormColumnName; //主表
    //private model.Form forms;

    @Override
    public void initDao(SQLHelper pSqlHelper) {
        paramId=getPage().getParameter("ParamId");
        filedGroup = 0; //默认没分组
        System.out.println(paramId+"ID-----------");
        super.initDao(pSqlHelper);
        backMarDao = new BackMarDao(pSqlHelper);
        filedDao = new FormFiledDao(pSqlHelper);
        formDao = new FormDao(pSqlHelper);
        optype=getPage().getUrlParameter("optype");
    }

    protected void loadData(String sql) {
        formList = formDao.getForm(companyId,formName);
        System.out.println(sql);
        sql = sqlGetIDs(paramId,sql,formList.getReplaceName());  //拼接参数ID
        mdAssoWord = getPage().getParameter("mdAssoWord");
        System.out.println(mdAssoWord);
        sql = sqlGetMD(mdAssoWord,sql,formList.getReplaceName());
        System.err.println(sql);
        List<Map<String, Object>> formFeildList=filedDao.getFormFeildList(getCompanyId(),formName);
        //判断是否有分组
        filedGroup = 0;  //默认没有分组
        for (Map<String, Object> filed : formFeildList) {
            if ((String) filed.get("filedGroup") != null || "".equals(filed.get("filedGroup"))) {
                filedGroup = 1;
            }
        }
        if (formName.contains(",")){
            FFormColumnName = formName.split(",")[0]; //主表
          //  forms =formDao.getForm(getCompanyId(),formName);
        }else {
          //   forms =formDao.getForm(getCompanyId(),formName);
        }


        //TableName = forms.getTable_name();
        super.loadData(sql);
    }

    protected void make(Div div) {
        div.add(new Script("js/commonUtils.js"));
        Div container = div.addCssClass("layui-container");
        Form saveForm=container.form();
        saveForm.id("addForm").addCssClass("layui-form");
        saveForm.styles("width:100%;text-align:center;");
        Input formname = saveForm.input("hidden",getFormName()).addCssClass("formName"); //formname
        Input tableName = saveForm.input("hidden",TableName).addCssClass("TableName"); //表名
        if (mdAssoWord!=null){
            String[] t=mdAssoWord.split(":");
            Input mdAssoWords =saveForm.input("hidden",mdAssoWord).addCssClass("mdAssoWords");  //双列表的子表关联字段
            mdAssoWords.name(t[0]+"_md");
            mdAssoWords.value(t[1]);
        }
//        Input mdAssoWords =saveForm.input("hidden",mdAssoWord).addCssClass("mdAssoWords");  //双列表的子表关联字段
        String filedStr = filedDao.getFormFiledStr(getCompanyId(),formName);
        List<View> views = getViewLists(filedStr);
        String html = getHtmlTemplate();
        List<String> list = new ArrayList<>();

        if(filedGroup == 0){ //没有分组
            if (optype!=null) {  //修改 、查看
                isEdit_Show(views,html,list,saveForm);
            }else{  //新增
                isAdd(views,html,list,saveForm);
            }
        }else{  //分组
            saveForm.styles("background-color: #F2F2F2;padding-bottom: 50px;");
            Map<String, List<View>> resultMap = new LinkedHashMap<>();
            try {
                for (View filed : views) {
                    if (resultMap.containsKey(filed.getFiledGroup())) {//map中异常已存在，将该数据存放到同一个key（key存放的是异常）的map中
                        resultMap.get(filed.getFiledGroup()).add(filed);
                    } else {//map中不存在，新建key，用来存放数据
                        List<View> tmpList = new ArrayList<View>();
                        tmpList.add(filed);
                        resultMap.put(filed.getFiledGroup(), tmpList);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(resultMap.toString());
            group(resultMap,saveForm,optype,html,"panel");//tab
        }
        Div btnDiv=saveForm.div();
        btnDiv.styles("text-align: center;");
        Button saveBtn=btnDiv.button();  //保存
        if (optype!=null){ //参数
            Input input=btnDiv.input("hidden",getFormName()).addCssClass("optype").value(optype);
        }
        if ("1".equals(optype)){    //修改
            Input input=btnDiv.input("hidden",getFormName()).addCssClass("paramId").value(paramId);
        } else if ("2".equals(optype)){     //查看
            saveBtn.styles(" pointer-events:none;");//禁止鼠标事件
        }
        if (mdAssoWord!=null){
            saveBtn.addCssClass("mdsaveBtn");
            saveBtn.text("提交");
            saveBtn.styles("background-color: #1E9FFF;border-radius: 2px;padding: 5px 10px;color:#fff;");
            saveBtn.onClick("gotoPage('submit:"+getFormName()+",newForm',null);");
            Button cancelBtn = btnDiv.button();
            cancelBtn.text("取消");
            cancelBtn.styles("background-color: #1E9FFF;border-radius: 2px;padding: 5px 10px;color:#fff;");
            cancelBtn.onClick("javascript:history.go(-1);location.reload();");
        }else {
            saveBtn.addCssClass("saveBtn");
            saveBtn.text("提交");
            saveBtn.styles("background-color: #1E9FFF;border-radius: 2px;padding: 5px 10px;color:#fff;");
            saveBtn.onClick("gotoPage('submit:"+getFormName()+",newForm',null);");
            Button cancelBtn = btnDiv.button();
            cancelBtn.text("取消");
            cancelBtn.styles("background-color: #1E9FFF;border-radius: 2px;padding: 5px 10px;color:#fff;");
            cancelBtn.onClick("javascript:history.go(-1);location.reload();");
        }
        saveBtn.attr("type","button");
    }

    //分组
    private void group(Map<String, List<View>> resultMap,Form saveForm,String optype,String html,String groupTab){
        // Div div1 = saveForm.div();
        //如果是分组的数据
        Div div1 = saveForm.div();
        //groupTab = "tab";
        if (groupTab=="tab"){
            div1.addCssClass("layui-tab layui-tab-brief");
            div1.filter("test1");
            UL ul = div1.ul();
            ul.addCssClass("layui-tab-title");
            for (String key : resultMap.keySet()) {
                ListItem li = new ListItem();
                if (key == null || "".equals(key)) {  //没分组数据
                    li.text("默认分组");
                }else {
                    if (key.contains("[^]")){
                        String before = StringUtils.substringBefore(key, "[^]");
                        li.text(before);
                        String after = StringUtils.substringAfter(key,"[^]");
                        li.layid(after);
                        if (key != null & !"".equals(key)) {
                            if (after == "1" || "1".equals(after)) {
                                li.addCssClass("layui-this");
                            }
                        }
                    }else {
                        li.text(key);
                    }
                }
                ul.li(li);
            }
            Div content = div1.div().addCssClass("layui-tab-content");
            for (String key : resultMap.keySet()) {
                Div div2 = content.div().addCssClass("layui-tab-item");
                if (key != null & !"".equals(key)) {
                    if (key.contains("[^]")){
                        String after = StringUtils.substringAfter(key,"[^]");
                        if (after == "1" || "1".equals(after)){
                            div2.addCssClass("layui-show");
                        }
                    }
                }
                for (View v : resultMap.get(key)) {
                    // as[^]asdf
                    Div div3 = div2.div().addCssClass("layui-form-item");
                    Label label = div3.label();
                    label.text(v.getTitle()).addCssClass("layui-form-label").styles("width:100px").toString();
                    html = makeView(v, null, html);
                    Div div4 = div3.div().addCssClass("layui-input-block").styles("margin-left: 35px;");
                    div4.text(html);
                }
            }
        }else {
            div1.styles("padding: 20px;background-color: #F2F2F2;margin-bottom:50px;");
            Div row = div1.div().addCssClass("layui-row layui-col-space15");
            for (String key : resultMap.keySet()) {
                if (key == null || "".equals(key)) {  //没分组数据
                    Div divmd12 = row.div().addCssClass("layui-col-md4");
                    Div card1 = divmd12.div();
                    card1.addCssClass("layui-card");
                    Div header1 = card1.div().addCssClass("layui-card-header");
                    Div body1 = card1.div().addCssClass("layui-card-body");
                    for (View v : resultMap.get(key)) {
                        Div div2 = body1.div().addCssClass("layui-form-item");
                        html = makeView(v, null, html);
                        Div div3 = div2.div().addCssClass("layui-input-block").styles("margin-left: 35px;");
                        div3.text(html);
                    }
                } else { //分组
                    Div divmd6 = row.div().addCssClass("layui-col-md4");
                    Div card = divmd6.div();
                    card.addCssClass("layui-card");
                    String value = resultMap.get(key).toString();//
                    System.out.println("key:" + key + " vlaue:" + value);
                    Div header = card.div().addCssClass("layui-card-header");
                    String before = StringUtils.substringBefore(key, "[^]");
                    header.text(before);

                    Div body = card.div().addCssClass("layui-card-body");
                    List<String> list = new ArrayList<>();
                    Map<String, String> listShow = new HashMap<>();
                    for (View v : resultMap.get(key)) {
                        Div div2 = body.div().addCssClass("layui-form-item");
                        /*Label label = div2.label();
                        label.text(v.getTitle()).addCssClass("layui-form-label").styles("width:100px").toString();*/
                        html = makeView(v, null, html);
                        Div div3 = div2.div().addCssClass("layui-input-block");
                        div3.text(html);
                    }
                }
                System.out.println();

            }
        }
    }

    //修改，查看
    private void isEdit_Show(List<View> views,String html,List<String> list, Form saveForm){
        Div layuiRow = saveForm.div().addCssClass("layui-row").styles("margin-top:10px;");
        if (mDatas!=null){
            Map<String,String> map = new HashMap<>();
            for (Map<String, Object> line : mDatas) {
                System.out.println(line.toString());
                if ("1".equals(optype)) {    //修改
                    String Modify_fields = formList.getModify_fields();

                    for (View view : views) {
                        if (!Modify_fields.contains("view")){
                            view.setWapAttribute("");
                        }
                        html = addMakeViewMap(map, view, line, html);
                    }
                }else{ //查看
                    for (View view : views) {
                        html = addMakeViewMap(map, view, line, html);
                    }
                }
                if (!TextUtils.isEmpty(html)) {
                    saveForm.text(html);
                }
                System.out.println(list);
                for(String key : map.keySet()){
                    Div div= null;
                    if (formList.getRow()!=null){
                        Div  divs = layuiRow.div().addCssClass("layui-col-xs6 layui-col-sm6 layui-col-md4");
                        div = divs.div().addCssClass("layui-form-item");
                    }else {
                        div = layuiRow.div().addCssClass("layui-form-item");
                    }
                    Div div1 =div.div();
                    String value = map.get(key).toString();
                    if (!value.contains("type=\"File\"")){
                        div1.styles("height: 30px;line-height: 30px;" );
                    }

                 /*   Div div = saveForm.div().addCssClass("layui-form-item");
                    Div div1 = div.div().addCssClass("layui-input-block");*/
                    key = key.replaceAll("<label>","<label class=\"labelRight\">");
                    div1.text(value);
                }
            }
        }
    }

    //新增
    private void isAdd(List<View> views,String html,List<String> list, Form saveForm){
        Div layuiRow = saveForm.div().addCssClass("layui-row").styles("margin-top:10px;");
        for (View view : views) {
            view.setIsValue("1");
            html = makeViews(list, view, null, html);
        }
        if (!TextUtils.isEmpty(html)) {
            saveForm.text(html);
        }
        for (String v : list) {
            Div div= null;
            if (formList.getRow()!=null){
                Div  divs = layuiRow.div().addCssClass("layui-col-xs6 layui-col-sm6 layui-col-md4");
                div = divs.div().addCssClass("layui-form-item");
            }else {
                div = layuiRow.div().addCssClass("layui-form-item");
            }
            Div div1 = div.div().styles("height: 30px;line-height: 30px;" );
            div1.addCssClass("layui-input-block").styles("margin:0px;");
            v = v.replaceAll("<label>","<label class=\"labelRight\">");
            div1.text(v);
        }
    }


}
