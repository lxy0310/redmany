package model;

import java.io.Serializable;
import java.util.List;

public class HighChart implements Serializable {
    private String titleText; //标题
    private String subtitleText; //小标题
    private String type; //图表类型
    private String yTitleText;
    private String[] xCategories;
    private List<HighChartAxis> xAxis; //x轴标题
    private List<HighChartAxis> yAxis; //y轴标题
    private List<HighCharttwo> data; //数据
    private String distance;

    public String getyTitleText() {
        return yTitleText;
    }

    public void setyTitleText(String yTitleText) {
        this.yTitleText = yTitleText;
    }

    public String[] getxCategories() {
        return xCategories;
    }

    public void setxCategories(String[] xCategories) {
        this.xCategories = xCategories;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public String getSubtitleText() {
        return subtitleText;
    }

    public void setSubtitleText(String subtitleText) {
        this.subtitleText = subtitleText;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<HighChartAxis> getxAxis() {
        return xAxis;
    }

    public void setxAxis(List<HighChartAxis> xAxis) {
        this.xAxis = xAxis;
    }

    public List<HighChartAxis> getyAxis() {
        return yAxis;
    }

    public void setyAxis(List<HighChartAxis> yAxis) {
        this.yAxis = yAxis;
    }

    public List<HighCharttwo> getData() {
        return data;
    }

    public void setData(List<HighCharttwo> data) {
        this.data = data;
    }
}