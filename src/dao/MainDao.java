package dao;


import common.SQLHelper;
import common.utils.DataHelper;
import model.ReplaceModel;
import page.HomeForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static dao.ReplacerDao.ObjIsNull;

/**
 * Created by cxy on 2017/6/12.
 */
public class MainDao extends BaseDao {
    public MainDao(SQLHelper pSQLHelper) {
        super(pSQLHelper);
    }

    /**
     * 根据企业Id获取系统设置结果集
     *
     * @param Company_Id
     * @return
     */
    public List<Map<String, Object>> GetSystemSetting(String Company_Id, String RoleId) {
        String sql = "SELECT * FROM [OaSystemSetting_b] WHERE 1=1";
        if (RoleId != null && !"".equals(RoleId)) {
            sql += " and RoleId=" + RoleId;
        }
        System.out.println(sql);
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        // datas = sqlHelper.executeQueryList(Company_Id,sql,null);
        System.out.println(datas);
        return datas;
    }

    /**
     * 查询模板信息
     *
     * @param Company_Id 企业id
     * @param formName   模板名称
     * @param showType   模板类型，是否是复合模板 1是 0否
     * @return
     */
    public List<HomeForm> getForm(String Company_Id, String formName, String showType) {
//        StringBuilder sb = new StringBuilder("");
//        sb.append("SELECT OaCopModel_b.copFormName,OaCopModel_b.showType FROM OaCopModel_b INNER JOIN " +
//                "Menu ON Menu.FormName=OaCopModel_b.compoundName WHERE Menu.FormName='" + formName + "'");
//        if ("copForm".equals(showType)) {//查询复合模板
//            sb.append(" AND Menu.ShowType='copForm'");
//        } else {//不是复合模板
//        }
//        sb.append(" ORDER BY CAST(OaCopModel_b.Index_number AS DECIMAL);");
//        System.out.println("getForm sql:" + sb.toString());
//        return sqlHelper.executeQueryList(Company_Id, sb.toString(), null, HomeForm.class);
//
        return getFormToMenu(Company_Id, formName);
    }



    public List<HomeForm> getFormNoMenu(String Company_Id, String formName) {
        StringBuilder sb = new StringBuilder("");
        sb.append("SELECT OaCopModel_b.copFormName,OaCopModel_b.showType FROM OaCopModel_b WHERE OaCopModel_b.compoundName='" + formName + "'");
        sb.append(" ORDER BY CAST(OaCopModel_b.Index_number AS DECIMAL);");
//        System.out.println("sql:" + sb.toString());
        return sqlHelper.executeQueryList(Company_Id, sb.toString(), null, HomeForm.class);
    }

    public List<HomeForm> getFormToMenu(String Company_Id, String formName) {
       /* String sql = "select Id from ReplaceModel_b where compoundName='" + formName + "'";
        Integer copFormId =sqlHelper.ExecScalar(Company_Id,sql,null)!=null?(Integer)sqlHelper.ExecScalar(Company_Id,sql,null):null;*/
        StringBuilder sb = new StringBuilder("");
        sb.append("SELECT OaCopModel_b.copFormName,OaCopModel_b.showType FROM OaCopModel_b WHERE OaCopModel_b.compoundName='" + formName + "'");
        sb.append(" ORDER BY CAST(OaCopModel_b.Index_number AS DECIMAL);");
        return sqlHelper.executeQueryList(Company_Id, sb.toString(), null, HomeForm.class);
    }

    public ReplaceModel getFormMenuHtml(String Company_Id, String formName) {
        String sql = "select * from ReplaceModel_b where compoundName=?";
        String[] parameters = {formName};
        return find(Company_Id, sql, parameters, ReplaceModel.class);

      /*  String[] parameters = {formName};
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        datas = sqlHelper.executeQueryList(Company_Id, sql, parameters);
        ReplaceModel model = new ReplaceModel();
        if (datas != null && datas.size() > 0) {
            Map<String, Object> map = datas.get(0);
            model = toModel(map);
        }
        return model;*/
    }

    public ReplaceModel toModel(Map<String, Object> map) {
        ReplaceModel model = new ReplaceModel();
        if (map != null && map.size() > 0) {
            model.setId(Integer.parseInt(ObjIsNull(map.get("id"), "0")));
            model.setCompoundName(ObjIsNull(map.get("compoundName"), ""));
            model.setHtml_template(ObjIsNull(map.get("html_template"), ""));

        }
        return model;
    }


    public String getTemplateControl(String CompanyId, String copFormname) {
        //template_contorl
        String sql = "Select * from OaTemplateControl where formName='"+copFormname+"'";
        List<Map<String, Object>> datas = sqlHelper.executeQueryList(CompanyId, sql, null);
        if (datas != null && datas.size() > 0) {
            System.err.println("getTemplateControl:"+datas);
            return DataHelper.toString(datas.get(0).get("template_control"), null);
        }else{
            System.err.println("getTemplateControl:no find "+sql);
        }
        return null;
    }


}
