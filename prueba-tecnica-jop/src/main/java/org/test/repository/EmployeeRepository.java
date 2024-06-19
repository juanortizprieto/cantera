package org.test.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.test.dto.EmployeeRequestDto;
import org.test.utils.Utils;

import java.text.ParseException;
import java.util.Date;

@ApplicationScoped
public class EmployeeRepository {

    @Inject
    EntityManager entityManager;

    public Long createEmployee(EmployeeRequestDto employeeRequest) throws Exception {
        StoredProcedureQuery sp = entityManager.createStoredProcedureQuery("JOP.INSERT_EMPLOYEE");
        sp.registerStoredProcedureParameter("in_name",String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("in_last_name",String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("in_birthdate", String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("in_job_id",Long.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("in_gender_id",Long.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("out_id_employee",Long.class, ParameterMode.OUT);

        sp.setParameter("in_name",employeeRequest.getName());
        sp.setParameter("in_last_name",employeeRequest.getLastName());
        sp.setParameter("in_birthdate", employeeRequest.getBirthdate());
        sp.setParameter("in_job_id",employeeRequest.getJobId());
        sp.setParameter("in_gender_id",employeeRequest.getGenderId());

        sp.execute();

        return (Long) sp.getOutputParameterValue("out_id_employee");

    }
}
