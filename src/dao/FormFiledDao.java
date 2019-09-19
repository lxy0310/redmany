package dao;


import common.SQLHelper;
import model.FormFiled;
import viewtype.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cxy on 2017/6/15.
 */
public class FormFiledDao extends BaseDao {
    public FormFiledDao(SQLHelper pSQLHelper) {
        super(pSQLHelper);
    }

    /**
     * 根据FormName查询返回结果集
     * @param CompanyId 企业Id
     * @param FormName FormName
     * @return
     */
    public List<Map<String,Object>> getFormFeildList(String CompanyId, String FormName){
        String sql = "SELECT * FROM [FormFiled] WHERE FormName=?";
        String[] parameters = {FormName};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(CompanyId,sql,parameters);
        return datas;
    }

    /**
     * 根据OaCopModel_b的compoundName查询copFormName集合
     *
     * @param Company_Id
     * @param compoundName
     * @param showType
     * @return
     */
    public List<String> getFormNames(String Company_Id, String compoundName, String showType) {
        String sql = "SELECT OaCopModel_b.copFormName from OaCopModel_b" +
                " where OaCopModel_b.compoundName='" + compoundName + "' and OaCopModel_b.showType='" + showType + "';";
        List<Map<String, Object>> datas = new ArrayList<>();
        datas = sqlHelper.executeQueryList(Company_Id, sql, null);
        List<String> names = new ArrayList<>();
        if (datas != null) {
            for (Map<String, Object> line : datas) {
                names.add(String.valueOf(line.get("copFormName")));
            }
        }
        return names;
    }

    /**
     * 获取FormName的Get_data_sql
     *
     * @param CompanyId
     * @return
     */
    public List<Map<String, Object>> getFormDatas(String CompanyId, String get_data_sql) {
        return sqlHelper.executeQueryList(CompanyId, get_data_sql, null);
    }

    public List<Map<String, Object>> getFiledDatas(String CompanyId, String sql) {
        return sqlHelper.executeQueryList(CompanyId, sql, null);
    }

    /**
     * 将结果集转为实体
     *
     * @param map
     * @return
     */
    public FormFiled toFormFiled(Map<String, Object> map) {
        FormFiled formFiled = new FormFiled();
        if (map != null && map.size() > 0) {
            formFiled.setId(Integer.parseInt(ObjIsNull(map.get("id"), "0")));
            formFiled.setFormName(ObjIsNull(map.get("FormName"), ""));
            formFiled.setName(ObjIsNull(map.get("Name"), ""));
            formFiled.setTitle(ObjIsNull(map.get("Title"), ""));
            formFiled.setType(ObjIsNull(map.get("Type"), ""));
            formFiled.setShowType(ObjIsNull(map.get("ShowType"), ""));
            formFiled.setReportShowType(ObjIsNull(map.get("reportShowType"), ""));
            formFiled.setValidateExpreesion(ObjIsNull(map.get("ValidateExpreesion"), ""));
            formFiled.setValidateErrorMessage(ObjIsNull(map.get("ValidateErrorMessage"), ""));
            formFiled.setValidateGroup(ObjIsNull(map.get("ValidateGroup"), ""));
            formFiled.setData_replacer(ObjIsNull(map.get("data_replacer"), ""));
            formFiled.setData_source(ObjIsNull(map.get("data_source"), ""));
            formFiled.setOptioner(ObjIsNull(map.get("optioner"), ""));
            formFiled.setDatabase_field(ObjIsNull(map.get("Database_field"), ""));
            formFiled.setDatabase_field_type(ObjIsNull(map.get("Database_field_type"), ""));
            formFiled.setAutoPostback(Integer.parseInt(ObjIsNull(map.get("AutoPostback"), "0")));
            formFiled.setStatisticsField(ObjIsNull(map.get("StatisticsField"), ""));
            formFiled.setIndex_number(Integer.parseInt(ObjIsNull(map.get("Index_number"), "0")));
            formFiled.setIsNull(ObjIsNull(map.get("IsNull"), ""));
            formFiled.setPublish(Integer.parseInt(ObjIsNull(map.get("publish"), "0")));
            formFiled.setDefaultContent(ObjIsNull(map.get("defaultContent"), ""));
            formFiled.setJsmethod(ObjIsNull(map.get("jsmethod"), ""));
            formFiled.setClickbutton(Integer.parseInt(ObjIsNull(map.get("clickbutton"), "0")));
            formFiled.setClickmethod(ObjIsNull(map.get("clickmethod"), ""));
            formFiled.setImage(ObjIsNull(map.get("image"), ""));
            formFiled.setAttributeId(Integer.parseInt(ObjIsNull(map.get("attributeId"), "0")));
            formFiled.setWapAttribute(ObjIsNull(map.get("wapAttribute"), ""));
            formFiled.setTarget(ObjIsNull(map.get("target"), ""));
            formFiled.setIsDataItem(Integer.parseInt(ObjIsNull(map.get("isDataItem"), "0")));
            formFiled.setTransferParams(ObjIsNull(map.get("transferParams"), ""));
            formFiled.setListAttributeId(ObjIsNull(map.get("listAttributeId"), ""));
            formFiled.setShowState(ObjIsNull(map.get("showState"), ""));
            formFiled.setShowPage(ObjIsNull(map.get("showPage"), ""));
        }
        return formFiled;
    }

