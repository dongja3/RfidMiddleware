-- Middleware schema creation script on Oracle

CREATE TABLE observation (
  id NUMBER(10) NOT NULL,
  tagID VARCHAR2(256) NOT NULL,
  deviceID VARCHAR2(256) NOT NULL,
  detectTime NUMBER(20) NOT NULL,
  removeTime NUMBER(20),
  CONSTRAINT pk_observation PRIMARY KEY (id)
);

CREATE TABLE users (
  id NUMBER(10) NOT NULL,
  userName VARCHAR2(100) NOT NULL,
  passwd VARCHAR2(100) NOT NULL,
  description VARCHAR2(200),
  CONSTRAINT pk_users PRIMARY KEY (id),
  CONSTRAINT idx_users_username UNIQUE (userName)
);

CREATE TABLE roles (
  id NUMBER(10) NOT NULL,
  roleType VARCHAR2(20) DEFAULT 'Viewer' NOT NULL,
  userName VARCHAR2(100) NOT NULL,
  CONSTRAINT pk_roles PRIMARY KEY (id),
  CONSTRAINT idx_roles_usernameAndroleType UNIQUE (userName,roleType),
  CONSTRAINT fk_roles_userName FOREIGN KEY (userName) REFERENCES users (userName) ON DELETE CASCADE
);

CREATE TABLE plugins (
  id NUMBER(10) NOT NULL,
  pluginName VARCHAR2(200) NOT NULL,
  class VARCHAR2(200) NOT NULL,
  CONSTRAINT pk_plugins PRIMARY KEY (id)
);

CREATE TABLE devices (
  id NUMBER(10) NOT NULL,
  connectType VARCHAR2(20) DEFAULT 'S' NOT NULL,
  deviceType VARCHAR2(20) DEFAULT 'F' NOT NULL,
  deviceName VARCHAR2(100) NOT NULL,
  deviceID VARCHAR2(256) NOT NULL,
  userName VARCHAR2(100),
  passwd VARCHAR2(100),
  connectionName VARCHAR2(100) NOT NULL,
  port NUMBER(10),
  persistTime NUMBER(10) DEFAULT 0,
  command VARCHAR2(100),
  class VARCHAR2(200) NOT NULL,
  startUp VARCHAR2(10) DEFAULT 'A' NOT NULL,
  description VARCHAR2(200),
  CONSTRAINT pk_devices PRIMARY KEY (id),
  CONSTRAINT idx_devices_deviceid UNIQUE (deviceid),
  CONSTRAINT idx_devices_devicename UNIQUE (devicename)
);

