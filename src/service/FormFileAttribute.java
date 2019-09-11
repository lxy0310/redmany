package service;

import viewtype.View;

/**
 * 处理FormFiled的样式配置的接口
 */
public interface FormFileAttribute {

    /**
     * 根据View(FormFiled的数据集)，模板类型，显示平台类型，获取相应的样式（添加到控件上的style上的行内样式）
     * @param view   FormFiled的数据集
     * @param companyId  企业Id
     * @param showType   显示模板(类型),如ListForm
     * @param platform  前端还是后台
     * @return  控件的相应的行内样式
     */
    public String getAttributeStr(View view,String companyId,String showType,String platform);
}
