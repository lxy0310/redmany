package model;

public class Operation {

    private int Id;
    private String OperationName;
    private String OperationICon;
    private String OperationType;
    private String TemplatePage;
    private int state;

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

    @Override
    public String toString() {
        return "Operation{" +
                "Id=" + Id +
                ", OperationName='" + OperationName + '\'' +
                ", OperationICon='" + OperationICon + '\'' +
                ", OperationType='" + OperationType + '\'' +
                ", TemplatePage='" + TemplatePage + '\'' +
                ", state=" + state +
                '}';
    }
}
