package com.example.processor;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;

import static com.example.processor.Constants.GETTER;
import static com.example.processor.Constants.RETURN;
import static java.text.MessageFormat.format;
import static java.util.Locale.ENGLISH;
import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;

/**
 * @author nassim niclas youssef
 * <p>
 * Utility Class for Creating Java Class Elements
 */
class ClassWriterHelper {
    private static final ClassWriterHelper OUR_INSTANCE = new ClassWriterHelper();
    private Elements elementUtils;
    private Messager messager;
    private Filer filer;

    private ClassWriterHelper() {

    }

    static ClassWriterHelper getInstance() {
        return OUR_INSTANCE;
    }

    void init(Elements elementUtils, Messager messager,Filer filer) {
        this.elementUtils = elementUtils;
        this.messager = messager;
        this.filer=filer;
    }

    MethodSpec generateGetters(Element field) {
        return MethodSpec
                .methodBuilder(format(GETTER, field.getSimpleName().toString().toUpperCase(ENGLISH)))
                .returns(TypeVariableName.get(getElementTypeFullName(field)))
                .addStatement(format(RETURN, field.getSimpleName().toString()))
                .addModifiers(PUBLIC)
                .build();
    }

    MethodSpec getAbstractMethod(Element element) {
        return MethodSpec
                .methodBuilder(format(GETTER, element.getSimpleName().toString()))
                .addModifiers(PUBLIC, ABSTRACT)
                .returns(TypeVariableName.get(getElementTypeFullName(element)))
                .build();
    }

    String getElementTypeFullName(Element element) {
        return elementUtils.getPackageOf(element)
                .getQualifiedName()
                .toString()
                .concat(".")
                .concat(element.getSimpleName().toString());
    }

    void createJavaFile( TypeSpec typeSpec) {
        try {
            JavaFile.builder(Constants.ROOM_DATABASE_PACKAGE, typeSpec).build().writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
