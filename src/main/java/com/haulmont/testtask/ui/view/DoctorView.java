package com.haulmont.testtask.ui.view;

import com.haulmont.testtask.dao.DAOException;
import com.haulmont.testtask.entity.Doctor;
import com.haulmont.testtask.ui.grid.DoctorGrid;
import com.haulmont.testtask.ui.subwindows.AddWindow;
import com.haulmont.testtask.ui.subwindows.ChangeWindow;
import com.haulmont.testtask.ui.subwindows.DeleteWindow;
import com.haulmont.testtask.ui.subwindows.MessageWindow;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

import static com.haulmont.testtask.dao.PrescriptionDAO.numberOfPrescriptions;

/*
 * Класс контента на экране с таблицой врачей
 */
public class DoctorView extends VerticalLayout implements View {

    /* Таблица с врачами */
    private DoctorGrid doctorGrid;
    /* Выбранная пользователем строчка в таблице */
    private Doctor selectedRow;

    /*
     * Базовый конструктор.
     * Создание панели с кнопками и создание таблицы вынесены в методы addMenuBar() и addGrid()
     */
    public DoctorView() {
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
        nameFilter.setPlaceholder("Поиск по ФИО");
        nameFilter.addValueChangeListener(valueChangeEvent -> {
            ListDataProvider<Doctor> dataProvider = (ListDataProvider<Doctor>) doctorGrid.getDataProvider();
            dataProvider.setFilter(Doctor::getFullName, s -> caseInsensitiveContains(s, valueChangeEvent.getValue()));
        });
        menuBar.addComponent(nameFilter);

        Button addButton = new Button();
        addButton.setCaption("Добавить");
        addButton.addClickListener(this::onAddButtonClick);
        menuBar.addComponent(addButton);

        Button changeButton = new Button();
        changeButton.setCaption("Изменить");
        changeButton.addClickListener(this::onChangeButtonClick);
        menuBar.addComponent(changeButton);

        Button deleteButton = new Button();
        deleteButton.setCaption("Удалить");
        deleteButton.addClickListener(this::onDeleteButtonClick);
        menuBar.addComponent(deleteButton);

        Button statisticsButton = new Button();
        Label statisticsLabel = new Label();
        statisticsLabel.setVisible(false);
        statisticsButton.setCaption("Показать статистику");
        statisticsButton.addClickListener(clickEvent -> {
            statisticsLabel.setValue("Количество рецептов выписанных врачами - " + numberOfPrescriptions());
            statisticsLabel.setVisible(true);
        });
        menuBar.addComponent(statisticsButton);
        menuBar.addComponent(statisticsLabel);
        menuBar.setComponentAlignment(statisticsLabel, Alignment.BOTTOM_CENTER);

        addComponent(menuBar);
    }

    /*
     * Проверка на содержание подстроки в строке.
     * Используется при создании фильра в методе#addMenuBar()
     */
    private Boolean caseInsensitiveContains(String where, String what) {
        return where.toLowerCase().contains(what.toLowerCase());
    }

    private void onAddButtonClick(Button.ClickEvent clickEvent) {
        AddWindow addWindow = new AddWindow(doctorGrid);
        UI.getCurrent().addWindow(addWindow);
    }

    private void onChangeButtonClick(Button.ClickEvent clickEvent) {
        if (selectedRow == null || !doctorGrid.getDoctors().contains(selectedRow)) {
            MessageWindow messageWindow = new MessageWindow("Для изменения врача необходимо выбрать его в таблице");
            UI.getCurrent().addWindow(messageWindow);
        } else {
            ChangeWindow changeWindow = new ChangeWindow(doctorGrid, selectedRow);
            UI.getCurrent().addWindow(changeWindow);
        }
    }

    private void onDeleteButtonClick(Button.ClickEvent clickEvent) {
        if (selectedRow == null || !doctorGrid.getDoctors().contains(selectedRow)) {
            MessageWindow messageWindow = new MessageWindow("Для удаления врача необходимо выбрать его в таблице");
            UI.getCurrent().addWindow(messageWindow);
        } else {
            DeleteWindow deleteWindow = new DeleteWindow(doctorGrid,selectedRow);
            UI.getCurrent().addWindow(deleteWindow);
        }
    }

    private void addGrid() {
        doctorGrid = new DoctorGrid();
        doctorGrid.setSizeFull();
        doctorGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        doctorGrid.addItemClickListener(event -> selectedRow = event.getItem());

        addComponentsAndExpand(doctorGrid);
    }
}