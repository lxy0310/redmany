package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.text.Span;

public class IntText extends ParentView {
    @Override
    public String getType() {
        return "IntText";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        Span span = new Span();
        span.id(getName());
        String styles = getDataProvider().getStyles(this, getForm());
        String css = getDataProvider().getCssClass(this, getForm());
        String text = getDataProvider().getText(this, getForm());
        String isEdit =getDataProvider().getTextEdit(this,getForm());
        String txtName =getDataProvider().getTextName(this,getForm());
        String placeholder =getDataProvider().getHintContent(this,getForm());
        String color = getDataProvider().getTextColor(this, getForm());
        String onclick = getDataProvider().getOnClick(getForm(),this, getView().getTarget(), getView().getTransferParams());
        if(onclick != null){
            span.onClick(onclick);
        }
        if(txtName!=null){
            Span name =span.span();
            name.addCssClass("edit-"+getName());
            name.text(txtName);
        }
        if(text!=null){
            Span txt =span.span();
            txt.addCssClass(getName()+"-val");
            txt.text(text);
        }
        if(isEdit!=null && isEdit.equals("1")){
            Input input =span.input();
            input.addCssClass(getName()+"-ipt");
            if(placeholder!=null){
                input.placeholder(placeholder);
            }
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
