import files.model.JavaFile;
import files.model.JavaFileContent.JavaClass;
import files.model.JavaFileContent.JavaMethod;
import files.model.PackageFile;

import java.util.List;

public class RTeam {

    public static void main(String[] args){
        //List<JavaFileContent> list = JavaFileContent.getFilesFrom(JavaFileContent.getProjectPath());
        //PackageFile pack = new PackageFile(JavaFile.getProjectPath());
        PackageFile pack = new PackageFile(JavaFile.getProjectPath());
        List<JavaFile> files = pack.getSubFiles();
        List<JavaClass> classes = pack.getSubClasses();
        List<JavaMethod> methods = pack.getSubMethods();
        //List<PackageFile> pack2 = pack.getPackages();
        System.out.println(files.get(5).getClassName());
        for(JavaMethod jm:methods){
            System.out.println(jm.getFullName());
        }
        for(JavaClass jc: classes){
            System.out.println(jc.getFullName());
        }
        System.out.println("kurdebele");
    }
}