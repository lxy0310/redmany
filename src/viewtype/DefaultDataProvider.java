package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import common.BaiduLocation;
import common.DataUtil;
import common.utils.DataHelper;
import common.utils.SafeString;
import common.utils.TextUtils;
import model.Variable;
import page.Page;
import showtype.ParentForm;
import servlet.StudentServlet;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DefaultDataProvider implements IDataProvider {
    public static String DIR_IMAGES = null;
    public static final DefaultDataProvider DEFALUT = new DefaultDataProvider();
    private Map<String, String> oldParams = new HashMap<>();
    private Map<String, String> oldTarget = new HashMap<>();
    private Map<String, String> newParams = new HashMap<>();
    private Map<String, String> globalVar = new HashMap<>();

    private void init(StudentServlet pServlet){

    }

    public DefaultDataProvider() {

    }

    @Override
    public String getImageUrl(ParentView view, ParentForm baseForm) {
        String name = view.getAttribute("content");
        if (name == null) {
            if (view.hasDatas()) {
                name = view.getData(view.getName());
            }
            if (name == null) {

                name = baseForm.getValue(view.getName());

            }
        }
        if (name == null) {
            return null;
        }
        String src = "images/" + name;
        if (DIR_IMAGES != null) {
            File file = new File(DIR_IMAGES, name);
            if (file.exists()) {
                return src;
            } else {
                return ParentView.IMAGE_PRE + name;
            }
        }
        return src;
    }

    @Override
    public String getText(ParentView view, ParentForm baseForm) {
        if ("mylocation".equals(view.getName()) || "loc-city".equals(view.getName())) {

            String city = SafeString.unescape(baseForm.getPage().getCookieValue(Page.LOC_ADDRESS_SHORT));
            if (!TextUtils.isEmpty(city)) {
                return city;
            }
            BaiduLocation.LocationData data = BaiduLocation.getLocationByIp(baseForm.getPage().getIP());
            BaiduLocation.saveLocation(data, baseForm.getPage().getHttpServletResponse());
            city = data.getShortName();
            if (TextUtils.isEmpty(city)) {
                return "请手动选择位置";
            }
            return city;
        }
        String text = view.getAttribute("Title");
        if (text == null) {
            if (view.hasDatas()) {
                text = view.getData(view.getName());
            }else {

                text = baseForm.getValue(view.getName());
            }
//            if (text == null) {
//                text = baseForm.getValue(view.getName());
//            }
        }
        return text;
    }


    public String getHintContent(ParentView view, ParentForm baseForm) {
        return view.getAttribute("hintContent");
    }

    public String getTextEdit(ParentView view, ParentForm baseForm) {
        return view.getAttribute("isEdit");
    }

    public String getTextDesc(ParentView view, ParentForm baseForm) {
        return view.getAttribute("txtDesc");
    }

    public String getTextName(ParentView view, ParentForm baseForm) {
        return view.getAttribute("editName");
    }

    public String getOptLabel(ParentView view, ParentForm baseForm) {
        return view.getAttribute("optLabel");
    }

    public String getOptVal(ParentView view, ParentForm baseForm) {
        return view.getAttribute("optVal");
    }

    @Override
    public String getTextColor(ParentView view, ParentForm baseForm) {
        return view.getAttribute("textColor");
    }

    @Override
    public String getStyles(ParentView view, ParentForm baseForm) {
        return null;
    }

    @Override
    public String getCssClass(ParentView view, ParentForm baseForm) {
        return null;
    }

    @Override
    public HtmlBodyElement<?> onCompleted(ParentView view, HtmlBodyElement<?> element) {
        return element;
    }

    @Override
    public String getOnClick(ParentForm baseForm, ParentView parentView, Map<String, Object> map) {
        if (map == null) return null;
        String target = DataHelper.get(map, "target");
        String transferParams;
        if (target == null) {

            target = baseForm.getFormData().getTarget();
            transferParams = baseForm.getFormData().getTransferParams();

            if(TextUtils.isEmpty(target)||target.startsWith("empty")){
                return null;
            }
        } else {
            if(TextUtils.isEmpty(target)||target.startsWith("empty")){
                return null;
            }
            transferParams = replaceParams(baseForm, map, DataHelper.get(map, "transferParams"));
        }
        if (transferParams == null) {
            return "gotoPage('" + target + "',null);";
        }else{
            String Id = DataHelper.get(map, "Id");
            String lowerId = DataHelper.get(map, "id");
            String Name = DataHelper.get(map, "Name");
            if (Id != null) {
                transferParams = transferParams.replace("{Id}", Id);
            }
            if (lowerId != null) {
                transferParams = transferParams.replace("{Id}", lowerId);
            }
            if (Name != null) {
                transferParams = transferParams.replace("{Name}", ""+Name);
                transferParams = transferParams.replace("{fromGlobal_edt}", ""+Name);
            }
        }
        return "gotoPage('" + target + "','" + transferParams + "');";
    }

    protected String replaceParams(ParentForm pParentForm, Map<String, Object> map, String transferParams) {
        if (transferParams == null || transferParams.length() == 0 || map == null) {
            return null;
        }
        if (!map.containsKey("cacheId")) {
            map.put("cacheId", pParentForm.getPage().getUserId());
        }

        transferParams = transferParams.replace("{cacheId}", "" + pParentForm.getPage().getUserId());
        //{serviceid}
        for (Map.Entry<String, Object> e : map.entrySet()) {
            String key = "{" + e.getKey() + "}";
            String val = DataHelper.toString(e.getValue(), "");
            transferParams = transferParams.replace(key, val);
        }
        return transferParams;
    }

    @Override
    public String getOnClick(ParentForm baseForm, ParentView parentView, String target, String transferParams) {
        newParams = Variable.mParam;
        globalVar = Variable.globalVariable;
        if (target == null) {
            return null;
        }else if(target.indexOf("submit:") >= 0 && target.indexOf("[^]") > 0){

            String targetSubmit = target.split("\\[\\^\\]")[0];
            if(target.contains("{cacheId}")){

                target = target.replace("{cacheId}", "" + baseForm.getPage().getUserId());
            }
            if(targetSubmit!=null&&targetSubmit.indexOf("$$")>0&&targetSubmit.indexOf("╗")>0){
                String targetSub = targetSubmit.split("\\$\\$")[1];
                String[] resultSub = targetSub.split("\\╗");
                for (String r : resultSub) {
                    int idx = r.indexOf("=");
                    if (idx > 0) {
                        oldTarget.put(r.substring(0, idx).trim(), r.substring(idx + 1).trim());
                    }
                }
                for (Map.Entry<String, String> e : oldTarget.entrySet()) {
                    String keys = e.getKey();
                    String oldTargetVal = e.getValue();
                    if (oldTargetVal.equals("{cacheId}")) {

                        target = target.replace("{cacheId}", "" + baseForm.getPage().getUserId());
                    }
                }
            }
            String targetVals = target.split("\\[\\^\\]")[1];
            if(targetVals.indexOf("$$")>0&&targetVals.indexOf("╗")>0){
                String targetVal = targetVals.split("\\$\\$")[1];
                String[] resultVal = targetVal.split("\\╗");
                for (String r : resultVal) {
                    int idx = r.indexOf("=");
                    if (idx > 0) {
                        oldTarget.put(r.substring(0, idx).trim(), r.substring(idx + 1).trim());
                    }
                }
                for (Map.Entry<String, String> e : oldTarget.entrySet()) {
                    String keys = e.getKey();
                    String oldTargetVal = e.getValue();
                    if (oldTargetVal.equals("{cacheId}")){

                        target = target.replace("{cacheId}", "" + baseForm.getPage().getUserId());
                    }
                    if(newParams!=null){
                        for (Map.Entry<String, String> m : newParams.entrySet()) {
                            String newkey = m.getKey();
                            String val = DataHelper.toString(m.getValue(), "");
                            if(newkey.equals("p.Id")){
                                if(keys.equals("goodid")){
                                    target = target.replace(oldTargetVal, val);
                                }
                            }
                            if(newkey.equals("m.id")){
                                if(keys.equals("dorid")){
                                    target = target.replace(oldTargetVal, val);
                                }
                            }
                            if(newkey.equals("m.Id")){
                                if(keys.equals("hospital")){
                                    target = target.replace(oldTargetVal, val);
                                }

                            }
                            int splitIdx=newkey.indexOf(".");
                            if(splitIdx > 0){
                                String splitKey = newkey.substring(splitIdx + 1).trim();
                                if(splitKey.contains(keys)){
                                    target = target.replace(oldTargetVal, val);
                                }
                            }
                        }
                    }
                }
            }
        }else if(target.indexOf("goto:") >= 0 && target.indexOf("|") > 0){
            String showtype="";
            if(target.indexOf(",")>0){
                showtype=target.split(",")[1];
            }
            int tIdx = target.indexOf("|");
            if (tIdx > 0) {
                target=target.substring(0, tIdx).trim()+","+showtype;
            }
        }
        if (transferParams == null) {
            return "gotoPage('" + target + "',null);";
        }else {
            String[] params = transferParams.split("\\[\\^\\]");
            for (String str : params) {
                String p = str;
                /*if(p.indexOf("Medical_getDoctorData:m.state=?")>=0){
                    if(p.equals("Medical_getDoctorData:m.state=?")){
                       p= p.replace("Medical_getDoctorData:m.state=?","");
                       transferParams = transferParams.replace("Medical_getDoctorData:m.state=?", "");
                    }
                }
                if(p.indexOf("Medical_MyCoupon:cc.state=?")>=0){
                    if(p.equals("Medical_MyCoupon:cc.state=?")){
                        p= p.replace("Medical_MyCoupon:cc.state=?","");
                        transferParams = transferParams.replace("Medical_MyCoupon:cc.state=?", "");
                    }
                }*/
                if (!p.equals("")) {
                    String val = "";
                    boolean flag = p.contains(":");
                    if (flag) {
                        String[] strs = p.split(":");
                        if (strs.length > 1) {
                            val = strs[1];
                        }
                    } else {
                        val = p;
                    }
                    if (val != null) {
                        int oldFlag = val.indexOf("$");
                        if (oldFlag > 0) {
                            val = val.split("\\$")[0];
                        }
                        int i = val.indexOf("=");
                        if (i > 0) {
                            oldParams.put(val.substring(0, i).trim(), val.substring(i + 1).trim());
                        }
                    }
                }
            }
            for (Map.Entry<String, String> e : oldParams.entrySet()) {
                String key = e.getKey();
                String oldVal = e.getValue();
                if (oldVal.equals("{cacheId}")) {
                    transferParams = transferParams.replace("{cacheId}", "" + baseForm.getPage().getUserId());
                }
                if (newParams != null) {
                    //判断是否替换上个页面的transferParams的参数
                    if (DataUtil.hasInnerKeyIgnoreCase(newParams, baseForm.getFormName(), key)) {
                        String val = DataUtil.getInnerParamIgnoreCase(newParams, baseForm.getFormName(), key);
                        if(val!=null){
                            transferParams = transferParams.replace(oldVal, val);
                        }
                    }


               /*     for (Map.Entry<String, String> m : newParams.entrySet()) {
                        String nkey = m.getKey();
                        String val = DataHelper.toString(m.getValue(), "");

                        if(nkey.equals("mro.id")){
                            if(oldVal.equals("{doctorId}")){
                                transferParams = transferParams.replace(oldVal, val);
                            }
                        }
                        if(nkey.equalsIgnoreCase(key)){
                            transferParams = transferParams.replace(oldVal, val);
                        }
                    }*/
                }

                if (globalVar != null) {
                    //判断是否需要替换全局变量
                    if (oldVal.indexOf("fromGlobal_") >= 0) {
                        String val = DataUtil.getInnerParam(globalVar, baseForm.getFormName(), oldVal.substring(1, oldVal.length()-1));
                        if(val!=null){
                            transferParams = transferParams.replace(oldVal, val);
                        }

                    }

                }
            }
        }
        return "gotoPage('" + target + "','" + transferParams + "');";
    }
}
