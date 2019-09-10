package model;

/**
 * Created by hy on 2018/1/25.
 */
public class CategoryInfo {

    public int Id;
    public String name;
    public String icon;
    public int parentID;
    public String adImg;


    public String getAdImg() {
        return adImg;
    }

    public void setAdImg(String adImg) {
        this.adImg = adImg;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    @Override
    public String toString() {
        return "CategoryInfo{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", parentID=" + parentID +
                ", adImg='" + adImg + '\'' +
                "}\n";
    }
}
