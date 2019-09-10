package page;

/**
 * Created by hy on 2017/10/21.
 */
public class HomeForm {

    private String copFormName;

    private String showType;

    /**
     * form类
     */
    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }


    /**
     * form表的formname
     */
    public String getCopFormName() {
        return copFormName;
    }

    @Override
    public String toString() {
        return "HomeForm{" +
                "copFormName='" + copFormName + '\'' +
                ", showType='" + showType + '\'' +
                '}';
    }
}
