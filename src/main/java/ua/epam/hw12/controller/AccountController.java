package ua.epam.hw12.controller;

import ua.epam.hw12.model.Account;
import ua.epam.hw12.service.AccountService;

import java.util.ArrayList;

public class AccountController {
    private AccountService accountService;

    public AccountController() {
        accountService = new AccountService();
    }

    public Account create(Account account) {
        return accountService.create(account);
    }

    public ArrayList<Account> read() {
        return accountService.read();
    }

    public Account readById(long id) {
        return accountService.readById(id);
    }

    public void edit(long id, Account account) {
        accountService.update(id, account);
    }

    public void delete(long id) {
        accountService.delete(id);
    }
}