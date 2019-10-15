package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.forms.Button;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.sections.A;
import com.sangupta.htmlgen.tags.body.table.TBody;
import com.sangupta.htmlgen.tags.body.table.THead;
import com.sangupta.htmlgen.tags.body.table.Table;

public class File extends ParentView {
    @Override
    public String getType() {
        return "File";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        boolean isShow = isShow(getForm().getPage().getShowType());
        Div div = new Div();
        div.id(getName());
        String styles = getDataProvider().getStyles(this, getForm());
        String css = getDataProvider().getCssClass(this, getForm());
        String text = getDataProvider().getText(this, getForm());//input值
        String color = getDataProvider().getTextColor(this, getForm());
        String onclick = getDataProvider().getOnClick(getForm(),this, getView().getTarget(), getView().getTransferParams());
        if (getView()!=null){
            View view=getView();
            if(view.getIsTitle()!=null && "1".equals(view.getIsTitle())) {//不长title
                div.text(text==null?"":text);
                return div;
            }else{
                div.text(view.getTitle()==null?"":view.getTitle());
            }
            A a1 = div.a();
            a1.id(getName()+"_btn");
            a1.herf("javascript:;");
            a1.addCssClass("file");
            a1.text("文件上传");
            a1.onClick("upload_a('"+getName()+"0');");

            Input input =a1.input();
            input.id(getName()+0);
            input.addCssClass(getName()+"0");
            input.type("File");
            input.attr("multiple","multiple");
            input.onChange("uploadFile(this,'"+getName()+"0')");

            Div divList = div.div();
            divList.id(getName()+"_list");

            Input hidden = div.input("hidden","");
            hidden.id(getName()+"0_hidden");

            if(onclick != null){
                div.onClick(onclick);
            }
            if (color != null) {
                div.style("color", color);
            }
            if (styles != null) {
                div.styles(styles);
            }
            if (css != null) {
                div.addCssClass(css);
            }
        }
        return div;
    }

}
