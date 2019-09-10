package showtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.forms.Form;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.text.Label;
import com.sangupta.htmlgen.tags.body.text.Span;
import com.sangupta.htmlgen.tags.head.Script;
import common.ApiParser;
import common.SQLHelper;
import common.utils.SafeString;
import model.CarInfo;
import model.ReceivingAddressInfo;
import viewtype.ParentView;

import javax.servlet.http.Cookie;
import java.util.List;

/**
 * Created by hy on 2017/11/20.
 */
public class AffirmOrderForm extends ParentForm {
    private String mIds;
    List<CarInfo> infos;

    int allPay = 0;
    //    List<CouponInfo> mCouponTimeInfos;
    List<ReceivingAddressInfo> mAddressInfos;
//    private ShopCarDao mShopCarItemInfoDao;

    @Override
    public void initDao(SQLHelper pSqlHelper) {
        super.initDao(pSqlHelper);
//        mShopCarItemInfoDao = new ShopCarDao(pSqlHelper);
    }


    @Override
    protected void initData() {
//        super.initData();
        mIds = getPage().getParamValue("Id", "");
        infos = ApiParser.getCarInfos(getPage().getUserId(),companyId,mIds);//ApiParser.parseList(HttpUtils.get(api), ShopCarItemInfo.class);

//        mCouponTimeInfos = ApiParser.getCouponInfos(getPage().getUserId());
        mAddressInfos = ApiParser.getRaddressInfos(getPage().getUserId(),companyId);
        System.out.println("mAddressInfos==============>"+mAddressInfos+"infos=============>>"+infos);
    }


    @Override
    public HtmlBodyElement<?> createViews() {
        Div div = new Div();
        div.id(formName + "-list");
        div.js("js/AffirmOrderPage.js");
        allPay = 0;
        if(infos!=null){
            for (CarInfo info : infos) {
                if (info.getCount() <= 0) {
                    continue;
                }
                allPay += (info.getCount() * info.getPrice());
                Div item = div.div();
                item.id(formName + "-item");
                //商品情况
                item.img(ParentView.IMAGE_PRE + info.getLogo());
                Div name = item.div();
                name.addCssClass("item-text");
                name.div().text(info.getSku_name()).addCssClass("item-name");
                name.div().text(info.getShopName()).addCssClass("item-shop");
                name.div().text(info.getPrice() + "  共" + info.getCount() + "件").addCssClass("item-price");
            }
        }
//        div.div().addCssClass("div-hr");
//        Select Select = div.Select();
//        Select.idAndName("coupon");
//        Select.option("选择代金券", "0");
//        Select.onChange("chooseCoupon(" + allPay + ", this.value);");
//        if(mCouponTimeInfos!=null){
//        for (CouponInfo couponInfo : mCouponTimeInfos) {
//            Select.option(couponInfo.name, couponInfo.Id + "")
//                    .id("coupon-" + couponInfo.Id)
//                    .attr("pay", couponInfo.Face_value)
//                    .onClick("$('#couponV').val('" + couponInfo.Face_value + "');" +
//                            "$('#couponId').val('" + couponInfo.Id + "');");
//        }
//        }

        div.p("总价:" + allPay + "元").id("item-all");
        div.div(bottomMenu());

        return div;
    }

    private Div bottomMenu() {
        Div div = new Div();
//        div.id(formName + "-bottom");
//        div.addCssClass("navbar-fixed-bottom");
        //地址显示
        div.div().addCssClass("hr");

        Form form = new Form();
        form.id("payform");
        form.action("pay");
        form.method("post");
        div.add(form);

        form.input("hidden", getPage().getUserId() + "").idAndName("userId");
        form.input("hidden", mIds).idAndName("ids");
        form.input("hidden", allPay + "").idAndName("realPay");
        form.input("hidden", "0").idAndName("couponId");
        form.input("hidden", "0").idAndName("couponV");
        form.input("hidden", "" + allPay).idAndName("allpay");

        if(mAddressInfos!=null){

            form.input("hidden", (mAddressInfos.size() > 0 ? "" + mAddressInfos.get(0).Id : "0")).idAndName("addressId");


            Span addres = form.span();
            addres.id("addres-list");
            addres.p("地址：");
            form.br();
            int i = 0;
            for (ReceivingAddressInfo info : mAddressInfos) {
                System.out.println("Info=============>"+info+"mAddressInfos.size()=========>"+mAddressInfos.size());

                i++;
                Label addr = addres.label();
                Input input = addr.input("radio", info.Id + "");
                input.idAndName("addressId2");
//            addr.text(info.consigee + " " + info.Mobile + " " + info.ServerAddress + " " + info.adoorplate);
                addr.text(info.Address);
                //checked
                if (i == 1) {
                    input.attr("checked", "checked");
                }
                addr.onClick("$('#address-add').hide();$('#addressId').val('" + info.Id + "');");
                addres.br();
            }


            {
                Span add = form.span();
                add.id("address-add");
                if (mAddressInfos.size() > 0) {
                    add.attr("hidden", null);
                }
                add.label("收件人");
                add.input("text", null).required().idAndName("name");
                add.br();
                add.label("手机号");
                add.input("text", null).required().idAndName("phone");
                add.br();
                add.label("地址");
                add.input("text", null).idAndName("address");
                add.br();
                add.label("门牌");
                add.input("text", null).idAndName("adoorplate");

                Label addr = addres.label();
                Input input = addr.input("radio", "0");
                input.idAndName("addressId2");
                addr.onClick("$('#address-add').show();$('#addressId').val('0');");
                if (mAddressInfos.size() == 0) {
                    input.attr("checked", "checked");
                }
                addr.text("自定义");
            }
        }
//        form.br();
//        form.label("留言:");
//        form.br();
//        form.textArea().idAndName("remark");
//        form.br();
        Input paybtn = form.input("button", "支付").idAndName("toPay").onClick("checkPayForm();");
        if(mAddressInfos==null||mAddressInfos.size()==0){
            //没商品，则支付不可以点击
            paybtn.attr("disabled","disabled");
        }
        div.button("首页").onClick("gotoPage('Service_mainPage,copForm', null);");
//        div.button("购物车").onClick("gotoPage('isNeedLogin:1[^]goto:ShoppingCarPage,Cus_BzService_ShoppingCarForm', null);");
        if ("2".equals(getPage().getCookieValue("show_order_add_fail"))) {
            getPage().saveCookie(new Cookie("show_order_add_fail", "0"), true);
            String msg = getPage().getCookieValue("show_order_add_error");
            Script script = new Script();
            script.text("alert('微信支付失败:" + SafeString.unescape(msg) + "');");
            div.add(script);
        } else if ("1".equals(getPage().getCookieValue("show_order_add_fail"))) {
            getPage().saveCookie(new Cookie("show_order_add_fail", "0"), true);
            Script script = new Script();
            script.text("alert('生成失败');");
            div.add(script);
        } else if ("3".equals(getPage().getCookieValue("show_order_add_fail"))) {
            getPage().saveCookie(new Cookie("show_order_add_fail", "0"), true);
            Script script = new Script();
            script.text("alert('微信授权失败');");
            div.add(script);
        }

        return div;
    }
}
