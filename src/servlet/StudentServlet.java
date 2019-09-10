package servlet;

import commandCenter.CommandCenter;
import common.APPConfig;
import common.AccountHelper;
import common.CommonUtils;
import common.SQLHelper;
import common.utils.DataHelper;
import common.utils.HttpUtils;
import common.utils.TextUtils;
import dao.OaSystemSettingDao;
import model.SystemSetting;
import model.Variable;
import page.Page;
import showtype.ParentForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自动页面
 */
public class StudentServlet extends BaseServlet {
    //OaCopModel表的compoundName='Service_mainPage' and showType='copForm'的数据集合   TODO ok
    //拿OaCopModel表的showType为FreeForm和collectionForm的数据集合（根据filterShowTypes过滤）
    //  循环遍历上面的数据
    //    HomeForm[copFormName,showType]反射.TODO showType:freeForm,formName=jkdView
    //       根据FormName查询FormFiled表的name，title，type，androidAttribute得到数据集合
    //       遍历，反射，如果androidAttribute不为空，则解析值，设置控件的数据和文本
    //    根据FormName查询Form表，取得Get_Data_sql的值，然后根据sql查询对应的数据表
    //        根据上面取得的数据，解析，并且字段对应的设置到控件（icon，title，target）【target是关联点击事件】
    @Override
    protected void doHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        System.out.println( "进入studentservlet");
        if(req.getParameter("transferParams")!=null){
            if(req.getParameter("transferParams").contains("globalVariable:medicaltype")){
                String type = req.getParameter("transferParams");
                String medicaltype = type.split(":")[1];
                int idx = medicaltype.indexOf("=");
                if (idx > 0) {
                    Variable.type = medicaltype.substring(idx + 1).trim();
                }
            }
        }

