package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hy on 2018/1/22 0022.
 */
public class PopupServlet extends BaseServlet {
    @Override
    protected void doHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html; charset=gb2312");
        out.println("<html><head>");
        out.println("<script type='text/javascript' src='ZsSuit.js'></script>");
        out.println("</head>");
        out.println("<body>");
        out.println("<script type='text/javascript' src='jquery-1.8.1.js'></script>");
        out.println("d = new dTree('d');");
        out.println("d.add(0,-1,'My example tree');");
        out.println("d.add(1,0,'Node 1','#');");
        out.println("document.write(d);");
        out.println("</script>");
        out.println("</body>");
        out.println("</html>");
    }

}
