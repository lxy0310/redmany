package common;

/**
 * Created by cxy on 2017/8/3.
 */
public interface APPConfig {
    //是否测试
    boolean DEBUG = false;
    //域名
    String HOST = "oa.redmany.com";
    //项目名

    String COMPANYID = "redmany2020";//waihui  CloudMall huasier  antmall mallTakeouts

    String COMPANYID = "aobaitreat";//waihui  CloudMall huasier  antmall mallTakeouts


    int VERSION=11;

    //默认页
   // String HOME_FORM = "Service_mainPage";
    //默认页
   // String HOME_TYPE = "copForm";

    //百度定位
    String BAIDU_KEY = "8EKE5OcQz2LlyAQtSPzrduxeP6f6Cg37";

    //String hosts = "193.112.244.24";
    String hosts = "193.112.244.24";
    //数据库
//    String URL_BZ5155 = "jdbc:sqlserver://iZjr5omeqdh7qsZ;DatabaseName=redmany_[CompanyId];useunicode=true;characterEncoding=UTF-8";
    String URL_BZ5155 = "jdbc:sqlserver://"+hosts+";DatabaseName=redmany_[CompanyId];useunicode=true;characterEncoding=UTF-8";
    String USERNAME = "redmany2908";
    String PASSWORD = "g_z38248269_"; //Bainaohuia702   g_z38248269_

    //测试数据库
    String URL_LOCAL = "jdbc:sqlserver://193.112.244.24;DatabaseName=redmany_[CompanyId];useunicode=true;characterEncoding=UTF-8";
    String USERNAME_DEBUG = "sa";
    String PASSWORD_DEBUG = "g_z38248269_";
//28516D
    //url参数
    String COP_FORM_NAME = "copformName";
    String SHOW_TYPE = "showType";
    //文件上传路劲
    String DOCUMENT="E:\\redmany\\redmany_ram0831\\redmany_ram\\document"; //http://oa.redmany.com:50003/document/  E:\备份\后台
}
