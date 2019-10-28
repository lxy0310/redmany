package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.text.Label;
import dao.CommonHelperDao;
import model.Replacer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Checkbox extends ParentView {
    @Override
    public String getType() {
        return "Checkbox";
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
            String Txtsource ="";
            String Datasql ="";
            if(view.getData_replacer()!=null){
                String replacerStr = view.getData_replacer();
                CommonHelperDao dao = new CommonHelperDao();
                String sql = "Select * from Replacer where Replacername='"+replacerStr+"'";
                Replacer replacer = dao.getReplacerBySql(getPage().getCompany_Id(),sql);
                if(replacer!=null){
                    Txtsource =  replacer.getTxtsource();
                    Datasql =  replacer.getDatasql();
                }
            }

            if(view.getIsTitle()!=null && "1".equals(view.getIsTitle())) {//不长title
                if(text!=null){
                    String[] texts=null;
                    String textStr = "";
                    if(text.indexOf(",")>0){
                        texts = text.split(",");
                    }
                    if (Txtsource!=null && Txtsource.length()>0){
                        String[] arr=Txtsource.split("\\#");
                        for (int i=0;i<arr.length;i++){
                            String a=arr[i].substring(0,arr[i].indexOf(':'));
                            String b=arr[i].substring(arr[i].indexOf(':')+1);
                            if(texts==null && a.equals(text)){
                                div.text(b);
                            }else if(texts!=null && Arrays.asList(texts).contains(a)){
                                textStr = textStr + b +",";
                            }
                        }
                        if(!"".equals(textStr)){
                            div.text(textStr.substring(0,textStr.length()-1));
                            return div;
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
            if (Txtsource!=null && Txtsource.length()>0){
                String[] arr=Txtsource.split("\\#");
                List<String> list=new ArrayList<String>();
                for (int i=0;i<arr.length;i++){
                    String a=arr[i].substring(0,arr[i].indexOf(':'));
                    String b=arr[i].substring(arr[i].indexOf(':')+1);
                    Input input =div.input();
//                    input.attr("lay-skin","primary");
                    input.attr("title",b);
                    input.id(getName()+i);
                    input.addCssClass(getName()+i);
                    input.name(getName());
                    input.type("checkbox");
                    input.value(a);
                    if (text!=null && (text.contains(a+",") || text.contains(","+a))){//默认选中
                        if(optype!=null && "1".equals(optype)){
                            input.attr("checked","checked");
                        }
                    }
                    if(optype!=null && "2".equals(optype)){
                        input.attr("checked","checked");
                        input.attr("disabled","true");//查看时不可选
                    }
                }
            }else{
                if (Datasql!=null && Datasql.length()>0){
                    CommonHelperDao dao = new CommonHelperDao();
                    List<Map<String, Object>> list = dao.getDataBySql(getPage().getCompany_Id(),Datasql);
                    if(list!=null && list.size()>0){
                        for(int i=0;i<list.size();i++){
                            Map map = list.get(i);
                            Object valueObj = map.get("value");
                            String value = valueObj.toString();
                            String name = (String) map.get("name");
                            Input input =div.input();
//                            input.attr("lay-skin","primary");
                            input.attr("title",name);
                            input.id(getName()+i);
                            input.addCssClass(getName()+i);
                            input.name(getName());
                            input.type("checkbox");
                            input.value(value);
                            if (text!=null && (text.contains(value+",") || text.contains(","+value))){//默认选中
                                if(optype!=null && "1".equals(optype)){
                                    input.attr("checked","checked");
                                }
                            }
                            if(optype!=null && "2".equals(optype)){
                                input.attr("checked","checked");
                                input.attr("disabled","true");//查看时不可选
                            }
                        }
                    }
                }
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
        }
        return div;
    }

}
