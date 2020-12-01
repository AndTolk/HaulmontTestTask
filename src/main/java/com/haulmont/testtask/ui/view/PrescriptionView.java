package com.haulmont.testtask.ui.view;

import com.haulmont.testtask.entity.Prescription;
import com.haulmont.testtask.ui.grid.PrescriptionGrid;
import com.haulmont.testtask.ui.subwindows.AddWindow;
import com.haulmont.testtask.ui.subwindows.ChangeWindow;
import com.haulmont.testtask.ui.subwindows.DeleteWindow;
import com.haulmont.testtask.ui.subwindows.MessageWindow;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

/*
 * Класс контента на экране с таблицой рецептов
 */
public class PrescriptionView extends VerticalLayout implements View {

    /* Таблица с рецптами */
    private PrescriptionGrid prescriptionGrid;
    /* Выбранная пользователем строчка в таблице */
    private Prescription selectedRow;

    /*
     * Базовый конструктор.
     * Создание панели с кнопками, панели с фильтрами и создание таблицы вынесены в методы {@link #addMenuBar()}, {@link #addFilterBar()} и {@link #addGrid()}
     */
    public PrescriptionView() {
        setSizeFull();
        addMenuBar();
        addFilterBar();
        addGrid();
    }

    /*
     * Создание панели с кнопками.
     * Добавляются кнопки: добавить, изменить, удалить.
     */
    private void addMenuBar() {
        HorizontalLayout menuLayout = new HorizontalLayout();

        Button addButton = new Button();
        Button changeButton = new Button();
        Button deleteButton = new Button();

        addButton.setCaption("Добавить");
        addButton.addClickListener(this::onAddButtonClick);

        changeButton.setCaption("Изменить");
        changeButton.addClickListener(this::onChangeButtonClick);

        deleteButton.setCaption("Удалить");
        deleteButton.addClickListener(this::onDeleteButtonClick);

        menuLayout.addComponent(addButton);
        menuLayout.addComponent(changeButton);
        menuLayout.addComponent(deleteButton);

        addComponent(menuLayout);
    }

    private void onAddButtonClick(Button.ClickEvent clickEvent) {
        AddWindow addWindow = new AddWindow(prescriptionGrid);
        UI.getCurrent().addWindow(addWindow);
    }

    private void onChangeButtonClick(Button.ClickEvent clickEvent) {
        if (selectedRow == null || !prescriptionGrid.getPrescriptions().contains(selectedRow)) {
            MessageWindow messageWindow = new MessageWindow("Для изменения пациента необходимо выбрать его в таблице");
            UI.getCurrent().addWindow(messageWindow);
        } else {
            ChangeWindow changeWindow = new ChangeWindow(prescriptionGrid, selectedRow);
            UI.getCurrent().addWindow(changeWindow);
        }
    }

    private void onDeleteButtonClick(Button.ClickEvent clickEvent) {
        if (selectedRow == null || !prescriptionGrid.getPrescriptions().contains(selectedRow)) {
            MessageWindow messageWindow = new MessageWindow("Для удаления пациента необходимо выбрать его в таблице");
            UI.getCurrent().addWindow(messageWindow);
        } else {
            DeleteWindow deleteWindow = new DeleteWindow(prescriptionGrid,selectedRow);
            UI.getCurrent().addWindow(deleteWindow);
        }
    }

    public void addFilterBar() {
        HorizontalLayout filterLayout = new HorizontalLayout();

        Label label = new Label("Фильтры: ");
        label.setSizeFull();
        filterLayout.addComponent(label);
        filterLayout.setComponentAlignment(label, Alignment.BOTTOM_CENTER);

        TextField patientFiler = new TextField("Поиск по пациенту");
        patientFiler.setPlaceholder("Введите ФИО");
        patientFiler.setSizeFull();
        filterLayout.addComponent(patientFiler);

        NativeSelect<String> priorityFilter = new NativeSelect<>("Приоритет");
        priorityFilter.setItems("Все", "Нормальный", "Срочный", "Немедленный");
        priorityFilter.setEmptySelectionAllowed(false);
        priorityFilter.setSelectedItem("Все");
        priorityFilter.setSizeFull();
        filterLayout.addComponent(priorityFilter);

        TextField descriptionFilter = new TextField("Описание");
        descriptionFilter.setPlaceholder("Текст описания");
        descriptionFilter.setSizeFull();
        filterLayout.addComponent(descriptionFilter);

        Button filterButton = new Button("Принять");
        filterButton.addClickListener(clickEvent -> {
            ListDataProvider<Prescription> dataProvider = (ListDataProvider<Prescription>) prescriptionGrid.getDataProvider();

            dataProvider.clearFilters();

            dataProvider.addFilter(Prescription::getPatientFullName, s -> caseInsensitiveContains(s, patientFiler.getValue()));

            if (!priorityFilter.getSelectedItem().get().equals("Все"))
                dataProvider.addFilter(Prescription::getPriority, s -> s.equals(priorityFilter.getSelectedItem().get()));

            dataProvider.addFilter(Prescription::getDescription, s -> caseInsensitiveContains(s, descriptionFilter.getValue()));
        });
        filterButton.setSizeFull();
        filterLayout.addComponent(filterButton);
        filterLayout.setComponentAlignment(filterButton, Alignment.BOTTOM_CENTER);

        filterLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        addComponent(filterLayout);
    }

    private Boolean caseInsensitiveContains(String where, String what) {
        return where.toLowerCase().contains(what.toLowerCase());
    }

    private void addGrid() {
        prescriptionGrid = new PrescriptionGrid();
        prescriptionGrid.setSizeFull();
        prescriptionGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        prescriptionGrid.addItemClickListener(event -> selectedRow = event.getItem());
        addComponentsAndExpand(prescriptionGrid);
    }
}