package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.text.Label;
import com.sangupta.htmlgen.tags.body.text.Span;

public class Datetime extends ParentView {
    @Override
    public String getType() {
        return "Datetime";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        Div span = new Div();
        span.addCssClass(getName()+"Div");
        String styles = getDataProvider().getStyles(this, getForm());
        String css = getDataProvider().getCssClass(this, getForm());
        String text = getDataProvider().getText(this, getForm());
        String txtName =getDataProvider().getTextName(this,getForm());
        String isEdit =getDataProvider().getTextEdit(this,getForm());
        String color = getDataProvider().getTextColor(this, getForm());



        Input input = span.input();
        if (getView() != null) {
            Label label = span.label();
           // Input input = span.input();
            input.addCssClass(getName()+"-val");
            input.id(getName()); //getName()
            input.type("text");
            input.onClick("useLayDateMultiple('"+getName()+"')");
            input.placeholder("请选择"+getView().getTitle());

            View view=getView();
            if(view.getIsTitle()!=null && "1".equals(view.getIsTitle())) {//不长title
                span.text(text==null?"":text);
                return span;
            }else{
                label.text(view.getTitle()==null?"":view.getTitle());
            }
        }

        if(txtName!=null){
            Span name =span.span();
            name.addCssClass("edit-"+getName());
            name.text(txtName);
        }

        if(text!=null){
            input.value(text);
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



        return span;
    }
}
