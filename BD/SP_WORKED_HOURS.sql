CREATE OR REPLACE PROCEDURE GET_WORKING_HOURS (in_employee_id IN VARCHAR2,
in_start_date IN VARCHAR2,
in_end_date IN VARCHAR2,
out_working_hours OUT NUMBER)
AS
v_worked_hours NUMBER;
BEGIN
v_worked_hours:=0;
FOR WH IN(SELECT WORKED_HOURS FROM EMPLOYEE_WORKED_HOURS WHERE EMPLOYEE_ID = in_employee_id AND WORKED_DATE >=TRUNC(TO_DATE(in_start_date,'YYYY-MM-DD')) AND WORKED_DATE <TRUNC(TO_DATE(in_end_date,'YYYY-MM-DD')+1))
LOOP
v_worked_hours:= v_worked_hours + WH.WORKED_HOURS;
END LOOP;
out_working_hours := v_worked_hours;
END GET_WORKING_HOURS;