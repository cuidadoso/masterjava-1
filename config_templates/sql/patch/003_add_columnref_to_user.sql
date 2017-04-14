--liquibase formatted sql
--changeset alpere:003
--comment ADD COLUMN CITY_ID(FOREIGN KEY) CITY TABLE

ALTER TABLE users
  ADD COLUMN city_id INTEGER REFERENCES city (id);

--rollback ALTER TABLE users DROP COLUMN city_id;
