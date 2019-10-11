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

public class ChatForm extends CustomForm {
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
        HighCharttwo char12 = new HighCharttwo();
        if (forms.getChatType().contains("pie")) {
          //  List<HighChartPie> listtwo = new ArrayList<>();
            /*  [{name: 'Chrome',y: 61.41}, { name: 'Internet Explorer',y: 11.84 }]*/
            List<HighChartPie> pieList = new ArrayList<>();
            JSONArray array = new JSONArray();
            for (int i = 0; i < xyDatas.size(); i++) {
                HighChartPie pie = new HighChartPie();

                Map<String, Object> entity = xyDatas.get(i);
                System.out.println(entity.toString());
                System.out.println("sums" + entity.get("sums").toString());
                String name = entity.get(x).toString();
                Double numerical = Double.parseDouble(entity.get("sums").toString());

                HighCharttwo char2 = new HighCharttwo();
                char2.setName(name);
                char2.setY(numerical);
                //list.add(char2);
                pie.setName(name);
                pie.setY(numerical);
                pieList.add(pie);

            }
            char12.setData(pieList);
            list.add(char12);

        } else {
            double[] yDatas = new double[xyDatas.size()];
            String[] xtitle = new String[xyDatas.size()];
            for (int i = 0; i < xyDatas.size(); i++) {
                Map<String, Object> entity = xyDatas.get(i);
                System.out.println(entity.toString());
                xtitle[i] = entity.get(x).toString();
                yDatas[i] = Double.parseDouble(entity.get("sums").toString());
            }
            char12.setName(y);
            //char12.setData(yDatas);
            list.add(char12);
            higt.setxCategories(xtitle);
        }
        HighCharttwo char13 = new HighCharttwo();
        char13.setName("1");
       // char13.setData(new double[]{49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4});
        //list.add(char13);

        higt.setData(list);

        /*        System.out.println(forms.getTable_name()+"\t"+forms.getXcolumn()+"\t"+forms.getYcolumn());
//        String tableName = forms.getTable_name();
      //  List<Map<String, Object>> datas = commonDao.getxyByData(companyId,tableName,x,y);
        List<String> names = new ArrayList<>();
       int[] data = new int[datas.size()];
        if (datas != null) {
            for (int i = 0; i < datas.size(); i++) {
                Map<String, Object> map = datas.get(i);
                String str = map.get(1).toString();
                data[i] = Integer.parseInt(str);
            }
        }

         Map<String,double[]> mapData = new LinkedHashMap<>();
        //x轴字段
        String[] xCategories =null;
        double[] data = null;
        List<double[]> listData = new ArrayList<>();
        if (mDatas!=null){
            int len = mDatas.size();
            xCategories = new String[len];
            data = new double[len];
            Map<String,Integer> map =xCount(x,mDatas);
            System.out.println(map);
            /*for (int i = 0; i < mDatas.size(); i++) {
                Map<String, Object> map = mDatas.get(i);
                System.out.println(map.get(xy).toString());
                if (map.get(xy).toString()!=null){
                    xCategories[i] = map.get(xy).toString();
                    if (y.contains(",")){
                        for (int j = 0; j < strY.length; j++){
                            String str = strY[i];
                            Double a = Double.parseDouble(str);
                            data[i] = a;
                        }
                    }else {


                        String str1 = map.get(y).toString();
                        System.out.println(str1);
                        Double a = Double.parseDouble(str1);
                        data[i] = a;
                    }
                }
     //       }
        listData.add(data);
    }
        */
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
        div.add(new Script("js/highcharts-3d.js"));
        div.add(new Script("js/grid.js"));
        div.add(new Script("js/exporting.js"));
        Div div1 = div.div();
        div1.id("container");
        div1.styles("width: 1000px;height:400px;");
        Input input = div.input();
        input.type("hidden");
        input.id("data");
        String json = JSONArray.toJSONString(higt).replaceAll("\"", "\'");//
        input.value(json);
//        div.add(new Script().text("" +
//                "$(function(){" +
//                "var data =$(\"#data\").val().replace(/'/g,\"\\\"\");"+
//                " var jsonObject= JSON.parse(data);"+
//                "var chart = Highcharts.chart('container', {"+
//                " chart: {  type: jsonObject.type },"+
//                "title: {  text: jsonObject.titleText },"+
//                " subtitle: {   text: jsonObject.subtitleText  },"+
//                " xAxis: {  categories: jsonObject.xCategories,crosshair: true},"+
//                "yAxis: {  categories: jsonObject.xCategories, title: { text: jsonObject.yTitleText }  },"+
//                "series: jsonObject.data"+
//                " });"+
//                "var chart = Highcharts.chart('container', chart);"+
//                "});"));
        div.add(new Script("js/chart1.js"));

    }


}
