package ua.epam.hw12.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.epam.hw12.model.Skill;
import ua.epam.hw12.repository.SkillRepository;
import ua.epam.hw12.repository.jdbc.JdbcSkillRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

    public Set<Skill> create(Set<Skill> skillSet) {
        logger.debug("Skill service->Create");
        ArrayList<Skill> skillArrayList = new ArrayList<>(skillSet);
        Set<Skill> skillSetWithId = new HashSet<>();
        if (skillArrayList.size() != 0) {
            for (int i = 0; i < skillArrayList.size(); i++) {
                skillSetWithId.add(skillRepository.save(skillArrayList.get(i)));
            }
        }
        return skillSetWithId;
    }

    public ArrayList<Skill> read() {
        logger.debug("Skill service->Read");
        return skillRepository.getAll();
    }

    public Skill readById(long id) {
        logger.debug("Skill service->Read by id");
        return skillRepository.getById(id);
    }

    public void update(long id, Skill skill) {
        logger.debug("Skill service->Edit by id");
        skillRepository.update(id, skill);
    }

    public void update(Set<Skill> skillSetWithId, Set<Skill> skillSet) {
        logger.debug("Skill service->Edit by id");
        ArrayList<Skill> skillArrayListWithId = new ArrayList<>(skillSetWithId);
        ArrayList<Skill> skillArrayList = new ArrayList<>(skillSet);
        if (skillArrayListWithId.size() != 0) {
            for (int i = 0; i < skillArrayList.size(); i++) {
                skillRepository.update(skillArrayListWithId.get(i).getId(), skillArrayList.get(i));
            }
        }
    }

    public void delete(long id) {
        logger.debug("Skill service->Delete");
        skillRepository.deleteById(id);
    }
}
