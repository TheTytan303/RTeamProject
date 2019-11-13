package files.service;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.expr.Name;
import files.model.JavaFile;
import files.model.PackageFile;

import java.io.FileNotFoundException;
import java.util.*;

public class Parser {

    public static String getPackage(JavaFile jf) throws FileNotFoundException {
        String content = jf.getContent();
        CompilationUnit cu = StaticJavaParser.parse(content);
        Optional<PackageDeclaration> pd = cu.getPackageDeclaration();
        if (pd.isPresent()) {
            return pd.get().getName().asString();
        } else {
            return "?";
        }
    }

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

    public static void parse(PackageFile pf) {
        for (JavaFile jf : pf.getJavaFiles()) {
            Set<Import> im;
            System.out.println(String.format("PackageFile: %s", jf.getPath()));
            try {
                im = Parser.getImports(jf);
            } catch (FileNotFoundException e) {
                continue;
            }
            for (Import i : im) {
                System.out.println(String.format("\t%s", i));
            }
        }
        for (PackageFile spf : pf.getPackages()) {
            parse(spf);
        }
    }

    public static void main(String[] args) {
        parse(new PackageFile(JavaFile.getProjectPath()));
    }
}
