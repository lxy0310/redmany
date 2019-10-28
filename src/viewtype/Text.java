package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.embed.Img;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.text.Label;
import com.sangupta.htmlgen.tags.body.text.Span;
import com.sangupta.htmlgen.tags.head.Script;
import common.ApiParser;
import common.SQLHelper;
import common.utils.HttpUtils;
import common.utils.SafeString;
import dao.FormDao;
import dao.FormFiledDao;
import model.Form;
import model.ShopCarPageInfo;
import page.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hy on 2017/11/3.
 */
public class Text extends ParentView {
    @Override
    public String getType() {
        return "Text";
    }

    public static List<ShopCarPageInfo> getShopCarItemInfos(int userId) {
        String url = ApiParser.getShopCarUrl(userId, null);
        String rs = HttpUtils.get(url);
        if (rs != null) {
          //  System.err.println(rs);
            return ApiParser.parseList(rs, ShopCarPageInfo.class);
        }
        return new ArrayList<>();
    }

    String enUrl;

    protected void loadData(String sql) {
        enUrl = SafeString.escape(SafeString.encode(getPage().getUrl()));
    }

    @Override
    protected HtmlBodyElement<?> create() {
        boolean isShow = isShow(getForm().getPage().getShowType());
        String optype = getPage().getParameter("optype");//修改1查看2
        Div div = new Div();
        div.id(getName());
        div.addCssClass("tableOverflow");
        String styles = getDataProvider().getStyles(this, getForm());
        String css = getDataProvider().getCssClass(this, getForm());
        String text = getDataProvider().getText(this, getForm());//input值
        String textDesc = getDataProvider().getTextDesc(this, getForm());
        String isEdit =getDataProvider().getTextEdit(this,getForm());//是否禁用
        String txtName =getDataProvider().getTextName(this,getForm());
        String placeholder =getDataProvider().getHintContent(this,getForm());//提示
        String color = getDataProvider().getTextColor(this, getForm());
        String onclick = getDataProvider().getOnClick(getForm(),this, getView().getTarget(), getView().getTransferParams());
        if (getView()!=null){
            View view=getView();
            String textStyle = "";
            if(view.getIsTitle()!=null && "1".equals(view.getIsTitle())) {//不长title
                div.text(text==null?"":text);
                return div;
            }else{
                Label label = div.label();
                label.text(view.getTitle()==null?"":view.getTitle());
            }
            Input input = div.input();
            input.id(getName()+'0');
            input.addCssClass(getName());
            if (view.getDatabase_field()!=null) {   //Database_field
                input.addCssClass(view.getDatabase_field().trim());
            }else{
                input.addCssClass(getName().trim());
            }
            input.name(getName());
            if (view.getIsNull()!=null){
                String isNull=view.getIsNull();//是否为空(1不为空 0 可以为空)
                if("1".equals(isNull)){
                    input.attr("required","required");
                }
            }
            String mesgName = "";
            if(view.getTitle()!=null){
                mesgName = view.getTitle().toString();
                if (view.getTitle().contains(":")){ //如果有：去掉
                    mesgName=mesgName.substring(0,mesgName.length()-1);
                }
            }
            if (view.getValidateExpreesion()!=null){
                String validateStr=view.getValidateExpreesion();//获取验证的正则表达式
                String messageStr = "";
                if(view.getValidateErrorMessage()!=null){
                    messageStr=view.getValidateErrorMessage();
                    messageStr="["+mesgName+"]"+messageStr;
                }else{
                    messageStr="["+mesgName+"]的格式有误";
                }
                input.attr("onblur","validate('"+getName()+"0',"+validateStr+",'"+messageStr+"')");
            }
            if (view.getOnlyOne()!=null){
                String onlyOne=view.getOnlyOne();//是否唯一(1是，0否)
                if("1".equals(onlyOne)){
                    if (view.getFormName()!=null){
                        String tableName = getForm().getFormData().getTable_name().trim();
                        if(view.getName()!=null && tableName.length()>0){
                            String fieldName = view.getName().trim();
                            String sql = "select * from "+tableName+" where "+fieldName+" =?";
                            input.attr("onchange","textOnlyOne('"+getName()+"0','"+sql+"','"+mesgName+"')");
                        }
                    }
                }
            }
            String hintContent ="";
            if (view.getWapAttribute()!=null){
                String str=view.getWapAttribute();//获取样式
                String[] strs = str.split("\\[\\^\\]");
                boolean hasHintContent=false;
                String hintContenth=null;
               if (strs!=null){
                   for(int i=0;i<strs.length;i++){
                       if (strs[i].contains("style")){
                           int index = strs[i].indexOf(":");
                           if (index > 0) {
                               textStyle = strs[i].substring(index + 1);
                               input.styles(textStyle);
                           }
                       }
                       if (strs[i].contains("isEdit")){//是否禁用
                           String  num=strs[i].substring(strs[i].lastIndexOf(":")+i);
                           if (num.equals("0")){
                               input.attr("readonly","readonly");
                           }
                       }
                       if (strs[i].contains("border")){//是否显示文本框的边框
                           String  num=strs[i].substring(strs[i].lastIndexOf(":")+i);
                           if (num.equals("none")){
                               input.attr("style","border:none;");
                           }
                       }
                       if (strs[i].contains("hintContent")){
                           hintContent = strs[i].substring(strs[i].lastIndexOf(":")+i);
                       }
                   }
               }
            }

            if (hintContent !=null && hintContent.length()>0){ //提示
                input.placeholder(hintContent);
            }else{ //默认提示
                String num=view.getTitle().toString();
                if (view.getTitle().contains(":")){ //如果有：去掉
                    num=num.substring(0,num.length()-1);
                }
                input.placeholder("请输入"+num);
            }
            if (text!=null && optype!=null){
                input.value(text);
            }
            if(optype!=null && "2".equals(optype)){
                input.attr("readonly","readonly");
            }
            if (view.getShowState()!=null){
                input.value("");
            }
        }

        if(onclick != null){
            div.onClick(onclick);
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
