package files.model.JavaFileContent;

import files.service.MethodDeclaration;

import java.util.ArrayList;
import java.util.List;

public class JavaMethod{
    String name;
    List<JavaClass> params;
    List<String> calledMethods;
    JavaClass returnVale;
    Boolean isStatic, isSynchronized;
    Access access;

    private String code;

    public JavaMethod(MethodDeclaration md){
        this.name = md.getName();
        if(md.getAccess() == "")
        {
            this.access = Access.type_private;
        }
        this.isStatic = md.isStatic();
        this.isSynchronized = md.isSynchronized();
        this.params = new ArrayList<>();
        this.returnVale = new JavaClass(md.getReturnType());
        for(String s: md.getArgumentTypes()){
            this.params.add(new JavaClass(s));
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
