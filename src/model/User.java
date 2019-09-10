package model;

public class User {
    public Integer id;
    public Integer pId;
    public String name;
    public boolean open;
    public boolean isParent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    /**
     * 构造函数
     * */
    public User() {
    }

    public User(int id, int pId, String name) {
        this.id = id;
        this.pId = pId;
        this.name = name;
    }

    public User(int id, int pId, String name, boolean open) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.open = open;
    }

    public User(int id, int pId, String name, boolean open, boolean isParent) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.open = open;
        this.isParent = isParent;
    }

    @Override
    public String toString() {
        return "TreeInfo{" +
                "id=" + id +
                ", pId=" + pId +
                ", name='" + name + '\'' +
                ", open=" + open +
                ", isParent=" + isParent +
                '}';
    }

}
