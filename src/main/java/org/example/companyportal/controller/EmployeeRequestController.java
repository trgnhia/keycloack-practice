package org.example.companyportal.controller;

import org.example.companyportal.dto.CreateEmployeeRequest;
import org.example.companyportal.dto.EmployeeRequest;
import org.example.companyportal.service.EmployeeRequestService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeRequestController {

    private final EmployeeRequestService employeeRequestService;

    public EmployeeRequestController(EmployeeRequestService employeeRequestService) {
        this.employeeRequestService = employeeRequestService;
    }

    @GetMapping("/requests/ping")
    public String ping() {
        return "requests controller is working";
    }

    @PostMapping("/requests")
    public EmployeeRequest createRequest(
            @RequestBody CreateEmployeeRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String username = jwt.getClaimAsString("preferred_username");

        return employeeRequestService.createRequest(
                request.title(),
                username
        );
    }

    @GetMapping("/requests/my")
    public List<EmployeeRequest> getMyRequests(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");

        return employeeRequestService.getMyRequests(username);
    }
}