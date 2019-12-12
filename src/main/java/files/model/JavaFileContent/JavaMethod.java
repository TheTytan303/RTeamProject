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

public class JavaMethod implements JavaEntity{
    private String name;
    private List<JavaClass> params;
    private List<JavaField> localVariables;
    private Map<String, Integer> calledMethodsNames;
    private Map<JavaMethod, Integer> calledMethods;
    private JavaClass returnType;
    private Boolean isStatic, isSynchronized;
    private Access access;
    private JavaClass parent;

    private String code;

    public JavaMethod(JavaClass parent, MethodDeclaration md){
        this.name = md.getName();
        this.access = accesTranslate(md.getAccessModifier());
        this.isStatic = md.isStatic();
        this.isSynchronized = md.isSynchronized();
        this.params = new ArrayList<>();
        this.returnType = new JavaClass(md.getReturnType());
        this.calledMethodsNames = md.getMethodCalls();
        this.calledMethods = new HashMap<>();
        this.localVariables = new ArrayList<>();
        this.localVariables.add(new JavaField(Access.type_private, parent, "this"));
        Map<String, String> entry2 = md.getLocalVariables();
        for(Map.Entry<String, String> entry : entry2.entrySet()){
            this.localVariables.add(new JavaField(Access.type_private, entry.getValue(), entry.getKey()));
            //System.out.println("");
        }
        this.parent = parent;
        for(String s: md.getArgumentTypeNames()){
            this.params.add(new JavaClass(s));
        }
    }
    public String getFullName(){
        String returnVale = ""+parent.getFullName()+"|"+this;
        return returnVale;
    }
    public String getClassMethodName(){
        return this.parent.getName()+"::"+this.getName()+"()";
    }
    public String getName(){
        return this.name;
    }
    public Map<JavaMethod, Integer> getCalledMethod(){
        return this.calledMethods;
    }
    public void convertMethods(){
        for (Map.Entry<String, Integer> entry : calledMethodsNames.entrySet()){
            String fieldName;
            String methodName;
            if(entry.getKey().contains(".")){
                fieldName = entry.getKey().split("\\.")[0];
                methodName = entry.getKey().split("\\.")[1];
            }else{
                fieldName = "this";
                methodName = entry.getKey();
            }
            for(JavaField jf: localVariables){
                if(jf.getName().equals(fieldName)){
                    JavaClass fieldClass = jf.getType();
                    if(fieldClass != null)
                    {
                        if(calledMethods.containsKey(fieldClass.searchForMethod(methodName))){
                            Integer i =calledMethods.get(fieldClass.searchForMethod(methodName));
                            this.calledMethods.put(fieldClass.searchForMethod(methodName),(i+entry.getValue()));
                        }
                        else {
                            this.calledMethods.put(fieldClass.searchForMethod(methodName),entry.getValue());
                        }
                    }
                }
            }
        }
        System.out.print("");
    }
    void convertLocalVariables(List<JavaClass> allClasses){
        for(JavaField jf: localVariables){
            if(jf.getType() == null){
                jf.searchForType(allClasses);
            }
        }
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
    static Access accesTranslate(AccessModifier access){
        Access returnVale = Access.type_package_private;
        switch(access){
            case PUBLIC:
                returnVale = Access.type_public;
                break;
            case DEFAULT:
                returnVale = Access.type_package_private;
                break;
            case PRIVATE:
                returnVale = Access.type_private;
                break;
            case PROTECTED:
                returnVale = Access.type_protected;
                break;
        }
        return returnVale;
    }
}