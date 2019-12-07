package files.model.JavaFileContent;
import files.model.JavaFile;
import files.service.ClassDeclaration;
import files.service.EnumDeclaration;
import files.service.FieldDeclaration;
import files.service.MethodDeclaration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaClass implements JavaEntity{
    private String name;
    private List<JavaClass> subClasses;
    private List<JavaMethod> methods;
    private List<JavaField> fields;
    private JavaClass returns;
    private JavaFile parent;
    private String code;

    public JavaClass(JavaFile parent, String name, String code){
        this.code = code;
        this.name = name;
        this.parent = parent;
        this.subClasses = new ArrayList<>();
        this.methods = new ArrayList<>();
        this.fields = new ArrayList<>();
    }
    public JavaClass(JavaFile parent, ClassDeclaration cd){
        this.name = cd.getName();
        this.parent = parent;
        methods = new ArrayList<>();
        fields = new ArrayList<>();
        for(FieldDeclaration fd : cd.getFields()){
            this.fields.add(new JavaField(fd, this));
        }
        for(MethodDeclaration md : cd.getMethods()){
            JavaMethod method = new JavaMethod(this, md);
            this.methods.add(method);
        }
    }
    public JavaClass(String name){
        this.name = name;
    }

    public JavaClass(JavaFile parent, EnumDeclaration ed) {
        this.name = ed.getName();
        this.parent = parent;
        methods = new ArrayList<>();
        fields = new ArrayList<>();
        for(MethodDeclaration md : ed.getMethods()){
            JavaMethod method = new JavaMethod(this, md);
            this.methods.add(method);
        }
    }

    //----------------------------------------------------------------------------------Getters
    public String getName() {return name; }
    public String getCode(){return code;}
    public JavaFile getParent(){return parent;}
    public List<JavaClass> getSubClasses() {return subClasses; }
    public List<JavaMethod> getMethods() {return methods; }
    public List<JavaField> getFields() {return fields; }
    public Map<JavaMethod, Integer> getMethodsCall(){
        Map<JavaMethod, Integer> returnVale = new HashMap<>();
        for(JavaMethod jm:methods){
            returnVale.putAll(jm.getCalledMethod());
        }
        return returnVale;
    }

    //----------------------------------------------------------------------------------Setters
    public void setMethods(List<JavaMethod> methods) {this.methods = methods; }
    public void setFields(List<JavaField> fields) {this.fields = fields; }
    public void setSubClasses(List<JavaClass> subClasses) {this.subClasses = subClasses; }
    public void setName(String name) {this.name = name; }
    public void setCode(String code){this.code=code;}
    //----------------------------------------------------------------------------------public

    public void convertFields(List<JavaClass> allClasses){
        for(JavaField jf: fields){
            if(jf.getType() == null){
                jf.searchForType(allClasses);
            }
        }
        for(JavaMethod jm: methods){
            jm.convertLocalVariables(allClasses);
        }
    }
    public JavaMethod searchForMethod(String name){
        if(this.methods == null){
            return null;
        }
        for(JavaMethod jm: this.methods){
            if(jm.getName().contains(name)){
                return jm;
            }
        }
        return null;
    }
    @Override
    public String toString(){
        return this.name;
    }
    public String getFullName(){
        String returnVale = parent.getFullName() +"|"+this;
        return returnVale;
    }
}
