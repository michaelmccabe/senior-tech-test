DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts (
  username VARCHAR(250) PRIMARY KEY,
  password VARCHAR(250) NOT NULL,
  details VARCHAR(250) DEFAULT NULL,
  type VARCHAR(250) NOT NULL
);

INSERT INTO accounts (username, password, type) VALUES
  ('admin', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 'ADMIN');