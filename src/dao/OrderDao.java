package dao;


import common.SQLHelper;
import model.OrderInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cxy on 2017/7/28.
 */
public class OrderDao extends BaseDao {
    public OrderDao(SQLHelper pSQLHelper) {
        super(pSQLHelper);
    }

    public Long insertOrder(String CompanyId, String UserId, String OrderType, String OrderPrice, String FoundingId, String TableId) {
        String sql = "Insert into [Order_b] (UserName,OrderType,OrderPrice,FoundingId,TableId) VALUES (?,?,?,?,?)";
        String[] parameters = {UserId, OrderType, OrderPrice, FoundingId, TableId};
        Long id = 0L;
        id = sqlHelper.ExecuteInsertGetId(CompanyId, sql, parameters);
        return id;
    }

    public int insertOrderDetails(String CompanyId, List<String> Values) {
        String val = "";
        String sql = "Insert into [OrderDetail_b] (OrderId,GoodName,GoodPrice,GoodNum) VALUES ";
        int i = 0;
        if (Values.size() > 1) {
            for (String str : Values) {
                val += "(" + str + "),";
            }
            val = val.substring(0, val.length() - 1);
        } else if (Values.size() == 1) {
            val = "(" + Values.get(0) + ")";
        }
        if (!"".equals(val)) {
            sql += val;
            i = sqlHelper.ExecuteNonQuery(CompanyId, sql, null);
        }
        return i;
    }

    public OrderInfo getOrderInfo(String CompanyId, String Id) {
        String sql = "Select * from [Order_b] where id=?";
        String[] parameters = {Id};
        List<OrderInfo> datas = sqlHelper.executeQueryList(CompanyId, sql, parameters, OrderInfo.class);
        if (datas != null && datas.size() > 0) {
            return datas.get(0);
        }
        return null;
    }

    public List<Map<String, Object>> getOrderId(String CompanyId, String FoundingId, String state) {
        String sql = "Select id from [Order_b] where FoundingId=? and state=?";
        String[] parameters = {FoundingId, state};
        if ("0".equals(state)) {
            sql += " and OrderType=0";
        }
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId, sql, parameters);
        return datas;
    }

    public List<Map<String, Object>> getOrderDetails(String CompanyId, String OrderId) {
        String sql = "Select g.GoodName,o.GoodPrice,o.GoodNum from [OrderDetail_b] o join Good_b g on o.GoodName = g.id where OrderId in (" + OrderId + ")";
        //String[] parameters = {};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId, sql, null);
        return datas;
    }

    public List<Map<String, Object>> getNeedPayOrderPrice(String CompanyId, String FoundingId) {
        String sql = "Select OrderPrice from [Order_b] where FoundingId=? and state=0";
        String[] parameters = {FoundingId};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId, sql, parameters);
        return datas;
    }

    public List<Map<String, Object>> getPaymentOrderPrice(String CompanyId, String FoundingId) {
        String sql = "Select OrderPrice from [Order_b] where FoundingId=? and state=1";
        String[] parameters = {FoundingId};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId, sql, parameters);
        return datas;
    }

    public Long settlement(String CompanyId, String FoundingId, String TableId, String EndTime, String Receivable, String Payment, String UserId) {
        String sql = "Insert into [settlementSY_b] (FoundingId,TableId,EndingTime,Total,Payment,UserName) VALUES (?,?,?,?,?,?)";
        String[] parameters = {FoundingId, TableId, EndTime, Receivable, Payment, UserId};
        Long id = 0L;
        id = sqlHelper.ExecuteInsertGetId(CompanyId, sql, parameters);
        return id;
    }

    public Long balancePay(String CompanyId, String UserId, String BalanceType, String money) {
        String sql = "Insert into [BalanceNote_b] (UserName,BalanceType,money) VALUES (?,?,?)";
        String[] parameters = {UserId, BalanceType, money};
        Long id = 0L;
        id = sqlHelper.ExecuteInsertGetId(CompanyId, sql, parameters);
        return id;
    }
    public int updateOrderState(String CompanyId, String OrderId, String state) {
        int result = 0;
        String sql = "update [Order_b] set state=? where id=?";
        String[] parameters = {state, OrderId};
        result = sqlHelper.ExecuteNonQuery(CompanyId, sql, parameters);
        return result;
    }


    public int updateOrderState(String CompanyId, String OrderId, String state, String PayType) {
        int result = 0;
        String sql = "update [Order_b] set state=?,PayType=? where id=?";
        String[] parameters = {state, PayType, OrderId};
        result = sqlHelper.ExecuteNonQuery(CompanyId, sql, parameters);
        return result;
    }

    public int deleteOrder(String CompanyId, String OrderId) {
        int result = 0;
        String sql = "update [Order_b] set state=-666 where id=?";
        String[] parameters = {OrderId};
        result = sqlHelper.ExecuteNonQuery(CompanyId, sql, parameters);
        return result;
    }

}
