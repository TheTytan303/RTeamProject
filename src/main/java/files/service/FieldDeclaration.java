package files.service;

public class FieldDeclaration {
    private com.github.javaparser.ast.body.FieldDeclaration fd;
    private int i;

    public FieldDeclaration(com.github.javaparser.ast.body.FieldDeclaration fd, int i) {
        this.fd = fd;
        this.i = i;
    }

    public AccessModifier getAccessModifier() {
        if (this.fd.isPrivate()) {
            return AccessModifier.PRIVATE;
        } else if (this.fd.isProtected()) {
            return AccessModifier.PROTECTED;
        } else if (this.fd.isPublic()) {
            return AccessModifier.PUBLIC;
        } else {
            return AccessModifier.DEFAULT;
        }
    }

    public boolean isStatic() {
        return this.fd.isStatic();
    }

    public boolean isVolatile() {
        return this.fd.isVolatile();
    }

    public boolean isTransient() {
        return this.fd.isTransient();
    }

    public String getType() {
        return this.fd.getCommonType().asString();
    }

    public String getName() {
        return this.fd.getVariable(i).toString();
    }

    @Override
    public String toString() {
        return this.getAccessModifier() + " " + this.getType() + " " + getName();
    }
}
