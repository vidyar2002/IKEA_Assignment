DROP TABLE IF EXISTS inventory;

CREATE TABLE inventory (
 ID INT PRIMARY KEY,
 name varchar(250) NOT NULL,
 stock INT NOT NULL,
 active boolean NOT NULL default TRUE
);