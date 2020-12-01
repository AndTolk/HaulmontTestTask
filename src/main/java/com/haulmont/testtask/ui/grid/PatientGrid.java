package com.haulmont.testtask.ui.grid;

import com.haulmont.testtask.dao.DAOException;
import com.haulmont.testtask.entity.Patient;
import com.vaadin.ui.Grid;

import java.util.ArrayList;
import java.util.List;

import static com.haulmont.testtask.dao.PatientDAO.*;

/*
 * Класс таблицы с пациентами.
 */
public class PatientGrid extends Grid<Patient> {

    /* Список пациентов загружающийся в таблицу */
    private List<Patient> patients;

    public List<Patient> getPatients() {
        return patients;
    }

    public PatientGrid() {
        this.patients = readAllPatients();

        addColumn(Patient::getId).setHidden(true);
        addColumn(Patient::getLastName).setCaption("Фамилия");
        addColumn(Patient::getFirstName).setCaption("Имя");
        addColumn(Patient::getMiddleName).setCaption("Отчество");
        addColumn(Patient::getPhoneNumber).setCaption("Телефон");

        setItems(patients);
    }

    public void addItem(Patient item) {
        addPatient(item);
        updateGrid();
    }

    public void changeItem(Patient oldItem, Patient newItem) {
        changePatient(oldItem, newItem);
        updateGrid();
    }

    public void deleteItem(Patient item) throws DAOException {
        deletePatient(item);
        updateGrid();
    }

    private void updateGrid() {
        setItems(new ArrayList<>());
        this.patients = readAllPatients();
        setItems(patients);
    }
}