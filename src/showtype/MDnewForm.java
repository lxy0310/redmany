package showtype;

import com.sangupta.htmlgen.tags.body.embed.IFrame;
import com.sangupta.htmlgen.tags.body.forms.Button;
import com.sangupta.htmlgen.tags.body.forms.Form;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.grouping.ListItem;
import com.sangupta.htmlgen.tags.body.grouping.UL;
import com.sangupta.htmlgen.tags.body.table.TBody;
import com.sangupta.htmlgen.tags.body.table.THead;
import com.sangupta.htmlgen.tags.body.table.Table;
import com.sangupta.htmlgen.tags.body.table.TableRow;
import com.sangupta.htmlgen.tags.body.text.Italic;
import com.sangupta.htmlgen.tags.body.text.Label;
import com.sangupta.htmlgen.tags.head.Script;
import common.CollectionUtil;
import common.SQLHelper;
import common.utils.TextUtils;
import dao.CommonDao;
import dao.FormDao;
import dao.FormFiledDao;
import dao.MenuDao;
import model.Menu;
import org.apache.commons.lang.StringUtils;
import viewtype.View;

import java.util.*;

public class MDnewForm extends CustomForm {

    private Menu Menus;
    private MenuDao menuDao;
    private CommonDao commonDao;
    private List<Map<String, Object>> FFormList; //主表Form
    private String FFormColumnName;//主表formname
    private Integer IsSFormColumn; //是否有多个主从关系 0 一个主从 普通显示 1 多个主从 选项卡显示
    private  FormUtil formUtil;
    private String ListFeilds;
    private FormDao formDao;
    private FormFiledDao filedDao;
    private String FFormTitle; //主表标题
    private List<Map<String, Object>>  SFormDate;//从表数据
    private String sformName;//从表第一个formname
    private String assoFields; //从表第一个关联字段
    private String ParamId ;// 主表ID
    private String optype;  //optype为1标识修改，2标识查看详情
    private List<String> sformTitle; //所有从表title
    private  Map<String,Object> SFormColumn; //存储从表的forname和关联id

    protected void initDao(SQLHelper pSQLHelper) {
        menuDao=new MenuDao(pSQLHelper);
        commonDao=new CommonDao(pSQLHelper);
        formDao = new FormDao(pSQLHelper);
        filedDao = new FormFiledDao(pSQLHelper);
        optype=getPage().getUrlParameter("optype");
        IsSFormColumn = 0;

    }

    protected void loadData(String sql) {
        ParamId=getPage().getParameter("ParamId"); //获取ID
        sql = sqlGetID(ParamId,sql); //拼接ID
        formUtil = new FormUtil();
        FFormColumnName = formName.split(",")[0]; //主表
        FFormList = formDao.getFormList(getCompanyId(), FFormColumnName);//获取主表form表信息
        System.out.println("FFormList:" + FFormList.toString());
        if (FFormList != null) {
            for (Map<String, Object> line : FFormList) {
               // sql = line.get("Get_data_sql").toString();   //获取主表的sql语句
                System.out.println("sql:"+sql);
                ListFeilds = line.get("List_fields").toString(); //获取主表显示字段
                FFormTitle = line.get("Title").toString();
            }
        }
        Menus=menuDao.getMenu(getCompanyId(),formName);
        String[] formSize = formName.split(",");
        System.out.println(formSize.length);
        String formList = StringUtils.substringAfter(formName,",");
        SFormColumn = sFormList(formList); //存储从表的forname和关联id
        sformTitle = new ArrayList<>();//所有的从表标题
        Map<String, Object> sFormData = new LinkedHashMap<>(); //存储从表的所有form表信息
        for (String key : SFormColumn.keySet()) {
            //根据formname获取form表信息
            model.Form sform = formDao.getForm(getCompanyId(),key);
            if (sform!=null){
                sformTitle.add(sform.getTitle());
            }
            sFormData.put(key,sform);
        }
        sformName = CollectionUtil.getKeyOrNull(sFormData); //从表第一个formname
        assoFields = (String) CollectionUtil.getFirstOrNull(SFormColumn); //从表第一个关联字段
        System.out.println(sformName);
        model.Form form1 = (model.Form) CollectionUtil.getFirstOrNull(sFormData); //从表第一个
        System.out.println(form1);
        //获取从表的数据
        SFormDate = formDao.getFormList(getCompanyId(), FFormColumnName);
        super.loadData(sql);
    }

