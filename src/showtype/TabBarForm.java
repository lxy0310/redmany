package showtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import common.APPConfig;
import common.SQLHelper;
import dao.MenuDao;
import model.Menu;
import viewtype.ParentView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabBarForm extends ParentForm {
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
        mMenus = menuDao.getMenuList(getCompanyId(), getFormName());
        //倒序，移除不需要的
        mDatas = new ArrayList<>();
        if(APPConfig.DEBUG) {
            System.err.println("mMenus=" + mMenus);
        }
        for (int i = 0; i < mMenus.size(); i++) {
            Menu menu = mMenus.get(i);
            Map<String, Object> data = new HashMap<>();
            data.put("MenuName", menu.getMenuName());
            data.put("FormName", menu.getFormName());
            data.put("ShowType", menu.getShowType());
            System.out.println(menu.getMenuName()+"----------"+menu.getFormName()+"----------"+menu.getShowType());
            mDatas.add(i, data);
        }
        for (int i = mMenus.size() - 1; i >= 0; i--) {
            Menu menu = mMenus.get(i);
//            if ("分类".equals(menu.getMenuName())) {
//                mMenus.remove(i);
//                System.err.println(menu);
//            } else if ("客服".equals(menu.getMenuName())) {
//                mMenus.remove(i);
//                System.err.println(menu);
//            }
        }
    }

    @Override
    public HtmlBodyElement<?> createViews() {
        //返回内容
        Div div = new Div();
        div.id(getFormName());
        div.addCssClass("navbar-fixed-bottom");
        div.styles("font-size: 0px;");
        Div ul = div.div();
        ul.id("nav-list");
        int w = 100 / mMenus.size();
        for (int i = 0; i < mMenus.size(); i++) {
            Menu menu = mMenus.get(i);
            //按照百分比分开
            Div li = ul.div();
            li.addCssClass("nav-item");
            li.style("display", "inline-block");
            li.style("width", w + "%");
            li.style("font-size", "14px");
            li.style("text-align", "center");

            Div a = li.div();
            a.onClick("gotoPage('isNeedLogin=1[^]goto:" + menu.getFormName() + "," + menu.getShowType() + "');");
            a.img(ParentView.IMAGE_PRE + menu.getIcon()).addCssClass("footer-item");
            a.div().text(menu.getMenuName());

            if ((getPage().getCopformName() != null && getPage().getCopformName().equals(menu.getFormName()))
                    || (getPage().getCopformName() == null && i == 0)) {
                li.addCssClass("active");
            }
        }
        return div;
    }
}
