package ua.epam.hw12.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.epam.hw12.model.Skill;
import ua.epam.hw12.repository.SkillRepository;
import ua.epam.hw12.repository.jdbc.JdbcSkillRepository;

import java.util.ArrayList;

public class SkillService {
    public static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private SkillRepository skillRepository;

    public SkillService() {
        skillRepository = new JdbcSkillRepository();
    }

    public Skill create(Skill skill) {
        logger.debug("Skill service->Create");
        return skillRepository.save(skill);
    }

    public ArrayList<Skill> read() {
        logger.debug("Skill service->Read");
        return skillRepository.getAll();
    }

    public Skill readById(long id) {
        logger.debug("Skill service->Read by id");
        return skillRepository.getById(id);
    }

    public void edit(long id, Skill skill) {
        logger.debug("Skill service->Edit by id");
        skillRepository.update(id, skill);
    }

    public void delete(long id) {
        logger.debug("Skill service->Delete");
        skillRepository.deleteById(id);
    }
}
