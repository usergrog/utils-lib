CREATE TABLE AppLogs (
    localId INTEGER PRIMARY KEY ASC AUTOINCREMENT,
    id INTEGER,
    createdAt INTEGER,
    updatedAt INTEGER,
    tag VARCHAR,
    title VARCHAR,
    tabletId VARCHAR,
    serverKey VARCHAR,
    deviceName VARCHAR,
    deviceId VARCHAR,
    appVersionCode INTEGER,
    CONSTRAINT "U_AppLogs_ID" UNIQUE ("localId")
);


CREATE INDEX i_AppLogs_id
ON AppLogs (id asc);

