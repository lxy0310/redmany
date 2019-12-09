package showtype;

import com.sangupta.htmlgen.tags.body.grouping.Div;
import common.SQLHelper;
import common.utils.TextUtils;
import dao.FormDao;
import viewtype.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hy on 2017/10/22.
 */
public class FreeForm extends CustomForm {
    private FormDao formDao;
    private model.Form formList;

    public void initDao(SQLHelper pSqlHelper) {
        formDao = new FormDao(pSqlHelper);
    }
    @Override
    protected void loadData(String sql) {
        formList = formDao.getForm(companyId,formName);
        System.out.println(formName);
        if (formName.equals("BondNewDetail")){
            String str=getPage().getUrlParameter("id")+getPage().getUrl();
            String id = str.substring(str.lastIndexOf("D")+1);
            sql=sql+" and id="+id;
        }
        System.out.println(sql);
        super.loadData(sql);
    }

    @Override
    protected void make(Div div) {
      //  Form form = div.form();
       // List<View> views = getViews();
        //mViews
        List<View> views = getViews();
        String html = null;
        if (formList!=null){
            html = formList.getHtml_template();
        }
        System.out.println("html-------"+html);

        List<String> list = new ArrayList<>();

        for (View view : views) {
//            view.setIsTitle("1");
            html = makeViews(list, view, null, html);
        }
        if (!TextUtils.isEmpty(html)) {
            div.text(html);
        }
        for (String v : list) {
            div.text(v);
        }

        if(mDatas!=null){
           // System.out.println("freeForm的mDatas============="+mDatas.toString());
        }

        if("Ant_InvitePage".equals(formName)){
            div.style("text-align","center");
        }

    }
}
