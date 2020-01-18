package files.model;

import files.model.JavaFileContent.JavaClass;
import files.model.JavaFileContent.JavaMethod;

import java.util.List;
import java.util.Map;

public class testClass {
    public static void main(String[] args){
        //PackageFile mainPackage = JavaFile.getFilesFrom();
        String path = JavaFile.getProjectPath();
        //path = path.concat("\\files\\service");
        PackageFile mainPackage = new PackageFile(path);
        Map<PackageFile, Integer> packageImport = mainPackage.getPackageDependencies();
        Map<PackageFile, Integer> packageImport2 = mainPackage.getPackages().get(0).getPackageDependencies();
        Map<PackageFile, Integer> packageImport3 = mainPackage.getPackages().get(0).getPackages().get(0).getPackageDependencies();

        List<JavaClass> subClasses = mainPackage.getSubClasses();
        for(JavaClass jc: subClasses){
            List<JavaMethod> methods = jc.getMethods();
            for(JavaMethod jm: methods){
                Map<JavaMethod, Integer> dependencyMethods = jm.getCalledMethod();
                System.out.print("");
            }
            System.out.print("");
        }
        return;
    }
}
