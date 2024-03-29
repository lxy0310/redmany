package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.sections.Option;
import com.sangupta.htmlgen.tags.body.text.Label;
import dao.CommonHelperDao;
import model.Replacer;

import java.util.List;
import java.util.Map;

public class Select extends ParentView {
    @Override
    public String getType() {
        return "Select";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        Div div = new Div();
        div.id(getName());
        div.addCssClass("layui-form-item");
        String optype = getPage().getParameter("optype");//修改1查看2
        String styles = getDataProvider().getStyles(this, getForm());
        String css = getDataProvider().getCssClass(this, getForm());
        String text = getDataProvider().getText(this, getForm());
        String color = getDataProvider().getTextColor(this, getForm());
        String onclick = getDataProvider().getOnClick(getForm(), this, getView().getTarget(), getView().getTransferParams());
        if (getView() != null) {
            View view = getView();
            String selectStyle = "";
            String Txtsource = "";
            String Datasql = "";
            if (view.getWapAttribute()!=null){
                String str=view.getWapAttribute();//获取样式
                if(str.indexOf("isEdit:")>=0){
                    String  num=str.substring(str.indexOf("isEdit:")+7,str.indexOf("isEdit:")+8);
                    if ("0".equals(num.trim())){
                        view.setIsTitle("0");
                    }
                }
            }
            if (view.getData_replacer() != null) {
                String replacerStr = view.getData_replacer();
                CommonHelperDao dao = new CommonHelperDao();
                String sql = "Select * from Replacer where Replacername='"+replacerStr+"'";
                Replacer replacer = dao.getReplacerBySql(getPage().getCompany_Id(),sql);
                if (replacer != null) {
                    Txtsource = replacer.getTxtsource();
                    Datasql = replacer.getDatasql();
                }
            }
            if(view.getIsTitle()!=null && "1".equals(view.getIsTitle())) {//不长title
                if(text!=null){
                    if (Txtsource!=null && Txtsource.length()>0){
                        String[] arr=Txtsource.split("\\#");
                        for (int i=0;i<arr.length;i++){
                            String a=arr[i].substring(0,arr[i].indexOf(':'));
                            String b=arr[i].substring(arr[i].indexOf(':')+1);
                            if(a.equals(text)){
                                div.text(b);
                                return div;
                            }
                        }
                    }else if (Datasql!=null && Datasql.length()>0){
                        CommonHelperDao dao = new CommonHelperDao();
                        List<Map<String, Object>> list = dao.getDataBySql(getPage().getCompany_Id(),Datasql);
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
                label.text(view.getTitle()==null?"":view.getTitle());
            }
//            div.style("margin-left","350px");
            Div div1 = div.div();
            div1.addCssClass("layui-input-block");
            div1.styles("margin-top: -30px;margin-left:123px;margin-right:73px;width: 182px;");
            div1.styles("width: 182px;");
            div1.styles("margin-top: -30px;");
            div1.styles("margin-left: 123px;");
            div1.styles("margin-right: 73px;");

            com.sangupta.htmlgen.tags.body.sections.Select select = div1.select();

            select.id(getName()+"0");
            select.attr("lay-filter",getName()+"0");
            select.name(getName());
            if (view.getWapAttribute() != null) {
                String str = view.getWapAttribute();//获取下拉框样式
                String[] strs = str.split("\\[\\^\\]");
                if (strs != null) {
                    for (int i = 0; i < strs.length; i++) {
                        if (strs[i].contains("style")) {
                            int index = strs[i].indexOf(":");
                            if (index > 0) {
                                selectStyle = strs[i].substring(index + 1);
                            }
                        }
                    }
                }
            }
            select.option("==请选择==", "");
            if (Txtsource != null && Txtsource.length() > 0) {
                String[] arr = Txtsource.split("\\#");
                for (int i = 0; i < arr.length; i++) {
                    String a = arr[i].substring(0, arr[i].indexOf(':'));
                    String b = arr[i].substring(arr[i].indexOf(':') + 1);
                    Option option = select.option(b, a);
                    if(text!=null && a.equals(text)){
                        if(optype!=null && ("1".equals(optype) || "2".equals(optype))){
                            option.selected();
                        }
                    }
                }
            } else {
                if (Datasql != null && Datasql.length() > 0) {
                    CommonHelperDao dao = new CommonHelperDao();
                    List<Map<String, Object>> list = dao.getDataBySql(getPage().getCompany_Id(),Datasql);
                    if (list != null && list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            Map map = list.get(i);
                            Object valueObj = map.get("value");
                            String value = valueObj.toString();
                            String name = (String) map.get("name");
                            Option option = select.option(name, value);
                            if(text!=null && value.equals(text)){
                                if(optype!=null && ("1".equals(optype) || "2".equals(optype))){
                                    option.selected();
                                }
                            }
                        }
                    }
                }
            }
            if(optype!=null && "2".equals(optype)){
                select.attr("disabled","disabled");
            }
            if (view.getIsNull() != null) {
                String isNull = view.getIsNull();//是否为空(1不为空 0 可以为空)
                if ("1".equals(isNull)) {
                    select.attr("required", "required");
                }
            }
            if (onclick != null) {
                select.onClick(onclick);
            }
            if (color != null) {
                select.style("color", color);
            }
            if (styles != null) {
                select.styles(styles);
            } else if (selectStyle != null && selectStyle.length() > 0) {
                select.styles(selectStyle);
            }
            if (css != null) {
                select.addCssClass(css);
            }
        }
            return div;
        }

    }
