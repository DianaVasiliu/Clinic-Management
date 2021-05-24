DELIMITER //
CREATE PROCEDURE create_tables()
BEGIN
CREATE TABLE IF NOT EXISTS employees (
	id 				INTEGER PRIMARY KEY,
    first_name 		VARCHAR(40),
    last_name 		VARCHAR(40),
    birthday 		DATE,
    sex 			CHAR(1),
    age 			INTEGER,
    salary 			DOUBLE,
    experience 		INTEGER,
    days_worked 	INTEGER,
    salary_per_hour DOUBLE,
    hours_per_day 	INTEGER,
    emp_type 		VARCHAR(20),
    CONSTRAINT emp_sex_check
		CHECK (sex IN ('f', 'F', 'm', 'M', null)),
	CONSTRAINT emp_age_check
		CHECK (age >= 18),
	CONSTRAINT emp_salary_check
		CHECK (salary >= 0),
	CONSTRAINT emp_experience_check
		CHECK (experience >= 0),
	CONSTRAINT emp_daysWorked_check
		CHECK (days_worked >= 0),
	CONSTRAINT emp_salaryPerHour_check
		CHECK (salary_per_hour > 0),
	CONSTRAINT emp_hoursPerDay_check
		CHECK (hours_per_day > 0),
	CONSTRAINT emp_empType_check
		CHECK (emp_type IN ('doctor', 'nurse', 'receptionist', 'administrator'))
);

CREATE TABLE IF NOT EXISTS doctor (
	id 		INTEGER PRIMARY KEY,
    specialization 	VARCHAR(50),
    CONSTRAINT doctor_emp_fk
		FOREIGN KEY (id) 
        REFERENCES employees(id)
);

CREATE TABLE IF NOT EXISTS equipment (
	id 					INTEGER PRIMARY KEY,
    name 				VARCHAR(40),
    price 				DOUBLE,
    buy_date 			DATE,
    eq_type 			VARCHAR(20),      
    CONSTRAINT equipment_price_check
		CHECK (price >= 0),
	CONSTRAINT equipment_eqType_check
		CHECK (eq_type IN ('consumable', 'nonconsumable', 'electronic'))
);

CREATE TABLE IF NOT EXISTS consumable (
	id					INTEGER PRIMARY KEY,
    items_in_package	INTEGER,
    avg_last_time		DOUBLE,
	CONSTRAINT consumable_itemsInPackage_check
		CHECK (items_in_package > 0),
	CONSTRAINT consumable_avgLastTime_check
		CHECK (avg_last_time > 0)        
);

CREATE TABLE IF NOT EXISTS nonconsumable (
	id					INTEGER PRIMARY KEY,
    days_after_change	DOUBLE,
	CONSTRAINT nonconsumable_daysAfterChange_check
		CHECK (days_after_change >= 0)
);

CREATE TABLE IF NOT EXISTS electronic (
	id					INTEGER PRIMARY KEY,	
	watts_per_hour		DOUBLE,
    hours_used			DOUBLE,
	CONSTRAINT electronic_watts_check
		CHECK (watts_per_hour >= 0),
	CONSTRAINT electronic_hoursUsed_check
		CHECK (hours_used >= 0)
);

CREATE TABLE IF NOT EXISTS patient (
	id		INTEGER PRIMARY KEY,
    first_name 		VARCHAR(40),
    last_name 		VARCHAR(40),
    SSN				VARCHAR(20),
    age				INTEGER,
    sex				CHAR(1),
    height			INTEGER,
    weight			DOUBLE,
    debt			DOUBLE DEFAULT 0,
    CONSTRAINT patient_ssn_check
		CHECK (length(ssn) = 11),
	CONSTRAINT patient_age_check
		CHECK (age >= 0),
	CONSTRAINT patient_sex_check
		CHECK (sex IN ('f', 'F', 'm', 'M', null)),
	CONSTRAINT patient_height_check
		CHECK (height > 0 AND height < 250),
	CONSTRAINT patient_weight_check
		CHECK (weight > 0 AND weight < 400),
	CONSTRAINT patient_debt_check
		CHECK (debt >= 0)
);

CREATE TABLE IF NOT EXISTS appointment (
	id			INTEGER PRIMARY KEY,
    date_time	DATETIME,
    doctor_id	INTEGER NOT NULL,
    patient_id	INTEGER NOT NULL,
    CONSTRAINT appointment_doctorid_fk
		FOREIGN KEY (doctor_id)
        REFERENCES doctor(id),
    CONSTRAINT appointment_patientid_fk
		FOREIGN KEY (patient_id)
        REFERENCES patient(id)
);

CREATE TABLE IF NOT EXISTS diagnostic (
	id				INTEGER PRIMARY KEY,
    description		VARCHAR(200),
    discover_date	DATE
);

CREATE TABLE IF NOT EXISTS medicine (
	id					INTEGER PRIMARY KEY,
    name				VARCHAR(40),
    producer			VARCHAR(50),
    price				DOUBLE,
    active_substance 	VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS treatment (
	patient_id		INTEGER,
    diagnostic_id	INTEGER,
    medicine_id		INTEGER,
    med_quantity	DOUBLE,
    PRIMARY KEY (patient_id, diagnostic_id, medicine_id)
);
END
//
DELIMITER ;
