package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.embed.Img;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.text.Label;
import com.sangupta.htmlgen.tags.body.text.Span;
import common.PropsUtil;

/**
 * Created by hy on 2017/10/25.
 */
public class Image extends ParentView {
    @Override
    public String getType() {
        return "Image";
    }

    //    if (view.getTitle().contains(".")){ //图片
//        String titles=view.getTitle().substring(view.getTitle().indexOf(".")+1);
//        if (titles.equals("png") || titles.equals("jpg") || titles.equals("gif")){
//            Img img=div.img(IMAGE_PRE+titles);
//            img.addCssClass(getName());
//            img.src(IMAGE_PRE+view.getTitle());
//            img.width(30);
//            img.height(30);
//        }
//    }

    @Override
    protected HtmlBodyElement<?> create() {
        Div div = new Div();
        div.attr("onclick","uploadImg()");
//        div.id(getName());
        boolean isShow = isShow(getForm().getPage().getShowType());
        String text = getDataProvider().getText(this, getForm());//input值
        if (getView() != null) {
            View view=getView();

            if(view.getIsTitle()!=null && "1".equals(view.getIsTitle())) {//不长title
                div.text(text==null?"":text);
                return div;
            }else{
                div.text(view.getTitle()==null?"":view.getTitle());
            }

            PropsUtil.CheckImageSuffixes("");//检查是否是图片

            Input input = div.input();
//            input.id(getName()+"0");
            input.id("file");
            input.type("file");
            input.attr("name","file");
            input.attr("accept","image/*");
            input.attr("multiple","multiple");

            Div div1 = div.div();
            div1.id("huixian");
            if (isShow) {
                Img img = new Img();
                img.id(getName());
                img.src(getDataProvider().getImageUrl(this, getForm()));
                img.width(60);
                img.height(60);
                String onclick = getDataProvider().getOnClick(getForm(), this, getView().getTarget(), getView().getTransferParams());
                if (onclick != null) {
                    img.onClick(onclick);
                }
                return img;
            } else {

                return div;
            }
        }
        return div;
    }
}
