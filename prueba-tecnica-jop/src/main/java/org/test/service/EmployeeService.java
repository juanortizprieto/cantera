package org.test.service;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.context.ThreadContext;
import org.test.dto.EmployeResponseByJobDto;
import org.test.dto.EmployeeRequestDto;
import org.test.dto.EmployeeResponseDto;
import org.test.entity.*;
import org.test.repository.EmployeeRepo;
import org.test.repository.EmployeeRepository;
import org.test.utils.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;

@Transactional
@ApplicationScoped
public class EmployeeService {

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    EmployeeRepository rep;

    @Inject
    EmployeeRepo employeeRepo;

    @PersistenceContext
    private EntityManager entityManager;


    public Response create(EmployeeRequestDto employeeRequest) {
        EmployeeResponseDto responseBody = null;
        try {
            Long idEmployee = employeeRepository.createEmployee(employeeRequest);
            System.out.println("Employee "+idEmployee);
            responseBody = new EmployeeResponseDto();
            responseBody.setId(idEmployee>0?idEmployee:0L);
            responseBody.setSuccess(idEmployee>0);

        } catch (Exception e) {
            e.printStackTrace();
            responseBody = new EmployeeResponseDto();
            responseBody.setSuccess(false);
            responseBody.setId(0L);

        }
        return Response.ok(responseBody).build();
    }

    public Response findAllByJob(Long jobId) {
        EmployeResponseByJobDto responseBody = new EmployeResponseByJobDto();
        List<Employee> employees = Employee.listAll();
        if(employees.isEmpty()){
            responseBody.setEmployees(null);
            responseBody.setSuccess(false);
        }else{
            Map<String, List<Employee>> employeesByLastName = employees.stream()
                    .filter(emp -> emp.getJob().getId() == jobId)
                    .sorted(Comparator.comparing(Employee::getLastName))
                    .collect(Collectors.groupingBy(Employee::getLastName));

            if(employeesByLastName.isEmpty()){
                responseBody.setEmployees(null);
                responseBody.setSuccess(false);
            }else{
                responseBody.setEmployees(employeesByLastName);
                responseBody.setSuccess(true);
            }


        }

        return Response.ok(responseBody).build();
    }
    @Transactional(REQUIRES_NEW)
    public Response findByEmployeeIds(List<Long> employeeId) {
        List<Employee> employees = new ArrayList<>();
        ManagedExecutor executor = ManagedExecutor.builder()
                .maxAsync(employeeId.size())
                .propagated(ThreadContext.CDI, ThreadContext.TRANSACTION)
                .build();
        for (Long id:employeeId) {
            executor.runAsync(() -> {
                final Long employeeIdCopy = id;
                System.out.println("Hello from thread: " + Thread.currentThread().getName());
                try {

                    Employee emp = entityManager.find(Employee.class,employeeIdCopy);
                    employees.add(emp);

                } catch (Exception e) {
                     e.printStackTrace();
                }
            });
        }






        return Response.ok(employees).build();


    }

    public Response workedHours(EmployeeRequestDto requestDto) {
        EmployeeResponseDto responseBody = new EmployeeResponseDto();
        try {
            if(Utils.isValidDate(requestDto.getStartDate(),requestDto.getEndDate())){
                double workingHours = employeeRepository.workingHours(requestDto.getEmployeeId(),requestDto.getStartDate(),requestDto.getEndDate());
                responseBody.setWorkingHours(workingHours);
                responseBody.setSuccess(true);
                responseBody.setPayment(null);
            }else{
                responseBody.setSuccess(false);
                responseBody.setWorkingHours(null);
                responseBody.setPayment(null);

            }
        } catch (ParseException e) {
            responseBody.setSuccess(false);
            responseBody.setWorkingHours(null);
            responseBody.setPayment(null);
        }
        return Response.ok(responseBody).build();
    }

    public Response payment(EmployeeRequestDto requestDto) {
        EmployeeResponseDto responseBody = new EmployeeResponseDto();
        try {
            if(Utils.isValidDate(requestDto.getStartDate(),requestDto.getEndDate())){
                double payment = employeeRepository.payment(requestDto.getEmployeeId(),requestDto.getStartDate(),requestDto.getEndDate());
                responseBody.setPayment(payment);
                responseBody.setSuccess(true);
                responseBody.setWorkingHours(null);
            }else{
                responseBody.setSuccess(false);
                responseBody.setPayment(null);
                responseBody.setWorkingHours(null);
            }
        } catch (ParseException e) {
            responseBody.setSuccess(false);
            responseBody.setPayment(null);
            responseBody.setWorkingHours(null);
        }
        return Response.ok(responseBody).build();
    }
}
