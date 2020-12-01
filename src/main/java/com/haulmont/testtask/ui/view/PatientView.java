package com.haulmont.testtask.ui.view;

import com.haulmont.testtask.dao.DAOException;
import com.haulmont.testtask.entity.Patient;
import com.haulmont.testtask.ui.grid.PatientGrid;
import com.haulmont.testtask.ui.subwindows.AddWindow;
import com.haulmont.testtask.ui.subwindows.ChangeWindow;
import com.haulmont.testtask.ui.subwindows.DeleteWindow;
import com.haulmont.testtask.ui.subwindows.MessageWindow;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

/*
 * Класс контента на экране с таблицой пациентов
 */
public class PatientView extends VerticalLayout implements View {

    /* Таблица с пацентами */
    private PatientGrid patientGrid;
    /* Выбранная пользователем строчка в таблице */
    private Patient selectedRow;

    /*
     * Базовый конструктор.
     * Создание панели с кнопками и создание таблицы вынесены в методы addMenuBar() и addGrid()
     */
    public PatientView() {
        setSizeFull();
        addMenuBar();
        addGrid();
    }

    /*
     * Создание панели с кнопками над таблицей.
     * Добавляется: фильтр по ФИО, кнопки добавить, изменить, удалить.
     */
    private void addMenuBar() {
        HorizontalLayout menuBar = new HorizontalLayout();

        TextField nameFilter = new TextField();
        Button addButton = new Button();
        Button changeButton = new Button();
        Button deleteButton = new Button();

        nameFilter.setPlaceholder("Поиск по ФИО");
        nameFilter.addValueChangeListener(valueChangeEvent -> {
            ListDataProvider<Patient> dataProvider = (ListDataProvider<Patient>) patientGrid.getDataProvider();
            dataProvider.setFilter(Patient::getFullName, s -> caseInsensitiveContains(s, valueChangeEvent.getValue()));
        });

        addButton.setCaption("Добавить");
        addButton.addClickListener(this::onAddButtonClick);

        changeButton.setCaption("Изменить");
        changeButton.addClickListener(this::onChangeButtonClick);

        deleteButton.setCaption("Удалить");
        deleteButton.addClickListener(this::onDeleteButtonClick);

        menuBar.addComponent(nameFilter);
        menuBar.addComponent(addButton);
        menuBar.addComponent(changeButton);
        menuBar.addComponent(deleteButton);

        addComponent(menuBar);
    }

    /*
     * Проверка на содержание подстроки в строке.
     * Используется при создании фильра в методе addMenuBar()
     */
    private Boolean caseInsensitiveContains(String where, String what) {
        return where.toLowerCase().contains(what.toLowerCase());
    }

    private void onAddButtonClick(Button.ClickEvent clickEvent) {
        AddWindow addWindow = new AddWindow(patientGrid);
        UI.getCurrent().addWindow(addWindow);
    }

    private void onChangeButtonClick(Button.ClickEvent clickEvent) {
        if (selectedRow == null || !patientGrid.getPatients().contains(selectedRow)) {
            MessageWindow messageWindow = new MessageWindow("Для изменения пациента необходимо выбрать его в таблице");
            UI.getCurrent().addWindow(messageWindow);
        } else {
            ChangeWindow changeWindow = new ChangeWindow(patientGrid, selectedRow);
            UI.getCurrent().addWindow(changeWindow);
        }
    }

    private void onDeleteButtonClick(Button.ClickEvent clickEvent) {
        if (selectedRow == null || !patientGrid.getPatients().contains(selectedRow)) {
            MessageWindow messageWindow = new MessageWindow("Для удаления пациента необходимо выбрать его в таблице");
            UI.getCurrent().addWindow(messageWindow);
        } else {
            DeleteWindow deleteWindow = new DeleteWindow(patientGrid,selectedRow);
            UI.getCurrent().addWindow(deleteWindow);
        }
    }

    private void addGrid() {
        patientGrid = new PatientGrid();
        patientGrid.setSizeFull();
        patientGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        patientGrid.addItemClickListener(event -> selectedRow = event.getItem());

        addComponentsAndExpand(patientGrid);
    }
}