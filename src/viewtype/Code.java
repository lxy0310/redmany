package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.head.Script;

public class Code extends ParentView {
    @Override
    public String getType() {
        return "Code";
    }

    @Override
    protected HtmlBodyElement<?> create() {

        String txt = getDataProvider().getText(this, getForm());
        String textDesc = getDataProvider().getTextDesc(this, getForm());

        Div div = new Div();
        div.addCssClass("qrcode");
        div.add(new Script("js/jquery-1.10.2.min.js"));
        div.add(new Script("js/jquery.qrcode.min.js"));
        div.add(new Script().text("$(function(){" +
                "$('.qrcode').qrcode({" +
                ""+ "foreground: \"#00BBFF\","+ "background: \"#FFF\","+"render: \"canvas\","+
                "text: \""+txt+"\""+
                //生成二维码的路径 txt

                "});"+
                "});"));


        return  div;
    }
}
