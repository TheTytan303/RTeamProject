package files.service;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.Set;

class MethodCallGatherer extends VoidVisitorAdapter<Set<String>> {
    @Override
    public void visit(MethodCallExpr mce, Set<String> m) {
        if (mce.getScope().isPresent()) {
            m.add(mce.getScope().get() + "." + mce.getName().asString());
        } else {
            m.add(mce.getName().asString());
        }
        super.visit(mce, m);
    }
}

