package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;

import java.util.ArrayList;
import java.util.List;
//import com.sangupta.htmlgen.tags.body.sections.Select;


public class Select extends ParentView {
    @Override
    public String getType() {
        return "Select";
    }

    @Override
    protected HtmlBodyElement<?> create() {

        com.sangupta.htmlgen.tags.body.sections.Select select = new com.sangupta.htmlgen.tags.body.sections.Select();
        select.id(getName());
        String styles = getDataProvider().getStyles(this, getForm());
        String css = getDataProvider().getCssClass(this, getForm());
        String text = getDataProvider().getText(this, getForm());
        String color = getDataProvider().getTextColor(this, getForm());
        String onclick = getDataProvider().getOnClick(getForm(),this, getView().getTarget(), getView().getTransferParams());
        if(onclick != null){
            select.onClick(onclick);
        }
        if (color != null) {
            select.style("color", color);
        }
        if (styles != null) {
            select.styles(styles);
        }
        if (css != null) {
            select.addCssClass(css);
        }
        View view = getView();

        if(text !=null){
            Div div = new Div();
            /* div.text("卡类型");*/
            //com.sangupta.htmlgen.tags.body.sections.Select select1 = new com.sangupta.htmlgen.tags.body.sections.Select();
            //select1 = div.select();
            /*      select1.option("请选择","0");*/
            String[] arr=text.split("\\#");
            List<String> list=new ArrayList<String>();

            for (int i=0;i<arr.length;i++){
                // list.add(arr[i]);
                System.out.println("-------------");
                System.out.println(arr[i].substring(0,arr[i].indexOf(':')));
                System.out.println(arr[i].substring(arr[i].indexOf(':')+1));
                String a=arr[i].substring(0,arr[i].indexOf(':'));
                String b=arr[i].substring(arr[i].indexOf(':')+1);
                System.out.println("a"+a+"------b"+b);
                select.option(b,a);
            }


            return  div;

        }
        if("cardType".equals(getName())){

            Div div = new Div();
            div.text("卡类型");
            com.sangupta.htmlgen.tags.body.sections.Select select1 = new com.sangupta.htmlgen.tags.body.sections.Select();
            select1 = div.select();
            select1.option("信用卡","信用卡");
            select1.option("储蓄卡","储蓄卡");
            return  div;
        }
 /*       if("transferType".equals(getName())){

            Div div = new Div();
            div.text("卡类型");
            com.sangupta.htmlgen.tags.body.sections.Select select1 = new com.sangupta.htmlgen.tags.body.sections.Select();
            select1 = div.select();
            select1.option("信用卡","信用卡");
            select1.option("储蓄卡","储蓄卡");
            return  div;
        }*/
        return select;

    }

}
