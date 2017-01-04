CREATE TABLE knapp_question_type (
ID  varchar(20) NOT NULL,
QuestionTypeDescription varchar(500) DEFAULT NULL,
Comments varchar(100) DEFAULT NULL,
CreatedBy bigint(20) unsigned NOT NULL,
CreatedAt datetime NOT NULL,
UpdatedBy bigint(20) unsigned NOT NULL,
UpdatedAt datetime NOT NULL,
PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;