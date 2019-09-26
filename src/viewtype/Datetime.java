package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.text.Span;

public class Datetime extends ParentView {
    @Override
    public String getType() {
        return "Datetime";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        Span span = new Span();
        span.id(getName());
        String styles = getDataProvider().getStyles(this, getForm());
        String css = getDataProvider().getCssClass(this, getForm());
        String text = getDataProvider().getText(this, getForm());
        String txtName =getDataProvider().getTextName(this,getForm());
        String isEdit =getDataProvider().getTextEdit(this,getForm());
        String color = getDataProvider().getTextColor(this, getForm());
        if (getView() != null) {
            View view = getView();
            if (view.getIsTitle() != null && "1".equals(view.getIsTitle())) {//不长title
                span.text(text == null ? "" : text);
                return span;
            } else {
                span.text(view.getTitle() == null ? "" : view.getTitle());
            }
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
