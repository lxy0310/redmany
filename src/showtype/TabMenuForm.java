package showtype;


import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import common.SQLHelper;
import dao.MenuDao;
import model.Menu;

import java.util.List;

/**
 * Created by Suhaibo on 2018/2/23.
 */
public class TabMenuForm extends ParentForm {

    private MenuDao menuDao;
    private Menu mMenu;
    private List<Menu> mMenus;

    @Override
    public void initDao(SQLHelper pSqlHelper) {
        super.initDao(pSqlHelper);
        menuDao = new MenuDao(pSqlHelper);
    }

    @Override
    protected void initData() {
        //查菜单表
        mMenu = menuDao.getMenu(getCompanyId(), getFormName());
        System.out.println("==========mMenu========" + mMenu);
        mMenus = menuDao.getMenuList(getCompanyId(), getFormName());
        System.out.println("==========mMenus========" + mMenus);

    }

    @Override
    public HtmlBodyElement<?> createViews() {
        //返回内容
        Div div = new Div();
        div.id(getFormName());
        Div topTitle = div.div();
        topTitle.addCssClass("top-title");
        topTitle.text(mMenu.getMenuName());
        Div wrapper =div.div();
        wrapper.addCssClass("guide-wrapper");
        int width = 100 / mMenus.size();
        for (int i = 0; i < mMenus.size(); i++) {
            Div title = wrapper.div();
            title.addCssClass("menus-"+i);
            title.style("width",width+ "%");
            title.style("display","inline-block");
            title.style("text-align","center");
            title.style("font-size","14px");
            title.text(mMenus.get(i).getMenuName());
            String formName=mMenus.get(i).getFormName();
            String showType=mMenus.get(i).getShowType();
            title.onClick("changeGuide('"+formName+"','"+showType+"')");

        }
        Div content = div.div();
        content.addCssClass("content");
        return div;
    }
}
