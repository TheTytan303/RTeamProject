package files.service;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.Name;
import files.model.JavaFile;
import files.model.PackageFile;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {

    /**
     * Parses the given file and returns the declared package name.
     * @param jf JavaFile
     * @return Name of the package
     * @throws FileNotFoundException
     */
    public static Optional<String> getPackage(JavaFile jf) throws FileNotFoundException {
        String content = jf.getContent();
        CompilationUnit cu = StaticJavaParser.parse(content);
        Optional<PackageDeclaration> pd = cu.getPackageDeclaration();
        return pd.map(packageDeclaration -> packageDeclaration.getName().asString());
    }

    /**
     * Gathers the imports in given file
     * @param jf JavaFile
     * @return Set of Imports
     * @throws FileNotFoundException
     */
    public static Set<Import> getImports(JavaFile jf) throws FileNotFoundException {
        Set<Import> imports = new HashSet<>();
        String content = jf.getContent();
        CompilationUnit cu = StaticJavaParser.parse(content);
        for (ImportDeclaration id : cu.getImports())  {
            Name importName = id.getName();
            String fullImport = importName.asString();
            Optional<Name> qualifier = importName.getQualifier();
            if (id.isAsterisk() || !qualifier.isPresent()) {
                imports.add(new Import(fullImport));
            } else {
                String fromPackage = qualifier.get().asString();
                String importedClass = importName.getIdentifier();
                imports.add(new Import(fromPackage, importedClass));
            }
        }
        return imports;
    }

    private static Map<String, Set<String>> _package_graph(PackageFile pf, Map<String, Set<String>> g) {
        for (JavaFile jf : pf.getJavaFiles()) {
            Set<Import> im;
            String key;
            try {
                key = getPackage(jf).orElse("default.package");
                im = Parser.getImports(jf);
            } catch (FileNotFoundException e) {
                continue;
            }
            for (Import i : im) {
                if (g.containsKey(key)) {
                    g.get(key).add(i.toString());
                } else {
                    Set<String> s = new HashSet<>();
                    s.add(i.toString());
                    g.put(key, s);
                }
            }
        }
        for (PackageFile spf : pf.getPackages()) {
            _package_graph(spf, g);
        }
        return g;
    }

    private static Map<String, Map<String, Integer>> _call_graph(PackageFile pf, Map<String, Map<String, Integer>> g) {
        for (JavaFile jf : pf.getJavaFiles()) {
            Set<ClassDeclaration> classDeclarations;
            try {
                classDeclarations = getClassesOrInterfaces(jf);
            } catch (FileNotFoundException e) {
                continue;
            }
            for (ClassDeclaration cd : classDeclarations) {
                for (MethodDeclaration md : cd.getMethods()) {
                    g.put(cd.getName() + "." + md.getName(), md.getMethodCalls());
                }
            }
        }
        for (PackageFile spf : pf.getPackages()) {
            _call_graph(spf, g);
        }
        return g;
    }

    /**
     * Constructs a mapping: package imports a set of packages; Map<String, Set<String>>
     * @param path directory to be scanned
     * @return Mapping
     */
    public static Map<String, Set<String>> package_graph(String path) {
        Map<String, Set<String>> g = new HashMap<>();
        return _package_graph(new PackageFile(path), g);
    }

    /**
     * Constructs a mapping: method calls other methods; the inner map
     * contains the calledfunction and the number of times it was called
     * @param path
     * @return
     */
    public static Map<String, Map<String, Integer>> call_graph(String path) {
        Map<String, Map<String, Integer>> g = new HashMap<>();
        return _call_graph(new PackageFile(path), g);
    }

    public static Set<ClassDeclaration> getClassesOrInterfaces(JavaFile jf) throws FileNotFoundException {
        Set<ClassDeclaration> cds = new HashSet<>();
        String content = jf.getContent();
        CompilationUnit cu = StaticJavaParser.parse(content);
        Set<ClassOrInterfaceDeclaration> cd = new HashSet<>(cu.findAll(ClassOrInterfaceDeclaration.class));
        for (ClassOrInterfaceDeclaration c : cd) {
            cds.add(new ClassDeclaration(c, getPackage(jf)));
        }
        return cds;
    }

    public static void main(String[] args) {
        try {
            PackageFile pf = new PackageFile(JavaFile.getProjectPath());
            for (JavaFile jf : pf.getSubFiles()) {
                for (ClassDeclaration c : Parser.getClassesOrInterfaces(jf)) {
                    System.out.println(c);
                }
            }
        } catch (FileNotFoundException ignore) {}

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
    }
}
