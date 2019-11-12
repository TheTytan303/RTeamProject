import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Parser {

    static void fillImports(List<JavaFile> files) {
        if (files == null) return;
        for (JavaFile jf : files) {
            CompilationUnit cu = StaticJavaParser.parse(jf.getContent());
            cu.findAll(ImportDeclaration.class).stream()
                    .forEach(i -> {
                        System.out.println(i.getName());
                    });
        }
    }

    /* Pokazówka */
    public static void main(String[] args) {
        Parser.fillImports(JavaFile.getFilesFrom("."));
    }
}
