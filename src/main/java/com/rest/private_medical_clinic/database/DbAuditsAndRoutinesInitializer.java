package com.rest.private_medical_clinic.database;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbAuditsAndRoutinesInitializer {

    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initialize() {

        createAudTables();
        createProcedures();
        createTriggers();
    }

    public void createAudTables() {

        String appointmentAud = """
                CREATE TABLE IF NOT EXISTS APPOINTMENTS_AUD (
                  EVENT_ID        INT        NOT NULL AUTO_INCREMENT,
                  EVENT_DATE      DATETIME   NOT NULL,
                  EVENT_TYPE      VARCHAR(10) NOT NULL,
                  APPOINTMENT_ID  BIGINT     NOT NULL,
                  OLD_DOCTOR_ID   BIGINT,
                  NEW_DOCTOR_ID   BIGINT,
                  OLD_PATIENT_ID  BIGINT,
                  NEW_PATIENT_ID  BIGINT,
                  OLD_DATE        DATE,
                  NEW_DATE        DATE,
                  OLD_TIME        TIME,
                  NEW_TIME        TIME,
                  OLD_STATUS      VARCHAR(20),
                  NEW_STATUS      VARCHAR(20),
                  OLD_NOTES       VARCHAR(255),
                  NEW_NOTES       VARCHAR(255),
                  PRIMARY KEY (EVENT_ID)
                );
                """;
        jdbcTemplate.update(appointmentAud);

        String userAud = """
                CREATE TABLE IF NOT EXISTS USERS_AUD (
                  EVENT_ID        INT        NOT NULL AUTO_INCREMENT,
                  EVENT_DATE      DATETIME   NOT NULL,
                  EVENT_TYPE      VARCHAR(10) NOT NULL,
                  USER_ID         BIGINT     NOT NULL,
                  OLD_USERNAME    VARCHAR(255),
                  NEW_USERNAME    VARCHAR(255),
                  OLD_MAIL        VARCHAR(255),
                  NEW_MAIL        VARCHAR(255),
                  OLD_PASSWORD    VARCHAR(255),
                  NEW_PASSWORD    VARCHAR(255),
                  OLD_ROLE        VARCHAR(10),
                  NEW_ROLE        VARCHAR(10),
                  OLD_CREATED_AT  DATETIME,
                  NEW_CREATED_AT  DATETIME,
                  OLD_BLOCKED     BOOLEAN,
                  NEW_BLOCKED     BOOLEAN,
                  PRIMARY KEY (EVENT_ID)
                );
                """;
        jdbcTemplate.update(userAud);

        String diagnosisAud = """
                CREATE TABLE IF NOT EXISTS DIAGNOSES_AUD (
                    EVENT_ID              BIGINT      NOT NULL AUTO_INCREMENT,
                    EVENT_DATE            DATETIME    NOT NULL,
                    EVENT_TYPE            VARCHAR(10) NOT NULL,
                    DIAGNOSIS_ID          BIGINT      NOT NULL,
                    OLD_APPOINTMENT_ID    BIGINT,
                    NEW_APPOINTMENT_ID    BIGINT,
                    OLD_DESCRIPTION       VARCHAR(1000),
                    NEW_DESCRIPTION       VARCHAR(1000),
                    OLD_RECOMMENDATION    VARCHAR(1000),
                    NEW_RECOMMENDATION    VARCHAR(1000),
                    OLD_CREATED_AT        DATETIME,
                    NEW_CREATED_AT        DATETIME,
                    OLD_DRUG_BRAND_NAME   VARCHAR(255),
                    NEW_DRUG_BRAND_NAME   VARCHAR(255),
                    OLD_DRUG_GENERIC_NAME VARCHAR(255),
                    NEW_DRUG_GENERIC_NAME VARCHAR(255),
                    OLD_DRUG_DOSAGE       TEXT,
                    NEW_DRUG_DOSAGE       TEXT,
                    PRIMARY KEY (EVENT_ID)
                );
                """;
        jdbcTemplate.update(diagnosisAud);

        String reviewAud = """
                CREATE TABLE IF NOT EXISTS REVIEWS_AUD (
                    EVENT_ID           BIGINT      NOT NULL AUTO_INCREMENT,
                    EVENT_DATE         DATETIME    NOT NULL,
                    EVENT_TYPE         VARCHAR(10) NOT NULL,
                    REVIEW_ID          BIGINT      NOT NULL,
                    OLD_APPOINTMENT_ID BIGINT,
                    NEW_APPOINTMENT_ID BIGINT,
                    OLD_DOCTOR_ID      BIGINT,
                    NEW_DOCTOR_ID      BIGINT,
                    OLD_PATIENT_ID     BIGINT,
                    NEW_PATIENT_ID     BIGINT,
                    OLD_RATING         INT,
                    NEW_RATING         INT,
                    OLD_COMMENT        VARCHAR(255),
                    NEW_COMMENT        VARCHAR(255),
                    OLD_CREATED_AT     DATETIME,
                    NEW_CREATED_AT     DATETIME,
                    PRIMARY KEY (EVENT_ID)
                );
                """;
        jdbcTemplate.update(reviewAud);

        String doctorAud = """
                CREATE TABLE IF NOT EXISTS DOCTORS_AUD (
                    EVENT_ID           BIGINT      NOT NULL AUTO_INCREMENT,
                    EVENT_DATE         DATETIME    NOT NULL,
                    EVENT_TYPE         VARCHAR(10) NOT NULL,
                    DOCTOR_ID          BIGINT      NOT NULL,
                    OLD_FIRSTNAME      VARCHAR(255),
                    NEW_FIRSTNAME      VARCHAR(255),
                    OLD_LASTNAME       VARCHAR(255),
                    NEW_LASTNAME       VARCHAR(255),
                    OLD_SPECIALIZATION VARCHAR(255),
                    NEW_SPECIALIZATION VARCHAR(255),
                    OLD_RATING         INT,
                    NEW_RATING         INT,
                    PRIMARY KEY (EVENT_ID)
                );
                """;
        jdbcTemplate.update(doctorAud);

        String patientAud = """
                CREATE TABLE IF NOT EXISTS PATIENTS_AUD (
                    EVENT_ID         BIGINT      NOT NULL AUTO_INCREMENT,
                    EVENT_DATE       DATETIME    NOT NULL,
                    EVENT_TYPE       VARCHAR(10) NOT NULL,
                    PATIENT_ID       BIGINT      NOT NULL,
                    OLD_FIRSTNAME    VARCHAR(255),
                    NEW_FIRSTNAME    VARCHAR(255),
                    OLD_LASTNAME     VARCHAR(255),
                    NEW_LASTNAME     VARCHAR(255),
                    OLD_PHONE_NUMBER INT,
                    NEW_PHONE_NUMBER INT,
                    OLD_PESEL        VARCHAR(11),
                    NEW_PESEL        VARCHAR(11),
                    OLD_BIRTH_DATE   DATE,
                    NEW_BIRTH_DATE   DATE,
                    PRIMARY KEY (EVENT_ID)
                );
                """;
        jdbcTemplate.update(patientAud);

        String doctorScheduleTemplateAud = """
                CREATE TABLE IF NOT EXISTS DOCTOR_SCHEDULE_TEMPLATE_AUD (
                    EVENT_ID        BIGINT      NOT NULL AUTO_INCREMENT,
                    EVENT_DATE      DATETIME    NOT NULL,
                    EVENT_TYPE      VARCHAR(10) NOT NULL,
                    TEMPLATE_ID     BIGINT      NOT NULL,
                    OLD_DOCTOR_ID   BIGINT,
                    NEW_DOCTOR_ID   BIGINT,
                    OLD_DAY_OF_WEEK VARCHAR(255),
                    NEW_DAY_OF_WEEK VARCHAR(255),
                    OLD_START_TIME  TIME,
                    NEW_START_TIME  TIME,
                    OLD_END_TIME    TIME,
                    NEW_END_TIME    TIME,
                    PRIMARY KEY (EVENT_ID)
                );
                """;
        jdbcTemplate.update(doctorScheduleTemplateAud);

        String doctorAvailabilityAud = """
                CREATE TABLE IF NOT EXISTS DOCTOR_AVAILABILITY_AUD (
                    EVENT_ID           BIGINT      NOT NULL AUTO_INCREMENT,
                    EVENT_DATE         DATETIME    NOT NULL,
                    EVENT_TYPE         VARCHAR(10) NOT NULL,
                    AVAILABILITY_ID    BIGINT      NOT NULL,
                    OLD_DOCTOR_ID      BIGINT,
                    NEW_DOCTOR_ID      BIGINT,
                    OLD_AVAILABLE_DATE DATE,
                    NEW_AVAILABLE_DATE DATE,
                    OLD_START_TIME     TIME,
                    NEW_START_TIME     TIME,
                    OLD_END_TIME       TIME,
                    NEW_END_TIME       TIME,
                    OLD_AVAILABLE      BOOLEAN,
                    NEW_AVAILABLE      BOOLEAN,
                    PRIMARY KEY (EVENT_ID)
                );
                """;
        jdbcTemplate.update(doctorAvailabilityAud);
    }

    public void createProcedures() {

        String updateDoctorsRating = """
                DROP PROCEDURE IF EXISTS updateDoctorsRating;
                CREATE PROCEDURE updateDoctorsRating()
                BEGIN
                    DECLARE FINISHED INT DEFAULT 0;
                    DECLARE DOC_ID INT;
                    DECLARE AVG_RATING DOUBLE;
                    DECLARE ALL_DOCTORS CURSOR FOR SELECT DISTINCT doctor_id FROM reviews;
                    DECLARE CONTINUE HANDLER FOR NOT FOUND SET FINISHED = 1;
                    OPEN ALL_DOCTORS;
                    WHILE (FINISHED = 0)
                        DO
                            FETCH ALL_DOCTORS INTO DOC_ID;
                            IF (FINISHED = 0) THEN
                                SELECT AVG(r.rating)
                                INTO AVG_RATING
                                FROM reviews r
                                WHERE r.doctor_id = DOC_ID;
                
                                UPDATE doctors d
                                SET d.rating = AVG_RATING
                                WHERE d.id = DOC_ID;
                            END IF;
                        END WHILE;
                    CLOSE ALL_DOCTORS;
                END;
                """;
        jdbcTemplate.execute(updateDoctorsRating);
    }

    @PostConstruct
    public void createTriggers() {

        //If you have problems adding the trigger, do this with root privileges:
        //set global log_bin_trust_function_creators=1;
        //Or
        //Open the my.cnf (Linux) or my.ini (Windows) file, usually in the /etc/mysql/ directory or in the MySQL installation directory.
        //In the [mysqld] section add the line:
        //[mysqld]
        //log_bin_trust_function_creators = 1
        //After that, restart the MySQL server.

        String averageRatingUpdate = """
                DROP TRIGGER IF EXISTS averageRatingUpdate;
                CREATE TRIGGER averageRatingUpdate AFTER INSERT ON reviews
                FOR EACH ROW
                    BEGIN
                        CALL updateDoctorsRating();
                    END ;
                """;
        jdbcTemplate.execute(averageRatingUpdate);

        String appointmentsAud = """
                DROP TRIGGER IF EXISTS appointmentsInsert;
                CREATE TRIGGER appointmentsInsert
                  AFTER INSERT ON APPOINTMENTS
                  FOR EACH ROW
                BEGIN
                  INSERT INTO APPOINTMENTS_AUD (
                    EVENT_DATE, EVENT_TYPE, APPOINTMENT_ID,
                    NEW_DOCTOR_ID, NEW_PATIENT_ID, NEW_DATE, NEW_TIME, NEW_STATUS, NEW_NOTES
                  ) VALUES (
                    NOW(), 'INSERT', NEW.APPOINTMENT_ID,
                    NEW.DOCTOR_ID, NEW.PATIENT_ID, NEW.APPOINTMENT_DATE, NEW.APPOINTMENT_TIME,
                    NEW.STATUS, NEW.NOTES
                  );
                END;
                
                DROP TRIGGER IF EXISTS appointmentsUpdate;
                CREATE TRIGGER appointmentsUpdate
                  AFTER UPDATE ON APPOINTMENTS
                  FOR EACH ROW
                BEGIN
                  INSERT INTO APPOINTMENTS_AUD (
                    EVENT_DATE, EVENT_TYPE, APPOINTMENT_ID,
                    OLD_DOCTOR_ID, NEW_DOCTOR_ID, OLD_PATIENT_ID, NEW_PATIENT_ID, OLD_DATE, NEW_DATE,
                    OLD_TIME, NEW_TIME, OLD_STATUS, NEW_STATUS, OLD_NOTES, NEW_NOTES
                  ) VALUES (
                    NOW(), 'UPDATE', OLD.APPOINTMENT_ID,
                    OLD.DOCTOR_ID, NEW.DOCTOR_ID, OLD.PATIENT_ID, NEW.PATIENT_ID,
                    OLD.APPOINTMENT_DATE, NEW.APPOINTMENT_DATE, OLD.APPOINTMENT_TIME, NEW.APPOINTMENT_TIME,
                    OLD.STATUS, NEW.STATUS, OLD.NOTES, NEW.NOTES
                  );
                END;
                
                DROP TRIGGER IF EXISTS appointmentsDelete;
                CREATE TRIGGER appointmentsDelete
                  AFTER DELETE ON APPOINTMENTS
                  FOR EACH ROW
                BEGIN
                  INSERT INTO APPOINTMENTS_AUD (
                    EVENT_DATE, EVENT_TYPE, APPOINTMENT_ID,
                    OLD_DOCTOR_ID, OLD_PATIENT_ID,
                    OLD_DATE, OLD_TIME, OLD_STATUS, OLD_NOTES
                  ) VALUES (
                    NOW(), 'DELETE', OLD.APPOINTMENT_ID,
                    OLD.DOCTOR_ID, OLD.PATIENT_ID, OLD.APPOINTMENT_DATE, OLD.APPOINTMENT_TIME,
                    OLD.STATUS, OLD.NOTES
                  );
                END;
                """;
        jdbcTemplate.execute(appointmentsAud);

        String diagnosisAud = """
                DROP TRIGGER IF EXISTS diagnosisInsert;
                CREATE TRIGGER diagnosisInsert
                  AFTER INSERT ON DIAGNOSES
                  FOR EACH ROW
                BEGIN
                  INSERT INTO DIAGNOSES_AUD (
                    EVENT_DATE, EVENT_TYPE, DIAGNOSIS_ID,
                    NEW_APPOINTMENT_ID, NEW_DESCRIPTION, NEW_RECOMMENDATION,
                    NEW_CREATED_AT, NEW_DRUG_BRAND_NAME, NEW_DRUG_GENERIC_NAME, NEW_DRUG_DOSAGE
                  ) VALUES (
                    NOW(), 'INSERT', NEW.DIAGNOSIS_ID,
                    NEW.APPOINTMENT_ID, NEW.DESCRIPTION, NEW.RECOMMENDATION,
                    NEW.CREATED_AT, NEW.DRUG_BRAND_NAME, NEW.DRUG_GENERIC_NAME, NEW.DRUG_DOSAGE
                  );
                END;
                
                DROP TRIGGER IF EXISTS diagnosisUpdate;
                CREATE TRIGGER diagnosisUpdate
                  AFTER UPDATE ON DIAGNOSES
                  FOR EACH ROW
                BEGIN
                  INSERT INTO DIAGNOSES_AUD (
                    EVENT_DATE, EVENT_TYPE, DIAGNOSIS_ID,
                    OLD_APPOINTMENT_ID, NEW_APPOINTMENT_ID, OLD_DESCRIPTION, NEW_DESCRIPTION,
                    OLD_RECOMMENDATION, NEW_RECOMMENDATION, OLD_CREATED_AT, NEW_CREATED_AT,
                    OLD_DRUG_BRAND_NAME, NEW_DRUG_BRAND_NAME, OLD_DRUG_GENERIC_NAME, NEW_DRUG_GENERIC_NAME,
                    OLD_DRUG_DOSAGE, NEW_DRUG_DOSAGE
                  ) VALUES (
                    NOW(), 'UPDATE', OLD.DIAGNOSIS_ID,
                    OLD.APPOINTMENT_ID, NEW.APPOINTMENT_ID, OLD.DESCRIPTION, NEW.DESCRIPTION,
                    OLD.RECOMMENDATION, NEW.RECOMMENDATION, OLD.CREATED_AT, NEW.CREATED_AT,
                    OLD.DRUG_BRAND_NAME, NEW.DRUG_BRAND_NAME, OLD.DRUG_GENERIC_NAME, NEW.DRUG_GENERIC_NAME,
                    OLD.DRUG_DOSAGE, NEW.DRUG_DOSAGE
                  );
                END;
                
                DROP TRIGGER IF EXISTS diagnosisDelete;
                CREATE TRIGGER diagnosisDelete
                  AFTER DELETE ON DIAGNOSES
                  FOR EACH ROW
                BEGIN
                  INSERT INTO DIAGNOSES_AUD (
                    EVENT_DATE, EVENT_TYPE, DIAGNOSIS_ID,
                    OLD_APPOINTMENT_ID, OLD_DESCRIPTION, OLD_RECOMMENDATION,
                    OLD_CREATED_AT, OLD_DRUG_BRAND_NAME, OLD_DRUG_GENERIC_NAME, OLD_DRUG_DOSAGE
                  ) VALUES (
                    NOW(), 'DELETE', OLD.DIAGNOSIS_ID,
                    OLD.APPOINTMENT_ID, OLD.DESCRIPTION, OLD.RECOMMENDATION,
                    OLD.CREATED_AT, OLD.DRUG_BRAND_NAME, OLD.DRUG_GENERIC_NAME, OLD.DRUG_DOSAGE
                  );
                END;
                """;
        jdbcTemplate.execute(diagnosisAud);

        String reviewsAud = """
                DROP TRIGGER IF EXISTS reviewsInsert;
                CREATE TRIGGER reviewsInsert
                  AFTER INSERT ON REVIEWS
                  FOR EACH ROW
                BEGIN
                  INSERT INTO REVIEWS_AUD (
                    EVENT_DATE, EVENT_TYPE, REVIEW_ID,
                    NEW_APPOINTMENT_ID, NEW_DOCTOR_ID, NEW_PATIENT_ID, NEW_RATING, NEW_COMMENT, NEW_CREATED_AT
                  ) VALUES (
                    NOW(), 'INSERT', NEW.REVIEW_ID,
                    NEW.APPOINTMENT_ID, NEW.DOCTOR_ID, NEW.PATIENT_ID, NEW.RATING, NEW.COMMENT, NEW.CREATED_AT
                  );
                END;
                
                DROP TRIGGER IF EXISTS reviewsUpdate;
                CREATE TRIGGER reviewsUpdate
                  AFTER UPDATE ON REVIEWS
                  FOR EACH ROW
                BEGIN
                  INSERT INTO REVIEWS_AUD (
                    EVENT_DATE, EVENT_TYPE, REVIEW_ID,
                    OLD_APPOINTMENT_ID, NEW_APPOINTMENT_ID, OLD_DOCTOR_ID, NEW_DOCTOR_ID,
                    OLD_PATIENT_ID, NEW_PATIENT_ID, OLD_RATING, NEW_RATING, OLD_COMMENT, NEW_COMMENT,
                    OLD_CREATED_AT, NEW_CREATED_AT
                  ) VALUES (
                    NOW(), 'UPDATE', OLD.REVIEW_ID,
                    OLD.APPOINTMENT_ID, NEW.APPOINTMENT_ID, OLD.DOCTOR_ID, NEW.DOCTOR_ID,
                    OLD.PATIENT_ID, NEW.PATIENT_ID, OLD.RATING, NEW.RATING, OLD.COMMENT, NEW.COMMENT,
                    OLD.CREATED_AT, NEW.CREATED_AT
                  );
                END;
                
                DROP TRIGGER IF EXISTS reviewsDelete;
                CREATE TRIGGER reviewsDelete
                  AFTER DELETE ON REVIEWS
                  FOR EACH ROW
                BEGIN
                  INSERT INTO REVIEWS_AUD (
                    EVENT_DATE, EVENT_TYPE, REVIEW_ID,
                    OLD_APPOINTMENT_ID, OLD_DOCTOR_ID, OLD_PATIENT_ID, OLD_RATING, OLD_COMMENT, OLD_CREATED_AT
                  ) VALUES (
                    NOW(), 'DELETE', OLD.REVIEW_ID,
                    OLD.APPOINTMENT_ID, OLD.DOCTOR_ID, OLD.PATIENT_ID, OLD.RATING, OLD.COMMENT, OLD.CREATED_AT
                  );
                END;
                """;
        jdbcTemplate.execute(reviewsAud);

        String usersAud = """
                DROP TRIGGER IF EXISTS usersInsert;
                CREATE TRIGGER usersInsert
                  AFTER INSERT ON USERS
                  FOR EACH ROW
                BEGIN
                  INSERT INTO USERS_AUD (
                    EVENT_DATE, EVENT_TYPE, USER_ID,
                    NEW_USERNAME, NEW_MAIL, NEW_PASSWORD, NEW_ROLE, NEW_CREATED_AT, NEW_BLOCKED
                  ) VALUES (
                    NOW(), 'INSERT', NEW.ID,
                    NEW.USERNAME, NEW.MAIL, NEW.PASSWORD, NEW.ROLE, NEW.CREATED_AT, NEW.BLOCKED
                  );
                END;
                
                DROP TRIGGER IF EXISTS usersUpdate;
                CREATE TRIGGER usersUpdate
                  AFTER UPDATE ON USERS
                  FOR EACH ROW
                BEGIN
                  INSERT INTO USERS_AUD (
                    EVENT_DATE, EVENT_TYPE, USER_ID,
                    OLD_USERNAME, NEW_USERNAME, OLD_MAIL, NEW_MAIL, OLD_PASSWORD, NEW_PASSWORD,
                    OLD_ROLE, NEW_ROLE, OLD_CREATED_AT, NEW_CREATED_AT, OLD_BLOCKED, NEW_BLOCKED
                  ) VALUES (
                    NOW(), 'UPDATE', OLD.ID,
                    OLD.USERNAME, NEW.USERNAME, OLD.MAIL, NEW.MAIL, OLD.PASSWORD, NEW.PASSWORD,
                    OLD.ROLE, NEW.ROLE, OLD.CREATED_AT, NEW.CREATED_AT, OLD.BLOCKED, NEW.BLOCKED
                  );
                END;
                
                DROP TRIGGER IF EXISTS usersDelete;
                CREATE TRIGGER usersDelete
                  AFTER DELETE ON USERS
                  FOR EACH ROW
                BEGIN
                  INSERT INTO USERS_AUD (
                    EVENT_DATE, EVENT_TYPE, USER_ID,
                    OLD_USERNAME, OLD_MAIL, OLD_PASSWORD, OLD_ROLE, OLD_CREATED_AT, OLD_BLOCKED
                  ) VALUES (
                    NOW(), 'DELETE', OLD.ID,
                    OLD.USERNAME, OLD.MAIL, OLD.PASSWORD, OLD.ROLE, OLD.CREATED_AT, OLD.BLOCKED
                  );
                END;
                """;
        jdbcTemplate.execute(usersAud);

        String doctorsAud = """
                DROP TRIGGER IF EXISTS doctorsInsert;
                CREATE TRIGGER doctorsInsert
                  AFTER INSERT ON DOCTORS
                  FOR EACH ROW
                BEGIN
                  INSERT INTO DOCTORS_AUD (
                    EVENT_DATE, EVENT_TYPE, DOCTOR_ID,
                    NEW_FIRSTNAME, NEW_LASTNAME, NEW_SPECIALIZATION, NEW_RATING
                  ) VALUES (
                    NOW(), 'INSERT', NEW.ID,
                    NEW.FIRSTNAME, NEW.LASTNAME, NEW.SPECIALIZATION, NEW.RATING
                  );
                END;
                
                DROP TRIGGER IF EXISTS doctorsUpdate;
                CREATE TRIGGER doctorsUpdate
                  AFTER UPDATE ON DOCTORS
                  FOR EACH ROW
                BEGIN
                  INSERT INTO DOCTORS_AUD (
                    EVENT_DATE, EVENT_TYPE, DOCTOR_ID,
                    OLD_FIRSTNAME, NEW_FIRSTNAME, OLD_LASTNAME, NEW_LASTNAME,
                    OLD_SPECIALIZATION, NEW_SPECIALIZATION, OLD_RATING, NEW_RATING
                  ) VALUES (
                    NOW(), 'UPDATE', OLD.ID,
                    OLD.FIRSTNAME, NEW.FIRSTNAME, OLD.LASTNAME, NEW.LASTNAME,
                    OLD.SPECIALIZATION, NEW.SPECIALIZATION, OLD.RATING, NEW.RATING
                  );
                END;
                
                DROP TRIGGER IF EXISTS doctorsDelete;
                CREATE TRIGGER doctorsDelete
                  AFTER DELETE ON DOCTORS
                  FOR EACH ROW
                BEGIN
                  INSERT INTO DOCTORS_AUD (
                    EVENT_DATE, EVENT_TYPE, DOCTOR_ID,
                    OLD_FIRSTNAME, OLD_LASTNAME, OLD_SPECIALIZATION, OLD_RATING
                  ) VALUES (
                    NOW(), 'DELETE', OLD.ID,
                    OLD.FIRSTNAME, OLD.LASTNAME, OLD.SPECIALIZATION, OLD.RATING
                  );
                END;
                """;
        jdbcTemplate.execute(doctorsAud);

        String patientsAud = """
                DROP TRIGGER IF EXISTS patientsInsert;
                CREATE TRIGGER patientsInsert
                  AFTER INSERT ON PATIENTS
                  FOR EACH ROW
                BEGIN
                  INSERT INTO PATIENTS_AUD (
                    EVENT_DATE, EVENT_TYPE, PATIENT_ID,
                    NEW_FIRSTNAME, NEW_LASTNAME, NEW_PHONE_NUMBER, NEW_PESEL, NEW_BIRTH_DATE
                  ) VALUES (
                    NOW(), 'INSERT', NEW.ID,
                    NEW.FIRSTNAME, NEW.LASTNAME, NEW.PHONE_NUMBER, NEW.PESEL, NEW.BIRTH_DATE
                  );
                END;
                
                DROP TRIGGER IF EXISTS patientsUpdate;
                CREATE TRIGGER patientsUpdate
                  AFTER UPDATE ON PATIENTS
                  FOR EACH ROW
                BEGIN
                  INSERT INTO PATIENTS_AUD (
                    EVENT_DATE, EVENT_TYPE, PATIENT_ID,
                    OLD_FIRSTNAME, NEW_FIRSTNAME, OLD_LASTNAME, NEW_LASTNAME, OLD_PHONE_NUMBER, NEW_PHONE_NUMBER,
                    OLD_PESEL, NEW_PESEL, OLD_BIRTH_DATE, NEW_BIRTH_DATE
                  ) VALUES (
                    NOW(), 'UPDATE', OLD.ID,
                    OLD.FIRSTNAME, NEW.FIRSTNAME, OLD.LASTNAME, NEW.LASTNAME, OLD.PHONE_NUMBER, NEW.PHONE_NUMBER,
                    OLD.PESEL, NEW.PESEL, OLD.BIRTH_DATE, NEW.BIRTH_DATE
                  );
                END;
                
                DROP TRIGGER IF EXISTS patientsDelete;
                CREATE TRIGGER patientsDelete
                  AFTER DELETE ON PATIENTS
                  FOR EACH ROW
                BEGIN
                  INSERT INTO PATIENTS_AUD (
                    EVENT_DATE, EVENT_TYPE, PATIENT_ID,
                    OLD_FIRSTNAME, OLD_LASTNAME, OLD_PHONE_NUMBER, OLD_PESEL, OLD_BIRTH_DATE
                  ) VALUES (
                    NOW(), 'DELETE', OLD.ID,
                    OLD.FIRSTNAME, OLD.LASTNAME, OLD.PHONE_NUMBER, OLD.PESEL, OLD.BIRTH_DATE
                  );
                END;
                """;
        jdbcTemplate.execute(patientsAud);

        String doctorScheduleTemplatesAud = """
                DROP TRIGGER IF EXISTS scheduleTemplatesInsert;
                CREATE TRIGGER scheduleTemplatesInsert
                  AFTER INSERT ON DOCTOR_SCHEDULE_TEMPLATE
                  FOR EACH ROW
                BEGIN
                  INSERT INTO DOCTOR_SCHEDULE_TEMPLATE_AUD (
                    EVENT_DATE, EVENT_TYPE, TEMPLATE_ID,
                    NEW_DOCTOR_ID, NEW_DAY_OF_WEEK, NEW_START_TIME, NEW_END_TIME
                  ) VALUES (
                    NOW(), 'INSERT', NEW.ID,
                    NEW.DOCTOR_ID, NEW.DAY_OF_WEEK, NEW.START_TIME, NEW.END_TIME
                  );
                END;
                
                DROP TRIGGER IF EXISTS scheduleTemplatesUpdate;
                CREATE TRIGGER scheduleTemplatesUpdate
                  AFTER UPDATE ON DOCTOR_SCHEDULE_TEMPLATE
                  FOR EACH ROW
                BEGIN
                  INSERT INTO DOCTOR_SCHEDULE_TEMPLATE_AUD (
                    EVENT_DATE, EVENT_TYPE, TEMPLATE_ID,
                    OLD_DOCTOR_ID, NEW_DOCTOR_ID, OLD_DAY_OF_WEEK, NEW_DAY_OF_WEEK,
                    OLD_START_TIME, NEW_START_TIME, OLD_END_TIME, NEW_END_TIME
                  ) VALUES (
                    NOW(), 'UPDATE', OLD.ID,
                    OLD.DOCTOR_ID, NEW.DOCTOR_ID, OLD.DAY_OF_WEEK, NEW.DAY_OF_WEEK,
                    OLD.START_TIME, NEW.START_TIME, OLD.END_TIME, NEW.END_TIME
                  );
                END;
                
                DROP TRIGGER IF EXISTS scheduleTemplatesDelete;
                CREATE TRIGGER scheduleTemplatesDelete
                  AFTER DELETE ON DOCTOR_SCHEDULE_TEMPLATE
                  FOR EACH ROW
                BEGIN
                  INSERT INTO DOCTOR_SCHEDULE_TEMPLATE_AUD (
                    EVENT_DATE, EVENT_TYPE, TEMPLATE_ID,
                    OLD_DOCTOR_ID, OLD_DAY_OF_WEEK, OLD_START_TIME, OLD_END_TIME
                  ) VALUES (
                    NOW(), 'DELETE', OLD.ID,
                    OLD.DOCTOR_ID, OLD.DAY_OF_WEEK, OLD.START_TIME, OLD.END_TIME
                  );
                END;
                """;
        jdbcTemplate.execute(doctorScheduleTemplatesAud);

        String doctorAvailabilityAud = """
                DROP TRIGGER IF EXISTS availabilityInsert;
                CREATE TRIGGER availabilityInsert
                  AFTER INSERT ON DOCTOR_AVAILABILITY
                  FOR EACH ROW
                BEGIN
                  INSERT INTO DOCTOR_AVAILABILITY_AUD (
                    EVENT_DATE, EVENT_TYPE, AVAILABILITY_ID,
                    NEW_DOCTOR_ID, NEW_AVAILABLE_DATE, NEW_START_TIME, NEW_END_TIME, NEW_AVAILABLE
                  ) VALUES (
                    NOW(), 'INSERT', NEW.ID,
                    NEW.DOCTOR_ID, NEW.AVAILABLE_DATE, NEW.START_TIME, NEW.END_TIME, NEW.AVAILABLE
                  );
                END;
                
                DROP TRIGGER IF EXISTS availabilityUpdate;
                CREATE TRIGGER availabilityUpdate
                  AFTER UPDATE ON DOCTOR_AVAILABILITY
                  FOR EACH ROW
                BEGIN
                  INSERT INTO DOCTOR_AVAILABILITY_AUD (
                    EVENT_DATE, EVENT_TYPE, AVAILABILITY_ID,
                    OLD_DOCTOR_ID, NEW_DOCTOR_ID, OLD_AVAILABLE_DATE, NEW_AVAILABLE_DATE,
                    OLD_START_TIME, NEW_START_TIME, OLD_END_TIME, NEW_END_TIME, OLD_AVAILABLE, NEW_AVAILABLE
                  ) VALUES (
                    NOW(), 'UPDATE', NEW.ID,
                    OLD.DOCTOR_ID, NEW.DOCTOR_ID, OLD.AVAILABLE_DATE, NEW.AVAILABLE_DATE,
                    OLD.START_TIME, NEW.START_TIME, OLD.END_TIME, NEW.END_TIME, OLD.AVAILABLE, NEW.AVAILABLE
                  );
                END;
                
                DROP TRIGGER IF EXISTS availabilityDelete;
                CREATE TRIGGER availabilityDelete
                  AFTER DELETE ON DOCTOR_AVAILABILITY
                  FOR EACH ROW
                BEGIN
                  INSERT INTO DOCTOR_AVAILABILITY_AUD (
                    EVENT_DATE, EVENT_TYPE, AVAILABILITY_ID,
                    OLD_DOCTOR_ID, OLD_AVAILABLE_DATE, OLD_START_TIME, OLD_END_TIME, OLD_AVAILABLE
                  ) VALUES (
                    NOW(), 'DELETE', OLD.ID,
                    OLD.DOCTOR_ID, OLD.AVAILABLE_DATE, OLD.START_TIME, OLD.END_TIME, OLD.AVAILABLE
                  );
                END;
                """;
        jdbcTemplate.execute(doctorAvailabilityAud);
    }
}
