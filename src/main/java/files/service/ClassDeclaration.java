package files.service;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import files.model.JavaFile;

import java.io.FileNotFoundException;
import java.util.*;

public class ClassDeclaration {
    private JavaFile sourceFile;
    private String pack;
    private ClassOrInterfaceDeclaration coid;
    private Set<MethodDeclaration> methods;
    private Map<String, MethodDeclaration> methodsByName;
    private Set<FieldDeclaration> fields;

    public ClassDeclaration(ClassOrInterfaceDeclaration coid, JavaFile sourceFile) {
        this.sourceFile = sourceFile;
        try {
            this.pack = Parser.getPackage(this.sourceFile).orElse("default.package");
        } catch (FileNotFoundException ignore) {}
        this.coid = coid;
        this.methods = new HashSet<>();
        for (com.github.javaparser.ast.body.MethodDeclaration md : this.coid.getMethods()) {
            this.methods.add(new MethodDeclaration(md));
        }
        this.methodsByName = new HashMap<>();
        for (MethodDeclaration md : this.methods) {
            this.methodsByName.put(md.getName(), md);
        }
        this.fields = new HashSet<>();
        List<com.github.javaparser.ast.body.FieldDeclaration> fds = this.coid.getFields();
        for (com.github.javaparser.ast.body.FieldDeclaration fd : this.coid.getFields()) {
            if (fd.getVariables().size() > 1) {
                for (int i = 0; i < fd.getVariables().size(); i++) {
                    this.fields.add(new FieldDeclaration(fd, i));
                }
            } else {
                this.fields.add(new FieldDeclaration(fd, 0));
            }
        }
    }

    public JavaFile getSourceFile() {
        return this.sourceFile;
    }

    public String getPackage() {
        return pack;
    }

    public boolean isAbstract() {
        return this.coid.isAbstract();
    }

    public boolean isInterface() {
        return this.coid.isInterface();
    }

    public String getName() {
        return this.coid.getName().asString();
    }

    public Set<MethodDeclaration> getMethods() {
        return methods;
    }

    public MethodDeclaration getMethod(String name) {
        return this.methodsByName.get(name);
    }

    public Set<FieldDeclaration> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.isAbstract()) {
            sb.append("abstract");
        }
        sb.append("class ");
        sb.append(this.getPackage());
        sb.append(".");
        sb.append(this.getName());
        sb.append(" {\n");
        for (FieldDeclaration fd : this.getFields()) {
            sb.append("\t");
            sb.append(fd);
            sb.append("\n");
        }
        for (MethodDeclaration md : this.getMethods()) {
            sb.append("\t");
            sb.append(md);
            sb.append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
