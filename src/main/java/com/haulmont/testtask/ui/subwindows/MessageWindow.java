package com.haulmont.testtask.ui.subwindows;

import com.vaadin.ui.*;

/*
 * Класс для создания окон с сообщениями и конпкой "Ок"
 */
public class MessageWindow extends Window {

    public MessageWindow(String text) {
        setModal(true);
        center();
        setWidth("35%");
        setHeight("25%");
        setClosable(false);
        setResizable(false);

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        setContent(layout);

        Label label = new Label(text);
        layout.addComponent(label);

        Button okButton = new Button("Ок");
        okButton.addClickListener(clickEvent1 -> close());
        layout.addComponent(okButton);

        layout.setComponentAlignment(label, Alignment.BOTTOM_CENTER);
        layout.setComponentAlignment(okButton, Alignment.MIDDLE_CENTER);
    }
}