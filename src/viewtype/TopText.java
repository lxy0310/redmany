package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;

/**
 * Created by Su on 2017/12/21.
 */
public class TopText extends Text {
    @Override
    public String getType() {
        return "TopText";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        Div span = new Div();
        String styles = getDataProvider().getStyles(this, getForm());
        String css = getDataProvider().getCssClass(this, getForm());
        String text = getDataProvider().getText(this, getForm());
        String color = getDataProvider().getTextColor(this, getForm());
        String onclick = getDataProvider().getOnClick(getForm(),this, getView().getTarget(), getView().getTransferParams());
        if(onclick != null){
            span.onClick(onclick);
        }
        if (text != null) {
            span.text(text);
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
        span.id(getName());
        return span;
    }
}
