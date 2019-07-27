package com.example.processor;

import androidx.room.Database;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

import static com.example.processor.Constants.ROOM_DATABASE_SUPER_CLASS;

class RoomDatabaseGenerator {
    private static final RoomDatabaseGenerator ourInstance = new RoomDatabaseGenerator();

    private RoomDatabaseGenerator() {
    }

    static RoomDatabaseGenerator getInstance() {
        return ourInstance;
    }

    TypeSpec generateRoomDatabaseClass(Set<? extends Element> daos, Set<? extends Element> enties) {
        TypeSpec.Builder roomDatabaseClassBuilder = TypeSpec.classBuilder("database")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .superclass(TypeVariableName.get(ROOM_DATABASE_SUPER_CLASS));

        AnnotationSpec.Builder databaseAnnotationBuilder = AnnotationSpec.builder(Database.class)
                .addMember("version", "1")
                .addMember("exportSchema" , "true");
        StringBuilder stringBuilder = new StringBuilder("{");

        int index = 0;
        for (Element entity :
                enties) {
            String entityName = ClassWriterHelper.getInstance().getElementTypeFullName(entity);
            stringBuilder.append(entityName).append(".class");
            if(index<enties.size()-1){
                stringBuilder.append(",\n");
            }
            index+=1;
        }
        stringBuilder.append("}");

        databaseAnnotationBuilder.addMember("entities", stringBuilder.toString());

        for (Element element :
                daos) {
            roomDatabaseClassBuilder.addMethod(ClassWriterHelper.getInstance().getAbstractMethod(element));
        }

        roomDatabaseClassBuilder.addAnnotation(databaseAnnotationBuilder.build());

        return roomDatabaseClassBuilder.build();
    }
}
