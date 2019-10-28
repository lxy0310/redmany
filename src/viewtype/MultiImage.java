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
       // text="034dc6fe45e442d8b07f7e6c683cc875.png,071dede250d44fe4a519e7e857ad9e8f.jpg";
        if (getView() != null) {
            View view=getView();
            String[] imgs = null;
            if(text!=null && text.length()>0) {//有值且为图片
                if(text.indexOf(",")<0){
                    text=text+",";
                }
                if (!"1".equals(view.getIsValue())){
                    imgs = text.split(",");
                }
            }
            if(view.getIsTitle()!=null && "1".equals(view.getIsTitle())) {//不长title
                if(imgs!=null && imgs.length>0){
                    for(int i=0;i<imgs.length;i++){
                        if(PropsUtil.CheckImageSuffixes(imgs[i])){
                            Img img = div.img(IMAGE_PRE+imgs[i]);
                            img.id(getName()+i);
                            img.width("30px");
                            img.height("30px");
                        }
                    }
                }
                return div;
            }else{
                Label label = div.label();
                label.text(view.getTitle()==null?"":view.getTitle());
              //  div.text(view.getTitle()==null?"":view.getTitle());
            }
            //旧数据隐藏域
            Input old_input = div.input();
            old_input.id(getName()+"_old");
            old_input.type("hidden");
            old_input.value(text);
            //删除数据隐藏域
            Input del_input = div.input();
            del_input.id(getName()+"_del");
            del_input.type("hidden");
            //上传按钮
            A a1 = div.a();
            a1.id(getName()+"_btn");
            a1.herf("javascript:void(0);");
            a1.addCssClass("file");
            a1.text("多图上传");
            a1.onClick("upload_a('"+getName()+"0');");
            //文件选择框
            Input input = a1.input();
            input.id(getName()+"0");
            input.type("file");
            input.name(getName());
            input.title("");
            input.attr("accept","image/*");
            input.attr("multiple","multiple");
            input.onChange("uploadMultiImg(this,'"+getName()+"')");
            if(imgs!=null && imgs.length>0){
                for(int i=0;i<imgs.length;i++){
                    Div div2 = div.div();
                    div2.id(getName()+i+"_div");
                    div2.attr("style","display:inline-block; position:relative;");
                    Img img = div2.img(IMAGE_PRE+imgs[i]);
                    img.id(getName()+i);
                    img.width("30px");
                    img.height("30px");
                    if(!isShow){
                        A a = div2.a();
                        a.herf("javascript:void(0);");
                        Img delImg = a.img("/redmany/images/delete.jpg");
                        delImg.attr("style","position: absolute; height: 15px;width: 15px;top: 0px; right: 0px; ");
                        delImg.onClick("delMultiImg('"+getName()+"','"+i+"')");
                    }
                }
            }
            if (isShow) {
                input.attr("disabled","disabled");
                input.attr("color","transparent");
                return div;
            }
        }
        return div;
    }
}
