package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.embed.Img;

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
        Img img = new Img();
        img.id(getName());
        img.src(getDataProvider().getImageUrl(this, getForm()));
        String onclick = getDataProvider().getOnClick(getForm(),this, getView().getTarget(), getView().getTransferParams());
        if(onclick != null){
            img.onClick(onclick);
        }
        return img;
    }
}