    public List<Map<String, Object>> getReplaceFiled(String CompanyId, String FormName) {
        String sql = "SELECT * FROM [FormFiled] WHERE FormName=? AND data_replacer<>0";
        String[] parameters = {FormName};
        List<Map<String, Object>> datas = sqlHelper.executeQueryList(CompanyId, sql, parameters);
        return datas;
    }

    /**
     * 判断Object是否为null
     *
     * @param obj
     * @param trueValue 若为null返回该值
     * @return
     */
    public String ObjIsNull(Object obj, String trueValue) {
        String result;
        if (obj == null) {
            result = trueValue;
        } else {
            result = obj.toString();
        }
        return result;
    }

    /**
     * 查询模板控件信息
     *
     * @param Company_Id 企业id
     * @param formName   模板名称
     * @param showType   模板类型，是否是复合模板 1是 0否
     * @return
     */
    public List<View> getFormContorl(String Company_Id, String formName, String showType) {
        StringBuilder sb = new StringBuilder("");
        if (showType != null) {
            sb.append("SELECT FormFiled.Index_number,FormFiled.Type,FormFiled.attributeId,FormFiled.target," +
                    "FormFiled.Name,FormFiled.iosAttribute,FormFiled.Title,FormFiled.androidAttribute,FormFiled.windowsAttribute,FormFiled.wapAttribute,FormFiled.transferParams ");
            sb.append(",FormFiled.listAttributeId ");
            sb.append(" from FormFiled INNER JOIN OaCopModel_b ON OaCopModel_b.copFormName=FormFiled.FormName");
            sb.append(" AND OaCopModel_b.showType='" + showType + "'");
        } else {
            sb.append("SELECT FormFiled.Index_number,FormFiled.Type,FormFiled.attributeId,FormFiled.target," +
                    "FormFiled.Name,FormFiled.iosAttribute,FormFiled.Title,FormFiled.androidAttribute,FormFiled.windowsAttribute,FormFiled.wapAttribute,FormFiled.transferParams ");
            sb.append(",FormFiled.listAttributeId ");
            sb.append(",FormFiled.filedGroup ");
            sb.append(" from FormFiled ");
            sb.append(" where FormFiled.FormName='" + formName + "'");
        }
//        System.out.println("sql:" + sb.toString());
        return sqlHelper.executeQueryList(Company_Id, sb.toString(), null, View.class);
    }

    public List<View> getFormList(String Company_Id, String formName, String showType) {
        StringBuilder sb = new StringBuilder("");
        sb.append("SELECT FormFiled.Index_number,FormFiled.Type,FormFiled.attributeId,FormFiled.target," +
                "FormFiled.Name,FormFiled.iosAttribute,FormFiled.androidAttribute,FormFiled.windowsAttribute,FormFiled.wapAttribute,FormFiled.transferParams ");
        sb.append(" from FormFiled where FormFiled.FormName='" + formName + "'");
        //sb.append(" where OaCopModel_b.copFormName='" + formName + "'");
        if (showType != null) {
            sb.append(" FormFiled.showType='" + showType + "'");
        }
        sb.append(" ORDER BY CAST(FormFiled.Index_number AS DECIMAL);");
        System.out.println("sql:" + sb.toString());
        return sqlHelper.executeQueryList(Company_Id, sb.toString(), null, View.class);
    }

    public List<View> getFormContorls(String Company_Id, String formName, String showType) {
        StringBuilder sb = new StringBuilder("");
        sb.append("SELECT FormFiled.Index_number,FormFiled.Type,FormFiled.attributeId,FormFiled.target," +
                "FormFiled.Name,FormFiled.iosAttribute,FormFiled.androidAttribute,FormFiled.windowsAttribute,FormFiled.wapAttribute,FormFiled.transferParams ");
        sb.append(" from FormFiled INNER JOIN OaCopModel_b ON OaCopModel_b.copFormName=FormFiled.FormName");
        sb.append(" where OaCopModel_b.copFormName='" + formName + "'");
        if (showType != null) {
            sb.append(" OaCopModel_b.showType='" + showType + "'");
        }
        sb.append(" ORDER BY CAST(FormFiled.Index_number AS DECIMAL);");
        System.out.println("sql:" + sb.toString());
        return sqlHelper.executeQueryList(Company_Id, sb.toString(), null, View.class);
    }

    /**
     * 获取控件属性
     *
     * @param id 属性id
     * @return
     */
    public String getFormContorlAttribute(String Company_Id, String id) {
        if (id == null) return null;
        String sql = "Select androidAttribute from OaAttribute_b where id=" + id;
//        String sql = "Select wapAttribute from OaAttribute_b where id=" + id;
//        System.out.println("===============sql:" + sql);
        String str = null;
        if (id != null && !"".equals(id))
            str = (String) sqlHelper.ExecScalar(Company_Id, sql, null);
//        System.out.println("=========str===============" + str);
        return str;
    }

}
