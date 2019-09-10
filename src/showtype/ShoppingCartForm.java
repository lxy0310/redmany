package showtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.embed.Img;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.text.Span;
import com.sangupta.htmlgen.tags.head.Script;
import common.ApiParser;
import common.utils.HttpUtils;
import common.utils.SafeString;
import model.ShopCarInfo;
import page.Page;
import viewtype.ParentView;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartForm extends ParentForm {

    private List<ShopCarInfo> mShopCarItemInfos = new ArrayList<>();

    public static List<ShopCarInfo> getShopCarItemInfos(int userId) {
        String url = ApiParser.getShopCarUrl(userId, null);
        String rs = HttpUtils.get(url);
        if (rs != null) {
            System.err.println(rs);
            return ApiParser.parseList(rs, ShopCarInfo.class);
        }
        return new ArrayList<>();
    }

    String enUrl;

    @Override
    protected void loadData(String sql) {
        System.out.println("ShoppingCartForm=========================="+sql);
        mShopCarItemInfos = getShopCarItemInfos(getPage().getAccountHelper().getUserId());
        enUrl = SafeString.escape(SafeString.encode(getPage().getUrl()));
        System.out.println("===========mShopCarItemInfos====="+mShopCarItemInfos);
        super.loadData(sql);
    }

    private Div makeContent() {


        if (mShopCarItemInfos == null) {
            return new Div().text("购物车还没有商品");
        }
        Div topdiv = new Div();

        Div div = topdiv.div();

        div.id(formName + "-list");
        for (ShopCarInfo info : mShopCarItemInfos) {
            if (info.getCount() <= 0) {
                continue;
            }
            Double pay = (info.getCount() * info.getPrice());
            Div item = div.div();
            item.id(formName + "-item");
//            item.div().style("width","100%").style("height","120px");
            Input input = item.input().type("checkbox").id("item-chb").addCssClass("demo--radio");
            input.value(info.getCartId() + "");
            Span checkbox = item.span().addCssClass("demo--checkbox demo--radioInput");
//                    <span class="demo--checkbox demo--radioInput"></span>
            //商品情况
            item.img(ParentView.IMAGE_PRE + info.getSku_picture()).addCssClass("itemImg");
            Div name = item.div();
            name.addCssClass("item-text");
            name.div().text(info.getProductName()).addCssClass("item-name");
            name.div().text(info.getSku_name()).addCssClass("item-shop");
            Div ndiv = name.div();
            ndiv.addCssClass("price-count");
            ndiv.span("￥"+info.getPrice()).addCssClass("item-price");
            ndiv.span("X"+info.getCount()).addCssClass("item-count");

            int allpay;
            Double pricet = info.getPrice();
            int count = info.getCount();
            allpay = (int) (pricet * count);

//            input.onClick("changle(this, " + info.getPrice() + ");");
            input.onClick("changle(this, " + allpay + ");");
//            Div right = item.div();
//            right.addCssClass("item-button");
//
//            right.button("删除").onClick("goto('shopCar?type=del&Id=" + info.getId()
//                    + "&shop=" + info.getProduct_id() + "&srvid=" + info.getProduct_id() +
//                    "&user=" + getPage().getAccountHelper().getUserId() +
//                    "&url=" + enUrl + "');");
//
//            right.button("-1").onClick("goto('shopCar?type=mod&Quantity=" + (info.getCount() - 1)
//                    + "&Id=" + info.getId() +
//                    "&user=" + getPage().getAccountHelper().getUserId() + "&url=" + enUrl +
//                    "');");
//            right.button("+1").onClick("goto('shopCar?type=mod&Quantity=" + (info.getCount() + 1)
//                    + "&Id=" + info.getId() +
//                    "&user=" + getPage().getAccountHelper().getUserId() + "&url=" + enUrl +
//                    "');");
        }

        div.div().style("height","100px");
        return div;
    }

    private Div bottomMenu() {
        List<Integer> ids = new ArrayList<>();
//        ShopCarInfo info=null;
        for ( ShopCarInfo info : mShopCarItemInfos) {
            if (info.getCount() <= 0) {
                continue;
            }
            ids.add(info.getCartId());
        }
        Div div = new Div();
        div.id(formName + "-bottom");
        div.addCssClass("navbar-fixed-bottom");
//        div.button("首页").onClick("gotoPage('goto:Service_mainPage,copForm', null);");
        //gotoPage('isNeedLogin:1[^]goto:ShoppingCarPage,Cus_BzService_ShoppingCarForm', null);

//        ShopCarInfo infos = new ShopCarInfo();
//        div.input("checkbox","value").text("全选");
        div.span("合计:").idAndName("item-all");
        if (ids.size() > 0) {
            div.button("结算").onClick("addOrder('" +
                    Page.getHomeUrl("orderConfirm", "OrderConfirmForm") + "');").addCssClass("settlement");
        }
//        div.button("结算").style("background-color","red").onClick("gotoPage('goto:ReceivingAddressForm,ReceivingAddressForm',null);");

//        div.button("清空").onClick("goto('shopCar?type=clear&Id=" +
//                "&user=" + getPage().getAccountHelper().getUserId() +
//                "&url=" + enUrl + "');");
        return div;
    }

    @Override
    public HtmlBodyElement<?> createViews() {
        Div div = new Div();
        div.id(formName);
        //标题
        div.add(makeContent());
        //购物车的底部
        div.add(bottomMenu());
        if ("1".equals(getPage().getCookieValue("show_del_ok"))) {
            getPage().saveCookie(new Cookie("show_del_ok", "0"));
            div.add(new Script().text("alert('删除成功');"));
        } else if ("1".equals(getPage().getCookieValue("show_mod_add_ok"))) {
            getPage().saveCookie(new Cookie("show_mod_add_ok", "0"));
            div.add(new Script().text("alert('增加成功');"));
        } else if ("1".equals(getPage().getCookieValue("show_mod_rm_ok"))) {
            getPage().saveCookie(new Cookie("show_mod_rm_ok", "0"));
            div.add(new Script().text("alert('减少成功');"));
        } else if ("1".equals(getPage().getCookieValue("show_order_fail"))) {
            getPage().saveCookie(new Cookie("show_order_fail", "0"));
            div.add(new Script().text("alert('下单失败');"));
        }

        return div;
    }

    //    @Override
    public String toHtml(ParentView view) {
        if ("ServiceImage".equals(view.getName())) {
            Img img = new Img();
            img.id(view.getName());
            img.src(getDataProvider().getImageUrl(view, view.getForm()));
            String onclick = getDataProvider().getOnClick(view.getForm(), view, view.getView().getTarget(), view.getView().getTransferParams());
            if (onclick != null) {
                img.onClick(onclick);
            }
            return img.toString();
        }
        return super.toHtml(view);
    }
}
