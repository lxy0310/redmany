package page;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.head.Script;
import commandCenter.CommandCenter;
import common.utils.TextUtils;
import servlet.StudentServlet;
import showtype.ParentForm;

import javax.servlet.http.Cookie;

/**
 * 单一页面
 */
public class SinglePage extends Page {

    private ParentForm mForm;


    @Override
    public void init(StudentServlet pServlet, String copformName, String showType, String title) {
        super.init(pServlet, copformName, showType, title);
        mForm = getForm();

        if (LOG && mForm != null) {
            System.out.println(getClass().getSimpleName() + ":form=" + mForm.getFormData());
        }
    }

    @Override
    public String getTitle() {
        String title = super.getTitle();
        if (!TextUtils.isEmpty(title)) {
            return title;
        }
        if (mForm != null && mForm.getFormData() != null) {
            return mForm.getFormData().getTitle();
        }
        return "";
    }

    @Override
    public void writeBody(HtmlBodyElement<?> body) {
        super.writeBody(body);
        body.add(getHtml(mForm));

        if ("1".equalsIgnoreCase(getCookieValue("login_fail"))) {
            saveCookie(new Cookie("login_fail", null), true);
            Script script = new Script();
            script.text("alert('登录失败');");
            body.add(script);
        }
    }

    protected ParentForm getForm() {
        System.out.println("start getForm...");
        ParentForm form = CommandCenter.compositeTemplate(getCompany_Id(), getShowType(), getCopformName());

        System.out.println("end getForm..."+form);
        if (form != null) {
            form.setPage(this);
        }
        return form;
    }


}
