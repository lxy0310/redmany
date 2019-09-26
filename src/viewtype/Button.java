package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;

public class Button extends ParentView {
    @Override
    public String getType() {
        return "Button";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        com.sangupta.htmlgen.tags.body.forms.Button btn = new com.sangupta.htmlgen.tags.body.forms.Button();
        btn.id(getName());
        String styles = getDataProvider().getStyles(this, getForm());
        String css = getDataProvider().getCssClass(this, getForm());
        String text = getDataProvider().getText(this, getForm());
        String color = getDataProvider().getTextColor(this, getForm());
        btn.text(getView().getTitle());
        /**
         * p.id={Id}$flag=0      flag=1或者0或者2(标签，1为立即购买，0为加入购物车，2为修改)
         * */

        String onclick = getDataProvider().getOnClick(getForm(), this, getView().getTarget(), getView().getTransferParams());
        if (onclick != null) {

           String Id = getData("Id");
            String id = getData("id");
            System.out.println("Id--------------"+Id+"id:----------------"+id);
            if(Id!=null){
                onclick = onclick.replace("{Id}", Id);
            }
            if(id!=null){
                onclick = onclick.replace("{Id}", id);
            }
            String Oid = getData("Oid");
            if(Oid!=null){
                onclick = onclick.replace("{Oid}", Oid);
            }
            String Amount_paid = getData("Amount_paid");
            if(Oid!=null){
                onclick = onclick.replace("{Amount_paid}", Amount_paid);
            }
            if(String.valueOf(getPage().getUserId())!=null){
                onclick = onclick.replace("{cacheid}", String.valueOf(getPage().getUserId()));
            }
            btn.onClick(onclick);
        }

        if(getView().getName().equals("shoppingCartText")){
            btn.onClick("gotoPage('goto:shoppingCart,ShoppingCartForm');");
        }else if(getView().getName().equals("addShoppingCart")){
            int userid = getForm().getPage().getUserId();
            String companyId = getForm().getCompanyId();
            String formName = getForm().getFormName();
            //String pId = getForm().getPage().getmParams("p.Id");
            String pId = getForm().getPage().getInnerParams(getFormName(),"p.Id");
            btn.onClick("addShoppingcart('"+userid+"','"+companyId+"','"+formName+"','"+pId+"');");
        }else if(getView().getName().equals("buy")){
            int userid = getForm().getPage().getUserId();
            String companyId = getForm().getCompanyId();
            String formName = getForm().getFormName();
            //String pId = getForm().getPage().getmParams("p.Id");
            String pId = getForm().getPage().getInnerParams(getFormName(),"p.Id");//20190916修改
             btn.onClick("addShoppingcart('"+userid+"','"+companyId+"','"+formName+"','"+pId+"');");
        }
        if(text!=null) {
            btn.text(text);
        }
        if (color != null) {
            btn.style("color", color);
        }
        if (styles != null) {
            btn.styles(styles);
        }
        if (css != null) {
            btn.addCssClass(css);
        }
        if("confirmReceipt".equals(getName()) && getPage().getCopformName().equals("Order_main")){

            btn.style("display","none");
        }else if("confirmPayment".equals(getName()) && getForm().getValue("state").equals("4")){
            btn.style("display","none");
        }else if("delBtn".equals(getName()) && getForm().getValue("state").equals("4")){
            btn.style("display","none");
        }
        return btn;
    }


}
