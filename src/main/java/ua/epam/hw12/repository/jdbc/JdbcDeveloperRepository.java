package ua.epam.hw12.repository.jdbc;

import ua.epam.hw12.model.Developer;
import ua.epam.hw12.model.Skill;
import ua.epam.hw12.repository.DeveloperRepository;
import ua.epam.hw12.mapper.ObjectMapper;
import ua.epam.hw12.util.ConnectionUtil;
import ua.epam.hw12.util.JdbcUtilLogic;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class JdbcDeveloperRepository implements DeveloperRepository {
    private final String ID_NOT_FOUND_TEXT = "Id not found";

    public Developer save(Developer developer) {
        String sql1 = "INSERT INTO developers(developer_name, account_id) VALUES (?, ?);";
        String sql2 = "INSERT INTO developers_skills(developer_id, skill_id) VALUES (?, ?);";
        String sql3 = "SELECT * FROM developers WHERE developer_name = ?;";
        return developerWriteToDB(sql1, sql2, sql3, developer);
    }

    public ArrayList<Developer> getAll() {
        String sql = "SELECT d.id, d.developer_name, a.id, a.account_name, a.account_status, s.id, s.skill_name\n" +
                "FROM developers d\n" +
                "         JOIN developers_skills ds ON d.id = ds.developer_id\n" +
                "         JOIN skills s ON ds.skill_id = s.id\n" +
                "         JOIN accounts a ON d.account_id = a.id;";
        return developerReadFromDB(sql);
    }

    public Developer getById(Long id) {
        String sql = "SELECT d.id, d.developer_name, a.id, a.account_name, a.account_status, s.id, s.skill_name\n" +
                "FROM developers d\n" +
                "         JOIN developers_skills ds ON d.id = ds.developer_id\n" +
                "         JOIN skills s ON ds.skill_id = s.id\n" +
                "         JOIN accounts a ON d.account_id = a.id \n" +
                "WHERE d.id = " + id + ";";
        Developer developer = new Developer();
        try {
            developer = developerReadFromDB(sql).get(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(ID_NOT_FOUND_TEXT);
        }
        return developer;
    }

    public void update(Long id, Developer developer) {
        String sql = "UPDATE developers SET developer_name = ?  WHERE id = ?;";
        developerWriteToDB(sql, developer, id);
    }

    public void deleteById(Long id) {
        String sql2 = "DELETE FROM developers WHERE id = ?;";
        String sql1 = "DELETE FROM developers_skills WHERE developer_id = ?;";
        JdbcUtilLogic.writeToDB(sql1, sql2, id);
    }

    private Developer developerWriteToDB(String sql1, String sql2, String sql3, Developer developer) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql1)) {
                preparedStatement.setString(1, developer.getName());
                preparedStatement.setLong(2, developer.getDeveloperAccount().getId());
                preparedStatement.executeUpdate();
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql3)) {
                preparedStatement.setString(1, developer.getName());
                ResultSet resultSet = preparedStatement.executeQuery();
                ObjectMapper.mapToDeveloper(resultSet, developer);
            }
            ArrayList<Skill> developerSkills = new ArrayList<>(developer.getDeveloperSkillsSet());
            for (int i = 0; i < developerSkills.size(); i++) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql2)) {
                    preparedStatement.setLong(1, developer.getId());
                    preparedStatement.setLong(2, developerSkills.get(i).getId());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }

    private void developerWriteToDB(String sql, Developer developer, long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, developer.getName());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Developer> developerReadFromDB(String sql) {
        ArrayList<Developer> developerArrayList = null;
        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            developerArrayList = ObjectMapper.mapToDeveloper(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        duplicateCleaner(developerArrayList);
        return developerArrayList;
    }

    private void duplicateCleaner(ArrayList<Developer> developerArrayList) {
        Comparator<Developer> comparator = new Comparator<Developer>() {
            @Override
            public int compare(Developer d1, Developer d2) {
                return d1.getId().compareTo(d2.getId());
            }
        };
        Collections.sort(developerArrayList, comparator);
        for (int i = 0; i < developerArrayList.size(); i++) {
            for (int j = i + 1; j < developerArrayList.size(); j++) {
                if (developerArrayList.get(i).getId().equals(developerArrayList.get(j).getId())) {
                    ArrayList<Skill> arrayList = new ArrayList<>(developerArrayList.get(j).getDeveloperSkillsSet());
                    Skill skill = new Skill();
                    for (int k = 0; k < arrayList.size(); k++) {
                        skill.setId(arrayList.get(k).getId());
                        skill.setSkill(arrayList.get(k).getSkill());
                        developerArrayList.get(i).setDeveloperSkills(skill);
                    }
                    developerArrayList.remove(j);
                }
            }
        }
    }
}
