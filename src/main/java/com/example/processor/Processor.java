package com.example.processor;

import androidx.room.Dao;
import androidx.room.Entity;

import com.example.annotations.Service;
import com.squareup.javapoet.TypeSpec;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import static com.example.processor.Constants.SUPPORTED_ANNOTATIONS;
import static javax.lang.model.SourceVersion.latestSupported;

/**
 * @author nassim niclas youssef
 */
public class Processor extends AbstractProcessor {
    private static boolean IS_DATABASE_CLASS_CREATED = false;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        ClassWriterHelper.getInstance().init(elementUtils, messager, filer);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return SUPPORTED_ANNOTATIONS;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return latestSupported();
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> setOfDoa = roundEnvironment.getElementsAnnotatedWith(Dao.class);
        Set<? extends Element> setOfEntities = roundEnvironment.getElementsAnnotatedWith(Entity.class);
        Set<? extends Element> setOfServices= roundEnvironment.getElementsAnnotatedWith(Service.class);

        TypeSpec database = RoomDatabaseGenerator.getInstance()
                .generateRoomDatabaseClass(setOfDoa, setOfEntities);
        if (!IS_DATABASE_CLASS_CREATED) {
            ClassWriterHelper.getInstance().createJavaFile(database);
            IS_DATABASE_CLASS_CREATED = true;
        }


        return true;
    }
}


