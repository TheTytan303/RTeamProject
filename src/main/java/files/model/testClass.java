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
        Map<JavaFile, Map<JavaMethod, Integer>> map = PackageFile.getMethodFileDependecies2(mainPackage);
        //PackageFile.partitionGraph(mainPackage);
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
/*
public static void partitionGraph(PackageFile target){
        SimpleGraph<JavaMethod, DefaultEdge> returnVale =new SimpleGraph<JavaMethod, DefaultEdge>(DefaultEdge.class);
        for(JavaMethod jm: target.getSubMethods()){
            returnVale.addVertex(jm);
        }
        for(JavaMethod jm: target.getSubMethods()){
            for(JavaMethod jm2: jm.getCalledMethod().keySet()){
                if(jm2 != null && jm2 != jm) returnVale.addEdge(jm,jm2);
            }
        }
        BipartitePartitioning partitioning = new BipartitePartitioning(returnVale);
        PartitioningAlgorithm.Partitioning part = partitioning.getPartitioning();
        System.out.print("damn");
        //PartitioningAlgorithm.PartitioningImpl tmp = new PartitioningAlgorithm.PartitioningImpl();
    }


 */