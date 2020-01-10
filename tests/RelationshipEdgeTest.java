import Graph.RelationshipEdge;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class RelationshipEdgeTest {

    RelationshipEdge re= new RelationshipEdge("label");

    @Test
    void checkIfLabelExists(){
        assertFalse(re.getLabel().isBlank());
    }

}