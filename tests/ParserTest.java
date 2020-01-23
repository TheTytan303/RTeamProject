import files.model.JavaFile;
import files.model.JavaFileContent.JavaClass;
import files.model.JavaFileContent.JavaMethod;
import files.model.PackageFile;
import files.service.Import;
import files.service.Parser;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import static files.service.Parser.getPackage;
import static files.service.Parser.getImports;

public class ParserTest {
    static PackageFile mainPackage;
    @BeforeAll
    public static void init(){
        String sep = File.separator;
        String path = System.getProperty("user.dir")+sep+"TestPackage";
        mainPackage=new PackageFile(path);

    }

    @Test
    void checkIfTotalNumberOfClassesForAPackageIsCorrect(){

        List<JavaFile> list = mainPackage.getSubFiles();
        int correctNumberOfClasses=2;

        assertEquals(correctNumberOfClasses, list.size());
    }

    @Test
    void checkIfNumberOfMethodsForAFileIsCorrect(){

        List<JavaMethod> javaMethods = mainPackage.getJavaFiles().get(0).getMethods();
        int correctNumberOfMethods=3;

        assertEquals(correctNumberOfMethods, javaMethods.size());
    }

    @Test
    void checkIfNameForAFileIsCorrect(){

        assertEquals("TestClass2.java",mainPackage.getJavaFiles().get(1).getName() );
    }
    @Test
    void checkIfLengthOfContentInAFileIsCorrect(){
        String content = null;
        try {
            content = mainPackage.getJavaFiles().get(0).getContent();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(133,content.length() );
    }

    @Test
    void checkIfSuchFileWasFound(){
        assertDoesNotThrow(()-> mainPackage.getJavaFiles().get(1));
    }
    @Test
    void checkIfNumberOfClassesForAPackageIsCorrect() {

       assertEquals(1, mainPackage.getJavaFiles().get(1).getClasses().size() );

    }
    @Test
    void checkIfPathIfPathIsGettingCorrectly(){
        File file = new File(mainPackage.getJavaFiles().get(1).getPath());
        assertTrue(file.canRead());

       /* try {
            List <String> lines= Files.readAllLines(Paths.get(mainPackage.getJavaFiles().get(1).getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //assertNotEquals(null,  mainPackage.getJavaFiles().get(1).getPath());
    }

    @Test
    void checkIfPackageIsNotEmpty(){
        assertNotEquals(null, mainPackage.getJavaFiles().isEmpty());
    }

    @Test
    void checkIfNameOfPackageExists(){
        assertNotEquals("",mainPackage.getFullName());
    }

    @Test
    void checkIfNumberOfJavaFilesInPackageIsCorrect(){
        assertEquals(2, mainPackage.getJavaFiles().size());
    }
    }
