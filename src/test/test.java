package test;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class test {

    public static void main(String[] args) {

        String sql = "select * from [User] u ";
        String re = "u";
       // String http://localhost:8080/redmany/queryStudentServlet?copformName=user1&showType=listForm&conditon=UserName=yong
        //UserName:yong,RealName:测
        String str = "UserName:yong,RealName:测";
        String[] str1 = str.split(",");
        String con = "";
        for (int i = 0; i <str1.length ; i++) {
            String str2 = str1[i];
            String name = StringUtils.substringBefore(str2,":");
            String value = StringUtils.substringAfter(str2,":");
            con += name +" like '%"+value+"%' and ";
        }
        con = con.substring(0,con.length()-4);
        sql = sql + "where " +con;
        System.out.println(sql);
      //  String after = StringUtils.substringAfter()

       /* String str = "<div><label></label><div>";
        int index=str.indexOf("label");
        String a = str.substring(str.indexOf("label"),str.length());
        //str.insert(index,"131");
       str =  str.replace("<label>","<label class=\"labelRight\">");

        System.out.println(str);*/

        /*String str = "SELECT * FROM FormFiled ORDER BY CONVERT(INT, IsNull(substring(filedGroup,charindex('[^]',filedGroup)+3,len(filedGroup)-charindex('[^]',filedGroup)),'100')), Index_number\n";
        if(str.toLowerCase().contains("order by")){
            str = str.toLowerCase();
            String after = StringUtils.substringAfter(str,"order by");

            System.out.println(after);
            String a = str.substring(str.indexOf("order by"),str.length());
            System.out.println(a);

            String before = StringUtils.substringBefore(str, "order by");
            System.out.println(before);
        }*/

    }

}
