package ua.epam.hw7_8.repository.jdbc;

import ua.epam.hw7_8.model.Skill;
import ua.epam.hw7_8.repository.SkillRepository;
import java.sql.*;
import java.util.ArrayList;

public class JdbcSkillRepository implements SkillRepository {
    private final String ID_NOT_FOUND_TEXT = "Id not found";
    private static final String DATABASE_URL = "jdbc:mysql://localhost/epam";
    private static final String USER = "root";
    private static final String PASSWORD = "hugo449079";

    public Skill save(Skill skill) {
        String sql1 = "INSERT INTO skills(skill_name) VALUES (?);";
        String sql2 = "SELECT * FROM skills WHERE skill_name = '" + skill.getSkill() + "';";
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1, skill.getSkill());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.executeQuery(sql2);
            while (resultSet.next()) {
                long id = resultSet.getInt("id");
                skill.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return skill;
    }

    private void skillWriteToDB(String sql, Skill skill, long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, skill.getSkill());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<Skill> skillReadFromDB(String sql) {
        Connection connection = null;
        Statement statement = null;
        ArrayList<Skill> skillArrayList = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                long id = resultSet.getInt("id");
                String skill_name = resultSet.getString("skill_name");
                Skill skill = new Skill();
                skill.setId(id);
                skill.setSkill(skill_name);
                skillArrayList.add(skill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return skillArrayList;
    }
}

