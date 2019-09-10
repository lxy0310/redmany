package viewtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.text.Label;
import com.sangupta.htmlgen.tags.head.Script;
import common.BaiduLocation;
import common.utils.SafeString;
import common.utils.TextUtils;
import page.Page;

public class AreaSelect extends ParentView {
    @Override
    public String getType() {
        return "AreaSelect";
    }

    @Override
    protected HtmlBodyElement<?> create() {
        Div icon = new Div();
        icon.id(getName());
        String text = getDataProvider().getText(this, getForm());
//        icon.label(text).id("loc-city");
        Label loc = icon.label(text).id("loc-city");
//        icon.img("images/buy_address.png").onClick("loc('loc-city');");
        String onclick = getDataProvider().getOnClick(getForm(),this, getView().getTarget(), getView().getTransferParams());
        if (onclick != null) {
            icon.onClick(onclick);
        } else {
            icon.onClick("searchAera();");
        }
        String city = SafeString.unescape(getPage().getCookieValue(Page.LOC_ADDRESS_SHORT));
        boolean needLoc = false;
        if (!TextUtils.isEmpty(city)) {
            loc.text(city);
        } else {
            BaiduLocation.LocationData data = BaiduLocation.getLocationByIp(getPage().getIP());
            BaiduLocation.saveLocation(data, getPage().getHttpServletResponse());
            city = data.getShortName();
            if (TextUtils.isEmpty(city)) {
                loc.text("城市选择");
                needLoc = true;
            } else {
                loc.text(city);
            }
        }
        if (needLoc) {
            icon.add(new Script().text("checkLoc('loc-city');"));
        }
        return icon;
    }
}
