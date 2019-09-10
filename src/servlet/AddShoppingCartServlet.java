package servlet;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hy on 2017/12/27.
 */
public class AddShoppingCartServlet extends BaseServlet {
    String isLogin;
    String addCart;
    @Override
    protected void doHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        String params = req.getParameter("params");
        System.out.println("=====================================action===  ==========================="+action.toString());
        System.out.println("=====================================params=============================="+params.toString());
        String[] target = strSplit(action);
        if(target != null && target.length > 0){
            for(int i = 0;i < target.length;i++){
                System.out.println(target[i]);
                isLogin = target[0];
                addCart = target[1];
            }
        }
        resp.getWriter().write(isLogin);

    }

    public String[] strSplit(String str){
        if(str == null || "".equals(str))
            return new String[1];
        str = str.replace("[^]","#");
        return str.split("#");
    }

}