--liquibase formatted sql
--changeset alpere:005
--comment CREATE TYPE group_type

CREATE TYPE group_type AS ENUM ('REGISTERING', 'CURRENT', 'FINISHED');

--rollback DROP TYPE group_type;