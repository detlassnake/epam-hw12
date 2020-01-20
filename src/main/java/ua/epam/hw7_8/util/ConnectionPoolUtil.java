package ua.epam.hw7_8.util;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolUtil {
    private static BasicDataSource ds = new BasicDataSource();
    static {
        ds.setUrl(UtilLogic.getProperties().get(0));
        ds.setUsername(UtilLogic.getProperties().get(1));
        ds.setPassword(UtilLogic.getProperties().get(2));
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private ConnectionPoolUtil(){ }
}
