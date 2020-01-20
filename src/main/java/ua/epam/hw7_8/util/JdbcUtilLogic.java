package ua.epam.hw7_8.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcUtilLogic {
    public static void writeToDB(String sql, long id) {
        try (Connection connection = ConnectionPoolUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void writeToDB(String sql1, String sql2, long id) {
        try (Connection connection = ConnectionPoolUtil.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql1)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql2)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}