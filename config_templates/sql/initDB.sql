/* Users */
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS user_seq;
DROP TYPE IF EXISTS user_flag;

CREATE TYPE user_flag AS ENUM ('active', 'deleted', 'superuser');

CREATE SEQUENCE user_seq START 100000;

CREATE TABLE users (
  id        INTEGER PRIMARY KEY DEFAULT nextval('user_seq'),
  full_name TEXT NOT NULL,
  email     TEXT NOT NULL,
  flag      user_flag NOT NULL
);

CREATE UNIQUE INDEX email_idx ON users (email);

/* Projects */
DROP TABLE IF EXISTS projects;
DROP SEQUENCE IF EXISTS project_seq;

CREATE SEQUENCE project_seq START 200000;

CREATE TABLE projects (
  id        INTEGER PRIMARY KEY DEFAULT nextval('project_seq'),
  name      TEXT NOT NULL
);

CREATE UNIQUE INDEX project_idx ON projects (name);

/* City */
DROP TABLE IF EXISTS cities;
DROP SEQUENCE IF EXISTS city_seq;

CREATE SEQUENCE city_seq START 300000;

CREATE TABLE cities (
  id        INTEGER PRIMARY KEY DEFAULT nextval('city_seq'),
  id_name   TEXT NOT NULL,
  name      TEXT NOT NULL
);

CREATE UNIQUE INDEX id_name_idx ON cities (id_name);

/* Groups */
DROP TABLE IF EXISTS groups;
DROP SEQUENCE IF EXISTS group_seq;
DROP TYPE IF EXISTS group_type;

CREATE TYPE group_type AS ENUM ('REGISTERING', 'CURRENT', 'FINISHED');

CREATE SEQUENCE group_seq START 400000;

CREATE TABLE groups (
  id          INTEGER PRIMARY KEY DEFAULT nextval('group_seq'),
  name        TEXT NOT NULL,
  type        group_type NOT NULL,
  project_id  INTEGER
);

CREATE UNIQUE INDEX group_idx ON groups (name);