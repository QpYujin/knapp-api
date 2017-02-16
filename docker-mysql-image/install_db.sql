# Question Type defining what type it is multiple choice, fillintheBlank etc.
# Mostly we will use multiple-choice but I added this so we can add any custom
# questions (questions —> how many answers and answer type mapping)

# Drop all tables
DROP TABLE IF EXISTS knapp_element;

DROP TABLE IF EXISTS knapp_question_type;

# Create all tables

CREATE TABLE knapp_question_type (
  id          INT         NOT NULL AUTO_INCREMENT,
  name        VARCHAR(50) NOT NULL,
  description VARCHAR(500)         DEFAULT NULL,
  comments    VARCHAR(100)         DEFAULT NULL,
  createdBy   VARCHAR(20) NOT NULL,
  createdAt   DATETIME    NOT NULL,
  updatedBy   VARCHAR(20),
  updatedAt   DATETIME,
  PRIMARY KEY (ID),
  UNIQUE KEY (name)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1
  ROW_FORMAT = COMPACT;


# Question has a elements  , Ex: MutlipleChoice is elementType , UIView :Radio Button
CREATE TABLE knapp_element (
  id        INT         NOT NULL AUTO_INCREMENT,
  typeid    INT         NOT NULL,
  uiview    VARCHAR(50) NOT NULL,
  comments  VARCHAR(100)         DEFAULT NULL,
  createdBy VARCHAR(20) NOT NULL,
  createdAt DATETIME    NOT NULL,
  updatedBy VARCHAR(20),
  updatedAt DATETIME,
  PRIMARY KEY (ID),
  FOREIGN KEY (typeid) REFERENCES knapp_question_type (id)
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1
  ROW_FORMAT = COMPACT;


CREATE TABLE knapp_question (
  id          INT          NOT NULL AUTO_INCREMENT,
  text        VARCHAR(500) NOT NULL,
  description VARCHAR(500)          DEFAULT NULL,
  typeid      INT          NOT NULL,
  createdBy   VARCHAR(20)  NOT NULL,
  createdAt   DATETIME     NOT NULL,
  updatedBy   VARCHAR(20),
  updatedAt   DATETIME,
  PRIMARY KEY (id),
  FOREIGN KEY (typeid) REFERENCES knapp_question_type (id)
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1
  ROW_FORMAT = COMPACT;

