package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;

public class selAll extends ParentView {

    public String getType() {
        return "selAll";
    }

    protected HtmlBodyElement<?> create() {
        Div div = new Div();



        return  div;
    }

}
