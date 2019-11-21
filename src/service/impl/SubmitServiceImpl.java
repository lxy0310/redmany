package service.impl;

import common.APPConfig;
import common.PropsUtil;
import common.SQLHelper;
import common.SQLUtil;
import dao.FormDao;
import dao.FormFiledDao;
import model.Form;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.SubmitService;
import viewtype.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubmitServiceImpl implements SubmitService {
    //获取Form
    private SQLHelper sqlHelper=null;
    private FormDao formDao=null;
    private HttpServletRequest request=null;
    private FormFiledDao formFiledDao=null;
    private String formName=null;
    private String showType=null;
    public  SubmitServiceImpl(HttpServletRequest req){
         request=req;
         sqlHelper=new SQLHelper(req);
         formDao=new FormDao(sqlHelper);
         formFiledDao=new FormFiledDao(sqlHelper);
    }

    @Override
    public Long doSubmit(String companyId) {
        Map<String,String> datas=new HashMap<>();
        Map<String,String> condition=new HashMap<>();


        //获取属性
        try {
            //创建磁盘文件项工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //创建核心上传对象
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            //获得所有的上传组件
            List<FileItem> list = fileUpload.parseRequest(request);
            for (FileItem fileItem : list) {
                //获得标签的name属性值
                String key=fileItem.getFieldName();
                String value=null;
                //判断是否是文件上传
                if(fileItem.isFormField()){
                    //普通上传组件
                    //获得普通上传组件的内容

                    value=fileItem.getString("utf-8");



                }else{
                    //文件上传组件
                    //获得文件上传组件的输入流
                    value= PropsUtil.updateOneFile(fileItem, APPConfig.DOCUMENT);
                }
                if ("Id".equalsIgnoreCase(key) ){
                    if(!"".equals(value.trim())){

                        condition.put("Id",value);
                    }

                }else{
                    if(datas.containsKey(key) && datas.get(key)!=null){
                        datas.put(key,datas.get(key)+","+value);
                    }else{
                        datas.put(key,value);
                    }
                }


            }
        } catch (FileUploadException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(!condition.containsKey("Id")){
            String Id=request.getParameter("Id")!=null?request.getParameter("Id"):request.getParameter("id");
            if(Id!=null && !"".equals(Id.trim())){
                condition.put("Id",Id);
            }

        }

         formName=datas.get("formName");
         showType=datas.get("showType");
        datas.remove("formName");
        datas.remove("showType");
        //获取sql语句
        Form submitForm=formDao.getForm(companyId,formName);
        List<View> viewList=formFiledDao.getFormContorl(companyId,formName,null);
        String type=condition.containsKey("Id")?"update":"insert";
        String sql= SQLUtil.getNonQuerySql(submitForm,viewList,datas,type,condition);
        //替换[userId]
        HttpSession session=request.getSession();
        Integer userid=-1;
        if(session.getAttribute("userId")!=null){
            userid=Integer.parseInt(session.getAttribute("userId").toString());
        }
        if(sql.indexOf("[userid]")>=0){
            sql=sql.replace("[userid]",userid+"");
        }
        long count=0;
        if("insert".equals(type)){
            count=sqlHelper.ExecuteInsertGetId(companyId,sql,null);
        }else{
            count =sqlHelper.ExecuteNonQuery(companyId,sql,null);

        }


        return count;
    }
}
