package model;

public class SystemSetting {
    private String keyStr;
    private String valueStr;

    public SystemSetting() {

    }

    public SystemSetting(String keyStr, String valueStr) {
        this.keyStr = keyStr;
        this.valueStr = valueStr;
    }

    public String getKeyStr() {
        return keyStr;
    }

    public void setKeyStr(String keyStr) {
        this.keyStr = keyStr;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }
}
