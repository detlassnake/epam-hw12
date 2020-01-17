package ua.epam.hw7_8.repository.io;

import ua.epam.hw7_8.model.Skill;
import ua.epam.hw7_8.repository.SkillRepository;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class JavaIOSkillRepository implements SkillRepository {
    private final String EXCEPTION_TEXT = "IOException";
    private final String ID_NOT_FOUND_TEXT = "Skill id not found";
    private final String PATH_NAME = "src/main/resources/files/skills.txt";

    public Skill save(Skill data) {
        File file = new File(PATH_NAME);
        Long id;
        ArrayList<String> arrayList = new ArrayList<String>();
        PrintWriter pw;
        try {
            JavaIOUtilLogic.read(arrayList, file);
            pw = new PrintWriter(file);
            if (arrayList.size() == 0) {
                pw.println("1 " + data.getSkill());
                data.setId(1L);
            } else {
                id = JavaIOUtilLogic.lastId(arrayList);
                for (int i = 0; i < arrayList.size(); i++) {
                    pw.println(arrayList.get(i));
                }
                pw.println(++id + " " + data.getSkill());
                data.setId(id);
            }
            pw.close();
        } catch (IOException e) {
            System.out.println(EXCEPTION_TEXT);
        }
        return data;
    }

    public ArrayList<Skill> getAll() {
        File file = new File(PATH_NAME);
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<Skill> skillArrayList = new ArrayList<Skill>();
        try {
            JavaIOUtilLogic.read(arrayList, file);
        } catch (IOException e) {
            System.out.println(EXCEPTION_TEXT);
        }
        createListSkill(arrayList, skillArrayList);
        return skillArrayList;
    }

    public Skill getById(Long id) {
        File file = new File(PATH_NAME);
        ArrayList<String> arrayList = new ArrayList<String>();
        Skill skill = null;
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
                    skill = createSkill(id.toString(), fileStr[1]);
                }
            }
        }
        return skill;
    }

    public void update(Long id, Skill data) {
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
                        pw.println(id + " " + data.getSkill());
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

    private Skill createSkill(String idSkill, String skillStr) {
        Skill skill = new Skill();
        skill.setId(Long.parseLong(idSkill));
        skill.setSkill(skillStr);
        return skill;
    }

    private void createListSkill(ArrayList<String> arrayList, ArrayList<Skill> skillArrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            String[] fileStr = arrayList.get(i).split(" ");
            Skill skill = createSkill(fileStr[0], fileStr[1]);
            skillArrayList.add(skill);
        }
    }
}