package files.service;

public class FieldDeclaration {
    private com.github.javaparser.ast.body.FieldDeclaration fd;
    private int i;

    public FieldDeclaration(com.github.javaparser.ast.body.FieldDeclaration fd, int i) {
        this.fd = fd;
        this.i = i;
    }

    public String getAccess() {
        return this.fd.getAccessSpecifier().asString();
    }

    public boolean isStatic() {
        return this.fd.isStatic();
    }

    public String getType() {
        return this.fd.getCommonType().asString();
    }

    public String getName() {
        return this.fd.getVariable(i).toString();
    }

    @Override
    public String toString() {
        return this.getAccess() + " " + this.getType() + " " + getName();
    }
}
