import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import Rteam.RTeam;


import java.util.concurrent.TimeUnit;

import static Rteam.RTeam.frameInit;
import static org.junit.jupiter.api.Assertions.*;

class RTeamTest {

    @BeforeClass
    public static void init(){
        try {
            frameInit("Test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void checkIfFrameIsOpening() {
       // RTeam.frameInit("tytul");
        Assertions.assertFalse(RTeam.frame.isShowing());
    }

    @Test
    public void checkIfStory1IsStarting() {
        //RTeam.frameInit("tytul");
        double scale = 1.0;
        assertDoesNotThrow(()->RTeam.story1(scale, RTeam.panel));
    }
    @Test
    void checkIfStory2IsStarting() {
       // RTeam.frameInit("tytul");
        double scale = 1.0;
        assertDoesNotThrow(()->RTeam.story2(scale, RTeam.panel));
    }

   @Test
    void checkIfStory3IsStarting() {
        //RTeam.frameInit("tytul");
        double scale = 1.0;
        assertDoesNotThrow(()->RTeam.story3(scale, RTeam.panel));
    }

    @Test
    void checkIfStory4IsStarting() {
       // RTeam.frameInit("tytul");
        double scale = 1.0;
        assertDoesNotThrow(()->RTeam.story4(scale, RTeam.panel));
    }

    @Test
    void shouldThrowExceptionIfScaleIsNegativeForStory1(){
       // RTeam.frameInit("tytul");
        assertThrows(IllegalArgumentException.class,()->{
            RTeam.story1(-1, RTeam.panel);} );
    }

    @Test
    void shouldThrowExceptionIfScaleIsNegativeForStory2() {
        //RTeam.frameInit("tytul");
        assertThrows(IllegalArgumentException.class,()->{
            RTeam.story2(-1, RTeam.panel);} );
    }

    @Test
    void shouldThrowExceptionIfScaleIsNegativeForStory4() {
       // RTeam.frameInit("tytul");
        assertThrows(IllegalArgumentException.class,()->{
            RTeam.story4(-1, RTeam.panel);} );
    }
    @Test
    void shouldThrowExceptionIfFrameIsNotInitialised(){
    RTeam.frame=null;
    assertThrows(Exception.class, ()->{frameInit("");});

    }
}