package files.service;

import com.github.javaparser.ast.body.EnumConstantDeclaration;

import java.util.HashSet;
import java.util.Set;

public class EnumDeclaration {
    private com.github.javaparser.ast.body.EnumDeclaration ed;
    private Set<String> constants;
    private Set<MethodDeclaration> methods;

    public EnumDeclaration(com.github.javaparser.ast.body.EnumDeclaration ed) {
        this.ed = ed;
        this.constants = new HashSet<>();
        for (EnumConstantDeclaration ecd : this.ed.getEntries()) {
            this.constants.add(ecd.getName().asString());
        }
        this.methods = new HashSet<>();
        for (com.github.javaparser.ast.body.MethodDeclaration md : this.ed.getMethods()) {
            this.methods.add(new MethodDeclaration(md));
        }
    }

    public String getName() {
        return this.ed.getName().asString();
    }

    public AccessModifier getAccessModifier() {
        if (this.ed.isPrivate()) {
            return AccessModifier.PRIVATE;
        } else if (this.ed.isProtected()) {
            return AccessModifier.PROTECTED;
        } else if (this.ed.isPublic()) {
            return AccessModifier.PUBLIC;
        } else {
            return AccessModifier.DEFAULT;
        }
    }

    public Set<String> getConstants() {
        return this.constants;
    }

    public Set<MethodDeclaration> getMethods() {
        return methods;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("enum ");
        sb.append(getName());
        sb.append(" {\n");
        for (String c : getConstants()) {
            sb.append("\t");
            sb.append(c);
            sb.append("\n");
        }
        for (MethodDeclaration md : getMethods()) {
            sb.append("\t");
            sb.append(md);
            sb.append("\n");
        }
        sb.append("}\n");
        return sb.toString();
    }
}
