package viewtype;

/**
 * Created by hasee on 2017/10/21.
 */
public class View {
    /*控件序号，添加顺序*/
    private Integer Index_number;
    /*控件类型*/
    private String Type;
    /*控件事件*/
    private String target;
    /*控件属性id*/
    private String attributeId;
    /*控件属性值*/
    private String attributeStr;

    private String wapAttribute;
    private String iosAttribute;
    private String androidAttribute;
    private String windowsAttribute;
    private  String listAttributeId;
    private String transferParams;

    /*名字*/
    private String Name;
    /*title*/
    private String Title;
    /* 正则表达式条件*/
    private String ValidateExpreesion;
    /*正则表达式提示*/
    private String ValidateErrorMessage;
    /**是否为空  1不为空 0 可以为空*/
    private String IsNull;
    /*是否要收集数据*/
    private String isDataItem;
    /*输入的值*/
    private String value;
    /*保存数据字段名*/
    private String Database_field;
    /*是否显示*/
    private String showState;
    /**是否唯一  1是 0否*/
    private String onlyOne;
    private String FormName;
    private String data_replacer;
    private String isTitle="0"; //是否传标题   默认长标题0 ，不长1
    private String isValue="0"; //是否有value   默认有value 0 ，没有value 1
    private String isDouble="0"; // 默认为0显示一个  1 显示两个
    private String isReadonly = "0";//默认为0 可以编辑，1 不可以编辑


    public String getIsValue() {
        return isValue;
    }

    public void setIsValue(String isValue) {
        this.isValue = isValue;
    }

    public View() {
    }

    @Override
    public String toString() {
        return "View{" +
                "Index_number=" + Index_number +
                ", Type='" + Type + '\'' +
                ", target='" + target + '\'' +
                ", attributeId='" + attributeId + '\'' +
                ", attributeStr='" + attributeStr + '\'' +
                ", wapAttribute='" + wapAttribute + '\'' +
                ", iosAttribute='" + iosAttribute + '\'' +
                ", androidAttribute='" + androidAttribute + '\'' +
                ", windowsAttribute='" + windowsAttribute + '\'' +
                ", listAttributeId='" + listAttributeId + '\'' +
                ", transferParams='" + transferParams + '\'' +
                ", Name='" + Name + '\'' +
                ", Title='" + Title + '\'' +
                ", ValidateExpreesion='" + ValidateExpreesion + '\'' +
                ", ValidateErrorMessage='" + ValidateErrorMessage + '\'' +
                ", IsNull='" + IsNull + '\'' +
                ", isDataItem='" + isDataItem + '\'' +
                ", value='" + value + '\'' +
                ", Database_field='" + Database_field + '\'' +
                ", showState='" + showState + '\'' +
                ", onlyOne='" + onlyOne + '\'' +
                ", FormName='" + FormName + '\'' +
                ", data_replacer='" + data_replacer + '\'' +
                ", isTitle='" + isTitle + '\'' +
                ", isValue='" + isValue + '\'' +
                ", isDouble='" + isDouble + '\'' +
                ", isReadonly='" + isReadonly + '\'' +
                ", filedGroup='" + filedGroup + '\'' +
                '}';
    }

    public String getIsReadonly() {
        return isReadonly;
    }

    public void setIsReadonly(String isReadonly) {
        this.isReadonly = isReadonly;
    }

    public String getIsDouble() {
        return isDouble;
    }

    public void setIsDouble(String isDouble) {
        this.isDouble = isDouble;
    }

    public String getIsTitle() {
        return isTitle;
    }

    public void setIsTitle(String isTitle) {
        this.isTitle = isTitle;
    }

    public String getData_replacer() { return data_replacer; }

    public void setData_replacer(String data_replacer) {
        this.data_replacer = data_replacer;
    }

    public String getFormName() { return FormName; }
  
    public void setFormName(String FormName) {
        this.FormName = FormName;
    }

    private String filedGroup;

    public String getFiledGroup() {
        return filedGroup;
    }

    public void setFiledGroup(String filedGroup) {
        this.filedGroup = filedGroup;
    }

    public String getOnlyOne() {
        return onlyOne;
    }

    public void setOnlyOne(String onlyOne) {
        this.onlyOne = onlyOne;
    }

    public String getShowState() { return showState; }

    public void setShowState(String showState) { this.showState = showState; }

    public String getDatabase_field() {
        return Database_field;
    }

    public void setDatabase_field(String database_field) {
        Database_field = database_field;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getWapAttribute() {
        return wapAttribute;
    }

    public void setWapAttribute(String wapAttribute) {
        this.wapAttribute = wapAttribute;
    }
    public String getWindowsAttribute() {
        return windowsAttribute;
    }

    public void setWindowsAttribute(String windowsAttribute) {
        this.windowsAttribute = windowsAttribute;
    }

    public String getIosAttribute() {
        return iosAttribute;
    }

    public void setIosAttribute(String iosAttribute) {
        this.iosAttribute = iosAttribute;
    }

    public String getAndroidAttribute() {
        return androidAttribute;
    }

    public void setAndroidAttribute(String androidAttribute) {
        this.androidAttribute = androidAttribute;
    }

    public String getTransferParams() {
        return transferParams;
    }

    public void setTransferParams(String transferParams) {
        this.transferParams = transferParams;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getAttributeStr() {
        return attributeStr;
    }

    public void setAttributeStr(String attributeStr) {
        this.attributeStr = attributeStr;
    }

    public Integer getIndex_number() {
        return Index_number;
    }

    public void setIndex_number(Integer index_number) {
        Index_number = index_number;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getValidateExpreesion() {
        return ValidateExpreesion;
    }

    public void setValidateExpreesion(String validateExpreesion) {
        ValidateExpreesion = validateExpreesion;
    }

    public String getValidateErrorMessage() {
        return ValidateErrorMessage;
    }

    public void setValidateErrorMessage(String validateErrorMessage) {
        ValidateErrorMessage = validateErrorMessage;
    }

    public String getIsNull() {
        return IsNull;
    }

    public void setIsNull(String isNull) {
        IsNull = isNull;
    }

    public String getIsDataItem() {
        return isDataItem;
    }

    public void setIsDataItem(String isDataItem) {
        this.isDataItem = isDataItem;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getListAttributeId() {
        return listAttributeId;
    }

    public void setListAttributeId(String listAttributeId) {
        this.listAttributeId = listAttributeId;
    }


}
