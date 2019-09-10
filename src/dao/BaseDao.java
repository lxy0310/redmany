package dao;

import common.SQLHelper;

import java.util.List;

public class BaseDao {
    protected final SQLHelper sqlHelper;

    public BaseDao(SQLHelper pSQLHelper) {
        sqlHelper = pSQLHelper;
    }

    public SQLHelper getSqlHelper() {
        return sqlHelper;
    }

    public <T> T find(String CompanyId, String sql, String[] parameters, Class<T> cc) {
        List<T> list = sqlHelper.executeQueryList(CompanyId, sql, parameters, cc);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


}
