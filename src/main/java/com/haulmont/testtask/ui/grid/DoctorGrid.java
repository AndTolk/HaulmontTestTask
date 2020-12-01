package com.haulmont.testtask.ui.grid;

import com.haulmont.testtask.dao.DAOException;
import com.haulmont.testtask.entity.Doctor;
import com.vaadin.ui.Grid;

import java.util.ArrayList;
import java.util.List;

import static com.haulmont.testtask.dao.DoctorDAO.*;

/*
 * Класс таблицы с врачами.
 */
public class DoctorGrid extends Grid<Doctor> {

    /* Список врачей загружающийся в таблицу */
    private List<Doctor> doctors;

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public DoctorGrid() {
        this.doctors = readAllDoctors();

        addColumn(Doctor::getId).setHidden(true);
        addColumn(Doctor::getLastName).setCaption("Фамилия");
        addColumn(Doctor::getFirstName).setCaption("Имя");
        addColumn(Doctor::getMiddleName).setCaption("Отчество");
        addColumn(Doctor::getSpecialization).setCaption("Специализация");

        setItems(doctors);
    }

    public void addItem(Doctor item) {
        addDoctor(item);
        updateGrid();
    }

    public void changeItem(Doctor oldItem, Doctor newItem) {
        changeDoctor(oldItem, newItem);
        updateGrid();
    }

    public void deleteItem(Doctor item) throws DAOException {
        deleteDoctor(item);
        updateGrid();
    }

    private void updateGrid() {
        setItems(new ArrayList<>());
        this.doctors = readAllDoctors();
        setItems(doctors);
    }
}