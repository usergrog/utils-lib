ALTER TABLE AppLogs ADD COLUMN testColumn VARCHAR;
/

CREATE INDEX i_AppLogs_testColumn
ON AppLogs (testColumn asc);
/