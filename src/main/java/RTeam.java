

import java.io.FileNotFoundException;
import java.util.List;

public class RTeam {

    public static void main(String[] args){
        List<JavaFile> list = JavaFile.getFilesFrom(JavaFile.getProjectPath());
        for(JavaFile file: list){
            System.out.println(file);
            try {
                System.out.println(file.getContent());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        System.out.println(System.getProperty("java.home"));
        JavaFile file = new JavaFile("none");
        System.out.println("kurdebele");
    }
}
