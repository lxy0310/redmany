package showtype;

import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.head.Script;
import common.SQLHelper;
import dao.CommonDao;
import dao.MenuDao;
import model.Menu;

public class MDnewForm extends CustomForm {

    private Menu Menus;
    private MenuDao menuDao;
    private CommonDao commonDao;
    private String FFormColumnName;//主表formname
    private Integer formList;


    protected void initDao(SQLHelper pSQLHelper) {
        menuDao=new MenuDao(pSQLHelper);
        commonDao=new CommonDao(pSQLHelper);
    }

    protected void loadData(String sql) {

        Menus=menuDao.getMenu(getCompanyId(),getFormName());
        FFormColumnName = formName.split(",")[0]; //主表
        String[] formSize = formName.split(",");
        System.out.println(formSize.length);
        super.loadData(sql);
    }

    protected void make(Div div) {
        div.add(new Script("js/MDnewForm.js"));
        div.add(new Script("js/jquery-2.1.4.min.js"));
        Div div1=div.div().idAndName("divCon");
        div1.input("hidden",getFormName()).id("newFormColum");


    }


}
