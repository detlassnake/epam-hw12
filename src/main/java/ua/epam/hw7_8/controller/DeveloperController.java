package ua.epam.hw7_8.controller;

import ua.epam.hw7_8.model.Developer;
import ua.epam.hw7_8.service.DeveloperService;
import java.util.ArrayList;

public class DeveloperController {
    private DeveloperService developerService;

    public DeveloperController() {
        developerService = new DeveloperService();
    }

    public void create(Developer developer) {
        developerService.create(developer);
    }

    public ArrayList<Developer> read() {
        return developerService.read();
    }

    public Developer readById(long id) {
        return developerService.readById(id);
    }

    public void edit(long id, Developer developer) {
        developerService.edit(id, developer);
    }

    public void delete(long id) {
        developerService.delete(id);
    }
}