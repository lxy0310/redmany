package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.head.Link;
import com.sangupta.htmlgen.tags.head.Script;

public class MultiImage extends ParentView {
    @Override
    public String getType() {
        return "MultiImage";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        /**
         * <div class="multi-imgs">
         <div id="files" class="div-files">
         <input type="file" id="img-1" name="img-1" style="display:none;" onchange='preImage(this, 1);'/>
         </div>
         <div id="add-div">
         <input type="button" id="btn-add" onclick="chooseImage();" value="添加"/>
         </div>
         </div>
         */
        Div div = new Div();
        div.id(getName()).addCssClass("multi-imgs");
        div.add(new Link().href("css/multi_image.css?v=2").rel("stylesheet"));
        div.add(new Script().src("js/multi_image.js?v=2"));

        Div inputs = div.div().id("files").addCssClass("div-files");
        inputs.input("file", null).idAndName("img-1").styles("display: none;").onChange("preImage(this, 1);");

        Div adddiv = div.div().id("add-div");
        adddiv.div().id("btn-add").onClick("chooseImage();").img("images/bzservice_add_icon.png");
        //隐藏
//        div.script("var remotePre='" + ParentView.IMAGE_PRE + "';var localPre='" + ParentView.IMAGE_UPLOAD + "';");
        return div;
    }
}
