package common;

import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传工具类
 * @author  Arios
 */
public class PropsUtil {

    /**
     * 检查是否是相应的文件
     * @param fileName 文件名
     * @param suffixes  扩展名的List集合
     * @return  true-是相应文件 false-不是相应文件
     */
      public static boolean CheckFileSuffixes(String fileName, List<String> suffixes){

          if(fileName!=null && fileName.lastIndexOf(".")>0){
              String fileSuffix=fileName.substring(fileName.lastIndexOf(".")+1);


              for (String suffix:
                   suffixes) {

                   if(suffix!=null && fileSuffix.equalsIgnoreCase(suffix)){

                       return true;
                   }
              }
          }


          return  false;
      }

    /**
     * 检查是否是图片,包括"png","jpg","jpeg","gif","bmp"
     * @param imageName 图片名
     * @return true-是图片 false-不是图片
     */
      public  static boolean CheckImageSuffixes(String imageName){
              List<String> suffixes= Arrays.asList("png","jpg","jpeg","gif","bmp");
              return  CheckFileSuffixes(imageName,suffixes);
      }

      /**
     * 文件重命名
     * @param fileName 文件名
     * @return 返回生成的文件名
     */
      public static String renameToUUID(String fileName) {
          if(fileName==null){
              return null;
          }
        return UUID.randomUUID().toString().replace("-","") + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
     }
    /**
     * 文件上传
     * @param fileItem 文件元素
     * @param savePath 文件保存路径
     * @return 成功返回上传的文件名
     */

     public static  String updateOneFile(FileItem fileItem,String savePath){
         //判断是否是文件表单字段
          if (fileItem!=null && !fileItem.isFormField()){
              //获取文件名
              String fileName=fileItem.getName();
              //获取新的文件名
              String uuidName=renameToUUID(fileName);




               //
           /*   if(savePath!=null){
              File file=new File( savePath);
              if(!file.exists() && file.isDirectory()){

                  file.mkdirs();
              }

              }*/
              try {
                  fileItem.write(new File(savePath,uuidName));
                  return uuidName;
              } catch (Exception e) {
                  e.printStackTrace();
                  return  null;
              }

          }
          return  null;

     }

}
