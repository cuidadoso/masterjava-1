--liquibase formatted sql
--changeset alpere:004
--comment CREATE TABLE project

CREATE TABLE project (
  id          INTEGER PRIMARY KEY DEFAULT nextval('common_seq'),
  name        TEXT NOT NULL UNIQUE,
  description TEXT
);

--rollback DROP TABLE project;

