package service.impl;

import common.SQLHelper;

import dao.OaAttributeDao;
import service.FormFileAttribute;
import common.utils.TextUtils;
import model.OaAttribute;
import viewtype.View;

public class FormFileAttributeImpl implements FormFileAttribute {
    public OaAttributeDao oaAttributeDao=null;
    public  FormFileAttributeImpl(SQLHelper sqlHelper){
            oaAttributeDao=new OaAttributeDao(sqlHelper);

    }
    @Override
    public String getAttributeStr(View view, String companyId,String showType, String isPc,String theme) {
        OaAttribute oaAttribute=null;
        //1.判断是否是listForm
         if(TextUtils.equalsIgnoreCase("ListForm",showType)){

             //判断listAttributeId是否为null并且不等于""
             if(view.getListAttributeId()!=null && !TextUtils.equalsIgnoreCase("",view.getListAttributeId().trim())){
                 oaAttribute= oaAttributeDao.getOaAttributeById(companyId,Integer.parseInt(view.getListAttributeId()));


             }

          }
          //2.当OaAttribute在并未实例化，判断attributeId是否为空并且不等于""
          if(oaAttribute==null && view.getAttributeId()!=null && !TextUtils.equalsIgnoreCase("",view.getAttributeId().trim())){

              oaAttribute= oaAttributeDao.getOaAttributeById(companyId,Integer.parseInt(view.getAttributeId()));
          }
          //3.当OaAttribute在并未实例化，判断view的windowsAttribute或者wapAttribute是否为空并且不等于""
          if(oaAttribute==null &&
                  ((view.getWindowsAttribute()!=null && !TextUtils.equalsIgnoreCase("",view.getWindowsAttribute().trim())) ||
                          (view.getWapAttribute()!=null && !TextUtils.equalsIgnoreCase("",view.getWapAttribute().trim())))){
              oaAttribute=new OaAttribute();
              oaAttribute.setWapAttribute(view.getWapAttribute());
              oaAttribute.setWindowsAttribute(view.getWapAttribute());

          }
          //4.判断OaAttribute是否实例化
          if (oaAttribute==null) oaAttribute = new OaAttribute();

          String attributeStr=null;
          //5.判断是什么平台，0获取wapAttribute，1获取windowsAttribute
          if(TextUtils.equalsIgnoreCase(isPc,"0")){

              attributeStr=oaAttribute.getWapAttribute();
          }else if(TextUtils.equalsIgnoreCase(isPc,"1")){

              attributeStr=oaAttribute.getWindowsAttribute();
          }
          //6.判断attributeStr是否为空，为空,则获取主题样式
          if(attributeStr==null || TextUtils.equalsIgnoreCase(attributeStr.trim(),"")){
              attributeStr=oaAttributeDao.getAttributeByTheme(companyId,theme==null?companyId:theme,showType,view.getType(),isPc);
          }
        return attributeStr;
    }
}
