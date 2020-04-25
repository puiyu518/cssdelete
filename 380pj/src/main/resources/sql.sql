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


CREATE TABLE threads (
id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
username VARCHAR(50) NOT NULL,
title VARCHAR(64) NOT NULL,
content VARCHAR(1000) NOT NULL,
category VARCHAR(100) NOT NULL,
created_at TIMESTAMP NOT NULL,
primary key (id),
FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE thread_attachments (
id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
rid INTEGER NOT NULL,
attachmentName VARCHAR(256) NOT NULL,
mimeContentType VARCHAR(256) NOT NULL,
contents BLOB NOT NULL,
primary key (id),
FOREIGN KEY (rid) REFERENCES threads(id)

);


CREATE TABLE replies (
id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
username VARCHAR(50) NOT NULL,
content varchar(1000) NOT NULL,
rid INTEGER NOT NULL,
created_at TIMESTAMP NOT NULL, 
primary key (id),
FOREIGN KEY (rid) REFERENCES threads(id),
FOREIGN KEY (username) REFERENCES users(username)

);

CREATE TABLE reply_attachments (
id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
rid INTEGER,
attachmentName VARCHAR(256) NOT NULL,
mimeContentType VARCHAR(256) NOT NULL,
contents BLOB NOT NULL,
primary key (id),
FOREIGN KEY (rid) REFERENCES replies(id)

);


CREATE TABLE pollings (
id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
title VARCHAR(64) NOT NULL,
option1 VARCHAR(64) DEFAULT NULL,
option2 VARCHAR(64) DEFAULT NULL,
option3 VARCHAR(64) DEFAULT NULL,
option4 VARCHAR(64) DEFAULT NULL,
enabled BOOLEAN DEFAULT FALSE,
primary key (id)
);

CREATE TABLE polling_results (
id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
rid INTEGER NOT NULL,
username VARCHAR(50) NOT NULL,
voted INTEGER NOT NULL,
primary key (id),
FOREIGN KEY (username) REFERENCES users(username),
FOREIGN KEY (rid) REFERENCES pollings(id)
);



INSERT INTO users VALUES ('admin', '{noop}admin',FALSE);
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_ADMIN');


INSERT INTO users VALUES ('test', '{noop}test',FALSE);
INSERT INTO user_roles(username, role) VALUES ('test', 'ROLE_USER');


insert into threads(username, title, content, category,created_at) values('admin','First Thread','testing','lecture','2020-04-05 20:54:18.742' );
insert into threads(username, title, content, category,created_at) values('test','I am a user(Other)','I am a user - test','other','2020-04-05 20:54:18.742' );
insert into threads(username, title, content, category,created_at) values('test','I am a user(Lab)','I am a user - test','lab','2020-04-05 20:54:18.742' );


insert into replies(username, content, rid,created_at) values('test','Lab reply',3,'2020-04-05 20:54:18.742' );
insert into replies(username, content, rid,created_at) values('test','Other reply',2,'2020-04-05 20:54:18.742' );
insert into replies(username, content, rid,created_at) values('test','reply to admin',1,'2020-04-05 20:54:18.742' );


insert into pollings(title, option1, option2,option3,option4,enabled) values('Polling test','Option 1' ,'Option 2' ,'Option 3' ,'Option 4',true );
