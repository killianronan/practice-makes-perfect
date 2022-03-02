use chatDB;
CREATE TABLE User(
  id int not null AUTO_INCREMENT,
  username varchar(20) not null,
  full_name varchar(100) not null,
  email varchar(100) not null,
  email_confirmed bit not null,
  profile_picture BLOB null,
  created_at timestamp not null,
  active bit not null,
  PRIMARY KEY(id),
  CONSTRAINT check_UsernameHasNum CHECK (username regexp '[0-9]'),
  CONSTRAINT check_UsernameLen CHECK (length(userName) > 3)
); 
CREATE TABLE Room(
  id int not null AUTO_INCREMENT,
  name varchar(100) not null,
  created_by int not null,
  created_at timestamp not null,
  active bit not null,
  FOREIGN KEY(created_by) REFERENCES User(id), 
  PRIMARY KEY(id)
);
CREATE TABLE Message(
  id int not null AUTO_INCREMENT,
  roomID int not null,
  userID int not null,
  responseTo int null,
  text varchar(1000) not null,
  message_time timestamp not null,
  FOREIGN KEY(roomID) REFERENCES Room(id), 
  FOREIGN KEY(userID) REFERENCES User(id), 
  FOREIGN KEY(responseTo) REFERENCES Message(id), 
  PRIMARY KEY(id)
);
CREATE TABLE Participants(
  id int not null AUTO_INCREMENT,
  roomID int not null,
  userID int not null,
  time_joined timestamp not null,
  time_left timestamp null,
  FOREIGN KEY(roomID) REFERENCES Room(id), 
  FOREIGN KEY(userID) REFERENCES User(id), 
  PRIMARY KEY(id)
);
CREATE TABLE Notification(
  id int not null AUTO_INCREMENT,
  roomID int not null,
  userID int not null,
  messageID int not null,
  isRead bit not null,
  FOREIGN KEY(roomID) REFERENCES Room(id), 
  FOREIGN KEY(userID) REFERENCES User(id), 
  FOREIGN KEY(messageID) REFERENCES Message(id), 
  PRIMARY KEY(id)
);

CREATE TABLE Attachment(
  id int not null AUTO_INCREMENT,
  messageID int not null,
  type varchar(20) not null,
  name varchar(100) not null,
  file BLOB null,
  FOREIGN KEY(messageID) REFERENCES Message(id), 
  PRIMARY KEY(id)
);

-- 10 users
INSERT INTO user(userName,full_name,email, email_confirmed, profile_picture, created_at, active)
VALUES('user1','Killian Ronan','kronan@tcd.ie', 0, null, NOW(), 0);
INSERT INTO user(userName,full_name,email, email_confirmed, profile_picture, created_at, active)
VALUES('user2','John Doe','jdoe@tcd.ie', 0, null, NOW(), 0);
INSERT INTO user(userName,full_name,email, email_confirmed, profile_picture, created_at, active)
VALUES('user3','Mary Magdalene','mmagdelene@tcd.ie', 0, null, NOW(), 0);
INSERT INTO user(userName,full_name,email, email_confirmed, profile_picture, created_at, active)
VALUES('user4','Robin Banks','rbanks@tcd.ie', 0, null, NOW(), 0);
INSERT INTO user(userName,full_name,email, email_confirmed, profile_picture, created_at, active)
VALUES('user5','Ben Dover','bdover@tcd.ie', 0, null, NOW(), 0);
INSERT INTO user(userName,full_name,email, email_confirmed, profile_picture, created_at, active)
VALUES('user6','Saad Maan','smaan@tcd.ie', 0, null, NOW(), 0);
INSERT INTO user(userName,full_name,email, email_confirmed, profile_picture, created_at, active)
VALUES('user7','Patrick Ennis','pennis@tcd.ie', 0, null, NOW(), 0);
INSERT INTO user(userName,full_name,email, email_confirmed, profile_picture, created_at, active)
VALUES('user8','Chris Peter Bacon','chrispbacon@tcd.ie', 0, null, NOW(), 0);
INSERT INTO user(userName,full_name,email, email_confirmed, profile_picture, created_at, active)
VALUES('user9','Faruk You','faryou@tecd.ie', 0, null, NOW(), 0);
INSERT INTO user(userName,full_name,email, email_confirmed, profile_picture, created_at, active)
VALUES('user10','Moe Lester','mlester@tecd.ie', 0, null, NOW(), 0);

-- 3 rooms
INSERT INTO Room(name, created_by, created_at, active)
VALUES('room1', 1,NOW(), 0);
INSERT INTO Room(name, created_by, created_at, active)
VALUES('room2', 4,NOW(), 0);
INSERT INTO Room(name, created_by, created_at, active)
VALUES('room3', 7,NOW(), 0);

-- add room participants
-- room 1
INSERT INTO participants(roomID, userID, time_joined, time_left)
VALUES(1, 1,NOW(), null);
INSERT INTO participants(roomID, userID, time_joined, time_left)
VALUES(1, 2,NOW(), null);
INSERT INTO participants(roomID, userID, time_joined, time_left)
VALUES(1, 3,NOW(), null);

