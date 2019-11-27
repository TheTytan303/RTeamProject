package files.model.JavaFileContent;

import files.service.MethodDeclaration;

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
        //...
    }

}
