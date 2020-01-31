package ua.epam.hw12.controller;

import ua.epam.hw12.model.Developer;
import ua.epam.hw12.service.DeveloperService;

import java.util.ArrayList;

public class DeveloperController {
    private DeveloperService developerService;

    public DeveloperController() {
        developerService = new DeveloperService();
    }

    public Developer create(Developer developer) {
        return developerService.create(developer);
    }

    public ArrayList<Developer> read() {
        return developerService.read();
    }

    public Developer readById(long id) {
        return developerService.readById(id);
    }

    public void edit(long id, Developer developer) {
        developerService.update(id, developer);
    }

    public void delete(long id) {
        developerService.delete(id);
    }
}