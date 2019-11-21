package servlet;

import com.alibaba.fastjson.JSON;
import common.APPConfig;
import common.CommonUtils;
import common.PropsUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UploadFileServlet extends BaseServlet {
    protected void doHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
    }

    /*
    单个文件上传的处理
    返回json串
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();//输出
        Map<String,Object> map = new HashMap<String,Object>();
        FileItemFactory fif = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(fif);
        String type = request.getParameter("type");
        try {
            List<FileItem> items = upload.parseRequest(request);
            boolean returnOld = false;//是否要返回原文件名
            for (FileItem item : items) {
                boolean isForm = item.isFormField();
                if(isForm){
                    String name = item.getFieldName();
                    if(name!=null && name.length()>0 && "type_file".equals(name)){
                        returnOld = true;
                    }
                }
                if (!isForm) {
                    String sourceFileName = item.getName();// 得到文件名 xxx.jpg
                    String uploadFilePath = CommonUtils.imgUploadPath;// 得到上传到服务器上的文件路径
                    String saveName = "";
                    if(returnOld){
                        saveName =  PropsUtil.updateFileOldName(item,uploadFilePath);//原文件名
                    }else{
                        saveName =  PropsUtil.updateOneFile(item,uploadFilePath);//新文件名
                    }
                    if(saveName!=null && saveName.length()>0){//上传成功
                        map.put("url", CommonUtils.getFileData+"01f8503c9a264f838ed5a7d7f786c01c.jpg");//测试
//                    map.put("url", CommonUtils.getFileData+uuidName);//正式
                        map.put("title", sourceFileName);//文件原名称
                        map.put("width", "50px");
                        map.put("state", "SUCCESS");//是否上传成功
                        map.put("name", saveName);
                        map.put("error",0);
                    }else{
                        map.put("error",1);
                    }
                }
            }
            if (map != null || map.size() > 0) {
                out.print(JSON.toJSONString(map));
                return;
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        return;
    }

}
