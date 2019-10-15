package common;

public class CommonUtils {

    public static String ORDER_ID="orderId"; //订单
    //
    public static String TAB_NAME="tab";
    public static String LOC_CITY="loc_city";
    public static String LOC_POS="loc_pos";
    public static String LOC_ADDRESS_SHORT="loc_city_short";
    public static String LOC_ADDRESS="loc_address";

    public static String getHost = "http://oa.redmany.com";
    public static String domain1 ="50003";
    public static String domain2 ="50011";
    public static String getFileData=getHost+":"+domain1+"/document/"; //加载文件
    public static String GetData = getHost+":"+domain2; //上传文件
    public static String chagePwd = getHost+":"+domain2+"/UpdatePwd.aspx?";//修改密码
    //数据获取
    public static String GetUrl3 = getHost+":"+domain2+"/getFormData3.aspx?";
    public static String GetUrl2 = getHost+":"+domain2+"/getFormData2.aspx?";
    public static String GetUrl =getHost+":"+domain2+"/getFormData.aspx?";
    //提交数据
    public static String SumbitUrl = getHost+":"+domain2+"/submitData.aspx?";
    public static String userLogin = getHost+":"+domain2+"/userLogin.aspx?";//登陆
    //在线替换器
    public static String getDataRepalOnline = getHost+":"+domain2+"/getDataReplacerOnline.aspx?";

    public static String userRegister =getHost+":"+domain2+"/userRegister.aspx?";//注册接口

    public static String language = "";//当前语言

    //富文本编辑器实例化
    public static String editorStr ="UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;\n" +
            "    UE.Editor.prototype.getActionUrl = function(action) {\n" +
            "        if (action == 'uploadimage') {\n" +
            "            return '/redmany/uploadFile';\n" +
            "        } else {\n" +
            "            return this._bkGetActionUrl.call(this, action);\n" +
            "        }\n" +
            "    } ";

    //图片上传路径（富文本）
    public static String imgUploadPath = "D://AAA";//本地测试路径

}
