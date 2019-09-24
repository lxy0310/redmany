package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.sections.Option;
import com.sangupta.htmlgen.tags.body.text.Label;
import com.sangupta.htmlgen.tags.body.text.Span;
import dao.CommonHelperDao;
import model.Replacer;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinkageSelect extends ParentView {
    @Override
    public String getType() {
        return "LinkageSelect";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        boolean isShow = isShow(getForm().getPage().getShowType());
        Map  LinkageSelects = new HashMap();
        HttpSession session = getPage().getHttpSession();
        List<View> views = getForm().getViews();
        for(int i=0;i<views.size();i++){
            if("LinkageSelect".equals(views.get(i).getType())){
                LinkageSelects.put(views.get(i).getData_replacer(), views.get(i).getName());
            }
        }
        Div div = new Div();
//        div.attr("class","layui-input-inline");
        div.id(getName());
        String styles = getDataProvider().getStyles(this, getForm());
        String css = getDataProvider().getCssClass(this, getForm());
        String text = getDataProvider().getText(this, getForm());
        String color = getDataProvider().getTextColor(this, getForm());
        String onclick = getDataProvider().getOnClick(getForm(),this, getView().getTarget(), getView().getTransferParams());
        if (getView()!=null){
            View view=getView();
            Replacer myReplacer = new Replacer();
            String selectStyle = "";
            if(view.getData_replacer()!=null){
                String replacerStr = view.getData_replacer();
                CommonHelperDao dao = new CommonHelperDao();
                String sql = "Select * from Replacer where Replacername='"+replacerStr+"'";
                Replacer replacer = dao.getReplacerBySql(sql);
                if(replacer!=null){
                    myReplacer.setID(replacer.getID());
                    myReplacer.setTxtsource(replacer.getTxtsource());
                    myReplacer.setDatasql(replacer.getDatasql());
                    myReplacer.setChild_Replacer(replacer.getChild_Replacer());
                    myReplacer.setChild_Control(replacer.getChild_Control());
                    myReplacer.setDatasql_Two_Level(replacer.getDatasql_Two_Level());
                }
            }
            if(view.getIsTitle()!=null && "1".equals(view.getIsTitle())) {//不长title
                if(text!=null){
                    if (myReplacer.getTxtsource() != null && myReplacer.getTxtsource().length() > 0) {
                        String[] arr = myReplacer.getTxtsource().split("\\#");
                        for (int i = 0; i < arr.length; i++) {
                            String a = arr[i].substring(0, arr[i].indexOf(':'));
                            String b = arr[i].substring(arr[i].indexOf(':') + 1);
                            if(a.equals(text)){
                                div.text(b);
                                return div;
                            }
                        }
                    }else if (myReplacer.getDatasql() != null && myReplacer.getDatasql().length() > 0) {
                        CommonHelperDao dao = new CommonHelperDao();
                        List<Map<String, Object>> list = dao.getDataBySql(myReplacer.getDatasql());
                        if(list!=null && list.size()>0){
                            for(int i=0;i<list.size();i++){
                                Map map = list.get(i);
                                Object valueObj = map.get("value");
                                String value = valueObj.toString();
                                String name = (String) map.get("name");
                                if(text.equals(value)){
                                    div.text(name);
                                    return div;
                                }
                            }
                        }
                    }
                }else{
                    div.text("");
                }
                return div;
            }else{
                Label label = div.label();
//                label.attr("class","layui-form-label");
                label.text(view.getTitle());
            }

            com.sangupta.htmlgen.tags.body.sections.Select  select= div.select();
//            select.attr("lay-ignore","lay-ignore");
            select.id(getName()+"0");
            select.addCssClass(getName());
            if (view.getAttributeStr()!=null){
                String str=view.getWapAttribute();//获取下拉框样式
                String[] strs = str.split("\\[\\^\\]");
                if (strs!=null){
                    for(int i=0;i<strs.length;i++){
                        if (strs[i].contains("style")){
                            int index = strs[i].indexOf(":");
                            if (index > 0) {
                                selectStyle = strs[i].substring(index + 1);
                            }
                        }
                    }
                }
            }
            select.option("==请选择==","");
            if(isShow){
                    if (myReplacer.getTxtsource() != null && myReplacer.getTxtsource().length() > 0) {
                        String[] arr = myReplacer.getTxtsource().split("\\#");
                        for (int i = 0; i < arr.length; i++) {
                            String a = arr[i].substring(0, arr[i].indexOf(':'));
                            String b = arr[i].substring(arr[i].indexOf(':') + 1);
                             Option o= select.option(b, a);
                            if (text!=null && a.equals(text)){//默认选中
                                o.attr("selected","selected");

                            }
                        }
                    } else {
                        if (myReplacer.getDatasql() != null && myReplacer.getDatasql().length() > 0) {
                            CommonHelperDao dao = new CommonHelperDao();
                            List<Map<String, Object>> list = dao.getDataBySql(myReplacer.getDatasql());
                            if (list != null && list.size() > 0) {
                                for (int i = 0; i < list.size(); i++) {
                                    Map map = list.get(i);
                                    Object valueObj = map.get("value");
                                    String value = valueObj.toString();
                                    String name = (String) map.get("name");
                                    Option o=  select.option(name, value);
                                    if (text!=null && value.equals(text)){//默认选中
                                        o.attr("selected","selected");
                                    }
                                }
                            }
                        }
                    }
            }else {
                if (myReplacer.getDatasql_Two_Level() == null || myReplacer.getDatasql_Two_Level().length() == 0) {//没有父级
                    if (myReplacer.getTxtsource() != null && myReplacer.getTxtsource().length() > 0) {
                        String[] arr = myReplacer.getTxtsource().split("\\#");
                        for (int i = 0; i < arr.length; i++) {
                            String a = arr[i].substring(0, arr[i].indexOf(':'));
                            String b = arr[i].substring(arr[i].indexOf(':') + 1);
                            Option o=  select.option(b, a);
                            if (text!=null && a.equals(text)){//默认选中
                                o.attr("selected","selected");
                            }
                        }
                    } else {
                        if (myReplacer.getDatasql() != null && myReplacer.getDatasql().length() > 0) {
                            CommonHelperDao dao = new CommonHelperDao();
                            List<Map<String, Object>> list = dao.getDataBySql(myReplacer.getDatasql());
                            if (list != null && list.size() > 0) {
                                for (int i = 0; i < list.size(); i++) {
                                    Map map = list.get(i);
                                    Object valueObj = map.get("value");
                                    String value = valueObj.toString();
                                    String name = (String) map.get("name");
                                    Option o=  select.option(name, value);
                                    if (text!=null && value.equals(text)){//默认选中
                                        o.attr("selected","selected");
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //只配置一个fid时的处理
            if((myReplacer.getTxtsource()!=null && myReplacer.getTxtsource().length()>0) || (myReplacer.getDatasql()!=null && myReplacer.getDatasql().length()>0)){
                if(LinkageSelects!=null && LinkageSelects.size()>1){//除了自己还有别人
                    String childIds = "";
                    CommonHelperDao dao = new CommonHelperDao();
                    String sql = "Select * from Replacer where fid ='"+myReplacer.getID()+"'";
                    List<Map<String, Object>> childList = dao.getDataBySql(sql);
                    if(childList!=null && childList.size()>0){
                        for(int i=0;i<childList.size();i++){
                            String childRname = childList.get(i).get("Replacername").toString();//子级替换器名
                            String childRsql = childList.get(i).get("Datasql_Two_Level").toString();//子级替换器的关联sql
                            if (LinkageSelects.get(childRname)!= null){
                                String childId = LinkageSelects.get(childRname).toString();//子级控件id
                                childIds = childIds + childId +",";
                                session.setAttribute(childId+"_sql",childRsql);
                                Input input = div.input();//定义隐藏域保存子级信息
                                input.type("hidden");
                                input.id(getName()+"0_hidden");
                                input.attr("value",childId);
                            }
                        }
                    }
                    if(!"".equals(childIds) && childIds.length()>0){
                        childIds = childIds.substring(0,childIds.length()-1);
                        select.attr("onchange","linkageSelectChange('"+getName()+"0','"+childIds+"0')");
                    }
                }
            }
            if (view.getIsNull()!=null){
                String isNull=view.getIsNull();//是否为空(1不为空 0 可以为空)
                if("1".equals(isNull)){
                    select.attr("required","required");
                }
            }
            if(onclick != null){
                select.onClick(onclick);
            }
            if (color != null) {
                select.style("color", color);
            }
            if (styles != null) {
                select.styles(styles);
            }else if(selectStyle!=null && selectStyle.length()>0){
                select.styles(selectStyle);
                div.styles(selectStyle);
            }else{
                select.styles(getStyle("select"));
                div.styles(getStyle("span"));
            }
            if (css != null) {
                select.addCssClass(css);
            }
        }
        return div;
    }

}
