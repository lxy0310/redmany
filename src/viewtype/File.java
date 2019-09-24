package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;

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
            Input input =div.input();
            input.id(getName()+0);
            input.addCssClass(getName());
            input.type("File");
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
