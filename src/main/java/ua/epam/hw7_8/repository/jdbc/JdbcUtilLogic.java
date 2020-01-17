package ua.epam.hw7_8.repository.jdbc;

import ua.epam.hw7_8.model.AccountStatus;
import java.sql.*;

public class JdbcUtilLogic {
    private static final String DATABASE_URL = "jdbc:mysql://localhost/epam";
    private static final String USER = "root";
    private static final String PASSWORD = "hugo449079";

    public static void writeToDB(String sql, long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeToDB(String sql1, String sql2, long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static AccountStatus accountStatusCheck(String AccountStatusStr) {
        if (AccountStatus.ACTIVE.toString().equals(AccountStatusStr))
            return AccountStatus.ACTIVE;
        else if (AccountStatus.BANNED.toString().equals(AccountStatusStr))
            return AccountStatus.BANNED;
        else if (AccountStatus.DELETED.toString().equals(AccountStatusStr))
            return AccountStatus.DELETED;
        return null;
    }
}