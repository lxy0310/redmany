package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;

import java.util.Map;

public class Button extends ParentView {
    @Override
    public String getType() {
        return "Button";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        com.sangupta.htmlgen.tags.body.forms.Button btn = new com.sangupta.htmlgen.tags.body.forms.Button();
        btn.id(getName());
        btn.attr("class",getName());
        btn.attr("type","button");
        String styles = getDataProvider().getStyles(this, getForm());
        String css = getDataProvider().getCssClass(this, getForm());
        String text = getDataProvider().getText(this, getForm());
        String color = getDataProvider().getTextColor(this, getForm());
        int isEdit = 1;
        String keyId =  getData("Id")==null?"":getData("Id").toString();
        String onclick = getDataProvider().getOnClick(getForm(), this, getView().getTarget(), getView().getTransferParams());
        if (onclick != null) {
           String Id = getData("Id");
            String id = getData("id");
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
        if("car".equals(getPage().getCompany_Id()) && "yuyueButton".equals(getName())){
            if(keyId!=null){
                btn.onClick("buy_car("+ keyId +")");
            }
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
        if (getView() != null) {
            String textStyle = "";
            if (getView().getWapAttribute()!=null){
                String str=getView().getWapAttribute();//获取样式
                String[] strs = str.split("\\[\\^\\]");
                if (strs!=null){
                    for(int i=0;i<strs.length;i++){
                        if (strs[i].contains("style")){
                            int index = strs[i].indexOf(":");
                            if (index > 0) {
                                textStyle = strs[i].substring(index + 1);
                                btn.styles(textStyle);
                            }
                        }
                        if (strs[i].contains("isEdit")){//当有背景图时，为防止文字挡住图片，可设置为0，即为不显示按钮文字
                            String  num=strs[i].substring(strs[i].lastIndexOf(":")+i+1);
                            if ("0".equals(num.trim())){
                                isEdit = 0;
                            }
                        }
                    }
                }
            }
        }
        if(isEdit==1){
            btn.text(getView().getTitle());
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
