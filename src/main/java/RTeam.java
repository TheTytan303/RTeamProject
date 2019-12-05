import files.model.JavaFile;
import files.model.PackageFile;
import files.service.ClassDeclaration;
import files.service.MethodDeclaration;
import files.service.Parser;

import java.io.FileNotFoundException;
import java.util.Map;

public class RTeam {

    public static void main(String[] args){
        /*

            OLD STUFF

            //List<JavaFileContent> list = JavaFileContent.getFilesFrom(JavaFileContent.getProjectPath());
            //PackageFile pack = new PackageFile(JavaFile.getProjectPath());
            PackageFile pack = new PackageFile(JavaFile.getProjectPath());
            List<JavaFile> files = pack.getSubFiles();
            List<JavaClass> classes = pack.getSubClasses();
            List<JavaMethod> methods = pack.getSubMethods();
            //List<PackageFile> pack2 = pack.getPackages();
            System.out.println(files.get(5).getClassName());
            for(JavaMethod jm:methods){
                System.out.println(jm.getFullName());
            }
            for(JavaClass jc: classes){
                System.out.println(jc.getFullName());
            }
            System.out.println("kurdebele");

        */

        //moved main from Parser class to RTeam
        PackageFile pf = new PackageFile(JavaFile.getProjectPath());

        try {
            for (JavaFile jf : pf.getSubFiles()) {
                System.out.println(jf.getName());
                for (ClassDeclaration c : Parser.getClassesOrInterfaces(jf)) {
                    System.out.println(c.getName());
                    for (MethodDeclaration md : c.getMethods()) {
                        //System.out.println("  "+md.getName());
                        Map<String, String> lv = md.getLocalVariables();
                        for (String key : lv.keySet()) {
                          // System.out.println(String.format("    %s %s", lv.get(key), key));
                        }
                        Map<String, Integer> mc = md.getMethodCalls();
                        for (String key : mc.keySet()) {
                            //System.out.println(String.format("    %s (%s)", key, mc.get(key)));
                        }
                    }
                }
            }
        } catch (FileNotFoundException ignore) {}

       Graf.draw("Grafy", true);


        /*
        Map<String, Set<String>> g = package_graph(JavaFile.getProjectPath());
        for (String k : g.keySet()) {
            System.out.println(String.format("'%s' imports: (%d)", k, g.get(k).size()));
            for (String v : g.get(k)) {
                System.out.println(String.format("\t%s", v));
            }
        }

        Map<String, Map<String, Integer>> c = call_graph(JavaFile.getProjectPath());
        for (String caller : c.keySet()) {
            Map<String, Integer> calls = c.get(caller);
            System.out.println(String.format("'%s' calls: (%d)", caller, calls.size()));
            for (String callee : calls.keySet()) {
                System.out.println(String.format("\t%s (%d)", callee, calls.get(callee)));
            }
        }
        */

    }
}