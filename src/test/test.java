package test;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class test {

    public static void main(String[] args) {
        String str = "SELECT * FROM FormFiled ORDER BY CONVERT(INT, IsNull(substring(filedGroup,charindex('[^]',filedGroup)+3,len(filedGroup)-charindex('[^]',filedGroup)),'100')), Index_number\n";
        if(str.toLowerCase().contains("order by")){
            str = str.toLowerCase();
            String after = StringUtils.substringAfter(str,"order by");

            System.out.println(after);
            String a = str.substring(str.indexOf("order by"),str.length());
            System.out.println(a);

            String before = StringUtils.substringBefore(str, "order by");
            System.out.println(before);
        }

    }

}
