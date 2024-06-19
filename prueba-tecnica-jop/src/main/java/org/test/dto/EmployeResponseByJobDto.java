package org.test.dto;

import jakarta.json.bind.annotation.JsonbProperty;

import org.test.entity.Employee;
import java.util.List;
import java.util.Map;

public class EmployeResponseByJobDto {



    private Map<String, List<Employee>> employees;
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, List<Employee>> getEmployees() {
        return employees;
    }

    public void setEmployees(Map<String, List<Employee>> employees) {
        this.employees = employees;
    }
}
