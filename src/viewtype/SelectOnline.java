package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.sections.Option;
import com.sangupta.htmlgen.tags.body.text.Label;
import com.sangupta.htmlgen.tags.body.text.Span;
import com.sangupta.htmlgen.tags.head.Script;
import common.ApiParser;
import common.utils.HttpUtils;
import common.utils.SafeString;
import dao.CommonHelperDao;
import model.Replacer;
import model.ShopCarPageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectOnline extends ParentView {
    @Override
    public String getType() {
        return "SelectOnline";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        String optype = getPage().getParameter("optype");//修改1查看2
        Div div = new Div();
        div.id(getName());
        String styles = getDataProvider().getStyles(this, getForm());
        String css = getDataProvider().getCssClass(this, getForm());
        String text = getDataProvider().getText(this, getForm());//input值
        String color = getDataProvider().getTextColor(this, getForm());
        String onclick = getDataProvider().getOnClick(getForm(),this, getView().getTarget(), getView().getTransferParams());
        if (getView()!=null){
            View view=getView();
            String replacerStr ="";
            String Txtsource = "";
            String Datasql = "";
            if (view.getData_replacer() != null) {
                replacerStr = view.getData_replacer();
                CommonHelperDao dao = new CommonHelperDao();
                String sql = "Select * from Replacer where Replacername='"+replacerStr+"'";
                Replacer replacer = dao.getReplacerBySql(getPage().getCompany_Id(),sql);
                if (replacer != null) {
                    Txtsource = replacer.getTxtsource();
                    Datasql = replacer.getDatasql();
                }
            }
            //列表的显示、其他情况时的title
            if(view.getIsTitle()!=null && "1".equals(view.getIsTitle())) {//为列表时，不长title
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
            }else{//为新增、修改或查看时，长title
                Label label = div.label();
                label.text(view.getTitle()==null?"":view.getTitle());
            }
            div.styles("left: 0; position: relative;");
//            div.style("margin-left","350px");
            Input hidden = div.input();//隐藏域，保存选择的下拉value值
            hidden.id(getName()+"0");
            hidden.name(getName());
            hidden.type("hidden");
            Input name = div.input();//选择的下拉显示值
            name.id(getName()+"1");
            name.type("text");

            if (view.getIsNull() != null) {
                String isNull = view.getIsNull();//是否为空(1不为空 0 可以为空)
                if ("1".equals(isNull)) {
                    name.attr("required", "required");
                    Span span = div.span();//当为必填时，显示红色星号
                    span.text(" * ");
                    span.styles("color:red;");
                }
            }

            Div domain = div.div();//下拉域
            domain.id(getName()+"_domain");
            domain.styles("display: none; width: 183px; min-height:50px; max-height:200px; overflow-y:auto; left: 124px;" +
                    "border: 1px #74c0f9 solid; background: #FFF; position: absolute; z-index: 80; top: 34px; color: #323232;");

//            String names = "";
//            String values = "";
            List<String> names = new ArrayList<>();
            List<String> values = new ArrayList<>();
            if (Txtsource != null && Txtsource.length() > 0) {
                String[] arr = Txtsource.split("\\#");
                for (int i = 0; i < arr.length; i++) {
                    String valueStr = arr[i].substring(0, arr[i].indexOf(':'));
                    String nameStr = arr[i].substring(arr[i].indexOf(':') + 1);
                    names.add(nameStr);
                    values.add(valueStr);
                    if(text!=null && valueStr.equals(text)){
                        if(optype!=null && ("1".equals(optype) || "2".equals(optype))){
                            hidden.value(valueStr);
                            name.value(nameStr);
                        }
                    }
                }
            }else if (Datasql != null && Datasql.length() > 0) {
                CommonHelperDao dao = new CommonHelperDao();
                List<Map<String, Object>> list = dao.getDataBySql(getPage().getCompany_Id(),Datasql);
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        Map map = list.get(i);
                        Object valueObj = map.get("value");
                        String valueStr = valueObj.toString();
                        String nameStr = (String) map.get("name");
                        names.add(nameStr);
                        values.add(valueStr);
                        if(text!=null && valueStr.equals(text)){
                            if(optype!=null && ("1".equals(optype) || "2".equals(optype))){
                                hidden.value(valueStr);
                                name.value(nameStr);
                            }
                        }
                    }
                }
            }
            if(optype!=null && "2".equals(optype)){//查看
                name.attr("disabled","disabled");
            }

            //当点击搜索框时，自动完成匹配
            div.add(new Script().text("$(function(){" +
                    "var old_value = $(\"#"+getName()+"1\").val();"+
//                    "$(\"#"+getName()+"1\").focus(function () {"+
//                        "if ($(\"#"+getName()+"1\").val() == \"\") {"+
//                            "AutoComplete(\""+getName()+"_domain\", \""+getName()+"1\",\""+getName()+"0\", old_value, \""+names+"\", \""+values+"\");"+
//                        "}"+
//                    "});"+
                    "$(\"#"+getName()+"1\").keyup(function () {"+
                        "AutoComplete(\""+getName()+"\", old_value, \""+names+"\" , \""+values+"\", \""+replacerStr+"\");"+
                    "});"+
                    "});"));
//                    "onmouseup="+
//                        "AutoComplete(\""+getName()+"_domain\", \""+getName()+"1\",\""+getName()+"0\", old_value, \""+names+"\" , \""+values+"\");"+
//                    "});"));

        }
        if(onclick != null){
            div.onClick(onclick);
        }
        if (color != null) {
            div.style("color", color);
        }
        if (styles != null) {
            div.styles(styles);
        }
        if (css != null) {
            div.addCssClass(css);
        }
        return div;
    }
}
