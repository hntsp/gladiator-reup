package com.codecool.gladiator.dao;

public abstract class DaoImplementationSupplier {
    private final static DaoType DAO_TYPE = DaoType.DATABASE;

    private final static DaoImplementation DAO_IMPLEMENTATION = new DaoImplementation(DAO_TYPE);

    public static DaoImplementation getDaoImplementation() {
        return DAO_IMPLEMENTATION;
    }
}
