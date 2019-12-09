package showtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.embed.Img;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.text.Span;
import common.SQLHelper;
import dao.FormDao;
import dao.MenuDao;
import model.Form;
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
    private FormDao formDao;

    private List<Menu> mMenus;
    private Form getForms;

    @Override
    public void initDao(SQLHelper pSqlHelper) {
        super.initDao(pSqlHelper);
        menuDao = new MenuDao(pSqlHelper);
        formDao = new FormDao(pSqlHelper);
    }

    @Override
    protected void initData() {
        getForms = formDao.getForm(getCompanyId(),formName);
        //查菜单表
        mMenu = menuDao.getMenu(getCompanyId(), getFormName());
        System.out.println("==========mMenu========" + mMenu);
        mMenus = menuDao.getMenuList(getCompanyId(), getFormName()); //获取子菜单
        System.out.println("==========mMenus========" + mMenus);
        //倒序，移除不需要的
        mDatas = new ArrayList<>();
        for (int i = 0; i < mMenus.size(); i++) {
            Menu menu = mMenus.get(i);
            Map<String, Object> data = new HashMap<>();
            data.put("MenuName", menu.getMenuName());
            data.put("FormName", menu.getFormName());
            data.put("ShowType", menu.getShowType());
            data.put("Icon",menu.getIcon());
            data.put("transferParams",menu.getTransferParams());
            data.put("target",menu.getTarget());
            data.put("FormName",menu.getFormName());
            data.put("ShowType",menu.getShowType());
            data.put("Id",menu.getId());
            mDatas.add(i, data);
        }
        System.out.println("==========MenuForm--mDatas========" + mDatas);
    }

    @Override
    public HtmlBodyElement<?> createViews() {
        //返回内容
        Div div = new Div();
        div.id(getFormName());
        String colmunCount = getForms.getColumnCount();//每一列显示多少个
        String rowCount = getForms.getRowCount();//显示多少行
        String dataCounts = getForms.getDataCounts(); //显示多少条数据
        //单位转换
        Integer s1 = 0 ;
        if (colmunCount != null && !"".equals(colmunCount.trim())){
            s1 = Integer.valueOf(colmunCount);
        }
        Integer s2 = 0 ;
        if (rowCount != null && !"".equals(rowCount.trim())){
            s2 = Integer.valueOf(rowCount);
        }
        Integer s3 = 0 ;
        if (dataCounts != null && !"".equals(dataCounts.trim())){
            s3 = Integer.valueOf(dataCounts);
        }
        System.out.println(mDatas.size());
        int counts = mDatas.size();
        double width = 0 ;

        if (s1!=null && !"".equals(s1) && s1 != 0){
          //  System.out.println("进来了1");
            width = 100 / s1;
        }else if (s2!=null && !"".equals(s2) && s2 != 0){
          //  System.out.println("进来了2");
          //  System.out.println(mDatas.size());
            double as  = counts*1.0 / s2;  //
            double as1 = Math.ceil(as);
            width = 100 / as1 ;
        }
        if ((s1!=null && !"".equals(s1) && s1 != 0) && (s2!=null && !"".equals(s2) && s2 != 0)){
            int num = s1 * s2;
            if (num<=counts){
                counts = num;
            }
        }else if (s3!=null && !"".equals(s3) && s3 != 0){
            counts = s3;
        }

        for (int i = 0; i < counts; i++) {
          //  System.out.println(mDatas.get(i).get("Icon").toString());
            Span span = div.span();
            span.styles(" width: "+width+"%;" + "float: left;text-align: center;margin-top: 20px;");
           // a.onClick("gotoPage('isNeedLogin=1[^]goto:" + menu.getFormName() + "," + menu.getShowType() + "');");
            span.onClick("gotoPage('goto:" + mDatas.get(i).get("FormName") + "," +  mDatas.get(i).get("ShowType") +"');");
            String target = mDatas.get(i).get("target") != null ? mDatas.get(i).get("target").toString() : null;
            String transferParams =  mDatas.get(i).get("transferParams") !=null ? mDatas.get(i).get("transferParams").toString():null;
            for (String filed : mDatas.get(i).keySet()) { //formfiled
                if(transferParams != null && transferParams.indexOf("{"+filed+"}")>=0){
                    transferParams=transferParams.replace("{"+filed+"}", mDatas.get(i).get(filed).toString());
                    System.out.println(transferParams);
                }
            }
            if ( (target !=null && "".equals(target.trim()))&& (transferParams !=null && "".equals(transferParams.trim()))){
                span.onClick("gotoPage('"+mDatas.get(i).get("target").toString()+"goto:" + mDatas.get(i).get("FormName") + "," +  mDatas.get(i).get("ShowType") + "','"+transferParams+"');");
            }
            else  if (transferParams !=null){
                span.onClick("gotoPage('goto:" + mDatas.get(i).get("FormName") + "," +  mDatas.get(i).get("ShowType") + "','"+transferParams+"');");
            }
            else if (target !=null){
                span.onClick("gotoPage('"+mDatas.get(i).get("target").toString()+"goto:" + mDatas.get(i).get("FormName") + "," +  mDatas.get(i).get("ShowType")+"');");
            }else{
                span.onClick("gotoPage('goto:" + mDatas.get(i).get("FormName") + "," +  mDatas.get(i).get("ShowType")+"');");
            }

            Img img1 = span.img("http://oa.redmany.com:50003/document/"+mDatas.get(i).get("Icon").toString());
            img1.styles("width:30%");
            Div div1 = span.div();
            div1.text(mDatas.get(i).get("MenuName").toString());
        }
        return div;
    }
}
