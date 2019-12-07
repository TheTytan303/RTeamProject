package files.model;

import files.model.JavaFileContent.JavaClass;
import files.model.JavaFileContent.JavaMethod;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
        List<JavaMethod> methods = getSubMethods();
        for(JavaMethod m : methods){
            m.convertMethods(methods);
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


    //---------------------------------------------------------------------------------------SUFF:
    //public void setPackages(List<PackageFile> packages) {this.packages = packages;}
    //public void setJavaFiles(List<JavaFile> javaFiles) {this.javaFiles = javaFiles;}
}
