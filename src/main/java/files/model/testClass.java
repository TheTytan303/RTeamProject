package files.model;

import files.model.JavaFileContent.JavaClass;
import files.model.JavaFileContent.JavaMethod;

import java.util.List;
import java.util.Map;

public class testClass {
    public static void main(String[] args){
        //PackageFile mainPackage = JavaFile.getFilesFrom();
        String path = JavaFile.getProjectPath();
        System.out.println(path);
        //path = path.concat("\\files\\service");
        PackageFile mainPackage = new PackageFile(path);
        Map<JavaMethod, JavaFile> map = PackageFile.getMethodFileDependecies(mainPackage);
        List<JavaClass> subClasses = mainPackage.getSubClasses();
        //Map<PackageFile, Integer> packageImport = mainPackage.getPackageDependencies();
        //Map<PackageFile, Integer> packageImport2 = mainPackage.getPackages().get(0).getPackageDependencies();
        //PackageFile tmp =  mainPackage.getPackages().get(0).getPackages().get(0);
        //PackageFile tmp =  mainPackage.getPackages().get(2);
        //Map<PackageFile, Integer> packageImport3 =tmp.getPackageDependencies();
        //List<PackageFile> list = mainPackage.getSubPackages();
        //Map<PackageFile, Map<PackageFile, Integer>> map = PackageFile.getAllDependeciesMap(mainPackage);
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
