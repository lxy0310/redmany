package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

public class PhoneCodeServlet extends BaseServlet {
    private Random mRandom = new Random(System.currentTimeMillis());

    @Override
    protected void doHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phone = req.getParameter("phone");
        String code = String.format("%04d", mRandom.nextInt(9999));
//        saveCookie("phone-code", code, true);

        String msg = "感谢注册etp服务，您的验证码是：" + code;
        String api = "http://web.cr6868.com/asmx/smsservice.aspx?" +
                "name=17808277888&pwd=bz-5155bz-5155&content=" + urlEncode(msg) +
                "&mobile=" + phone + "&sign=testsign&type=pt&extno=";
        String result = openUrl(api);
        System.err.println(result);
        resp.getWriter().write("{code:'" + code + "',status:2}");
    }
}
