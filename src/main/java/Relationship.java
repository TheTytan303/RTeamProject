import java.util.ArrayList;

public class Relationship {
    private String thisName;
    private ArrayList<String> name;
    private Integer inCount;
    private Integer outCount;

    public Relationship(String thisName, ArrayList<String> name) {
        this.thisName = thisName;
        this.name = name;
    }

    public String getThisName() {
        return thisName;
    }

    public void setThisName(String thisName) {
        this.thisName = thisName;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public void setName(ArrayList<String> name) {
        this.name = name;
    }

    public Integer getInCount() {
        return inCount;
    }

    public void setInCount(Integer inCount) {
        this.inCount = inCount;
    }

    public Integer getOutCount() {
        return outCount;
    }

    public void setOutCount(Integer outCount) {
        this.outCount = outCount;
    }
}
