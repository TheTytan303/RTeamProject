package files.model;

import files.model.JavaFileContent.JavaClass;
import files.model.JavaFileContent.JavaMethod;
import org.jgrapht.alg.interfaces.PartitioningAlgorithm;
import org.jgrapht.alg.partition.BipartitePartitioning;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackageFile {
    private List<PackageFile> packages;
    private List<JavaFile> javaFiles;
    private List<JavaFile> allJavaSubFiles;
    private String name;
    private String path;
    private PackageFile parent;



    private PackageFile(String path, PackageFile parent){
        File folder = new File(path);
        this.parent = parent;
        this.packages = new ArrayList<>();
        this.javaFiles = new ArrayList<>();
        this.allJavaSubFiles = new ArrayList<>();
        this.path=path;
        this.name = path.split("\\\\")[path.split("\\\\").length-1];
        File[] searchResults = folder.listFiles(pathname -> pathname.getPath().endsWith(".java"));
        for(File f: searchResults){
            JavaFile tmp = new JavaFile(f);
            tmp.setParent(this);
            javaFiles.add(tmp);
        }
        for(JavaFile f: this.javaFiles){
            f.addImport(javaFiles);
        }
        allJavaSubFiles.addAll(javaFiles);
        searchResults = folder.listFiles();
        for(File f: searchResults){
            if(f.isDirectory()) {
                PackageFile pf = new PackageFile(f.getPath(), this);
                packages.add(pf);
                allJavaSubFiles.addAll(pf.getSubFiles());
            }
        }
    }
    public PackageFile(String path){
        this.path = path;
        File folder = new File(path);
        this.packages = new ArrayList<>();
        this.javaFiles = new ArrayList<>();
        this.allJavaSubFiles = new ArrayList<>();
        this.path=path;
        this.name = path.split("\\\\")[path.split("\\\\").length-1];
        File[] searchResults = folder.listFiles(pathname -> pathname.getPath().endsWith(".java"));
        for(File f: searchResults){
            JavaFile tmp = new JavaFile(f);
            tmp.setParent(this);
            javaFiles.add(tmp);
        }
        for(JavaFile f: this.javaFiles){
            f.addImport(javaFiles);
        }
        allJavaSubFiles.addAll(javaFiles);
        searchResults = folder.listFiles();
        for(File f: searchResults){
            if(f.isDirectory()) {
                PackageFile pf = new PackageFile(f.getPath(), this);
                packages.add(pf);
                allJavaSubFiles.addAll(pf.getSubFiles());
            }
        }
        for(JavaFile f: allJavaSubFiles){
            try {
                f.convertImports(allJavaSubFiles);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        for(JavaClass jc: getSubClasses()){
            jc.convertFields(getSubClasses());
        }
        List<JavaMethod> methods = getSubMethods();
        for(JavaMethod m : methods){
            m.convertMethods();
        }
    }

    /**
     * Returns child-packs of this package
     */
    public List<PackageFile> getPackages() {
        return packages;
    }
    /**
     * Returns JavaFiles from only this package
     */
    public List<JavaFile> getJavaFiles() {
        return javaFiles;
    }
    public String getName() {
        return name;
    }
    /**
     * Returns ALL methods from ALL JavaFiles in this pack and it's child-packs
     * */
    public List<JavaFile> getSubFiles(){
        return this.allJavaSubFiles;
    }
    /**
    * Returns ALL methods from ALL JavaFiles in this pack and it's child-packs
    * */
    public List<JavaMethod> getSubMethods(){
      List<JavaMethod> returnVale = new ArrayList<>();
      for(PackageFile pf: packages){
          returnVale.addAll(pf.getSubMethods());
      }
      for(JavaFile jf: javaFiles){
          returnVale.addAll(jf.getMethods());
      }
      return  returnVale;
    }
    /**
     * Returns ALL Classes from ALL JavaClasses in this pack and it's child-packs
     * */
    public List<JavaClass> getSubClasses(){
        List<JavaClass> returnVale = new ArrayList<>();
        for(PackageFile pf: packages){
            returnVale.addAll(pf.getSubClasses());
        }
        for(JavaFile jf: javaFiles){
            returnVale.addAll(jf.getClasses());
        }

        return returnVale;
    }
    public List<PackageFile> getSubPackages(){
        List<PackageFile> returnVale = new ArrayList<>();
        returnVale.addAll(this.packages);
        for(PackageFile pf: this.packages){
            returnVale.addAll(pf.getSubPackages());
        }
        return returnVale;
    }
    /**
     * Returns map o package dependencies
     * */
    public Map<PackageFile, Integer> getPackageDependencies(){
        Map<PackageFile, Integer> returnVale= new HashMap<>();
        for(JavaFile jf: javaFiles){
            List<JavaFile> imports = jf.getImports();
            for(JavaFile imported:imports){
                if(imported.getParent() == this) continue;
                Integer i = returnVale.putIfAbsent(imported.getParent() , 1);
                if(i != null){
                    returnVale.replace(imported.getParent(), i+1);
                }
            }
        }
        return returnVale;
    }

    public void setName(String name) {
        this.name = name;
    }
    /**
     * Returns unique name for this package
     * unique name contains path
     * there cannot be 2 same unique package names in one Directory structure
     * */
    public String getFullName(){
        return this.path;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Map<PackageFile, Map<PackageFile, Integer>> getAllDependeciesMap(PackageFile target){
        Map<PackageFile, Map<PackageFile, Integer>> returnVale = new HashMap<>();
        for(PackageFile pf: target.getSubPackages()){
            returnVale.put(pf, pf.getPackageDependencies());
        }
        returnVale.put(target, target.getPackageDependencies());

        return returnVale;
    }
    public static Map<JavaMethod, JavaFile> getMethodFileDependecies(PackageFile target){
        Map<JavaMethod, JavaFile> returnVale = new HashMap<>();
        for(JavaFile jf: target.getSubFiles()){
            for(JavaMethod jm: jf.getMethods()){
                returnVale.put(jm, jf);
            }
        }
        return returnVale;
    }
    public static Map<JavaFile, Map<JavaMethod, Integer>> getMethodFileDependecies2(PackageFile target){
        Map<JavaFile, Map<JavaMethod, Integer>> returnVale = new HashMap<>();
        for(JavaFile jf: target.getSubFiles()){
            Map<JavaMethod, Integer> tmp = new HashMap<>();
            for(JavaMethod jm: jf.getMethods()){
                tmp.put(jm, 1);
            }
            returnVale.put(jf, tmp);
        }
        return returnVale;
    }
        //---------------------------------------------------------------------------------------SUFF:
    //public void setPackages(List<PackageFile> packages) {this.packages = packages;}
    //public void setJavaFiles(List<JavaFile> javaFiles) {this.javaFiles = javaFiles;}
}
