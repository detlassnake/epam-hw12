package ua.epam.hw12.repository.jdbc;

import ua.epam.hw12.model.Skill;
import ua.epam.hw12.repository.SkillRepository;
import ua.epam.hw12.mapper.ObjectMapper;
import ua.epam.hw12.util.ConnectionUtil;
import ua.epam.hw12.util.JdbcUtilLogic;

import java.sql.*;
import java.util.ArrayList;

public class JdbcSkillRepository implements SkillRepository {
    private final String ID_NOT_FOUND_TEXT = "Id not found";

    public Skill save(Skill skill) {
        String sql1 = "INSERT INTO skills(skill_name) VALUES (?);";
        String sql2 = "SELECT * FROM skills WHERE skill_name = ?;";
        return skillWriteToDB(sql1, sql2, skill);
    }

    public ArrayList<Skill> getAll() {
        String sql = "SELECT * FROM skills;";
        return skillReadFromDB(sql);
    }

    public Skill getById(Long id) {
        String sql = "SELECT * FROM skills WHERE id = " + id + ";";
        Skill skill = new Skill();
        try {
            skill = skillReadFromDB(sql).get(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(ID_NOT_FOUND_TEXT);
        }
        return skill;
    }

    public void update(Long id, Skill skill) {
        String sql = "UPDATE skills SET skill_name = ? WHERE id = ?;";
        skillWriteToDB(sql, skill, id);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM skills WHERE id = ?;";
        JdbcUtilLogic.writeToDB(sql, id);
    }

    private Skill skillWriteToDB(String sql1, String sql2, Skill skill) {
        ArrayList<Skill> skillArrayList = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql1)) {
                preparedStatement.setString(1, skill.getSkill());
                preparedStatement.executeUpdate();
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql2)) {
                preparedStatement.setString(1, skill.getSkill());
                ResultSet resultSet = preparedStatement.executeQuery();
                skillArrayList = ObjectMapper.mapToSkill(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert skillArrayList != null;
        return skillArrayList.get(0);
    }

    private void skillWriteToDB(String sql, Skill skill, long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, skill.getSkill());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Skill> skillReadFromDB(String sql) {
        ArrayList<Skill> skillArrayList = null;
        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            skillArrayList = ObjectMapper.mapToSkill(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skillArrayList;
    }
}

