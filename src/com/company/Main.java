package com.company;

import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.table.TBody;
import com.sangupta.htmlgen.tags.body.table.Table;
import com.sangupta.htmlgen.tags.body.table.TableDataCell;
import com.sangupta.htmlgen.tags.body.table.TableRow;
import common.CollectionUtil;
import common.DataUtil;
import common.DateUtil;
import common.SQLHelper;
import dao.FormFiledDao;
import viewtype.View;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Main {

    public static void main(String[] args)  {
        FormFiledDao ffd=new FormFiledDao(new SQLHelper(true));
      List<View> tes= ffd.getFormContorl("AntMall","Order_management",null);
        Map<String,List<View>> test= CollectionUtil.getMap(tes,View.class,"filedGroup",String.class);

/*        Div div = new Div();
        Table table = div.table();
        int count = 15;
        int MAX_COl = 5;
        TBody tBody = table.tbody();

        TableRow tr = null;
        for (int i = 0, j = 0; i < count; i++, j++) {
            if (j % MAX_COl == 0) {
                tr = tBody.tr();
            }
            TableDataCell tableRow = tr.td();
            tableRow.styles("text-align:center; font-szie:18px;");
            tableRow.img("green.jpg");
            tableRow.p("文字:" + i);
        }
        System.out.println(div);*/
    }
}
