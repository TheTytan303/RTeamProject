import Rteam.RTeam;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static Rteam.RTeam.frameInit;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class UserInterfaceTest {

   @BeforeClass
    public static void init(){
       try {
           frameInit("Test");
       } catch (Exception e) {
           e.printStackTrace();
       }
   }


   @Test
   void checkIfStory1ButtonIsOpeningStory1() {
       assertDoesNotThrow(() -> { RTeam.story1.doClick(1); });
   }

    @Test
    void checkIfStory2ButtonIsOpeningStory2(){
       assertDoesNotThrow(()->{RTeam.story2.doClick(1);});
    }
    @Test
    void checkIfStory3ButtonIsOpeningStory3(){
        assertDoesNotThrow(()->{RTeam.story3.doClick(1);});
    }
    @Test
    void checkIfStory4ButtonIsOpeningStory4(){
        assertDoesNotThrow(()->{RTeam.story4.doClick(1);});
    }
}
