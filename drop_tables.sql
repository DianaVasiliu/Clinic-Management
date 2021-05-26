DELIMITER //
CREATE PROCEDURE drop_tables()
BEGIN
    TRUNCATE TABLE consumable;
    TRUNCATE TABLE electronic;
    TRUNCATE TABLE nonconsumable;
    TRUNCATE TABLE equipment;    
	TRUNCATE TABLE appointment; 
    TRUNCATE TABLE diagnostic;
    TRUNCATE TABLE medicine;
    TRUNCATE TABLE treatment;
    TRUNCATE TABLE doctor;
    TRUNCATE TABLE employees;
    TRUNCATE TABLE patient;
    
    DROP TABLE consumable;
    DROP TABLE electronic;
    DROP TABLE nonconsumable;
    DROP TABLE equipment;    
	DROP TABLE appointment; 
    DROP TABLE diagnostic;
    DROP TABLE medicine;
    DROP TABLE treatment;
    DROP TABLE doctor;
    DROP TABLE employees;
    DROP TABLE patient;
END
//
DELIMITER ;
