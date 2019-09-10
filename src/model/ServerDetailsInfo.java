package model;

import java.sql.Timestamp;

public class ServerDetailsInfo {
    public int Id;
    public int state;
    public String Name;
    public String OriginalPrice;
    public String NowPrice;
    public String SoldNumber;
    public String Product_code;

    public String ServerDescribe;
    public String ServerDetails;
    public String Heat;
    public String Fastest;
    public String GoodRate;
    public String keywords;
    public String Product_type_id;
    public String ServeImage;
    public String Is_alive;
    public String Category_id;
    public String Shop_id;
    public Timestamp Add_time;
    public String Modify_admin_id;
    public String unit;
    public String shopName;
    public String DetailsImages;
    public String preference;
    public String Modify_time;


    @Override
    public String toString() {
        return "ServerDetailsInfo{" +
                "Id=" + Id +
                ", state=" + state +
                ", Name='" + Name + '\'' +
                ", OriginalPrice='" + OriginalPrice + '\'' +
                ", NowPrice='" + NowPrice + '\'' +
                ", SoldNumber='" + SoldNumber + '\'' +
                ", Product_code='" + Product_code + '\'' +
                ", ServerDescribe='" + ServerDescribe + '\'' +
                ", ServerDetails='" + ServerDetails + '\'' +
                ", Heat='" + Heat + '\'' +
                ", Fastest='" + Fastest + '\'' +
                ", GoodRate='" + GoodRate + '\'' +
                ", keywords='" + keywords + '\'' +
                ", Product_type_id='" + Product_type_id + '\'' +
                ", ServeImage='" + ServeImage + '\'' +
                ", Is_alive='" + Is_alive + '\'' +
                ", Category_id='" + Category_id + '\'' +
                ", Shop_id='" + Shop_id + '\'' +
                ", Add_time=" + Add_time +
                ", Modify_admin_id='" + Modify_admin_id + '\'' +
                ", unit='" + unit + '\'' +
                ", shopName='" + shopName + '\'' +
                ", DetailsImages='" + DetailsImages + '\'' +
                ", preference='" + preference + '\'' +
                '}';
    }
}
