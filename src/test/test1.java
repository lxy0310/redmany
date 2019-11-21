package test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Iterator;
import java.util.List;

public class test1 {

    private static void JsonToSTring() {
        // TJSONObject 转换为 String
        JSONObject json = new JSONObject();
        //向json中添加数据
        json.put("name", "kwang");
        json.put("height", 175);
        json.put("age", 24);
        System.out.println(json);

        JSONObject json1 = new JSONObject();
        //向json中添加数据
        json1.put("name1", "kwang");
        json1.put("height1", 175);
        json1.put("age1", 24);
        System.out.println(json1);
        //创建JSONArray数组，并将json添加到数组
        JSONArray array = new JSONArray();
        array.add(json);
        array.add(json1);
        //转换为字符串
        String jsonStr = array.toString();

        JSONArray array1 = new JSONArray();
        System.out.println(jsonStr);
        String s = jsonStr.replaceAll("\"","\'");
        System.out.println("sss\t"+s);
        System.out.println("JSONArray\t\t\t"+JSONArray.parseArray(jsonStr));

        String str = "[{"+"datas:"+jsonStr+"}]";
        System.out.println(str);


//        JSONObject json = new JSONObject();
//        json.put("name",name);
//        json.put("y",numerical);
//        array.add(json);
//
//        //转换为字符串
//        String jsonStr = array.toString();
//        String s = jsonStr.replaceAll("\"","\'");
//        System.out.println(jsonStr);
//        System.out.println("sss\t"+s);
//        String str = "[{"+"datas:"+jsonStr+"}]";
//        System.out.println("json数据\t"+str);

    }
    public static void main(String[] args) {
        JsonToSTring();
    }
    private static void StringToJson(String str) {
        //（解析json） String 转换为 JSONObject 对象
        JSONObject json = (JSONObject) JSONObject.parse(str);
        //遍历jsonObject出来的是 key /value ，get(key)是value
        Iterator it = json.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            String value = String.valueOf(json.get(key));
            System.out.println(key + ":" + value);
        }
    }

    private static void createJsonArray() {
        //  创建 JSONArray
        JSONArray jsonarray = new JSONArray();

        JSONObject node = new JSONObject();
        node.put("name","kwang");
        node.put("age",20);
        jsonarray.add(node);

        node = new JSONObject();
        node.put("name","xkang");
        node.put("age",15);
        jsonarray.add(node);
        System.out.println(jsonarray);
    }



    private static void createJSONObject() {
        // 创建json对象
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Jason");
        jsonObject.put("id", 1);
        jsonObject.put("phone", "18271415782");
        System.out.println(jsonObject.toString());
    }


}
