package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.text.Span;

/**
 * 单行密码
 */
public class CopPwd extends ParentView {
    @Override
    public String getType() {
        return "CopPwd";
    }
    @Override
    protected HtmlBodyElement<?> create() {
        Span span = new Span();
        span.id(getName());
        String text = getDataProvider().getText(this, getForm());
        String placeholder =getDataProvider().getHintContent(this,getForm());
        String onclick = getDataProvider().getOnClick(getForm(),this, getView().getTarget(), getView().getTransferParams());
        if(onclick != null){
            span.onClick(onclick);
        }
        if(text!=null){
            span.text(text);
        }
        if (getView()!=null){
            View view=getView();

        }




        return span;
    }

}
