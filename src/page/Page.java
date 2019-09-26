package page;

import com.sangupta.htmlgen.Html;
import com.sangupta.htmlgen.HtmlBody;
import com.sangupta.htmlgen.HtmlHead;
import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.head.Meta;
import common.*;
import common.utils.CookieHelper;
import common.utils.SafeString;
import common.utils.TextUtils;
import dao.MainDao;
import model.Variable;
import servlet.BaseServlet;
import servlet.StudentServlet;
import showtype.ParentForm;
import viewtype.DefaultDataProvider;
import viewtype.IDataProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Page implements ParentForm.ISQLReplacer {
    private SQLHelper sSQLHelper;


    public static final String ORDER_ID = CommonUtils.ORDER_ID;
    //定位
    public static final String TAB_NAME = CommonUtils.TAB_NAME;
    public static final String LOC_CITY = CommonUtils.LOC_CITY;
    public static final String LOC_POS =CommonUtils.LOC_POS;
    public static final String LOC_ADDRESS_SHORT = CommonUtils.LOC_ADDRESS_SHORT;
    public static final String LOC_ADDRESS = CommonUtils.LOC_ADDRESS;

    public static final String COMPANYID = APPConfig.COMPANYID;


    private String isPc="0";  //是否是Pc端,0-手机 1-PC，默认为0
    public static String platform = "1";//前后端 0-前端，1-后端，默认为0
    private String theme=COMPANYID; //主题。默认为COMPANYID
    private int pageIndex=1; //当前页码
    private int pageSize=5;//每页条数
    private int pageCount=1;//总页数
    private  int dataCount=0;//总条数
    public static String getLoginPage() {
        return getHomeUrl("OaLoginHM", "LoginForm");
    }

    protected AccountHelper mAccountHelper;

    public HttpServletRequest getHttpServletRequest() {
        return mHttpServletRequest;
    }

    public interface Settings {
        String USERID = "userId";
        String ACCOUNT = "account";
        String USER_NAME = "user_name";
        String ROLEID = "roleId";
        String XMPP = "xmpp";
        String PWD = "pwd";
        String TARGETURL = "targetUrl";
        String IP = "ip";
        String DeptId = "deptId";
    }

    protected MainDao mainDao;
    private String Company_Id;
    private String copformName;
    private String showType;
    private HttpServletRequest mHttpServletRequest;
    private HttpServletResponse mHttpServletResponse;
    private final IDataProvider mDataProvider;
    private Map<String, String> urlStrings = new HashMap<>();
    private String mIP;

    public Page() {
        mDataProvider = createProvider();
    }

    public AccountHelper getAccountHelper() {
        return mAccountHelper;
    }

    protected IDataProvider createProvider() {
        return DefaultDataProvider.DEFALUT;
    }

    public void saveCookie(Cookie pCookie) {
        saveCookie(pCookie, false);
    }

    public void saveCookie(Cookie pCookie, boolean temp) {
        if (!temp) {
            pCookie.setMaxAge(30 * 24 * 60 * 60);
        }
        mHttpServletResponse.addCookie(pCookie);
    }

    /**
     * html元素属性提供
     */
    public final IDataProvider getDataProvider() {
        return mDataProvider;
    }

    /**
     * 网页标题
     */
    public String getTitle() {
        return mDefTitle;
    }


    public int getUserId() {
        return mAccountHelper.getUserId();
    }


    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
        if(dataCount%pageSize==0){
            setPageCount(dataCount/pageSize);
        }else{
            setPageCount(dataCount/pageSize+1);
        }
    }

    /**
     * 首页链接
     *
     * @param formName
     * @param showType
     * @return
     */
    public static String getHomeUrl(String formName, String showType) {
        return "queryStudentServlet?" + APPConfig.COP_FORM_NAME + "=" + formName + "&" + APPConfig.SHOW_TYPE + "=" + showType;
    }

    /**
     * 订单的链接
     *
     * @return
     */
    public static String getOrderTabUrl(String formName, String showType, String tab) {
        return getHomeUrl(formName, showType) + "&" + TAB_NAME + "=" + tab;
    }

    public String getIP() {
        return mIP;
    }

    public HttpServletResponse getHttpServletResponse() {
        return mHttpServletResponse;
    }

    public SQLHelper getSQLHelper() {
        return sSQLHelper;
    }

    private StudentServlet mPageServlet;

    private String mDefTitle = "";

    /**
     * 初始化
     *
     * @param copformName
     * @param showType
     */
    public void init(StudentServlet pServlet, String copformName, String showType, String title) {

        mPageServlet = pServlet;
        mDefTitle = title;
        HttpServletRequest req = pServlet.getRequest();
         HttpServletResponse res = pServlet.getResponse();
        this.sSQLHelper = new SQLHelper(req);
        mAccountHelper = new AccountHelper(req, res);
        mainDao = new MainDao(sSQLHelper);
        Company_Id = pServlet.getCompany_Id();
        this.copformName = copformName;
        this.showType = showType;
        this.mHttpServletRequest = req;
        this.mHttpServletResponse = res;
        this.mIP = BaseServlet.getRemortIP(req);
        this.pageIndex=req.getParameter("pageIndex")==null?this.pageIndex:Integer.parseInt(req.getParameter("pageIndex"));
        this.pageSize=req.getParameter("pageSize")==null?this.pageSize:Integer.parseInt(req.getParameter("pageSize"));
        urlStrings.clear();
        String url = mHttpServletRequest.getQueryString();
        if (url != null) {
            String[] ws = url.split("&");
            for (String w : ws) {
                if (w.contains("=")) {
                    String[] vs = w.split("=");
                    if (vs.length > 1) {
                        urlStrings.put(vs[0].trim(), vs[1]);
                    }
                }
            }
        }
        doTransferParams();
        System.err.println(getClass().getSimpleName() + ":" + mParams);
    }

    public String getmParams(String key) {
        return mParams.get(key);
    }

    /**
     * 根据内外键获取参数值
     * @param outSizeKey
     * @param innerKey
     * @return
     */
    public String getInnerParams(String outSizeKey,String innerKey){
            return DataUtil.getInnerParam(mParams,outSizeKey,innerKey);
    }
    /** 上个页面传过来的 transferParams参数的键值对 */
    private Map<String, String> mParams = new HashMap<>();
    private Map<String, String> gVariable = new HashMap<>();


    private String checkTransferParams() {
        String transferParams = getParameter("transferParams");
        return transferParams;
    }

    private void doTransferParams() {
        mParams.clear();


        String transferParams = checkTransferParams();

        if (transferParams != null) {
            String[] params = transferParams.split("\\[\\^\\]");
            for (String p : params) {
                try {
                    String val = p.contains(":") ? p.split(":")[1] : p;
                    String globalVar = p.split(":")[0];

                    if(p.contains(":")){
                        //把变量按:分成键值对
                        mParams.put(p.split(":")[0],p.split(":")[1]);
                        int i = val.indexOf("=");
                        if (i > 0) {
                           // mParams.put(val.substring(0, i).trim(), val.substring(i + 1).trim());
                            if (globalVar.equals("globalVariable")) {
                                gVariable.put("fromGlobal_" + val.substring(0, i).trim(), val.substring(i + 1).trim());
                            }
                        }
                    }
                    /*
                    //把formName按键值对存起来
                    if(p.contains(":")){
                        mParams.put(p.split(":")[0],p.split(":")[0]);
                    }


                    if(val.contains("and")){
                        String[] andVal = val.split("and");
                        for (String a : andVal) {
                            int idx = a.indexOf("=");
                            if (idx > 0) {
                                mParams.put(a.substring(0, idx).trim(), a.substring(idx + 1).trim());
                            }
                        }
                    } */
                   /* int i = val.indexOf("=");
                    if (i > 0) {
                        mParams.put(val.substring(0, i).trim(), val.substring(i + 1).trim());
                        if(globalVar.equals("globalVariable")){
                            gVariable.put("fromGlobal_"+val.substring(0, i).trim(), val.substring(i + 1).trim());
                        }
                    }*/
                } catch (Exception e) {
                    System.err.println("doTransferParams fail=" + p);
                }

            }
            Variable.mParam=mParams;
            Variable.globalVariable=gVariable;
        }
    }

    public HttpSession getHttpSession() {
        return mHttpServletRequest == null ? null : mHttpServletRequest.getSession();
    }


    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getIsPc() {
        return isPc;
    }

    public void setIsPc(String isPc) {
        this.isPc = isPc;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getShowType() {
        return showType;
    }

    public String getCompany_Id() {
        return Company_Id;
    }

    public String getParameter(String name) {
        return getParameter(name, null);
    }

    public String getParameter(String name, String def) {
        return getUrlParameter(name, def);
    }

    public String getUrlParameter(String name) {
        return SafeString.unescape(urlStrings.get(name));
    }

    public String getUrlParameter(String name, String def) {
        String val = SafeString.unescape(urlStrings.get(name));
        if (val == null) {
            return def;
        }
        return val;
    }

    public String getCopformName() {
        return copformName;
    }

    public String getParamValue(String key, String def) {
        String v = mParams.get(key);
        if (v == null) {
            return def;
        }
        return v;
    }

    public void setParamValue(String key, String def) {
        mParams.put(key, def);
    }

    public void clearParams() {
        mParams.clear();
    }

    public String getUrl() {
        return mHttpServletRequest.getRequestURI() + "?" + mHttpServletRequest.getQueryString();
    }

    /**
     * 从url获取参数
     *
     * @param sql
     * @return
     */
    protected String applyTransferParams(ParentForm baseForm,String sql) {


       if(mParams.containsKey(baseForm.getFormName().toString()));{
            String val =mParams.get(baseForm.getFormName().toString());

            if (needParam(val)) {
                if (sql.toLowerCase(Locale.US).contains("where")) {
                    sql += " and " + val;
                } else {
                    sql += " where " + val;
                }
            }
        }
        //获取url上面的TransferParams参数
       /* String transferParams = checkTransferParams();
        if (transferParams != null) {
            //transferParams分割，获取参数的字符串数组
            String[] params = transferParams.split("\\[\\^\\]");
            for (String p : params) {
                //1.将参数分割为paramsName和paramsVal
                String paramsName = p.contains(":") ? p.split(":")[0] : p;
              //  String paramsVal = p.contains(":") ? p.split(":")[1] : p;
                String paramsVal="";
                boolean   flag = p.contains(":");
                if(flag){
                   String[] strs= p.split(":");
                   if(strs.length>1){
                       paramsVal=strs[1];
                   }
                }else{
                    paramsVal=p;
                }

                //循环遍历Page初始化，生成的mParams
                for (Map.Entry<String, String> e : mParams.entrySet()) {
                     String val = e.getValue();
                    //   String val = e.getKey() + "=" + e.getValue();
                    if(val.equalsIgnoreCase("cc.state=?")){
                        val=null;
                    }
                    if(paramsName.equals(baseForm.getFormName().toString())&&paramsVal.equals(val)){
                        if (needParam(val)) {
                            if (sql.toLowerCase(Locale.US).contains("where")) {
                                sql += " and " + val;
                            } else {
                                sql += " where " + val;
                            }
                        }
                    }
//                    if(!e.getKey().equals(baseForm.getFormName().toString())){
//                        if (needParam(val)) {
//                            if (sql.toLowerCase(Locale.US).contains("where")) {
//                                sql += " and " + val;
//                            } else {
//                                sql += " where " + val;
//                            }
//                        }
//                    }
                }
            }
        }
          */
        return sql;
    }

    protected boolean needParam(String val) {
        if (val != null && val.contains(":$")) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "copformName='" + copformName + '\'' +
                ", showType='" + showType + '\'' +
                '}';
    }

    public Cookie getCookie(String name) {
        if (mHttpServletRequest == null || name == null) {
            return null;
        }
        return CookieHelper.getCookie(mHttpServletRequest, name);
    }

    public String getCookieValue(String name) {
        Cookie cookie = getCookie(name);
        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }

    static final boolean LOG = true;

    public final void writeHtml(HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        //html5
        out.println("<!DOCTYPE html>");
        Html html = new Html();
        HtmlHead head = html.head();
        HtmlBody body = html.body();
        if (LOG) {
            System.out.println(getCopformName() + " write head");
        }
        ParentForm menuForm = mPageServlet.getMenuForm(getCompany_Id(), getSQLHelper());
        System.out.println("-----------------------我----------------------------。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
        System.out.println("---------------menuForm-------------"+menuForm);
        System.out.println("---------------getCompany_Id()-------------"+getCompany_Id());
        System.out.println("---------------gegetSQLHelper()-------------"+getSQLHelper());
     //   System.out.println("---------------getCopformName()-------------"+getCopformName());
        System.out.println("---------------getShowType()-------------"+getShowType());
        boolean hasMenu = mPageServlet.hasMenu(menuForm, getCopformName(), getShowType());

        try {
            writeHead(head);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (LOG) {
            System.out.println(getCopformName() + " 页面主体 ***** write body");
        }
        try {
            writeBody(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (hasMenu) {
            menuForm.setPage(this);
            HtmlBodyElement element = getHtml(menuForm);


            if (element != null) {
                body.add(element);


            }
        }
        try {


            out.println(html.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeHead(HtmlHead head) {
        head.meta("viewport", "width=device-width");
      //  head.css("css/bootstrap.min.css");
        head.css("css/bootstrap-theme.min.css");
        head.meta(new Meta().httpEquiv("Content-Type").content("text/html; charset=utf-8"));
        head.meta(new Meta().httpEquiv("Access-Control-Allow-Origin").content("*"));
        head.title(getTitle());
//        head.script().text("var gCompany_Id='"+Company_Id+"';var gUesrId=" + getUserId()+";var gplatform=" +";var gValues=new Array();\n" +
//                "gValues['isNeedLogin']=" + (mAccountHelper.isLogin() ? 0 : 1) + ";\n");//isNeedLogin
        head.script().text("var gCompany_Id='"+Company_Id+"';var gUesrId=1" +";var gplatform=1"+";var gValues=new Array();\n" +
                "gValues['isNeedLogin']=" + (mAccountHelper.isLogin() ? 0 : 1) + ";\n");//isNeedLogin
        head.script("js/page.js?v=" + APPConfig.VERSION);
        head.script("js/jquery.min.js");
        head.script("js/ShoppingCarPage.js");
    //    head.script("js/cardInfo.js");

        String name = getCopformName();
        head.css("css/" + name + ".css");
        head.css("css/common.css");
        head.css("css/swiper.min.css");
        head.css("css/bootstrap.css");
        head.script("js/bootstrap.js");
        head.script("js/swiper.min.js");
       /* head.script("js/"+name+".js");*/
        head.script("js/jquery-1.8.1.js");

        head.css("layui/css/layui.css");
        head.script("layui/layui.js");

        head.script("js/commonUtils.js");
        /*树形图*/
        head.css("css/demo.css");
        head.css("css/zTreeStyle.css");
/*        head.script("js/jquery.ztree.core.js");
        head.script("js/jquery.ztree.excheck.js");
        head.script("js/jquery.ztree.exedit.js");*/
        /* 手机端弹出层插件*/
      /*  head.css("css/bounced/dialog.css");
        head.script("../js/bounced/dialog.js");
        head.script("../js/bounced/zepto.min.js");*/


    }

    public void writeBody(HtmlBodyElement<?> body) {

    }

    /**
     * 处理form的sql
     *
     * @param sql
     * @return
     */
    public String replaceSqlParams(ParentForm baseForm, String sql) {
        if (LOG) {
            System.err.println(sql);
        }
        //循环transferParams生成的键值对mParams
        for (Map.Entry<String, String> e : mParams.entrySet()) {
            String paramsName=e.getKey();
            String formVal = e.getValue();
            //判断
            if(baseForm.getFormName().toString().equals(paramsName)){
                sql = applyTransferParams(baseForm,sql);
            }
         /*   if(baseForm.getFormName().toString().equals(formVal)){
                    sql = applyTransferParams(baseForm,sql);
            }*/
        }
        String loc = getCookieValue(Page.LOC_POS);
        String lat = "0";
        String lng = "0";
        if (!TextUtils.isEmpty(loc)) {
            String[] vs = loc.split(",");
            if (vs.length > 1) {
                lat = vs[0];
                lng = vs[1];
            }
        }
        sql = sql.replace("{interactiveLatitude}", lat);
        sql = sql.replace("{interactiveLongitude}", lng);
        return sql.replace("[userid]", "" + getUserId());
    }

    protected HtmlBodyElement getHtml(ParentForm form) {
        if (form == null) return null;
        if (form.getPage() == null) {

            form.setPage(this);
        }
        form.loadData(getSQLHelper(), this);
        return form.createView();
    }

    protected boolean checkData(ParentForm baseForm, Map<String, Object> line) {
        //retrun false;则不显示
        return true;
    }

    public void filterData(ParentForm baseForm, List<Map<String, Object>> datas) {
        //根据Id过滤数据
        if (datas == null) return;
        int count = datas.size();
        for (int i = count - 1; i >= 0; i--) {
            Map<String, Object> line = datas.get(i);
            if (!checkData(baseForm, line)) {
                //不符合条件，移除
                datas.remove(i);
            }
        }
    }
}
