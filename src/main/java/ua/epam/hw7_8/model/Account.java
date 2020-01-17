package ua.epam.hw7_8.model;

public class Account {
    private String email;
    private AccountStatus accountStatus;
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    @Override
    public String toString() {
        return this.email == null ? "" : this.id + " " + this.email + " " + this.accountStatus;
    }
}