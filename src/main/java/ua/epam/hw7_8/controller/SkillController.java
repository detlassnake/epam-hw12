package ua.epam.hw7_8.controller;

import ua.epam.hw7_8.model.Skill;
import ua.epam.hw7_8.service.SkillService;
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
        skillService.edit(id, skill);
    }

    public void edit(long id, Set<Skill> skill) {
        ArrayList<Skill> skills = new ArrayList<>(skill);
        for (int i = 0; i < skills.size(); i++) {
            skillService.edit(id, skills.get(i));
        }

    }

    public void delete(long id) {
        skillService.delete(id);
    }
}