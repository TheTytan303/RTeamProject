import files.model.JavaFile;
import files.model.PackageFile;

public class RTeam {

    public static void main(String[] args){
        //List<JavaFile> list = JavaFile.getFilesFrom(JavaFile.getProjectPath());
        PackageFile pack = new PackageFile(JavaFile.getProjectPath());
        System.out.println(System.getProperty("java.home"));
        System.out.println("kurdebele");
    }
}