package ua.epam.hw7_8.view;

import ua.epam.hw7_8.model.Account;
import ua.epam.hw7_8.model.AccountStatus;

import java.util.Scanner;

public class AccountView {
    private final String ERROR_TEXT = "error";
    private final String INPUT_EMAIL_TEXT = "Input account:";
    private final String SET_STATUS_TEXT = "Set account status Active(1), Baned(2), Deleted(3):";
    static Scanner in = new Scanner(System.in);

    public Account inputEmail() {
        String input;
        Account account = new Account();

        System.out.println(INPUT_EMAIL_TEXT);
        while (!in.hasNextLine()) {
            System.out.println(ERROR_TEXT);
            in.next();
        }
        input = in.nextLine();
        account.setAccount(input);
        return account;
    }

    public AccountStatus inputStatus() {
        int input;

        System.out.println(SET_STATUS_TEXT);
        while (!in.hasNextInt()) {
            System.out.println(ERROR_TEXT);
            in.next();
        }
        input = in.nextInt();
        switch (input) {
            case 1:
                return AccountStatus.ACTIVE;
            case 2:
                return AccountStatus.BANNED;
            case 3:
                return AccountStatus.DELETED;
        }
        return null;
    }

    public Account inputAccount() {
        Account account = inputEmail();
        account.setAccountStatus(inputStatus());
        return account;
    }
}