package files.model.JavaFileContent;

import files.service.FieldDeclaration;

import java.util.List;

public class JavaField {
    private JavaEntity parent;
    private String name;
    private JavaClass type;
    private Access access;
    private String stringType;
    private String code;

    JavaField(FieldDeclaration fd, JavaEntity parent){
        this.parent =parent;
        this.name = fd.getName();
        this.stringType = fd.getType();
        this.access = JavaMethod.accesTranslate(fd.getAccessModifier());
        if(this.stringType.contains("<")){
            int begin = stringType.indexOf('<');
            int end = stringType.indexOf('>');
            this.stringType = stringType.substring(begin+1,end);
        }
        if(this.stringType.contains(".")){
            String[] tmp = stringType.split("\\.");
            this.stringType = tmp[tmp.length-1];
        }
        if(this.stringType.contains("[")){
            String[] tmp = stringType.split("\\[");
            this.stringType = tmp[tmp.length-1];
        }

        try {
            this.type = PredefinedJavaClass.getPredefinedJavaClass(stringType);
        }catch (Exception e){
        }
        //System.out.print("");
    }
    public JavaField(Access access, JavaClass type, String name){
        this.type = type;
        this.access = access;
        this.name = name;
    }
    public JavaField(Access access, String type, String name){
        this.name = name;
        this.stringType = type;
        this.access = access;
        if(this.stringType.contains("<")){
            int begin = stringType.indexOf('<');
            int end = stringType.indexOf('>');
            this.stringType = stringType.substring(begin+1,end);
        }
        if(this.stringType.contains(".")){
            String[] tmp = stringType.split("\\.");
            this.stringType = tmp[tmp.length-1];
        }
        if(this.stringType.contains("[")){
            String[] tmp = stringType.split("\\[");
            this.stringType = tmp[0];
        }
        try {
            this.type = PredefinedJavaClass.getPredefinedJavaClass(stringType);
        }catch (Exception e){
        }
    }
    public void setCode(String code){
        this.code=code;
    }

    public JavaClass getType(){return this.type;}
    void searchForType(List<JavaClass> allClasses){
        for(JavaClass jc: allClasses){
            if(jc.getName().equals(this.stringType)){
                this.type = jc;
                return;
            }
        }
        System.out.println(this.stringType+ " " + this.name);
    }
    public String getName() {return name;}
}
