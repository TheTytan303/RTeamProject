//package io.github.TheTytan303.RTeamProject;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.expr.Name;

import java.io.FileNotFoundException;
import java.util.*;

public class Parser {

    public String getPackage(JavaFile jf) throws FileNotFoundException {
        String content = jf.getContent();
        CompilationUnit cu = StaticJavaParser.parse(content);
        Optional<PackageDeclaration> pd = cu.getPackageDeclaration();
        if (pd.isPresent()) {
            return pd.get().getName().asString();
        } else {
            return "";
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

    /* Pokazówka */
    public static void main(String[] args) {
        for (JavaFile jf : JavaFile.getFilesFrom(JavaFile.getProjectPath())) {
            try {
                Set<Import> im = Parser.getImports(jf);
                for (Import i : im) {
                    System.out.println(String.format("%s |||| %s , %s", i.getImportedPackage(), i.getImportedClass(), i));
                }
            } catch (FileNotFoundException ignore) {}
        }
    }
}
