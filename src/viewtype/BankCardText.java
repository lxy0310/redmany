package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.text.Span;

/**
 * Created by Suhaibo on 2018/3/5.
 */
public class BankCardText extends ParentView {
    @Override
    public String getType() {
        return "BankCardText";
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

        if(text!=null){
            span.text(text);
        }

        if(isEdit!=null && isEdit.equals("1")){
            if(txtName!=null){
                Span name =span.span();
                name.addCssClass("bank-"+getName());
                name.text(txtName);
            }
            Input input =span.input();
            input.addCssClass(getName()+"-bank");
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
        if("number".equals(getName())){

            Input input = span.input();
            input.type("text").placeholder("请填写银行卡号");

        }

        return span;
    }
}
