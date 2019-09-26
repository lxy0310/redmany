package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.embed.Img;
import com.sangupta.htmlgen.tags.body.forms.Button;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.BlockQuote;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.sections.A;
import com.sangupta.htmlgen.tags.body.text.Label;
import com.sangupta.htmlgen.tags.head.Link;
import com.sangupta.htmlgen.tags.head.Script;
import common.PropsUtil;

public class MultiImage extends ParentView {
    @Override
    public String getType() {
        return "MultiImage";
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
                    String[] imgs = null;
                    if(text.indexOf(",")>0){
                        text=text+",";
                    }
                    imgs = text.split(",");
                    if(imgs!=null && imgs.length>0){
                        for(int i=0;i<imgs.length;i++){
                            Img img = div.img(IMAGE_PRE+imgs[i]);
                            img.id(getName()+i);
                            img.width("30px");
                            img.height("30px");
                        }
                    }
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
            input.attr("multiple","multiple");
            input.onChange("uploadImg(this,'"+getName()+"',false)");
//            Div div2 = div.div();
//            div2.id(getName()+"_div");
//            div2.attr("style","display:inline-block; position:relative;");
            if(text!=null && PropsUtil.CheckImageSuffixes(text)) {//有值且为图片
                String[] imgs = null;
                if(text.indexOf(",")>0){
                    text=text+",";
                }
                imgs = text.split(",");
                if(imgs!=null && imgs.length>0){
                    for(int i=0;i<imgs.length;i++){
                        Div div2 = div.div();
                        div2.id(getName()+i+"_div");
                        div2.attr("style","display:inline-block; position:relative;");
                        Img img = div2.img(IMAGE_PRE+imgs[i]);
                        img.id(getName()+i);
                        img.width("50px");
                        img.height("50px");
                        if(!isShow){
                            A a = div2.a();
                            a.herf("javascript:void(0);");
                            Img delImg = a.img("/redmany/images/delete.jpg");
                            delImg.attr("style","position: absolute; height: 15px;width: 15px;top: 0px; right: 0px; ");
                            delImg.onClick("delFile('"+getName()+i+"','multiImage')");
                        }
                    }
                }
            }
            if (isShow) {
                input.attr("disabled","disabled");
                input.attr("color","transparent");
                return div;
            }else{
//                A a = div2.a();
//                a.herf("javascript:void(0);");
//                Img delImg = a.img("/redmany/images/delete.jpg");
//                delImg.attr("style","position: absolute; height: 15px;width: 15px;top: 0px; right: 0px; ");
//                delImg.onClick("delFile('"+getName()+"','multiImage')");
            }
        }
        return div;
    }
}
