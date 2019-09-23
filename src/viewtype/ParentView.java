package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import common.CommonUtils;
import common.utils.DataHelper;
import model.Position;
import page.Page;
import showtype.ParentForm;
import showtype.ListForm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hy on 2017/10/24.
 */
public abstract class ParentView {

    public abstract String getType();

    public static String IMAGE_UPLOAD;
    public static String IMAGE_PRE = CommonUtils.getFileData;
   // public static String IMAGE_PRE = "http://"+a+"/document/";

    public Page getPage() {
        return getForm().getPage();
    }

    private ParentForm mForm;
    protected String mName;
    private int mIndex;
    private Map<String, String> mAttrs;
    private Map<String, Object> mDatas;
    private IDataProvider mIDataProvider;

    public IDataProvider getDataProvider() {
        return mIDataProvider;
    }


    public ParentForm getForm() {
        return mForm;
    }

    public int getmIndex() {
        return mIndex;
    }

    public String getName() {
        return mName;
    }

    public String getFormName() {
        return mForm.getFormName();
    }

    public String getAttribute(String key) {
        if (mAttrs == null) {
            return null;
        }
        String var = mAttrs.get(key);
        if (var == null) {
            return null;
        }
        return String.valueOf(var);
    }

    public void setDatas(Map<String, Object> datas) {
        mDatas = datas;
    }
    public  Map<String, Object> getDatas(){
        return  mDatas;
    }
    public String getData(String key) {
        if (mDatas == null || mDatas.size() == 0) {
            return null;
        }
        return DataHelper.get(mDatas, key);
    }

    public boolean hasDatas() {
        return mDatas != null && mDatas.size() > 0;
    }

    protected abstract HtmlBodyElement<?> create();

    private static String last = null;

    /**
     */
    public final String createView() {
        HtmlBodyElement<?> element = create();
        if (element == null) return "";
        HtmlBodyElement<?> element2 = getDataProvider().onCompleted(this, element);
        if (element2 == null) return "";
        element2.styles(getAttribute("style"));
        //列表头字母
        if ("firstLetter".equals(element2.getId())) {
            String cur = element2.getText().trim();
            if (last != null) {
                if (last.equals(cur)) {
                    element2.styles("display:none");
                } else {
                    last = cur;
                }
            } else {
                last = cur;
            }
        } else {

            if("Medical_getDoctorData".equalsIgnoreCase(getFormName())){
                String isRecommend =getData("is_recommend");
                if(isRecommend.equals("1")){
                    if("is_hot".equals(element2.getId())){
                        element2.styles("display:block");
                    }
                }else if(isRecommend.equals("2")){
                    if("is_recommend".equals(element2.getId())){
                        element2.styles("display:block");
                    }
                }
            }else if ("professionalName".equals(element2.getId())){
                ListForm form = new ListForm();
                String professionalName = (String) mDatas.get("professionalName");
                if (professionalName.equals("1")){
                    element2.text(form.one);
                    System.out.println(form.one);
                }else if (professionalName.equals("2")){
                    element2.text(form.two);
                    System.out.println(form.two);
                }else if (professionalName.equals("3")){
                    element2.text(form.three);
                    System.out.println(form.three);
                }else if (professionalName.equals("4")){
                    element2.text(form.four);
                    System.out.println(form.four);
                }
                System.out.println("form.one======>"+form.one);
            }
//            if ("homePage".equalsIgnoreCase(getFormName())) {
//                String state = getData("state");
//                System.out.println("===================state=" + state);
//                if (!"0".equals(state)) {
//                    element2.style("display", "none");
//                }
//            }
        }
        return element2.toString();
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
                if (index > 0) {
                    k = w.substring(0, index);
                    v = w.substring(index + 1);
                    attrs.put(k, v);
                }
            }
            if (attrs.size() == 0) {
                System.err.println("parseAttribute failed? size = 0, attribute= " + attribute);
            }
        }
        return attrs;
    }

    protected int str2int(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {

        }
        return 0;
    }

    public Position getPosition(Map<String, String> attrs) {
        Position position = new Position();
        String var = attrs.get("position");
        if (var != null) {
            //265/400,25/1500
            String[] ws = var.split(",");
            if (ws.length > 1) {
                String xy1 = ws[0];//265/400
                String xy2 = ws[1];//25/1500
                position.setX1(str2int(xy1.split("/")[0]));
                position.setY1(str2int(xy1.split("/")[1]));

                position.setX2(str2int(xy2.split("/")[0]));
                position.setY2(str2int(xy2.split("/")[1]));
            }
        }
        return position;
    }

    private View mView;

    public View getView() {
        if (null != mView) {
            return mView;
        }
        return mView;
    }

    //type='textNoTitle' formName='jkdView' name='line'
    public final void initView(ParentForm form, View view, IDataProvider dataProvider) {
        mForm = form;
        mView = view;
        mName = view.getName();
       // mAttrs = parseAttribute(view.getWapAttribute());
        mAttrs = parseAttribute(view.getAttributeStr());
        mIndex = view.getIndex_number();
        mIDataProvider = dataProvider;
    }

    //控件默认样式
    public String getStyle(String type) {
        String sytle = "";
        if("span".equals(type)){
            sytle = "width:250px;height:25px;font-size: 14px;";
        }else if("select".equals(type)){
            sytle = "width:250px;height:25px;";
        }
        return sytle;
    }

    //当前form是否为显示
    public boolean isShow(String showType) {
        if("listForm".equals(showType) || "MDlistForm".equals(showType)){
            return true;
        }else if("newForm".equals(showType) || "MDnewForm".equals(showType) || "listModifyForm".equals(showType) || "MDlistModifyForm".equals(showType)){
            return false;
        }
        return false;
    }

}
