-- Application user
CREATE DATABASE knapp;
CREATE USER 'knappuser'@'192.168.99.100' IDENTIFIED BY 'knappuser';
GRANT ALL ON knapp.* TO 'knappuser'@'192.168.99.100';

-- Application test user
CREATE DATABASE knapptest;
CREATE USER 'knapptestuser'@'192.168.99.100' IDENTIFIED BY 'knapptestuser';
GRANT ALL ON knapptest.* TO 'knapptestuser'@'192.168.99.100';

