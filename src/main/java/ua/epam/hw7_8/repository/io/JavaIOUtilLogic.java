package ua.epam.hw7_8.repository.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class JavaIOUtilLogic {
    public static void read(ArrayList<String> arrayList, File file) throws IOException {
        Scanner in = new Scanner(file);
        while (in.hasNextLine()) {
            arrayList.add(in.nextLine());
        }
        in.close();
    }

    public static int maxId(ArrayList<String> arrayList) {
        int maxId = -10000;
        int id;
        for (int i = 0; i < arrayList.size(); i++) {
            String[] fileStr = arrayList.get(i).split(" ");
            id = Integer.parseInt(fileStr[0]);
            if (maxId <= id) {
                maxId = id;
            }
        }
        return maxId;
    }

    public static Long lastId(ArrayList<String> arrayList) {
        String[] lastStr = arrayList.get(arrayList.size() - 1).split(" ");
        return Long.parseLong(lastStr[0]);
    }
}