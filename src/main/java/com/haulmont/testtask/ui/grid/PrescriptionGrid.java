package com.haulmont.testtask.ui.grid;

import com.haulmont.testtask.entity.Prescription;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextArea;

import java.util.ArrayList;
import java.util.List;

import static com.haulmont.testtask.dao.PrescriptionDAO.*;

/*
 * Класс таблицы с рецептами.
 */
public class PrescriptionGrid extends Grid<Prescription> {

    /* Список рецептов загружающийся в таблицу */
    private List<Prescription> prescriptions;

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public PrescriptionGrid() {
        setRowHeight(125);

        this.prescriptions = readAllPrescriptions();

        addColumn(Prescription::getId).setHidden(true);

        addComponentColumn(prescription -> {
            TextArea ta = new TextArea();
            ta.setValue(prescription.getDescription());
            ta.setHeight("50%");
            ta.setReadOnly(true);
            return ta;
        }).setWidth(250).setCaption("Описание");


        addColumn(Prescription::getPatientFullName).setCaption("Пациент");
        addColumn(Prescription::getDoctorFullName).setCaption("Врач");
        addColumn(Prescription::getDateOfCreation).setCaption("Дата создания");
        addColumn(Prescription::getValidityPeriod).setCaption("Срок действия");
        addColumn(Prescription::getPriority).setCaption("Приоритет");

        setItems(prescriptions);
    }

    public void addItem(Prescription item) {
        addPrescription(item);
        updateGrid();
    }

    public void changeItem(Prescription oldItem, Prescription newItem) {
        changePrescription(oldItem, newItem);
        updateGrid();
    }

    public void deleteItem(Prescription item) {
        deletePrescription(item);
        updateGrid();
    }

    private void updateGrid() {
        setItems(new ArrayList<>());
        this.prescriptions = readAllPrescriptions();
        setItems(prescriptions);
    }
}