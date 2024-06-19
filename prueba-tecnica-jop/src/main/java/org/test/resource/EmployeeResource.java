package org.test.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.test.dto.EmployeeRequestArrayDto;
import org.test.dto.EmployeeRequestDto;
import org.test.dto.EmployeeResponseDto;
import org.test.service.EmployeeService;

import java.util.HashMap;

@Path("/employee")
public class EmployeeResource {

    @Inject
    EmployeeService employeeService;
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(EmployeeRequestDto employeeRequest){

        return employeeService.create(employeeRequest);

    }

    @POST
    @Path("/byJob")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByJobId(EmployeeRequestDto requestDto){

        return employeeService.findAllByJob(requestDto.getJobId());

    }

    @POST
    @Path("/byEmployeeIds")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByEmployeeIds(EmployeeRequestArrayDto requestDto){

        return employeeService.findByEmployeeIds(requestDto.getEmployeeId());

    }
}
