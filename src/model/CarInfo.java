package model;

/**
 * Created by hy on 2018/2/1 0001.
 */
public class CarInfo {

    public int Id;
    public String Logo;
    public String ShopName;
    public String ProductName;
    public float Price;
    public String Sku_name;
    public String Sku_picture;
    public int Count;
    public String cartId;
    public String Shop_id;
    public String Product_id;
    public String Freight;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public String getSku_name() {
        return Sku_name;
    }

    public void setSku_name(String sku_name) {
        Sku_name = sku_name;
    }

    public String getSku_picture() {
        return Sku_picture;
    }

    public void setSku_picture(String sku_picture) {
        Sku_picture = sku_picture;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getShop_id() {
        return Shop_id;
    }

    public void setShop_id(String shop_id) {
        Shop_id = shop_id;
    }

    public String getProduct_id() {
        return Product_id;
    }

    public void setProduct_id(String product_id) {
        Product_id = product_id;
    }

    public String getFreight() {
        return Freight;
    }

    public void setFreight(String freight) {
        Freight = freight;
    }

    @Override
    public String toString() {
        return "CarInfo{" +
                "Id=" + Id +
                ", Logo='" + Logo + '\'' +
                ", ShopName='" + ShopName + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", Price='" + Price + '\'' +
                ", Sku_name='" + Sku_name + '\'' +
                ", Sku_picture='" + Sku_picture + '\'' +
                ", Count='" + Count + '\'' +
                ", cartId='" + cartId + '\'' +
                ", Shop_id='" + Shop_id + '\'' +
                ", Product_id='" + Product_id + '\'' +
                ", Freight='" + Freight + '\'' +
                '}';
    }
}
