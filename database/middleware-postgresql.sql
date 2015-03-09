/*
  Middleware schema creation script on Postgresql
*/


CREATE TABLE observation (
  id serial,
  tagID varchar(256) NOT NULL,
  deviceID varchar(256) NOT NULL,
  detectTime int8 NOT NULL DEFAULT 0,
  removeTime int8,
  CONSTRAINT pk_observation PRIMARY KEY (id)
);

CREATE TABLE users (
  id serial,
  userName varchar(100) NOT NULL,
  passwd varchar(100) NOT NULL,
  description varchar(200),
  CONSTRAINT pk_users PRIMARY KEY (id),
  CONSTRAINT idx_users_username UNIQUE (userName) 
);

CREATE TABLE roles (
  id serial,
  roleType varchar(20) NOT NULL DEFAULT 'Viewer',
  userName varchar(100) NOT NULL,
  CONSTRAINT pk_roles PRIMARY KEY (id),
  CONSTRAINT idx_roles_usernameAndroleType UNIQUE (userName,roleType),
  CONSTRAINT fk_roles_userName FOREIGN KEY (userName) REFERENCES users (userName) ON DELETE CASCADE
);

CREATE TABLE plugins (
  id serial,
  pluginName varchar(200) NOT NULL,
  class varchar(200) NOT NULL,
  CONSTRAINT pk_plugins PRIMARY KEY (id)
);

CREATE TABLE devices (
  id serial,
  connectType varchar(20) NOT NULL DEFAULT 'S',
  deviceType varchar(20) NOT NULL DEFAULT 'F',
  deviceName varchar(100) NOT NULL,
  deviceID varchar(256) NOT NULL,
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
);

CREATE TABLE userdevice (
  id serial,
  deviceid int NOT NULL,
  userid int NOT NULL,
  CONSTRAINT pk_userdevice PRIMARY KEY (id),
  CONSTRAINT fk_userdevice_deviceid FOREIGN KEY (deviceid) REFERENCES devices (id) ON DELETE CASCADE,
  CONSTRAINT fk_userdevice_userid FOREIGN KEY (userid) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE groups (
  id serial,
  groupname varchar(100) NOT NULL,
  description varchar(200),
  CONSTRAINT pk_groups PRIMARY KEY (id),
  CONSTRAINT idx_groups_groupname UNIQUE (groupname)
);

CREATE TABLE devicegroups (
  id serial,
  deviceid int NOT NULL,
  groupid int NOT NULL,
  CONSTRAINT pk_devicegroups PRIMARY KEY (id),
  CONSTRAINT fk_devicegroups_groupid FOREIGN KEY (groupid) REFERENCES groups (id) ON DELETE CASCADE,
  CONSTRAINT fk_devicegroups_deviceid FOREIGN KEY (deviceid) REFERENCES devices (id) ON DELETE CASCADE
);

CREATE TABLE tasks (
  id serial,
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
);

CREATE TABLE taskdevice (
  id serial,
  deviceid int NOT NULL,
  taskid int NOT NULL,
  groupid int NOT NULL,
  CONSTRAINT pk_taskdevice PRIMARY KEY (id),
  CONSTRAINT fk_taskdevice_deviceid FOREIGN KEY (deviceid) REFERENCES devices (id) ON DELETE CASCADE,
  CONSTRAINT fk_taskdevice_taskid FOREIGN KEY (taskid) REFERENCES tasks (id) ON DELETE CASCADE,
  CONSTRAINT fk_taskdevice_groupid FOREIGN KEY (groupid) REFERENCES groups (id) ON DELETE CASCADE
);

CREATE TABLE filedestination (
  id serial,
  taskid int NOT NULL,
  folder varchar(200) NOT NULL,
  CONSTRAINT pk_filedestination PRIMARY KEY (id),
  CONSTRAINT fk_filedestination_taskid FOREIGN KEY (taskid) REFERENCES tasks (id) ON DELETE CASCADE
);

CREATE TABLE httpdestination (
  id serial,
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
);

CREATE TABLE sysconfig (
  id serial,
  systemName varchar(100) NOT NULL,
  queueSize int NOT NULL DEFAULT 1000,
  soapStartup varchar(10) NOT NULL DEFAULT 'A',
  messageStartup varchar(10) NOT NULL DEFAULT 'A',
  currentTime int8,
  CONSTRAINT pk_sysconfig PRIMARY KEY (id)
);

CREATE TABLE httpcommandreply (
  id serial,
  ip varchar(100) NOT NULL DEFAULT '',
  port int NOT NULL,
  path varchar(200),
  username varchar(100),
  password varchar(100),
  authMode varchar(20),
  https varchar(20),
  CONSTRAINT idx_httpcommand_ip UNIQUE (ip),
  CONSTRAINT pk_httpcommandreply PRIMARY KEY (id)
);

CREATE INDEX idx_observation_deviceid ON observation (deviceid);
CREATE INDEX idx_observation_detectTime ON observation (detectTime);
CREATE INDEX idx_observation_removeTime ON observation (removeTime);

INSERT INTO users(userName,passwd,description) VALUES('admin', 'ubipass', 'built-in administrator');

INSERT INTO roles(roletype,userName) VALUES('Administrator','admin');

INSERT INTO plugins(pluginName,class) VALUES('Alien Class1 Reader', 'com.ubipass.middleware.plugin.alien.AlienClass1ReaderPlugin');
INSERT INTO plugins(pluginName,class) VALUES('Awid Reader', 'com.ubipass.middleware.plugin.awid.AwidReaderPlugin');
INSERT INTO plugins(pluginName,class) VALUES('Intermec Reader', 'com.ubipass.middleware.plugin.intermec.IntermecReaderPlugin');

INSERT INTO sysconfig(systemname,queuesize,soapstartup,messagestartup) VALUES('AutoID Middleware', 1000, 'A', 'A');  

