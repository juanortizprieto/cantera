package org.test.dto;


import jakarta.json.bind.annotation.JsonbProperty;

public class EmployeeResponseDto {

    private Long id;
@JsonbProperty("total_worked_hours")
    private Double workingHours;
    @JsonbProperty("payment")
    private Double payment;
    private boolean success;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Double workingHours) {
        this.workingHours = workingHours;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