CREATE TABLE userdevice (
  id NUMBER(10) NOT NULL,
  deviceid NUMBER(10) NOT NULL,
  userid NUMBER(10) NOT NULL,
  CONSTRAINT pk_userdevice PRIMARY KEY (id),
  CONSTRAINT fk_userdevice_deviceid FOREIGN KEY (deviceid) REFERENCES devices (id) ON DELETE CASCADE,
  CONSTRAINT fk_userdevice_userid FOREIGN KEY (userid) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE groups (
  id NUMBER(10) NOT NULL,
  groupname VARCHAR2(100) NOT NULL,
  description VARCHAR2(200),
  CONSTRAINT pk_groups PRIMARY KEY (id),
  CONSTRAINT idx_groups_groupname UNIQUE (groupname)
);

CREATE TABLE devicegroups (
  id NUMBER(10) NOT NULL,
  deviceid NUMBER(10) NOT NULL,
  groupid NUMBER(10) NOT NULL,
  CONSTRAINT pk_devicegroup PRIMARY KEY (id),
  CONSTRAINT fk_devicegroup_groupid FOREIGN KEY (groupid) REFERENCES groups (id) ON DELETE CASCADE,
  CONSTRAINT fk_devicegroup_deviceid FOREIGN KEY (deviceid) REFERENCES devices (id) ON DELETE CASCADE
);

CREATE TABLE tasks (
  id NUMBER(10) NOT NULL,
  taskName VARCHAR2(200) NOT NULL,
  taskType VARCHAR2(10) DEFAULT 'A' NOT NULL,
  startup VARCHAR2(10) DEFAULT 'M',
  triggerMode VARCHAR2(10) DEFAULT 'T' NOT NULL,
  parameter NUMBER(10),
  formatType VARCHAR2(10) DEFAULT 'F' NOT NULL,
  topLevelIDType VARCHAR2(10) DEFAULT 'M' NOT NULL,
  readerIDType VARCHAR2(10) DEFAULT 'N' NOT NULL,
  command VARCHAR2(100),
  description VARCHAR2(200),
  CONSTRAINT pk_tasks PRIMARY KEY (id),
  CONSTRAINT idx_tasks_taskname UNIQUE (taskName)
);

CREATE TABLE taskdevice (
  id NUMBER(10) NOT NULL,
  deviceid NUMBER(10) NOT NULL,
  taskid NUMBER(10) NOT NULL,
  groupid NUMBER(10) NOT NULL,
  CONSTRAINT pk_taskdevice PRIMARY KEY (id),
  CONSTRAINT fk_taskdevice_deviceid FOREIGN KEY (deviceid) REFERENCES devices (id) ON DELETE CASCADE,
  CONSTRAINT fk_taskdevice_taskid FOREIGN KEY (taskid) REFERENCES tasks (id) ON DELETE CASCADE,
  CONSTRAINT fk_taskdevice_groupid FOREIGN KEY (groupid) REFERENCES groups (id) ON DELETE CASCADE
);

CREATE TABLE filedestination (
  id NUMBER(10) NOT NULL,
  taskid NUMBER(10) NOT NULL,
  folder VARCHAR2(200) NOT NULL,
  CONSTRAINT pk_filedestination PRIMARY KEY (id),
  CONSTRAINT fk_filedestination_taskid FOREIGN KEY (taskid) REFERENCES tasks (id) ON DELETE CASCADE
);

CREATE TABLE httpdestination (
  id NUMBER(10) NOT NULL,
  taskid NUMBER(10) NOT NULL,
  ip VARCHAR2(100) NOT NULL,
  port NUMBER(10) NOT NULL,
  path VARCHAR2(200),
  username VARCHAR2(100),
  password VARCHAR2(100),
  authMode varchar(20),
  https varchar(20),
  CONSTRAINT pk_socketdestination PRIMARY KEY (id),
  CONSTRAINT fk_socketdestination_taskid FOREIGN KEY (taskid) REFERENCES tasks (id) ON DELETE CASCADE
);

CREATE TABLE sysconfig (
  id NUMBER(10) NOT NULL,
  systemName VARCHAR2(100) NOT NULL,
  queueSize NUMBER(10) DEFAULT 1000 NOT NULL,
  soapStartup VARCHAR2(10) DEFAULT 'A' NOT NULL,
  messageStartup VARCHAR2(10) DEFAULT 'A' NOT NULL,
  currentTime NUMBER(20),
  CONSTRAINT pk_sysconfig PRIMARY KEY (id)
);

CREATE TABLE httpcommandreply (
  id NUMBER(10) NOT NULL,
  ip VARCHAR2(100) NOT NULL,
  port NUMBER(10) NOT NULL,
  path VARCHAR2(200),
  username VARCHAR2(100),
  password VARCHAR2(100),
  authMode varchar(20),
  https varchar(20),
  CONSTRAINT idx_httpcommand_ip UNIQUE (ip),
  CONSTRAINT pk_httpcommandreply PRIMARY KEY (id)
);


CREATE SEQUENCE seq_observation;
CREATE SEQUENCE seq_users;
CREATE SEQUENCE seq_roles;
CREATE SEQUENCE seq_devices;
CREATE SEQUENCE seq_userdevice;
CREATE SEQUENCE seq_groups;
CREATE SEQUENCE seq_devicegroups;
CREATE SEQUENCE seq_tasks;
CREATE SEQUENCE seq_taskdevice;
CREATE SEQUENCE seq_filedestination;
CREATE SEQUENCE seq_httpdestination;
CREATE SEQUENCE seq_httpcommandreply;

CREATE OR REPLACE TRIGGER trg_observation
BEFORE INSERT ON observation FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT seq_observation.nextval INTO :new.id FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_users
BEFORE INSERT ON users FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT seq_users.nextval INTO :new.id FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_roles
BEFORE INSERT ON roles FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT seq_roles.nextval INTO :new.id FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_devices
BEFORE INSERT ON devices FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT seq_devices.nextval INTO :new.id FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_userdevice
BEFORE INSERT ON userdevice FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT seq_userdevice.nextval INTO :new.id FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_groups
BEFORE INSERT ON groups FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT seq_groups.nextval INTO :new.id FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_devicegroups
BEFORE INSERT ON devicegroups FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT seq_devicegroups.nextval INTO :new.id FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_tasks
BEFORE INSERT ON tasks FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT seq_tasks.nextval INTO :new.id FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_taskdevice
BEFORE INSERT ON taskdevice FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT seq_taskdevice.nextval INTO :new.id FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_filedestination
BEFORE INSERT ON filedestination FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT seq_filedestination.nextval INTO :new.id FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_httpdestination
BEFORE INSERT ON httpdestination FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT seq_httpdestination.nextval INTO :new.id FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_httpcommandreply
BEFORE INSERT ON httpcommandreply FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT seq_httpcommandreply.nextval INTO :new.id FROM dual;
    END IF;
END;
/

CREATE INDEX idx_observation_deviceid ON observation(deviceID);
CREATE INDEX idx_observation_detectTime ON observation(detectTime);
CREATE INDEX idx_observation_removeTime ON observation(removeTime);

INSERT INTO users(userName,passwd,description) VALUES('admin', 'ubipass', 'built-in administrator');

INSERT INTO roles(roletype,userName) VALUES('Administrator','admin');

INSERT INTO plugins VALUES(1, 'Alien Class1 Reader', 'com.ubipass.middleware.plugin.alien.AlienClass1ReaderPlugin');
INSERT INTO plugins VALUES(2, 'Awid Reader', 'com.ubipass.middleware.plugin.awid.AwidReaderPlugin');
INSERT INTO plugins VALUES(3, 'Intermec Reader', 'com.ubipass.middleware.plugin.intermec.IntermecReaderPlugin');

INSERT INTO sysconfig VALUES(1, 'AutoID Middleware', 1000, 'A', 'A', NULL);

COMMIT;

