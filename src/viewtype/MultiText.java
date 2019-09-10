package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.text.Span;
import com.sangupta.htmlgen.tags.body.text.TextArea;

/**
 * Created by Suhaibo on 2018/3/3.
 */
public class MultiText extends ParentView {
    @Override
    public String getType() {
        return "MultiText";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        Span span = new Span();
        span.id(getName());
        String styles = getDataProvider().getStyles(this, getForm());
        String css = getDataProvider().getCssClass(this, getForm());
        String text = getDataProvider().getText(this, getForm());
        String isEdit =getDataProvider().getTextEdit(this,getForm());
        String placeholder =getDataProvider().getHintContent(this,getForm());
        String color = getDataProvider().getTextColor(this, getForm());
        String onclick = getDataProvider().getOnClick(getForm(),this, getView().getTarget(), getView().getTransferParams());
        if(onclick != null){
            span.onClick(onclick);
        }

        if(text!=null){
            span.text(text);
        }

        if(isEdit!=null && isEdit.equals("1")){
            TextArea txtArea =span.textArea();
            txtArea.rows(5);
            txtArea.addCssClass(getName()+"-txt");
            if(placeholder!=null){
                txtArea.placeholder(placeholder);
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
