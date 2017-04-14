--liquibase formatted sql
--changeset alpere:006
--comment CREATE TABLE groups

CREATE TABLE groups (
id         INTEGER PRIMARY KEY DEFAULT nextval('common_seq'),
name       TEXT       NOT NULL UNIQUE,
type       group_type NOT NULL,
project_id INTEGER    NOT NULL REFERENCES project (id)
);

--rollback DROP TABLE groups;
