package ua.epam.hw12.mapper;

import ua.epam.hw12.model.Account;
import ua.epam.hw12.model.Developer;
import ua.epam.hw12.model.Skill;
import ua.epam.hw12.util.UtilLogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ObjectMapper {
    public static ArrayList<Account> mapToAccount(ResultSet resultSet) throws SQLException {
        ArrayList<Account> accountArrayList = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getInt("id");
            String account_name = resultSet.getString("Account_name");
            String account_status = resultSet.getString("Account_status");
            Account account = new Account();
            account.setId(id);
            account.setAccount(account_name);
            account.setAccountStatus(UtilLogic.accountStatusCheck(account_status));
            accountArrayList.add(account);
        }
        return accountArrayList;
    }

    public static ArrayList<Skill> mapToSkill(ResultSet resultSet) throws SQLException {
        ArrayList<Skill> skillArrayList = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getInt("id");
            String skill_name = resultSet.getString("skill_name");
            Skill skill = new Skill();
            skill.setId(id);
            skill.setSkill(skill_name);
            skillArrayList.add(skill);
        }
        return skillArrayList;
    }

    public static void mapToDeveloper(ResultSet resultSet, Developer developer) throws SQLException {
        while (resultSet.next()) {
            long id = resultSet.getInt("id");
            developer.setId(id);
        }
    }

    public static ArrayList<Developer> mapToDeveloper(ResultSet resultSet) throws SQLException {
        ArrayList<Developer> developerArrayList = new ArrayList<>();
        while (resultSet.next()) {
            long accountId = resultSet.getInt("a.id");
            long skillId = resultSet.getInt("s.id");
            long developerId = resultSet.getInt("d.id");
            String skill_name = resultSet.getString("skill_name");
            String account_name = resultSet.getString("account_name");
            String account_status = resultSet.getString("account_status");
            String developer_name = resultSet.getString("developer_name");
            Developer developer = new Developer();
            Account account = new Account();
            Skill skill = new Skill();
            skill.setId(skillId);
            skill.setSkill(skill_name);
            account.setId(accountId);
            account.setAccountStatus(UtilLogic.accountStatusCheck(account_status));
            account.setAccount(account_name);
            developer.setId(developerId);
            developer.setName(developer_name);
            developer.setDeveloperAccount(account);
            developer.setDeveloperSkills(skill);
            developerArrayList.add(developer);
        }
        return developerArrayList;
    }
}
