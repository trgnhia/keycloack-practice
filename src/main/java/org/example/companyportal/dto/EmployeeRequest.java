package org.example.companyportal.dto;

import org.example.companyportal.constants.EmployeeStatus;

public record EmployeeRequest(
        Long id,
        String title,
        String createdBy,
        EmployeeStatus status
) {
}
