package ua.epam.hw7_8.repository.jdbc;

import ua.epam.hw7_8.model.Account;
import ua.epam.hw7_8.repository.AccountRepository;
import ua.epam.hw7_8.repository.objectMapper.ObjectMapper;
import ua.epam.hw7_8.util.ConnectionPoolUtil;
import ua.epam.hw7_8.util.JdbcUtilLogic;

import java.sql.*;
import java.util.ArrayList;

public class JdbcAccountRepository implements AccountRepository {
    private final String ID_NOT_FOUND_TEXT = "Id not found";

    public Account save(Account account) {
        String sql1 = "INSERT INTO accounts (account_name, account_status) VALUES (?, ?);";
        String sql2 = "SELECT * FROM accounts WHERE account_name = ?;";
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
        ArrayList<Account> accountArrayList = null;
        try (Connection connection = ConnectionPoolUtil.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql1)) {
                preparedStatement.setString(1, account.getAccount());
                preparedStatement.setString(2, account.getAccountStatus().toString());
                preparedStatement.executeUpdate();
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql2)) {
                preparedStatement.setString(1, account.getAccount());
                ResultSet resultSet = preparedStatement.executeQuery();
                accountArrayList = ObjectMapper.mapToAccount(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert accountArrayList != null;
        return accountArrayList.get(0);
    }

    private void accountWriteToDB(String sql, Account account, long id) {
        try (Connection connection = ConnectionPoolUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, account.getAccount());
            preparedStatement.setString(2, account.getAccountStatus().toString());
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Account> accountReadFromDB(String sql) {
        ArrayList<Account> accountArrayList = null;
        try (Connection connection = ConnectionPoolUtil.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            accountArrayList = ObjectMapper.mapToAccount(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountArrayList;
    }
}
