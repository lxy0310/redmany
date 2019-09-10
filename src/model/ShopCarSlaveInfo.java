package model;

import java.sql.Timestamp;

public class ShopCarSlaveInfo {
    public static final String TABLE = "ShoppingCar_slave_b";
    public long Id;
    public int state;
    public String Shopping_cart_id;
    public String Shop_id;
    public String ServerId;
    public String Quantity;
    public String Account_id;
    public Timestamp Add_time;
}
