-- RECIPEBOOK_USER : Application users table.
CREATE TABLE RECIPEBOOK_USER 
(
  USER_ID NUMBER NOT NULL 
, USER_NAME VARCHAR2(50 CHAR) NOT NULL 
, PASSWORD VARCHAR2(150 CHAR) NOT NULL 
, FIRST_NAME VARCHAR2(50 CHAR) NOT NULL 
, LAST_NAME VARCHAR2(50 CHAR) NOT NULL 
, CONSTRAINT RECIPEBOOK_USER_PK PRIMARY KEY 
  (
    USER_ID 
  )
  ENABLE 
);

ALTER TABLE RECIPEBOOK_USER
ADD CONSTRAINT RECIPEBOOK_USER_UK1 UNIQUE 
(
  USER_NAME 
)
ENABLE;

CREATE TRIGGER RECIPEBOOK_USER_TRG 
BEFORE INSERT ON RECIPEBOOK_USER 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.USER_ID IS NULL THEN
      SELECT RECIPEBOOK_USER_SEQ.NEXTVAL INTO :NEW.USER_ID FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;

-- Initial user.
INSERT INTO recipebook_user
    (user_name, password, first_name, last_name)
VALUES
    ('mgoudie', 'TEST', 'Michael', 'Goudie')
;
COMMIT;

--RECIPEBOOK_MEASURE_UNIT : Application measure table.
CREATE TABLE RECIPEBOOK_MEASURE_UNIT 
(
  MEASURE_UNIT_ID NUMBER NOT NULL 
, MEASURE_UNIT VARCHAR2(35 CHAR) NOT NULL 
, MEASURE_TYPE VARCHAR2(25) DEFAULT '-'
, CONSTRAINT RECIPEBOOK_MEASURE_UNIT_PK PRIMARY KEY 
  (
    MEASURE_UNIT_ID 
  )
  ENABLE 
);

CREATE SEQUENCE RECIPEBOOK_MEASURE_UNIT_SEQ;

CREATE TRIGGER RECIPEBOOK_MEASURE_UNIT_TRG 
BEFORE INSERT ON RECIPEBOOK_MEASURE_UNIT 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.MEASURE_UNIT_ID IS NULL THEN
      SELECT RECIPEBOOK_MEASURE_UNIT_SEQ.NEXTVAL INTO :NEW.MEASURE_UNIT_ID FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;

-- Initial measures.
INSERT ALL 
    INTO recipebook_measure_unit (measure_unit, measure_type) VALUES ('teaspoon', 'recipe')
    INTO recipebook_measure_unit (measure_unit, measure_type) VALUES ('tablespoon', 'recipe')
    INTO recipebook_measure_unit (measure_unit, measure_type) VALUES ('fluid ounce', 'recipe')
    INTO recipebook_measure_unit (measure_unit, measure_type) VALUES ('gill', 'recipe')
    INTO recipebook_measure_unit (measure_unit, measure_type) VALUES ('cup', 'recipe')
    INTO recipebook_measure_unit (measure_unit, measure_type) VALUES ('pint', 'recipe')
    INTO recipebook_measure_unit (measure_unit, measure_type) VALUES ('quart', 'recipe')
    INTO recipebook_measure_unit (measure_unit, measure_type) VALUES ('gallon', 'recipe')
    INTO recipebook_measure_unit (measure_unit, measure_type) VALUES ('milliliter', 'recipe')
    INTO recipebook_measure_unit (measure_unit, measure_type) VALUES ('liter', 'recipe')
    INTO recipebook_measure_unit (measure_unit, measure_type) VALUES ('deciliter', 'recipe')
    INTO recipebook_measure_unit (measure_unit, measure_type) VALUES ('pound', 'recipe')
    INTO recipebook_measure_unit (measure_unit, measure_type) VALUES ('ounce', 'recipe')
    INTO recipebook_measure_unit (measure_unit, measure_type) VALUES ('milligram', 'recipe')
    INTO recipebook_measure_unit (measure_unit, measure_type) VALUES ('gram', 'recipe')
    INTO recipebook_measure_unit (measure_unit, measure_type) VALUES ('kilogram', 'recipe')
    INTO recipebook_measure_unit (measure_unit, measure_type) VALUES ('millimeter', 'recipe')
    INTO recipebook_measure_unit (measure_unit, measure_type) VALUES ('centimeter', 'recipe')
    INTO recipebook_measure_unit (measure_unit, measure_type) VALUES ('meter', 'recipe')
    INTO recipebook_measure_unit (measure_unit, measure_type) VALUES ('inch', 'recipe')
SELECT 1 FROM dual;
COMMIT;

