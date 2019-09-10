package model;

/**
 * Created by hy on 2017/12/21.
 */
public class OaAttribute {
    private String wapAttribute;
    private String windowsAttribute;
    private String name;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "OaAttribute{" +
                "wapAttribute='" + wapAttribute + '\'' +
                ", windowsAttribute='" + windowsAttribute + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
