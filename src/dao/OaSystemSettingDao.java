package dao;


import common.SQLHelper;
import model.SystemSetting;

import java.util.List;

/**
 * Created by cxy on 2017/7/4.
 */
public class OaSystemSettingDao extends BaseDao {
    public OaSystemSettingDao(SQLHelper pSQLHelper) {
        super(pSQLHelper);
    }

    private static final String TAGBLE = "OaSystemSetting_b";

    /**
     * 查询某个keyStr的第一个valueStr
     *
     * @param CompanyId
     * @param keyStr
     */
    public SystemSetting getSystemSetting(String CompanyId, String keyStr) {
        String sql = "SELECT top 1 * FROM [" + TAGBLE + "] where keyStr = ?";
        String[] parameters = {keyStr};
        List<SystemSetting> systemSettings = sqlHelper.executeQueryList(CompanyId, sql, parameters, SystemSetting.class);
        if (systemSettings != null && systemSettings.size() > 0) {
            return systemSettings.get(0);
        }
        return null;
    }

    /**
     *
     * @param CompanyId
     * @param keyStr
     * @return
     */
    public String getSystemSettingValue(String CompanyId, String keyStr) {
        return getSystemSettingValue(CompanyId, keyStr, null);
    }
    public String getSystemSettingValue(String CompanyId, String keyStr, String def) {
        SystemSetting systemSetting = getSystemSetting(CompanyId, keyStr);
        if (systemSetting != null) {
            return systemSetting.getValueStr();
        }
        return def;
    }
    /**
     * 查询某个keyStr的全部valueStr
     *
     * @param CompanyId
     * @param keyStr
     */
    public List<SystemSetting> getSystemSettingList(String CompanyId, String keyStr) {
        String sql = "SELECT * FROM [" + TAGBLE + "] where keyStr = ?";
        String[] parameters = {keyStr};
        return sqlHelper.executeQueryList(CompanyId, sql, parameters, SystemSetting.class);
    }
}
