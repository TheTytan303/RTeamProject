import Graph.Relationship;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RelationshipTest {
    ArrayList<String> dependencies = new ArrayList<>();

     Relationship relationship=new Relationship("name",dependencies);

    @Test
    void checkIfNameExists() {
        assertFalse(relationship.getName().isBlank());
    }

    @Test
    void checkIfDependenciesExists() {
        dependencies.add("test");
        assertFalse(relationship.getDependencies().isEmpty());
    }

    @Test
    void checkIfInCountIsNotNegative() {
        assertTrue(relationship.getInCount()>=0);
    }

    @Test
    void checkIfInCountIsIncrementedCorrectly() {
        int tmp=relationship.getInCount()+1;
        relationship.incrementInCount();
        assertEquals(tmp, relationship.getInCount());
    }

    @Test
    void checkIfOutCountIsNotNegative() {
        assertTrue(relationship.getOutCount()>=0);
    }

    @Test
    void checkIfOutCountIsIncrementedCorrectly() {
        int tmp=relationship.getOutCount()+1;
        relationship.incrementOutCount();
        assertEquals(tmp, relationship.getOutCount());
    }

    @Test
    void checkIfOutCountIsAddedCorrectly() {
        relationship.addOutCount(4);
        assertEquals(4, relationship.getOutCount());

    }

    @Test
    void checkIfConnectionDoesNotExists() { ArrayList<Relationship> rs=new ArrayList<>();
      assertEquals(-1, Relationship.getIndexFromName(rs,"test"));
    }
}