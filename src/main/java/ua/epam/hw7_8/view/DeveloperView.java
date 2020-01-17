package ua.epam.hw7_8.view;

import java.util.Scanner;

public class DeveloperView {
    private final String ERROR_TEXT = "error";
    private final String INPUT_TEXT = "Input name:";
    static Scanner in = new Scanner(System.in);

    public String inputDeveloper() {
        String input;

        System.out.println(INPUT_TEXT);
        while (!in.hasNextLine()) {
            System.out.println(ERROR_TEXT);
            in.next();
        }
        input = in.nextLine();
        return input;
    }
}