package dao;

import common.SQLHelper;
import common.utils.DataHelper;
import common.utils.TextUtils;
import model.*;

import java.util.List;

public class ShopCarDao extends BaseDao {
    public ShopCarDao(SQLHelper pSQLHelper) {
        super(pSQLHelper);
    }

    public ShopCarInfo getShopCarItemInfoList(String Compid, int Ids) {
//        String sql = "Select * from " + ShopCarPageInfo.TABLE + " where Id in (" + Ids + ");";
        String sql = "SELECT sc.id,sc.[state],s.logo,s.name as ShopName,ps.product_id as Product_id,pg.name as ProductName,ps.price,ps.sku_name,ps.picture as Sku_picture,ci.quantity as [count],ci.Id as cartId,s.Id as Shop_id,pg.Id as product_group_id ,co.coupon_type_id as is_has_coupon FROM shopping_cart sc LEFT JOIN cart_info ci ON sc.Id=ci.shopping_cart_id LEFT JOIN shop s ON ci.shop_id=s.Id LEFT JOIN product_group pg ON ci.product_group_id=pg.Id LEFT JOIN product_sku ps ON pg.sku_id=ps.Id left join (Select c.* from coupon c where not exists (Select 1 from coupon where shop_id=c.shop_id and Id > c.Id)) as co on co.shop_id = s.Id where sc.state=0 and ci.state=0 AND sc.account_id="+Ids;
        List<ShopCarInfo> infos = sqlHelper.executeQueryList(Compid, sql, null, ShopCarInfo.class);
        if (infos != null && infos.size() > 0) {
            return infos.get(0);
        }
        return null;
    }



    public List<ShopCarSlaveInfo> getShopCarSlaveInfoList(String Compid, String userId) {
        String sql = "Select * from " + ShopCarSlaveInfo.TABLE + " where Account_id=?";
        return sqlHelper.executeQueryList(Compid, sql, new String[]{userId}, ShopCarSlaveInfo.class);
    }

    /**
     * 主要是查询购物车某个商品得数量
     *
     * @param Compid
     * @param userId
     * @param Shop_id
     * @param ServerId
     * @return
     */
    public ShopCarSlaveInfo getShopCarSlaveInfo(String Compid, String userId, String Shop_id, String ServerId) {
        String sql = "Select * from " + ShopCarSlaveInfo.TABLE + " where Account_id=? and Shop_id=? and ServerId=?";
        return find(Compid, sql, new String[]{userId, Shop_id, ServerId}, ShopCarSlaveInfo.class);
    }

    public ServerDetailsInfo getServerDetailsInfo(String CompanyId, String sId) {
        String sql = "Select * from ServerDetails_b where Id=?";
        return find(CompanyId, sql, new String[]{sId}, ServerDetailsInfo.class);
    }

    public ShopDetailInfo getShopDetailInfo(String compId, String shopId) {
        String sql = "Select * from " + ShopDetailInfo.TABLE + " where Id=" + shopId;
        return find(compId, sql, null, ShopDetailInfo.class);
    }

