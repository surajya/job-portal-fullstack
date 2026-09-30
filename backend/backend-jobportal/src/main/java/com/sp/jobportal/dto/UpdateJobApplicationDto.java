package com.sp.jobportal.dto;

public record UpdateJobApplicationDto(
        Long applicationId,
        JobApplicationStatus status,
        String notes
) {
}
