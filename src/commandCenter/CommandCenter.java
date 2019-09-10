package commandCenter;

import page.*;
import servlet.StudentServlet;
import showtype.ParentForm;
import viewtype.IDataProvider;
import viewtype.ParentView;
import viewtype.View;

import java.util.Locale;

/**
 * Created by hy on 2017/10/17.
 */
public class CommandCenter {
    private static final String PAGE_PACKAGE = "page.";
    private static final String SHOWTYPE_PACKAGE = "showtype.";
    private static final String VIEW_TYPE_PACKAGE = "viewtype.";

    private static ParentForm makeForm(String showType) {
        ParentForm parentForm = null;
        Class<?> clazz = null;
        String fshowType = showType.substring(0, 1).toUpperCase() + showType.substring(1);
        String className = SHOWTYPE_PACKAGE + fshowType;
        try {
            clazz = Class.forName(className);
            if (clazz == null) {
                System.err.println("no class " + clazz);
                return null;
            }
            parentForm = (ParentForm) clazz.newInstance();

        } catch (Exception e) {
            //
        }
        return parentForm;
    }


    /**
     * 根据type反射
     */
    public static ParentView makeFormField(ParentForm form, View view, IDataProvider dataProvider) {

        String CLa;
        ParentView parentView = null;
        Class<? extends ParentView> clazz = null;
        String type = view.getType().trim();
        String viewTypeName = type.substring(0, 1).toUpperCase() + type.substring(1);
        CLa = VIEW_TYPE_PACKAGE + viewTypeName;
        try {
            if(null!=CLa){
                clazz = (Class<? extends ParentView>) Class.forName(CLa);
            }
            if (clazz == null) {
                return null;
            }
            parentView = (ParentView) clazz.newInstance();
            if(parentView != null){
                parentView.initView(form, view, dataProvider);//返回页面
            }else{
                System.out.println("parentView==null");
            }
        } catch(Exception e){
            System.err.println("makeFormField,type=" + type + ",view=" + view.getName() + ",form=" + form.getFormName());
//                e.printStackTrace();
        }



        return parentView;
    }


    /**
     * 拿到数据库的字段根据字段数据进行反射，并返回页面代码
     *
     * @param showType  类
     * @param formName  form表
     * @param companyId
     * @return
     */
    public static ParentForm compositeTemplate(String companyId, String showType, String formName) {
        //showType='freeForm',formName='jkdView'
        ParentForm parentForm = makeForm(showType);

        if (parentForm == null) {
            System.err.println("compositeTemplate:no find :showType=" + showType + ",formName=" + formName);
            return null;
        }
        try {
            parentForm.init(companyId, showType, formName);
            //  System.out.println(companyId+"------------------------------++"+showType+formName);
        } catch (Throwable e) {
            System.err.println("init form showType=" + showType + ",formname=" + formName);
//            e.printStackTrace();
        }
        //内部代码拼接html
        return parentForm;
    }

    private static String warpFirst(String str) {
        return str.substring(0, 1).toUpperCase(Locale.US) + str.substring(1);
    }

    public static void writeHtml(StudentServlet pServlet, String copformName, String showType) {
        //默认标题
        String title = "";
        //特殊
        if ("ServiceComment".equalsIgnoreCase(copformName)) {
            showType = "ServiceCommentForm";
        } else if ("Service_personal".equalsIgnoreCase(copformName)) {
            showType = "ServicePersonalForm";
            title = "我的";
        } else if ("LoginPage".equalsIgnoreCase(copformName)) {
            showType = "OaLoginForm";
        } else if ("ServiceDetailsPage".equalsIgnoreCase(copformName)) {
            title = "服务搜索";
        } else if ("SelectAre".equalsIgnoreCase(copformName)) {
            title = "选择小区";
        } else if("Order_main|Order_details|Order_info_id".equalsIgnoreCase(copformName)){
            copformName = "Order_main";
        }
        Page page = null;
        Class<? extends Page> clazz = null;
        try {
            clazz = (Class<? extends Page>) Class.forName("page." + warpFirst(copformName));
            if (clazz != null) {
                page = clazz.newInstance();
            }
        } catch (Throwable e) {
        }
        if (page == null) {
            if ("copForm".equalsIgnoreCase(showType)) {
                //组合模板
                page = new CopPage();
            } else {
                //单模板
                page = new SinglePage();
            }
        }
        page.init(pServlet, warpFirst(copformName), showType, title);
        if (page.getUserId() == 0) {
            page.getAccountHelper().autoLogin();
        }
        System.out.println(pServlet.getRequest().getRequestURI() + "  " + page);
        try {
            page.writeHtml(pServlet.getResponse());
            System.out.println(pServlet.getResponse());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
