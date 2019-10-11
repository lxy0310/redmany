package servlet;

import common.APPConfig;
import common.PropsUtil;
import common.SQLHelper;
import dao.FormDao;
import dao.FormFiledDao;
import model.Form;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.SubmitService;
import service.impl.SubmitServiceImpl;
import viewtype.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubmitServlet extends BaseServlet {

    @Override
    protected void doHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          // String showType=req.getParameter("showType");
         ///  String formName=req.getParameter("formName");
        resp.setCharacterEncoding("utf-8");
        PrintWriter out=resp.getWriter();
        SubmitService submitService=new SubmitServiceImpl(req);
        boolean flag=submitService.doSubmit(getCompany_Id());
         if(flag){
               out.print("提交成功");
         }else{
             out.print("提交失败");
         }
    }
}
