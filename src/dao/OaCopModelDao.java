package dao;


import common.SQLHelper;
import common.utils.DataHelper;

import java.util.List;
import java.util.Map;

/**
 * Created by cxy on 2017/7/4.
 */
public class OaCopModelDao extends BaseDao {
    public OaCopModelDao(SQLHelper pSQLHelper) {
        super(pSQLHelper);
    }

    /**
     * 根据compoundName，返回复合模板集
     *
     * @param CompanyId
     * @param compoundName
     * @return
     */
    public List<Map<String, Object>> getOaCopModel(String CompanyId, String compoundName) {
        String sql = "SELECT * FROM [OaCopModel_b] WHERE compoundName=? ORDER BY Index_number";
        String[] parameters = {compoundName};
        List<Map<String, Object>> datas = sqlHelper.executeQueryList(CompanyId, sql, parameters);
        return datas;
    }

    /**
     * 根据compoundName，返回标题
     * @param CompanyId
     * @param compoundName
     * @return
     */
    public String getTitle(String CompanyId, String compoundName) {
        String sql = "SELECT top 1 title FROM [OaCopModel_b] WHERE compoundName=?";
        String[] parameters = {compoundName};
        List<Map<String, Object>> datas = sqlHelper.executeQueryList(CompanyId, sql, parameters);
        if (datas != null && datas.size() > 0) {
            return DataHelper.get(datas.get(0), "title");
        }
        return "";
    }
}
