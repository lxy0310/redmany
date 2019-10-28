package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.text.Label;
import com.sangupta.htmlgen.tags.body.text.Span;

public class Datetime extends ParentView {
    @Override
    public String getType() {
        return "Datetime";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        Div span = new Div();
        span.addCssClass(getName()+"Div");
        String styles = getDataProvider().getStyles(this, getForm());
        String css = getDataProvider().getCssClass(this, getForm());
        String text = getDataProvider().getText(this, getForm());
        String txtName =getDataProvider().getTextName(this,getForm());
        String isEdit =getDataProvider().getTextEdit(this,getForm());
        String color = getDataProvider().getTextColor(this, getForm());

        if (getView() != null) {

            View view=getView();
            Label label = span.label();
            if(view.getIsTitle()!=null && "1".equals(view.getIsTitle())) {//不长title
                span.text(text==null?"":text);
                return span;
            }else{
                label.text(view.getTitle()==null?"":view.getTitle());
            }
            if (view.getIsDouble() !=null && "1".equals(view.getIsDouble())){
                Div div = span.div().styles("display: inline;");
                Span span1 = div.span();
               // Span label1 = span1.span();
               // label1.text("开始时间");
                Input input1 = span1.input();
                input1.addCssClass(getName()+"-val");
                input1.id(getName()); //getName()
                input1.name(getName()+"1");
                input1.type("text");
                input1.onClick("useLayDateMultiple('"+getName()+"')");
                input1.placeholder("请选择"+getView().getTitle());
                Span span2 = div.span();
                Span label2 = span2.span();
                label2.text(" 至 ");
                Input input2 = span2.input();
                input2.addCssClass(getName()+"-val");
                input2.name(getName()+"2");
                input2.id(getName()); //getName()
                input2.type("text");
                input2.onClick("useLayDateMultiple('"+getName()+"')");
                input2.placeholder("请选择"+getView().getTitle());
            }else {
                Input input = span.input();
                input.addCssClass(getName()+"-val");
                input.id(getName()); //getName()
                input.type("text");
                input.onClick("useLayDateMultiple('"+getName()+"')");
                input.placeholder("请选择"+getView().getTitle());
                if(text!=null){
                    input.value(text);
                }
            }

        }

        if(txtName!=null){
            Span name =span.span();
            name.addCssClass("edit-"+getName());
            name.text(txtName);
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



        return span;
    }
}
