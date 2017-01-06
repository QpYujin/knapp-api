DROP TABLE knapp_question_type ;

CREATE TABLE knapp_question_type (
  ID INT NOT NULL AUTO_INCREMENT,
  description varchar(500) DEFAULT NULL,
  comments varchar(100) DEFAULT NULL,
  createdBy varchar(20) NOT NULL,
  createdAt datetime NOT NULL,
  updatedBy varchar(20),
  updatedAt datetime,
  PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;