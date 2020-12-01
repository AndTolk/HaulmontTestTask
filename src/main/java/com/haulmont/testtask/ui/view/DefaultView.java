package com.haulmont.testtask.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/*
 * Конетент отображающийся по стартовой ссылке.
 */
public class DefaultView extends VerticalLayout implements View {

    public DefaultView() {
        setSizeFull();
        setDefaultComponentAlignment(Alignment.TOP_CENTER);
        Label label = new Label("Тестовое задание");

        addComponent(label);
    }
}