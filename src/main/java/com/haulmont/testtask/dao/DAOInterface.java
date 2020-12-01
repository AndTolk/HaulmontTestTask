package com.haulmont.testtask.dao;

public interface DAOInterface {

    String DRIVER = "org.hsqldb.jdbc.JDBCDriver";
    String CONNECTION = "jdbc:hsqldb:file:" + System.getProperty("user.dir") + "/src/main/resources/dbfiles/";
    String CONNECTION_LOGIN = "SA";
    String CONNECTION_PASSWORD = "";
}
