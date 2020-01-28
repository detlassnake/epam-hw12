package ua.epam.hw12.util;

import ua.epam.hw12.model.AccountStatus;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class UtilLogic {
    public static ArrayList<String> getProperties () {
        ArrayList<String> applicationPropertiesArrayList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
            Properties property = new Properties();
            property.load(fis);
            applicationPropertiesArrayList.add(property.getProperty("database.url"));
            applicationPropertiesArrayList.add(property.getProperty("database.username"));
            applicationPropertiesArrayList.add(property.getProperty("database.password"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return applicationPropertiesArrayList;
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
