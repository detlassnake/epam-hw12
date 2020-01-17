INSERT INTO skills(skill_name)
VALUES ('Java');
INSERT INTO skills(skill_name)
VALUES ('SQL');
INSERT INTO skills(skill_name)
VALUES ('js');

INSERT INTO accounts(account_name, account_status)
VALUES ('sergty@gmail.com', 'ACTIVE');
INSERT INTO accounts(account_name, account_status)
VALUES ('joe20@gmail.com', 'DELETED');
INSERT INTO accounts(account_name, account_status)
VALUES ('franklin345@gmail.com', 'BANNED');

INSERT INTO developers(developer_name, account_id)
VALUES ('Joe', '2');
INSERT INTO developers(developer_name, account_id)
VALUES ('Serg', '1');
INSERT INTO developers(developer_name, account_id)
VALUES ('Frank', '3');

INSERT INTO developers_skills(developer_id, skill_id)
VALUES ('2', '1');
INSERT INTO developers_skills(developer_id, skill_id)
VALUES ('1', '3');
INSERT INTO developers_skills(developer_id, skill_id)
VALUES ('3', '2');
INSERT INTO developers_skills(developer_id, skill_id)
VALUES ('3', '1');
INSERT INTO developers_skills(developer_id, skill_id)
VALUES ('1', '2');

UPDATE skills SET skill_name = 'Marketing' WHERE id = 4;
DELETE FROM skills WHERE id = 7;

SELECT * FROM skills;
SELECT * FROM accounts;
SELECT * FROM developers;
SELECT * FROM developers_skills;



