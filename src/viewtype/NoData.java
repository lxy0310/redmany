package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;

/**
 * Created by hy on 2017/10/25.
 */
public class NoData extends ParentView {
    @Override
    public String getType() {
        return "NoData";
    }



    @Override
    protected HtmlBodyElement<?> create() {
        return null;
    }
}
