package dao;

import common.APPConfig;
import common.SQLHelper;
import model.Replacer;
import java.util.List;
import java.util.Map;

/*
dao层公共帮助类
 */
public class CommonHelperDao {

    //根据sql语句查询数据
    public List<Map<String, Object>> getDataBySql(String sql){
        SQLHelper sqlHelper = new SQLHelper(false);
        List<Map<String, Object>> datas = sqlHelper.executeQueryList(APPConfig.COMPANYID, sql, null);
        if(datas!=null && datas.size()>0){
            return datas;
        }
        return null;
    }

    //根据替换器名称获取替换器信息
    public Replacer getReplacerByName(String replacername){
        Replacer replacer = null;
        String sql = "Select * from Replacer where Replacername='"+replacername+"'";
        SQLHelper sqlHelper = new SQLHelper(false);
        List<Map<String, Object>> datas = sqlHelper.executeQueryList(APPConfig.COMPANYID, sql, null);
        if(datas!=null && datas.size()>0){
            replacer = new Replacer();
            datas.get(0).get("Txtsource");
            replacer.setTxtsource(datas.get(0).get("Txtsource")==null ? "": datas.get(0).get("Txtsource").toString());
            replacer.setDatasql(datas.get(0).get("Datasql")==null ? "": datas.get(0).get("Datasql").toString());
        }
        return replacer;
    }

}
