DROP TABLE IF EXISTS developers_skills;
DROP TABLE IF EXISTS developers;
DROP TABLE IF EXISTS skills;
DROP TABLE IF EXISTS accounts;

CREATE TABLE IF NOT EXISTS accounts (
    id             INT PRIMARY KEY AUTO_INCREMENT,
    account_name   VARCHAR(255) NOT NULL,
    account_status VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS developers (
    id             INT PRIMARY KEY AUTO_INCREMENT,
    developer_name VARCHAR(255) NOT NULL,
    account_id     INT          NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts (id)
);

CREATE TABLE IF NOT EXISTS skills (
    id         INT PRIMARY KEY AUTO_INCREMENT,
    skill_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS developers_skills (
    developer_id INT NOT NULL,
    skill_id     INT NOT NULL,
    UNIQUE (developer_id, skill_id),
    FOREIGN KEY (developer_id) REFERENCES developers (id),
    FOREIGN KEY (skill_id) REFERENCES skills (id)
);
