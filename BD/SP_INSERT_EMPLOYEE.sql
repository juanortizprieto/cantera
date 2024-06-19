CREATE OR REPLACE PROCEDURE INSERT_EMPLOYEE(in_name IN VARCHAR2,
in_last_name IN VARCHAR2,
in_birthdate IN VARCHAR2,
in_job_id IN NUMBER,
in_gender_id IN NUMBER,
out_id_employee OUT NUMBER)
AS
v_count_employee NUMBER;
v_employee_age NUMBER;
v_count_job NUMBER;
v_count_gender NUMBER;
v_employee_id NUMBER;
BEGIN
	SELECT COUNT(*) INTO v_count_employee 
	FROM EMPLOYEES
	WHERE UPPER(TRIM(NAME))=UPPER(TRIM(in_name))
	AND UPPER(TRIM(LAST_NAME))=UPPER(TRIM(in_last_name));
    
    SELECT months_between (TRUNC(SYSDATE),TRUNC(TO_DATE(in_birthdate,'YYYY-MM-DD')))/12 INTO v_employee_age FROM DUAL;

	SELECT COUNT(*) INTO v_count_gender
	FROM GENDER 
	WHERE ID = in_gender_id;

SELECT COUNT(*) INTO v_count_job
	FROM JOBS 
	WHERE ID = in_job_id;

	IF v_count_employee > 0 OR v_employee_age <18 OR v_count_job <= 0 OR  v_count_gender <=0 THEN 
	v_employee_id := 0;

	ELSE
	SELECT SEQ_EMPLOYEE.NEXTVAL INTO v_employee_id FROM DUAL; 
	
	INSERT INTO EMPLOYEES
	(ID, GENDER_ID, JOB_ID, NAME, LAST_NAME, BIRTHDATE)
	VALUES(v_employee_id, in_gender_id, in_job_id, in_name, in_last_name, TO_DATE(in_birthdate,'YYYY-MM-DD'));
	COMMIT;

END IF;
	
    out_id_employee:=v_employee_id;

END INSERT_EMPLOYEE;
