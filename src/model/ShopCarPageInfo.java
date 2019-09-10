package model;

public class ShopCarPageInfo {
    public static final String TABLE="ShoppingCarPage_b";
    private int Id;
    private int shopID;
    private String ServiceImage;
    private String ServiceName;
    private int NowPrice;
    private int buyCount;
    private int ServiceID;
    private String unit;
    private String shopName;

    public ShopCarPageInfo() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int pId) {
        Id = pId;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int pShopID) {
        shopID = pShopID;
    }

    public String getServiceImage() {
        return ServiceImage;
    }

    public void setServiceImage(String pServiceImage) {
        ServiceImage = pServiceImage;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String pServiceName) {
        ServiceName = pServiceName;
    }

    public int getNowPrice() {
        return NowPrice;
    }

    public void setNowPrice(int pNowPrice) {
        NowPrice = pNowPrice;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int pBuyCount) {
        buyCount = pBuyCount;
    }

    public int getServiceID() {
        return ServiceID;
    }

    public void setServiceID(int pServiceID) {
        ServiceID = pServiceID;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String pUnit) {
        unit = pUnit;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String pShopName) {
        shopName = pShopName;
    }

    @Override
    public String toString() {
        return "ShopCarItemInfo{" +
                "Id=" + Id +
                ", shopID=" + shopID +
                ", ServiceImage='" + ServiceImage + '\'' +
                ", ServiceName='" + ServiceName + '\'' +
                ", NowPrice=" + NowPrice +
                ", buyCount=" + buyCount +
                ", ServiceID=" + ServiceID +
                ", unit='" + unit + '\'' +
                ", shopName='" + shopName + '\'' +
                "}\n";
    }
}
