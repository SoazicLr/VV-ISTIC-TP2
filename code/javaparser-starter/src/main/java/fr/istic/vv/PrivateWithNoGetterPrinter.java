package fr.istic.vv;

import com.github.javaparser.ast.AccessSpecifier;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;

import java.util.ArrayList;
import java.util.List;

public class PrivateWithNoGetterPrinter extends VoidVisitorWithDefaults<Void> {

    final List<FieldDeclaration> fdList = new ArrayList<>();
    int cpt = 0;

    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for(TypeDeclaration<?> type : unit.getTypes()) {
            type.accept(this, null);
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        // Visit Methods
        for(MethodDeclaration method : declaration.getMethods()) {
            method.accept(this, arg);
        }

        // Visit private Fields
        for(FieldDeclaration field : declaration.getFields()) {
            field.accept(this, arg);
        }

        for(BodyDeclaration<?> member : declaration.getMembers()) {
            if (member instanceof TypeDeclaration) {
                member.accept(this, arg);
            }
        }

        if (fdList.isEmpty()) return;

        System.out.println(declaration.getFullyQualifiedName().orElse("[Anonymous]"));
        fdList.forEach((fd) -> {
            String fieldName = fd.getVariables().get(0).getNameAsString();
            String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            List<MethodDeclaration> methods = declaration.getMethodsByName(getterName);
            if (methods.isEmpty()) {
                System.out.println("No getter : " + fd.getVariables().get(0).getNameAsString());
                cpt++;
            }
        });
        fdList.clear();
        System.out.println("total : " + cpt);
    }

    @Override
    public void visit(FieldDeclaration field, Void arg) {
        if (field.getAccessSpecifier().equals(AccessSpecifier.PRIVATE)) {
            fdList.add(field);
        }
    }
}