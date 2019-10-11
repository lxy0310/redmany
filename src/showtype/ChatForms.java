package showtype;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.head.Script;
import common.SQLHelper;
import dao.CommonDao;
import dao.FormDao;
import dao.FormFiledDao;
import dao.MenuDao;
import model.*;
import page.CustomForm;

import java.util.*;

public class ChatForms extends CustomForm {
    //dao层
    private MenuDao menuDao;
    private FormDao formDao;
    private FormFiledDao filedDao;
    private CommonDao commonDao;
    //对象与变量
    private Form forms;
    private List<Map<String, Object>> formFeilds;
    private Menu Menus;
    private HighChart higt; //图表实例
    private String strs;

    protected void initDao(SQLHelper pSQLHelper) {
        menuDao = new MenuDao(pSQLHelper);
        formDao = new FormDao(pSQLHelper);
        filedDao = new FormFiledDao(pSQLHelper);
        commonDao = new CommonDao(pSQLHelper);
    }

    @Override
    protected void loadData(String sql) {
        Menus = menuDao.getMenu(getCompanyId(), getFormName()); //获取menu表数据
        //获取form表数据
        forms = formDao.getForm(getCompanyId(), formName); //form表数据
        formFeilds = filedDao.getFormFeildList(getCompanyId(), formName); //formfiled表数据

        super.loadData(sql);
        System.out.println(sql);
    }

    private void HighChart() {
        higt = new HighChart();
        higt.setTitleText(forms.getTitle());
        // higt.setSubtitleText("213");
        if (forms.getbFormColumn() != null) {
            higt.setType(forms.getChatType());
        } else {
            higt.setType("column");
        }
        //x,y轴的字段
        String x = forms.getXcolumn();
        String y = forms.getYcolumn();
        String xy = forms.getXycolumn();
        String[] strY = null;
        String[] strYTitle = null;
        if (y.contains(",")) {
            strY = y.split(",");
        }
        for (Map<String, Object> filed : formFeilds) {
            if (filed.get("Name").equals(xy) || filed.get("Name") == xy) {
                higt.setyTitleText(filed.get("Title").toString());
            }
            if (strY != null) {
                strYTitle = new String[strY.length];
                for (int i = 0; i < strY.length; i++) {
                    String str = strY[i];
                    if (filed.get("Name").equals(str) || filed.get("Name") == str) {
                        strYTitle[i] = filed.get("Title").toString();
                    }
                }
            }
        }
        //查询统计数据
        List<Map<String, Object>> xyDatas = null;
        if (y.contains("counts")) {
            String sql = "select top 10 COUNT(1) as sums," + x + " from " + forms.getTable_name() + " " + forms.getReplaceName() + " GROUP BY " + x;
            System.out.println(sql);
            xyDatas = commonDao.getxyByDatas(getCompanyId(), sql);
        } else {
            String sql = "select top 10 SUM(convert(DECIMAL(8,1)," + y + ")) as sums," + x + " from " + forms.getTable_name() + " " + forms.getReplaceName() + " GROUP BY " + x;
            System.out.println(sql);
            xyDatas = commonDao.getxyByDatas(getCompanyId(), sql);
        }
        List<HighCharttwo> list = new ArrayList<HighCharttwo>();
        JSONObject json1 = new JSONObject();

        if (forms.getChatType().contains("pie")) {
            JSONArray array = new JSONArray();
            for (int i = 0; i < xyDatas.size(); i++) {
                JSONObject json = new JSONObject();
                Map<String, Object> entity = xyDatas.get(i);
                System.out.println(entity.toString());
                System.out.println("sums" + entity.get("sums").toString());
                String name = entity.get(x).toString();
                Double numerical = Double.parseDouble(entity.get("sums").toString());
                json.put("name",name);
                json.put("y",numerical);
                array.add(json);
            }
            //转换为字符串
            String jsonStr = array.toString();
            String jsonStrs = jsonStr.replaceAll("\"name\"","name");
            String jsonStr2 = jsonStrs.replaceAll("\"y\"","y");
            //String jsonStr3
            System.out.println("jsonStrs\t\t"+jsonStrs);
            String str = "[{"+"datas:"+jsonStr+"}]";
            System.out.println(str);
            strs = "var chart = Highcharts.chart('container', {\n"+
                    "chart:{ type:'pie'},\n"+
                    "title:{text:'"+xy+"'},\n"+
                    "plotOptions: {column: { borderWidth: 0  }},\n"+
                    "series:[{"+
                    "data:"+jsonStr2.replaceAll("\"", "\'")+" \n" +
                    "}]\n"+
                    " });"
                    ;
            System.out.println("strs\t\t"+strs);
        }
        higt.setData(list);
    }


    private Map<String, Integer> xCount(String code, List<Map<String, Object>> mDatas) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < mDatas.size(); i++) {
            Map<String, Object> entity = mDatas.get(i);
            if (map.containsKey(entity.get(code))) {
                int num = map.get(entity.get(code));
                map.put(entity.get(code).toString(), ++num);
            } else {
                map.put(entity.get(code).toString(), 1);
            }
        }
        return map;
    }

    private Map<String, Double> xSum(String code, Double sum, List<Map<String, Object>> mDatas) {
        Map<String, Double> map = new HashMap<String, Double>();
        for (int i = 0; i < mDatas.size(); i++) {
            Map<String, Object> entity = mDatas.get(i);
            if (map.containsKey(entity.get(code))) {
                double num = map.get(entity.get(code));
                map.put(entity.get(code).toString(), ++sum);
            } else {
                map.put(entity.get(code).toString(), sum);
            }
        }
        return map;
    }


    protected void make(Div div) {

        // div.add(new Script("http://cdn.highcharts.com.cn/highcharts/modules/exporting.js"));
        // div.add(new Script("js/highcharts-more.js"));
        HighChart(); //图表的封装
        System.out.println(mDatas.toString());
        div.add(new Script("js/jquery-2.1.4.min.js"));
        div.add(new Script("js/highcharts.js"));
        Div div1 = div.div();
        div1.id("container");
        div1.styles("width: 1000px;height:400px;");
        Input input = div.input();
        input.type("hidden");
        input.id("data");
        String json = JSONArray.toJSONString(strs).replaceAll("\"", "\'");
        input.value(json);
        System.out.println(json);
        div.add(new Script().text("$(function(){\n" +
                strs+
//                "console.log("+strs+");"+
                "var chart = Highcharts.chart('container', chart);\n"+
                "});"));
      //  div.add(new Script("js/chart1.js"));
        div.add(new Script("js/jquery.js"));
    }


}
