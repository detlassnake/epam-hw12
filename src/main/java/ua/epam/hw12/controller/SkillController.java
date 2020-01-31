package ua.epam.hw12.controller;

import ua.epam.hw12.model.Skill;
import ua.epam.hw12.service.SkillService;
import java.util.ArrayList;
import java.util.Set;

public class SkillController {
    private SkillService skillService;

    public SkillController() {
        skillService = new SkillService();
    }

    public Skill create(Skill skill) {
        return skillService.create(skill);
    }

    public ArrayList<Skill> read() {
        return skillService.read();
    }

    public Skill readById(long id) {
        return skillService.readById(id);
    }

    public void edit(long id, Skill skill) {
        skillService.update(id, skill);
    }

    public void edit(Set<Skill> skillWithId, Set<Skill> skill) {
        ArrayList<Skill> skills = new ArrayList<>(skill);
        ArrayList<Skill> skillsWithId = new ArrayList<>(skillWithId);
        if (skillsWithId.size() != 0) {
            for (int i = 0; i < skills.size(); i++) {
                skillService.update(skillsWithId.get(i).getId(), skills.get(i));
            }
        }
    }

    public void delete(long id) {
        skillService.delete(id);
    }
}