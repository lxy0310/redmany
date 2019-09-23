package page;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import common.utils.TextUtils;
import showtype.ParentForm;
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
        //判断生成的页面元素是否有{}的形式,进行相应的替换
        if( parentView !=null && parentView.getDatas()!=null && parentView.getDatas().size()>0){
            for (String filed:
                    parentView.getDatas().keySet()) {
                if(childView.indexOf("{"+filed+"}")>=0){
                    childView=childView.replace("{"+filed+"}", parentView.getDatas().get(filed).toString());
                }
            }

        }else if(parentView !=null && parentView.getForm() !=null && parentView.getForm().getDatas()!=null && parentView.getForm().getDatas().size()>0  && parentView.getForm().getDatas().get(0)!=null && parentView.getForm().getDatas().get(0).size()>0){
            for (String filed:
                    parentView.getForm().getDatas().get(0).keySet()) {
                if(childView.indexOf("{"+filed+"}")>=0){
                    childView=childView.replace("{"+filed+"}",  parentView.getForm().getDatas().get(0).get(filed).toString());
                }
            }

        }
        list.add(childView);
        return null;
    }

//    protected String makeViews(List<String> list, View view, Map<String, Object> datas, String html) {
//        if (view == null) return null;
//        ParentView parentView = makeType(view);
//        if (parentView != null) {
//            parentView.setDatas(datas);
//        }
//        String childView = toHtml(parentView);
//        String key = "##" + view.getName();
//        return  null;
//    }

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
