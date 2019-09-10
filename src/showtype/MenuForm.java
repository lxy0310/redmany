package showtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import common.APPConfig;
import common.SQLHelper;
import dao.MenuDao;
import model.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Suhaibo on 2018/2/23.
 */
public class MenuForm extends CustomForm {

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
            mDatas.add(i, data);
        }
        System.out.println("==========MenuForm--mDatas========" + mDatas);
    }

    @Override
    public HtmlBodyElement<?> createViews() {
        //返回内容
        Div div = new Div();
        div.id(getFormName());
        return div;
    }
}
