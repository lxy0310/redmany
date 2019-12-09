package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.embed.Img;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.text.Label;
import com.sangupta.htmlgen.tags.body.text.Span;

/**
 * 新密码（生成一个新密码，一个确认密码）
 */
public class MultiPwd extends ParentView {
    @Override
    public String getType() {
        return "MultiPwd";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        Span span = new Span();
        span.id(getName());
        String text = getDataProvider().getText(this, getForm());
        String placeholder = getDataProvider().getHintContent(this, getForm());
        //生成Div
        Div div = new Div();
        div.id(getName());
        div.addCssClass("tableOverflow");
        Label label = div.label();
//        Label label2 = div.label();

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
//                    label2.text(view.getTitle());
                }
            }
            // 生成input
            Input input = div.input();
            input.id(getName() + "0");
            input.type("password");
            input.placeholder(placeholder != null && placeholder.length() > 0 ? placeholder : "请输入密码");
            input.attr("required","required");
            String onclick = getDataProvider().getOnClick(getForm(), this, getView().getTarget(), getView().getTransferParams());
            if (onclick != null) {
                input.onClick(onclick);
            }
            Input input2 = div.input();
            input2.id(getName() + "again0");
            input2.type("password");
            input2.placeholder(placeholder != null && placeholder.length() > 0 ? placeholder : "请确认密码");
            input2.attr("required","required");
            String onclick2 = getDataProvider().getOnClick(getForm(), this, getView().getTarget(), getView().getTransferParams());
            if (onclick2 != null) {
                input2.onClick(onclick2);
            }
        }
        return div;
    }
}
