--liquibase formatted sql
--changeset alpere:001
--comment ADD SEQUENCE common_seq

CREATE SEQUENCE common_seq START 100000;

--rollback DROP SEQUENCE common_seq;