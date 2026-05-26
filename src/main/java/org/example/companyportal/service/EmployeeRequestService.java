package org.example.companyportal.service;

import org.example.companyportal.constants.EmployeeStatus;
import org.example.companyportal.dto.EmployeeRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmployeeRequestService {

    private final Map<Long, EmployeeRequest> requests = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public EmployeeRequest createRequest(String title, String createdBy) {
        Long id = idGenerator.getAndIncrement();

        EmployeeRequest request = new EmployeeRequest(
                id,
                title,
                createdBy,
                EmployeeStatus.PENDING
        );

        requests.put(id, request);
        return request;
    }

    public List<EmployeeRequest> getMyRequests(String username) {
        return requests.values()
                .stream()
                .filter(request -> request.createdBy().equals(username))
                .toList();
    }

    public List<EmployeeRequest> getAllRequests() {
        return new ArrayList<>(requests.values());
    }

    public EmployeeRequest approveRequest(Long id) {
        EmployeeRequest existingRequest = getRequestOrThrow(id);

        EmployeeRequest updatedRequest = new EmployeeRequest(
                existingRequest.id(),
                existingRequest.title(),
                existingRequest.createdBy(),
                EmployeeStatus.APPROVED
        );

        requests.put(id, updatedRequest);
        return updatedRequest;
    }

    public EmployeeRequest rejectRequest(Long id) {
        EmployeeRequest existingRequest = getRequestOrThrow(id);

        EmployeeRequest updatedRequest = new EmployeeRequest(
                existingRequest.id(),
                existingRequest.title(),
                existingRequest.createdBy(),
                EmployeeStatus.REJECTED
        );

        requests.put(id, updatedRequest);
        return updatedRequest;
    }

    private EmployeeRequest getRequestOrThrow(Long id) {
        EmployeeRequest request = requests.get(id);

        if (request == null) {
            throw new IllegalArgumentException("Request not found with id: " + id);
        }

        return request;
    }
}