package service.impl;

import service.FormFileAttribute;
import common.utils.TextUtils;
import model.OaAttribute;
import viewtype.View;

public class FormFileAttributeImpl implements FormFileAttribute {

    @Override
    public String getAttributeStr(View view, String companyId,String showType, String platform) {
        OaAttribute oaAttribute=null;
        //1.判断是否是listForm
         if(TextUtils.equalsIgnoreCase("ListForm",showType)){

             //判断listAttributeId是否为null并且不等于""
             if(view.getListAttributeId()!=null && TextUtils.equalsIgnoreCase("",view.getListAttributeId().trim())){



             }

          }
          //2.当OaAttribute在并未实例化，判断attributeId是否为空并且不等于""
          if(oaAttribute==null && view.getAttributeId()!=null && TextUtils.equalsIgnoreCase("",view.getAttributeId().trim())){


          }
          //3.当OaAttribute在并未实例化，判断view的windowsAttribute或者wapAttribute是否为空并且不等于""
          if(oaAttribute==null &&
                  ((view.getWindowsAttribute()!=null && TextUtils.equalsIgnoreCase("",view.getWindowsAttribute().trim())) ||
                          (view.getWapAttribute()!=null && TextUtils.equalsIgnoreCase("",view.getWapAttribute().trim())))){


          }
          //4.判断OaAttribute是否实例化
          if (oaAttribute==null){
              oaAttribute=new OaAttribute();
          }

          String attributeStr=null;
          //5.判断是什么平台，0获取wapAttribute，1获取windowsAttribute
          if(TextUtils.equalsIgnoreCase(platform,"0")){

              attributeStr=oaAttribute.getWapAttribute();
          }else if(TextUtils.equalsIgnoreCase(platform,"1")){

              attributeStr=oaAttribute.getWindowsAttribute();
          }
          //6.判断attributeStr是否为空，为空根据固有模板获取相应的属性

        return null;
    }
}
