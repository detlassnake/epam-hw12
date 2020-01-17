package ua.epam.hw7_8.service;

import ua.epam.hw7_8.model.Skill;
import ua.epam.hw7_8.repository.SkillRepository;
import ua.epam.hw7_8.repository.jdbc.JdbcSkillRepository;
import java.util.ArrayList;

public class SkillService {
    private SkillRepository skillRepository;

    public SkillService() {
        skillRepository = new JdbcSkillRepository();
    }

    public Skill create(Skill skill) {
        return skillRepository.save(skill);
    }

    public ArrayList<Skill> read() {
        return skillRepository.getAll();
    }

    public Skill readById(long id) {
        return skillRepository.getById(id);
    }

    public void edit(long id, Skill skill) {
        skillRepository.update(id, skill);
    }

    public void delete(long id) {
        skillRepository.deleteById(id);
    }
}
