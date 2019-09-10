package showtype;


import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.text.Span;
import common.utils.TextUtils;
import viewtype.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hy on 2017/10/22.
 */
public class CollectionForm extends CustomForm {

    @Override
    protected void loadData(String sql) {
        String nSql="";
        if(formName.equals("likesThree")) {
            if(sql.contains("select")){
                nSql=sql.split("select")[1];
            }
            sql="select top 3 "+nSql;
        }
        if(formName.equals("mineFunction")) {
            sql.replace("oi.state in (4,5)","oi.state =4");
        }

        super.loadData(sql);
    }
    @Override
    protected void make(Div div) {
        if (mDatas != null) {
            List<View> views = getViews();
            for (Map<String, Object> item : mDatas) {
                String html = getHtmlTemplate();
                List<String> list = new ArrayList<>();
                Span span = div.span();
                span.addCssClass(getFormName() + "-item");
                String onclick = getPage().getDataProvider().getOnClick(this, null, item);
                if("jkdMenu".equals(formName)){
                    span.onClick("gotoPage('goto:ServiceScreenPage,Cus_ServiceForm','');");
                }
                if (onclick != null) {
                    span.onClick(onclick);
                }
                for (View view : views) {
                    html = makeViews(list, view, item, html);
                }
                if (!TextUtils.isEmpty(html)) {
                    span.text(html);
                }
                for (String v : list) {
                    span.text(v);
                }
            }
        }
    }
}
