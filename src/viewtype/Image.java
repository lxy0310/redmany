package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.embed.Img;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.text.Label;
import com.sangupta.htmlgen.tags.body.text.Span;

/**
 * Created by hy on 2017/10/25.
 */
public class Image extends ParentView {
    @Override
    public String getType() {
        return "Image";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        Span span = new Span();
        boolean isShow = isShow(getForm().getPage().getShowType());
        String text = getDataProvider().getText(this, getForm());//input值
        if (getView() != null) {
            View view=getView();
            if (isShow) {
                Label label = span.label();
//            label.text(view.getTitle());
                Img img = new Img();
                img.id(getName());
                img.src(getDataProvider().getImageUrl(this, getForm()));
                String onclick = getDataProvider().getOnClick(getForm(), this, getView().getTarget(), getView().getTransferParams());
                if (onclick != null) {
                    img.onClick(onclick);
                }
                return img;
            } else {
//            Span span = new Span();
                Img img = new Img();
                img.id(getName());
                img.src(getDataProvider().getImageUrl(this, getForm()));
                String onclick = getDataProvider().getOnClick(getForm(), this, getView().getTarget(), getView().getTransferParams());
                if (onclick != null) {
                    img.onClick(onclick);
                }
                Input input = new Input();
                input.attr("id", "hohoaf");
                return img;
            }
        }
        return span;
    }
}
