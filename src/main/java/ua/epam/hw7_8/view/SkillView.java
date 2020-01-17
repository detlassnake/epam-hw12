package ua.epam.hw7_8.view;

import ua.epam.hw7_8.model.Skill;

import java.util.Scanner;

public class SkillView {
    private final String ERROR_TEXT = "error";
    private final String INPUT_TEXT = "Input skill:";
    static Scanner in = new Scanner(System.in);

    public Skill inputSkill(){
        String input;
        Skill skill = new Skill();

        System.out.println(INPUT_TEXT);
        while (!in.hasNextLine()) {
            System.out.println(ERROR_TEXT);
            in.next();
        }
        input = in.nextLine();
        skill.setSkill(input);
        return skill;
    }
}