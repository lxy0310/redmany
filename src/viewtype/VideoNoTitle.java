package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.sections.Video;
import common.CommonUtils;

/**
 * Created by Administrator on 2018/4/4 0004.
 */
public class VideoNoTitle extends ParentView {
    public static String IMAGE_PRE = CommonUtils.getFileData;
    @Override
    public String getType() {
        return "VideoNoTitle";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        Div div = new Div();
        div.id(getName());
        String videoSrc = getDataProvider().getText(this, getForm());
        Video video = div.video(IMAGE_PRE+videoSrc);
        video.attr("autoplay","true").addCssClass("productVideo");
        return div;
    }
}