    public int changeShopCarItemCount(String Compid, String userId, String Shop_id, String ServerId, int count) {
        ShopCarSlaveInfo shopCarSlaveInfo = getShopCarSlaveInfo(Compid, userId, Shop_id, ServerId);
        boolean needAdd = shopCarSlaveInfo == null;
        int oldCount = shopCarSlaveInfo == null ? 0 : DataHelper.toInt(shopCarSlaveInfo.Quantity, 0);
        if (needAdd || TextUtils.isEmpty(shopCarSlaveInfo.Shopping_cart_id)) {
            if (count <= 0) {
                return 0;
            }
            //需要添加
            //ShopCarPageInfo
            ServerDetailsInfo serverDetailsInfo = getServerDetailsInfo(Compid, ServerId);
            if (serverDetailsInfo == null) {
                System.err.println("没有找到该服务，修改数量失败:Shop_id=" + Shop_id + ",ServerId=" + ServerId);
                return 0;
            }
            //
            ShopDetailInfo shopDetailInfo = getShopDetailInfo(Compid, shopCarSlaveInfo.Shop_id);
            String sql = "INSERT INTO [redmany_bzService].[dbo].[ShoppingCarPage_b] " +
                    "([state], [shopID], [ServiceImage], [ServiceName], [NowPrice], [buyCount], [ServiceID], [unit], [shopName]) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            if (needAdd) {
                shopCarSlaveInfo = new ShopCarSlaveInfo();
                shopCarSlaveInfo.Quantity = "" + count;
            }
//            sqlHelper.ExecuteNonQuery(Compid, sql, new String[]{"0" + serverDetailsInfo.Shop_id,serverDetailsInfo.ServeImage, serverDetailsInfo.Name, serverDetailsInfo.NowPrice,shopCarSlaveInfo.Quantity,""+serverDetailsInfo.Id,serverDetailsInfo.unit,shopDetailInfo == null ? null : shopDetailInfo.Name+ ""});
            shopCarSlaveInfo.Shopping_cart_id = "" + sqlHelper.ExecuteInsertGetId(Compid, sql, new String[]{
                    "0",
                    serverDetailsInfo.Shop_id,
                    serverDetailsInfo.ServeImage,
                    serverDetailsInfo.Name,
                    serverDetailsInfo.NowPrice,
                    shopCarSlaveInfo.Quantity,
                    "" + serverDetailsInfo.Id,
                    serverDetailsInfo.unit,
                    shopDetailInfo == null ? null : shopDetailInfo.Name
            });

            if (needAdd) {
                sql = "INSERT INTO [redmany_bzService].[dbo].[ShoppingCar_slave_b] " +
                        "([state], [Shopping_cart_id], [Shop_id], [ServerId], [Quantity], [Account_id], [Add_time])" +
                        " VALUES (?, ?, ?, ?, ?, ?, NULL);";

                long id = sqlHelper.ExecuteInsertGetId(Compid, sql, new String[]{
                        "0",
                        shopCarSlaveInfo.Shopping_cart_id,
                        serverDetailsInfo.Shop_id,
                        "" + serverDetailsInfo.Id,
                        "" + count,
                        userId,
                });
                shopCarSlaveInfo.Id = id;
            } else {
                sql = "update " + ShopCarSlaveInfo.TABLE + " set Shopping_cart_id=?" + " where Id=?";
                sqlHelper.ExecuteNonQuery(Compid, sql, new String[]{shopCarSlaveInfo.Shopping_cart_id, shopCarSlaveInfo.Id + ""});
            }
            //保存shopCarSlaveInfo
        }
        if (count <= 0) {
            //删除
            String sql = "delete from " + ShopCarSlaveInfo.TABLE + " where Id=?";
            sqlHelper.ExecuteNonQuery(Compid, sql, new String[]{shopCarSlaveInfo.Id + ""});
            String sql2 = "delete from " + ShopCarPageInfo.TABLE + " where Id=?";
            sqlHelper.ExecuteNonQuery(Compid, sql2, new String[]{shopCarSlaveInfo.Id + ""});
            count = 0;
        } else {
            String sql = "update " + ShopCarSlaveInfo.TABLE + " set Quantity=?" + " where Id=?";
            sqlHelper.ExecuteNonQuery(Compid, sql, new String[]{"" + count, shopCarSlaveInfo.Id + ""});
            String sql2 = "update " + ShopCarPageInfo.TABLE + " set buyCount=?" + " where Id=?";
            sqlHelper.ExecuteNonQuery(Compid, sql2, new String[]{"" + count, shopCarSlaveInfo.Id + ""});
        }
        ShopCarAll shopCarAll = find(Compid, "Select * from " + ShopCarAll.TABLE + " where Account_id=?", new String[]{userId}, ShopCarAll.class);
        if (shopCarAll == null) {

            String sql = "INSERT INTO [redmany_bzService].[dbo].[ShoppingCar_b] " +
                    "([state], [Account_id], [Total_quantity], [Add_time]) " +
                    "VALUES ('0', ?, ?, NULL);";
            sqlHelper.ExecuteInsertGetId(Compid, sql, new String[]{userId, "" + count});
        } else {
            int Total_quantity = DataHelper.toInt(shopCarAll.Total_quantity, 0);
            Total_quantity -= oldCount;
            Total_quantity += count;
            String sql = "update " + ShopCarAll.TABLE + " set Total_quantity=?" + " where Id=?";
            sqlHelper.ExecuteNonQuery(Compid, sql, new String[]{"" + Total_quantity, shopCarAll.Id + ""});
        }
        return count;
    }

}
