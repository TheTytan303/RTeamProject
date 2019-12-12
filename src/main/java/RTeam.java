import Graph.Graph;
import Graph.Relationship;
import files.model.JavaFile;
import files.model.JavaFileContent.JavaClass;
import files.model.JavaFileContent.JavaMethod;
import files.model.JavaFileContent.PredefinedJavaClass;
import files.model.PackageFile;

import files.service.ClassDeclaration;
import files.service.Parser;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class RTeam {

    public static void story1(){
        PackageFile pack = new PackageFile(JavaFile.getProjectPath());
        List<JavaClass> classes = pack.getSubClasses();
        List<JavaFile> files = pack.getSubFiles();
        Map<JavaMethod, Integer> map = classes.get(3).getMethodsCall();

        ArrayList<String> fileName = new ArrayList<>();
        ArrayList<Long> fileSize = new ArrayList<>();
        ArrayList<Relationship> relationships = new ArrayList<>();
        try {
            for (JavaFile jf : pack.getSubFiles()) {
                fileName.add(jf.getName());
                fileSize.add(jf.getSize());

                ArrayList<String> relationshipNames = new ArrayList<>();
                for(JavaFile a : jf.getImports()) {
                    relationshipNames.add(a.getName());
                }
                relationships.add(new Relationship(jf.getName(), relationshipNames));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Graph applet = new Graph();
        applet.importData(fileName, fileSize, relationships);
        applet.draw("Dependencies", true);
    }

    public static void story2(){
        PackageFile pack = new PackageFile(JavaFile.getProjectPath());

        ArrayList<String> methodName = new ArrayList<>();
        ArrayList<Long> methodCallCount = new ArrayList<>();
        ArrayList<Relationship> relationships = new ArrayList<>();

        List<JavaFile> files =pack.getSubFiles();
        List<JavaMethod> methods= new ArrayList<>();

        for(JavaFile jf: files){
            methods.addAll(jf.getMethods());
        }

        for (JavaMethod jm : methods) {
            Map<JavaMethod, Integer> calledMethods = jm.getCalledMethod();

            Iterator<Map.Entry<JavaMethod, Integer>> itr = calledMethods.entrySet().iterator();

            while(itr.hasNext()) {
                Map.Entry<JavaMethod, Integer> entry = itr.next();

                if(entry.getKey() != null) {
                    methodName.add(entry.getKey().getName());
                    methodCallCount.add((long) entry.getValue());
                    ArrayList<String> calls = new ArrayList<>();

                    Map<JavaMethod, Integer> innerCalls = entry.getKey().getCalledMethod();
                    Iterator<Map.Entry<JavaMethod, Integer>> innerItr = innerCalls.entrySet().iterator();

                    while(innerItr.hasNext()) {
                        Map.Entry<JavaMethod, Integer> call = innerItr.next();
                        if(call.getKey() != null) {
                            calls.add(call.getKey().getName());
                        }
                    }

                    relationships.add(new Relationship(entry.getKey().getName(), calls));
                }
            }
        }

        Graph applet = new Graph();
        applet.importData(methodName, methodCallCount, relationships);
        applet.draw("Method calls", true);
    }

    public static void main(String[] args){
        story2();

    }
}

/*
    //Test helper for PredefinedJavaClass:
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

        }
    } catch (FileNotFoundException ignore) {}

*/