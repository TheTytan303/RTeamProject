package files.service;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ClassDeclaration {
    private String pack;
    private ClassOrInterfaceDeclaration coid;
    private Set<MethodDeclaration> methods;
    private Set<FieldDeclaration> fields;

    public ClassDeclaration(ClassOrInterfaceDeclaration coid, Optional<String> pack) {
        this.pack = pack.orElse("default.package");
        this.coid = coid;
        this.methods = new HashSet<>();
        for (com.github.javaparser.ast.body.MethodDeclaration md : this.coid.getMethods()) {
            this.methods.add(new MethodDeclaration(md));
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

    public String getPackage() {
        return pack;
    }

    public boolean isAbstract() {
        return this.coid.isAbstract();
    }

    public String getName() {
        return this.coid.getName().asString();
    }

    public Set<MethodDeclaration> getMethods() {
        return methods;
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
        sb.append(this.getPackage()+".");
        sb.append(this.getName());
        sb.append(" {\n");
        for (FieldDeclaration fd : this.getFields()) {
            sb.append("\t"+fd+"\n");
        }
        for (MethodDeclaration md : this.getMethods()) {
            sb.append("\t"+md+"\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
