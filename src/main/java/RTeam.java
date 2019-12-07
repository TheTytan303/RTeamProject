import files.model.JavaFile;
import files.model.JavaFileContent.JavaClass;
import files.model.JavaFileContent.JavaMethod;
import files.model.JavaFileContent.PredefinedJavaClass;
import files.model.PackageFile;

import java.util.List;

public class RTeam {

    public static void main(String[] args){


        //----------Test helper for PredefinedJavaClass:
        try {
            JavaClass Integer = PredefinedJavaClass.getPredefinedJavaClass("int");
            JavaClass Integer2 = PredefinedJavaClass.getPredefinedJavaClass("Integer");
            if(Integer == Integer2){        //if(Integer and Integer2 are references to same objects)
                System.out.println(true);
            }else{
                System.out.println(false);
            }
            JavaClass Double2 = PredefinedJavaClass.getPredefinedJavaClass("double");
            JavaClass Double = PredefinedJavaClass.getPredefinedJavaClass("Double");
            if(Integer == Double){          //if(Integer and Double are references to same objects)
                System.out.println(true);
            }else{
                System.out.println(false);
            }
            JavaClass Double3 = PredefinedJavaClass.getPredefinedJavaClass("City"); //throws exception

            System.out.println("damn");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }





        System.out.println("kurdebele");
    }
}

//-------------------------------------------------------- OLD STUFF
    /*
PackageFile pack = new PackageFile(JavaFile.getProjectPath());
    List<JavaFile> jf = pack.getJavaFiles();
    List<JavaFile> files = pack.getSubFiles();
        pack.getPackages();
                List<JavaClass> classes = pack.getSubClasses();
        List<JavaMethod> methods = pack.getSubMethods();
        System.out.println(files.get(5).getClassName());
        for(JavaMethod jm:methods){
        System.out.println(jm.getFullName());
        }
        for(JavaClass jc: classes){
        System.out.println(jc.getFullName());
        }



        //*/