package ua.epam.hw7_8.repository.io;

import ua.epam.hw7_8.model.Account;
import ua.epam.hw7_8.model.AccountStatus;
import ua.epam.hw7_8.repository.AccountRepository;
import ua.epam.hw7_8.util.JavaIOUtilLogic;
import ua.epam.hw7_8.util.UtilLogic;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class JavaIOAccountRepository implements AccountRepository {
    private final String EXCEPTION_TEXT = "IOException";
    private final String ID_NOT_FOUND_TEXT = "Account id not found";
    private final String PATH_NAME = "src/main/resources/files/accounts.txt";

    public Account save(Account data) {
        File file = new File(PATH_NAME);
        Long id;
        ArrayList<String> arrayList = new ArrayList<String>();
        PrintWriter pw;
        try {
            JavaIOUtilLogic.read(arrayList, file);
            pw = new PrintWriter(file);
            if (arrayList.size() == 0) {
                pw.println("1 " + data.getAccount() + " " + data.getAccountStatus().toString());
                data.setId(1L);
            } else {
                id = JavaIOUtilLogic.lastId(arrayList);
                for (int i = 0; i < arrayList.size(); i++) {
                    pw.println(arrayList.get(i));
                }
                pw.println(++id + " " + data.getAccount() + " " + data.getAccountStatus().toString());
                data.setId(id);
            }
            pw.close();
        } catch (IOException e) {
            System.out.println(EXCEPTION_TEXT);
        }
        return data;
    }

    public ArrayList<Account> getAll() {
        File file = new File(PATH_NAME);
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<Account> accountArrayList = new ArrayList<Account>();
        try {
            JavaIOUtilLogic.read(arrayList, file);
        } catch (IOException e) {
            System.out.println(EXCEPTION_TEXT);
        }
        createListAccount(arrayList,accountArrayList);
        return accountArrayList;
    }

    public Account getById(Long id) {
        File file = new File(PATH_NAME);
        ArrayList<String> arrayList = new ArrayList<String>();
        Account account = new Account();
        try {
            JavaIOUtilLogic.read(arrayList, file);
        } catch (IOException e) {
            System.out.println(EXCEPTION_TEXT);
        }
        if (arrayList.size() == 0) {
            System.out.println(ID_NOT_FOUND_TEXT);
        } else if (id > JavaIOUtilLogic.maxId(arrayList)) {
            System.out.println(ID_NOT_FOUND_TEXT);
        } else {
            for (int i = 0; i < arrayList.size(); i++) {
                String[] fileStr = arrayList.get(i).split(" ");
                int idFromFile = Integer.parseInt(fileStr[0]);
                if (idFromFile == id) {
                    account = createAccount(id.toString(), fileStr[1], fileStr[2]);
                }
            }
        }
        return account;
    }

    public void update(Long id, Account data) {
        File file = new File(PATH_NAME);
        ArrayList<String> arrayList = new ArrayList<String>();
        PrintWriter pw;
        try {
            JavaIOUtilLogic.read(arrayList, file);
            if (arrayList.size() == 0) {
                System.out.println(ID_NOT_FOUND_TEXT);
            } else if (id > JavaIOUtilLogic.maxId(arrayList)) {
                System.out.println(ID_NOT_FOUND_TEXT);
            } else {
                pw = new PrintWriter(file);
                for (int i = 0; i < arrayList.size(); i++) {
                    String[] fileStr = arrayList.get(i).split(" ");
                    int idFromFile = Integer.parseInt(fileStr[0]);
                    if (idFromFile != id) {
                        pw.println(arrayList.get(i));
                    } else {
                        pw.println(id + " " + data.getAccount() + " " + data.getAccountStatus());
                    }
                }
                pw.close();
            }
        } catch (IOException e) {
            System.out.println(EXCEPTION_TEXT);
        }
    }

    public void deleteById(Long id) {
        File file = new File(PATH_NAME);
        ArrayList<String> arrayList = new ArrayList<String>();
        PrintWriter pw;
        try {
            JavaIOUtilLogic.read(arrayList, file);
            if (arrayList.size() == 0) {
                System.out.println(ID_NOT_FOUND_TEXT);
            } else if (id > JavaIOUtilLogic.maxId(arrayList)) {
                System.out.println(ID_NOT_FOUND_TEXT);
            } else {
                pw = new PrintWriter(file);
                for (int i = 0; i < arrayList.size(); i++) {
                    String[] fileStr = arrayList.get(i).split(" ");
                    int idFromFile = Integer.parseInt(fileStr[0]);
                    if (idFromFile != id) {
                        pw.println(arrayList.get(i));
                    }
                }
                pw.close();
            }
        } catch (IOException e) {
            System.out.println(EXCEPTION_TEXT);
        }
    }

    private static Account createAccount(String idAccount, String accountStr, String AccountStatusStr) {
        Account account = new Account();
        account.setId(Long.parseLong(idAccount));
        account.setAccount(accountStr);
        account.setAccountStatus(UtilLogic.accountStatusCheck(AccountStatusStr));
        return account;
    }

    private static void createListAccount(ArrayList<String> arrayList, ArrayList<Account> accountArrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            String[] fileStr = arrayList.get(i).split(" ");
            Account account = createAccount(fileStr[0], fileStr[1], fileStr[2]);
            accountArrayList.add(account);
        }
    }
}