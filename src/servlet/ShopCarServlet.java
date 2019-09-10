package servlet;


import common.CommonUtils;
import common.SQLHelper;
import common.utils.SafeString;
import dao.ShopCarDao;
import page.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShopCarServlet extends BaseServlet {

    @Override
    protected void doHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SQLHelper sqlHelper = new SQLHelper(req);
        ShopCarDao shopCarDao = new ShopCarDao(sqlHelper);
        req.setCharacterEncoding("UTF-8");
        String type = req.getParameter("type");
        if("mod2".equals(type)){
            String user = req.getParameter("Account_id");
            String ShopId = req.getParameter("Shop_id");
            String ServerId = req.getParameter("ServerId");
            int Quantity = Integer.parseInt(req.getParameter("Quantity"));
            shopCarDao .changeShopCarItemCount(Page.COMPANYID,user,ShopId,ServerId,Quantity);
        }else if ("pay".equals(type)) {
            String userName = req.getParameter("user");
            String ServerId = req.getParameter("ServerId");
            String ShopId = req.getParameter("Shop_id");
            int Quantity = Integer.parseInt(req.getParameter("Quantity"));
            String api = CommonUtils.SumbitUrl+"Company_Id="+ Page.COMPANYID +"&formName=ShoppingCar_slave&showType=newForm" +
                    "&Account_id="+userName+"&userId="+userName+"&Quantity="+Quantity+"&Shop_id="+ShopId+"&ServerId="+ServerId;

            openUrl(api);
            String url = Page.getHomeUrl("AffirmOrderPage", "Cus_BzService_AffirmOrderForm")
                    + "&transferParams=" + SafeString.escape("s.ServerId=" +ServerId);
         //   Cus_BzService_AffirmOrderForm.num = Quantity;
            resp.sendRedirect(url);
        } else if("clear".equals(type)){
            String userName = req.getParameter("user");
            String clearApi = CommonUtils.SumbitUrl+"uId="+userName+"&Company_Id="+ Page.COMPANYID +"&formName=DeleteServiceMidForm&showType=newForm" +
                    "&Id=&userId="+userName;
            openUrl(clearApi);
        }else if("del".equals(type)){
            String userName = req.getParameter("user");
            String Id = req.getParameter("Id");
            String delApi = CommonUtils.SumbitUrl+"Company_Id="+ Page.COMPANYID +"&formName=ShoppingCar_slave&showType=listModifyForm" +
                    "&Id="+Id+"&state=-666&userId="+userName;
            openUrl(delApi);
        } else if("mod".equals(type)){
            int Quantity = Integer.parseInt(req.getParameter("Quantity"));
            String userName = req.getParameter("user");
            String Id = req.getParameter("Id");
            String modApi = CommonUtils.SumbitUrl+"Company_Id="+ Page.COMPANYID +"&formName=cart_info&showType=listModifyForm" +
                    "&Quantity="+Quantity+"&Id="+Id+"&userId="+userName;
            openUrl(modApi);
        } else if ("payAll".equals(type)) {
            String totalPrice = req.getParameter("totalPrice");
         //   Cus_BzService_AffirmOrderForm.totalPrice = totalPrice;

        }else if ("subOrder".equals(type)) {
//            userId,deliveryAddressId,cartInfoId,amountPaid,freightPayable,shopId
            String userId = req.getParameter("userId");
            String deliveryAddressId = req.getParameter("deliveryAddressId");
            String cartInfoId = req.getParameter("cartInfoId");
            String amountPaid = req.getParameter("amountPaid");
            String freightPayable = req.getParameter("freightPayable");
            String shopId = req.getParameter("shopId");
            String customerMsg = req.getParameter("customerMsg");

            String subApi = CommonUtils.SumbitUrl+"Company_Id="+ Page.COMPANYID +"&formName=tempOrder_info&showType=newForm&userId=" +userId+
                    "&Account_id="+userId+"&Delivery_address_id="+deliveryAddressId+"&cart_info_id="+cartInfoId+"&Amount_paid="+amountPaid+"&Id=&Remark_customer="+ SafeString.escape(customerMsg)+"&Coupon_code=&Freight_payable="+freightPayable+"&shop_id="+shopId;
            openUrl(subApi);
        }
    }
}
