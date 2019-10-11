package model;

import java.util.List;

public class HighCharttwo {
    private String name; //标题
   // private double[] data;
    private double y;
    private List<HighChartPie> data; //数据
    private String datas;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public double[] getData() {
//        return data;
//    }
//
//    public void setData(double[] data) {
//        this.data = data;
//    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public List<HighChartPie> getData() {
        return data;
    }

    public void setData(List<HighChartPie> data) {
        this.data = data;
    }
}
