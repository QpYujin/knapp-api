CREATE DATABASE knapptest;

CREATE USER 'knapptestuser'@'localhost' IDENTIFIED BY 'knapptestuser';

GRANT ALL ON knapptest.* TO 'knapptestuser'@'localhost';