package dao;

import common.APPConfig;
import common.SQLHelper;
import model.FormFiled;
import model.Replacer;
import java.util.List;
import java.util.Map;

/*
dao层公共帮助类
 */
public class CommonHelperDao {

    //根据sql语句查询数据
    public List<Map<String, Object>> getDataBySql(String Company_Id,String sql){
        SQLHelper sqlHelper = new SQLHelper(false);
        List<Map<String, Object>> datas = sqlHelper.executeQueryList(Company_Id, sql, null);
        if(datas!=null && datas.size()>0){
            return datas;
        }
        return null;
    }

    //根据替换器名称获取替换器信息
    public Replacer getReplacerBySql(String Company_Id,String sql){
        Replacer replacer = null;
        SQLHelper sqlHelper = new SQLHelper(false);
        List<Map<String, Object>> datas = sqlHelper.executeQueryList(Company_Id, sql, null);
        if(datas!=null && datas.size()>0){
            replacer = new Replacer();
            replacer.setID(datas.get(0).get("ID")==null ? 0 : Integer.parseInt(datas.get(0).get("ID").toString()));
            replacer.setTxtsource(datas.get(0).get("Txtsource")==null ? "": datas.get(0).get("Txtsource").toString());
            replacer.setDatasql(datas.get(0).get("Datasql")==null ? "": datas.get(0).get("Datasql").toString());
            replacer.setChild_Control(datas.get(0).get("Child_Control")==null ? "": datas.get(0).get("Child_Control").toString());
            replacer.setChild_Replacer(datas.get(0).get("Child_Replacer")==null ? "": datas.get(0).get("Child_Replacer").toString());
            replacer.setDatasql_Two_Level(datas.get(0).get("Datasql_Two_Level")==null ? "": datas.get(0).get("Datasql_Two_Level").toString());
            replacer.setStripNumber(datas.get(0).get("stripNumber")==null ? "": datas.get(0).get("stripNumber").toString());
        }
        return replacer;
    }

    //根据sql语句获取FormFiled信息
    public FormFiled getFormFiledBySql(String sql){
        FormFiled formFiled = null;
        SQLHelper sqlHelper = new SQLHelper(false);
        List<Map<String, Object>> datas = sqlHelper.executeQueryList(APPConfig.COMPANYID, sql, null);
        if(datas!=null && datas.size()>0){
            formFiled = new FormFiled();
            formFiled.setName(datas.get(0).get("Name")==null ? "": datas.get(0).get("Name").toString());
            formFiled.setType(datas.get(0).get("Type")==null ? "": datas.get(0).get("Type").toString());
        }
        return formFiled;
    }

}