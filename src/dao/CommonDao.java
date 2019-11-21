package dao;

import common.SQLHelper;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommonDao extends BaseDao {

    public CommonDao(SQLHelper pSQLHelper) {
        super(pSQLHelper);
    }


    public List<Map<String,Object>> getxyByDatas(String Company_Id,String sql){
        return sqlHelper.executeQueryList(Company_Id, sql, null);
    }

    public List<Map<String,Object>> getxyByData(String Company_Id,String tableName,String xColumn,String yColumn){

        String sql= "";
        if (yColumn.contains("counts")){
            sql = "select COUNT(1)" +" from " + tableName +" GROUP BY "+xColumn;
        }else{
            sql = "select top 10 SUM(convert(DECIMAL(8,1),"+yColumn+"))"+" from "+tableName+" GROUP BY "+xColumn;
        }
        System.out.println(sql);

        return sqlHelper.executeQueryList(Company_Id, sql, null);
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
     *  点击更改当前数据按钮状态
     * @param Company_Id
     * @param listId
     * @param afterState
     * @param tableName
     * @return
     */
    public int updateBtnList(String Company_Id,int listId,int afterState,String tableName ){
        String sql="update "+tableName+" set State=? where Id="+listId;
        String[] parameters={String.valueOf(afterState)};
        return sqlHelper.ExecuteNonQuery(Company_Id,sql,parameters);
    }

    /**
     * 根据 ID 批量删除数据
     * @param Company_Id
     * @param id
     * @return
     */
    public int batchList(String Company_Id,int afterState,String TableName,String id) {
        Integer result = 0;
        if (TableName != null || id != null) {
            String sql = "update " + TableName + "set State=? where Id in (?)";//(”+id+")"
            String[] parameters = {String.valueOf(afterState),id};
            result = sqlHelper.ExecuteNonQuery(Company_Id, sql, parameters);
        }
        return result;
    }

    /**
     * 根据 ID 删除数据
     * @param Company_Id
     * @param id
     * @return
     */
    public int delDate(String Company_Id,String TableName,String id) {
        Integer result = 0;
        if (TableName != null || id != null) {
            String sql = "delete from " + TableName + " where Id = ?";
            String[] parameters = {id};
            result = sqlHelper.ExecuteNonQuery(Company_Id, sql, parameters);
        }
        return result;
    }

    /**
     * 根据 ID 批量删除数据
     * @param Company_Id
     * @param id
     * @return
     */
    public int delBatch(String Company_Id,String TableName,String id) {
        Integer result = 0;
        if (TableName != null || id != null) {
            String sql = "delete from " + TableName + " where Id in ("+id+")";//(”+id+")"
            String[] parameters = {id};
            result = sqlHelper.ExecuteNonQuery(Company_Id, sql, null);
        }
        return result;
    }

    /**
     * 添加数据
     * @param Company_Id
     * @param TableName
     * @param map
     * @return
     */
    public int addDate(String Company_Id,String TableName,Map<String,Object> map,String mdWord){
        int result=0;
        StringBuilder sb = new StringBuilder("");
        if (TableName!=null && map!=null){
          //  sb.append("INSERT INTO "+TableName+"(state");
            sb.append("INSERT INTO "+TableName+"(");
            String parentheses="";
            String content="";
            int states = 0; //默认是没有state
            for (Map.Entry<String,Object> entry : map.entrySet()) {
                parentheses+=","+entry.getKey();
                content+=",'"+entry.getValue()+"'";
                System.out.println(parentheses);
                System.out.println(content);
                System.out.println("key= " + entry.getKey() + " and value= "
                        + entry.getValue());
                if (entry.getKey().equals("state") || entry.getKey()=="state"){
                    states = 1 ;
                }
            }
            if (states == 0 ){

                if (mdWord!=null){
                    String name = StringUtils.substringBefore(mdWord,"&");
                    String value = StringUtils.substringAfter(mdWord,"&");
                    sb.append("state"+","+name+parentheses+")");
                    sb.append(" VALUES ('0'"+","+value+content+")");
                }else {
                    sb.append("state"+parentheses+")");
                    sb.append(" VALUES ('0'"+content+")");
                }
            }
            else
            {
                String parenthese=parentheses.substring(1,parentheses.length());
                String contents=content.substring(1,content.length());
                sb.append(parenthese+")");
                sb.append(" VALUES ("+contents+")");
            }
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
                parentheses+= entry.getKey()+" = "+"'"+entry.getValue()+"' ,";
                System.out.println(parentheses);
            }
            String param=parentheses.substring(0,parentheses.length()-1);

            sb.append(param);
            sb.append(" where id = "+paramId);
            System.out.println(sb.toString());
        }
        result=sqlHelper.ExecuteNonQuery(Company_Id,sb.toString(),null);
        return result;
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
     * 查询后台操作列表
     * @param Company_Id
     * @param FormStateId
     * @return
     */
    public List<Map<String, Object>> getFormStateOperationByStateId(String Company_Id, String FormStateId){
        String sql="select OperationName from FormStateOperation where FormStateId=?";
        String[] parameters={FormStateId};

        return sqlHelper.executeQueryList(Company_Id,sql,parameters);
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
     *  查询列表按钮
     * @param Company_Id
     * @param userId
     * @param FormName
     * @param FormState
     * @return
     */
    public  List<Map<String, Object>> getFormListOperationShow(String Company_Id,int userId,String FormName,int FormState){
        String sql="{call GetFormStateOperationUserIds(?,?,?)}";
        String[] parameters={String.valueOf(userId),FormName,String.valueOf(FormState)};
        return sqlHelper.executeQueryList(Company_Id,sql,parameters)!=null?sqlHelper.executeQueryList(Company_Id,sql,parameters):null;
    }

    }

