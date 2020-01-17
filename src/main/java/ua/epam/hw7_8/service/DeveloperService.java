package ua.epam.hw7_8.service;

import ua.epam.hw7_8.model.Developer;
import ua.epam.hw7_8.repository.DeveloperRepository;
import ua.epam.hw7_8.repository.jdbc.JdbcDeveloperRepository;
import java.util.ArrayList;

public class DeveloperService {
    private DeveloperRepository developerRepository;

    public DeveloperService() {
        developerRepository = new JdbcDeveloperRepository();
    }

    public void create(Developer developer) {
        developerRepository.save(developer);
    }

    public ArrayList<Developer> read() {
        return developerRepository.getAll();
    }

    public Developer readById(long id) {
        return developerRepository.getById(id);
    }

    public void edit(long id, Developer developer) {
        developerRepository.update(id, developer);
    }

    public void delete(long id) {
        developerRepository.deleteById(id);
    }
}
