package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.embed.Img;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import common.CommonUtils;

/**
 * Created by hy on 2017/10/25.
 */
public class ImageNoTitle extends ParentView {
    String imgUrl = CommonUtils.getFileData;
    @Override
    public String getType() {
        return "ImageNoTitle";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        Img img = new Img();
        img.id(getName());
        img.src(getDataProvider().getImageUrl(this, getForm()));

        String styles = getDataProvider().getStyles(this, getForm());
        String css = getDataProvider().getCssClass(this, getForm());
        String text = getDataProvider().getText(this, getForm());
        String color = getDataProvider().getTextColor(this, getForm());
        String onclick = getDataProvider().getOnClick(getForm(),this, getView().getTarget(), getView().getTransferParams());

        if(onclick != null){
            String Id = getData("Id");
            String id = getData("id");
            if(Id!=null){
                onclick = onclick.replace("{Id}", Id);
            }
            if(id!=null){
                onclick = onclick.replace("{Id}", id);
            }
            String Oid = getData("Oid");
            if(Oid!=null){
                onclick = onclick.replace("{Oid}", Oid);
            }
            String Amount_paid = getData("Amount_paid");
            if(Oid!=null){
                onclick = onclick.replace("{Amount_paid}", Amount_paid);
            }
            if(getName().equals("noData3440")){
                Div bg = new Div();
                bg.onClick(onclick);
                return bg;
            }else {
                img.onClick(onclick);
            }
            img.onClick(onclick);
        }
        if("headImg".equals(getName())){

            img.onClick("gotoPage('goto:myInfo,freeForm','myInfo:Id=638');");
        }
        if (text != null) {
            img.text(text);
        }
        if (color != null) {
            img.style("color", color);
        }
        if (styles != null) {
            img.styles(styles);
        }
        if (css != null) {
            img.addCssClass(css);
        }
        return img;
    }
}
