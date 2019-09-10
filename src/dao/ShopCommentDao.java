package dao;

import common.SQLHelper;
import common.utils.TextUtils;
import model.ShopCommentInfo;

import java.util.List;

public class ShopCommentDao extends BaseDao {
    private final static String TABLE = "CommentPrimary_b";

    public ShopCommentDao(SQLHelper pSQLHelper) {
        super(pSQLHelper);
    }

    /**
     * 最后一个，按照时间排序
     *
     * @param CompanyId
     * @param shopId
     * @return
     */
    public ShopCommentInfo getShopIdLast(String CompanyId, int shopId) {
        String sql = "Select top 1 * from " + TABLE + " where CommentShop=" + shopId + " order by CommentTime desc;";
        List<ShopCommentInfo> list = sqlHelper.executeQueryList(CompanyId, sql, null, ShopCommentInfo.class);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public void addComment(String CompanyId, ShopCommentInfo pShopCommentInfo){

    }
    /**
     * @param CompanyId
     * @param shopId
     * @param commentLevel 评分 -1则是全部
     * @return
     */
    public List<ShopCommentInfo> getShopIdList(String CompanyId, String shopId, int commentLevel) {
        String sql = "Select * from " + TABLE + " where CommentShop=" + shopId;
        if (commentLevel >= 0) {
            if (commentLevel == 4) {
                //null也算是4
                sql += " and ( commentLevel is NULL or commentLevel=" + commentLevel + ")";
            } else {
                sql += " and commentLevel-" + commentLevel;
            }
        }
        sql += " order by CommentTime desc;";
        return sqlHelper.executeQueryList(CompanyId, sql, null, ShopCommentInfo.class);
    }

    public List<ShopCommentInfo> getAllShopIdList(String CompanyId, String shopId, String CommentServiceId) {
        String sql = "Select * from " + TABLE + " where CommentShop=" + shopId;
        if (!TextUtils.isEmpty(CommentServiceId)) {
            sql += " and CommentServiceId=" + CommentServiceId;
        }
        sql += " order by CommentTime desc;";
        return sqlHelper.executeQueryList(CompanyId, sql, null, ShopCommentInfo.class);
    }
}
