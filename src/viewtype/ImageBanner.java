package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.embed.Img;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.head.Script;

/**
 * Created by hy on 2017/12/22.
 */
public class ImageBanner extends ParentView {
    @Override
    public String getType() {
        return "ImageBanner";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        Div div = new Div();
        div.id(getName());
        Div swiper = div.div();
        swiper.id(getName() + "-swiper");
        swiper.addCssClass("swiper-container swiper-container-horizontal");
        Div content = swiper.div();
        content.addCssClass("swiper-wrapper");
        String src =getDataProvider().getImageUrl(this, getForm());
        int index =src.lastIndexOf("/");
        String srcDefault="";
        String name="";
        if(index>0){
            srcDefault =src.substring(0,index);
            name=src.substring(index+1);
        }
        if(name.contains(",")){
            String[] srcList = name.split(",");
            for (String n:srcList){
                src = srcDefault+"/"+n;
                Div slide = content.div();
                slide.addCssClass("swiper-slide");
                Img img = slide.img(src);
                img.addCssClass("imgcls");
                String styles = getDataProvider().getStyles(this, getForm());
                String css = getDataProvider().getCssClass(this, getForm());
                String text = getDataProvider().getText(this, getForm());
                String color = getDataProvider().getTextColor(this, getForm());
                String onclick = getDataProvider().getOnClick(getForm(),this, getView().getTarget(), getView().getTransferParams());
                if(onclick != null){
                    img.onClick(onclick);
                }
                if(text!=null){
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
            }
        }
        if(name!=null&&!name.contains(",")){
            src = srcDefault+"/"+name;
            Div slide = content.div();
            slide.addCssClass("swiper-slide");
            Img img = slide.img(src);
            img.addCssClass("imgcls");
            String styles = getDataProvider().getStyles(this, getForm());
            String css = getDataProvider().getCssClass(this, getForm());
            String text = getDataProvider().getText(this, getForm());
            String color = getDataProvider().getTextColor(this, getForm());
            String onclick = getDataProvider().getOnClick(getForm(),this, getView().getTarget(), getView().getTransferParams());
            if(onclick != null){
                img.onClick(onclick);
            }
            if(text!=null){
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
        }
        div.add(new Script().text("\n" +
                "    var swiper = new Swiper('#" + getName() + "-swiper', {\n" +
                "        iOSEdgeSwipeDetection :true,\n" +
                "        pagination: '#" + getName() + "-swiper-page',\n" +
                "        paginationClickable: true\n" +
                "    });"));
        return div;
    }
}
