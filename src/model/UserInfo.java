package model;

public class UserInfo {
    public static final UserInfo NULL = new UserInfo();
    public int Id;
    private String line1;
    private String headTitle;
    public String headImg;
    public String line2;
    private String nickTitle;
    public String NickName;
    private String line3;
    private String sexTitle;
    public String Sex;

    public String line4;
    public String phoneTitle;
    public String Mobile;
    private String line5;
    public String setPW;
    public String setPWtext;
    private String line6;
    public String levelTitle;
    public String levelText;
    private String line7;
    private String saveBtn;

    private int pid;
    private String userName;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public static UserInfo getNULL() {
        return NULL;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "Id=" + Id +
                ", line1='" + line1 + '\'' +
                ", headTitle='" + headTitle + '\'' +
                ", headImg='" + headImg + '\'' +
                ", line2='" + line2 + '\'' +
                ", nickTitle='" + nickTitle + '\'' +
                ", NickName='" + NickName + '\'' +
                ", line3='" + line3 + '\'' +
                ", sexTitle='" + sexTitle + '\'' +
                ", Sex='" + Sex + '\'' +
                ", line4='" + line4 + '\'' +
                ", phoneTitle='" + phoneTitle + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", line5='" + line5 + '\'' +
                ", setPW='" + setPW + '\'' +
                ", setPWtext='" + setPWtext + '\'' +
                ", line6='" + line6 + '\'' +
                ", levelTitle='" + levelTitle + '\'' +
                ", levelText='" + levelText + '\'' +
                ", line7='" + line7 + '\'' +
                ", saveBtn='" + saveBtn + '\'' +
                '}';
    }
}
