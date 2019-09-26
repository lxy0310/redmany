package common;

/**
 * sql工具类，用于拼接sql
 */
public class SQLUtil {
    /**
     * 获取分页sql
     * @param sql 要处理的sql
     * @param pageSize 页面大小
     * @param pageIndex 当前页码
     * @param replaceName 替换名
     * @return 返回处理后的sql
     */
    public  static String getPagingSQL(String sql,Integer pageSize,Integer pageIndex,String replaceName){
              String targetSql="select top "+ pageSize+" * from ( {innerSql}) temp_row where rownumber>(("+pageIndex+"-1)* "+pageSize+")";
              int selectIndex=sql.toLowerCase().indexOf("select");
              String replace=replaceName==null ?"":(replaceName+".");
              String replaresql="select top 1000 row_number() over(order by "+replace+"Id asc) as rownumber," +sql.substring(selectIndex+6);
               targetSql=targetSql.replace(" {innerSql}",replaresql);
               return   targetSql;
      }

    /**
     * 获取sql的总条数
     * @param sql
     * @return
     */
    public  static String getCountSql(String sql){
          int fromIndex=sql.toLowerCase().indexOf("from");
          int orderByIndex=sql.toLowerCase().lastIndexOf("order by");
        String targetSql=null;
          if(orderByIndex>0){
            targetSql="select count(1) from " +sql.substring(fromIndex+4,orderByIndex);
          }else{
              targetSql="select count(1) from " +sql.substring(fromIndex+4);
          }


          return targetSql;
      }
}
