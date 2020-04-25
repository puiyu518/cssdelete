CREATE TABLE users (
username VARCHAR(50) NOT NULL,
password VARCHAR(50) NOT NULL,
disabled BOOLEAN DEFAULT FALSE,
PRIMARY KEY (username)
);
CREATE TABLE user_roles (
user_role_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
username VARCHAR(50) NOT NULL,
role VARCHAR(50) NOT NULL,
PRIMARY KEY (user_role_id),
FOREIGN KEY (username) REFERENCES users(username)
);

-- noop->encrypt
INSERT INTO users VALUES ('admin', '{noop}admin',FALSE);
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_ADMIN');


INSERT INTO users VALUES ('test', '{noop}test',FALSE);
INSERT INTO user_roles(username, role) VALUES ('test', 'ROLE_USER');


