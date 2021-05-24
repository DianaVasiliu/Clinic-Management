DELIMITER //
CREATE PROCEDURE drop_tables()
BEGIN
    DROP TABLE appointment;
    DROP TABLE consumable;
    DROP TABLE diagnostic;
    DROP TABLE doctor;
    DROP TABLE electronic;
    DROP TABLE employees;
    DROP TABLE equipment;
    DROP TABLE medicine;
    DROP TABLE nonconsumable;
    DROP TABLE patient;
    DROP TABLE treatment;
END
//
DELIMITER ;
