package showtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.text.Span;
import common.ApiParser;
import common.SQLHelper;
import model.CarInfo;
import model.ReceivingAddressInfo;
import viewtype.ParentView;

import java.util.List;

/**
 * Created by Administrator on 2018/4/11 0011.
 */
public class OrderConfirmForm extends ParentForm {
    private int freight;
    private int count;
    private float totalPrice;
    private String mIds;
    private int deliveryAddressId;
    private String nshopid="";
////    private Map<String, String> cartInfoId = new HashMap<>();
//    private List<String> cartInfoId= new ArrayList<>();
    private String cartInfoId="";
    List<CarInfo> infos;
    int allPay = 0;
    List<ReceivingAddressInfo> mAddressInfos;

    @Override
    public void initDao(SQLHelper pSqlHelper) {
        super.initDao(pSqlHelper);
    }


    @Override
    protected void initData() {
        mIds = getPage().getParamValue("ci.Id", "");
        infos = ApiParser.getCarInfos(getPage().getUserId(),companyId,mIds);//ApiParser.parseList(HttpUtils.get(api), ShopCarItemInfo.class);
        mAddressInfos = ApiParser.getRaddressInfos(getPage().getUserId(),companyId);
        System.out.println("mAddressInfos==============>"+mAddressInfos);
        System.out.println("infos=============>>"+infos);
    }


    @Override
    public HtmlBodyElement<?> createViews() {
        Div div = new Div();
        div.id(formName + "-list");
        div.js("js/AffirmOrderPage.js");
        Div address=div.div();
        address.addCssClass("payAddress");
        Div otherAddr=address.div();
        otherAddr.text("使用其他地址").addCssClass("otherAddress").onClick("gotoPage('goto:delivery_address_manage,copForm','delivery_address_manage:d.Account_id="+getPage().getUserId()+"')");
        Div infoName=address.div();
        infoName.addCssClass("addressWapper");
        Div infoaddr=address.div();
        for (ReceivingAddressInfo info : mAddressInfos) {
            infoName.span().text(info.Consignee).addCssClass("addressPerson");
            infoName.span().text(info.Mobile+"").addCssClass("addressMobile");
            infoaddr.text(info.getRegion()+info.Address).addCssClass("detailAddress");
            deliveryAddressId=info.getId();
        }
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
                Div title = item.div();
                title.addCssClass("shopTitle");
                title.img(ParentView.IMAGE_PRE + info.getLogo()).addCssClass("titleImg");
                title.div().text(info.getShopName()).addCssClass("shopName");
                Div name = item.div();
                name.addCssClass("item-detail");
                name.img(ParentView.IMAGE_PRE + info.getSku_picture()).addCssClass("item-detailimg");
                Div desc=name.div();
                desc.addCssClass("rtMsg");
                desc.div().text(info.getProductName()).addCssClass("item-productname");
                desc.div().text(info.getSku_name()).addCssClass("item-sku");
                desc.div().text("单价:￥"+info.getPrice()).addCssClass("item-price");
                desc.div().text("X"+info.getCount()).addCssClass("item-count");
                desc.div().text("合计:￥"+info.getPrice()*info.getCount()).addCssClass("item-total");
                count+=info.getCount();
                freight+=Integer.parseInt(info.getFreight());
                totalPrice+=info.getPrice()*info.getCount();
//                570^572^573^574
                cartInfoId+=info.getCartId()+"^";
                if(!nshopid.contains(info.getShop_id())){
                    nshopid+=info.getShop_id()+"^";
                }
            }
        }
        Div buyFreight=div.div();
        buyFreight.addCssClass("freight");
        buyFreight.span().text("运费").addCssClass("freightTitle");
        buyFreight.span().text("￥"+freight).addCssClass("freightDetail");
        Div Coupon=div.div();
        Coupon.addCssClass("coupon");
        Coupon.span().text("优惠券").addCssClass("couponTitle");
        Coupon.span().text("请选择").addCssClass("couponSelect");
        Div buyerMsg=div.div();
        buyerMsg.addCssClass("buyMsg").div().text("买家留言:").addCssClass("buyMsgTitle");
        buyerMsg.textArea(8,46).addCssClass("txtarea");
        Div bottom=div.div();
        bottom.addCssClass("allOrderMsg");
        Span orderLeft=bottom.span();
        orderLeft.addCssClass("leftMsg");
        orderLeft.text("共"+count+"件,￥"+totalPrice).addCssClass("orderPrice");
        bottom.span().text("提交订单").addCssClass("subOrder").onClick("subOrder("+getPage().getUserId()+","+deliveryAddressId+",'"+cartInfoId+"',"+totalPrice+","+freight+",'"+nshopid+"')");
        return div;
    }

}
