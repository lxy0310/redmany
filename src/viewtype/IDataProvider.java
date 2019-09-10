package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import showtype.ParentForm;

import java.util.Map;

public interface IDataProvider {

    String getImageUrl(ParentView view, ParentForm baseForm);

    String getText(ParentView view, ParentForm baseForm);

    String getTextDesc(ParentView view, ParentForm baseForm);

    String getHintContent(ParentView view, ParentForm baseForm);

    String getTextEdit(ParentView view, ParentForm baseForm);

    String getTextName(ParentView view, ParentForm baseForm);

    String getOptLabel(ParentView view, ParentForm baseForm);

    String getOptVal(ParentView view, ParentForm baseForm);

    String getTextColor(ParentView view, ParentForm baseForm);

    String getStyles(ParentView view, ParentForm baseForm);

    String getCssClass(ParentView view, ParentForm baseForm);

    HtmlBodyElement<?> onCompleted(ParentView view, HtmlBodyElement<?> element);

    String getOnClick(ParentForm baseForm, ParentView parentView, Map<String, Object> map);

    String getOnClick(ParentForm baseForm, ParentView parentView, String target, String transferParams);

//    String getHintContent(ParentView view, ParentForm baseForm);
}
