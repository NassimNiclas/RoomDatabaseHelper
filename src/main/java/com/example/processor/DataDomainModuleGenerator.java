package com.example.processor;

public class DataDomainModuleGenerator {
    private static final DataDomainModuleGenerator ourInstance = new DataDomainModuleGenerator();

    public static DataDomainModuleGenerator getInstance() {
        return ourInstance;
    }

    private DataDomainModuleGenerator() {
    }
}
