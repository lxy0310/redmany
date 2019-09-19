package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.embed.Img;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.text.Label;
import com.sangupta.htmlgen.tags.body.text.Span;
import common.ApiParser;
import common.utils.HttpUtils;
import common.utils.SafeString;
import model.ShopCarPageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hy on 2017/11/3.
 */
public class Text extends ParentView {
    @Override
    public String getType() {
        return "Text";
    }
   // private List<ShopCarInfo> mShopCarItemInfos = new ArrayList<>();

    public static List<ShopCarPageInfo> getShopCarItemInfos(int userId) {
        String url = ApiParser.getShopCarUrl(userId, null);
        String rs = HttpUtils.get(url);
        if (rs != null) {
            System.err.println(rs);
            return ApiParser.parseList(rs, ShopCarPageInfo.class);
        }
        return new ArrayList<>();
    }

    String enUrl;

    protected void loadData(String sql) {
//        super.loadData(sql);
     //   mShopCarItemInfos = getShopCarItemInfos(getPage().getAccountHelper().getUserId());
        enUrl = SafeString.escape(SafeString.encode(getPage().getUrl()));

    }

    @Override
    protected HtmlBodyElement<?> create() {
        Span span = new Span();
        span.id(getName());
       /* System.out.println(getName());//UserName
        System.out.println(getPage());//SinglePage{copformName='BondModifyPersonDate', showType='freeForm'}
        System.out.println(getFormName());//BondModifyPersonDate*/
        String styles = getDataProvider().getStyles(this, getForm());
        String css = getDataProvider().getCssClass(this, getForm());
        String text = getDataProvider().getText(this, getForm());//input值
        String textDesc = getDataProvider().getTextDesc(this, getForm());
        String isEdit =getDataProvider().getTextEdit(this,getForm());//是否禁用
        String txtName =getDataProvider().getTextName(this,getForm());
        String placeholder =getDataProvider().getHintContent(this,getForm());//提示
        String color = getDataProvider().getTextColor(this, getForm());
        String onclick = getDataProvider().getOnClick(getForm(),this, getView().getTarget(), getView().getTransferParams());
        //View{Index_number=0, Type='text', target='setReadonly()', attributeId='null', attributeStr='style:padding-right:100px;pointer-events:none;',
        // wapAttribute='style:padding-right:100px;pointer-events:none;', windowsAttribute='null',
        // iosAttribute='null', androidAttribute='null', transferParams='null', Name='UserName', Title='用户邮箱', ValidateExpreesion='null', ValidateErrorMessage='null'}
        if (getView()!=null){
            View view=getView();
            System.out.println(view);
            if (view.getTitle()!=null){ //title
               if (view.getTitle().contains(".")){ //图片
                   String titles=view.getTitle().substring(view.getTitle().indexOf(".")+1);
                   if (titles.equals("png") || titles.equals("jpg") || titles.equals("gif")){
                       Img img=span.img(IMAGE_PRE+titles);
                       img.addCssClass(getName());
                       img.src(IMAGE_PRE+view.getTitle());
                       img.width(30);
                       img.height(30);
                   }
               }else { //文本
                  // Label label = span.label();
                 //  label.addCssClass(getName());
                 //  label.text(view.getTitle());
               }
            }
            Input input =span.input();
            input.id(getName()+'0');
           // input.addCssClass(getName());
            if (view.getDatabase_field()!=null) {   //Database_field
                input.addCssClass(view.getDatabase_field().trim());
            }else{
                input.addCssClass(getName().trim());
            }
            input.name(getName());
            if (view.getWapAttribute()!=null){
                /*Input input =span.input();
                input.id(getName()+'0');
                input.addCssClass(getName());*/

                String str=view.getWapAttribute();//获取样式
                String[] strs = str.split("\\[\\^\\]");
              /* for(int i=0;i<strs.length;i++){
                    System.out.println("值:"+i+strs[i]);
                }*/
               if (strs!=null){
                if (strs[0].contains("isEdit")){ //是否禁用
                    String  num=strs[0].substring(strs[0].lastIndexOf(":")+1);
                    // pointer-events:none; 禁用鼠标点击事件
                    if (num.equals("0")){
                        input.attr("readonly","readonly");
                    }
                    System.out.println(num);
                }

//                if (strs[1].contains("hintContent")){ //提示
//                    String  num=strs[1].substring(strs[1].lastIndexOf(":")+1);
//                    if (num!=null){
//                        System.out.println("num"+num);
//                        input.placeholder(num);
//                    }
//                    System.out.println(num);
//                }else{ //默认提示
//                  String num=view.getTitle().toString();
//                  if (view.getTitle().contains(":")){ //如果有：去掉
//                      num=num.substring(0,num.length()-1);
//                  }
//                    input.placeholder("请输入"+num);
//                }

               }
                System.out.println();
            }
            if (text!=null){
                input.value(text);
            }
            if (view.getShowState()!=null){
                input.value("");
            }


        }
        if(onclick != null){
            span.onClick(onclick);
        }

       /* if(text!=null){

            if(textDesc!=null){
                text=textDesc+":"+text;
            }
            Input input =span.input();
            input.id(getName()+'0');
            input.addCssClass(getName());

            if(placeholder!=null){
                input.placeholder(placeholder);
            }

            input.value(text);
        }else if(text==null) {
            if(textDesc!=null){
                text=textDesc+":";
            }
            span.text(text);
        }*/

/*        if(text!=null){

            if(textDesc!=null){
                text=textDesc+":"+text;
            }
            span.text(text);
        }else if(text==null) {
            if(textDesc!=null){
                text=textDesc+":";
            }
            span.text(text);
        }*/

   /*    if(isEdit!=null && isEdit.equals("1")){
            if(txtName!=null){
                Span name =span.span();
                name.addCssClass("edit-"+getName());
                name.text(txtName);
            }
            Input input =span.input();
            input.id("keyword");
            input.addCssClass(getName()+"-ipt");
            if(placeholder!=null){
                input.placeholder(placeholder);
            }
        }*/

        if (color != null) {
            span.style("color", color);
        }
        if (styles != null) {
            span.styles(styles);
        }
        if (css != null) {
            span.addCssClass(css);
        }

/*        if("phone".equals(getName())){

            Input input = span.input();
            input.type("text").placeholder("请填写预留手机号");
//            com.sangupta.htmlgen.tags.body.forms.Button btn = span.button("保存").style("background","blue");

        }*/
       /* span.add(new Script("js/jquery-1.4.2.js"));*/
        // 拿到值
      /*  span.add(new Script().text("\n"+
                " $(document).ready(function(){\n" +
                "$('input').bind('input propertychange', function() {\n"+
                    "alert(\"bbb\");\\n"+
                    "var input1 = $(\"#\"+getName()+\"0\").val();"+

                "});"+
                "$(\"#"+getName()+"0\").blur(function(){\n"+

                "});\n"+
                "});\n"
        ));*/
        return span;
    }
}
