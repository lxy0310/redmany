package model;

public class AddressInfo {
    public static final String TABLE = "AddressPage_b";
    public int Id;
    //任命
    public String consigee;
    //地址
    public String ServerAddress;
    //手机
    public String Mobile;
    //门牌
    public String adoorplate;
    public int state;
    @Override
    public String toString() {
        return "AddressInfo{" +
                "Id=" + Id +
                ", state=" + state +
                ", consigee='" + consigee + '\'' +
                ", ServerAddress='" + ServerAddress + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", adoorplate='" + adoorplate + '\'' +
                "}\n";
    }
}
