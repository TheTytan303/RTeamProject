import static org.junit.jupiter.api.Assertions.*;

import Graph.Graph;
import Graph.Relationship;
import  org.junit.jupiter.api.Test;

import java.util.ArrayList;

class GraphTest {
    private Graph applet = new Graph(1);

    //no files to import
    @Test
    public void shouldSayThatThereAreNoFilesToImport() {
        ArrayList<String> fileName = new ArrayList<>();
        ArrayList<Long> fileSize = new ArrayList<>();
        ArrayList<Relationship> relationships = new ArrayList<>();
        assertThrows(IllegalArgumentException.class,()->{applet.importData(fileName, fileSize, relationships);} );
    }



    //check if
}