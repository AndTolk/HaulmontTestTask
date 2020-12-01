package com.haulmont.testtask;

import com.vaadin.ui.DateField;

/*
 * Класс статических методов для валидации форм в программе
 */
public class Validator {

    /*
     * Валидация Имени, Фамилии или Отчества.
     * Разрешено использовать только кириллицу знаки дефиса и пробеллов(сделано по причине возможности двойных имен или фамилий)
     */
    public static boolean nameValidate(String name) {
        return name.matches("^[а-яА-Я\\s-]{2,}$");
    }

    /*
     * Валидация номеров телофона. Не имеет жестких ограничений.
     */
    public static boolean phoneNumberValidate(String phoneNumber) {
        return phoneNumber.matches("^[\\s0-9_()+-]{2,}$");
    }

    public static boolean specializationValidate(String specialization) {
        return specialization.matches("^[а-яА-Я\\s-]{2,}$");
    }

    /*
     * Валидация описания рецепта. Не представляю как это необходимо валидировать)).
     * Размер строки должен быть от 5 до 1000 знаков
     */
    public static boolean descriptionValidate(String description) {
        return description.length() >= 5 && (description.length() <= 1000);
    }

    public static boolean dateValidate(DateField startDate, DateField endDate) {
        if (startDate.isEmpty() || endDate.isEmpty())
            return false;
        return !endDate.getValue().isBefore(startDate.getValue());
    }
}