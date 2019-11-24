package files.service;

import com.github.javaparser.ast.body.Parameter;

import java.util.ArrayList;
import java.util.List;

public class MethodDeclaration {
    private com.github.javaparser.ast.body.MethodDeclaration md;

    public MethodDeclaration(com.github.javaparser.ast.body.MethodDeclaration md) {
        this.md = md;
    }

    public String getAccess() {
        return this.md.getAccessSpecifier().asString();
    }

    public boolean isStatic() {
        return this.md.isStatic();
    }

    public boolean isSynchronized() {
        return this.md.isSynchronized();
    }

    public String getReturnType() {
        return this.md.getType().asString();
    }

    public String getName() {
        return this.md.getName().asString();
    }

    public List<String> getArgumentTypes() {
        List<String> args = new ArrayList<>();
        for (Parameter p : this.md.getParameters()) {
            args.add(p.getName().asString());
        }
        return args;
    }

    /* TODO one can search for method calls there */
    /* public List<?> getMethodCalls() {} */

    @Override
    public String toString() {
        return this.getAccess() + " " + this.getReturnType()  + " " + getName() + " (" + getArgumentTypes() + ") {...}";
    }
}
