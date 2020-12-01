package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * DAO-класс для работы с врачами
 */
public class DoctorDAO implements DAOInterface {

    public static void addDoctor(Doctor doctor) {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECTION, CONNECTION_LOGIN, CONNECTION_PASSWORD);

            statement = con.prepareStatement("insert into DOCTORS (lastname,firstname,middlename,specialization) values(?,?,?,?)");
            statement.setString(1, doctor.getLastName());
            statement.setString(2, doctor.getFirstName());
            statement.setString(3, doctor.getMiddleName());
            statement.setString(4, doctor.getSpecialization());
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

    public static Doctor readDoctorById(long id) {
        Doctor doctor = null;
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECTION, CONNECTION_LOGIN, CONNECTION_PASSWORD);

            statement = con.prepareStatement("SELECT * FROM doctors WHERE id_doctors=?");
            statement.setLong(1, id);
            rs = statement.executeQuery();
            rs.next();
            doctor = new Doctor(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
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
        return doctor;
    }

    public static Doctor readDoctorByParams(String lastName, String firstName, String middleName, String specialization) {
        Doctor doctor = null;
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECTION, CONNECTION_LOGIN, CONNECTION_PASSWORD);

            statement = con.prepareStatement("SELECT * FROM doctors WHERE lastname = ? and firstname = ? and middlename = ? and specialization = ?");
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setString(3, middleName);
            statement.setString(4, specialization);
            rs = statement.executeQuery();

            rs.next();
            doctor = new Doctor(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
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
        return doctor;
    }

    public static List<Doctor> readAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECTION, CONNECTION_LOGIN, CONNECTION_PASSWORD);

            statement = con.prepareStatement("SELECT * FROM DOCTORS");
            rs = statement.executeQuery();
            while (rs.next()) {
                doctors.add(new Doctor(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
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
        return doctors;
    }

    public static void changeDoctor(Doctor oldDoctor, Doctor newDoctor) {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECTION, CONNECTION_LOGIN, CONNECTION_PASSWORD);

            statement = con.prepareStatement("update DOCTORS set lastname = ?, firstname = ?, middlename = ?, specialization = ?" +
                    " where id_doctors = ?");

            statement.setString(1, newDoctor.getLastName());
            statement.setString(2, newDoctor.getFirstName());
            statement.setString(3, newDoctor.getMiddleName());
            statement.setString(4, newDoctor.getSpecialization());

            statement.setLong(5, oldDoctor.getId());

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

    public static void deleteDoctor(Doctor doctor) throws DAOException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECTION, CONNECTION_LOGIN, CONNECTION_PASSWORD);

            statement = con.prepareStatement("delete from DOCTORS where id_doctors = ?");
            statement.setLong(1, doctor.getId());
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