package org.test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.json.bind.annotation.JsonbProperty;


public class EmployeeRequestDto {
    @JsonbProperty("gender_id")
    private Long genderId;
    @JsonbProperty("job_id")
    private Long jobId;
    @JsonbProperty("name")
    private String name;
    @JsonbProperty("last_name")
    private String lastName;
    @JsonbProperty("birthdate")
    private String birthdate;



    public Long getGenderId() {
        return genderId;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
