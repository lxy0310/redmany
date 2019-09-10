package model;

public class ShopDetailInfo {
    public static final String TABLE = "Shop_b";
    public int Id;
    public String Name;
    public String Region_id;
    public String Logo;
    public String Createtime;
    public String Description;
    public String Mobile;
    public String Phone;
    public String refuseCount;
    public String acceptCount;
    public String businessTime;
    public String Honesties;
    public String businessLicense;
    public String approve;
    public String oShopType;
    public String tShopType;
    public String appointment;
    public String minimumFee;
    public String distributionFee;
    public String extendFee;
    public String qq;
    public String uId;
    public String priorityLevel;
    public String businessEndTime;
    public String is_recommend;
    public String is_hot;
    public String tips;

    @Override
    public String toString() {
        return "ShopDetailInfo{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Region_id='" + Region_id + '\'' +
                ", Logo='" + Logo + '\'' +
                ", Createtime='" + Createtime + '\'' +
                ", Description='" + Description + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Phone='" + Phone + '\'' +
                ", refuseCount='" + refuseCount + '\'' +
                ", acceptCount='" + acceptCount + '\'' +
                ", businessTime='" + businessTime + '\'' +
                ", Honesties='" + Honesties + '\'' +
                ", businessLicense='" + businessLicense + '\'' +
                ", approve='" + approve + '\'' +
                ", oShopType='" + oShopType + '\'' +
                ", tShopType='" + tShopType + '\'' +
                ", appointment='" + appointment + '\'' +
                ", minimumFee='" + minimumFee + '\'' +
                ", distributionFee='" + distributionFee + '\'' +
                ", extendFee='" + extendFee + '\'' +
                ", qq='" + qq + '\'' +
                ", uId='" + uId + '\'' +
                ", priorityLevel='" + priorityLevel + '\'' +
                ", businessEndTime='" + businessEndTime + '\'' +
                ", is_recommend='" + is_recommend + '\'' +
                ", is_hot='" + is_hot + '\'' +
                ", tips='" + tips + '\'' +
                '}';
    }
}
