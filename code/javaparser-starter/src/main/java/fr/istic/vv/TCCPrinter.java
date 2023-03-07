package fr.istic.vv;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;
import com.github.javaparser.utils.Pair;

import java.util.*;


// This class visits a compilation unit and
// calculate its TCC value
public class TCCPrinter extends VoidVisitorWithDefaults<Void> {

    private int methodCounter;
    private MethodDeclaration currentMethod;
    private final Map<String, List<MethodDeclaration>> accessMap = new HashMap<>();

    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for(TypeDeclaration<?> type : unit.getTypes()) {
            type.accept(this, null);
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        methodCounter = 0;
        currentMethod = null;
        accessMap.clear();

        // Visit private Fields
        for(FieldDeclaration field : declaration.getFields()) {
            field.accept(this, arg);
        }

        // Visit Methods
        for(MethodDeclaration method : declaration.getMethods()) {
            method.accept(this, arg);
        }

        if (methodCounter == 0 || methodCounter == 1) {
            System.out.print(declaration.getFullyQualifiedName().orElse("[Anonymous]") + " : ");
            System.out.println(1);
            return;
        }

        int denominator = methodCounter * (methodCounter -1) / 2;
        int numerator = 0;
        Set<Pair<MethodDeclaration, MethodDeclaration>> links = new HashSet<>();
        for (List<MethodDeclaration> mdList : accessMap.values()) {
            for (int i = 0; i<mdList.size(); i++) {
                MethodDeclaration md1 = mdList.get(i);
                for (int j = i+1; j<mdList.size(); j++) {
                    MethodDeclaration md2 = mdList.get(j);
                    links.add(new Pair<>(md1, md2));
                }
            }
        }

        numerator = links.size();

        System.out.print(declaration.getFullyQualifiedName().orElse("[Anonymous]") + " : ");
        System.out.print(numerator + "/" + denominator + " -- ");
        System.out.println(((double) numerator)/denominator);
    }

    @Override
    public void visit(FieldDeclaration field, Void arg) {
        accessMap.putIfAbsent(field.getVariables().get(0).getNameAsString(), new ArrayList<>());
    }


    @Override
    public void visit(MethodDeclaration method, Void arg) {
        currentMethod = method;
        methodCounter++;
        method.getBody().ifPresent((body) -> body.accept(this, arg));
    }

    @Override
    public void visit(FieldAccessExpr expr, Void arg) {
        if (expr.getScope().toString().equals("this")) {
            List<MethodDeclaration> mdList = accessMap.get(expr.getNameAsString());
            if (mdList != null) {
                mdList.add(currentMethod);
            }
        }
    }

    @Override
    public void defaultAction(Node n, Void arg) {
        super.defaultAction(n, arg);
        n.getChildNodes().forEach((child) -> child.accept(this, arg));
    }
}
