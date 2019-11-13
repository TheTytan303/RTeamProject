//package io.github.TheTytan303.RTeamProject;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.Name;

import java.io.FileNotFoundException;
import java.util.*;

public class Parser {

    public static Set<Import> getImports(JavaFile jf) {
        Set<Import> imports = new HashSet<>();
        String content;
        try {
            content = jf.getContent();
        } catch (FileNotFoundException e) {
            return imports;
        }
        CompilationUnit cu = StaticJavaParser.parse(content);
        for (ImportDeclaration id : cu.getImports())  {
            Name importName = id.getName();
            String fullImport = importName.asString();
            Optional<Name> qualifier = importName.getQualifier();
            if (qualifier.isPresent()) {
                String fromPackage = qualifier.get().asString();
                String importedClass = importName.removeQualifier().asString();
                imports.add(new Import(fromPackage, importedClass));
            } else {
                imports.add(new Import(fullImport));
            }
        }
        return imports;
    }

    /* Pokazówka */
    public static void main(String[] args) {
        for (JavaFile jf : JavaFile.getFilesFrom(JavaFile.getProjectPath())) {
            Set<Import> im = Parser.getImports(jf);
            for (Import i : im) {
                System.out.println(i);
            }
        }
    }
}
