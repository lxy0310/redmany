package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.embed.Img;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.sections.A;
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

    @Override
    protected HtmlBodyElement<?> create() {
        Div div = new Div();
        div.id(getName());
        boolean isShow = isShow(getForm().getPage().getShowType());
        String text = getDataProvider().getText(this, getForm());//input值
        if (getView() != null) {
            View view=getView();
            if(view.getIsTitle()!=null && "1".equals(view.getIsTitle())) {//不长title
                if(text!=null && PropsUtil.CheckImageSuffixes(text)){//有值且为图片
                    Img img = new Img();
                    img.id(getName()+"0");
                    img.src(IMAGE_PRE+text);
                    img.width("30px");
                    img.height("30px");
                    return img;
                }
                return div;
            }else{
                div.text(view.getTitle()==null?"":view.getTitle());
            }

            Input input = div.input();
            input.id(getName()+"0");
            input.type("file");
            input.name(getName());
            input.attr("accept","image/*");
            input.onChange("uploadImg(this,'"+getName()+"',true)");
            Div div2 = div.div();
            div2.id(getName()+"_div");
            div2.attr("style","display:inline-block; position:relative;");
            if(text!=null && PropsUtil.CheckImageSuffixes(text)) {//有值且为图片
                Img img = div2.img(IMAGE_PRE+text);
                img.width("50px");
                img.height("50px");
                img.onClick("lookPicture('http://oa.redmany.com:50016/Picture.aspx?images=a9f106358f2a46b58d94d60f9332f60f.jpg&num=0')");
            }
            if (isShow) {
                input.attr("disabled","disabled");
                input.attr("color","transparent");
                return div;
            }else{
                A a = div2.a();
                a.herf("javascript:void(0);");
                Img delImg = a.img("/redmany/images/delete.jpg");
                delImg.attr("style","position: absolute; height: 15px;width: 15px;top: 0px; right: 0px; ");
                delImg.onClick("delFile('"+getName()+"','image')");
//                点击查看：lookPicture('../Picture.aspx?images=a9f106358f2a46b58d94d60f9332f60f.jpg&num=0')
            }
        }
        return div;
    }
}