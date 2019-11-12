import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    static void fillImports(List<JavaFile> files) {
        if (files == null) return;
        for (JavaFile jf : files) {
            CompilationUnit cu = StaticJavaParser.parse(jf.getContent());
            List<JavaFile> imports = new ArrayList<>();
            cu.findAll(ImportDeclaration.class).stream()
                    .forEach(i -> {
                        // TODO ktoś musi "przetłumaczyć" import na ścieżkę
                        imports.add(new JavaFile(new File("./path/to/"+i.getName())));
                    });
            jf.setImports(imports);
        }
    }

    /* Pokazówka */
    public static void main(String[] args) {
        Parser.fillImports(JavaFile.getFilesFrom("."));
    }
}
