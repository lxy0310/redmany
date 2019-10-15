package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.embed.Img;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.sections.A;
import com.sangupta.htmlgen.tags.body.text.Label;

public class Video extends ParentView {
    @Override
    public String getType() {
        return "Video";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        String text = getDataProvider().getText(this, getForm());
        String optype = getPage().getParameter("optype");//修改1查看2
        Div div = new Div();
        div.id(getName());
        if (getView()!=null){
            View view=getView();
            if(view.getIsTitle()!=null && "1".equals(view.getIsTitle())) {//不长title
                if(text!=null && text.length()>0){
                    Input hidden = div.input("hidden","http://oa.redmany.com:50016/document/LSQ_20190505_161704372.mp4");
                    hidden.id(getName()+"_hidden");
                    com.sangupta.htmlgen.tags.body.sections.Video video = div.video("http://oa.redmany.com:50016/document/LSQ_20190505_161704372.mp4");
                    video.attr("width","30px");
                    video.attr("height","30px");
                    video.onClick("openVideo('"+getName()+"_hidden')");
                }
                return div;
            }else{
                Label label = div.label();
                label.text(view.getTitle()==null?"":view.getTitle());
            }
        }

        A a1 = div.a();
        a1.herf("javascript:;");
        a1.addCssClass("file");
        a1.text("上传视频");
        a1.onClick("upload_a('"+getName()+"0');");

        Input input = a1.input();
        input.id(getName()+"0");
        input.type("file");
        input.title("");
        input.name(getName());
        input.attr("accept","video/*");
        input.onChange("uploadVideo(this,'"+getName()+"')");

        Div div2 = div.div();
        div2.id(getName()+"_div");
        div2.attr("style","display:inline-block; position:relative;");

        if(optype!=null && optype.length()>0){
            if(text!=null && text.length()>0){
                Input hidden = div2.input("hidden","http://oa.redmany.com:50016/document/LSQ_20190505_161704372.mp4");
                hidden.id(getName()+"_hidden");
                com.sangupta.htmlgen.tags.body.sections.Video video = div2.video("http://oa.redmany.com:50016/document/LSQ_20190505_161704372.mp4");
                video.attr("width","50px");
                video.attr("height","50px");
                video.onClick("openVideo('"+getName()+"_hidden')");
            }
            if("1".equals(optype)){
                A delA = div2.a();
                delA.herf("javascript:void(0);");
                delA.id(getName()+"_a");
                Img delImg = delA.img("/redmany/images/delete.jpg");
                delImg.attr("style","position: absolute; height: 15px;width: 15px;top: 0px; right: 0px;");
                delImg.onClick("delFile('"+getName()+"','video')");
            }else if("2".equals(optype)){
                input.attr("disabled","disabled");
            }
        }
        return div;
    }
}