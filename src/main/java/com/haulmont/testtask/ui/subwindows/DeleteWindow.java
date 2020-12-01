package com.haulmont.testtask.ui.subwindows;

import com.haulmont.testtask.dao.DAOException;
import com.haulmont.testtask.entity.Doctor;
import com.haulmont.testtask.entity.Patient;
import com.haulmont.testtask.entity.Prescription;
import com.haulmont.testtask.ui.grid.DoctorGrid;
import com.haulmont.testtask.ui.grid.PatientGrid;
import com.haulmont.testtask.ui.grid.PrescriptionGrid;
import com.vaadin.ui.*;


public class DeleteWindow extends Window {

    public DeleteWindow(PatientGrid patientGrid, Patient selected) {
        setModal(true);
        center();
        setWidth("20%");
        setHeight("15%");
        setClosable(false);
        setResizable(false);

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        setContent(layout);

        Label label = new Label("Вы действительно хотите удалить?");
        layout.addComponent(label);
        layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSizeFull();
        layout.addComponent(buttonLayout);

        Button okButton = new Button("Да");
        okButton.addClickListener(clickEvent -> {
            try {
                patientGrid.deleteItem(selected);
                close();
            } catch (DAOException e) {
                MessageWindow messageWindow = new MessageWindow("Невозможно удалить пациента, для которого существуют рецепты!");
                UI.getCurrent().addWindow(messageWindow);
                close();
            }
        });
        buttonLayout.addComponent(okButton);

        Button cancelButton = new Button("Нет");
        cancelButton.addClickListener(clickEvent -> close());
        buttonLayout.addComponent(cancelButton);

        buttonLayout.setComponentAlignment(okButton, Alignment.MIDDLE_CENTER);
        buttonLayout.setComponentAlignment(cancelButton, Alignment.MIDDLE_CENTER);
    }

    public DeleteWindow(DoctorGrid doctorGrid, Doctor selected) {
        setModal(true);
        center();
        setWidth("20%");
        setHeight("15%");
        setClosable(false);
        setResizable(false);

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        setContent(layout);

        Label label = new Label("Вы действительно хотите удалить?");
        layout.addComponent(label);
        layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSizeFull();
        layout.addComponent(buttonLayout);

        Button okButton = new Button("Да");
        okButton.addClickListener(clickEvent -> {
            try {
                doctorGrid.deleteItem(selected);
                close();
            } catch (DAOException e) {
                MessageWindow messageWindow = new MessageWindow("Невозможно удалить врача, для которого существуют рецепты!");
                UI.getCurrent().addWindow(messageWindow);
                close();
            }
        });
        buttonLayout.addComponent(okButton);

        Button cancelButton = new Button("Нет");
        cancelButton.addClickListener(clickEvent -> close());
        buttonLayout.addComponent(cancelButton);

        buttonLayout.setComponentAlignment(okButton, Alignment.MIDDLE_CENTER);
        buttonLayout.setComponentAlignment(cancelButton, Alignment.MIDDLE_CENTER);
    }

    public DeleteWindow(PrescriptionGrid prescriptionGrid, Prescription selected) {
        setModal(true);
        center();
        setWidth("20%");
        setHeight("15%");
        setClosable(false);
        setResizable(false);

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        setContent(layout);

        Label label = new Label("Вы действительно хотите удалить?");
        layout.addComponent(label);
        layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSizeFull();
        layout.addComponent(buttonLayout);

        Button okButton = new Button("Да");
        okButton.addClickListener(clickEvent -> {
            prescriptionGrid.deleteItem(selected);
            close();
        });
        buttonLayout.addComponent(okButton);

        Button cancelButton = new Button("Нет");
        cancelButton.addClickListener(clickEvent ->

                close());
        buttonLayout.addComponent(cancelButton);

        buttonLayout.setComponentAlignment(okButton, Alignment.MIDDLE_CENTER);
        buttonLayout.setComponentAlignment(cancelButton, Alignment.MIDDLE_CENTER);
    }
}