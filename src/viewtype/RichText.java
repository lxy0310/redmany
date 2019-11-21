package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.text.Label;
import com.sangupta.htmlgen.tags.body.text.TextArea;
import com.sangupta.htmlgen.tags.head.Script;
import common.ApiParser;
import common.CommonUtils;
import common.utils.HttpUtils;
import common.utils.SafeString;
import model.ShopCarPageInfo;

import java.util.ArrayList;
import java.util.List;

public class RichText extends ParentView {
    @Override
    public String getType() {
        return "RichText";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        String text = getDataProvider().getText(this, getForm());
        String optype = getPage().getParameter("optype");//修改1查看2
        Div div = new Div();
        div.id(getName());
        div.name(getName());
        div.style("margin","0 auto");
        div.attr("wideh","500px");
        div.attr("height","360px");
        if (getView()!=null){
            View view=getView();
            if(view.getIsTitle()!=null && "1".equals(view.getIsTitle())) {//不长title
                div.text(text==null?"":"......");
                return div;
            }else{
                Label label = div.label();
                label.text(view.getTitle()==null?"":view.getTitle());
            }
        }
        String scriptText = "";
        text = text==null?"":text;
        if(optype!=null && optype.length()>0){
            if("1".equals(optype)){
                scriptText = "var ue = UE.getEditor('"+getName()+"'); "+ CommonUtils.editorStr +
                        ", setTimeout(function(){ue.setContent('"+text+"',false)},150);";//修改
            }else if("2".equals(optype)){
                scriptText = "var ue = UE.getEditor('"+getName()+"'); "+ CommonUtils.editorStr +
                ", setTimeout(function(){ue.setContent('"+text+"',false)},150); ue.ready(function() {ue.setDisabled(); });";//查看
            }
        }else{
            scriptText = "var ue = UE.getEditor('"+getName()+"'); "+ CommonUtils.editorStr;//新增
        }
        div.add(new Script().text(scriptText));
        return div;
    }
}

//<p><img src="/redmany/ueditor/jsp/upload/image/20190929/1569753737596008905.jpg" title="小蛋糕副本.jpg" alt=""/></p>