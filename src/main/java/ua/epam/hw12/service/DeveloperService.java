package ua.epam.hw12.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.epam.hw12.model.Developer;
import ua.epam.hw12.repository.DeveloperRepository;
import ua.epam.hw12.repository.jdbc.JdbcDeveloperRepository;

import java.util.ArrayList;

public class DeveloperService {
    public static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private DeveloperRepository developerRepository;

    public DeveloperService() {
        developerRepository = new JdbcDeveloperRepository();
    }

    public Developer create(Developer developer) {
        logger.debug("Developer service->Create");
        return developerRepository.save(developer);
    }

    public ArrayList<Developer> read() {
        logger.debug("Developer service->Read");
        return developerRepository.getAll();
    }

    public Developer readById(long id) {
        logger.debug("Developer service->Read by id");
        return developerRepository.getById(id);
    }

    public void edit(long id, Developer developer) {
        logger.debug("Developer service->Edit by id");
        developerRepository.update(id, developer);
    }

    public void delete(long id) {
        logger.debug("Developer service->Delete");
        developerRepository.deleteById(id);
    }
}
