import java.util.ArrayList;

public class Relationship {
    private String name;
    private ArrayList<String> dependencies;
    private Integer inCount;
    private Integer outCount;

    public Relationship(String name, ArrayList<String> dependencies) {
        this.name = name;
        this.dependencies = dependencies;
        this.inCount = 0;
        this.outCount = 0;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getDependencies() {
        return dependencies;
    }

    public Integer getInCount() {
        return inCount;
    }

    public void incrementInCount() {
        this.inCount++;
    }

    public Integer getOutCount() {
        return outCount;
    }

    public void incrementOutCount() {
        this.outCount++;
    }

    public static Integer getIndexFromName(ArrayList<Relationship> relationships, String name) {
        for(int i = 0; i < relationships.size(); i++) {
            if(relationships.get(i).getName().compareTo(name) == 0) {
                return i;
            }
        }
        return -1;
    }
}
