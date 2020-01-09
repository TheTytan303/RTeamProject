package files.service;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;

import java.util.*;

public class MethodDeclaration {
    private com.github.javaparser.ast.body.MethodDeclaration md;
    private com.github.javaparser.ast.body.ConstructorDeclaration cd;
    private String className;

    public MethodDeclaration(com.github.javaparser.ast.body.ConstructorDeclaration cd, String className) {
        this.cd = cd;
        this.className = className;
    }

    public MethodDeclaration(com.github.javaparser.ast.body.MethodDeclaration md) {
        this.md = md;
    }

    public AccessModifier getAccessModifier() {
        if (this.md != null) {
            if (this.md.isPrivate()) {
                return AccessModifier.PRIVATE;
            } else if (this.md.isProtected()) {
                return AccessModifier.PROTECTED;
            } else if (this.md.isPublic()) {
                return AccessModifier.PUBLIC;
            } else {
                return AccessModifier.DEFAULT;
            }
        } else {
            if (this.cd.isPrivate()) {
                return AccessModifier.PRIVATE;
            } else if (this.cd.isProtected()) {
                return AccessModifier.PROTECTED;
            } else if (this.cd.isPublic()) {
                return AccessModifier.PUBLIC;
            } else {
                return AccessModifier.DEFAULT;
            }
        }
    }

    public boolean isStatic() {
        if (this.md != null) {
            return this.md.isStatic();
        } else {
            return false;
        }
    }

    public boolean isSynchronized() {
        if (this.md != null) {
            return this.md.isSynchronized();
        } else {
            return false;
        }
    }

    public String getReturnType() {
        if (this.md != null) {
            return this.md.getType().asString();
        } else {
            return this.className;
        }
    }

    public String getName() {
        if (this.md != null) {
            return this.md.getName().asString();
        } else {
            return this.className;
        }
    }

    public List<String> getArgumentTypeNames() {
        List<String> args = new ArrayList<>();
        List<Parameter> params;
        if (this.md != null) {
            params = this.md.getParameters();
        } else {
            params = this.cd.getParameters();
        }
        for (Parameter p : params) {
            args.add(p.getType().asString());
        }
        return args;
    }

    /**
     * @return Mapping: variable name -> variable type
     */
    public Map<String, String> getLocalVariables() {
        Map<String, String> lv = new HashMap<>();
        List<VariableDeclarationExpr> vdes;
        if (this.md != null) {
            vdes = this.md.findAll(VariableDeclarationExpr.class);
        } else {
            vdes = this.cd.findAll(VariableDeclarationExpr.class);
        }
        for (VariableDeclarationExpr vde : vdes) {
            for (VariableDeclarator vd : vde.getVariables()) {
                lv.put(vd.getName().getIdentifier(), vd.getType().asString());
            }
        }
        return lv;
    }

    public Map<String, Integer> getMethodCalls() {
        Map<String, Integer> mc = new HashMap<>();
        List<MethodCallExpr> mces;
        if (this.md != null) {
            mces = this.md.findAll(MethodCallExpr.class);
        } else {
            mces = this.cd.findAll(MethodCallExpr.class);
        }
        for (MethodCallExpr mce : mces) {
            String key = "";
            if (mce.getScope().isPresent()) {
                key = mce.getScope().get().toString() + ".";
            }
            key += mce.getName();
            if (mc.containsKey(key)) {
                Integer count = mc.get(key);
                mc.replace(key, count+1);
            } else {
                mc.put(key, 1);
            }
        }
        return mc;
    }

    @Override
    public String toString() {
        return getAccessModifier() + " " + getReturnType()  + " " + getName() + " (" + getArgumentTypeNames() + ") {...}";
    }
}
