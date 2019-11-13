package files.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PackageFile {
    private List<PackageFile> packages;
    private List<JavaFile> javaFiles;
    private String name;
    private String path;



    public PackageFile(String path){
        File folder = new File(path);
        this.packages = new ArrayList<>();
        this.javaFiles = new ArrayList<>();
        this.path=path;
        this.name = path.split("\\\\")[path.split("\\\\").length-1];
        File[] searchResults = folder.listFiles(pathname -> pathname.getPath().endsWith(".java"));
        for(File f: searchResults){
            javaFiles.add(new JavaFile(f));
        }
        searchResults = folder.listFiles();
        for(File f: searchResults){
            if(f.isDirectory()) {
                packages.add(new PackageFile(f.getPath()));
            }
        }

    }

    public List<PackageFile> getPackages() {
        return packages;
    }
    public List<JavaFile> getJavaFiles() {
        return javaFiles;
    }
    public String getName() {
        return name;
    }

    public void setPackages(List<PackageFile> packages) {
        this.packages = packages;
    }
    public void setJavaFiles(List<JavaFile> javaFiles) {
        this.javaFiles = javaFiles;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
