package common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 集合工具类
 * @author  Arios
 */
public class CollectionUtil {
    /**
     * 将List集合按照自身元素里面的某个属性（分组条件）合成为一个map集合。map的键（key）是分组条件，值（value）是一个List集合。
     * @param original 用于合成map的List集合
     * @param T List集合（original）的元素类型的实体类
     * @param fieldName 作为分组条件的属性名
     * @param N 分组条件的类型实体类
     * @param <T> List集合的元素类型
     * @param <N> 分组条件的类型
     * @return
     */
    public static  <T,N>Map<N,List<T>> getGroupMap(List<T> original,Class<T> T,String fieldName,Class<N> N){
           //1.获取类名
           String className=T.getSimpleName();
             Map<N,List<T>> target=new HashMap<>();

           if (original!=null){
               //2.循环original
               for (T t:
                    original) {

                   try {

                       //（1）.获取相应的getter方法

                       Method method=T.getMethod( "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1));

                       //(2) .调用方法
                       N key= (N) method.invoke(t);


                        //(3) .判断是否已经有这个key
                       List<T> value=null;
                       if(target.containsKey(key)){
                          target.get(key).add(t);

                       }else{
                           value=new ArrayList<>();
                           value.add(t);
                           target.put(key,value);
                       }

                   }catch (NoSuchMethodException e) {
                       e.printStackTrace();
                   }catch (IllegalAccessException e) {
                       e.printStackTrace();
                   } catch (InvocationTargetException e) {
                       e.printStackTrace();
                   }catch (Exception e){
                       e.printStackTrace();
                   }
               }

           }
           if(target.size()>0){
               return  target;
           }
           return  null;
    }

    public static String getGetter(String fieldName){
      return  null;
    }
}
