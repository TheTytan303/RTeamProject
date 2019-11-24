package files.service;

import com.github.javaparser.ast.body.Parameter;

import java.util.ArrayList;
import java.util.List;

public class MethodDeclaration {
    private com.github.javaparser.ast.body.MethodDeclaration md;

    public MethodDeclaration(com.github.javaparser.ast.body.MethodDeclaration md) {
        this.md = md;
    }

    public AccessModifier getAccessModifier() {
        if (this.md.isPrivate()) {
            return AccessModifier.PRIVATE;
        } else if (this.md.isProtected()) {
            return AccessModifier.PROTECTED;
        } else if (this.md.isPublic()) {
            return AccessModifier.PUBLIC;
        } else {
            return AccessModifier.DEFAULT;
        }
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
        return this.getAccessModifier() + " " + this.getReturnType()  + " " + getName() + " (" + getArgumentTypes() + ") {...}";
    }
}
