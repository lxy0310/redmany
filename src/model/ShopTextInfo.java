package model;

public class ShopTextInfo {
    public int Id;
    public String SafeguardName;
    public String  CreateDate;
    public int shopID;

    @Override
    public String toString() {
        return "ShopTextInfo{" +
                "Id=" + Id +
                ", SafeguardName='" + SafeguardName + '\'' +
                ", CreateDate='" + CreateDate + '\'' +
                ", shopID=" + shopID +
                '}';
    }
}
