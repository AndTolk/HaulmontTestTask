package com.haulmont.testtask.ui.subwindows;

import com.haulmont.testtask.Validator;
import com.haulmont.testtask.entity.Doctor;
import com.haulmont.testtask.entity.Patient;
import com.haulmont.testtask.entity.Prescription;
import com.haulmont.testtask.ui.grid.DoctorGrid;
import com.haulmont.testtask.ui.grid.PatientGrid;
import com.haulmont.testtask.ui.grid.PrescriptionGrid;
import com.vaadin.data.Binder;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.haulmont.testtask.dao.DoctorDAO.readAllDoctors;
import static com.haulmont.testtask.dao.DoctorDAO.readDoctorByParams;
import static com.haulmont.testtask.dao.PatientDAO.readAllPatients;
import static com.haulmont.testtask.dao.PatientDAO.readPatientByParams;

/*
 * Класс для создания окон с формами для добавления пациента/врача/рецепта. Для каждого существует свой конструктор.
 */
public class AddWindow extends Window {

    private AddWindow() {
        setClosable(false);
        setResizable(false);
        setModal(true);
        center();
    }

    /*
     * Конструктор для пациента
     */
    public AddWindow(PatientGrid patientGrid) {
        this();
        setWidth("25%");
        setHeight("40%");

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(false);

        FormLayout formLayout = new FormLayout();
        formLayout.setSizeFull();
        formLayout.setMargin(true);

        Binder<Patient> binder = new Binder<>();

        TextField lastNameField = new TextField("Фамилия");
        binder.forField(lastNameField).withValidator(Validator::nameValidate, "Введены неккоректные данные").bind(Patient::getLastName, Patient::setLastName);
        formLayout.addComponent(lastNameField);

        TextField firstNameField = new TextField("Имя");
        binder.forField(firstNameField).withValidator(Validator::nameValidate, "Введены неккоректные данные").bind(Patient::getFirstName, Patient::setFirstName);
        formLayout.addComponent(firstNameField);

        TextField middleNameField = new TextField("Отчество");
        binder.forField(middleNameField).withValidator(Validator::nameValidate, "Введены неккоректные данные").bind(Patient::getMiddleName, Patient::setMiddleName);
        formLayout.addComponent(middleNameField);

        TextField phoneNumberField = new TextField("Телефон");
        binder.forField(phoneNumberField).withValidator(Validator::phoneNumberValidate, "Введены неккоректные данные").bind(Patient::getPhoneNumber, Patient::setPhoneNumber);
        formLayout.addComponent(phoneNumberField);

        formLayout.setMargin(true);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSizeFull();
        Button addButton = new Button("ОК");
        addButton.addClickListener(clickEvent -> {
            if (!binder.isValid())
                createMessageWindow("Поля заполнены некорректно!");
            else {
                patientGrid.addItem(new Patient(0, lastNameField.getValue(), firstNameField.getValue(), middleNameField.getValue(), phoneNumberField.getValue()));
                close();
            }
        });
        Button cancelButton = new Button("Отменить");
        cancelButton.addClickListener(clickEvent -> close());
        buttonLayout.addComponent(addButton);
        buttonLayout.addComponent(cancelButton);
        buttonLayout.setComponentAlignment(addButton, Alignment.MIDDLE_CENTER);
        buttonLayout.setComponentAlignment(cancelButton, Alignment.MIDDLE_CENTER);
        buttonLayout.setMargin(new MarginInfo(true, true, false, false));

        layout.addComponent(formLayout);
        layout.addComponent(buttonLayout);

        setContent(layout);
    }

    /*
     * Конструктор для врача
     */
    public AddWindow(DoctorGrid doctorGrid) {
        this();
        setWidth("25%");
        setHeight("40%");

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(false);

        FormLayout formLayout = new FormLayout();
        formLayout.setSizeFull();
        formLayout.setMargin(true);

        Binder<Doctor> binder = new Binder<>();

        TextField lastNameField = new TextField("Фамилия");
        binder.forField(lastNameField).withValidator(Validator::nameValidate, "Введены неккоректные данные").bind(Doctor::getLastName, Doctor::setLastName);
        formLayout.addComponent(lastNameField);

        TextField firstNameField = new TextField("Имя");
        binder.forField(firstNameField).withValidator(Validator::nameValidate, "Введены неккоректные данные").bind(Doctor::getFirstName, Doctor::setFirstName);
        formLayout.addComponent(firstNameField);

        TextField middleNameField = new TextField("Отчество");
        binder.forField(middleNameField).withValidator(Validator::nameValidate, "Введены неккоректные данные").bind(Doctor::getMiddleName, Doctor::setMiddleName);
        formLayout.addComponent(middleNameField);

        TextField specializationField = new TextField("Специализация");
        binder.forField(specializationField).withValidator(Validator::specializationValidate, "Введены неккоректные данные").bind(Doctor::getSpecialization, Doctor::setSpecialization);
        formLayout.addComponent(specializationField);

        formLayout.setMargin(true);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSizeFull();
        Button addButton = new Button("ОК");
        addButton.addClickListener(clickEvent -> {
            if (!binder.isValid())
                createMessageWindow("Поля заполнены некорректно!");
            else {
                doctorGrid.addItem(new Doctor(0, lastNameField.getValue(), firstNameField.getValue(), middleNameField.getValue(), specializationField.getValue()));
                close();
            }
        });
        Button cancelButton = new Button("Отменить");
        cancelButton.addClickListener(clickEvent -> close());
        buttonLayout.addComponent(addButton);
        buttonLayout.addComponent(cancelButton);
        buttonLayout.setComponentAlignment(addButton, Alignment.MIDDLE_CENTER);
        buttonLayout.setComponentAlignment(cancelButton, Alignment.MIDDLE_CENTER);
        buttonLayout.setMargin(new MarginInfo(true, true, false, false));

        layout.addComponent(formLayout);
        layout.addComponent(buttonLayout);

        setContent(layout);
    }

