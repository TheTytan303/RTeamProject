package file.reading;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static void fillImports(List<JavaFile> files) {
        if (files == null) return;
        for (JavaFile jf : files) {
            String content;
            try {
                content = jf.getContent();
            } catch (FileNotFoundException e) {
                continue;
            }
            CompilationUnit cu = StaticJavaParser.parse(content);
            List<JavaFile> imports = new ArrayList<>();
            cu.findAll(ImportDeclaration.class).stream()
                    .forEach(i -> {
                        // TODO ktoś musi "przetłumaczyć" import na ścieżkę
                        imports.add(new JavaFile("./secret-path/"+i.getName()));
                    });
            jf.setImports(imports);
        }
    }

    /* Pokazówka */
    public static void main(String[] args) {
        Parser.fillImports(JavaFile.getFilesFrom(JavaFile.getProjectPath()));
    }
}