--RECIPEBOOK_RECIPES : Application recipe table.
CREATE TABLE RECIPEBOOK_RECIPES 
(
  RECIPE_ID NUMBER NOT NULL 
, RECIPE_NAME VARCHAR2(75 CHAR) NOT NULL 
, RECIPE_DESCRIPTION VARCHAR2(255 CHAR) NOT NULL 
, SERVING_SIZE NUMBER NOT NULL 
, INSTRUCTIONS BLOB NOT NULL 
, CONSTRAINT RECIPEBOOK_RECIPES_PK PRIMARY KEY 
  (
    RECIPE_ID 
  )
  ENABLE 
);

CREATE SEQUENCE RECIPEBOOK_RECIPES_SEQ;

CREATE TRIGGER RECIPEBOOK_RECIPES_TRG 
BEFORE INSERT ON RECIPEBOOK_RECIPES 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.RECIPE_ID IS NULL THEN
      SELECT RECIPEBOOK_RECIPES_SEQ.NEXTVAL INTO :NEW.RECIPE_ID FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;

--RECIPEBOOK_INGREDIENTS : Application ingredients table.
CREATE TABLE RECIPEBOOK_INGREDIENTS 
(
  INGREDIENT_ID NUMBER NOT NULL 
, RECIPE_ID NUMBER NOT NULL 
, INGREDIENT_NAME VARCHAR2(75 CHAR) NOT NULL 
, PORTION_MEASURE NUMBER(9, 2) NOT NULL 
, MEASURE_UNIT_ID NUMBER NOT NULL 
, CONSTRAINT RECIPEBOOK_INGREDIENTS_PK PRIMARY KEY 
  (
    INGREDIENT_ID 
  )
  ENABLE 
);

CREATE INDEX RECIPEBOOK_INGREDIENTS_RID_IDX ON RECIPEBOOK_INGREDIENTS (RECIPE_ID ASC);

ALTER TABLE RECIPEBOOK_INGREDIENTS
ADD CONSTRAINT RECIPEBOOK_INGREDIENTS_FK1 FOREIGN KEY
(
  RECIPE_ID 
)
REFERENCES RECIPEBOOK_RECIPES
(
  RECIPE_ID 
)
ON DELETE CASCADE ENABLE;

ALTER TABLE RECIPEBOOK_INGREDIENTS
ADD CONSTRAINT RECIPEBOOK_INGREDIENTS_FK2 FOREIGN KEY
(
  MEASURE_UNIT_ID 
)
REFERENCES RECIPEBOOK_MEASURE_UNIT
(
  MEASURE_UNIT_ID 
)
ENABLE;

CREATE SEQUENCE RECIPEBOOK_INGREDIENTS_SEQ;

CREATE TRIGGER RECIPEBOOK_INGREDIENTS_TRG 
BEFORE INSERT ON RECIPEBOOK_INGREDIENTS 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.INGREDIENT_ID IS NULL THEN
      SELECT RECIPEBOOK_INGREDIENTS_SEQ.NEXTVAL INTO :NEW.INGREDIENT_ID FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;

ALTER TABLE RECIPEBOOK_RECIPES 
ADD (USER_ID NUMBER NOT NULL);

CREATE INDEX RECIPEBOOK_RECIPES_FK ON RECIPEBOOK_RECIPES (USER_ID ASC);

ALTER TABLE RECIPEBOOK_RECIPES
ADD CONSTRAINT RECIPEBOOK_RECIPES_UK1 UNIQUE 
(
  RECIPE_NAME 
, USER_ID 
)
ENABLE;

ALTER TABLE RECIPEBOOK_RECIPES
ADD CONSTRAINT RECIPEBOOK_RECIPES_FK1 FOREIGN KEY
(
  USER_ID 
)
REFERENCES RECIPEBOOK_USER
(
  USER_ID 
)
ON DELETE CASCADE ENABLE;

CREATE OR REPLACE TRIGGER RECIPEBOOK_RECIPES_TRG 
BEFORE INSERT ON RECIPEBOOK_RECIPES 
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    NULL;
  END COLUMN_SEQUENCES;
END;

ALTER TABLE RECIPEBOOK_RECIPES 
DROP CONSTRAINT RECIPEBOOK_RECIPES_UK1;

CREATE TABLE RECIPEBOOK_CONFIG 
(
  CONFIG_NAME VARCHAR2(50 CHAR) NOT NULL 
, CONFIG_VARIABLE VARCHAR2(250 CHAR) NOT NULL 
, CONSTRAINT RECIPEBOOK_CONFIG_PK PRIMARY KEY 
  (
    CONFIG_NAME 
  )
  ENABLE 
);

INSERT INTO recipebook_config
    (config_name, config_variable)
VALUES
    ('FILESHARE_PATH', 'C:\DevFileShare')
;
COMMIT;