package ua.epam.hw12.repository.jdbc;

import ua.epam.hw12.model.Account;
import ua.epam.hw12.repository.AccountRepository;
import ua.epam.hw12.mapper.ObjectMapper;
import ua.epam.hw12.util.ConnectionUtil;
import ua.epam.hw12.util.JdbcUtilLogic;

import java.sql.*;
import java.util.ArrayList;

public class JdbcAccountRepository implements AccountRepository {
    private final String ID_NOT_FOUND_TEXT = "Id not found";

    public Account save(Account account) {
        return accountWriteToDB(JdbcQueryStorage.sqlCreateAccount1, JdbcQueryStorage.sqlCreateAccount2, account);
    }

    public ArrayList<Account> getAll() {
        return accountReadFromDB(JdbcQueryStorage.sqlReadAccount);
    }

    public Account getById(Long id) {
        Account account = new Account();
        try {
            account = accountReadFromDB(JdbcQueryStorage.sqlReadByIdAccount + id + ";").get(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(ID_NOT_FOUND_TEXT);
        }
        return account;
    }

    public void update(Long id, Account account) {
        accountWriteToDB(JdbcQueryStorage.sqlUpdateAccount, account, id);
    }

    public void deleteById(Long id) {
        JdbcUtilLogic.writeToDB(JdbcQueryStorage.sqlDeleteAccount, id);
    }

    private Account accountWriteToDB(String sql1, String sql2, Account account) {
        ArrayList<Account> accountArrayList = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
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
        try (Connection connection = ConnectionUtil.getConnection();
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
        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            accountArrayList = ObjectMapper.mapToAccount(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountArrayList;
    }
}
