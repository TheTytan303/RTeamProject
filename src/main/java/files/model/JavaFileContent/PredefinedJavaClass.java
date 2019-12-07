package files.model.JavaFileContent;

import java.util.HashMap;
import java.util.Map;

/**
 * Class designed to create, manage and return predefined class in Java
 */
public class PredefinedJavaClass{
    static Map<String, JavaClass> predefined = new HashMap<>();
    private PredefinedJavaClass(){}

    /**
     * Always return the same class for same name, f.e:
     * name = "Integer" -> returns Integer1;
     * name = "int" -> returns Integer2;
     * Integer1 and Integer2 are references to the same object, so (Integer1 == Integer2) is always true;
     *
     * Throws ClassNotFoundException if there is no predefined class with specified name, f.e:
     * input = "City" -> throws ClassNotFoundException;
     */
    public static JavaClass getPredefinedJavaClass(String name) throws ClassNotFoundException{
        if(predefined.get(name)!=null){
            return predefined.get(name);
        }else{
            JavaClass tmp;
            switch(name){
                case "int":
                case "Integer":
                    tmp = PredefinedJavaClass.Integer();
                    predefined.put("int", tmp);
                    predefined.put("Integer", tmp);
                    break;
                case "double":
                case "Double":
                    tmp = PredefinedJavaClass.Double();
                    predefined.put("double", tmp);
                    predefined.put("Double", tmp);
                    break;
                case "float":
                case "Float":
                    tmp = PredefinedJavaClass.Float();
                    predefined.put("float", tmp);
                    predefined.put("Float", tmp);
                    break;
                case "Long":
                case "long":
                    tmp = PredefinedJavaClass.Long();
                    predefined.put("long", tmp);
                    predefined.put("Long", tmp);
                    break;
                case "String":
                    tmp = PredefinedJavaClass.String();
                    predefined.put("String", tmp);
                    predefined.put("string", tmp);
                    break;
                case "Char":
                case "char":
                    tmp = PredefinedJavaClass.Char();
                    predefined.put("char", tmp);
                    predefined.put("Char", tmp);
                    break;
                case "short":
                case "Short":
                    tmp = PredefinedJavaClass.Short();
                    predefined.put("short", tmp);
                    predefined.put("Short", tmp);
                    break;
                case "Dimension":
                    tmp = PredefinedJavaClass.Dimension();
                    predefined.put("Dimension", tmp);
                    break;
                case "Boolean":
                    tmp = PredefinedJavaClass.Boolean();
                    predefined.put("Boolean", tmp);
                    break;
                case "File":
                    tmp = PredefinedJavaClass.File();
                    predefined.put("File", tmp);
                    break;
                case "Name":
                    tmp = PredefinedJavaClass.Name();
                    predefined.put("Name", tmp);
                    break;
                default:
                    ClassNotFoundException exception = new ClassNotFoundException("No predefined class named \""+ name + "\" found");
                    throw exception;
            }
        }
        return predefined.get(name);
    }



    private static JavaClass Integer(){
        JavaClass Integer = new JavaClass("Integer");
        return Integer;
    }
    private static JavaClass String(){
        JavaClass Integer = new JavaClass("String");
        return Integer;
    }
    private static JavaClass Char(){
        JavaClass Integer = new JavaClass("char");
        return Integer;
    }
    private static JavaClass Double(){
        JavaClass Integer = new JavaClass("Double");
        return Integer;
    }
    private static JavaClass Long(){
        JavaClass Integer = new JavaClass("Long");
        return Integer;
    }
    private static JavaClass Short(){
        JavaClass Integer = new JavaClass("Short");
        return Integer;
    }
    private static JavaClass Float(){
        JavaClass Integer = new JavaClass("Float");
        return Integer;
    }
    private static JavaClass Dimension(){
        JavaClass Integer = new JavaClass("Dimension");
        return Integer;
    }
    private static JavaClass Boolean(){
        JavaClass Integer = new JavaClass("Boolean");
        return Integer;
    }
    private static JavaClass File(){
        JavaClass Integer = new JavaClass("File");
        return Integer;
    }
    private static JavaClass Name(){
        JavaClass Integer = new JavaClass("Name");
        return Integer;
    }
}
