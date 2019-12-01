package files.model.JavaFileContent;
import files.model.JavaFile;
import files.service.ClassDeclaration;
import files.service.FieldDeclaration;
import files.service.MethodDeclaration;

import java.util.ArrayList;
import java.util.List;

public class JavaClass{
    private String name;
    private List<JavaClass> subClasses;
    private List<JavaMethod> methods;
    private List<JavaField> fields;
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
    public JavaClass(ClassDeclaration cd){
        this.name = cd.getName();
        methods = new ArrayList<>();
        fields = new ArrayList<>();
        for(FieldDeclaration fd : cd.getFields()){
            fields.add(new JavaField(fd));
        }
        for(MethodDeclaration md : cd.getMethods()){
            JavaMethod method = new JavaMethod(this, md);
            this.methods.add(method);
        }
    }
    public JavaClass(String name){
        this.name = name;
    }
    //----------------------------------------------------------------------------------Getters
    public String getName() {
        return name;
    }
    public String getCode(){return code;}
    public JavaFile getParent(){return parent;}
    public List<JavaClass> getSubClasses() {
        return subClasses;
    }
    public List<JavaMethod> getMethods() {
        return methods;
    }
    public List<JavaField> getFields() {
        return fields;
    }

    //----------------------------------------------------------------------------------Setters
    public void setMethods(List<JavaMethod> methods) {
        this.methods = methods;
    }
    public void setFields(List<JavaField> fields) {
        this.fields = fields;
    }
    public void setSubClasses(List<JavaClass> subClasses) {
        this.subClasses = subClasses;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCode(String code){this.code=code;
    }
    //----------------------------------------------------------------------------------public

    @Override
    public String toString(){
        return this.name;
    }
}
