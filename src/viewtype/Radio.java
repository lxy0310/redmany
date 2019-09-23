package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.text.Label;
import com.sangupta.htmlgen.tags.body.text.Span;
import dao.CommonHelperDao;
import model.Replacer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Radio extends ParentView {
    @Override
    public String getType() {
        return "Radio";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        Span span = new Span();
        span.id(getName());
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
                Replacer replacer = dao.getReplacerByName(replacerStr);
                if(replacer!=null){
                    Txtsource =  replacer.getTxtsource();
                    Datasql =  replacer.getDatasql();
                }
            }
            if (view.getTitle()!=null){
                Label label = span.label();
                label.addCssClass(getName());
                label.text(view.getTitle());
            }
            if (Txtsource!=null && Txtsource.length()>0){
                String[] arr=Txtsource.split("\\#");
                List<String> list=new ArrayList<String>();
                for (int i=0;i<arr.length;i++){
                    String a=arr[i].substring(0,arr[i].indexOf(':'));
                    String b=arr[i].substring(arr[i].indexOf(':')+1);
                    Label label = span.label();
                    Input input =label.input();
                    input.id(getName()+i);
                    input.name(getName());
                    input.type("Radio");
                    input.value(a);
                    label.text(b);
                }
            }else{
                if (Datasql!=null && Datasql.length()>0){
                    CommonHelperDao dao = new CommonHelperDao();
                    List<Map<String, Object>> list = dao.getDataBySql(Datasql);
                    if(list!=null && list.size()>0){
                        for(int i=0;i<list.size();i++){
                            Map map = list.get(i);
                            Integer value = (Integer) map.get("value");
                            String name = (String) map.get("name");
                            Label label = span.label();
                            Input input =label.input();
                            input.id(getName()+i);
                            input.name(getName());
                            input.type("Radio");
                            input.value(value.toString());
                            label.text(name);
                        }
                    }
                }
            }
            if(onclick != null){
                span.onClick(onclick);
            }
                if (color != null) {
                span.style("color", color);
            }
            if (styles != null) {
                span.styles(styles);
            }
            if (css != null) {
                span.addCssClass(css);
            }
        }
        return span;
    }

}