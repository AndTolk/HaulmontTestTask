package com.haulmont.testtask.dao;

/*
 * Класс исключений для выброса его из DAO классов
 */
public class DAOException extends Exception {

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}