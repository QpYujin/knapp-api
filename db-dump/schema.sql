-- Application user
CREATE DATABASE knapp;
CREATE USER 'knappuser'@'localhost' IDENTIFIED BY 'knappuser';
GRANT ALL ON knapp.* TO 'knappuser'@'localhost';

-- Application test user
CREATE DATABASE knapptest;
CREATE USER 'knapptestuser'@'localhost' IDENTIFIED BY 'knapptestuser';
GRANT ALL ON knapptest.* TO 'knapptestuser'@'localhost';

