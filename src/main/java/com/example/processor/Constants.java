package com.example.processor;


import androidx.room.Dao;
import androidx.room.Database;

import com.example.annotations.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

class Constants {

    //Supported Annotation
    static final Set<String> SUPPORTED_ANNOTATIONS = new TreeSet<>(Arrays.asList(
            Database.class.getCanonicalName(),
            Dao.class.getCanonicalName(),
            Service.class.getCanonicalName()));

    //room database
    static final String ROOM_DATABASE_PACKAGE = "room";
    static final String ROOM_DATABASE_MODULE = "RoomDatabaseModule";
    static final String ROOM_DATABASE_SUPER_CLASS = "androidx.room.RoomDatabase";

    //Class Constants
    static final String GETTER = "get{0}";
    static final String RETURN = "return {}";


    private Constants() {
        //Constant Class
    }
}
