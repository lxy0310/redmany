package common;

import model.Form;
import viewtype.View;

import java.util.List;
import java.util.Map;




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



      public  static  String getNonQuerySql(Form form, List<View> viewList, Map<String,String> datas, String type,Map<String,String> condition){
          StringBuffer sql=new StringBuffer();
          String filed="";
          String values="";
          Map<String,String>  tempData=datas;
          if("insert".equalsIgnoreCase(type)){
           sql.append("insert into "+form.getTable_name());

           //拼接formfiled
              for (View view:
                   viewList) {
                  if (!"noData".equalsIgnoreCase(view.getType()) && tempData.containsKey(view.getName()) ){
                      //FormFiled 的ShowType!=noData 并且提交的参数中有这个值
                      filed+=view.getName()+", ";

                      values+= tempData.get(view.getName())!=null?"'"+tempData.get(view.getName())+"', ":tempData.get(view.getName())+", ";
                      tempData.remove(view.getName());
                  }
              }
              //添加tempData剩余的值
       /*       for ( String key:
                   tempData.keySet()) {
                  if(!"id".equalsIgnoreCase(key)){
                      filed+=key+", ";
                      values+= tempData.get(key)+", ";
                  }

              }*/
             //拼接form的put_data_sql_...
              if(form.getPut_data_sql_set_field()!=null && "".equalsIgnoreCase(form.getPut_data_sql_set_field().trim())){
                  filed+=form.getPut_data_sql_set_field();
                  values+=form.getPut_data_sql_set_value();
              }
              filed=filed.trim();
              values=values.trim();
              if( filed.lastIndexOf(",")!=-1 &&  filed.lastIndexOf(",")==filed.length()-1){
                  filed=filed.substring(0,filed.lastIndexOf(","));
              }
              if(values.lastIndexOf(",")!=-1 &&  values.lastIndexOf(",")==values.length()-1){
                  values=values.substring(0,values.lastIndexOf(","));
              }
              sql.append(" ("+filed+") ");
              sql.append(" values ("+values+"); ");
              sql.append("select @@identity;");
                  return  sql.toString();
          }else if("update".equalsIgnoreCase(type)){

              sql.append("update "+form.getTable_name() +" set ");
              String setfiled="";
              String where="";
              //拼接formfiled
              String[] Modify_fields=form.getModify_fields().split(",");

              for (String Modify_field:Modify_fields){
                  setfiled+= Modify_field+"="+(tempData.get(Modify_field)==null?"null":"'"+tempData.get(Modify_field)+"'")+",";
              }
              if( setfiled.lastIndexOf(",") !=-1 && setfiled.lastIndexOf(",")==setfiled.length()-1){
                  setfiled=setfiled.substring(0,setfiled.lastIndexOf(","));
              }

              sql.append(setfiled);
              int i=0;
              for (String key:
                   condition.keySet()) {
                     if(i==0){
                         where+=" where " +key+"="+condition.get(key);
                     }else{
                         where+=" and " +key+"="+condition.get(key);
                     }

                     i++;
              }
              sql.append(where);
              return  sql.toString();
          }


        return  null;
      }



    public static String getCondition(String sql, Map map){

        return  sql;
    }


}
