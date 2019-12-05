package files.model.JavaFileContent;

import files.service.FieldDeclaration;

public class JavaField {
    String name;
    //JavaClass type;
    String type;
    private String code;

    JavaField(FieldDeclaration fd){
        this.name = fd.getName();
        this.type = fd.getType();
    }
}
