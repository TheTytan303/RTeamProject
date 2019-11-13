package files.model;



import files.service.Import;
import files.service.Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JavaFile implements Comparator<JavaFile> {
    private String path;
    private String pack;
    private Long size;
    private PackageFile parent;
    private List<JavaFile> imports;
    private List<Import> imports2;



    public JavaFile(String path){
        this.path = path;
    }
    public JavaFile(File f){
        this.path = f.getPath();
        this.size = f.length();
        try {
            this.pack = Parser.getPackage(this);
            imports2 = new ArrayList<>(Parser.getImports(this));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------Getters
    public String getContent() throws FileNotFoundException {
        String returnVale = new String();
        File f= new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(f));
        try {
            String line = reader.readLine();
            while(line != null){
                returnVale = returnVale.concat(line + "\n");
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnVale;
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
    public String getName(){
        String[] tab = this.getPath().split("/");
        return tab[tab.length-1];
    }

    //----------------------------------------------------------------------------------Setters
    public void setImports(List<JavaFile> imports) {
        this.imports = imports;
    }

    //----------------------------------------------------------------------------------Overrides
    @Override
    public int compare(JavaFile o1, JavaFile o2) {

        return o1.getPath().compareTo(o2.path);
    }
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
        String sep = File.separator;
        return System.getProperty("user.dir")+sep+"src"+sep+"main"+sep+"java";
    }

    public static JavaFile toJF(String path){
        File file = new File(path);
        if(file.getPath().endsWith(".java")){
            return new JavaFile(file);
        }
        return null;
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