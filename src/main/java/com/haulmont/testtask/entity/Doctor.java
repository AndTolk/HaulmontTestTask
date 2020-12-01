package com.haulmont.testtask.entity;

/*
 * Сущность врач
 */
public class Doctor {

    private long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String specialization;

    public long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getFullName() {
        return lastName + " " + firstName + " " + middleName;
    }

    public Doctor(long id, String lastName, String firstName, String middleName, String specialization) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.specialization = specialization;
    }
}