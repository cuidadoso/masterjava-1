--liquibase formatted sql
--changeset alpere:002
--comment ADD CITY TABLE

CREATE TABLE city (
  id   INTEGER PRIMARY KEY DEFAULT nextval('common_seq'),
  ref  TEXT UNIQUE,
  name TEXT NOT NULL
);

--rollback DROP TABLE city;
