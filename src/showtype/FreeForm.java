package showtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.forms.Form;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.table.Table;
import common.SQLHelper;
import common.utils.TextUtils;
import dao.FormDao;
import dao.FormFiledDao;
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
   /*     if(formName.equals("simpleIDInfo_09")) {
            sql=sql+" and id="+getPage().getUserId();
        }
        if(formName.equals("Ant_mainMenu")) {
            sql=sql+" and Id="+getPage().getUserId();
        }
        if(formName.equals("Ant_Personal")) {
            sql=sql+" and Id="+getPage().getUserId();
        }*/
        formList = formDao.getForm(companyId,formName);
        System.out.println(formName);
        if (formName.equals("BondNewDetail")){

            String str=getPage().getUrlParameter("id")+getPage().getUrl();
            String id = str.substring(str.lastIndexOf("D")+1);
           // System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"+str+"id:"+id);
            sql=sql+" and id="+id;
        }
        System.out.println(sql);

        super.loadData(sql);
    }
   /* public HtmlBodyElement<?> createViews() {
        if (formList.getHtml_template()!=null && "".equals(formList.getHtml_template())){

        }else{
            Div div = new Div();
            div.id(formName);
            make(div);
            return  div;
        }

    }*/
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
