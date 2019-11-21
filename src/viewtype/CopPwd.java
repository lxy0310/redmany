package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.embed.Img;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.text.Label;
import com.sangupta.htmlgen.tags.body.text.Span;

/**
 * 单行密码
 */
public class CopPwd extends ParentView {
    @Override
    public String getType() {
        return "CopPwd";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        Span span = new Span();
        span.id(getName());
        String text = getDataProvider().getText(this, getForm());
        String placeholder = getDataProvider().getHintContent(this, getForm());
        //生成Div
        Div passwordCotent = new Div();
        passwordCotent.id(getName());
        Label label = passwordCotent.label();
        label.attr("for", getName() + "0");

        if (getView() != null) {
            View view = getView();
            if (view.getTitle() != null) { //title
                if (view.getTitle().contains(".")) { //图片
                    String titles = view.getTitle().substring(view.getTitle().indexOf(".") + 1);
                    if (titles.equals("png") || titles.equals("jpg") || titles.equals("gif")) {
                        Img img = label.img(IMAGE_PRE + titles);
                        img.addCssClass(getName());
                        img.src(IMAGE_PRE + view.getTitle());
                        img.width(30);
                        img.height(30);
                    }
                } else { //文本
                    label.text(view.getTitle());
                    // Label label = span.label();
                    //  label.addCssClass(getName());
                    //  label.text(view.getTitle());

                }
            }
            // 生成input
            Input input = passwordCotent.input();
            input.id(getName() + "0");
            input.type("password");
            input.placeholder(placeholder != null && placeholder.length() > 0 ? placeholder : "请输入密码");
            input.attr("required","required");
            String onclick = getDataProvider().getOnClick(getForm(), this, getView().getTarget(), getView().getTransferParams());
            if (onclick != null) {
                input.onClick(onclick);
            }
            /*  *//*   if(text!=null){
            span.text(text);
        }*//*
        if (getView()!=null){
            View view=getView();

        }*/



        }
        return passwordCotent;
    }
}
