DROP TABLE IF EXISTS Student;

CREATE TABLE Student
(
  ID            BIGINT NOT NULL,
  first_name    VARCHAR(255),
  last_name     VARCHAR(255),
  grade         VARCHAR(10),
  nationality   VARCHAR(255),
  status        VARCHAR(12)
);