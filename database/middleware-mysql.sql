/*
  Middleware schema creation script on MySQL
 */


CREATE TABLE observation (
  id int NOT NULL AUTO_INCREMENT,
  tagID varchar(255) NOT NULL,
  deviceID varchar(255) NOT NULL,
  detectTime bigint NOT NULL,
  removeTime bigint,
  CONSTRAINT pk_observation PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE users (
  id int NOT NULL AUTO_INCREMENT,
  userName varchar(100) NOT NULL,
  passwd varchar(100) NOT NULL,
  description varchar(200),
  CONSTRAINT pk_users PRIMARY KEY (id),
  CONSTRAINT idx_users_username UNIQUE (userName)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE roles (
  id int NOT NULL AUTO_INCREMENT,
  roleType varchar(20) NOT NULL DEFAULT 'Viewer',
  userName varchar(100) NOT NULL,
  CONSTRAINT pk_roles PRIMARY KEY (id),
  CONSTRAINT idx_roles_usernameAndroleType UNIQUE (userName,roleType),
  CONSTRAINT fk_roles_userName FOREIGN KEY (userName) REFERENCES users (userName) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE plugins (
  id int NOT NULL AUTO_INCREMENT,
  pluginName varchar(200) NOT NULL,
  class varchar(200) NOT NULL,
  CONSTRAINT pk_plugins PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE devices (
  id int NOT NULL AUTO_INCREMENT,
  connectType varchar(20) NOT NULL DEFAULT 'S',
  deviceType varchar(20) NOT NULL DEFAULT 'F',
  deviceName varchar(100) NOT NULL,
  deviceID varchar(255) NOT NULL,
  userName varchar(100),
  passwd varchar(100),
  connectionName varchar(100) NOT NULL,
  port int,
  persistTime int DEFAULT 0,
  command varchar(100),
  class varchar(200) NOT NULL,
  startUp varchar(10) NOT NULL DEFAULT 'A',
  description varchar(200),
  CONSTRAINT pk_devices PRIMARY KEY (id),
  CONSTRAINT idx_devices_deviceid UNIQUE (deviceid),
  CONSTRAINT idx_devices_devicename UNIQUE (devicename)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE userdevice (
  id int NOT NULL AUTO_INCREMENT,
  deviceid int NOT NULL,
  userid int NOT NULL,
  CONSTRAINT pk_userdevice PRIMARY KEY (id),
  CONSTRAINT fk_userdevice_deviceid FOREIGN KEY (deviceid) REFERENCES devices (id) ON DELETE CASCADE,
  CONSTRAINT fk_userdevice_userid FOREIGN KEY (userid) REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE groups (
  id int NOT NULL AUTO_INCREMENT,
  groupname varchar(100) NOT NULL,
  description varchar(200),
  CONSTRAINT pk_groups PRIMARY KEY (id),
  CONSTRAINT idx_groups_groupname UNIQUE (groupname)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE devicegroups (
  id int NOT NULL AUTO_INCREMENT,
  deviceid int NOT NULL,
  groupid int NOT NULL,
  CONSTRAINT pk_devicegroups PRIMARY KEY (id),
  CONSTRAINT fk_devicegroups_groupid FOREIGN KEY (groupid) REFERENCES groups (id) ON DELETE CASCADE,
  CONSTRAINT fk_devicegroups_deviceid FOREIGN KEY (deviceid) REFERENCES devices (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE tasks (
  id int NOT NULL AUTO_INCREMENT,
  taskName varchar(200) NOT NULL,
  taskType varchar(10) NOT NULL DEFAULT 'A',
  startup varchar(10) DEFAULT 'M',
  triggerMode varchar(10) NOT NULL DEFAULT 'T',
  parameter int,
  formatType varchar(10) NOT NULL DEFAULT 'F',
  topLevelIDType varchar(10) NOT NULL DEFAULT 'M',
  readerIDType varchar(10) NOT NULL DEFAULT 'N',
  command varchar(100),
  description varchar(200),
  CONSTRAINT pk_tasks PRIMARY KEY (id),
  CONSTRAINT idx_tasks_taskname UNIQUE (taskName)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE taskdevice (
  id int NOT NULL AUTO_INCREMENT,
  deviceid int NOT NULL,
  taskid int NOT NULL,
  groupid int NOT NULL,
  CONSTRAINT pk_taskdevice PRIMARY KEY (id),
  CONSTRAINT fk_taskdevice_deviceid FOREIGN KEY (deviceid) REFERENCES devices (id) ON DELETE CASCADE,
  CONSTRAINT fk_taskdevice_taskid FOREIGN KEY (taskid) REFERENCES tasks (id) ON DELETE CASCADE,
  CONSTRAINT fk_taskdevice_groupid FOREIGN KEY (groupid) REFERENCES groups (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE filedestination (
  id int NOT NULL AUTO_INCREMENT,
  taskid int NOT NULL,
  folder varchar(200) NOT NULL,
  CONSTRAINT pk_filedestination PRIMARY KEY (id),
  CONSTRAINT fk_filedestination_taskid FOREIGN KEY (taskid) REFERENCES tasks (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE httpdestination (
  id int NOT NULL AUTO_INCREMENT,
  taskid int NOT NULL,
  ip varchar(100) NOT NULL,
  port int NOT NULL,
  path varchar(200),
  username varchar(100),
  password varchar(100),
  authMode varchar(20),
  https varchar(20),
  CONSTRAINT pk_httpdestination PRIMARY KEY (id),
  CONSTRAINT fk_httpdestination_taskid FOREIGN KEY (taskid) REFERENCES tasks (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE sysconfig (
  id int NOT NULL AUTO_INCREMENT,
  systemName varchar(100) NOT NULL,
  queueSize int NOT NULL DEFAULT 1000,
  soapStartup varchar(10) NOT NULL DEFAULT 'A',
  messageStartup varchar(10) NOT NULL DEFAULT 'A',
  currentTime bigint,
  CONSTRAINT pk_sysconfig PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE httpcommandreply (
  id int NOT NULL AUTO_INCREMENT,
  ip varchar(100) NOT NULL,
  port int NOT NULL,
  path varchar(200),
  username varchar(100),
  password varchar(100),
  authMode varchar(20),
  https varchar(20),
  CONSTRAINT idx_httpcommand_ip UNIQUE (ip),
  CONSTRAINT pk_httpcommandreply PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE INDEX idx_observation_deviceid ON observation(deviceID);
CREATE INDEX idx_observation_detectTime ON observation(detectTime);
CREATE INDEX idx_observation_removeTime ON observation(removeTime);

INSERT INTO users VALUES(NULL, 'admin', 'ubipass', 'built-in administrator');

INSERT INTO roles VALUES(NULL, 'Administrator','admin');

INSERT INTO plugins VALUES(NULL, 'Alien Class1 Reader', 'com.ubipass.middleware.plugin.alien.AlienClass1ReaderPlugin');
INSERT INTO plugins VALUES(NULL, 'Awid Reader', 'com.ubipass.middleware.plugin.awid.AwidReaderPlugin');
INSERT INTO plugins VALUES(NULL, 'Intermec Reader', 'com.ubipass.middleware.plugin.intermec.IntermecReaderPlugin');

INSERT INTO sysconfig VALUES(NULL, 'AutoID Middleware', 1000, 'A', 'A', NULL);  