    protected void make(Div div) {
        div.styles("padding: 20px;background-color: #f2f2f2;");
        div.add(new Script("js/md.js"));
        div.add(new Script("js/jquery-2.1.4.min.js"));
        //主表
        Div panel = div.div().addCssClass("layui-card");//.styles("width:500px;");
        Div header =panel.div().text(FFormTitle).addCssClass("layui-card-header");
        Div body = panel.div().addCssClass("layui-card-body");
        Form saveForm=body.form().addCssClass("layui-form");
        saveForm.id("mdAddForm");
        String filedStr = filedDao.getFormFiledStr(getCompanyId(),formName);  //获取formfiled表的数据
        List<View> views = getViewLists(filedStr);
        String html = getHtmlTemplate();
        List<String> list = new ArrayList<>();
        if (ParamId!=null){
            Tables(views,html,list,saveForm);
}else {
        Table(views,html,list,saveForm);
        }


        //从表
        STable(div);

        //按钮操作
        Div btnDiv=saveForm.div().addCssClass("layui-form-item").styles("margin-left: 180px;");
        Input formname = btnDiv.input("hidden",getFormName()).addCssClass("formName").value(FFormColumnName);
        Input hiddenId = saveForm.input("hidden",ParamId).id("mdID");
        hiddenId.attr("name","Id");
        Button saveBtn = btnDiv.button().text("提交").addCssClass("layui-btn saveBtn");//保存按钮
        saveBtn.attr("type","button");
        String firstFormName=getFormName().split(",")[0];
        saveBtn.onClick("gotoPage('submit:"+firstFormName+",MDnewForm',null);");
        Button cancelBtn = btnDiv.button().text("取消").addCssClass("layui-btn").onClick("javascript:history.go(-1);location.reload();"); //取消按钮

        }
    //主表
    public  void Table(List<View> views,String html,List<String> list, Form saveForm){
        Map<String,String> map = new HashMap<>();
        // List<String> list1 = new ArrayList<>();
        for (View view : views) {
            if ("TextNoTitle".equals(view.getType())){
                view.setType("text");
            }
            view.setIsValue("1");
            html = addMakeViews(list, view, null, html);
        }
        if (!TextUtils.isEmpty(html)) {
            saveForm.text(html);
        }
        System.out.println(list);
        for(String key : list){ //String v : list
            Div div = saveForm.div().addCssClass("layui-form-item").styles("line-height: 30px;height: 30px;");//margin: 20px auto;width: 800px;
            //Label label = div.label().addCssClass("layui-form-label").styles("width:200px;");
            Div div1 = div.div().addCssClass("layui-input-block");//.styles("width: 700px;padding-top: 7px;margin-left: 30px;");
            //label.text(key);
            // String value = map.get(key).toString();
            key = key.replaceAll("<label>","<label class=\"labelRight\">");
            div1.text(key);
            // System.out.println(key+"  "+value);
        }
    }

    public  void Tables(List<View> views,String html,List<String> list, Form saveForm){
        if (mDatas!=null){
            for (Map<String, Object> line : mDatas){
                Map<String,String> map = new HashMap<>();
                // List<String> list1 = new ArrayList<>();
                for (View view : views) {
                    if ("TextNoTitle".equals(view.getType())){
                        view.setType("text");
                    }
                    html = addMakeViews(list, view, line, html);
                }
                if (!TextUtils.isEmpty(html)) {
                    saveForm.text(html);
                }
                System.out.println(list);
                for(String key : list){ //String v : list
                    Div div = saveForm.div().addCssClass("layui-form-item").styles("line-height: 30px;height: 30px;");//margin: 20px auto;width: 800px;
                    //Label label = div.label().addCssClass("layui-form-label").styles("width:200px;");
                    Div div1 = div.div().addCssClass("layui-input-block");//.styles("width: 700px;padding-top: 7px;margin-left: 30px;");

                    key = key.replaceAll("<label>","<label class=\"labelRight\">");
                    div1.text(key);
                    // System.out.println(key+"  "+value);
                }
            }

        }

    }

    //从表的formname、关联字段
    private Map<String,Object> sFormList(String formname){
        String[] formSize = formname.split(",");

        System.out.println(formSize.length);
        for (int i = 0; i < formSize.length; i++) {
            System.out.println(formSize[i]);
        }
        Map sFormColumn = new LinkedHashMap<>();
        List<String> key = new ArrayList<>();
        List<String> value = new ArrayList<>();
        for (int i = 0; i <formSize.length ; i++) {
            if (i%2!=0){ //奇数
                value.add(formSize[i]);
            }else{
                key.add(formSize[i]);
            }
        }

        for (int i = 0; i <key.size() ; i++) {
            IsSFormColumn++;
            System.out.println(IsSFormColumn);
            sFormColumn.put(key.get(i),value.get(i));
            System.out.println("key:"+key.get(i) +"\t"+ "value:" + value.get(i));

        }
        return sFormColumn;
    }

    private void  STable(Div div){
        // String ListFeilds = getFormFieldNames();
        Map<String,Object> popup = new LinkedHashMap<>();
        Div sdiv = div.div().styles("background-color: white;");
        System.out.println(IsSFormColumn);

            Div tab = sdiv.div().addCssClass("layui-tab layui-tab-brief");
            tab.filter("sform");
            //选项卡标题
            UL ul = tab.ul();
            ul.addCssClass("layui-tab-title");
            for (int j = 0; j < sformTitle.size(); j++) {
                ListItem li = new ListItem();
                li.text(sformTitle.get(j));
                if (j ==1){
                    li.addCssClass("layui-this");
                }
                ul.li(li);
            }
            //选项卡内容
            Div tabContent = tab.div();
            tabContent.addCssClass("layui-tab-content");

            for (String key : SFormColumn.keySet()) {
                Div item = tabContent.div();
                item.addCssClass("layui-tab-item");
                // Div divtext =item.text(key+"\t"+SFormColumn.get(key)).styles("width:400px");
                if (key==sformName || key.equals(sformName)){
                    item.addCssClass("layui-tab-item layui-show");
                }

                Button addMDform1 = item.button().addCssClass("layui-btn"); //新增子表按钮
                String url ="queryStudentServlet?copformName="+key+"&showType=NewForm&mdAssoWord="+SFormColumn.get(key)+":"+"151";
                addMDform1.onClick("addMDform('"+url+"');");
             /*   if (ParamId==null || "".equals(ParamId)){
                    addMDform1.onClick("addShow('"+FFormTitle+"');");
                }else {
                    addMDform1.onClick("addMDform('"+url+"');");
                }*/
                Italic i = new Italic();
                i.text("&#xe608;").addCssClass("layui-icon");
                addMDform1.italic(i);
                addMDform1.text("新增");
                addMDform1.attr("type","button");
                IFrame iFrame =item.iframe("queryStudentServlet?copformName="+key+ "&showType=ListModifyForm&mdAssoWord="+SFormColumn.get(key)+":"+ParamId);
                iFrame.attr("width","100%");
                iFrame.height(500);
            }
    }




}
