# Question Type defining what type it is multiple choice, fillintheBlank etc.
# Mostly we will use multiple-choice but I added this so we can add any custom
# questions (questions —> how many answers and answer type mapping)
DROP TABLE knapp_question_type;

CREATE TABLE knapp_question_type (
  ID          INT         NOT NULL AUTO_INCREMENT,
  description VARCHAR(500)         DEFAULT NULL,
  comments    VARCHAR(100)         DEFAULT NULL,
  createdBy   VARCHAR(20) NOT NULL,
  createdAt   DATETIME    NOT NULL,
  updatedBy   VARCHAR(20),
  updatedAt   DATETIME,
  PRIMARY KEY (ID)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1
  ROW_FORMAT = COMPACT;


# Question has a elements  , Ex: MutlipleChoice is elementType , UIView :Radio Button
DROP TABLE knapp_elements;

CREATE TABLE knapp_elements (
  ID          INT         NOT NULL AUTO_INCREMENT,
  ElementType VARCHAR(500)         DEFAULT NULL,
  UIView      VARCHAR(50) NOT NULL,
  Comments    VARCHAR(100)         DEFAULT NULL,
  createdBy   VARCHAR(20) NOT NULL,
  createdAt   DATETIME    NOT NULL,
  updatedBy   VARCHAR(20),
  updatedAt   DATETIME,
  PRIMARY KEY (ID)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1
  ROW_FORMAT = COMPACT;