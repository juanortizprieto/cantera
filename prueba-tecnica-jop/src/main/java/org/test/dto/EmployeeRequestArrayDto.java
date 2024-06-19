package org.test.dto;

import jakarta.json.bind.annotation.JsonbProperty;

import java.util.List;

public class EmployeeRequestArrayDto {
    @JsonbProperty("employee_id")
    private List<Long> employeeId;

    public List<Long> getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(List<Long> employeeId) {
        this.employeeId = employeeId;
    }
}
