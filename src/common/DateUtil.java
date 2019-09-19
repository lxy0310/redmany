package common;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间（日期）工具类
 * @author  Arios
 */
public class DateUtil {
    /**
     * 时间格式转换器
     * @param date 要转换的时间，如“2019-09-18 12：00”
     * @param originalFormat date原来的格式，如“yyyy-MM-dd hh:mm”
     * @param targetFormat 转换后的格式，如“yyyy年MM月dd日 hh时mm分”
     * @return 返回转换后的时间字符串，如2019年
     * @throws ParseException
     */
    public  static  String convert(String date,String originalFormat,String targetFormat) throws ParseException {
        String target=null;
        //1.将date转换成Date对象
        SimpleDateFormat  dateGetter=new SimpleDateFormat(originalFormat) ;
        Date content=dateGetter.parse(date);
        //2.转换成相应的格式
        SimpleDateFormat   converter=new SimpleDateFormat( targetFormat);
        target=converter.format(content);
        return  target;
    }
}
