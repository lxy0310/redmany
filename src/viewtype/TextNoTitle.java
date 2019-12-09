package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.embed.Img;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import common.CommonUtils;
import org.apache.commons.lang.StringUtils;

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

        if (getView()!=null){
            View view=getView();
            if (view.getWapAttribute()!=null){
                String str=view.getWapAttribute();//获取样式
                String[] strs = str.split("\\[\\^\\]");
                if (strs!=null){
                    for(int i=0;i<strs.length;i++){
                        if (strs[i].contains("content")){ //文本默认内容
                            text = StringUtils.substringAfterLast(strs[i],":");
                            if(text.indexOf("fromGlobal_")>0){
                                text = strs[i].substring(strs[i].indexOf("{")+1,strs[i].indexOf("}")).trim();
                                text = StringUtils.substringAfterLast(text,"fromGlobal_");
                                text = getPage().getHttpSession().getAttribute(text).toString();
                            }
                        }
                    }
                }
            }
        }

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

