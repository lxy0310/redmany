package showtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import common.utils.TextUtils;
import viewtype.ParentView;
import viewtype.View;

import java.util.List;
import java.util.Map;

public class CustomForm extends ParentForm {

    @Override
    public HtmlBodyElement<?> createViews() {
        Div div = new Div();
        div.id(formName);
        make(div);
        return div;
    }

    protected void make(Div div) {

    }

    protected String getHtmlTemplate() {
        return mFormData != null ? mFormData.getHtml_template() : null;
    }


    protected String makeViews(List<String> list, View view, Map<String, Object> datas, String html) {
        if (view == null) return null;
        ParentView parentView = makeType(view);
        if (parentView != null) {
            parentView.setDatas(datas);
        }
        String childView = toHtml(parentView);
        String key = "##" + view.getName();
        if (!TextUtils.isEmpty(html) && html.contains(key)) {
            return html.replace(key, childView);
        }
        list.add(childView);
        return null;
    }

    protected String addMakeViews(List<String> list, View view, Map<String, Object> datas, String html) {
        if (view == null) return null;
        ParentView parentView = makeTypes(view);
        if (parentView != null) {
            parentView.setDatas(datas);
        }
        String childView = toHtml(parentView);
        String key = "##" + view.getName();
        if (!TextUtils.isEmpty(html) && html.contains(key)) {
            return html.replace(key, childView);
        }
        list.add(childView);
        return null;
    }


}