        String copformName = getParameter(APPConfig.COP_FORM_NAME);
        String showType = getParameter(APPConfig.SHOW_TYPE);
        if (TextUtils.isEmpty(copformName) || TextUtils.isEmpty(showType)) {
            //首页
            gotoHomePage(req, resp);
        //    gotoHomePage();
        } else if("1".equals(req.getParameter("addshop"))){ //打开购物弹窗
            String userid = req.getParameter("userid");
            String companyId = req.getParameter("companyId");
            String formName = "Product_spec_Sku";
            String pId = req.getParameter("pId");
            String url = CommonUtils.GetUrl3+"interactiveLatitude=23.140941&condition=%27p.id=";
            url += pId;
            String url2 = "%27&interactiveAreaId=440100&Company_Id=";
            url2+=companyId;
            String url3 = "&formName=";
            url3 += formName;
            String url4 = "&interactiveLongitude=113.363104&startRowNo=&rowCount=&userid=";
            url4 += userid;
            String url5 = "&indistinctSearch=&updateType=all";
            url += url2;
            url += url3;
            url += url4;
            url += url5;
            String rs = HttpUtils.get(url);
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write(this.getAddShopCart(rs,userid));

        } else if("2".equals(req.getParameter("addshop"))){
            String number = req.getParameter("number");//购买数量
            String id = req.getParameter("id");//商品id
            String userid = req.getParameter("userid");//用户id

            String url = CommonUtils.SumbitUrl+"Sku_id="+id+"&Account_id="+userid+"&Company_Id="+ APPConfig.COMPANYID+"&formName=addShoppingCart&showType=newForm&Id=&state=0&userId="+userid+"&account="+number;
            String rs = HttpUtils.get(url);
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write(rs);
        } else if("3".equals(req.getParameter("addshop"))){
            String number = req.getParameter("number");//购买数量
            String id = req.getParameter("id");//商品id
            String userid = req.getParameter("userid");//用户id
            String url = CommonUtils.SumbitUrl+"Sku_id="+id+"&Account_id="+userid+"&Company_Id="+ APPConfig.COMPANYID+"&formName=addShoppingCart&showType=newForm&Id=&state=1&userId="+userid+"&account="+number;
            String rs = HttpUtils.get(url);
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write(rs);
        }
        else {
            //网页
            CommandCenter.writeHtml(this, copformName, showType);
        }
    }

    /**
     * 通过返回的数据组装出弹出窗口选择规格并加入购物车
     * @param str 商品规格信息
     * @return
     */
    public String getAddShopCart(String str,String userid){
        StringBuilder sb = new StringBuilder();
        String img="";
        List<String> price=new ArrayList<>();//商品规格对应的单价
        List<String> stock=new ArrayList<>();//商品规格对应的库存
        List<String> spec = new ArrayList<>();//不同的规格
        List<String> gids = new ArrayList<>();//不通的规格对应的id
        String[] split = str.split("╚");
        String array[] = null;
        if(split.length > 1){
            array = split[0].split("╝");
        }
        if(array != null && array.length > 0){
            for(int i = 0;i < array.length;i++){
                String[] info = array[i].split("&");
                if("".equals(img) && info != null && info.length > 4)
                    img = info[5];
                if(info.length >= 7){
                    price.add(info[4]);
                    stock.add(info[3]);
                    spec.add(info[2]);
                    gids.add(info[0]);
                }
            }
        }
        List<String> guiges = new ArrayList<>();
        for(int i = 0;i < spec.size();i++){
            if(i == 0)
                guiges.add("<button data-choose=\"1\" onclick=\"chooseSpec(this)\" name=\"btn"+i+"\" data-id=\""+gids.get(i)+"\" data-price=\""+price.get(i)+"\" data-stock=\""+stock.get(i)+"\" data-spec=\""+spec.get(i)+"\" style=\"background-color:red;border-radius:25px\">"+spec.get(i));
            else
                guiges.add("<button data-choose=\"0\" onclick=\"chooseSpec(this)\" name=\"btn" + i + "\" data-id=\""+gids.get(i)+"\" data-price=\""+price.get(i)+"\" data-stock=\""+stock.get(i)+"\" data-spec=\""+spec.get(i)+"\" style=\"border-radius:25px\">"+spec.get(i));
        }
        sb.append("<div id=\"zzc\" onclick=\"hider(this)\" class=\"modal-maska\" id=\"Mask\" style=\"opacity: 1; display: block;\"></div>");
        sb.append("<div id=\"heyesb\" class='modal-actionsheet'><div id=\"guige\"><div><img style=\"width:100px;height:100px;position: relative;top: -24px;left: 10px;float: left;\" " +
                "src=\""+CommonUtils.getFileData+img+"\"/><span class=\"priceParent\" name=\"priceParent\">￥<span name=\"price\">"+(price != null && price.size() > 0 ? price.get(0) : "0")+"</span></span>" +
                "<span class=\"stockParent\" name=\"stockParent\">库存<span name=\"stock\">"+(stock != null && stock.size() > 0 ? stock.get(0) : "0")+"</span></span>" +
                "<span class=\"cspecParent\" name=\"cspecParent\">已选:<span name=\"cspec\">"+(spec != null && spec.size() > 0 ? spec.get(0) : "" )+"</span></span></div></div>");
        sb.append("<div class=\"capacity\" id=\"specdiv\"><div>容量</div>");
        for(int i = 0;i < guiges.size();i++){
            sb.append(guiges.get(i));
        }
        sb.append("</div><img style=\"width:100%;background-color:gray;height:1px\"></img><div class=\"purchase-quantity\">购买数量<span class=\"number-delete\" onclick=\"numberSub("+userid+")\">-</span><span class=\"number\" name=\"number\">1</span><span class=\"number-add\" onclick=\"numberAdd("+userid+")\">+</span>");
        sb.append("</div>");
        sb.append("<div style=\"width:100%;height:50px;position:fixed;bottom:0;float:left;\"><button class=\"add-shopcart\" onclick=\"addShopCart("+userid+")\">加入购物车</button><button class=\"quick-buy\" onclick=\"quickBuy("+userid+")\">立即购买</button></div>");
        return sb.toString();
    }

    //菜单
    private ParentForm mMenuForm;



    public ParentForm getMenuForm() {
        return mMenuForm;
    }

    public ParentForm getMenuForm(String companyId, SQLHelper pSQLHelper) {
        System.out.println("---------------进入getmenuform-------------");

        if (mMenuForm == null) {
            OaSystemSettingDao systemSettingDao = new OaSystemSettingDao(pSQLHelper);
            //通用
            //SystemSetting homepage = systemSettingDao.getSystemSetting(companyId, "homepage");
            //巴中
            SystemSetting homepage = systemSettingDao.getSystemSetting(companyId, "homepage");
            System.out.println("///////////////////////////"+homepage+"//////////////////////");
            if(homepage!=null){
                System.out.println("-----------进来了******homepage*********----------------");
                List<String> vals = DataHelper.toList(homepage.getValueStr());
                String name = vals.get(0);
                String type = vals.get(1);
                System.err.println(vals);
                mMenuForm = CommandCenter.compositeTemplate(companyId, type, name);


            }
            mMenuForm.loadData(pSQLHelper, new ParentForm.ISQLReplacer() {
                @Override
                public String replaceSqlParams(ParentForm pBaseForm, String sql) {
                    return sql;
                }
            });

        }
        return mMenuForm;
    }

    /**
     * 是否有菜单
     *
     * @param menuForm
     * @param copformName
     * @param showType
     */
    public boolean hasMenu(ParentForm menuForm, String copformName, String showType) {
        List<Map<String, Object>> datas = menuForm.getDatas();
        System.out.println("---------------进入hasMenu-------------");
        for (int i = 0; i < datas.size(); i++) {
            //查数据库，取第一个按钮的
            Map<String, Object> first = datas.get(i);
            String FormName = DataHelper.get(first, "FormName");
//            String ShowType = DataHelper.get(first, "ShowType");
            if (TextUtils.equalsIgnoreCase(FormName, copformName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 首页
     */
    private void gotoHomePage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SQLHelper sqlHelper = new SQLHelper(req);
        OaSystemSettingDao systemSettingDao = new OaSystemSettingDao(sqlHelper);
        SystemSetting isforcelogin = systemSettingDao.getSystemSetting(getCompany_Id(), "isforcelogin");
        System.out.println(isforcelogin);
        if (isforcelogin != null && DataHelper.toInt(isforcelogin.getValueStr(), 0) != 0) {
            //是否登录？
            System.err.println("need login");
            AccountHelper accountHelper = new AccountHelper(req, resp);
            if (accountHelper.getUserId() > 0 || accountHelper.autoLogin()) {
                //已经登录，自动登录
                System.err.println("auto login goto home");
            } else {
                //登录页
                SystemSetting loginType = systemSettingDao.getSystemSetting(getCompany_Id(), "loginType");
                if (loginType == null || loginType.getValueStr() == null) {
                    System.err.println("no find loginType");
                    resp.setStatus(404);
                } else {
                    String[] strs = loginType.getValueStr().split(",");
                    if (strs.length <= 1) {
                        String FormName = strs[0].trim();
                        String ShowType = strs[1].trim();
                        System.err.println("goto login:" + FormName + "/" + ShowType);
                        resp.sendRedirect(Page.getHomeUrl(FormName, ShowType));
                    } else {
                        System.err.println("find loginType:" + loginType.getValueStr());
                        resp.setStatus(404); }
                }
                return;
            }
        }


        //获取首页
        ParentForm parentForm = getMenuForm(getCompany_Id(), sqlHelper);

        List<Map<String, Object>> datas = parentForm.getDatas();
        System.out.println("====================gotoHomePage-datas=============="+datas);
        if (datas != null && datas.size() > 0) {
            //查数据库，取第一个按钮的
            Map<String, Object> first = datas.get(0);
            String FormName = DataHelper.get(first, "FormName");
            String ShowType = DataHelper.get(first, "ShowType");
            System.err.println("goto home:" + FormName + "/" + ShowType);
            resp.sendRedirect(Page.getHomeUrl(FormName, ShowType));
        } else {
            //404
            System.err.println("no find home:" + parentForm);
            resp.setStatus(404);
        }
    }

}
