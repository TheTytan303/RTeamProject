import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import rTeam.RTeam;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class RTeamTest {

    RTeam testObject=new RTeam();
    public static JFrame frame = new JFrame();
    static double scale=1.0;

    @Test
    void checkIfFrameIsOpening() {
        testObject.frameInit("tytul");
        Assertions.assertTrue(testObject.frame.isShowing());
    }

    @Test
    void checkIfStory1IsStarting() {
        testObject.frameInit("tytul");
        RTeam.story1(scale, RTeam.panel);
    }

    @Test
    public void shouldThrowExceptionIfScaleIsNegativeForStory1(){
        testObject.frameInit("tytul");
        assertThrows(IllegalArgumentException.class,()->{
            RTeam.story1(-1, RTeam.panel);} );
    }

    @Test
    void shouldThrowExceptionIfScaleIsNegativeForStory2() {
        testObject.frameInit("tytul");
        assertThrows(IllegalArgumentException.class,()->{
            RTeam.story2(-1, RTeam.panel);} );
    }
}