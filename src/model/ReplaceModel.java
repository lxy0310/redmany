package model;

import java.io.Serializable;

public class ReplaceModel implements Serializable {
    private Integer Id;
    public int state;
    private String compoundName;
    private String html_template;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getCompoundName() {
        return compoundName;
    }

    public void setCompoundName(String compoundName) {
        this.compoundName = compoundName;
    }

    public String getHtml_template() {
        return html_template;
    }

    public void setHtml_template(String html_template) {
        this.html_template = html_template;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ReplaceModel{" +
                "Id=" + Id +
                ", state=" + state +
                ", compoundName='" + compoundName + '\'' +
                ", html_template='" + html_template + '\'' +
                '}';
    }
}
