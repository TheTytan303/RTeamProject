package files.model;



import files.model.JavaFileContent.JavaClass;
import files.model.JavaFileContent.JavaMethod;
import files.service.ClassDeclaration;
import files.service.EnumDeclaration;
import files.service.Import;
import files.service.Parser;

import java.io.*;
import java.util.*;

public class JavaFile implements Comparator<JavaFile> {
    private String path;
    private String pack;
    private Long size;
    private PackageFile parent;
    private List<JavaFile> imports;
    private List<JavaClass> javaClass;


    public JavaFile(String path){
        this.path = path;
    }
    public JavaFile(File f){  //throws FileNotFoundException
        this.path = f.getPath();
        this.size = f.length();
        this.imports = new ArrayList<>();
        try {
            this.pack = Parser.getPackage(this).orElse("?");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Set<ClassDeclaration> set = Parser.getClassesOrInterfaces(this);
            Set<EnumDeclaration> enums = Parser.getEnumDeclarations(this);
            javaClass = new ArrayList<>();
            for(ClassDeclaration cd: set){
                javaClass.add(new JavaClass(this, cd));
            }
            for(EnumDeclaration ed: enums){
                javaClass.add(new JavaClass(this, ed));
            }
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
        String separator = File.separator;
        if(separator.equals("\\")){
            String[] tab = this.getPath().split("\\\\");
            return tab[tab.length-1];
        }
        else {

            String[] tab = this.getPath().split(File.separator);
            return tab[tab.length-1];
        }
    }
    public String getClassName(){
        return javaClass.toString();
    }
    public String getPack(){return this.pack;}
    public PackageFile getParent(){return this.parent;}
    public List<JavaMethod> getMethods(){
        List<JavaMethod> returnVale = new ArrayList<>();
        for(JavaClass jc: javaClass){
            returnVale.addAll(jc.getMethods());
        }
        return returnVale;
    }
    public List<JavaClass> getClasses(){
        return javaClass;
    }
    //----------------------------------------------------------------------------------Setters
    public void setImports(List<JavaFile> imports) {
        this.imports = imports;
    }
    public void setParent(PackageFile parent) {
        this.parent = parent;
    }
    public void addImport(Collection<JavaFile> collection){
        for(JavaFile f : collection){
            if(f!=this)
            this.imports.add(f);
        }
    }
    //----------------------------------------------------------------------------------Overrides
    @Override
    public int compare(JavaFile o1, JavaFile o2) {
        return o1.getPath().compareTo(o2.path);
    }
    @Override
    public String toString() {
        return "["+this.getSize()+"B]: "+this.getClassName();
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
    //----------------------------------------------------------------------------------Package-Private
    void convertImports(List<JavaFile> allFiles) throws FileNotFoundException {
        Map<String, JavaFile> projectFilesNames = new HashMap<>();
        for(JavaFile file: allFiles){
            String className =  "";
            if(file.getClasses().size() == 0){
                className = "";
            }
            else {
                className = file.getClasses().get(0).getName();
            }
            projectFilesNames.put(file.getPack() + "." +className,file);
        }
        for(Import i:Parser.getImports(this)){
            JavaFile tmp = projectFilesNames.get(i.toString());
            if(tmp != null){
                this.imports.add(tmp);
            }
        }
    }
    public String getFullName(){
        String returnVale = parent.getFullName() + "\\" + this.getName();

        return returnVale;
    }
}