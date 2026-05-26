package org.example.companyportal.controller;

import org.example.companyportal.dto.EmployeeRequest;
import org.example.companyportal.service.EmployeeRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/requests")
public class ManagerRequestController {

    private final EmployeeRequestService employeeRequestService;

    public ManagerRequestController(EmployeeRequestService employeeRequestService) {
        this.employeeRequestService = employeeRequestService;
    }

    @GetMapping
    public List<EmployeeRequest> getAllRequests() {
        return employeeRequestService.getAllRequests();
    }

    @PutMapping("/{id}/approve")
    public EmployeeRequest approveRequest(@PathVariable Long id) {
        return employeeRequestService.approveRequest(id);
    }

    @PutMapping("/{id}/reject")
    public EmployeeRequest rejectRequest(@PathVariable Long id) {
        return employeeRequestService.rejectRequest(id);
    }
}