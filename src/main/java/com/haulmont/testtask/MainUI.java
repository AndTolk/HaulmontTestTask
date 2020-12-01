package com.haulmont.testtask;

import com.haulmont.testtask.ui.view.DefaultView;
import com.haulmont.testtask.ui.view.DoctorView;
import com.haulmont.testtask.ui.view.PatientView;
import com.haulmont.testtask.ui.view.PrescriptionView;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;

/*
 * Стартовый класс программы.
 */

public class MainUI extends UI {

    String DRIVER = "org.hsqldb.jdbc.JDBCDriver";
    String CONNECTION = "jdbc:hsqldb:file:" + System.getProperty("user.dir") + "/src/main/resources/dbfiles/";
    String CONNECTION_LOGIN = "SA";
    String CONNECTION_PASSWORD = "";

    /*
     * Создается меню и навигатор.
     * В зависимости от выбранного пользователем пункта меню меняется контент в приложении.
     * Для каждого экрана с таблицами существует отдельный класс View.
     */
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        createDB();
        setSizeFull();

        Label title = new Label("Меню");
        title.addStyleName(ValoTheme.MENU_TITLE);

        Button patients = new Button("Пациенты", e -> getNavigator().navigateTo("patients"));
        patients.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
        Button doctors = new Button("Врачи", e -> getNavigator().navigateTo("doctors"));
        doctors.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
        Button prescriptions = new Button("Рецепты", e -> getNavigator().navigateTo("prescriptions"));
        prescriptions.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);

        CssLayout menuLayout = new CssLayout(title, patients, doctors, prescriptions);
        menuLayout.addStyleName(ValoTheme.MENU_ROOT);

        VerticalLayout contentLayout = new VerticalLayout();
        HorizontalLayout mainLayout = new HorizontalLayout(menuLayout, contentLayout);

        mainLayout.setSizeFull();
        menuLayout.setSizeUndefined();
        contentLayout.setSizeFull();

        mainLayout.setExpandRatio(contentLayout, 1);

        Navigator navigator = new Navigator(this, contentLayout);
        navigator.addView("", DefaultView.class);
        navigator.addView("patients", PatientView.class);
        navigator.addView("doctors", DoctorView.class);
        navigator.addView("prescriptions", PrescriptionView.class);

        setContent(mainLayout);
        System.out.println();
    }

    /*
     * Проверяет наличие БД. В случае отсутствия создает ее запуская скрипт script.sql.
     * БД создается внутри проекта по пути /src/main/resources/dbfiles
     */
    private void createDB() {
        Connection con = null;
        Reader reader = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECTION, CONNECTION_LOGIN, CONNECTION_PASSWORD);

            DatabaseMetaData metaData = con.getMetaData();
            ResultSet table = metaData.getTables(null, null, "PATIENTS", null);
            if (!table.next()) {
                ScriptRunner scriptRunner = new ScriptRunner(con);
                reader = new BufferedReader(new FileReader("src/main/java/com/haulmont/testtask/script.sql"));
                scriptRunner.runScript(reader);
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.exit(1);
        } finally {
            try {
                if (reader != null)
                    reader.close();

                if (con != null)
                    con.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}