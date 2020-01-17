package ua.epam.hw7_8.service;

import ua.epam.hw7_8.model.Account;
import ua.epam.hw7_8.repository.AccountRepository;
import ua.epam.hw7_8.repository.io.JavaIOAccountRepository;
import ua.epam.hw7_8.repository.jdbc.JdbcAccountRepository;

import java.util.ArrayList;

public class AccountService {
    private AccountRepository accountRepository;

    public AccountService() {
        accountRepository = new JdbcAccountRepository();
    }

    public Account create(Account account) {
        return accountRepository.save(account);
    }

    public ArrayList<Account> read() {
        return accountRepository.getAll();
    }

    public Account readById(long id) {
        return accountRepository.getById(id);
    }

    public void editById(long id, Account account) {
        accountRepository.update(id, account);
    }

    public void delete(long id) {
        accountRepository.deleteById(id);
    }
}
