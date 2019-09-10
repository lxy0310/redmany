package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.embed.Img;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import common.CommonUtils;

/**
 * Created by hy on 2017/10/25.
 */
public class TextNoTitle extends ParentView {
    String iTmg = "images/tImg.png";
    String url = CommonUtils.getFileData;

    @Override
    public String getType() {
        return "TextNoTitle";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        Div span = new Div();
        span.id(getName());
        String styles = getDataProvider().getStyles(this, getForm());
        String css = getDataProvider().getCssClass(this, getForm());
        String text = getDataProvider().getText(this, getForm());
        String color = getDataProvider().getTextColor(this, getForm());
        String onclick = getDataProvider().getOnClick(getForm(),this, getView().getTarget(), getView().getTransferParams());


        if(onclick != null){
            span.onClick(onclick);
        }
        if(getView().getName().equals("title")) {
            span.text(text);
        }else if(text!=null){
            span.text(text);
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
        if("avatarStatic".equals(getName())){

//            System.out.println("img:"+getForm().getValue("headImg"));
            Img img = span.img(url+"20171220175135.jpg");
        }
        if("greyLine".equals(getName())){

            Div div = span.div();
            div.style("background","gray").style("height","1px").style("margin-top","5px");
        }
        if("affirmative".equals(getName())){
            Div div = span.div();
            div.text("威廉杰斯");
        }
        if("phoneStatic".equals(getName())){
            Div div = span.div();
            div.text("yonghu2");
        }
//        System.out.println("textNoTitle.state"+ getForm().getValue("state"));
        if("orderState".equals(getName()) && getForm().getValue("state").equals("1")){
            span.text("本单未付款");
        } else if("orderState".equals(getName()) && getForm().getValue("state").equals("4")){
            span.text("待评论");
        }

        if("cardType".equals(getName()) && text.equals("2")){

            span.text("储蓄卡");
        } else if("cardType".equals(getName()) && text.equals("1")){
            span.text("信用卡");
        }
        span.id(getName());
        return span;
    }
}

