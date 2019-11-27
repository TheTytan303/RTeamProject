import files.model.JavaFile;
import files.model.PackageFile;

import java.util.List;

public class RTeam {

    public static void main(String[] args){
        //List<JavaFileContent> list = JavaFileContent.getFilesFrom(JavaFileContent.getProjectPath());
        //PackageFile pack = new PackageFile(JavaFile.getProjectPath());
        PackageFile pack = new PackageFile(JavaFile.getProjectPath());
        List<JavaFile> files = pack.getSubFiles();
        //List<PackageFile> pack2 = pack.getPackages();
        System.out.println("kurdebele");
    }
}