    /*
     * Конструктор для рецепта
     */
    public AddWindow(PrescriptionGrid prescriptionGrid) {
        this();
        setWidth("35%");
        setHeight("60%");

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(false);

        FormLayout formLayout = new FormLayout();
        formLayout.setSizeFull();
        formLayout.setMargin(true);

        Binder<Prescription> binder = new Binder<>();

        TextArea descriptionField = new TextArea("Описание");
        binder.forField(descriptionField).withValidator(Validator::descriptionValidate, "Описание не должно быть меньше 5 знаков").bind(Prescription::getDescription, Prescription::setDescription);
        descriptionField.setSizeFull();
        formLayout.addComponent(descriptionField);

        List<String> patients = readAllPatients().stream().map(patient -> patient.getFullName() + " " + patient.getPhoneNumber()).collect(Collectors.toList());
        ComboBox<String> patientBox = new ComboBox<>("Пациент");
        patientBox.setSizeFull();
        patientBox.setItems(patients);
        patientBox.setEmptySelectionAllowed(false);
        formLayout.addComponent(patientBox);

        List<String> doctors = readAllDoctors().stream().map(doctor -> doctor.getFullName() + " " + doctor.getSpecialization()).collect(Collectors.toList());
        ComboBox<String> doctorBox = new ComboBox<>("Врач");
        doctorBox.setSizeFull();
        doctorBox.setItems(doctors);
        doctorBox.setEmptySelectionAllowed(false);
        formLayout.addComponent(doctorBox);

        DateField dateOfCreationField = new DateField("Дата создания");
        dateOfCreationField.setValue(LocalDate.now());
        dateOfCreationField.setTextFieldEnabled(false);
        dateOfCreationField.setSizeFull();
        formLayout.addComponent(dateOfCreationField);

        DateField validityPeriodField = new DateField("Срок действия");
        validityPeriodField.setValue(LocalDate.now());
        validityPeriodField.setTextFieldEnabled(false);
        validityPeriodField.setSizeFull();
        formLayout.addComponent(validityPeriodField);

        NativeSelect<String> priorityNativeSelect = new NativeSelect<>("Приоритет");
        priorityNativeSelect.setItems("Нормальный", "Срочный", "Немедленный");
        priorityNativeSelect.setEmptySelectionAllowed(false);
        priorityNativeSelect.setSelectedItem("Нормальный");
        priorityNativeSelect.setSizeFull();
        formLayout.addComponent(priorityNativeSelect);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidthFull();
        buttonLayout.setHeightUndefined();
        Button addButton = new Button("ОК");
        addButton.addClickListener(clickEvent -> {
            if (!binder.isValid() || patientBox.isEmpty() || doctorBox.isEmpty())
                createMessageWindow("Поля заполнены некорректно!");
            else if (!Validator.dateValidate(dateOfCreationField, validityPeriodField))
                createMessageWindow("Срок действия рецепта не может истечь раньше его создания!");
            else {
                String patientBoxValue = patientBox.getValue();
                String[] patientValues = patientBoxValue.split(" ", 4);
                Patient patient = readPatientByParams(patientValues[0], patientValues[1], patientValues[2], patientValues[3]);

                String doctorBoxValue = doctorBox.getValue();
                String[] doctorValues = doctorBoxValue.split(" ", 4);
                Doctor doctor = readDoctorByParams(doctorValues[0], doctorValues[1], doctorValues[2], doctorValues[3]);

                String dateOfCreation = dateOfCreationField.getValue().getDayOfMonth() + "." + dateOfCreationField.getValue().getMonthValue() + "." + dateOfCreationField.getValue().getYear();
                String validatyPeriod = validityPeriodField.getValue().getDayOfMonth() + "." + validityPeriodField.getValue().getMonthValue() + "." + validityPeriodField.getValue().getYear();

                prescriptionGrid.addItem(new Prescription(0, descriptionField.getValue(), patient, doctor, dateOfCreation, validatyPeriod, priorityNativeSelect.getSelectedItem().get()));
                close();
            }
        });

        Button cancelButton = new Button("Отменить");
        cancelButton.addClickListener(clickEvent -> close());
        buttonLayout.addComponent(addButton);
        buttonLayout.addComponent(cancelButton);
        buttonLayout.setComponentAlignment(addButton, Alignment.MIDDLE_CENTER);
        buttonLayout.setComponentAlignment(cancelButton, Alignment.MIDDLE_CENTER);
        buttonLayout.setMargin(false);

        layout.addComponent(formLayout);
        layout.addComponent(buttonLayout);
        layout.setExpandRatio(formLayout, 5);
        layout.setExpandRatio(buttonLayout, 1);
        setContent(layout);
    }

    /*
     * Метод создающий окно с сообщением используя класс "MessageWindow"
     * Используется в конструкторах в случае если пользователь ввел неверные данные и нажал кнопку "Ок"
     */
    private static void createMessageWindow(String s) {
        MessageWindow messageWindow = new MessageWindow(s);
        UI.getCurrent().addWindow(messageWindow);
    }
}