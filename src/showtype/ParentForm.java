package showtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import commandCenter.CommandCenter;
import common.SQLHelper;
import common.utils.DataHelper;
import dao.FormDao;
import dao.FormFiledDao;
import dao.OaAttributeDao;
import model.Form;
import model.OaAttribute;
import org.apache.commons.lang.StringUtils;
import page.Page;
import service.FormFileAttribute;
import service.impl.FormFileAttributeImpl;
import viewtype.View;
import viewtype.DefaultDataProvider;
import viewtype.IDataProvider;
import viewtype.NoData;
import viewtype.ParentView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ParentForm {
    private static final boolean LOG = false;

    public interface ISQLReplacer {
        String replaceSqlParams(ParentForm pBaseForm, String sql);
    }

    protected String companyId;
    protected String viewType;
    protected String formName;
    protected String Title;


    /**
     * form信息
     */
    protected Form mFormData;
    protected Form mfFormData;

   /* protected List<Map<String,Object>> formList;
    protected List<Map<String,Object>> formFeildList;*/

    /**
     * 控件信息
     */
    protected List<View> mViews;
    protected View view;
    private List<String> mViewNames;
    /**
     * Get_data_sql
     */
    protected List<Map<String, Object>> mDatas;
    protected OaAttribute sAttrs;


    private static FormFiledDao sFormFiledDao;
    private static OaAttributeDao oaAttributeDao;
    private static FormDao sFormDao;
    private  static FormFileAttribute formFileAttribute;
    protected SQLHelper sqlHelper;

    public ParentForm() {

    }

    public ParentForm(ParentForm pParentForm) {
        init(pParentForm);
    }

    public ParentForm init(ParentForm pParentForm) {
        initDao(pParentForm.sqlHelper);
        this.companyId = pParentForm.companyId;
        this.viewType = pParentForm.viewType;
        this.formName = pParentForm.formName;
        this.Title = pParentForm.Title;
        this.mFormData = pParentForm.mFormData;
        this.mViews = pParentForm.mViews;
        this.mDatas = pParentForm.mDatas;
        this.mPage = pParentForm.mPage;
        this.mViewNames = pParentForm.mViewNames;
        this.mfFormData = pParentForm.mfFormData;
        return this;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getViewType() {
        return viewType;
    }

    public String getFormName() {
        return formName;
    }

    public Form getFormData() {
        return mFormData;
    }
//    public FormFiled getFormFiledData() {
//        return mViews.getTransferParams();
//    }


    public List<String> getFormFieldNames() {
        if (mViewNames == null) {
            System.out.println(mFormData.getList_fields());
            mViewNames = DataHelper.toList(mFormData.getList_fields());
        }
        return mViewNames;
    }

    public List<String> getFormFieldNames(String mFormDate) {
        if (mViewNames == null) {
            mViewNames = DataHelper.toList(mFormDate);
        }
        return mViewNames;
    }




    public List<View> getViews() {
        List<String> names = getFormFieldNames();
        if (names != null && names.size() > 0) {
            List<View> views = new ArrayList<>();
            //List_fields不为空，则按照这个字段的顺序去添加
            int count = names.size();
            for (int i = 0; i < count; i++) {
                String name = names.get(i);
                View view = getViewByName(name);
                if (view != null) {
                    views.add(view);
                }
            }
            return views;
        } else {
            return mViews;
        }
    }

    public List<View> getViewLists(String ListFeilds) {
        // mFormData=sFormDao.getForm(companyId, formNames);
        List<String> names = DataHelper.toList(ListFeilds);
        if (names != null && names.size() > 0) {
            List<View> views = new ArrayList<>();
            //List_fields不为空，则按照这个字段的顺序去添加
            int count = names.size();
            for (int i = 0; i < count; i++) {
                String name = names.get(i);
                View view = getViewByName(name);
                if (view != null) {
                    views.add(view);
                }
            }
            return views;
        } else {
            return mViews;
        }
    }


    public List<Map<String, Object>> getDatas() {
        return mDatas;
    }



    public void init(String pCompanyId, String pViewType, String pFormName) {
        companyId = pCompanyId;
        viewType = pViewType;
        formName = pFormName;

    }

    protected void initDao(SQLHelper pSQLHelper) {
        this.sqlHelper = pSQLHelper;
        if (sFormFiledDao == null) {
            synchronized (FormFiledDao.class) {
                if (sFormFiledDao == null) {
                    sFormFiledDao = new FormFiledDao(pSQLHelper);
                }
            }
        }
        if (sFormDao == null) {
            synchronized (FormDao.class) {
                if (sFormDao == null) {
                    sFormDao = new FormDao(pSQLHelper);
                }
            }
        }
        if(oaAttributeDao == null){
            synchronized (OaAttributeDao.class) {
                if (oaAttributeDao == null) {
                    oaAttributeDao = new OaAttributeDao(pSQLHelper);
                }
            }
        }
        if(formFileAttribute==null){
            synchronized(FormFileAttributeImpl.class) {
                formFileAttribute = new FormFileAttributeImpl(pSQLHelper);

            }
        }
    }

    public void loadData(SQLHelper pSQLHelper, ISQLReplacer pISQLReplacer) {
        initDao(pSQLHelper);
        String companyId = getCompanyId();
        String formName = getFormName();
        //获取Form表的实体对象
        mFormData = sFormDao.getForm(companyId, formName);


//        System.out.println(getFormName() + " form:" + mFormData);
        //获取FormFiled表的数据集mViews
        if (getFormName().contains(",")){   //双列表
            String fFormCloumn = formName.split(",")[0];
            mViews = sFormFiledDao.getFormContorl(companyId, fFormCloumn, null);
            mFormData = sFormDao.getForm(companyId, fFormCloumn);
        }else{
            mViews = sFormFiledDao.getFormContorl(companyId, getFormName(), null);
        }
        if (mViews != null) {
            //循环mViews获取相应的样式配置
            for (View v : mViews) {
//                System.out.println("mViews-v-getTransferParams====>"+v.getTransferParams());
//                String str = v.getAndroidAttribute();
//                v.setAttributeStr(str);
                //获得wapAttribute值
                String wapAttribute = null;
//                String windowsAttribute = v.getWindowsAttribute();

                //如果有attributeId，则查询出这个OAAttribute

          /*      if (v.getAttributeId()!=null){
                    String wapAttributes =oaAttributeDao.getAttributeById(getCompanyId(),Integer.valueOf(v.getAttributeId().toString()));
                    if (wapAttributes!=null){
                        wapAttribute=wapAttributes;
                        System.out.println(wapAttribute);
                    }else {
                        wapAttribute=v.getWapAttribute();
                    }

                }else{
                    wapAttribute=v.getWapAttribute();
                }*/
                wapAttribute=formFileAttribute.getAttributeStr(v,getCompanyId(),mPage.getShowType(),mPage.getIsPc(),mPage.getTheme());
                v.setAttributeStr(wapAttribute);
                v.setWapAttribute(wapAttribute);
            }
            if (LOG) {
                System.out.println(getClass().getSimpleName() + ":Views:" + mViews);
            }
        }
        if (LOG) {
            System.out.println(getFormName() + "/" + getViewType() + ",initData=" + mViews);
        }
        //Form的实体对象不为空，则获取相应的数据
        if (mFormData != null) {
            String Get_data_sql = DataHelper.toString(mFormData.getGet_data_sql());
            if (Get_data_sql != null && Get_data_sql.length() > 0) {
                String sql;
                if (pISQLReplacer == null) {
                    sql = Get_data_sql;
                } else {
                    sql = pISQLReplacer.replaceSqlParams(this, Get_data_sql);
                }
//                if(sql!=null){
//                    sql = sql.replace("$suburl=type", "type=1");
//                    sql = sql.replace("{interactiveLatitude}", getPage().getParameter("interactiveLatitude", "0"));
//                    sql = sql.replace("{interactiveLongitude}", getPage().getParameter("interactiveLongitude","0"));
//                    if(sql.contains("from maintain_menu_navigation_b m")){
//                        sql = sql.replace("i.Id","Id").replace("ipId","Id").replace("and type=1", "");
//                    }else if(sql.contains("[dbo].[checkStore1]")){
//                        sql = sql.replace("ipId","i.Id").replace("and type=1", "");
//                    }else if(sql.contains("from itemProvide_b i join serviceItem_b si on i.serviceItemId")){
//                        sql = sql.replace("ipId","i.Id").replace("and type=1", "");
//                    }else if(sql.contains("[dbo].[select_my_car_maintain]")){
//                        sql = sql.replace("i.Id","Id").replace("ipId","Id").replace("and type=1", "");
//                    }else if(sql.contains("from itemProvideProducts_b i join product_sku ps")){
//                        sql = sql.replace("and type=1", "");
//                    }
//                }
                loadData(sql);
                if (LOG) {
                    System.out.println("" + getClass().getSimpleName() + ":sql=" + sql + ":数据=" + mDatas);
                }
            }
        }
        if ("jkdView".equalsIgnoreCase(getFormName())) {
            System.out.println(mFormData + ":Views:" + mViews);
        }
        initData();
//        System.out.println("aa");
    }

    protected void initData() {

    }

    protected void loadData(String sql) {
        try {
            mDatas = sFormFiledDao.getFiledDatas(getCompanyId(), sql);
        } catch (Throwable e) {
            System.err.println("loadData:" + sql);
            e.printStackTrace();
        }
        getPage().filterData(this, mDatas);

    }
    public abstract HtmlBodyElement<?> createViews();
    public  HtmlBodyElement createView() {
        HtmlBodyElement<?> element = createViews();

        if(element!=null){
            if(sAttrs==null){
                /* sAttrs = oaAttributeDao.getOaAttribute(companyId,formName);*/
                if(sAttrs!=null){
                    String wapAttrs = sAttrs.getWapAttribute();
                    Map<String,String> map =parseAttribute(wapAttrs);
                    if(wapAttrs!=null){

                    }
                }
//            if (onclick!=null){element.styles(map.get("style"));
            }
//                element.onClick(onclick);
//            }

        }
        return element;
    }



    /**
     * 解析属性
     */
    public Map<String, String> parseAttribute(String attribute) {
        //attribute='position:265/400,25/1500[^]content:最快30分钟上门[^]textColor:#888888[^]textSize:13'
        Map<String, String> attrs = new HashMap<>();
        if (attribute != null && attribute.length() > 0) {
            String k = null;
            String v = null;
            String[] ws = attribute.split("\\[\\^\\]");
            for (String w : ws) {
                int index = w.indexOf(":");
//                String[] vs = w.split(":");

//                if (vs.length > 1) {
//                    attrs.put(vs[0], vs[1]);
//
//                }
                if(index>0){
                    k=w.substring(0,index);
                    v=w.substring(index+1);
                    attrs.put(k,v);
                }
            }
            if (attrs.size() == 0) {
                System.err.println("parseAttribute failed? size = 0, attribute= " + attribute);
            }
        }
        return attrs;
    }

    /**
     *
     * @param paramId
     * @param sql
     * @return
     */
    public String sqlGetID(String paramId,String sql){
        if (paramId!=null){
            if (sql.toLowerCase().contains("where")){
                //截取
                String before = StringUtils.substringBefore(sql, "where");
                String after = StringUtils.substringAfter(sql, "where");
                sql = before + " where Id="+paramId  +" and "+after;
            }else {
                sql=sql+" where Id="+paramId;
            }
        }
        return sql;
    }


    public String get(Map<String, Object> data, String key) {
        Object var = data.get(key);
        if (var == null) {
            return null;
        }
        return String.valueOf(var);
    }

    public String getValue(String key) {
        if (mDatas == null || mDatas.size() == 0) {
            return null;
        }
        return get(mDatas.get(0), key);
    }

    public View getViewByName(String name) {
        if (mViews == null) return null;
        for (View view : mViews) {
            if (name.equalsIgnoreCase(view.getName())) {
                return view;
            }
        }
        return null;
    }
    public View getViewByNames(String name) {
        if (mViews == null) return null;
        for (View view : mViews) {
            if (name.equalsIgnoreCase(view.getName())) {
                return view;
            }
        }
        return null;
    }

    /**
     * 根据type反射
     */
    public ParentView makeType(View view) {
        ParentView parentView = CommandCenter.makeFormField(this, view, mPage.getDataProvider());

        System.out.println("parentView"+parentView);
        if (parentView == null) {
            parentView = new NoData();
        }
        String Id = companyId;
        String formname = formName;
        return parentView;
    }

    public ParentView makeTypes(View view) {
        ParentView parentView = CommandCenter.makeFormField(this, view, mPage.getDataProvider());
        if (parentView == null) {
            parentView = new NoData();
        }
        String Id = companyId;
        String formname = formName;
        String title=Title;
        return parentView;
    }
    //Page

    private Page mPage;

    public Page getPage() {
        return mPage;
    }

    public void setPage(Page pPage) {
        mPage = pPage;
    }

    public IDataProvider getDataProvider() {
        if (getPage() == null) {
            return DefaultDataProvider.DEFALUT;
        }
        return getPage().getDataProvider();
    }

    public String toHtml(ParentView view) {
        if (view != null) {
            //formField和名字和内容给模板
            return view.createView();
        } else {
            System.err.println("no craete view " + view.getName() + " by " + view.getType());
        }
        return "";
    }

    @Override
    public String toString() {
        return "ParentForm{" +
                "companyId='" + companyId + '\'' +
                ", viewType='" + viewType + '\'' +
                ", formName='" + formName + '\'' +
                ", mFormData=" + mFormData +
                ", mViews=" + mViews +
                ", mDatas=" + mDatas +
                ", mPage=" + mPage +
                '}';
    }
}
