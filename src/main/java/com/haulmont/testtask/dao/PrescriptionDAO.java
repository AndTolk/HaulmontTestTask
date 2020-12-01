package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.Prescription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.haulmont.testtask.dao.DoctorDAO.readDoctorById;
import static com.haulmont.testtask.dao.PatientDAO.readPatientById;

/*
 * DAO-класс для работы с рецептами
 */
public class PrescriptionDAO implements DAOInterface {

    public static void addPrescription(Prescription prescription) {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECTION, CONNECTION_LOGIN, CONNECTION_PASSWORD);

            statement = con.prepareStatement("insert into prescriptions (description,id_patients,id_doctors,dateofcreation,validityperiod,priority) values (?,?,?,?,?,?);");
            statement.setString(1, prescription.getDescription());
            statement.setLong(2, prescription.getPatient().getId());
            statement.setLong(3, prescription.getDoctor().getId());
            statement.setString(4, prescription.getDateOfCreation());
            statement.setString(5, prescription.getValidityPeriod());
            statement.setString(6, prescription.getPriority());
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                if (statement != null)
                    statement.close();

                if (con != null)
                    con.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Prescription> readAllPrescriptions() {
        List<Prescription> prescriptions = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECTION, CONNECTION_LOGIN, CONNECTION_PASSWORD);
            statement = con.prepareStatement("SELECT * FROM PRESCRIPTIONS");
            rs = statement.executeQuery();
            while (rs.next())
                prescriptions.add(new Prescription(rs.getLong(1), rs.getString(2),
                        readPatientById(rs.getLong(3)), readDoctorById(rs.getLong(4)),
                        rs.getString(5), rs.getString(6), rs.getString(7)));
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                if (rs != null)
                    rs.close();

                if (statement != null)
                    statement.close();

                if (con != null)
                    con.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return prescriptions;
    }

    public static void changePrescription(Prescription oldPrescription, Prescription newPrescription) {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECTION, CONNECTION_LOGIN, CONNECTION_PASSWORD);

            statement = con.prepareStatement("update prescriptions set description = ?, id_patients = ?, id_doctors = ?, dateofcreation = ?, validityperiod = ?, priority = ?" +
                    " where id_patients = ?");
            statement.setString(1, newPrescription.getDescription());
            statement.setLong(2, newPrescription.getPatient().getId());
            statement.setLong(3, newPrescription.getDoctor().getId());
            statement.setString(4, newPrescription.getDateOfCreation());
            statement.setString(5, newPrescription.getValidityPeriod());
            statement.setString(6, newPrescription.getPriority());

            statement.setLong(7, oldPrescription.getId());

            statement.execute();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                if (statement != null)
                    statement.close();

                if (con != null)
                    con.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deletePrescription(Prescription prescription) {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECTION, CONNECTION_LOGIN, CONNECTION_PASSWORD);

            statement = con.prepareStatement("DELETE FROM prescriptions WHERE id_prescriptions = ?");
            statement.setLong(1, prescription.getId());
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                if (statement != null)
                    statement.close();

                if (con != null)
                    con.close();

            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }
        }
    }

    public static int numberOfPrescriptions() {
        int counter = 0;
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection("jdbc:hsqldb:file:" + System.getProperty("user.dir") + "/src/main/resources/dbfiles/", "SA", "");
            statement = con.prepareStatement("SELECT * FROM PRESCRIPTIONS");
            rs = statement.executeQuery();
            while (rs.next())
                counter++;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                if (rs != null)
                    rs.close();

                if (statement != null)
                    statement.close();

                if (con != null)
                    con.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return counter;
    }
}