package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.text.Label;
import com.sangupta.htmlgen.tags.body.text.Span;
import com.sangupta.htmlgen.tags.head.Script;

/**
 * 双日历日期范围选取器控件
 */
public class DatePicker extends ParentView {
    @Override
    public String getType() {
        return "DatePicker";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        Div div = new Div();
        div.id(getName());
        div.addCssClass("tableOverflow");
        String optype = getPage().getParameter("optype");//修改1查看2
        String text = getDataProvider().getText(this, getForm());//input值
        String styles = getDataProvider().getStyles(this, getForm());
        String css = getDataProvider().getCssClass(this, getForm());
        String color = getDataProvider().getTextColor(this, getForm());
        if (getView()!=null){
            View view=getView();
            Label label = div.label();
            if (view.getWapAttribute()!=null){
                String str=view.getWapAttribute();//获取样式
                if(str.indexOf("isEdit:")>=0){
                    str = str.replace(" ","");
                    String num=str.substring(str.indexOf("isEdit:")+7,str.indexOf("isEdit:")+8);
                    if ("0".equals(num.trim())){
                        view.setIsTitle("0");
                    }
                }
            }
            if(view.getIsTitle()!=null && "1".equals(view.getIsTitle())) {//不长title
                div.text(text==null?"":text);
                return div;
            }else{
                label.text(view.getTitle()==null?"":view.getTitle());
            }

            Input input = div.input();
            input.id(getName()+'0');
            input.name(getName());
            input.addCssClass(getName());

            /*   wapAttribute字段，用[^]区分开
            注意：当设置格式中有汉字时，存储的字段需要为varchar类型
            默认的值无需设置，当与默认值不同时设置即可。
            singleDate 日历格式： 0双日历、1单日历 默认双日历
            date 日期格式： 0 YYYY/MM/DD、1 YYYY-MM-DD、2 YYYY年MM月DD日
            time 时间格式： 0 HH:mm:ss 1 HH时mm分ss秒 默认为0
            showTime 是否显示时间： 0不显示 1显示 默认不显示
            单日历默认为YYYY-MM-DD 双日历默认为YYYY/MM/DD-YYYY/MM/DD   */

            String scriptText = "";
            String dateSdf = "YYYY/MM/DD";
            String timeSdf = "HH:mm:ss";
            boolean showTime = false;
            String datePickerStr = "autoUpdateInput:false, locale:datePircker_locale,minDate: moment(),";//选择器的属性
            String valueStr = "picker.startDate.format('"+ dateSdf +"') + ' - ' + picker.endDate.format('"+ dateSdf +"')";
            if (view.getWapAttribute()!=null){
                String str=view.getWapAttribute();//获取样式
                String[] strs = str.split("\\[\\^\\]");
                if (strs!=null){
                    for(int i=0;i<strs.length;i++){
                        if (strs[i].contains("singleDate")){//显示单日历
                            String  num=strs[i].substring(strs[i].lastIndexOf(":")+i);
                            if (num.equals("1")){
                                datePickerStr = datePickerStr + "singleDatePicker: true,";
                                valueStr = "picker.startDate.format('"+ dateSdf +"')";
                            }
                        }
                        if (strs[i].contains("showTime")){//显示时间
                            String  num=strs[i].substring(strs[i].lastIndexOf(":")+i);
                            if (num.equals("1")){
                                datePickerStr = datePickerStr + "timePicker: true,";
                                showTime = true;
                            }
                        }
                        if (strs[i].contains("date")){//日期格式： 0 YYYY/MM/DD、1 YYYY-MM-DD、2 YYYY年MM月DD日
                            String  num=strs[i].substring(strs[i].lastIndexOf(":")+i);
                            if (num.equals("1")){
                                dateSdf = "YYYY-MM-DD";
                            }else if(num.equals("2")){
                                dateSdf = "YYYY年MM月DD日";
                            }
                        }
                        if (strs[i].contains("time")){//时间格式 0 HH:mm:ss 1 HH时mm分ss秒
                            String  num=strs[i].substring(strs[i].lastIndexOf(":")+i);
                            if (num.equals("1")){
                                timeSdf = "HH时mm分ss秒";
                            }
                        }
                    }
                }
            }
            if(showTime){
                dateSdf = dateSdf + timeSdf; //为防止顺序问题被替换，放到最后修改
            }
            datePickerStr = datePickerStr.substring(0,datePickerStr.length()-1);
            scriptText = "$('input[name=\""+ getName() +"\"]').daterangepicker({"+ datePickerStr +"});" +
                    "$('input[name=\""+ getName() +"\"]').on('apply.daterangepicker', function(ev, picker) {" +
                    "$(this).val("+ valueStr +");});" +
                    "$('input[name=\""+ getName() +"\"]').on('cancel.daterangepicker', function(ev, picker) {" +
                    "$(this).val('');});";
            div.add(new Script().text(scriptText));

            if (text!=null && optype!=null){
                input.value(text);
            }
            if(optype!=null && "2".equals(optype)){
                input.attr("readonly","false");
            }else if (view.getIsReadonly()=="1"){
                input.attr("readonly","false");
                input.styles("color: #939192;background: #f5f5f5!important;border: 1px solid;");
            }
        }

        if (color != null) {
            div.style("color", color);
        }
        if (styles != null) {
            div.styles(styles);
        }
        if (css != null) {
            div.addCssClass(css);
        }
        return div;
    }
}