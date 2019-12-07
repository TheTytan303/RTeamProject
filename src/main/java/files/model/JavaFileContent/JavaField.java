package files.model.JavaFileContent;

import files.service.FieldDeclaration;

public class JavaField {
    private String name;
    private JavaClass type;
    private Access acces;
    //String type;
    private String code;

    JavaField(FieldDeclaration fd){
        this.name = fd.getName();
        //this.type = fd.getType();
    }
    JavaField(Access acces, JavaClass type, String name){
        this.type = type;
        this.acces = acces;
        this.name = name;
    }

    public void setCode(String code){
        this.code=code;
    }
}
