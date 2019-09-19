package model;

public class Operation {

    private int Id;
    private String OperationName;
    private String OperationICon;
    private String OperationType;
    private String TemplatePage;
    private int state;
    private String Batch;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getOperationName() {
        return OperationName;
    }

    public void setOperationName(String operationName) {
        OperationName = operationName;
    }

    public String getOperationICon() {
        return OperationICon;
    }

    public void setOperationICon(String operationICon) {
        OperationICon = operationICon;
    }

    public String getOperationType() {
        return OperationType;
    }

    public void setOperationType(String operationType) {
        OperationType = operationType;
    }

    public String getTemplatePage() {
        return TemplatePage;
    }

    public void setTemplatePage(String templatePage) {
        TemplatePage = templatePage;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getBatch() {
        return Batch;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "Id=" + Id +
                ", OperationName='" + OperationName + '\'' +
                ", OperationICon='" + OperationICon + '\'' +
                ", OperationType='" + OperationType + '\'' +
                ", TemplatePage='" + TemplatePage + '\'' +
                ", state=" + state +
                ", Batch='" + Batch + '\'' +
                '}';
    }

    public void setBatch(String batch) {
        Batch = batch;
    }

}
