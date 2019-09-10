package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;

public class SelectBar extends ParentView {
    @Override
    public String getType() {
        return "SelectBar";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        Div div = new Div();
        div.id(getName());
//        div.text("" + getForm().getDatas());
        Div top = div.div();
        top.id("search-top");
        top.img("images/search.png").id("ic_search");
        top.input("text", null).placeholder("搜索本地服务").idAndName("keyword").onChange("inputKeyword('keyword');");

        div.label("搜索").id("btn_search").onClick("searchKeyword('keyword');");

        return div;
    }
}
