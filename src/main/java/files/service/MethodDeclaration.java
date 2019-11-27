package files.service;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.MethodCallExpr;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<String> getArgumentTypeNames() {
        List<String> args = new ArrayList<>();
        for (Parameter p : this.md.getParameters()) {
            args.add(p.getType().asString());
        }
        return args;
    }

    public Map<String, Integer> getMethodCalls() {
        Map<String, Integer> mc = new HashMap<>();
        for (MethodCallExpr mce : new HashSet<>(this.md.findAll(MethodCallExpr.class))) {
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
        return this.getAccessModifier() + " " + this.getReturnType()  + " " + getName() + " (" + getArgumentTypeNames() + ") {...}";
    }
}
