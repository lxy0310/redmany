package showtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import common.SQLHelper;
import common.utils.TextUtils;
import dao.FormDao;
import model.Form;
import viewtype.View;

import java.util.ArrayList;
import java.util.List;

public class SearchForm extends CustomForm  {

    private FormDao formDao;
    private Form froms ;//form表信息
    private String search_fields; //查询条件

    protected void initDao(SQLHelper pSQLHelper) {
        formDao = new FormDao(pSQLHelper);
    }

    public HtmlBodyElement<?> createViews() {
        Div div = new Div();
        div.styles("margin:20px auto;");
        search_fields = null;
        div.id("condition");
        com.sangupta.htmlgen.tags.body.forms.Form searchForm = div.form();
        searchForm.id("searchForm");
        if (froms!=null){
            search_fields = froms.getSearch_fields();
        }
        List<View> views = getViewLists(search_fields);
        String html = getHtmlTemplate();

        List<String> list = new ArrayList<>();
        for (View view : views) {
            view.setIsValue("1");
            html = makeViews(list, view, null, html);
        }
        if (!TextUtils.isEmpty(html)) {
            searchForm.text(html);
        }
        for (String v : list) {
            Div condition = searchForm.div().addCssClass("layui-form-item");
            Div div1 = condition.div().styles("height: 30px;line-height: 30px;" );
            div1.addCssClass("layui-input-block");
            v = v.replaceAll("<label>","<label class=\"labelRight\">");
            div1.text(v);
        }
        return div;
    }


}
