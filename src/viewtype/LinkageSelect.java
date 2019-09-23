package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.text.Label;
import com.sangupta.htmlgen.tags.body.text.Span;
import dao.CommonHelperDao;
import model.Replacer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LinkageSelect extends ParentView {
    @Override
    public String getType() {
        return "LinkageSelect";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        Span span = new Span();
        span.id(getName());
        String styles = getDataProvider().getStyles(this, getForm());
        String css = getDataProvider().getCssClass(this, getForm());
        String text = getDataProvider().getText(this, getForm());
        String color = getDataProvider().getTextColor(this, getForm());
        String onclick = getDataProvider().getOnClick(getForm(),this, getView().getTarget(), getView().getTransferParams());
        if (getView()!=null){
            View view=getView();
            String selectStyle = "";
            String Txtsource ="";
            String Datasql ="";
            if (view.getTitle()!=null){
                Label label = span.label();
                label.addCssClass(getName());
                label.text(view.getTitle());
            }
            com.sangupta.htmlgen.tags.body.sections.Select  select= span.select();
            if (view.getWapAttribute()!=null){
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
            if(view.getData_replacer()!=null){
                String replacerStr = view.getData_replacer();
                CommonHelperDao dao = new CommonHelperDao();
                Replacer replacer = dao.getReplacerByName(replacerStr);
                if(replacer!=null){
                    Txtsource =  replacer.getTxtsource();
                    Datasql =  replacer.getDatasql();
                }
            }
            select.option("==请选择==","");
            if (Txtsource!=null && Txtsource.length()>0){
                String[] arr=Txtsource.split("\\#");
                List<String> list=new ArrayList<String>();
                for (int i=0;i<arr.length;i++){
                    String a=arr[i].substring(0,arr[i].indexOf(':'));
                    String b=arr[i].substring(arr[i].indexOf(':')+1);
                    select.option(b,a);
                }
            }else{
                if (Datasql!=null && Datasql.length()>0){
                    CommonHelperDao dao = new CommonHelperDao();
                    List<Map<String, Object>> list = dao.getDataBySql(Datasql);
                    if(list!=null && list.size()>0){
                        for(int i=0;i<list.size();i++){
                            Map map = list.get(i);
                            Object valueObj = map.get("value");
                            String value = valueObj.toString();
                            String name = (String) map.get("name");
                            select.option(name,value.toString());
                        }
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
            }
            if (css != null) {
                select.addCssClass(css);
            }
        }
//        if(text !=null){
//            Div div = new Div();
//            /* div.text("卡类型");*/
//            //com.sangupta.htmlgen.tags.body.sections.Select select1 = new com.sangupta.htmlgen.tags.body.sections.Select();
//            //select1 = div.select();
//            /*      select1.option("请选择","0");*/
//            String[] arr=text.split("\\#");
//            List<String> list=new ArrayList<String>();
//            for (int i=0;i<arr.length;i++){
//                String a=arr[i].substring(0,arr[i].indexOf(':'));
//                String b=arr[i].substring(arr[i].indexOf(':')+1);
//                select.option(b,a);
//            }
//            return  div;
//        }
//        if("cardType".equals(getName())){
//            Div div = new Div();
//            div.text("卡类型");
//            com.sangupta.htmlgen.tags.body.sections.Select select1 = new com.sangupta.htmlgen.tags.body.sections.Select();
//            select1 = div.select();
//            select1.option("信用卡","信用卡");
//            select1.option("储蓄卡","储蓄卡");
//            return  div;
//        }
        return span;
    }

}
