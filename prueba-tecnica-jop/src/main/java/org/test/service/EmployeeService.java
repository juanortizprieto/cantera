package org.test.service;

import io.smallrye.context.CleanAutoCloseable;
import io.smallrye.context.SmallRyeThreadContext;
import io.smallrye.context.api.CurrentThreadContext;
import io.smallrye.context.api.ThreadContextConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.context.ThreadContext;
import org.test.dto.EmployeResponseByJobDto;
import org.test.dto.EmployeeRequestDto;
import org.test.dto.EmployeeResponseDto;
import org.test.entity.*;
import org.test.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Transactional
@ApplicationScoped
public class EmployeeService {

    @Inject
    EmployeeRepository employeeRepository;


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

    public Response findByEmployeeIds(List<Long> employeeId) {
        ManagedExecutor executor = ManagedExecutor.builder()
                .maxAsync(5)
                .propagated(ThreadContext.CDI)
                .build();

        List<CompletableFuture<Employee>> futures = employeeId.stream()
                .map(id -> CompletableFuture.supplyAsync(() -> Employee.findById(id), executor)
                        .thenApplyAsync(result -> (Employee) result))
                .collect(Collectors.toList());


        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));


        allOf.join();

        
        List<Employee> employees = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        return Response.ok(employees).build();

    }
}
