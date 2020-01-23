package files.service;

import Graph.Relationship;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.*;
import com.sun.source.tree.CaseTree;
import files.model.JavaFile;
import files.model.PackageFile;

import java.io.FileNotFoundException;
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
            //args.add(p.getType().asString()); // oldo
            args.add(p.getType().asString()+"\\"+p.getName().asString()); // sorka Michał, szybka modyfikacja cobym dostawał też nazwy argumentów :D ~WC
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

    /**
     * McCabe Java Cyclomatic Complexity
     * @return cyclomatic complexity
     */
    public int getCyclomaticComplexity() {
        CallableDeclaration cd = this.md == null ? this.cd : this.md;
        int cc = 1;
        List<ReturnStmt> returnStatements = cd.findAll(ReturnStmt.class); /* Return Statements */
        cc += (returnStatements.size() > 1 ? returnStatements.size() : 0); // TODO very roughly true
        cc += cd.findAll(WhileStmt.class).size(); /* While Statements */
        cc += cd.findAll(DoStmt.class).size(); /* Do Loops */
        cc += cd.findAll(ForEachStmt.class).size(); /* ForEach Statements */
        cc += cd.findAll(ForStmt.class).size(); /* For Statements */
        cc += cd.findAll(ContinueStmt.class).size(); /* Continue Statements */
        cc += cd.findAll(BreakStmt.class).size(); /* Break Statements */

        List<IfStmt> ifStatements = cd.findAll(IfStmt.class); /* If Statements */
        cc += ifStatements.size();
        for (IfStmt is : ifStatements) {
            Optional<Statement> elseStatement = is.getElseStmt();
            if (elseStatement.isPresent()) {
                cc++;
            }
        }
        cc += cd.findAll(SwitchEntry.class).size(); /* Switch Entries (including default) */
        List<CatchClause> catchClauses = cd.findAll(CatchClause.class); /* Catch Clauses */
        cc += catchClauses.size();
        List<ThrowStmt> throwStatments = cd.findAll(ThrowStmt.class); /* Throw Statements */
        cc += throwStatments.size();
        /* TODO NODE javaparser seem to not have FinallyStmt */
        return cc;
    }

    @Override
    public String toString() {
        return getAccessModifier() + " " + getReturnType()  + " " + getName() + " (" + getArgumentTypeNames() + ") {...}";
    }
}
