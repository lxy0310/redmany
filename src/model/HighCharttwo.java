package model;

import java.util.List;

public class HighCharttwo {
    private String name; //标题
    private double[] data1;
    private double y;
    private List<HighChartPie> data2; //数据
    private Object data;
    private String size;
    private String innerSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }


    public double[] getData1() {
        return data1;
    }

    public void setData1(double[] data1) {
        this.data1 = data1;
    }

    public List<HighChartPie> getData2() {
        return data2;
    }

    public void setData2(List<HighChartPie> data2) {
        this.data2 = data2;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
