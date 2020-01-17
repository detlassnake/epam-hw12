package ua.epam.hw7_8.repository.jdbc;

import ua.epam.hw7_8.model.Account;
import ua.epam.hw7_8.repository.AccountRepository;
import java.sql.*;
import java.util.ArrayList;

public class JdbcAccountRepository implements AccountRepository {
    private final String ID_NOT_FOUND_TEXT = "Id not found";
    private static final String DATABASE_URL = "jdbc:mysql://localhost/epam";
    private static final String USER = "root";
    private static final String PASSWORD = "hugo449079";

    public Account save(Account account) {
        String sql1 = "INSERT INTO accounts (account_name, account_status) VALUES (?, ?);";
        String sql2 = "SELECT * FROM accounts WHERE account_name = '" + account.getEmail() + "';";
        return accountWriteToDB(sql1, sql2, account);
    }

    public ArrayList<Account> getAll() {
        String sql = "SELECT * from accounts;";
        return accountReadFromDB(sql);
    }

    public Account getById(Long id) {
        String sql = "SELECT * from accounts WHERE id = " + id + ";";
        Account account = new Account();
        try {
            account = accountReadFromDB(sql).get(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(ID_NOT_FOUND_TEXT);
        }
        return account;
    }

    public void update(Long id, Account account) {
        String sql = "UPDATE accounts SET account_name = ?, account_status = ? WHERE id = ?;";
        accountWriteToDB(sql, account, id);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM accounts WHERE id = ?;";
        JdbcUtilLogic.writeToDB(sql, id);
    }

    private Account accountWriteToDB(String sql1, String sql2, Account account) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1, account.getEmail());
            preparedStatement.setString(2, account.getAccountStatus().toString());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.executeQuery(sql2);
            while (resultSet.next()) {
                long id = resultSet.getInt("id");
                account.setId(id);
            }
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
        return account;
    }

    private void accountWriteToDB(String sql, Account account, long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getEmail());
            preparedStatement.setString(2, account.getAccountStatus().toString());
            preparedStatement.setLong(3, id);
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

    private ArrayList<Account> accountReadFromDB(String sql) {
        Connection connection = null;
        Statement statement = null;
        ArrayList<Account> accountArrayList = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                long id = resultSet.getInt("id");
                String account_name = resultSet.getString("Account_name");
                String account_status = resultSet.getString("Account_status");

                Account account = new Account();
                account.setId(id);
                account.setEmail(account_name);
                account.setAccountStatus(JdbcUtilLogic.accountStatusCheck(account_status));
                accountArrayList.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return accountArrayList;
    }
}
