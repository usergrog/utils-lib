ALTER TABLE AppLogs ADD COLUMN testColumn2 VARCHAR;
/

CREATE INDEX i_AppLogs_testColumn2
ON AppLogs (testColumn2 asc);
/