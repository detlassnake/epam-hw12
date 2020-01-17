package ua.epam.hw7_8.model;

import java.util.HashSet;
import java.util.Set;

public class Developer {
    private Set<Skill> devSkills = new HashSet<Skill>();
    private Account devAccount;
    private String name;
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public void setDevSkills(Skill devSkills) {
        this.devSkills.add(devSkills);
    }

    public void setDevAccount(Account devAccount) {
        this.devAccount = devAccount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Set<Skill> getDevSkills() {
        return devSkills;
    }

    public Account getDevAccount() {
        return devAccount;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name == null ? "" : this.id + " " + this.name + " " + this.devAccount.getEmail()
                + " " + this.devAccount.getAccountStatus() + " " + this.devSkills.toString() ;
    }
}