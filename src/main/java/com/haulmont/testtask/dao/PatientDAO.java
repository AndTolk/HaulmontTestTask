package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * DAO-класс для работы с пациентами
 */
public class PatientDAO implements DAOInterface {

    public static void addPatient(Patient patient) {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECTION, CONNECTION_LOGIN, CONNECTION_PASSWORD);

            statement = con.prepareStatement("insert into PATIENTS (lastname,firstname,middlename,phonenumber) values(?,?,?,?)");
            statement.setString(1, patient.getLastName());
            statement.setString(2, patient.getFirstName());
            statement.setString(3, patient.getMiddleName());
            statement.setString(4, patient.getPhoneNumber());
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

    public static Patient readPatientById(long id) {
        Patient patient = null;
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECTION, CONNECTION_LOGIN, CONNECTION_PASSWORD);

            statement = con.prepareStatement("SELECT * FROM patients WHERE id_patients=?");
            statement.setLong(1, id);
            rs = statement.executeQuery();
            rs.next();
            patient = new Patient(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
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
                e.printStackTrace(System.out);
            }
        }
        return patient;
    }

    public static Patient readPatientByParams(String lastName, String firstName, String middleName, String phoneNumber) {
        Patient patient = null;
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECTION, CONNECTION_LOGIN, CONNECTION_PASSWORD);

            statement = con.prepareStatement("SELECT * FROM PATIENTS WHERE lastname = ? and firstname = ? and middlename = ? and phonenumber = ?");
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setString(3, middleName);
            statement.setString(4, phoneNumber);
            rs = statement.executeQuery();
            rs.next();
            patient = new Patient(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
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
        return patient;
    }

    public static List<Patient> readAllPatients() {
        List<Patient> patients = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECTION, CONNECTION_LOGIN, CONNECTION_PASSWORD);

            statement = con.prepareStatement("SELECT * FROM PATIENTS");
            rs = statement.executeQuery();
            while (rs.next()) {
                patients.add(new Patient(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
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
                e.printStackTrace(System.out);
            }
        }
        return patients;
    }

    public static void changePatient(Patient oldPatient, Patient newPatient) {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECTION, CONNECTION_LOGIN, CONNECTION_PASSWORD);

            statement = con.prepareStatement("update PATIENTS set lastname = ?, firstname = ?, middlename = ?, phonenumber = ?" +
                    " where id_patients = ?");
            statement.setString(1, newPatient.getLastName());
            statement.setString(2, newPatient.getFirstName());
            statement.setString(3, newPatient.getMiddleName());
            statement.setString(4, newPatient.getPhoneNumber());

            statement.setLong(5, oldPatient.getId());

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

    public static void deletePatient(Patient patient) throws DAOException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECTION, CONNECTION_LOGIN, CONNECTION_PASSWORD);

            statement = con.prepareStatement("delete from PATIENTS where id_patients = ?");
            statement.setLong(1, patient.getId());
            statement.execute();
        } catch (Exception e) {
            throw new DAOException("Невозможно удалить врача", e);
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
}