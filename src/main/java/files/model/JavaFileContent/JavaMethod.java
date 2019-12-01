package files.model.JavaFileContent;

import files.service.AccessModifier;
import files.service.MethodDeclaration;
import files.service.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static files.service.AccessModifier.*;
//import static files.service.AccessModifier.PUBLIC;

public class JavaMethod{
    private String name;
    private List<JavaClass> params;
    private Map<String, Integer> calledMethodsNames;
    private Map<JavaMethod, Integer> calledMethods;
    private JavaClass returnType;
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
        this.isStatic = md.isStatic();
        this.isSynchronized = md.isSynchronized();
        this.params = new ArrayList<>();
        this.returnType = new JavaClass(md.getReturnType());
        this.calledMethodsNames = md.getMethodCalls();

        this.calledMethods = new HashMap<>();
        this.parent = parent;
        for(String s: md.getArgumentTypeNames()){
            this.params.add(new JavaClass(s));
        }
    }
    public String getFullName(){
        String returnVale = ""+parent.getFullName()+"|"+this;
        return returnVale;
    }
    void convertMethods(List<JavaMethod> allMethods){
        Map<String, JavaMethod> map = new HashMap<>();
        for(JavaMethod m: allMethods){
            map.put(m.parent + "\\\\"+m.name, m);
        }
        //for(String methodName: calledMethodsNames){
        //    System.out.println(methodName);
        //}
    }

    @Override
    public String toString(){
        String returnVale = this.returnType + " " + this.name + "(" ;
        for(JavaClass jc:params) {
            returnVale = returnVale.concat(jc + ", ");
        }
        if(params.size()!=0)
        returnVale = returnVale.substring(0, returnVale.length()-2);
        returnVale =returnVale.concat(")");
        return returnVale;
    }
}