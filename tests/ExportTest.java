import files.service.Export;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ExportTest {

    static Export export;

    @BeforeEach
    public void init() {
        export = new Export("test.txt");
        export.addRelation("test1From:","test1To:", "test1Arrow");
        export.addRelation("test2From:","test2To:", "test2Arrow");
        export.addRelation("test3From:","test3To:", "test3Arrow");
        export.save();
    }

    @Test
    public void checkIfFileHasCorrectNumberOfLines(){

        int expectedNumberOfLines=3;
        int counter = 0;
        try {
            String content = Files.readString(Paths.get("test.txt"));
            System.out.println(content);
            for(char c: content.toCharArray()){
                if(c=='\n') counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(expectedNumberOfLines, counter);

    }

    @Test
    public void checkIfFileContainsOnlyOneColonForLine(){

        int expectedNumberOfColons=3;
        int counter = 0;
        try {
            String content = Files.readString(Paths.get("test.txt"));
            System.out.println(content);
            for(char c: content.toCharArray()){
                if(c==':') counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(expectedNumberOfColons, counter);

    }

}