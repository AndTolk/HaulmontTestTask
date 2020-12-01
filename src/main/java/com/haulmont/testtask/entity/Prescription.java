package com.haulmont.testtask.entity;

/*
 * Сущность рецепт
 */
public class Prescription {

    long id;
    private String description;
    private Patient patient;
    private Doctor doctor;
    private String dateOfCreation;
    private String validityPeriod;
    private String priority;

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getPatientFullName() {
        return patient.getFullName();
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getDoctorFullName() {
        return doctor.getFullName();
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public String getPriority() {
        return priority;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Prescription(long id, String description, Patient patient, Doctor doctor, String dateOfCreation, String validityPeriod, String priority) {
        this.id = id;
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
        this.dateOfCreation = dateOfCreation;
        this.validityPeriod = validityPeriod;
        this.priority = priority;
    }
}