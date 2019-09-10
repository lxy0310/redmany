package model;

public class UserPanel {

    private Integer ID;
    private String icon;
    private String PanelName;
    private Integer SortIndex;
    private String UserRole;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPanelName() {
        return PanelName;
    }

    public void setPanelName(String panelName) {
        PanelName = panelName;
    }

    public Integer getSortIndex() {
        return SortIndex;
    }

    public void setSortIndex(Integer sortIndex) {
        SortIndex = sortIndex;
    }

    public String getUserRole() {
        return UserRole;
    }

    public void setUserRole(String userRole) {
        UserRole = userRole;
    }

    @Override
    public String toString() {
        return "UserPanel{" +
                "ID=" + ID +
                ", icon='" + icon + '\'' +
                ", PanelName='" + PanelName + '\'' +
                ", SortIndex=" + SortIndex +
                ", UserRole='" + UserRole + '\'' +
                '}';
    }
}
