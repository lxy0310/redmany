package model;

import java.sql.Timestamp;

public class OrderInfo {
    //
    public int Id;
    public int state;

    /**
     * 待付款
     */
    public boolean isWaitPay() {
        return state == 1;
    }

    /**
     * 进行中
     */
    public boolean isReceipt() {
        return state == 2 || state == 7;
    }

    /**
     * 已完成
     */
    public boolean isCompleted() {
        return state == 3;
    }

    /**
     * 待评价
     */
    public boolean isComment() {
        return state == 4;
    }

    public String shopName;
    public String ServiceImage;
    public String ServiceName;
    public String NowPrice;
    public String buyCount;
    public Timestamp createdate;
    public String shopID;
    public String ServiceID;
    public String Amount_payable;
    public String Coupon_reduce_price;
    public String Consignee;
    public String Address;
    public String Mobile;
    public String appointmentTime;

    public String remark;
    public String CarID;

    /**
     * 总价
     *
     * @return
     */
    public int getAllPay() {
        try {
            int count = Integer.parseInt(buyCount);
            int price = Integer.parseInt(NowPrice);
            return count * price;
        } catch (Exception e) {
            return 0;
        }
    }

    public OrderInfo() {

    }

    @Override
    public String toString() {
        return "ShopInfo{" +
                "id=" + Id +
                ", state=" + state +
                ", shopName='" + shopName + '\'' +
                ", ServiceImage='" + ServiceImage + '\'' +
                ", ServiceName='" + ServiceName + '\'' +
                ", NowPrice='" + NowPrice + '\'' +
                ", buyCount='" + buyCount + '\'' +
                ", createdate=" + createdate +
                ", shopID='" + shopID + '\'' +
                ", ServiceID='" + ServiceID + '\'' +
                ", Amount_payable='" + Amount_payable + '\'' +
                ", Coupon_reduce_price='" + Coupon_reduce_price + '\'' +
                ", Consignee='" + Consignee + '\'' +
                ", Address='" + Address + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", appointmentTime='" + appointmentTime + '\'' +
                ", remark='" + remark + '\'' +
                ", CarID='" + CarID + '\'' +
                '}';
    }
}
