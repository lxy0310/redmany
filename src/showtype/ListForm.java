package showtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.embed.Img;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.sections.A;
import com.sangupta.htmlgen.tags.body.table.TBody;
import com.sangupta.htmlgen.tags.body.table.THead;
import com.sangupta.htmlgen.tags.body.table.Table;
import com.sangupta.htmlgen.tags.body.table.TableRow;
import com.sangupta.htmlgen.tags.body.text.Span;
import com.sangupta.htmlgen.tags.head.Script;
import common.CommonUtils;
import common.SQLHelper;
import common.utils.SQLFixer;
import common.utils.TextUtils;
import dao.FormDao;
import dao.FormFiledDao;
import dao.MenuDao;
import dao.ReplacerDao;
import model.FormFiled;
import model.Menu;
import model.Operation;
import model.Replacer;
import page.CustomForm;
import viewtype.Image;
import viewtype.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static page.Page.platform;

public class ListForm extends CustomForm {
    private static ReplacerDao sReplacer;
    private Menu Menus;
    private MenuDao menuDao;
    private FormDao formDao;
    private FormFiledDao filedDao;
    // private String publish; //   1 pc,2 mobile
    private String isShow;  //  不为空,可以修改,   1   替换页面,   2 上下页面
    private int group;   // 判断是否有分组



    public String one, two, three, four;

    @Override
    public HtmlBodyElement<?> createViews() {
        Div div = new Div();
        div.id(formName);

        make(div);
        //添加分页菜单栏

        Div pageDiv=div.div();
        pageDiv.id("pageDiv");
        pageDiv.attr("width","100%");
        pageDiv.attr("style","text-align:center;padding: 20px 0;");

        A firstPage=pageDiv.a();
        firstPage.id("firstPage");

        // hiddenIndex.value(getPage().getPageIndex()+"");
        firstPage.attr("href","javascript:pageJump('"+getFormName()+"','ListForm',1");
        firstPage.text("首页");
        A prePage=pageDiv.a();
        prePage.id("prePage=");

        // hiddenIndex.value(getPage().getPageIndex()+"");
        prePage.attr("href","javascript:pageJump('"+getFormName()+"','ListForm',"+(getPage().getPageIndex()-1<1?1:getPage().getPageIndex()-1)+")");
        prePage.text("上一页");

        A nextPage=pageDiv.a();
        nextPage.id("nextPage");

        nextPage.attr("href","javascript:pageJump('"+getFormName()+"','ListForm',"+(getPage().getPageIndex()+1>getPage().getPageCount()?getPage().getPageCount():getPage().getPageIndex()+1)+")");
        nextPage.text("下一页");

        A lastPage=pageDiv.a();
        lastPage.id("lastPage");

        lastPage.attr("href","javascript:pageJump('"+getFormName()+"','ListForm',"+getPage().getPageCount()+")");
        lastPage.text("尾页");

        Input goText=pageDiv.input();
        goText.id("goText");
        goText.type("text");
        goText.value(getPage().getPageIndex()+"");
        goText.attr("style","width:20px;height:18px");
        A goPage=pageDiv.a();
        goPage.id("goPage");

        goPage.attr("href","javascript:pageJump('"+getFormName()+"','ListForm','goText')");
        goPage.text("跳转");

        return div;
    }

    //数据模型初始化
    protected void initDao(SQLHelper pSQLHelper) {
        if (sReplacer == null) {
            synchronized (ReplacerDao.class) {
                if (sReplacer == null) {
                    sReplacer = new ReplacerDao(pSQLHelper);
                }
            }
        }
        menuDao = new MenuDao(pSQLHelper);
        formDao = new FormDao(pSQLHelper);
        filedDao = new FormFiledDao(pSQLHelper);
    }

    @Override
    protected void loadData(String sql) {
        Menus = menuDao.getMenu(getCompanyId(), getFormName());

        if (Menus != null) {
            isShow = Menus.getIsShow();
        }
        //查询是否有分组
        group = 0;  //默认没有分组
        List<Map<String, Object>> filedList = filedDao.getFormFeildList(getCompanyId(), formName);
        for (Map<String, Object> filed : filedList) {
            if ((String) filed.get("filedGroup") != null || "".equals(filed.get("filedGroup"))) {
                group = 1;
            }
        }
        super.loadData(sql);
        System.out.println(sql);
        if (mDatas != null) {
            System.out.println("mDatas>>>>>>>>>>>>>>>>>>" + mDatas.toString());
        } else {
            System.out.println("getData()>>>>>>>>>>>>>>>>>>>>>>" + getDatas().toString());
        }
    }

    @Override
    protected void make(Div div) {
       // div.add(new Script("js/commonUtils.js"));
        div.add(new Script("js/jquery-1.12.4.js"));
        //list(div);
//        platform = "1";
        if (platform.equals("1")){//1为后台
            //没有分组
            if (group==0){
                showBack(div);
            }else{
                //showGroupBack(div);
            }
        }else{ // 前端
            list(div);
        }

    }



    /**
     * 后台没有分组
     *
     * @param div
     */
    public void showBack(Div div) {
        List<View> views = getViews();
        Table table = div.table().addCssClass("layui-table");
        THead thead = table.thead();
        TableRow rowTh = new TableRow();  //表头
        for (View view : views) {
            rowTh.td(view.getTitle());
        }
        if (isShow != null) {
            rowTh.td("操作");
        }
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
            if (isShow != null) {
                Span span = row.td().span();
            }
            tBody.tr(row);
        }
    }




    /**
     * 前端没有分组
     *
     * @param div
     */
    public void list(Div div) {
        if (mDatas != null) {
            List<View> views = getViews();
            for (Map<String, Object> line : mDatas) {
                String html = getHtmlTemplate();
                List<String> list = new ArrayList<>();
                Div item = div.div();
                item.addCssClass(formName + "-item");
                String transferParams = get(line, "transferParams");
//            String serviceid = get(line, "serviceid");
                if (transferParams != null) {
                    line.put("transferParams", transferParams.replace("{"+transferParams+"}",transferParams ));
                }
                String onclick = getPage().getDataProvider().getOnClick(this, null, line);
                if (onclick != null) {
                    item.onClick(onclick);
                }
                System.out.println("state:" + line.get("state"));
                int state = (Integer) line.get("state");

                if (state == -666) {
                    item.style("display", "none");
                }
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

    //分组
    private Map<String, List<FormFiled>> groupBillingDataByExcpBatchCode(List<FormFiled> billingList) throws Exception {
        Map<String, List<FormFiled>> resultMap = new HashMap<String, List<FormFiled>>();

        try {
            for (FormFiled filed : billingList) {

                if (resultMap.containsKey(filed.getFiledGroup())) {//map中异常批次已存在，将该数据存放到同一个key（key存放的是异常批次）的map中
                    resultMap.get(filed.getFiledGroup()).add(filed);
                } else {//map中不存在，新建key，用来存放数据
                    List<FormFiled> tmpList = new ArrayList<FormFiled>();
                    tmpList.add(filed);
                    resultMap.put(filed.getFiledGroup(), tmpList);
                }
            }

        } catch (Exception e) {
            throw new Exception("按分组时出现异常", e);
        }
        return resultMap;
    }

}
