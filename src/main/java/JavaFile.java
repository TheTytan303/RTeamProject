import java.io.File;
import java.io.FileFilter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class JavaFile {
    String path;
    Long size;
    List<JavaFile> imports;



    public JavaFile(){}
    public JavaFile(File f){
        this.path = f.getPath();
        this.size = f.getTotalSpace();
    }

    //----------------------------------------------------------------------------------Getters
    public String getContent(){
        return null;
    }
    public Long getSize(){
        return this.size;
    }
    public List<JavaFile> getImports() {
        return imports;
    }
    public String getPath(){
        return this.path;
    }

    //----------------------------------------------------------------------------------Setters
    public void setImports(List<JavaFile> imports) {
        this.imports = imports;
    }


    //----------------------------------------------------------------------------------Overrides


    @Override
    public String toString() {

        return "["+this.getSize()+"B]: "+this.getPath();
    }

    //----------------------------------------------------------------------------------Static
    //poberz listę plików w folderze (i jego podfolderach) o podanej ścieżce (path)
    public static List<JavaFile> getFilesFrom(String path){
        List<JavaFile> returnVale = new ArrayList<>();
        File file = new File(path);
        searchFolderFor(file, ".java", returnVale);
        return returnVale;
    }

    //poberz scieżkę projektu
    public static String getProjectPath(){
        return System.getProperty("user.dir");
    }

    private static void searchFolderFor(File folder, String name, List<JavaFile> list){
        File[] searchResults = folder.listFiles(pathname -> pathname.getPath().endsWith(name));
        for(File f: searchResults){
            list.add(new JavaFile(f));
        }
        searchResults = folder.listFiles();
        for(File f: searchResults){
            if(f.getPath().endsWith("name")){
                list.add(new JavaFile(f));
            }else{
                if(f.isDirectory()){
                    searchFolderFor(f ,name, list);
                }
            }
        }
    }
}