-- room 2
INSERT INTO participants(roomID, userID, time_joined, time_left)
VALUES(2, 4,NOW(), null);
INSERT INTO participants(roomID, userID, time_joined, time_left)
VALUES(2, 5,NOW(), null);
INSERT INTO participants(roomID, userID, time_joined, time_left)
VALUES(2, 6,NOW(), null);

-- room 3
INSERT INTO participants(roomID, userID, time_joined, time_left)
VALUES(3, 7,NOW(), null);
INSERT INTO participants(roomID, userID, time_joined, time_left)
VALUES(3, 8,NOW(), null);
INSERT INTO participants(roomID, userID, time_joined, time_left)
VALUES(3, 9,NOW(), null);
INSERT INTO participants(roomID, userID, time_joined, time_left)
VALUES(3, 10,NOW(), null);

-- messages 
-- room1
INSERT INTO message(roomID, userID, responseTo, text, message_time)
VALUES(1, 1, null, 'Hello room 1', NOW());
INSERT INTO message(roomID, userID, responseTo, text, message_time)
VALUES(1, 2, null, 'Hello room 1', NOW());
INSERT INTO message(roomID, userID, responseTo, text, message_time)
VALUES(1, 3, null, 'Hello room 1', NOW());

-- room2
INSERT INTO message(roomID, userID, responseTo, text, message_time)
VALUES(2, 4, null, 'Hello room 2', NOW());
INSERT INTO message(roomID, userID, responseTo, text, message_time)
VALUES(2, 5, null, 'Hello room 2', NOW());
INSERT INTO message(roomID, userID, responseTo, text, message_time)
VALUES(2, 6, null, 'Hello room 2', NOW());

-- room3
INSERT INTO message(roomID, userID, responseTo, text, message_time)
VALUES(3, 7, null, 'Hello room 3', NOW());
INSERT INTO message(roomID, userID, responseTo, text, message_time)
VALUES(3, 8, null, 'Hello room 3', NOW());
INSERT INTO message(roomID, userID, responseTo, text, message_time)
VALUES(3, 9, null, 'Hello room 3', NOW());
INSERT INTO message(roomID, userID, responseTo, text, message_time)
VALUES(3, 10, null, 'Hello room 3', NOW());

-- notifications for each message

INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(1, 2, 1, 0);
INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(1, 3, 1, 0);

INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(1, 1, 2, 0);
INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(1, 3, 2, 0);

INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(1, 1, 3, 0);
INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(1, 2, 3, 0);

INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(2, 5, 4, 0);
INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(2, 6, 4, 0);

INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(2, 4, 5, 0);
INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(2, 6, 5, 0);

INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(2, 4, 6, 0);
INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(2, 5, 6, 0);

INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(3, 8, 7, 0);
INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(3, 9, 7, 0);
INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(3, 10, 7, 0);

INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(3, 7, 8, 0);
INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(3, 9, 8, 0);
INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(3, 10, 8, 0);

INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(3, 7, 9, 0);
INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(3, 8, 9, 0);
INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(3, 10, 9, 0);

INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(3, 7, 10, 0);
INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(3, 8, 10, 0);
INSERT INTO notification(roomID, userID, messageID, isRead)
VALUES(3, 9, 10, 0);

-- Attachments
INSERT INTO attachment(messageID, type, name, file)
VALUES(3, "jpeg", "photo1", 0xFF);
INSERT INTO attachment(messageID, type, name, file)
VALUES(4, "png", "C:\images\photo1", null);

-- Views
-- Participants from 2019
CREATE VIEW ParticipantsFrom2019 AS
SELECT userID, time_joined
FROM participants
WHERE time_joined >= TIMESTAMP("2019-01-01",  "00:00:00");

-- Email confirmed users
CREATE VIEW EmailConfirmedUsers AS
SELECT username, full_name, email, created_at
FROM user
WHERE email_confirmed = 1;

-- Non Email confirmed users
CREATE VIEW NonEmailConfirmedUsers AS
SELECT username, full_name, email, created_at
FROM user
WHERE email_confirmed = 0;

-- Triggers
-- Adds user who creates room to participants table
CREATE TRIGGER addRoomCreator 
AFTER INSERT ON room 
FOR EACH ROW 
INSERT INTO participants(roomID, userID, time_joined, time_left)
VALUES(NEW.id, NEW.created_by, NOW(), null);


CREATE TRIGGER removeMessageAttachment 
BEFORE DELETE ON message 
FOR EACH ROW 
DELETE FROM attachment WHERE attachment.messageID=OLD.id; 

CREATE TRIGGER removeMessageNotifications
BEFORE DELETE ON message 
FOR EACH ROW 
DELETE FROM notification WHERE notification.messageID=OLD.id; 

INSERT INTO Room(name, created_by, created_at, active)
VALUES('room4', 1, NOW(), 0);

DELETE FROM message WHERE id=3;

UPDATE participants set time_left = NOW() where userID = 2 and roomID = 1;
