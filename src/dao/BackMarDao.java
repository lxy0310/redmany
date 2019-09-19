package dao;

import common.SQLHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BackMarDao extends BaseDao {
    public BackMarDao (SQLHelper pSQLHelper){ super(pSQLHelper);}

    /**
     * 根据替换名查询替换器
     * @param Company_Id
     * @param replacerName
     * @return
     */
    public List<Map<String,Object>> getReplacer(String Company_Id,String replacerName){
        String sql="select * from Replacer where Replacername = ?";
        String[] parameters={replacerName};
        return  sqlHelper.executeQueryList(Company_Id,sql,parameters)!=null?sqlHelper.executeQueryList(Company_Id,sql,parameters):null;
    }

    /**
     * 根据sql,uid查询有多少条数据
     * @param Company_Id
     * @param Get_data_sql
     * @param uid
     * @return
     */
    public int getCountData(String Company_Id,String Get_data_sql,String uid){
        int count=0;
        String sql="";
        if (Get_data_sql!=null){
            if (Get_data_sql.contains("[userid]")){
                Get_data_sql = Get_data_sql.replace("[userid]",uid);
            }
            String strs=Get_data_sql.replaceAll("select.*?from","select count(1) from");
            sql = strs;
        }
        count = (int)sqlHelper.ExecScalar(Company_Id,sql,null);
        return count;
    }


    /**
     * 根据sql,uid查询数据
     * @param Company_Id
     * @param Get_data_sql
     * @param uid
     * @return
     */
    public List<Map<String,Object>> getListBySql(String Company_Id,String Get_data_sql,String uid){
        String sql="";
        if (Get_data_sql!=null){
            if (Get_data_sql.contains("[userid]")){
                Get_data_sql = Get_data_sql.replace("[userid]",uid);
            }
            sql = Get_data_sql;
        }
        return sqlHelper.executeQueryList(Company_Id,sql,null)!=null?sqlHelper.executeQueryList(Company_Id,sql,null):null;
    }

    /**
     * 根据sql查询数据
     * @param Company_Id
     * @param Get_data_sql
     * @return
     */
    public List<Map<String,Object>> getListBySql(String Company_Id,String Get_data_sql){
        String sql="";
        if (Get_data_sql!=null){
            sql = Get_data_sql;
        }
        return sqlHelper.executeQueryList(Company_Id,sql,null)!=null?sqlHelper.executeQueryList(Company_Id,sql,null):null;
    }

    /**
     * 搜索页面查询
     * @param Company_Id
     * @param formName
     * @param Search_fields
     * @return
     */
    public  List<Map<String,Object>> getSearchFormField(String Company_Id,String formName,String Search_fields){
        List<Map<String,Object>> data=new ArrayList<>();
        if (Search_fields!=null){
            if (Search_fields.contains(",")){
                String[] Search_fieldList=Search_fields.split(",");
                for (int i=0;i<Search_fieldList.length;i++){
                    List<Map<String,Object>> datas=new ArrayList<>();
                    String sql="SELECT * from FormFiled where FormName=? and Name=?";
                    String search=Search_fieldList[i];
                    String[] param={formName,search};
                    datas= sqlHelper.executeQueryList(Company_Id,sql,param);
                    data.addAll(datas);
                }
            }
        }
        return data;
    }

    /**
     * 查询form表单
     * @param Company_Id
     * @param formName
     * @return
     */
    public List<Map<String,Object>> getFormSearchLsit(String Company_Id,String formName){
        String sql="select * from Form where formName=?";
        String[] parameters={formName};
        return sqlHelper.executeQueryList(Company_Id,sql,parameters)!=null?sqlHelper.executeQueryList(Company_Id,sql,parameters):null;
    }

    /**
     *  点击更改当前数据按钮状态
     * @param Company_Id
     * @param listId
     * @param afterState
     * @param tableName
     * @return
     */
    public int updateBtnList(String Company_Id,int listId,int afterState,String tableName ){
        String sql="update "+tableName+" set State=? where Id=?";
        String[] parameters={String.valueOf(afterState),String.valueOf(listId)};
        return sqlHelper.ExecuteNonQuery(Company_Id,sql,parameters);
    }

    /**
     * 查询菜单
     * @param Company_Id
     * @param userId
     * @param roleId
     * @param deptId
     * @return
     */
    public  List<Map<String, Object>> getMenuLists(String Company_Id,String userId,String roleId,String deptId){
        String sql="";
        if (userId!=null){
            sql="select m.* from menu m  LEFT JOIN UserMenu u on m.id=u.MenuID where 1=1  and u.UserID="+userId;
        }
        if (roleId!=null){
            sql+=" UNION";
            sql+=" (select m.* from menu m LEFT JOIN RoleMenu r on m.id=r.MenuID where 1=1  AND r.roleId="+roleId+") ";
        }
        if (deptId!=null){
            sql+=" UNION";
            sql+=" (select m.* from menu m  LEFT JOIN DepartmentMenu d on m.id=d.MenuID where 1=1  and d.DepartmentId="+deptId+") ";
        }

        /*String sql="select * from menu m LEFT JOIN RoleMenu r on m.id=r.MenuID LEFT JOIN UserMenu u on m.id=u.MenuID LEFT JOIN DepartmentMenu d on m.id=d.MenuID where 1=1 ";
        if (userId!=null && !"0".equals(userId)){
            sql+=" and u.UserID="+userId;
        }
        if (roleId!=null){
            if (userId!=null && !"0".equals(userId)){
                sql+=" or r.roleId="+roleId;
            }else {
                sql += " and r.roleId=" + roleId;
            }
        }
        if (deptId!=null && !deptId.equals("0")){
                if (userId!=null || roleId!=null){
                    sql+=" or d.DepartmentId="+deptId;
                }else{
                    sql+=" and d.DepartmentId="+deptId;
                }

        }*/
        System.out.println(sql);
        return sqlHelper.executeQueryList(Company_Id,sql,null);
    }

    /**
     * 根据formname获取数据表名(tableName)
     * @param Company_Id
     * @param FormName
     * @return
     */
    public String getTableNameByFormName(String Company_Id,String FormName){
        String sql="select Table_name from Form where FormName=?";
        String[] parameters={FormName};
        return  sqlHelper.ExecScalar(Company_Id,sql,parameters)!=null?(String) sqlHelper.ExecScalar(Company_Id,sql,parameters):null;
    }

    /**
     *  查询列表按钮
     * @param Company_Id
     * @param userId
     * @param FormName
     * @param FormState
     * @return
     */
    public  List<Map<String, Object>> getFormListOperationShow(String Company_Id,int userId,String FormName,int FormState){
        String sql="{call GetFormStateOperationUserId(?,?,?)}";
        String[] parameters={String.valueOf(userId),FormName,String.valueOf(FormState)};
        return sqlHelper.executeQueryList(Company_Id,sql,parameters)!=null?sqlHelper.executeQueryList(Company_Id,sql,parameters):null;
    }

    /**
     *  获取操作表的id
     * @param Company_Id
     * @param operationId
     * @return
     */
    public String getTemplatePageByOperationId(String Company_Id,int operationId){
        String sql="select TemplatePage from Operation where Id=?";
        String[] parameters={String.valueOf(operationId)};
        return sqlHelper.ExecScalar(Company_Id,sql,parameters)!=null?(String) sqlHelper.ExecScalar(Company_Id,sql,parameters):null;
    }


    /**
     * 查询后台操作列表
     * @param Company_Id
     * @param FormStateId
     * @return
     */
    public List<Map<String, Object>> getFormStateOperationByStateId(String Company_Id,String FormStateId){
        String sql="select OperationName from FormStateOperation where FormStateId=?";
        String[] parameters={FormStateId};

        return sqlHelper.executeQueryList(Company_Id,sql,parameters);
    }

    /**
     * 根据FormName查询FormState的ID
     * @param Company_Id
     * @param FormName
     * @return
     */
    public Integer getFormStateIdByFormName(String Company_Id,String FormName){
        String sql="select Id from FormState where FormName=?";
        String[] parameters={FormName};
        return  sqlHelper.ExecScalar(Company_Id,sql,parameters)!=null?(Integer) sqlHelper.ExecScalar(Company_Id,sql,parameters):null;
    }


    /**
     * 根据FormName查询数据库Form
     * @param Company_Id
     * @param FormName
     * @return
     */
    public List<Map<String, Object>> getForms(String Company_Id,String FormName){
        String sql="select * from Form where formName=?";
        String[] parameters = {FormName};
        return sqlHelper.executeQueryList(Company_Id,sql,parameters);
    }

    /**
     * 根据FormName查询FormFeild
     * @param Company_Id
     * @param FormName
     * @return
     */
    public List<Map<String, Object>> getFormFeilds(String Company_Id,String FormName){
        String sql="select * from FormFiled where formName=?";
        String[] parameters = {FormName};
        return sqlHelper.executeQueryList(Company_Id,sql,parameters);
    }

    /**
     * 查询面板列表
     * @param Company_Id
     * @return
     */
    public List<Map<String,Object>> getPanelList(String Company_Id){
        String sql="select * from UserPanel";
        return sqlHelper.executeQueryList(Company_Id,sql,null);
    }

    public  List<Map<String,Object>> getPanelId(String Company_Id){
        String sql="select ParentMenu,MenuName,TemplateFrom from Menu";
        return sqlHelper.executeQueryList(Company_Id,sql,null);
    }

    /**
     * 根据角色查询菜单列表
     * @param Company_Id
     * @param RoleId
     * @return
     */
    public List<Map<String, Object>> getMenuList(String Company_Id, String RoleId) {
        //select * from menu m INNER JOIN RoleMenu r on m.id=r.MenuID where publish=1
        String sql = "select * from menu m INNER JOIN RoleMenu r on m.id=r.MenuID where publish=1";
        if (RoleId != null && !"".equals(RoleId)) {
            sql += " and RoleId=" + RoleId;
        }

        System.out.println(sql);
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
         datas = sqlHelper.executeQueryList(Company_Id,sql,null);
        System.out.println(datas);
        return datas;
    }

    /**
     * 根据用户id查询部门Id
    * @param Company_Id
     * @param UserId
     * @return
     */
    public Integer getDeptIdByUserId(String Company_Id,int UserId){
        String sql="select deptId from [User] where Id=?";
        String[] parameters={String.valueOf(UserId)};
        Long result=(Long) sqlHelper.ExecScalar(Company_Id,sql,parameters);
        Integer results=null;
        if (result!=null){
            results=result.intValue();
        }
        //sqlHelper.ExecScalar(Company_Id,sql,parameters)!=null?(Integer) sqlHelper.ExecScalar(Company_Id,sql,parameters):null
        return results;
    }

    /**
     * 添加数据
     * @param Company_Id
     * @param TableName
     * @param map
     * @return
     */
    public int addDate(String Company_Id,String TableName,Map<String,Object> map){
        int result=0;
        StringBuilder sb = new StringBuilder("");
        if (TableName!=null && map!=null){
            sb.append("INSERT INTO "+TableName+"(state");
            String parentheses="";
            String content="";
            for (Map.Entry<String,Object> entry : map.entrySet()) {
                //Map.entry<Integer,String> 映射项（键-值对）  有几个方法：用上面的名字entry
                //entry.getKey() ;entry.getValue(); entry.setValue();
                //map.entrySet()  返回此映射中包含的映射关系的 Set视图。
                parentheses+=","+entry.getKey();
                content+=",'"+entry.getValue()+"'";
                System.out.println(parentheses);
                System.out.println(content);
                System.out.println("key= " + entry.getKey() + " and value= "
                        + entry.getValue());
            }
            sb.append(parentheses+")");
            sb.append(" VALUES ('0'"+content+")");

            System.out.println(sb.toString());
        }
        result=sqlHelper.ExecuteInsertGetId(Company_Id,sb.toString(),null).intValue();
        return result;
    }

    /**
     * 修改数据
     * @param Company_Id
     * @param TableName
     * @param map
     * @param paramId
     * @return
     */
    public int editDate(String Company_Id,String TableName,Map<String,Object> map,String paramId){
        int result=0;
        StringBuilder sb = new StringBuilder("");

        if (TableName!=null && map!=null){
            String parentheses="";

            sb.append("update "+TableName+" set ");
            for (Map.Entry<String,Object> entry : map.entrySet()) {
                parentheses+= entry.getKey()+" = "+"'"+entry.getValue()+"' , ";
                System.out.println(parentheses);
            }
            String param=parentheses.substring(0,parentheses.length()-1);
            sb.append(param);
            sb.append(" where id = "+paramId);
            System.out.println(sb.toString());
        }
        result=sqlHelper.ExecuteInsertGetId(Company_Id,sb.toString(),null).intValue();
        return result;
    }

    /**
     * 根据 ID 删除数据
     * @param Company_Id
     * @param id
     * @return
     */
    public int delDate(String Company_Id,String TableName,String id){
        Integer result=0;
        if (TableName!=null || id!=null)
        {
            String sql="delete from "+TableName+ " where Id = ?";
            String[] parameters = {id};
            result = sqlHelper.ExecuteNonQuery(Company_Id,sql,parameters);
        }
        return result;
    }









}

