package page;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.head.Script;
import commandCenter.CommandCenter;
import common.utils.DataHelper;
import common.utils.TextUtils;
import dao.OaCopModelDao;
import model.ReplaceModel;
import servlet.StudentServlet;
import showtype.ParentForm;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

/**
 * 组合页面
 */
public class CopPage extends Page {

    private ReplaceModel replaceModel;

    protected List<HomeForm> getForms() {
        return mainDao.getForm(getCompany_Id(), getCopformName(), getShowType());//获取配置模板
    }

   /* protected List<>*/

    private OaCopModelDao mOaCopModelDao;

    @Override
    public void init(StudentServlet pServlet, String copformName, String showType, String title) {
        super.init(pServlet, copformName, showType, title);
        mOaCopModelDao = new OaCopModelDao(getSQLHelper());
    }

    @Override
    public String getTitle() {
        String title = super.getTitle();
        if (!TextUtils.isEmpty(title)) {
            return title;
        }
        return DataHelper.toString(mOaCopModelDao.getTitle(getCompany_Id(), getCopformName()), "");
    }

    @Override
    public void writeBody(HtmlBodyElement<?> body) {
        super.writeBody(body);
        writeForms(body);

        if ("1".equals(getCookieValue("show_order_add_ok"))) {
            saveCookie(new Cookie("show_order_add_ok", "0"), true);
            Script script = new Script();
            script.text("alert('支付成功');");
            body.add(script);
        }
    }

    protected void writeForms(HtmlBodyElement<?> body) {
        List<String> views = new ArrayList<>();
        replaceModel = mainDao.getFormMenuHtml(getCompany_Id(),getCopformName());
        String html = null;
        if (replaceModel!=null){
            html = replaceModel.getHtml_template();
        }
        List<HomeForm> datas = getForms();//获取配置模板
        if (Page.LOG) {
            System.out.println(getCopformName() + "/" + getShowType() + "=" + datas);
        }
        Div div = body.div();
        div.id("main");
        for (int i = 0; i < datas.size(); i++) {
            HomeForm homeForm = datas.get(i);
            System.out.println(datas.get(i).getCopFormName());
            if (!showForm(homeForm)) {
                continue;
            }
            if (Page.LOG) {
                System.out.println("add form " + homeForm.getCopFormName() + "/" + homeForm.getShowType());
            }

            ParentForm form = CommandCenter.compositeTemplate(getCompany_Id(), homeForm.getShowType(), homeForm.getCopFormName());//获取最终页面

            if (form != null) {
                form.setPage(this);
                HtmlBodyElement element = getHtml(form);
                String key = "##" + form.getFormName();
                if (html != null && html.contains(key)) {
                    html = html.replace(key, element.toString());
                } else {
                    views.add(element.toString());
                }
            }
        }

        if (!TextUtils.isEmpty(html)) {
            div.text(html);
        }
        for (String str : views) {
            div.text(str);
        }
    }

    protected boolean showForm(HomeForm form) {
        return true;
    }
}
