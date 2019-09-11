package dao;


import common.SQLHelper;
import model.OaAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cxy on 2017/6/15.
 */
public class OaAttributeDao  extends BaseDao {
    public OaAttributeDao(SQLHelper pSQLHelper) {
        super(pSQLHelper);
    }

    /**
     * 根据企业Id，样式Id 返回样式结果集
     * @param
     * @param CompanyId
     * @return
     */
   public List<Map<String,Object>> getOaAttribute(String CompanyId, String id){
       String sql = "SELECT * FROM [OaAttribute_b] WHERE id=?";
       String[] parameters ={id};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,parameters);
       return datas;
   }

//    public OaAttribute getOaAttribute(String CompanyId, String FormName){
//        String sql = "SELECT * FROM [OaAttribute_b] WHERE name=?";
//        String[] parameters = {FormName};
//        return find(CompanyId, sql, parameters, OaAttribute.class);
//    }


    public String getAttributeById(String CompanyId, Integer id){
        String sql = "SELECT wapAttribute FROM [OaAttribute_b] WHERE id=?";
        String[] parameters ={id.toString()};
        System.out.println("OaAttribute+++"+ sqlHelper.ExecScalar(CompanyId,sql,parameters));
        return  sqlHelper.ExecScalar(CompanyId,sql,parameters)!=null?(String)sqlHelper.ExecScalar(CompanyId,sql,parameters):null;
    }

    public OaAttribute getOaAttributeById(String CompanyId, Integer id){

        String sql = "SELECT name,wapAttribute,windowsAttribute FROM [OaAttribute_b] WHERE id=?";
        String[] parameters ={id.toString()};
       // System.out.println("OaAttribute+++"+ sqlHelper.ExecScalar(CompanyId,sql,parameters));
        return  find(CompanyId,sql,parameters,OaAttribute.class);

   }


}
