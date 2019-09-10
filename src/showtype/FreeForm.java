package showtype;

import com.sangupta.htmlgen.tags.body.grouping.Div;
import common.utils.TextUtils;
import viewtype.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hy on 2017/10/22.
 */
public class FreeForm extends CustomForm {

    @Override
    protected void loadData(String sql) {
        if(formName.equals("simpleIDInfo_09")) {
            sql=sql+" where U.id="+getPage().getUserId();
        }
        if(formName.equals("Ant_mainMenu")) {
            sql=sql+" where u.Id="+getPage().getUserId();
        }
        if(formName.equals("Ant_Personal")) {
            sql=sql+" where u.Id="+getPage().getUserId();
        }
        System.out.println(formName);
        if (formName.equals("BondNewDetail")){

            String str=getPage().getUrlParameter("id")+getPage().getUrl();
            String id = str.substring(str.lastIndexOf("D")+1);
           // System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"+str+"id:"+id);
            sql=sql+" where id="+id;
        }
        System.out.println(sql);

        super.loadData(sql);
    }

    @Override
    protected void make(Div div) {
        List<View> views = getViews();
        String html = getHtmlTemplate();
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
            System.out.println("freeForm的mDatas============="+mDatas.toString());
        }

        if("Ant_InvitePage".equals(formName)){

        div.style("text-align","center");
        }
    }
}
