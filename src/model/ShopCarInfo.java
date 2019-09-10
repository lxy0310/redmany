package model;

/**
 * Created by Su on 2017/12/19.
 */
public class ShopCarInfo{
    public static final String TABLE="shopping_cart";
    public int Id;
    public int state;
    public String logo;
    public String ShopName;
    public int Product_id;
    public String ProductName;
    public Double Price;
    public String Sku_name;
    public String Sku_picture;

    public int Count;
    public int cartId;
    public String Shop_id;
    public int product_group_id;
    public String is_has_coupon;


//    public ShopCarPageInfo() {
//    }
    public int getId() {
        return Id;
    }
    public String getProductName() {
        return ProductName;
    }
    public String logo() {
        return logo;
    }
    public Double getPrice() {
        return Price;
    }
    public String getSku_name() {
        return Sku_name;
    }
    public String getSku_picture() {
        return Sku_picture;
    }
    public int getCount() {
        return Count;
    }
    public int getCartId() {
        return cartId;
    }
    public String getShop_id() {
        return Shop_id;
    }
    public int getProduct_group_id() {
        return product_group_id;
    }
    public String getIs_has_coupon() {
        return is_has_coupon;
    }

    public int getProduct_id() {
        return Product_id;
    }
    public void setShopName(String pShopName) {
        ShopName = pShopName;
    }

    @Override
    public String toString() {
        return "ShopCarInfo{" +
                "Id=" + Id +
                ", state=" + state +
                ", logo='" + logo + '\'' +
                ", ShopName='" + ShopName + '\'' +
                ", Product_id=" + Product_id +'\'' +
                ", ProductName=" + ProductName +'\'' +
                ", Price=" + Price +'\'' +
                ", Sku_name='" + Sku_name + '\'' +
                ", Sku_picture='" + Sku_picture + '\'' +
                ", Count='" + Count + '\'' +
                ", cartId='" + cartId + '\'' +
                ", Shop_id='" + Shop_id + '\'' +
                ", product_group_id='" + product_group_id + '\'' +
                ", is_has_coupon='" + is_has_coupon + '\'' +
                "}\n";
    }
}
