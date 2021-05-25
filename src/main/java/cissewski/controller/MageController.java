package cissewski.controller;

import cissewski.repository.MageRepository;
import cissewski.entity.Mage;

public class MageController {
    private MageRepository repository;

    public MageController(MageRepository repository) {
        this.repository = repository;
    }

    public String find(String name) {
        if (repository.find(name).isEmpty()) {
            return "not found";
        }
        return repository.find(name).toString();
    }

    public String delete(String name) {
        try {
            repository.delete(name);
            return "done";
        } catch (IllegalArgumentException e) {
            return "bad request";
        }
    }

    public String save(String name, int level) {
        try {
            repository.save(Mage.builder().name(name).level(level).build());
            return "done";
        } catch (IllegalArgumentException e) {
            return "bad request";
        }
    }
}
