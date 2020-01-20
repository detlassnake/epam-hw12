package ua.epam.hw7_8.view;

import ua.epam.hw7_8.controller.AccountController;
import ua.epam.hw7_8.controller.DeveloperController;
import ua.epam.hw7_8.controller.SkillController;
import ua.epam.hw7_8.model.Developer;

import java.util.Scanner;

public class AppView {
    private final String ERROR_TEXT = "error";
    private final String NO_SUCH_NUMBER_TEXT = "There is no such number";
    private final String INPUT_ID_TEXT = "Input id:";
    private final String MAKE_CHOICE_TEXT = "Make choice create(1), read all(2), get by id(3), edit by id(4), delete by id(5)";
    static Scanner in = new Scanner(System.in);

    SkillView skillView = new SkillView();
    SkillController skillController = new SkillController();

    AccountView accountView = new AccountView();
    AccountController accountController = new AccountController();
    DeveloperView developerView = new DeveloperView();
    DeveloperController developerController = new DeveloperController();
    Developer developer = new Developer();

    public void start() {
        System.out.println(MAKE_CHOICE_TEXT);
        switch (choice()) {
            case 1:         //create
                developer.setName(developerView.inputDeveloper());
                developer.setDeveloperSkills(skillController.create(skillView.inputSkill()));
                developer.setDeveloperSkills(skillController.create(skillView.inputSkill()));
                developer.setDeveloperAccount(accountController.create(accountView.inputAccount()));
                developerController.create(developer);
                break;
            case 2:         //read all
                System.out.println(skillController.read());
                System.out.println(accountController.read());
                System.out.println(developerController.read());
                break;
            case 3:         //get by id
                System.out.println(INPUT_ID_TEXT);
                System.out.println(developerController.readById(choice()));
                break;
            case 4:         //edit by id
                System.out.println(INPUT_ID_TEXT);
                long idToEdit = choice();
                if (developerController.readById(idToEdit).getName() != null) {
                    developer.setName(developerView.inputDeveloper());
                    developer.setDeveloperSkills(skillView.inputSkill());
                    developer.setDeveloperSkills(skillView.inputSkill());
                    developer.setDeveloperAccount(accountView.inputAccount());
                    accountController.edit(idToEdit, developer.getDeveloperAccount());
                    developerController.edit(idToEdit, developer);
                    skillController.edit(developerController.readById(idToEdit).getDeveloperSkills(), developer.getDeveloperSkills());
                }
                break;
            case 5:         //delete by id
                System.out.println(INPUT_ID_TEXT);
                long idToDelete = choice();
                if (developerController.readById(idToDelete).getName() != null) {
                developerController.delete(idToDelete);
                accountController.delete(idToDelete);
                }
                break;
            default:
                System.out.println(NO_SUCH_NUMBER_TEXT);
        }
    }

    private int choice() {
        int input;

        while (!in.hasNextInt()) {
            System.out.println(ERROR_TEXT);
            in.next();
        }
        input = in.nextInt();
        return input;
    }
}