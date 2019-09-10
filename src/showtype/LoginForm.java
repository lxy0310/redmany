package showtype;

import com.sangupta.htmlgen.core.HtmlBodyElement;
import com.sangupta.htmlgen.tags.body.forms.Form;
import com.sangupta.htmlgen.tags.body.forms.Input;
import com.sangupta.htmlgen.tags.body.grouping.Div;
import com.sangupta.htmlgen.tags.body.text.Label;

/**
 * Created by hy on 2017/11/13.
 */
public class LoginForm extends ParentForm {

    public HtmlBodyElement<?> createViews() {
        Form form = new Form();
        form.addCssClass("form-horizontal");
        form.method("POST");
        form.action("login");
        Div name = form.div();
        name.addCssClass("form-group");
        name.label("用户名").id("label-name").addCssClass("control-label");
        Input input = name.input().type("text");
        input.required().idAndName("userName").addCssClass("form-control");

        Div pwd = form.div();
        pwd.addCssClass("form-group");
        pwd.label("密码").id("label-pwd").addCssClass("control-label");
        pwd.input().type("password").required().idAndName("passWord").addCssClass("form-control")
                .attr("placeholder", "至少6位密码");

       /* Div msg=form.div();
        msg.addCssClass("form-group");
        pwd.label("验证码").id("label-pwd").addCssClass("control-label");
        pwd.input().type("PhoneValidate").required().idAndName("PhoneValidate").addCssClass("form-control")
                .attr("placeholder", "验证码错误");*/


        Div rm = form.div();

        Label label = rm.label();
        label.input("checkbox", "true").idAndName("reme");
        label.text("记住我");
        rm.input("submit", "登录").idAndName("login").addCssClass("btn btn-primary ");
        rm.input("button", "注册").idAndName("reg").addCssClass("btn btn-success")
                .onClick("gotoPage('goto:bondRegMember,freeForm',null);");
        return form;

    }
}
