package showtype;


import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.head.Script;
import common.SQLHelper;
import common.utils.TextUtils;
import dao.MenuDao;
import model.Menu;
import viewtype.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Su on 2017/12/21.
 */
public class ListModifyForm extends CustomForm {

    private Menu Menus;
    private MenuDao menuDao;
//    private BackMarDao backMarDao;
    private String publish; //   1 pc,2 mobile
    private String isShow;  //  不为空,可以修改,   1   替换页面,   2 上下页面
    private List<Map<String, Object>> FormStateOpertionList;
    private List<Map<String, Object>> getFormUpdateFileldList;
    private String Parameter;
    private List<Map<String, Object>>  formStateOperation;

    protected void initDao(SQLHelper pSQLHelper) {
        menuDao=new MenuDao(pSQLHelper);
//        backMarDao=new BackMarDao(pSQLHelper);
    }

    @Override
    protected void loadData(String sql) {

        Menus=menuDao.getMenu(getCompanyId(),getFormName());
        if (Menus!=null){
            publish=Menus.getPublish();
            isShow = Menus.getIsShow();
        }
        if (isShow!=null){
           // FormStateIdList=backMarDao.getFormStateIdList(getCompanyId(),getFormName());
            System.out.println("FormName:"+getFormName());
        }
        if (publish.equals("1")){
           // getFormUpdateFileldList=backMarDao.getFormSearchLsit(getCompanyId(),getFormName());
            System.out.println("getFormUpdateFileldList:>>>>"+getFormUpdateFileldList.toString());
            String setList=getPage().getUrlParameter("map");
            System.out.println("setList==="+setList);
            Menus=menuDao.getMenu(getCompanyId(),getFormName());
            if (Menus!=null){
                publish=Menus.getPublish();
                isShow = Menus.getIsShow();
            }

        }

        if (publish.equals("2")){
            sql=sql+" where u.Id="+getPage().getUserId();
        }



        System.out.println(sql);
        super.loadData(sql);
    }


    @Override
    protected void make(Div div) {

        div.add(new Script("js/newForm.js"));
        div.add(new Script("js/jquery-1.12.4.js"));


    }


    public void list(Div div){
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
