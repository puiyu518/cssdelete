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

insert into threads(username, title, content, category,created_at) values('admin','First Thread','testing','lecture','2020-04-05 20:54:18.742' );