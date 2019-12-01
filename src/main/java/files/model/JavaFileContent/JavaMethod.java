package files.model.JavaFileContent;

import files.service.AccessModifier;
import files.service.MethodDeclaration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static files.service.AccessModifier.*;
//import static files.service.AccessModifier.PUBLIC;

public class JavaMethod{
    private String name;
    private List<JavaClass> params;
    private List<String> calledMethodsNames;
    private List<JavaMethod> calledMethods;
    private JavaClass returnVale;
    private Boolean isStatic, isSynchronized;
    private Access access;
    private JavaClass parent;

    private String code;

    public JavaMethod(JavaClass parent, MethodDeclaration md){
        this.name = md.getName();
        switch(md.getAccessModifier()){
            case PUBLIC:
                this.access = Access.type_public;
                break;
            case DEFAULT:
                this.access = Access.type_package_private;
                break;
            case PRIVATE:
                this.access = Access.type_private;
                break;
            case PROTECTED:
                this.access = Access.type_protected;
                break;
        }
        //this.access = md.getAccessModifier();
        this.isStatic = md.isStatic();
        this.isSynchronized = md.isSynchronized();
        this.params = new ArrayList<>();
        this.returnVale = new JavaClass(md.getReturnType());
        this.calledMethodsNames = new ArrayList<>();
        this.calledMethods = new ArrayList<>();
        this.parent = parent;
        for(String s: md.getArgumentTypeNames()){
            this.params.add(new JavaClass(s));
        }
    }

    void convertMethods(List<JavaMethod> allMethods){
        Map<String, JavaMethod> map = new HashMap<>();
        for(JavaMethod m: allMethods){
            map.put(m.parent + "\\\\"+m.name, m);
        }
        for(String methodName: calledMethodsNames){
            System.out.println(methodName);
        }
    }

    @Override
    public String toString(){
        String returnVale = this.returnVale + " " + this.name + "(" ;
        for(JavaClass jc:params) {
            returnVale = returnVale.concat(jc + ", ");
        }
        returnVale = returnVale.split(",")[0];
        returnVale =returnVale.concat(")");
        return returnVale;
    }

}
