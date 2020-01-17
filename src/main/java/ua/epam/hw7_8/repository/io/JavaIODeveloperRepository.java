package ua.epam.hw7_8.repository.io;

import ua.epam.hw7_8.model.Developer;
import ua.epam.hw7_8.model.Skill;
import ua.epam.hw7_8.repository.DeveloperRepository;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class JavaIODeveloperRepository implements DeveloperRepository {
    private final String EXCEPTION_TEXT = "IOException";
    private final String ID_NOT_FOUND_TEXT = "Developer id not found";
    private final String PATH_NAME = "src/main/resources/files/developers.txt";

    private JavaIOAccountRepository accountRepository = new JavaIOAccountRepository();
    private JavaIOSkillRepository skillRepository = new JavaIOSkillRepository();

    public Developer save(Developer data) {
        File file = new File(PATH_NAME);
        Long id;
        ArrayList<String> arrayList = new ArrayList<String>();
        PrintWriter pw;
        try {
            JavaIOUtilLogic.read(arrayList, file);
            pw = new PrintWriter(file);
            if (arrayList.size() == 0) {
                ArrayList<Skill> skillArrayList = new ArrayList<Skill>(data.getDevSkills());
                Long skillId = skillArrayList.get(0).getId();
                pw.println("1 " + data.getName() + " " + data.getDevAccount().getId() + " " + skillId);
            } else {
                id = JavaIOUtilLogic.lastId(arrayList);
                for (int i = 0; i < arrayList.size(); i++) {
                    pw.println(arrayList.get(i));
                }
                ArrayList<Skill> skillArrayList = new ArrayList<Skill>(data.getDevSkills());
                Long skillId = skillArrayList.get(0).getId();
                pw.println(++id + " " + data.getName() + " " + data.getDevAccount().getId() + " " + skillId);
            }
            pw.close();
        } catch (IOException e) {
            System.out.println(EXCEPTION_TEXT);
        }
        return data;
    }

    public ArrayList<Developer> getAll() {
        File file = new File(PATH_NAME);
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<Developer> developerArrayList = new ArrayList<Developer>();
        try {
            JavaIOUtilLogic.read(arrayList, file);
        } catch (IOException e) {
            System.out.println(EXCEPTION_TEXT);
        }
        createListDeveloper(arrayList, developerArrayList);
        return developerArrayList;
    }

    public Developer getById(Long id) {
        File file = new File(PATH_NAME);
        ArrayList<String> arrayList = new ArrayList<String>();
        Developer developer = null;
        try {
            JavaIOUtilLogic.read(arrayList, file);
        } catch (IOException e) {
            System.out.println(EXCEPTION_TEXT);
        }
        if (arrayList.size() == 0) {
            System.out.println(ID_NOT_FOUND_TEXT);
        } else if (id > JavaIOUtilLogic.maxId(arrayList)) {
            System.out.println(ID_NOT_FOUND_TEXT);
        } else {
            for (int i = 0; i < arrayList.size(); i++) {
                String[] fileStr = arrayList.get(i).split(" ");
                int idFromFile = Integer.parseInt(fileStr[0]);
                if (idFromFile == id) {
                    developer = createDeveloper(fileStr[0],fileStr[1],fileStr[2],fileStr[3]);
                }
            }
        }
        return developer;
    }

    public void update(Long id, Developer data) {
        File file = new File(PATH_NAME);
        ArrayList<String> arrayList = new ArrayList<String>();
        PrintWriter pw;
        try {
            JavaIOUtilLogic.read(arrayList, file);
            if (arrayList.size() == 0) {
                System.out.println(ID_NOT_FOUND_TEXT);
            } else if (id > JavaIOUtilLogic.maxId(arrayList)) {
                System.out.println(ID_NOT_FOUND_TEXT);
            } else {
                pw = new PrintWriter(file);
                for (int i = 0; i < arrayList.size(); i++) {
                    String[] fileStr = arrayList.get(i).split(" ");
                    int idFromFile = Integer.parseInt(fileStr[0]);
                    if (idFromFile != id) {
                        pw.println(arrayList.get(i));
                    } else {
                        long accountId = Long.parseLong(fileStr[2]);
                        long skillId = Long.parseLong(fileStr[3]);
                        pw.println(id + " " + data.getName() + " " + ++accountId + " " + ++skillId);
                    }
                }
                pw.close();
            }
        } catch (IOException e) {
            System.out.println(EXCEPTION_TEXT);
        }
    }

    public void deleteById(Long id) {
        File file = new File(PATH_NAME);
        ArrayList<String> arrayList = new ArrayList<String>();
        PrintWriter pw;
        try {
            JavaIOUtilLogic.read(arrayList, file);
            if (arrayList.size() == 0) {
                System.out.println(ID_NOT_FOUND_TEXT);
            } else if (id > JavaIOUtilLogic.maxId(arrayList)) {
                System.out.println(ID_NOT_FOUND_TEXT);
            } else {
                pw = new PrintWriter(file);
                for (int i = 0; i < arrayList.size(); i++) {
                    String[] fileStr = arrayList.get(i).split(" ");
                    int idFromFile = Integer.parseInt(fileStr[0]);
                    if (idFromFile != id) {
                        pw.println(arrayList.get(i));
                    }
                }
                pw.close();
            }
        } catch (IOException e) {
            System.out.println(EXCEPTION_TEXT);
        }
    }

    private Developer createDeveloper(String idDeveloper, String nameStr, String idAccount, String idSkill) {
        Developer developer = new Developer();
        developer.setId(Long.parseLong(idDeveloper));
        developer.setName(nameStr);
        developer.setDevSkills(skillRepository.getById(Long.parseLong(idSkill)));
        developer.setDevAccount(accountRepository.getById(Long.parseLong(idAccount)));
        return developer;
    }

    private void createListDeveloper(ArrayList<String> arrayList, ArrayList<Developer> developerArrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            String[] fileStr = arrayList.get(i).split(" ");
            Developer developer = createDeveloper(fileStr[0], fileStr[1], fileStr[2], fileStr[3]);
            developerArrayList.add(developer);
        }
    }
}