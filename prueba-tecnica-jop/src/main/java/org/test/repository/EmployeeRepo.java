package org.test.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.test.entity.Employee;
@ApplicationScoped
public class EmployeeRepo implements PanacheRepositoryBase<Employee, Long> {
}
